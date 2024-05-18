package digital.witte.wittemobilelibrary.box;

import static org.junit.Assert.assertEquals;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class CRC8Test {
    @Test
    public void testComputeChecksum() {
        // Test case 1: Test with a known byte array and its expected checksum
        byte[] data1 = {0x01, 0x02, 0x03, 0x04, 0x05};
        byte expectedChecksum1 = (byte) 0x85; // Correct expected checksum for data1 with polynomial 0x1D
        byte actualChecksum1 = BoxCommandBuilder.CRC8.computeChecksum(data1, 0, data1.length);
        assertEquals(expectedChecksum1, actualChecksum1);

        // Test case 2: Test with a different known byte array and its expected checksum
        byte[] data2 = {0x10, 0x20, 0x30, 0x40, 0x50};
        byte expectedChecksum2 = (byte) 0xB8; // Correct expected checksum for data2 with polynomial 0x1D
        byte actualChecksum2 = BoxCommandBuilder.CRC8.computeChecksum(data2, 0, data2.length);
        assertEquals(expectedChecksum2, actualChecksum2);

        // Test case 3: Test with a subset of a known byte array and its expected checksum
        byte expectedChecksum3 = (byte) 0xA3; // Correct expected checksum for data1[1:4] with polynomial 0x1D
        byte actualChecksum3 = BoxCommandBuilder.CRC8.computeChecksum(data1, 1, 3);
        assertEquals(expectedChecksum3, actualChecksum3);

        // Test case 4: Test with an empty byte array
        byte[] data4 = {};
        byte expectedChecksum4 = 0x00; // Checksum for empty array should be 0
        byte actualChecksum4 = BoxCommandBuilder.CRC8.computeChecksum(data4, 0, data4.length);
        assertEquals(expectedChecksum4, actualChecksum4);

        // Test case 5: Test with a single byte array
        byte[] data5 = {0x55};
        byte expectedChecksum5 = (byte) 0xB7; // Correct expected checksum for data5 with polynomial 0x1D
        byte actualChecksum5 = BoxCommandBuilder.CRC8.computeChecksum(data5, 0, data5.length);
        assertEquals(expectedChecksum5, actualChecksum5);
    }

    @Test
    public void testComputeChecksumWithOffsetAndLength() {
        // Test case 6: Test with a byte array and specified offset and length
        byte[] data6 = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
        byte expectedChecksum6 = (byte) 0xC8; // Correct expected checksum for data6[2:5] with polynomial 0x1D
        byte actualChecksum6 = BoxCommandBuilder.CRC8.computeChecksum(data6, 2, 3);
        assertEquals(expectedChecksum6, actualChecksum6);
    }
}
