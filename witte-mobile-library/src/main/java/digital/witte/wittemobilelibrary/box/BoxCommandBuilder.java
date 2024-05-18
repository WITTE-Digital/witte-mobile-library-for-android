package digital.witte.wittemobilelibrary.box;

/**
 * The BoxCommandBuilder class is responsible for creating commands for the Flinkey BLE box.
 */
public class BoxCommandBuilder {

    private static final byte BIT_MASK_0 = (byte) (1 << 0);
    private static final byte BIT_MASK_1 = (byte) (1 << 1);
    private static final byte BIT_MASK_2 = (byte) (1 << 2);
    private static final byte BIT_MASK_3 = (byte) (1 << 3);
    private static final byte BIT_MASK_4 = (byte) (1 << 4);
    private static final byte BIT_MASK_5 = (byte) (1 << 5);
    private static final byte BIT_MASK_6 = (byte) (1 << 6);
    private static final byte BIT_MASK_7 = (byte) (1 << 7);
    private static final int COMMAND_LENGTH = 16;
    private static final short COMMAND_VERSION = 1;

    protected static final CommandConfig CONFIG_UNLOCK_CAR_UNLOCK_BOX = new CommandConfig(false, true, true, false, true, false);
    protected static final CommandConfig CONFIG_UNLOCK_CAR_LOCK_BOX = new CommandConfig(false, true, true, false, false, true);
    protected static final CommandConfig CONFIG_LOCK_CAR_LOCK_BOX = new CommandConfig(false, true, false, true, false, true);
    protected static final CommandConfig CONFIG_STATUS = new CommandConfig(false, true, false, false, false, false);
    protected static final CommandConfig CONFIG_UNLOCK_CAR_UNLOCK_BOX_AND_READ_NFC = new CommandConfig(false, true, true, false, true, false, true);
    protected static final CommandConfig CONFIG_UNLOCK_CAR_LOCK_BOX_AND_READ_NFC = new CommandConfig(false, true, true, false, false, true, true);
    protected static final CommandConfig CONFIG_LOCK_CAR_LOCK_BOX_AND_READ_NFC = new CommandConfig(false, true, false, true, false, true, true);
    protected static final CommandConfig CONFIG_STATUS_AND_READ_NFC = new CommandConfig(false, true, false, false, false, false, true);
    protected static final CommandConfig CONFIG_READ_NFC = new CommandConfig(false, false, false, false, false, false, true);

    /**
     * Builds a command to unlock the car and unlock the box.
     *
     * @return A byte array representing the command.
     */
    public static byte[] buildUnlockCarUnlockBox() {
        return build(CONFIG_UNLOCK_CAR_UNLOCK_BOX);
    }

    /**
     * Builds a command to unlock the car and unlock the box with an optional NFC read.
     *
     * @param readNfc If true, the command will include reading NFC.
     * @return A byte array representing the command.
     */
    public static byte[] buildUnlockCarUnlockBox(boolean readNfc) {
        return readNfc ? build(CONFIG_UNLOCK_CAR_UNLOCK_BOX_AND_READ_NFC) : build(CONFIG_UNLOCK_CAR_UNLOCK_BOX);
    }

    /**
     * Builds a command to unlock the car and lock the box.
     *
     * @return A byte array representing the command.
     */
    public static byte[] buildUnlockCarLockBox() {
        return build(CONFIG_UNLOCK_CAR_LOCK_BOX);
    }

    /**
     * Builds a command to unlock the car and lock the box with an optional NFC read.
     *
     * @param readNfc If true, the command will include reading NFC.
     * @return A byte array representing the command.
     */
    public static byte[] buildUnlockCarLockBox(boolean readNfc) {
        return readNfc ? build(CONFIG_UNLOCK_CAR_LOCK_BOX_AND_READ_NFC) : build(CONFIG_UNLOCK_CAR_LOCK_BOX);
    }

    /**
     * Builds a command to lock the car and lock the box.
     *
     * @return A byte array representing the command.
     */
    public static byte[] buildLockCarLockBox() {
        return build(CONFIG_LOCK_CAR_LOCK_BOX);
    }

    /**
     * Builds a command to lock the car and lock the box with an optional NFC read.
     *
     * @param readNfc If true, the command will include reading NFC.
     * @return A byte array representing the command.
     */
    public static byte[] buildLockCarLockBox(boolean readNfc) {
        return readNfc ? build(CONFIG_LOCK_CAR_LOCK_BOX_AND_READ_NFC) : build(CONFIG_LOCK_CAR_LOCK_BOX);
    }

    /**
     * Builds a command to get the status.
     *
     * @return A byte array representing the command.
     */
    public static byte[] buildStatus() {
        return build(CONFIG_STATUS);
    }

    /**
     * Builds a command to get the status with an optional NFC read.
     *
     * @param readNfc If true, the command will include reading NFC.
     * @return A byte array representing the command.
     */
    public static byte[] buildStatus(boolean readNfc) {
        return readNfc ? build(CONFIG_STATUS_AND_READ_NFC) : build(CONFIG_STATUS);
    }

    /**
     * Builds a command to read NFC.
     *
     * @return A byte array representing the command.
     */
    public static byte[] buildReadNfc() {
        return build(CONFIG_READ_NFC);
    }

    /**
     * Builds a command based on the provided configuration.
     * The method constructs a command byte array based on the provided configuration.
     * It sets various flags in the command byte array based on the properties of the configuration.
     * It also includes a checksum for data integrity.
     *
     * @param config The configuration for the command to be built.
     * @return A byte array representing the built command.
     */
    protected static byte[] build(CommandConfig config) {
        return build(config, System.currentTimeMillis());
    }

