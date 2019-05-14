package digital.witte.wittemobilelibrary.box;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import digital.witte.wittemobilelibrary.util.ByteUtils;
import static org.junit.Assert.assertEquals;

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

    @Test
    public void createBoxFeedback_correct_Locked(){

        List<String> feedbackList = new ArrayList<>();
        feedbackList.add(BoxFeedbackLocked01);
        feedbackList.add(BoxFeedbackLocked02);
        feedbackList.add(BoxFeedbackLocked03);
        feedbackList.add(BoxFeedbackLocked04);
        feedbackList.add(BoxFeedbackLocked05);

        for(String bf : feedbackList){

            byte[] bytes = ByteUtils.toByteArray(bf.replace("-", ""));
            BoxFeedback boxFeedback = BoxFeedback.create(bytes);
            if(BoxState.DRAWER_OPEN == boxFeedback.getBoxState()){
                // the drawer of the box is opened
            }
            else if(BoxState.LOCKED == boxFeedback.getBoxState()){
                // the box has been locked
            }
            else if(BoxState.UNLOCKED == boxFeedback.getBoxState()){
                // the box has been opened
            }
            assertEquals(BoxState.LOCKED, boxFeedback.getBoxState());
        }
    }

    @Test
    public void createBoxFeedback_correct_Unlocked(){

        List<String> feedbackList = new ArrayList<>();
        feedbackList.add(BoxFeedbackUnlocked01);
        feedbackList.add(BoxFeedbackUnlocked02);
        feedbackList.add(BoxFeedbackUnlocked03);
        feedbackList.add(BoxFeedbackUnlocked04);
        feedbackList.add(BoxFeedbackUnlocked05);

        for(String bf : feedbackList){

            byte[] bytes = ByteUtils.toByteArray(bf.replace("-", ""));
            BoxFeedback boxFeedback = BoxFeedback.create(bytes);

            assertEquals(BoxState.UNLOCKED, boxFeedback.getBoxState());
        }
    }

    @Test
    public void createBoxFeedback_correct_DrawerOpen(){

        List<String> feedbackList = new ArrayList<>();
        feedbackList.add(BoxFeedbackDrawerOpen01);
        feedbackList.add(BoxFeedbackDrawerOpen02);
        feedbackList.add(BoxFeedbackDrawerOpen03);
        feedbackList.add(BoxFeedbackDrawerOpen04);
        feedbackList.add(BoxFeedbackDrawerOpen05);

        for(String bf : feedbackList){

            byte[] bytes = ByteUtils.toByteArray(bf.replace("-", ""));
            BoxFeedback boxFeedback = BoxFeedback.create(bytes);

            assertEquals(BoxState.DRAWER_OPEN, boxFeedback.getBoxState());
        }
    }
}
