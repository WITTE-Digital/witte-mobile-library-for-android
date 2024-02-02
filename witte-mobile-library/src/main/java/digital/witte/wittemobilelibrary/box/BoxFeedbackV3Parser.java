package digital.witte.wittemobilelibrary.box;

/**
 * Class to parse the feedback data and populate the BoxFeedbackV3 object.
 * It processes a byte array containing feedback data to populate
 * a BoxFeedbackV3 object with battery and drawer state information.
 */
public class BoxFeedbackV3Parser {
    private static final int LEGACY_BOX_FEEDBACK_LENGTH = 10;
    private static final int RESPONSE_HEADER_LENGTH = 2;
    private static final int GROUP_HEADER_LENGTH = 2;

    /**
     * Parses the feedback data from a byte array and populates a BoxFeedbackV3 object.
     * This method checks the input data for the correct structure and length
     * before parsing the battery and drawer state information.
     *
     * @param data Byte array containing the feedback data.
     * @return BoxFeedbackV3 object populated with the parsed data if parsing is successful,
     * null otherwise.
     */
    public static BoxFeedbackV3 parse(byte[] data) {
        // Check for null input or incorrect data length
        if (data == null || data.length <= (RESPONSE_HEADER_LENGTH + GROUP_HEADER_LENGTH) || data.length == LEGACY_BOX_FEEDBACK_LENGTH) {
            return null;
        }

        BoxFeedbackV3 boxFeedback = new BoxFeedbackV3();

        // Parsing starts after the response header
        for (int i = (RESPONSE_HEADER_LENGTH + 1); i < data.length; i++) {
            int groupNo = data[i - 1];
            int groupSize = data[i];

            // Ensure not reading beyond buffer's end
            if (i + groupSize >= data.length) {
                return null;
            }

            // Parse specific group data
            if (groupNo == 1) {
                boxFeedback.setBatteryStateOfCharge((byte) (data[i + 1] & 0x7F));
                boxFeedback.setBatteryIsCharging((data[i + 1] & (1 << 7)) != 0);
                boxFeedback.setBatteryChargerIsConnected((data[i + 2] & (1 << 7)) != 0);
                boxFeedback.setDrawerState((data[i + 3] & (1 << 7)) != 0);
                boxFeedback.setDrawerAccessibility((data[i + 3] & (1 << 6)) != 0);
                return boxFeedback; // Successful parsing of Group 1
            }
            else {
                i += (1 + groupSize); // Skip unrelated groups
            }
        }
        return null; // Return null if parsing is unsuccessful
    }
}
