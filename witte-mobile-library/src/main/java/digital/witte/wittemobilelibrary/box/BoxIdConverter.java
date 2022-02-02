package digital.witte.wittemobilelibrary.box;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import digital.witte.wittemobilelibrary.util.ByteUtils;

/**
 * Converts a physical lock id to a flinkey box id and vice versa.
 */
public class BoxIdConverter {
    // Prefix byte count for physical lock IDs
    private final static int PrefixByteCount = 2;

    // Separator for Box IDs (e.g 'C1-08-F0-94')
    private final static String BoxIdSeparator = "-";

    // Regular expression used to match Box IDs (e.g 'C1-08-F0-94')
    private final static String BoxIdRegEx = "[A-Z0-9]{2}(-[A-Z0-9]{2}){3}";

    // Compiled pattern used to match Box IDs
    private final static Pattern BoxIdPattern = Pattern.compile(BoxIdRegEx);

    // Regular expression used to match physical lock IDs (e.g. 'BADBCPCU')
    private final static String PhysicalLockIdRegEx = "[a-zA-Z0-9/+]{8}={0,2}";

    // Compiled pattern used to match physical lock IDs
    private final static Pattern PhysicalLockIdPattern = Pattern.compile(PhysicalLockIdRegEx);

    /**
     * Convert a WITTE/flinkey Box ID (e.g 'C1-08-F0-94') to a physical lock ID (e.g. 'BADBCPCU')
     * that can be provided to the Tapkey Mobile SDK.
     * @param boxId WITTE/flinkey Box ID.
     * @return Physical lock ID.
     */
    public static String toPhysicalLockId(String boxId) throws IllegalArgumentException {
        if (null == boxId) {

            String message = "The argument boxId must not be null.";
            throw new IllegalArgumentException(message);
        }

        if (!BoxIdPattern.matcher(boxId).matches()) {

            String message = String.format("The argument boxId '%s' does not match the format of a WITTE/flinkey Box ID (e.g. 'C1-08-F0-94')", boxId);
            throw new IllegalArgumentException(message);
        }

        // Remove all separators from boxId
        String hexString = boxId.replace(BoxIdSeparator, "");

        // Create box ID bytes
        byte[] boxIdBytes = ByteUtils.toByteArray(hexString);

        // Create prefix containing the length information of the box ID
        ByteBuffer byteBuffer = ByteBuffer.allocate(PrefixByteCount);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort((short)boxIdBytes.length);
        byte[] prefixBytes = byteBuffer.array();

        // Concat arrays
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bos.write(prefixBytes);
            bos.write(boxIdBytes);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = bos.toByteArray();

        // Encode byte array to Base64
        String physicalLockId = Base64.encodeToString(bytes, Base64.NO_WRAP);

        return physicalLockId;
    }

    /**
     * Convert a physical lock ID to a WITTE/flinkey Box ID.
     * @param physicalLockId The physical lock ID as required by the Tapkey Mobile Lib API.
     * @return WITTE/flinkey Box ID
     */
    public static String toBoxId(String physicalLockId) throws IllegalArgumentException {
        String boxId = null;

        if (null == physicalLockId) {

            String message = "The argument physicalLockId must not be null";
            throw new IllegalArgumentException(message);
        }

        if (!PhysicalLockIdPattern.matcher(physicalLockId).matches()) {

            String message = String.format("The argument physicalLockId '%s' does not match the format of a physical lock ID (e.g. 'BADBCPCU')", physicalLockId);
            throw new IllegalArgumentException(message);
        }

        byte[] bytes = Base64.decode(physicalLockId, Base64.DEFAULT);

        if (null != bytes && PrefixByteCount < bytes.length) {

            // determine number of bytes used for the box ID (should be 4)
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, 0, PrefixByteCount);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            int idByteCount = byteBuffer.getShort();

            if ((PrefixByteCount + idByteCount) <= bytes.length) {

                // copy box ID related bytes to a new byte array
                byte[] idBytes = new byte[idByteCount];
                System.arraycopy(bytes, PrefixByteCount, idBytes, 0, idByteCount);

                List<String> segments = new ArrayList<>();
                for (byte b : idBytes) {

                    // convert byte to hexadecimal string representation
                    segments.add(String.format("%02X", b));
                }

                // concat hexadecimal string separated with a dash-sign
                boxId = TextUtils.join(BoxIdSeparator, segments);
            }
        }

        return boxId;
    }
}
