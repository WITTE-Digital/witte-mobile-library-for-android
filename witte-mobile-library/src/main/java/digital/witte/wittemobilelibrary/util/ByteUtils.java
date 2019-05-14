package digital.witte.wittemobilelibrary.util;

public class ByteUtils {

    /**
     * Convert a string of hexadecimal (e.g. 'C17C') values to a byte array.
     * @param hex hexadecimal string
     * @return byte array
     */
    public static byte[] toByteArray(String hex) {

        if (null == hex) {

            String message = "";
            throw new IllegalArgumentException(message);
        }

        if (0 != hex.length() % 2) {
            String message = "";
            throw new IllegalArgumentException(message);
        }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {

            int offset = i * 2;
            short shortValue = Short.parseShort(hex.substring(offset, (offset + 2)), 16);
            bytes[i] = (byte) (shortValue);
        }

        return bytes;
    }

    /**
     * Check if a bit on an index within a byte is set.
     * @param byteValue
     * @param bitIndex index within range 0 to 7
     * @return true if the bit is set
     */
    public static boolean isBitSet(byte byteValue, int bitIndex) {

        if (bitIndex < 0 || 7 < bitIndex) {

            String message = "";
            throw new IllegalArgumentException(message);
        }

        return 0 != (byteValue & (1 << bitIndex));
    }
}
