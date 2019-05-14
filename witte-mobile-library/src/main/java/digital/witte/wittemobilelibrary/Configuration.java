package digital.witte.wittemobilelibrary;

public class Configuration {

    private int _witteCustomerId;
    private String _witteSdkKey;
    private String _witteSubscriptionKey;

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

    public Configuration(int customerId, String sdkKey, String subscriptionKey) {

        _witteCustomerId = customerId;
        _witteSdkKey = sdkKey;
        _witteSubscriptionKey = subscriptionKey;
    }

    public int getWitteCustomerId() {
        return _witteCustomerId;
    }

    public String getWitteSdkKey() {
        return _witteSdkKey;
    }

    public String getWitteSubscriptionKey() {
        return _witteSubscriptionKey;
    }
}
