package digital.witte.wittemobilelibrary.box;

import static org.junit.Assert.assertEquals;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import digital.witte.wittemobilelibrary.util.ByteUtils;

@Config(sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class BoxFeedbackTest {
    private final static String BoxFeedbackLocked01 = "4C-42-0F-FA-50-08-00-37-00-D8";
    private final static String BoxFeedbackLocked02 = "4F-42-0F-FA-53-08-00-37-00-D9";
    private final static String BoxFeedbackLocked03 = "4C-42-0F-FA-4E-08-00-38-00-D9";
    private final static String BoxFeedbackLocked04 = "4D-42-0F-FA-4E-08-00-37-00-D8";
    private final static String BoxFeedbackLocked05 = "4A-42-0F-FA-4C-08-00-36-00-D8";

    private final static String BoxFeedbackUnlocked01 = "4F-C1-0F-F0-59-00-00-36-2A-D9";
    private final static String BoxFeedbackUnlocked02 = "52-C2-0F-F0-55-00-00-35-2B-DA";
    private final static String BoxFeedbackUnlocked03 = "4F-C2-0F-F0-55-00-00-35-29-D9";
    private final static String BoxFeedbackUnlocked04 = "4F-C3-0F-F0-57-00-00-36-2A-D9";
    private final static String BoxFeedbackUnlocked05 = "4C-C3-0F-F0-57-00-00-36-28-D9";

    private final static String BoxFeedbackDrawerOpen01 = "0D-C1-0F-FF-00-19-03-00-00-B0";
    private final static String BoxFeedbackDrawerOpen02 = "0D-C3-0F-FF-00-19-03-00-00-B0";
    private final static String BoxFeedbackDrawerOpen03 = "0D-C2-0F-FF-00-19-03-00-00-AF";
    private final static String BoxFeedbackDrawerOpen04 = "0D-C3-0F-FF-00-19-03-00-00-AF";
    private final static String BoxFeedbackDrawerOpen05 = "0D-C2-0F-FF-00-19-03-00-00-B0";

    private final static String BoxV3FeedbackBattery01 = "FF-3D-34-00-32-00-EC-28-FF-00";
    private final static double BoxV3FeedbackBattery01Result = 93.65;
    private final static String BoxV3FeedbackBattery02 = "FF-BD-34-00-32-00-F0-29-FF-00";
    private final static double BoxV3FeedbackBattery02Result = 95.24;
    private final static String BoxV3FeedbackBattery03 = "FF-3D-33-00-33-00-E0-1D-FF-00";
    private final static double BoxV3FeedbackBattery03Result = 88.89;
    private final static String BoxV3FeedbackBattery04 = "FF-BC-34-00-33-00-EC-2A-FF-00";
    private final static double BoxV3FeedbackBattery04Result = 93.65;
    private final static String BoxV3FeedbackBattery05 = "FF-3C-34-00-36-00-F0-1B-FF-00";
    private final static double BoxV3FeedbackBattery05Result = 95.24;
    private final static String BoxV3FeedbackBattery06 = "FF-BC-34-00-31-00-F0-28-FF-00";
    private final static double BoxV3FeedbackBattery06Result = 95.24;
    private final static String BoxV3FeedbackBattery07 = "FF-3D-34-00-31-00-F8-1D-FF-00";
    private final static double BoxV3FeedbackBattery07Result = 98.41;
    private final static String BoxV3FeedbackBattery08 = "FF-BD-34-00-31-00-F8-28-FF-00";
    private final static double BoxV3FeedbackBattery08Result = 98.41;

    @Test
    public void createBoxFeedback_correct_Locked() {
        List<String> feedbackList = new ArrayList<>();
        feedbackList.add(BoxFeedbackLocked01);
        feedbackList.add(BoxFeedbackLocked02);
        feedbackList.add(BoxFeedbackLocked03);
        feedbackList.add(BoxFeedbackLocked04);
        feedbackList.add(BoxFeedbackLocked05);

        for (String bf : feedbackList) {

            byte[] bytes = ByteUtils.toByteArray(bf.replace("-", ""));
            BoxFeedback boxFeedback = BoxFeedback.create(bytes);
            if (BoxState.DRAWER_OPEN == boxFeedback.getBoxState()) {
                // the drawer of the box is opened
            }
            else if (BoxState.LOCKED == boxFeedback.getBoxState()) {
                // the box has been locked
            }
            else if (BoxState.UNLOCKED == boxFeedback.getBoxState()) {
                // the box has been opened
            }
            assertEquals(BoxState.LOCKED, boxFeedback.getBoxState());
        }
    }

    @Test
    public void createBoxFeedback_correct_Unlocked() {
        List<String> feedbackList = new ArrayList<>();
        feedbackList.add(BoxFeedbackUnlocked01);
        feedbackList.add(BoxFeedbackUnlocked02);
        feedbackList.add(BoxFeedbackUnlocked03);
        feedbackList.add(BoxFeedbackUnlocked04);
        feedbackList.add(BoxFeedbackUnlocked05);

        for (String bf : feedbackList) {
            byte[] bytes = ByteUtils.toByteArray(bf.replace("-", ""));
            BoxFeedback boxFeedback = BoxFeedback.create(bytes);

            assertEquals(BoxState.UNLOCKED, boxFeedback.getBoxState());
        }
    }

    @Test
    public void createBoxFeedback_correct_DrawerOpen() {
        List<String> feedbackList = new ArrayList<>();
        feedbackList.add(BoxFeedbackDrawerOpen01);
        feedbackList.add(BoxFeedbackDrawerOpen02);
        feedbackList.add(BoxFeedbackDrawerOpen03);
        feedbackList.add(BoxFeedbackDrawerOpen04);
        feedbackList.add(BoxFeedbackDrawerOpen05);

        for (String bf : feedbackList) {
            byte[] bytes = ByteUtils.toByteArray(bf.replace("-", ""));
            BoxFeedback boxFeedback = BoxFeedback.create(bytes);

            assertEquals(BoxState.DRAWER_OPEN, boxFeedback.getBoxState());
        }
    }

    @Test
    public void calculateBatteryStateOfCharge() {
        // Hint: Battery state of charge is only available for flinkey Box 3.3 box feedback
        List<Map.Entry<String, Double>> feedbackResultList = new ArrayList<>();
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery01, BoxV3FeedbackBattery01Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery02, BoxV3FeedbackBattery02Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery03, BoxV3FeedbackBattery03Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery04, BoxV3FeedbackBattery04Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery05, BoxV3FeedbackBattery05Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery06, BoxV3FeedbackBattery06Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery07, BoxV3FeedbackBattery07Result));
        feedbackResultList.add(new AbstractMap.SimpleEntry<>(BoxV3FeedbackBattery08, BoxV3FeedbackBattery08Result));

        for (Map.Entry<String, Double> pair : feedbackResultList) {
            byte[] bytes = ByteUtils.toByteArray(pair.getKey());
            double batteryStateOfCharge = BoxFeedback.create(bytes).getBatteryStateOfCharge("C3-XX-XX-XX");
            assertEquals(pair.getValue(), batteryStateOfCharge, 0.001);
        }
    }
}
