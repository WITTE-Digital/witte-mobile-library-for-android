package digital.witte.wittemobilelibrary.box;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.os.Build;

@Config(sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class CRC8Test {
    private byte[] testData1;
    private byte[] testData2;
    private byte[] testData3;
    private byte[] testData4;
    private byte[] testData5;
    private byte[] testData6;

    @Before
    public void setUp() {
        testData1 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        testData2 = new byte[]{0x10, 0x20, 0x30, 0x40, 0x50};
        testData3 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
        testData4 = new byte[]{};
        testData5 = new byte[]{0x55};
    }

    @Test
    public void testComputeChecksumForKnownByteArray() {
        byte expectedChecksum1 = (byte) 0x85; // Explained: CRC8 checksum for testData1 with polynomial 0x1D
        byte actualChecksum1 = BoxCommandBuilder.CRC8.computeChecksum(testData1, 0, testData1.length);
        assertEquals(expectedChecksum1, actualChecksum1);
    }

    @Test
    public void testComputeChecksumForDifferentByteArray() {
        byte expectedChecksum2 = (byte) 0xB8; // Explained: CRC8 checksum for testData2 with polynomial 0x1D
        byte actualChecksum2 = BoxCommandBuilder.CRC8.computeChecksum(testData2, 0, testData2.length);
        assertEquals(expectedChecksum2, actualChecksum2);
    }

    @Test
    public void testComputeChecksumForSubsetOfByteArray() {
        byte expectedChecksum3 = (byte) 0xA3; // Explained: CRC8 checksum for subset of testData1[1:4] with polynomial 0x1D
        byte actualChecksum3 = BoxCommandBuilder.CRC8.computeChecksum(testData1, 1, 3);
        assertEquals(expectedChecksum3, actualChecksum3);
    }

    @Test
    public void testComputeChecksumForEmptyArray() {
        byte expectedChecksum4 = 0x00; // Explained: CRC8 checksum for empty array should be 0
        byte actualChecksum4 = BoxCommandBuilder.CRC8.computeChecksum(testData4, 0, testData4.length);
        assertEquals(expectedChecksum4, actualChecksum4);
    }

    @Test
    public void testComputeChecksumForSingleByte() {
        byte expectedChecksum5 = (byte) 0xB7; // Explained: CRC8 checksum for single byte array testData5 with polynomial 0x1D
        byte actualChecksum5 = BoxCommandBuilder.CRC8.computeChecksum(testData5, 0, testData5.length);
        assertEquals(expectedChecksum5, actualChecksum5);
    }

    @Test
    public void testComputeChecksumWithOffsetAndLength() {
        byte expectedChecksum6 = (byte) 0xC8; // Explained: CRC8 checksum for part of testData3[2:5] with polynomial 0x1D
        byte actualChecksum6 = BoxCommandBuilder.CRC8.computeChecksum(testData3, 2, 3);
        assertEquals(expectedChecksum6, actualChecksum6);
    }
}
