package digital.witte.wittemobilelibrary.box;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
    private byte[] largeDataArray;

    @Before
    public void setUp() {
        testData1 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05};
        testData2 = new byte[]{0x10, 0x20, 0x30, 0x40, 0x50};
        testData3 = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
        testData4 = new byte[]{};
        testData5 = new byte[]{0x55};

        largeDataArray = new byte[10000]; // Large data array for performance testing
        for (int i = 0; i < largeDataArray.length; i++) {
            largeDataArray[i] = (byte) (i % 256 - 128); // Filling with all possible byte values repeatedly
        }
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

    @Test
    public void testComputeChecksumWithNullInput() {
        assertThrows(NullPointerException.class,
                () -> BoxCommandBuilder.CRC8.computeChecksum(null, 0, 1));
    }

    @Test
    public void testComputeChecksumWithNegativeOffset() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> BoxCommandBuilder.CRC8.computeChecksum(new byte[10], -1, 5));
    }

    @Test
    public void testComputeChecksumWithExcessiveLength() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> BoxCommandBuilder.CRC8.computeChecksum(new byte[10], 0, 15));
    }

    @Test
    public void testComputeChecksumWithMaximumAndMinimumValues() {
        byte[] edgeCaseData = {Byte.MAX_VALUE, Byte.MIN_VALUE};
        byte actualChecksum = BoxCommandBuilder.CRC8.computeChecksum(edgeCaseData, 0, edgeCaseData.length);
        byte expectedChecksum = (byte) 0xAE; // The expected checksum needs to be calculated or defined based on your CRC8 algorithm specifics
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void testPerformanceWithLargeArray() {
        long startTime = System.currentTimeMillis();
        byte actualChecksum = BoxCommandBuilder.CRC8.computeChecksum(largeDataArray, 0, largeDataArray.length);
        byte expectedChecksum = (byte) 0x2E;
        long endTime = System.currentTimeMillis();
        assertTrue("Performance test failed, took too long", (endTime - startTime) < 200); // Expecting the test to complete within 200 milliseconds
        assertEquals(expectedChecksum, actualChecksum);
    }
}
