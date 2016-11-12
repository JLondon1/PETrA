package it.unisa.petra.ui;

import it.unisa.petra.process.PETrAProcess;
import it.unisa.petra.process.PETrAProcessOutput;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Antonio Prota
 */
public class MainUI extends javax.swing.JFrame {

    public MainUI() {
        initComponents();
        PrintStream out = new PrintStream(new TextAreaOutputStream(statusLabel));
        System.setOut(out);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        appNameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        apkLocationField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        platformFolder = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        powerprofileFile = new javax.swing.JTextField();
        startProcessButton = new javax.swing.JButton();
        apkLocationButton = new javax.swing.JButton();
        androidSDKFolderButton = new javax.swing.JButton();
        powerprofileButton = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        runsSlider = new javax.swing.JSlider();
        viewStats = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        statusLabel = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        timeInteractionsSlider = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        monkeyInteractionsSlider = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        scriptLocationField = new javax.swing.JTextField();
        scriptLocationButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PETrA");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/petra.png")).getImage());
        setResizable(false);

        jLabel1.setText("App Name");

        appNameField.setToolTipText("Name of the app to analyze (must be the same of the app process).");

        jLabel3.setText("Apk Location");

        apkLocationField.setEditable(false);
        apkLocationField.setToolTipText("Apk to analyze.");

        jLabel4.setText("Runs");

        jLabel7.setText("Android SDK folder");

        platformFolder.setEditable(false);
        platformFolder.setToolTipText("Path of the Android SDK Platform Tools folder.");

        jLabel9.setText("Power Profile File");

        powerprofileFile.setEditable(false);
        powerprofileFile.setToolTipText("Device power profile (see https://source.android.com/devices/tech/power/).");

        startProcessButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/play-button.png"))); // NOI18N
        startProcessButton.setText("Start Energy Estimation");
        startProcessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startProcessButtonActionPerformed(evt);
            }
        });

        apkLocationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        apkLocationButton.setText("Open");
        apkLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apkLocationButtonActionPerformed(evt);
            }
        });

        androidSDKFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        androidSDKFolderButton.setText("Open");
        androidSDKFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                androidSDKFolderButtonActionPerformed(evt);
            }
        });

        powerprofileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        powerprofileButton.setText("Open");
        powerprofileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                powerprofileButtonActionPerformed(evt);
            }
        });

        progressBar.setStringPainted(true);

        runsSlider.setMajorTickSpacing(5);
        runsSlider.setMaximum(30);
        runsSlider.setMinorTickSpacing(1);
        runsSlider.setPaintLabels(true);
        runsSlider.setPaintTicks(true);
        runsSlider.setSnapToTicks(true);
        runsSlider.setToolTipText("Number of repetitions of the measurement process.");
        runsSlider.setValue(0);

        viewStats.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graph.png"))); // NOI18N
        viewStats.setText("Statistics");
        viewStats.setEnabled(false);
        viewStats.setFocusPainted(false);
        viewStats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewStatsActionPerformed(evt);
            }
        });

        jScrollPane1.setToolTipText("");

        statusLabel.setEditable(false);
        jScrollPane1.setViewportView(statusLabel);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Monkey Options"));

        jLabel6.setText("Time Between Interactions");

        timeInteractionsSlider.setMajorTickSpacing(500);
        timeInteractionsSlider.setMaximum(5000);
        timeInteractionsSlider.setMinorTickSpacing(100);
        timeInteractionsSlider.setPaintLabels(true);
        timeInteractionsSlider.setPaintTicks(true);
        timeInteractionsSlider.setSnapToTicks(true);
        timeInteractionsSlider.setToolTipText("Time between an Android Monkey event and the next one in ms.");
        timeInteractionsSlider.setValue(0);

        jLabel5.setText("Interactions");

        monkeyInteractionsSlider.setMajorTickSpacing(500);
        monkeyInteractionsSlider.setMaximum(5000);
        monkeyInteractionsSlider.setMinorTickSpacing(100);
        monkeyInteractionsSlider.setPaintLabels(true);
        monkeyInteractionsSlider.setPaintTicks(true);
        monkeyInteractionsSlider.setSnapToTicks(true);
        monkeyInteractionsSlider.setToolTipText("Number of Android Monkey event to perform.");
        monkeyInteractionsSlider.setValue(0);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 109, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeInteractionsSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                    .addComponent(monkeyInteractionsSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(46, 46, 46))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(monkeyInteractionsSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(timeInteractionsSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Monkeyrunner Options"));

        jLabel2.setText("Script Location");

        scriptLocationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/folder.png"))); // NOI18N
        scriptLocationButton.setText("Open");
        scriptLocationButton.setToolTipText("The location of the Monkey runner script.");
        scriptLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scriptLocationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(scriptLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scriptLocationButton))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(scriptLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(scriptLocationButton))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(apkLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(apkLocationButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(platformFolder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                                    .addComponent(powerprofileFile))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(androidSDKFolderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(powerprofileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(runsSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(startProcessButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewStats, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(appNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(apkLocationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(apkLocationButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(runsSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(platformFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(androidSDKFolderButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(powerprofileFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(powerprofileButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startProcessButton)
                    .addComponent(viewStats, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        startProcessButton.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startProcessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startProcessButtonActionPerformed
        statusLabel.setText(null);
        viewStats.setEnabled(false);

        String appName = this.appNameField.getText();
        String apkLocationPath = this.apkLocationField.getText();
        int runs = this.runsSlider.getValue();
        int interactions = monkeyInteractionsSlider.getValue();
        int timeBetweenInteractions = timeInteractionsSlider.getValue();
        String scriptLocationPath = this.scriptLocationField.getText();
        String sdkFolderPath = platformFolder.getText();
        String powerProfilePath = powerprofileFile.getText();

        String outputLocationPath = new File(apkLocationPath).getParent() + File.separator + "test_data" + File.separator + appName;

        boolean valid = true;

        if (appName.isEmpty()) {
            System.out.println("App name missing.");
            valid = false;
        }

        if (apkLocationPath.isEmpty()) {
            System.out.println("Apk location missing.");
            valid = false;
        }

        if (interactions <= 0 && scriptLocationPath.isEmpty()) {
            System.out.println("You must perform at least one Monkey interaction or select a Monkeyrunner script.");
            valid = false;
        }
        
        if (runs <= 0) {
            System.out.println("You must execute at least one run.");
            valid = false;
        }

        if (sdkFolderPath.isEmpty()) {
            System.out.println("Please select the Android SDK Platform Tools folder.");
            valid = false;
        }

        if (powerProfilePath.isEmpty()) {
            System.out.println("Please select an Android Power Profile file.");
            valid = false;
        }

        if (valid == false) {
            return;
        }
        Task task = new Task(appName, apkLocationPath, interactions, timeBetweenInteractions,
                scriptLocationPath, runs, sdkFolderPath, powerProfilePath, outputLocationPath);
        task.addPropertyChangeListener((PropertyChangeEvent evt1) -> {
            if ("progress".equals(evt1.getPropertyName())) {
                progressBar.setValue((Integer) evt1.getNewValue());
            }
        });
        task.execute();
    }//GEN-LAST:event_startProcessButtonActionPerformed

    private void apkLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apkLocationButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Apk Files", "apk");
        chooser.setFileFilter(filter);
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            apkLocationField.setText(filename);
        }
    }//GEN-LAST:event_apkLocationButtonActionPerformed

    private void androidSDKFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_androidSDKFolderButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            platformFolder.setText(filename);
        }
    }//GEN-LAST:event_androidSDKFolderButtonActionPerformed

    private void powerprofileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_powerprofileButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            powerprofileFile.setText(filename);
        }
    }//GEN-LAST:event_powerprofileButtonActionPerformed

    private void viewStatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewStatsActionPerformed
        this.setVisible(false);
        String outputLocationPath = new File(apkLocationField.getText()).getParent() + File.separator + "test_data" + File.separator + appNameField.getText();
        new StatsUI(outputLocationPath).setVisible(true);
    }//GEN-LAST:event_viewStatsActionPerformed

    private void scriptLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scriptLocationButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(null);
        if (res == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            String filename = f.getAbsolutePath();
            scriptLocationField.setText(filename);
        }
    }//GEN-LAST:event_scriptLocationButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            OUTER:
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (info.getName() != null) {
                    switch (info.getName()) {
                        case "GTK+":
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break OUTER;
                        case "Windows":
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break OUTER;
                        case "Nimbus":
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(
                () -> {
                    new MainUI().setVisible(true);
                }
        );
    }

    class Task extends SwingWorker<Void, Void> {

        private final String appName;
        private final String apkLocationPath;
        private final int interactions;
        private final int timeBetweenInteractions;
        private final String scriptLocationPath;
        private final int runs;
        private final String sdkFolderPath;
        private final String powerProfilePath;
        private final String outputLocationPath;

        public Task(String appName, String apkLocationPath, int interactions, int timeBetweenInteractions, String scriptLocationPath,
                int runs, String sdkFolderPath, String powerProfilePath, String outputLocationPath) {
            this.appName = appName;
            this.apkLocationPath = apkLocationPath;
            this.interactions = interactions;
            this.timeBetweenInteractions = timeBetweenInteractions;
            this.scriptLocationPath = scriptLocationPath;
            this.runs = runs;
            this.sdkFolderPath = sdkFolderPath;
            this.powerProfilePath = powerProfilePath;
            this.outputLocationPath = outputLocationPath;
        }

        @Override
        public Void doInBackground() {
            try {
                apkLocationButton.setEnabled(false);
                scriptLocationButton.setEnabled(false);
                androidSDKFolderButton.setEnabled(false);
                powerprofileButton.setEnabled(false);
                startProcessButton.setEnabled(false);
                PETrAProcess process = new PETrAProcess();
                setProgress(0);
                int progress;
                int trials = 0;

                File appDataFolder = new File(outputLocationPath);
                appDataFolder.mkdirs();
                File seedsFile = new File(outputLocationPath + File.separator + "seeds");
                BufferedWriter seedsWriter = new BufferedWriter(new FileWriter(seedsFile, true));
                process.installApp(outputLocationPath, apkLocationPath);

                int timeCapturing = (interactions * timeBetweenInteractions) / 1000;

                if (timeCapturing <= 0) {
                    timeCapturing = 1;
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int run = 1; run <= runs; run++) {
                    try {
                        PETrAProcessOutput output = process.playRun(run, trials, appName, interactions, timeBetweenInteractions, timeCapturing,
                                scriptLocationPath, sdkFolderPath, powerProfilePath, outputLocationPath);
                        seedsWriter.append(output.getSeed() + "\n");
                        timeCapturing = output.getTimeCapturing();
                        progress = (100 * run / runs);
                        setProgress(progress);
                    } catch (InterruptedException | ParseException | IOException ex) {
                        run--;
                        trials++;
                    }
                }
                startProcessButton.setEnabled(true);
                apkLocationButton.setEnabled(true);
                scriptLocationButton.setEnabled(true);
                androidSDKFolderButton.setEnabled(true);
                powerprofileButton.setEnabled(true);
                viewStats.setEnabled(true);
                process.uninstallApp(appName);
            } catch (NoDeviceFoundException | IOException ex) {
                startProcessButton.setEnabled(true);
                apkLocationButton.setEnabled(true);
                scriptLocationButton.setEnabled(true);
                androidSDKFolderButton.setEnabled(true);
                powerprofileButton.setEnabled(true);
                viewStats.setEnabled(false);
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton androidSDKFolderButton;
    private javax.swing.JButton apkLocationButton;
    private javax.swing.JTextField apkLocationField;
    private javax.swing.JTextField appNameField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider monkeyInteractionsSlider;
    private javax.swing.JTextField platformFolder;
    private javax.swing.JButton powerprofileButton;
    private javax.swing.JTextField powerprofileFile;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JSlider runsSlider;
    private javax.swing.JButton scriptLocationButton;
    private javax.swing.JTextField scriptLocationField;
    private javax.swing.JButton startProcessButton;
    private javax.swing.JTextArea statusLabel;
    private javax.swing.JSlider timeInteractionsSlider;
    private javax.swing.JButton viewStats;
    // End of variables declaration//GEN-END:variables
}
