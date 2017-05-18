package it.unisa.petra.PowerProfile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dardin88
 */
public class PowerProfileParserTester {

    /**
     * Test of parseFile method of class PowerProfileParserTester.
     */
    @Test
    public void testNumberOfEnergyInfo() throws Exception {
        String fileName = "src/test/resources/power_profile_example.xml";
        PowerProfile powerProfile = PowerProfileParser.parseFile(fileName);
        assertEquals(47.05, powerProfile.getCpuInfo().get(384000), 0.00001);
        assertEquals(62.09, powerProfile.getDevices().get("wifi.active"), 0.00001);
    }

}
