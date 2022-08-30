package digital.witte.wittemobilelibrary.box;

import digital.witte.wittemobilelibrary.util.ByteUtils;

/**
 * This class serves the evaluation of a 10 byte box feedback.
 * Use this class to determine if the WITTE/flinkey Box is locked, unlocked or
 * if the drawer is open.
 */
public class BoxFeedback {
    /**
     * The feedback of a WITTE/flinkey Box has a fix byte count of 10 bytes.
     */
    private final static int ByteCount = 10;
    private final byte[] _boxFeedbackBytes;
    /**
     * The state of a WITTE/flinkey Box.
     */
    private @BoxState
    int _boxState = BoxState.UNDEFINED;

    /**
     * Private ctor.
     */
    private BoxFeedback(byte[] boxFeedbackBytes) {
        _boxFeedbackBytes = boxFeedbackBytes;
    }

    /**
     * Create a BoxFeedback instance.
     *
     * @param responseData The 10 bytes response data aka box feedback taken from the CommandResult
     *                     of a triggerLockAsync call.
     * @return BoxFeedback instance.
     */
    public static BoxFeedback create(byte[] responseData) {
        if (null == responseData) {
            String message = "The argument responseData must not be null.";
            throw new IllegalArgumentException(message);
        }

        if (ByteCount != responseData.length) {
            String message = "The argument responseData must have a fixed length of 10 bytes.";
            throw new IllegalArgumentException(message);
        }

        BoxFeedback boxFeedback = new BoxFeedback(responseData);

        if (ByteUtils.isBitSet(responseData[6], 0)) {
            // the drawer of the box is open (and therefore the box is unlocked)
            boxFeedback._boxState = BoxState.DRAWER_OPEN;
        }
        else if (ByteUtils.isBitSet(responseData[1], 7)) {
            // the box is unlocked
            boxFeedback._boxState = BoxState.UNLOCKED;
        }
        else {
            // the box is locked
            boxFeedback._boxState = BoxState.LOCKED;
        }

        return boxFeedback;
    }

    /**
     * Return the state (locked, unlocked or drawer open) of the WITTE/flinkey Box as determined
     * by the 10 byte response data aka box feedback.
     *
     * @return the BoxState.
     */
    public @BoxState
    int getBoxState() {
        return _boxState;
    }

    /**
     * IMPORTANT: This feature is only available for the new flinkey Box v3.3.
     * Returns the battery state of charge.
     *
     * @param boxId flinkey Box v3.3 ID.
     * @return Battery state of charge in percent.
     */
    public double getBatteryStateOfCharge(String boxId) {
        double batteryStateOfCharge;

        if (BoxIdConverter.isValidBoxId(boxId)) {
            if (!boxId.startsWith("C1")) {
                byte b = _boxFeedbackBytes[6];
                String bits = ByteUtils.toBitString(b).substring(0, 6);
                int rawValue = Integer.parseInt(bits, 2);
                batteryStateOfCharge = rawValue / 63.0 * 100.0;

                // round to 2 digits and compare with expected value
                batteryStateOfCharge = Math.round(batteryStateOfCharge * 100.) / 100.0;
            }
            else {
                throw new IllegalArgumentException("Evaluation of the battery state of charge is only supported for flinkey Boxes version 3.");
            }
        }
        else {
            throw new IllegalArgumentException(String.format("The argument boxId '%s' is not a valid flinkey Box ID.", boxId));
        }

        return batteryStateOfCharge;
    }
}
