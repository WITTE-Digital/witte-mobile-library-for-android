package digital.witte.wittemobilelibrary.box;

/**
 * The BoxCommandBuilder class is responsible for creating commands for the flinkey BLE box.
 */
public class BoxCommandBuilder {

    private static final byte BitMask0 = (byte) (1 << 0);
    private static final byte BitMask1 = (byte) (1 << 1);
    private static final byte BitMask2 = (byte) (1 << 2);
    private static final byte BitMask3 = (byte) (1 << 3);
    private static final byte BitMask4 = (byte) (1 << 4);
    private static final byte BitMask5 = (byte) (1 << 5);
    private static final byte BitMask6 = (byte) (1 << 6);
    private static final byte BitMask7 = (byte) (1 << 7);
    private static final int CommandLength = 16;
    private static final short CommandVersion = 1;

    protected final static CommandConfig configUnlockCarUnlockBox = new CommandConfig(false, true, true, false, true, false);
    protected final static CommandConfig configUnlockCarLockBox = new CommandConfig(false, true, true, false, false, true);
    protected final static CommandConfig configLockCarLockBox = new CommandConfig(false, true, false, true, false, true);
    protected final static CommandConfig configStatus = new CommandConfig(false, true, false, false, false, false);

    /**
     * Builds a command to unlock the car and unlock the box.
     * @return A byte array representing the command.
     */
    public static byte[] buildUnlockCarUnlockBox() {
        return build(configUnlockCarUnlockBox);
    }

    /**
     * Builds a command to unlock the car and lock the box.
     * @return A byte array representing the command.
     */
    public static byte[] buildUnlockCarLockBox() {
        return build(configUnlockCarLockBox);
    }

    /**
     * Builds a command to lock the car and lock the box.
     * @return A byte array representing the command.
     */
    public static byte[] buildLockCarLockBox() {
        return build(configLockCarLockBox);
    }

    /**
     * Builds a command to get the status.
     * @return A byte array representing the command.
     */
    public static byte[] buildStatus() {
        return build(configStatus);
    }

    /**
     * Builds a command based on the provided configuration.
     * The method constructs a command byte array based on the provided configuration.
     * It sets various flags in the command byte array based on the properties of the configuration.
     * It also includes a checksum for data integrity.
     * @param config The configuration for the command to be built.
     * @return A byte array representing the built command.
     */
    protected static byte[] build(CommandConfig config) {
        long totalMillis = System.currentTimeMillis(); // Use System.currentTimeMillis() for API level 21
        return build(config, totalMillis);
    }

    /**
     * Builds a command based on the provided configuration.
     * The method constructs a command byte array based on the provided configuration.
     * It sets various flags in the command byte array based on the properties of the configuration.
     * It also includes a checksum for data integrity.
     * @param config The configuration for the command to be built.
     * @param totalMillis The UNIX timestamp for the command to be built.
     * @return A byte array representing the built command.
     */
    protected static byte[] build(CommandConfig config, long totalMillis) {
        byte[] bytes = new byte[CommandLength];

        try {
            bytes[1] = (byte) bytes.length;

            // Set version bytes for CommandVersion (little-endian order)
            bytes[2] = (byte) (CommandVersion & 0xff);
            bytes[3] = (byte) ((CommandVersion >> 8) & 0xff);

            // Convert totalMillis to bytes ( (little-endian order))
            for (int i = 4; i < 12; i++) {
                bytes[i] = (byte) (totalMillis & 0xff);
                totalMillis >>= 8;
            }

            byte commandByte = 0x00;
            if (config.isLockBox()) {
                commandByte |= BitMask0;
            }
            if (config.isUnlockBox()) {
                commandByte |= BitMask1;
            }
            if (config.isLockCar()) {
                commandByte |= BitMask2;
            }
            if (config.isUnlockCar()) {
                commandByte |= BitMask3;
            }
            if (config.isStatus()) {
                commandByte |= BitMask4;
            }
            if (config.isFactoryReset()) {
                commandByte |= BitMask6;
            }
            bytes[12] = commandByte;

            // Spares
            bytes[13] = 0x00;
            bytes[14] = 0x00;
            bytes[15] = 0x00;

            // Compute and set checksum for bytes 1 through 15
            bytes[0] = CRC8.computeChecksum(bytes, 1, 15);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return bytes;
    }

    /**
     * The CommandConfig class is used to configure the commands that are built by the BoxCommandBuilder.
     */
    private static class CommandConfig {
        private final boolean factoryReset;
        private final boolean status;
        private final boolean unlockCar;
        private final boolean lockCar;
        private final boolean unlockBox;
        private final boolean lockBox;

        /**
         * Initializes a new instance of the CommandConfig class.
         * @param factoryReset Indicates whether a factory reset command should be built.
         * @param status Indicates whether a status command should be built.
         * @param unlockCar Indicates whether a command to unlock the car should be built.
         * @param lockCar Indicates whether a command to lock the car should be built.
         * @param unlockBox Indicates whether a command to unlock the box should be built.
         * @param lockBox Indicates whether a command to lock the box should be built.
         */
        public CommandConfig(boolean factoryReset, boolean status, boolean unlockCar, boolean lockCar, boolean unlockBox, boolean lockBox) {
            this.factoryReset = factoryReset;
            this.status = status;
            this.unlockCar = unlockCar;
            this.lockCar = lockCar;
            this.unlockBox = unlockBox;
            this.lockBox = lockBox;
        }

        public boolean isFactoryReset() { return factoryReset; }
        public boolean isStatus() { return status; }
        public boolean isUnlockCar() { return unlockCar; }
        public boolean isLockCar() { return lockCar; }
        public boolean isUnlockBox() { return unlockBox; }
        public boolean isLockBox() { return lockBox; }
    }

    /**
     * The CRC8 class provides a method for computing a checksum for a byte array.
     * This checksum is used for ensuring the integrity of the command bytes in the BoxCommandBuilder.
     */
    public static class CRC8 {
        private static final byte[] CrcTable = new byte[256];

        // Initialize the CRC table with a specific polynomial.
        static {
            final byte polynomial = (byte) 0x1D; // Polynomial for CRC-8
            for (int i = 0; i < 256; ++i) {
                byte crc = (byte) i;
                for (int j = 0; j < 8; ++j) {
                    if ((crc & 0x80) != 0) {
                        crc = (byte) ((crc << 1) ^ polynomial);
                    } else {
                        crc <<= 1;
                    }
                }
                CrcTable[i] = crc;
            }
        }

        /**
         * Computes a CRC8 checksum for the given byte array.
         * @param bytes The byte array for which to compute the checksum.
         * @param start The start index in the array from which to begin computation.
         * @param length The number of bytes to include in the computation.
         * @return The computed CRC8 checksum.
         */
        public static byte computeChecksum(byte[] bytes, int start, int length) {
            byte crc = 0;
            for (int i = start; i < start + length; ++i) {
                byte index = (byte) (crc ^ bytes[i]);
                crc = CrcTable[index & 0xFF];
            }
            return crc;
        }
    }
}
