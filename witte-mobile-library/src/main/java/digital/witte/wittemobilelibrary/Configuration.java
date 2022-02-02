package digital.witte.wittemobilelibrary;

public class Configuration {
    /**
     * The tenant id used with the Tapkey backend.
     */
    public final static String TenantId = "wma";

    /**
     * The id token type used to authenticate with the Tapkey backend.
     */
    public final static String IpId = "wma.oauth";

    /**
     * The Bluetooth LE service UUID of a flinkey box.
     */
    public final static String BleServiceUuid = "6e65742e-7470-6ba0-0000-060601810057";

    /**
     * The Bluetooth LE advertising format for flinkey box 2.4.
     */
    public final static String BleAdvertisingFormatV1 = "6e65742e-7470-6ba0-0000-060601810057";

    /**
     * The Bluetooth LE advertising format for flinkey box 3.3.
     */
    public final static Integer BleAdvertisingFormatV2 = 0x5754;
}
