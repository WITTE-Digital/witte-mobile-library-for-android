package digital.witte.wittemobilelibrary.box;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxFeedbackV3Test {

    @Test
    public void testGetBatteryStateOfCharge() {
        BoxFeedbackV3 boxFeedback = new BoxFeedbackV3();
        byte expected = 50;
        boxFeedback.setBatteryStateOfCharge(expected);
        byte result = boxFeedback.getBatteryStateOfCharge();
        assertEquals(expected, result);
    }

    @Test
    public void testIsBatteryIsCharging() {
        BoxFeedbackV3 boxFeedback = new BoxFeedbackV3();
        boolean expected = true;
        boxFeedback.setBatteryIsCharging(expected);
        boolean result = boxFeedback.isBatteryIsCharging();
        assertEquals(expected, result);
    }

    @Test
    public void testIsBatteryChargerIsConnected() {
        BoxFeedbackV3 boxFeedback = new BoxFeedbackV3();
        boolean expected = true;
        boxFeedback.setBatteryChargerIsConnected(expected);
        boolean result = boxFeedback.isBatteryChargerIsConnected();
        assertEquals(expected, result);
    }

    @Test
    public void testIsDrawerState() {
        BoxFeedbackV3 boxFeedback = new BoxFeedbackV3();
        boolean expected = true;
        boxFeedback.setDrawerState(expected);
        boolean result = boxFeedback.isDrawerState();
        assertEquals(expected, result);
    }

    @Test
    public void testIsDrawerAccessibility() {
        BoxFeedbackV3 boxFeedback = new BoxFeedbackV3();
        boolean expected = true;
        boxFeedback.setDrawerAccessibility(expected);
        boolean result = boxFeedback.isDrawerAccessibility();
        assertEquals(expected, result);
    }
}