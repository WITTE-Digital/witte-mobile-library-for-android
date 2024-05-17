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
        if (data == null || data.length <= (RESPONSE_HEADER_LENGTH + GROUP_HEADER_LENGTH) || data.length == LEGACY_BOX_FEEDBACK_LENGTH) {
            return null;
        }

        BoxFeedbackV3 boxFeedback = null;
        int index = RESPONSE_HEADER_LENGTH;

        while (index+1 < data.length) {
            int groupNo = data[index++];
            int groupSize = data[index++];

            if (index + groupSize > data.length) {
                return null;
            }

            switch (groupNo) {
                case 1: {
                    if (null == boxFeedback) {
                        boxFeedback = new BoxFeedbackV3();
                    }
                    parseBatteryAndDrawerState(data, index, boxFeedback);
                    break;
                }
                case 3: {
                    if (null == boxFeedback) {
                        boxFeedback = new BoxFeedbackV3();
                    }
                    parseNfcTags(data, index, groupSize, boxFeedback);
                    break;
                }

                default:
                    break;
            }

            index += groupSize;
        }

        return boxFeedback;
    }

    /**
     * Parses battery and drawer state information from the data array.
     *
     * @param data        Byte array containing the feedback data.
     * @param index       The current index in the data array.
     * @param boxFeedback The BoxFeedbackV3 object to populate.
     */
    private static void parseBatteryAndDrawerState(byte[] data, int index, BoxFeedbackV3 boxFeedback) {
        boxFeedback.setBatteryStateOfCharge((byte) (data[index] & 0x7F));
        boxFeedback.setBatteryIsCharging((data[index] & (1 << 7)) != 0);
        boxFeedback.setBatteryChargerIsConnected((data[index + 1] & (1 << 7)) != 0);
        boxFeedback.setDrawerState((data[index + 2] & (1 << 7)) != 0);
        boxFeedback.setDrawerAccessibility((data[index + 2] & (1 << 6)) != 0);
    }

    /**
     * Parses NFC tag information from the data array.
     *
     * @param data        Byte array containing the feedback data.
     * @param index       The current index in the data array.
     * @param groupSize   The size of the NFC tag group data.
     * @param boxFeedback The BoxFeedbackV3 object to populate.
     */
    private static void parseNfcTags(byte[] data, int index, int groupSize, BoxFeedbackV3 boxFeedback) {
        int nfcTagNumber = 1;
        int endIndex = index + groupSize;

        while (index < endIndex && nfcTagNumber <= 3) {
            int uidLength = data[index] & 0x0F;
            if (uidLength > 0) {
                String nfcTagUid = bytesToHex(data, index + 1, uidLength);
                switch (nfcTagNumber) {
                    case 1:
                        boxFeedback.setNfcTag1Uid(nfcTagUid);
                        break;
                    case 2:
                        boxFeedback.setNfcTag2Uid(nfcTagUid);
                        break;
                    case 3:
                        boxFeedback.setNfcTag3Uid(nfcTagUid);
                        break;
                    default:
                        break;
                }
                nfcTagNumber++;
            }
            index += uidLength + 1;
        }
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes  The byte array to convert.
     * @param offset The starting index in the byte array.
     * @param length The number of bytes to convert.
     * @return A hexadecimal string representation of the specified bytes.
     */
    private static String bytesToHex(byte[] bytes, int offset, int length) {
        StringBuilder hexString = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            hexString.append(String.format("%02X", bytes[i]));
        }
        return hexString.toString();
    }
}
