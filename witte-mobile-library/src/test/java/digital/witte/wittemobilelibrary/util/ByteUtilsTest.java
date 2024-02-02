package digital.witte.wittemobilelibrary.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ByteUtilsTest {

    @Test
    public void testToByteArray() {
        String hex = "C17C";
        byte[] expected = {(byte) 0xC1, (byte) 0x7C};
        byte[] result = ByteUtils.toByteArray(hex);
        assertArrayEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToByteArrayWithNullHex() {
        String hex = null;
        ByteUtils.toByteArray(hex);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToByteArrayWithInvalidHexLength() {
        String hex = "C1A";
        ByteUtils.toByteArray(hex);
    }

    @Test
    public void testIsBitSetPositive() {
        byte byteValue = (byte) 127;
        assertTrue(ByteUtils.isBitSet(byteValue, 0));
        assertTrue(ByteUtils.isBitSet(byteValue, 1));
        assertTrue(ByteUtils.isBitSet(byteValue, 2));
        assertTrue(ByteUtils.isBitSet(byteValue, 3));
        assertTrue(ByteUtils.isBitSet(byteValue, 4));
        assertTrue(ByteUtils.isBitSet(byteValue, 5));
        assertTrue(ByteUtils.isBitSet(byteValue, 6));
        assertFalse(ByteUtils.isBitSet(byteValue, 7));
    }

    @Test
    public void testIsBitSetNegative() {
        byte byteValue = (byte) -128;
        assertFalse(ByteUtils.isBitSet(byteValue, 0));
        assertFalse(ByteUtils.isBitSet(byteValue, 1));
        assertFalse(ByteUtils.isBitSet(byteValue, 2));
        assertFalse(ByteUtils.isBitSet(byteValue, 3));
        assertFalse(ByteUtils.isBitSet(byteValue, 4));
        assertFalse(ByteUtils.isBitSet(byteValue, 5));
        assertFalse(ByteUtils.isBitSet(byteValue, 6));
        assertTrue(ByteUtils.isBitSet(byteValue, 7));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsBitSetWithInvalidBitIndex() {
        byte byteValue = (byte) 0b10101010;
        ByteUtils.isBitSet(byteValue, 8);
    }

    @Test
    public void testToBitString() {
        byte byteValue = (byte) 0b10101010;
        String expected = "10101010";
        String result = ByteUtils.toBitString(byteValue);
        assertEquals(expected, result);
    }
}