package digital.witte.wittemobilelibrary.box;

import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Base64;

public class BoxCommandBuilderTest {
    @Test
    public void testBuildUnlockCarUnlockBox() {
        byte[] expected = {
                (byte) 0x7e, // [00]
                (byte) 0x10, // [01]
                (byte) 0x01, // [02]
                (byte) 0x00, // [03]
                (byte) 0xbb, // [04]
                (byte) 0xb1, // [05]
                (byte) 0x18, // [06]
                (byte) 0x6a, // [07]
                (byte) 0x8d, // [08]
                (byte) 0x01, // [09]
                (byte) 0x00, // [10]
                (byte) 0x00, // [11]
                (byte) 0x1a, // [12]
                (byte) 0x00, // [13]
                (byte) 0x00, // [14]
                (byte) 0x00  // [15]
        };

        byte[] result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_UNLOCK_CAR_UNLOCK_BOX, 1706882019771L);
        assertArrayEquals(expected, result);

        result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_UNLOCK_CAR_UNLOCK_BOX_AND_READ_NFC);
        String boxCommandUnlockCarUnlockBoxAndReadNfc = java.util.Base64.getEncoder().encodeToString(result);

        result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_UNLOCK_CAR_LOCK_BOX_AND_READ_NFC);
        String boxCommandUnlockCarLockBoxAndReadNfc = java.util.Base64.getEncoder().encodeToString(result);

        result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_LOCK_CAR_LOCK_BOX_AND_READ_NFC);
        String boxCommandLockCarLockBoxAndReadNfc = java.util.Base64.getEncoder().encodeToString(result);

        result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_STATUS_AND_READ_NFC);
        String boxCommandStatusAndReadNfc = java.util.Base64.getEncoder().encodeToString(result);

        result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_READ_NFC);
        String boxCommandReadNfc = java.util.Base64.getEncoder().encodeToString(result);
    }

    @Test
    public void testBuildUnlockCarLockBox() {
        byte[] expected = {
                (byte) 0xc4, // [00]
                (byte) 0x10, // [01]
                (byte) 0x01, // [02]
                (byte) 0x00, // [03]
                (byte) 0xbb, // [04]
                (byte) 0xb1, // [05]
                (byte) 0x18, // [06]
                (byte) 0x6a, // [07]
                (byte) 0x8d, // [08]
                (byte) 0x01, // [09]
                (byte) 0x00, // [10]
                (byte) 0x00, // [11]
                (byte) 0x19, // [12]
                (byte) 0x00, // [13]
                (byte) 0x00, // [14]
                (byte) 0x00  // [15]
        };

        byte[] result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_UNLOCK_CAR_LOCK_BOX, 1706882019771L);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBuildLockCarLockBox() {
        byte[] expected = {
                (byte) 0x16, // [00]
                (byte) 0x10, // [01]
                (byte) 0x01, // [02]
                (byte) 0x00, // [03]
                (byte) 0xbb, // [04]
                (byte) 0xb1, // [05]
                (byte) 0x18, // [06]
                (byte) 0x6a, // [07]
                (byte) 0x8d, // [08]
                (byte) 0x01, // [09]
                (byte) 0x00, // [10]
                (byte) 0x00, // [11]
                (byte) 0x15, // [12]
                (byte) 0x00, // [13]
                (byte) 0x00, // [14]
                (byte) 0x00  // [15]
        };

        byte[] result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_LOCK_CAR_LOCK_BOX, 1706882019771L);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testBuildStatus() {
        byte[] expected = {
                (byte) 0xc5, // [00]
                (byte) 0x10, // [01]
                (byte) 0x01, // [02]
                (byte) 0x00, // [03]
                (byte) 0xbb, // [04]
                (byte) 0xb1, // [05]
                (byte) 0x18, // [06]
                (byte) 0x6a, // [07]
                (byte) 0x8d, // [08]
                (byte) 0x01, // [09]
                (byte) 0x00, // [10]
                (byte) 0x00, // [11]
                (byte) 0x10, // [12]
                (byte) 0x00, // [13]
                (byte) 0x00, // [14]
                (byte) 0x00  // [15]
        };

        byte[] result = BoxCommandBuilder.build(BoxCommandBuilder.CONFIG_STATUS, 1706882019771L);
        assertArrayEquals(expected, result);
    }
}