    /**
     * Builds a command based on the provided configuration.
     * The method constructs a command byte array based on the provided configuration.
     * It sets various flags in the command byte array based on the properties of the configuration.
     * It also includes a checksum for data integrity.
     *
     * @param config The configuration for the command to be built.
     * @param totalMillis The UNIX timestamp for the command to be built.
     * @return A byte array representing the built command.
     */
    protected static byte[] build(CommandConfig config, long totalMillis) {
        byte[] bytes = new byte[COMMAND_LENGTH];

        try {
            bytes[1] = (byte) bytes.length;

            // Set version bytes for COMMAND_VERSION (little-endian order)
            bytes[2] = (byte) (COMMAND_VERSION & 0xff);
            bytes[3] = (byte) ((COMMAND_VERSION >> 8) & 0xff);

            // Convert totalMillis to bytes (little-endian order)
            for (int i = 4; i < 12; i++) {
                bytes[i] = (byte) (totalMillis & 0xff);
                totalMillis >>= 8;
            }

            byte commandByte = 0x00;
            commandByte |= config.isLockBox() ? BIT_MASK_0 : 0;
            commandByte |= config.isUnlockBox() ? BIT_MASK_1 : 0;
            commandByte |= config.isLockCar() ? BIT_MASK_2 : 0;
            commandByte |= config.isUnlockCar() ? BIT_MASK_3 : 0;
            commandByte |= config.isStatus() ? BIT_MASK_4 : 0;
            commandByte |= config.isReadNfc() ? BIT_MASK_5 : 0;
            commandByte |= config.isFactoryReset() ? BIT_MASK_6 : 0;
            bytes[12] = commandByte;

            // Spares
            bytes[13] = 0x00;
            bytes[14] = 0x00;
            bytes[15] = 0x00;

            // Compute and set checksum for bytes 1 through 15
            bytes[0] = CRC8.computeChecksum(bytes, 1, 15);
        }
        catch (Exception exception) {
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
        private final boolean readNfc;

        /**
         * Initializes a new instance of the CommandConfig class.
         *
         * @param factoryReset Indicates whether a factory reset command should be built.
         * @param status Indicates whether a status command should be built.
         * @param unlockCar Indicates whether a command to unlock the car should be built.
         * @param lockCar Indicates whether a command to lock the car should be built.
         * @param unlockBox Indicates whether a command to unlock the box should be built.
         * @param lockBox Indicates whether a command to lock the box should be built.
         */
        public CommandConfig(boolean factoryReset, boolean status, boolean unlockCar, boolean lockCar, boolean unlockBox, boolean lockBox) {
            this(factoryReset, status, unlockCar, lockCar, unlockBox, lockBox, false);
        }

        /**
         * Initializes a new instance of the CommandConfig class with an option for reading NFC.
         *
         * @param factoryReset Indicates whether a factory reset command should be built.
         * @param status Indicates whether a status command should be built.
         * @param unlockCar Indicates whether a command to unlock the car should be built.
         * @param lockCar Indicates whether a command to lock the car should be built.
         * @param unlockBox Indicates whether a command to unlock the box should be built.
         * @param lockBox Indicates whether a command to lock the box should be built.
         * @param readNfc Indicates whether a command to read NFC should be built.
         */
        public CommandConfig(boolean factoryReset, boolean status, boolean unlockCar, boolean lockCar, boolean unlockBox, boolean lockBox, boolean readNfc) {
            this.factoryReset = factoryReset;
            this.status = status;
            this.unlockCar = unlockCar;
            this.lockCar = lockCar;
            this.unlockBox = unlockBox;
            this.lockBox = lockBox;
            this.readNfc = readNfc;
        }

        public boolean isFactoryReset() {
            return factoryReset;
        }

        public boolean isStatus() {
            return status;
        }

        public boolean isUnlockCar() {
            return unlockCar;
        }

        public boolean isLockCar() {
            return lockCar;
        }

        public boolean isUnlockBox() {
            return unlockBox;
        }

        public boolean isLockBox() {
            return lockBox;
        }

        public boolean isReadNfc() {
            return readNfc;
        }
    }

    /**
     * The CRC8 class provides a method for computing a checksum for a byte array.
     * This checksum is used for ensuring the integrity of the command bytes in the BoxCommandBuilder.
     */
    public static class CRC8 {
        private static final byte[] CRC_TABLE = new byte[256];

        // Initialize the CRC table with a specific polynomial.
        static {
            final byte POLYNOMIAL = (byte) 0x1D; // Polynomial for CRC-8
            for (int i = 0; i < 256; ++i) {
                byte crc = (byte) i;
                for (int j = 0; j < 8; ++j) {
                    crc = (byte) ((crc & 0x80) != 0 ? (crc << 1) ^ POLYNOMIAL : crc << 1);
                }
                CRC_TABLE[i] = crc;
            }
        }

        /**
         * Computes a CRC8 checksum for the given byte array.
         *
         * @param bytes The byte array for which to compute the checksum.
         * @param start The start index in the array from which to begin computation.
         * @param length The number of bytes to include in the computation.
         * @return The computed CRC8 checksum.
         */
        public static byte computeChecksum(byte[] bytes, int start, int length) {
            byte crc = 0;
            for (int i = start; i < start + length; ++i) {
                byte index = (byte) (crc ^ bytes[i]);
                crc = CRC_TABLE[index & 0xFF];
            }
            return crc;
        }
    }
}
