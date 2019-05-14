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

    /**
     * The state of a WITTE/flinkey Box.
     */
    private @BoxState
    int _boxState = BoxState.UNDEFINED;

    /**
     * Private ctor.
     */
    private BoxFeedback() {
    }

    /**
     * Create a BoxFeedback instance.
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

        BoxFeedback boxFeedback = new BoxFeedback();

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
     * @return the BoxState.
     */
    public @BoxState
    int getBoxState() {

        return _boxState;
    }
}
