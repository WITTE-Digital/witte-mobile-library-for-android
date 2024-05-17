
package digital.witte.wittemobilelibrary.box;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxFeedbackV3ParserTest {

    @Test
    public void testParseValidDataCase01() {
        byte[] data = {
                (byte) 0x01, // [00]
                (byte) 0x00, // [01]
                (byte) 0x02, // [02]
                (byte) 0x01, // [03]
                (byte) 0x00, // [04]
                (byte) 0x01, // [05]
                (byte) 0x03, // [06]
                (byte) 0x59, // [07]
                (byte) 0x43, // [08]
                (byte) 0x40, // [09]
                (byte) 0x00, // [10]
                (byte) 0x00  // [11]
        };

        BoxFeedbackV3 expected = new BoxFeedbackV3();
        expected.setBatteryStateOfCharge((byte) 89);
        expected.setBatteryIsCharging(false);
        expected.setBatteryChargerIsConnected(false);
        expected.setDrawerState(false);
        expected.setDrawerAccessibility(true);

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNotNull(result);
        assertEquals(expected.getBatteryStateOfCharge(), result.getBatteryStateOfCharge());
        assertEquals(expected.isBatteryIsCharging(), result.isBatteryIsCharging());
        assertEquals(expected.isBatteryChargerIsConnected(), result.isBatteryChargerIsConnected());
        assertEquals(expected.isDrawerState(), result.isDrawerState());
        assertEquals(expected.isDrawerAccessibility(), result.isDrawerAccessibility());
    }

    @Test
    public void testParseValidDataCase02() {
        byte[] data = {
                (byte) 0x01, // [00]
                (byte) 0x00, // [01]
                (byte) 0x02, // [02]
                (byte) 0x01, // [03]
                (byte) 0x00, // [04]
                (byte) 0x01, // [05]
                (byte) 0x03, // [06]
                (byte) 0x5c, // [07]
                (byte) 0x43, // [08]
                (byte) 0x00, // [09]
                (byte) 0x00, // [10]
                (byte) 0x00  // [11]
        };

        BoxFeedbackV3 expected = new BoxFeedbackV3();
        expected.setBatteryStateOfCharge((byte) 92);
        expected.setBatteryIsCharging(false);
        expected.setBatteryChargerIsConnected(false);
        expected.setDrawerState(false);
        expected.setDrawerAccessibility(false);

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNotNull(result);
        assertEquals(expected.getBatteryStateOfCharge(), result.getBatteryStateOfCharge());
        assertEquals(expected.isBatteryIsCharging(), result.isBatteryIsCharging());
        assertEquals(expected.isBatteryChargerIsConnected(), result.isBatteryChargerIsConnected());
        assertEquals(expected.isDrawerState(), result.isDrawerState());
        assertEquals(expected.isDrawerAccessibility(), result.isDrawerAccessibility());
    }

    @Test
    public void testParseValidDataCase04() {
        byte[] data = {
                (byte) 0x01, // [00]
                (byte) 0x00, // [01]
                (byte) 0x01, // [02]
                (byte) 0x03, // [03]
                (byte) 0x5b, // [04]
                (byte) 0x43, // [05]
                (byte) 0x00  // [06]
        };

        BoxFeedbackV3 expected = new BoxFeedbackV3();
        expected.setBatteryStateOfCharge((byte) 91);
        expected.setBatteryIsCharging(false);
        expected.setBatteryChargerIsConnected(false);
        expected.setDrawerState(false);
        expected.setDrawerAccessibility(false);

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNotNull(result);
        assertEquals(expected.getBatteryStateOfCharge(), result.getBatteryStateOfCharge());
        assertEquals(expected.isBatteryIsCharging(), result.isBatteryIsCharging());
        assertEquals(expected.isBatteryChargerIsConnected(), result.isBatteryChargerIsConnected());
        assertEquals(expected.isDrawerState(), result.isDrawerState());
        assertEquals(expected.isDrawerAccessibility(), result.isDrawerAccessibility());
    }

    @Test
    public void testParseNullData() {
        byte[] data = null;

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNull(result);
    }

    @Test
    public void testParseInvalidDataLength() {
        byte[] data = {0x02, 0x03};

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNull(result);
    }

    @Test
    public void testParseLegacyData() {
        byte[] data = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNull(result);
    }

    @Test
    public void testParseInvalidGroupData() {
        byte[] data = {0x02, 0x03, 0x02, 0x01, 0x02, 0x03};

        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNull(result);
    }

    @Test
    public void testParseValidDataCase01With2NfcTags() {
        BoxFeedbackV3 expected = new BoxFeedbackV3();
        expected.setBatteryStateOfCharge((byte) 86);
        expected.setBatteryIsCharging(false);
        expected.setBatteryChargerIsConnected(false);
        expected.setDrawerState(false);
        expected.setDrawerAccessibility(true);
        expected.setNfcTag1Uid("041858DA181390");
        expected.setNfcTag2Uid("04758ECAF26281");

        String base64 = "AQACAQADEAcEGFjaGBOQBwR1jsryYoEBA1ZBQA==";
        byte[] data = java.util.Base64.getDecoder().decode(base64);
        BoxFeedbackV3 result = BoxFeedbackV3Parser.parse(data);

        assertNotNull(result);
        assertEquals(expected.getBatteryStateOfCharge(), result.getBatteryStateOfCharge());
        assertEquals(expected.isBatteryIsCharging(), result.isBatteryIsCharging());
        assertEquals(expected.isBatteryChargerIsConnected(), result.isBatteryChargerIsConnected());
        assertEquals(expected.isDrawerState(), result.isDrawerState());
        assertEquals(expected.isDrawerAccessibility(), result.isDrawerAccessibility());
        assertEquals(expected.getNfcTag1Uid(), result.getNfcTag1Uid());
        assertEquals(expected.getNfcTag2Uid(), result.getNfcTag2Uid());
        assertEquals(expected.getNfcTag3Uid(), result.getNfcTag3Uid());
    }
}