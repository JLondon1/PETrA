package it.unisa.petra.core;

import it.unisa.petra.core.batterystats.BatteryStatsParser;
import it.unisa.petra.core.batterystats.EnergyInfo;
import it.unisa.petra.core.exceptions.ADBNotFoundException;
import it.unisa.petra.core.exceptions.MonkeyPlaybackNotFoundException;
import it.unisa.petra.core.exceptions.NoDeviceFoundException;
import it.unisa.petra.core.powerprofile.PowerProfile;
import it.unisa.petra.core.powerprofile.PowerProfileParser;
import it.unisa.petra.core.systrace.CpuFrequency;
import it.unisa.petra.core.systrace.SysTrace;
import it.unisa.petra.core.systrace.SysTraceParser;
import it.unisa.petra.core.traceview.TraceLine;
import it.unisa.petra.core.traceview.TraceViewParser;
import it.unisa.petra.core.traceview.TraceviewStructure;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Process {

    public void installApp(String apkLocation, String sdkFolderPath) throws NoDeviceFoundException, ADBNotFoundException {

        this.checkADBExists(sdkFolderPath);

        this.executeCommand("adb shell dumpsys battery set ac 0", null);
        this.executeCommand("adb shell dumpsys battery set usb 0", null);

        System.out.println("Installing app.");
        this.executeCommand("adb install " + apkLocation, null);
    }

    public void uninstallApp(String appName, String sdkFolderPath) throws NoDeviceFoundException, ADBNotFoundException {

        this.checkADBExists(sdkFolderPath);

        System.out.println("Uninstalling app.");
        this.executeCommand("adb shell pm uninstall " + appName, null);
    }

    public ProcessOutput playRun(int run, String appName, int interactions, int timeBetweenInteractions,
                                 int timeCapturing, String scriptLocationPath, String sdkFolderPath, String powerProfileFile, String outputLocation)
            throws InterruptedException, IOException, NoDeviceFoundException, ADBNotFoundException, MonkeyPlaybackNotFoundException {

        this.checkADBExists(sdkFolderPath);

        File platformToolsFolder = new File(sdkFolderPath + File.separator + "platform-tools");
        File toolsFolder = new File(sdkFolderPath + File.separator + "tools");

        Random random = new Random();
        String seed = random.nextInt() + "";

        if (scriptLocationPath.isEmpty()) {
            System.out.println("Run " + run + ": seed: " + seed);
        }
        String runDataFolderName = outputLocation + "run_" + run + File.separator;
        File runDataFolder = new File(runDataFolderName);

        runDataFolder.mkdirs();

        String batteryStatsFilename = runDataFolderName + "batterystats";
        String systraceFilename = runDataFolderName + "systrace";
        String traceviewFilename = runDataFolderName + "tracedump";

        this.executeCommand("adb shell pm clear " + appName, null);

        System.out.println("Run " + run + ": resetting battery stats.");
        this.executeCommand("adb shell dumpsys batterystats --reset", null);

        System.out.println("Run " + run + ": opening app.");
        this.executeCommand("adb shell input keyevent 82", null);
        this.executeCommand("adb shell monkey -p " + appName + " 1", null);
        this.executeCommand("adb shell am broadcast -a org.thisisafactory.simiasque.SET_OVERLAY --ez enable true", null);

        System.out.println("Run " + run + ": start profiling.");
        this.executeCommand("adb shell am profile start " + appName + " ./data/local/tmp/log.trace", null);
        Date time1 = new Date();

        System.out.println("Run " + run + ": capturing system traces.");
        SysTraceRunner sysTraceRunner = new SysTraceRunner(timeCapturing, systraceFilename, platformToolsFolder);
        Thread systraceThread = new Thread(sysTraceRunner);
        systraceThread.start();

        this.executeCommand("adb kill-server", null);
        this.executeCommand("adb start-server", null);

        if (scriptLocationPath.isEmpty() && interactions > 0) {
            System.out.println("Run " + run + ": executing random actions.");
            this.executeCommand("adb shell monkey -p " + appName + " -s " + seed + " --throttle " + timeBetweenInteractions + " --ignore-crashes --ignore-timeouts --ignore-security-exceptions " + interactions, null);
        } else {
            System.out.println("Run " + run + ": running monkeyrunner script.");
            this.executeCommand(toolsFolder + "/bin/monkeyrunner " + "src/main/resources/monkey_playback.py " + scriptLocationPath, null);
        }

        Date time2 = new Date();
        long timespent = time2.getTime() - time1.getTime();

        timeCapturing = (int) ((timespent + 10000) / 1000);

        System.out.println("Run " + run + ": stop profiling.");
        this.executeCommand("adb shell am profile stop " + appName, null);

        System.out.println("Run " + run + ": saving battery stats.");
        this.executeCommand("adb shell dumpsys batterystats", new File(batteryStatsFilename));

        System.out.println("Run " + run + ": saving traceviews.");
        this.executeCommand("adb pull ./data/local/tmp/log.trace " + runDataFolderName, null);
        this.executeCommand(platformToolsFolder + "/dmtracedump -o " + runDataFolderName + "log.trace", new File(traceviewFilename));

        systraceThread.join();

        try {
            System.out.println("Run " + run + ": aggregating results.");

            if (powerProfileFile.isEmpty()) {
                System.out.println("Run " + run + ": extracting power profile.");
                this.extractPowerProfile(outputLocation);
                powerProfileFile = outputLocation + "power_profile.xml";
            }

            System.out.println("Run " + run + ": parsing power profile.");
            PowerProfile powerProfile = PowerProfileParser.parseFile(powerProfileFile);

            List<TraceLine> traceLinesWiConsumptions = parseAndAggregateResults(traceviewFilename, batteryStatsFilename,
                    systraceFilename, powerProfile, appName, run);

            PrintWriter resultsWriter = new PrintWriter(runDataFolderName + "result.csv", "UTF-8");
            resultsWriter.println("signature, joule, seconds");

            for (TraceLine traceLine : traceLinesWiConsumptions) {
                resultsWriter.println(traceLine.getSignature() + "," + traceLine.getConsumption() + "," + traceLine.getTimeLength());
            }

            resultsWriter.flush();
        } finally {
            System.out.println("Run " + run + ": stop app.");
            this.executeCommand("adb shell am broadcast -a org.thisisafactory.simiasque.SET_OVERLAY --ez enable false", null);
            this.executeCommand("adb shell am force-stop " + appName, null);
            this.executeCommand("adb shell pm clear " + appName, null);
        }

        this.executeCommand("adb shell dumpsys battery reset", null);

        System.out.println("Run " + run + ": complete.");
        return new ProcessOutput(timeCapturing, seed);
    }

    private void extractPowerProfile(String outputLocation) throws NoDeviceFoundException {
        this.executeCommand("adb pull /system/framework/framework-res.apk", null);
        this.executeCommand("java -jar src/main/resources/apktool_2.0.3.jar if framework-res.apk", null);
        this.executeCommand("java -jar src/main/resources/apktool_2.0.3.jar d framework-res.apk", null);
        this.executeCommand("mv framework-res/res/xml/power_profile.xml " + outputLocation, null);
    }

    List<TraceLine> parseAndAggregateResults(String traceviewFilename, String batteryStatsFilename, String systraceFilename,
                                             PowerProfile powerProfile, String appName, int run) throws IOException {
        List<TraceLine> traceLinesWConsumption = new ArrayList();

        System.out.println("Run " + run + ": elaborating traceview info.");
        TraceviewStructure traceviewStructure = TraceViewParser.parseFile(traceviewFilename, appName);
        List<TraceLine> traceLines = traceviewStructure.getTraceLines();
        int traceviewLength = traceviewStructure.getEndTime();
        int traceviewStart = traceviewStructure.getStartTime();

        System.out.println("Run " + run + ": elaborating battery stats info.");
        List<EnergyInfo> energyInfoArray = BatteryStatsParser.parseFile(batteryStatsFilename, traceviewStart, traceviewLength);

        System.out.println("Run " + run + ": elaborating sys trace stats info.");
        SysTrace cpuInfo = SysTraceParser.parseFile(systraceFilename, traceviewStart, traceviewLength);

        System.out.println("Run " + run + ": aggregating results.");
        energyInfoArray = this.mergeEnergyInfo(energyInfoArray, cpuInfo, powerProfile.computeNumberOfCores());
        for (TraceLine traceLine : traceLines) {
            traceLinesWConsumption.add(this.calculateConsumption(traceLine, energyInfoArray, powerProfile));
        }

        return traceLinesWConsumption;
    }

    private List<EnergyInfo> mergeEnergyInfo(List<EnergyInfo> energyInfoArray, SysTrace cpuInfo, int numberOfCores) {

        List<Integer> cpuFrequencies = new ArrayList<>(numberOfCores);

        for (EnergyInfo energyInfo : energyInfoArray) {
            int fixedEnergyInfoTime = cpuInfo.getSystraceStartTime() + energyInfo.getTime() * 1000; //systrace time are in nanoseconds
            for (CpuFrequency frequency : cpuInfo.getFrequencies()) {
                if (frequency.getTime() <= fixedEnergyInfoTime) {
                    int cpuId = frequency.getCore();
                    if (cpuFrequencies.size() < cpuId + 1) {
                        cpuFrequencies.add(frequency.getValue());
                    } else {
                        cpuFrequencies.set(cpuId, frequency.getValue());
                    }
                    energyInfo.setCpuFrequencies(cpuFrequencies);
                }
            }
        }
        return energyInfoArray;
    }

    private TraceLine calculateConsumption(TraceLine traceLine, List<EnergyInfo> energyInfoArray, PowerProfile powerProfile) {

        double joule = 0;
        double totalSeconds = 0;

        int numberOfCores = powerProfile.computeNumberOfCores();

        boolean[] previouslyIdle = new boolean[numberOfCores];

        for (EnergyInfo energyInfo : energyInfoArray) {

            double ampere = 0;

            List<Integer> cpuFrequencies = energyInfo.getCpuFrequencies();

            for (int i = 0; i < numberOfCores; i++) {
                int coreFrequency = cpuFrequencies.get(i);
                int coreCluster = powerProfile.getClusterByCore(i);
                ampere += powerProfile.getCpuConsumptionByFrequency(coreCluster, coreFrequency) / 1000;
                if (coreFrequency != 0) {
                    if (previouslyIdle[i]) {
                        ampere += powerProfile.getDevices().get("cpu.awake") / 1000;
                    }
                } else {
                    previouslyIdle[i] = true;
                }
            }

            for (String deviceString : energyInfo.getDevices()) {
                if (deviceString.contains("wifi")) {
                    ampere += powerProfile.getDevices().get("wifi.on") / 1000;
                } else if (deviceString.contains("wifi.scanning")) {
                    ampere += powerProfile.getDevices().get("wifi.scan") / 1000;
                } else if (deviceString.contains("wifi.running")) {
                    ampere += powerProfile.getDevices().get("wifi.active") / 1000;
                } else if (deviceString.contains("phone.scanning")) {
                    ampere += powerProfile.getDevices().get("radio.scan") / 1000;
                } else if (deviceString.contains("phone.running")) {
                    ampere += powerProfile.getDevices().get("radio.active") / 1000;
                } else if (deviceString.contains("bluetooth")) {
                    ampere += powerProfile.getDevices().get("bluetooth.on") / 1000;
                } else if (deviceString.contains("bluetooth.running")) {
                    ampere += powerProfile.getDevices().get("bluetooth.active") / 1000;
                } else if (deviceString.contains("screen")) {
                    ampere += powerProfile.getDevices().get("screen.on") / 1000;
                } else if (deviceString.contains("gps")) {
                    ampere += powerProfile.getDevices().get("gps.on") / 1000;
                }
            }

            int phoneSignalStrength = energyInfo.getPhoneSignalStrength();

            if (powerProfile.getRadioInfo().size() == phoneSignalStrength - 1) {
                ampere += powerProfile.getRadioInfo().get(phoneSignalStrength - 1) / 1000;
            } else {
                ampere += powerProfile.getRadioInfo().get(powerProfile.getRadioInfo().size() - 1) / 1000;
            }


            double watt = ampere * energyInfo.getVoltage() / 1000;
            double microseconds = 0;
            if (traceLine.getEntrance() >= energyInfo.getTime()) {
                if (traceLine.getEntrance() > energyInfo.getTime()) {
                    microseconds = traceLine.getExit() - energyInfo.getTime();
                } else {
                    microseconds = traceLine.getExit() - traceLine.getEntrance();
                }
            }
            double seconds = microseconds / 1000000000;
            totalSeconds += seconds;
            joule += watt * seconds;
        }

        traceLine.setTimeLength(totalSeconds);
        traceLine.setConsumption(joule);

        return traceLine;
    }

    private void executeCommand(String command, File outputFile) throws NoDeviceFoundException {
        try {
            List<String> listCommands = new ArrayList<>();

            String[] arrayExplodedCommands = command.split(" ");
            listCommands.addAll(Arrays.asList(arrayExplodedCommands));
            ProcessBuilder pb = new ProcessBuilder(listCommands);
            pb.redirectErrorStream(true);
            if (outputFile != null) {
                pb.redirectOutput(outputFile);
            }

            java.lang.Process commandProcess = pb.start();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.contains("error: no devices/emulators found")) {
                        throw new NoDeviceFoundException();
                    }
                }

                commandProcess.waitFor();
                Thread.sleep(3000);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkADBExists(String sdkFolderPath) throws ADBNotFoundException {
        String adbPath = sdkFolderPath + "platform-tools/adb";
        File adbFile = new File(adbPath);
        if (!adbFile.exists()) {
            throw new ADBNotFoundException();
        }
    }

}
