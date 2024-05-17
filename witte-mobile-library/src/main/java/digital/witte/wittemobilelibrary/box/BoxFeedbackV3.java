package digital.witte.wittemobilelibrary.box;

/**
 * Class representing the box feedback data.
 * This class includes properties for battery state of charge, charging status,
 * charger connection status, drawer state, and drawer accessibility.
 */
public class BoxFeedbackV3 {
    // Represents battery's state of charge as a percentage (0-100)
    private byte batteryStateOfCharge;

    // True if the battery is currently charging
    private boolean batteryIsCharging;

    // True if the charger is connected
    private boolean batteryChargerIsConnected;

    // True if the drawer is opened, false if drawer is closed
    private boolean drawerState;

    // True if the drawer is unlocked, false if drawer is locked
    private boolean drawerAccessibility;

    private String nfcTag1Uid = null;
    private String nfcTag2Uid = null;
    private String nfcTag3Uid = null;

    /**
     * Gets the battery's state of charge.
     *
     * @return The battery's state of charge as a percentage (0-100).
     */
    public byte getBatteryStateOfCharge() {
        return batteryStateOfCharge;
    }

    /**
     * Sets the battery's state of charge.
     *
     * @param batteryStateOfCharge The battery's state of charge as a percentage (0-100).
     */
    public void setBatteryStateOfCharge(byte batteryStateOfCharge) {
        this.batteryStateOfCharge = batteryStateOfCharge;
    }

    /**
     * Checks if the battery is currently charging.
     *
     * @return True if the battery is charging, false otherwise.
     */
    public boolean isBatteryIsCharging() {
        return batteryIsCharging;
    }

    /**
     * Sets the battery charging status.
     *
     * @param batteryIsCharging True if the battery is charging, false otherwise.
     */
    public void setBatteryIsCharging(boolean batteryIsCharging) {
        this.batteryIsCharging = batteryIsCharging;
    }

    /**
     * Checks if the charger is connected.
     *
     * @return True if the charger is connected, false otherwise.
     */
    public boolean isBatteryChargerIsConnected() {
        return batteryChargerIsConnected;
    }

    /**
     * Sets the charger connection status.
     *
     * @param batteryChargerIsConnected True if the charger is connected, false otherwise.
     */
    public void setBatteryChargerIsConnected(boolean batteryChargerIsConnected) {
        this.batteryChargerIsConnected = batteryChargerIsConnected;
    }

    /**
     * Checks the drawer state.
     *
     * @return True if the drawer is opened, false if it is closed.
     */
    public boolean isDrawerState() {
        return drawerState;
    }

    /**
     * Sets the drawer state.
     *
     * @param drawerState True if the drawer is opened, false if it is closed.
     */
    public void setDrawerState(boolean drawerState) {
        this.drawerState = drawerState;
    }

    /**
     * Checks the drawer accessibility.
     *
     * @return True if the drawer is unlocked, false if it is locked.
     */
    public boolean isDrawerAccessibility() {
        return drawerAccessibility;
    }

    /**
     * Sets the drawer accessibility.
     *
     * @param drawerAccessibility True if the drawer is unlocked, false if it is locked.
     */
    public void setDrawerAccessibility(boolean drawerAccessibility) {
        this.drawerAccessibility = drawerAccessibility;
    }

    public String getNfcTag1Uid() {
        return this.nfcTag1Uid;
    }

    public String getNfcTag2Uid() {
        return this.nfcTag2Uid;
    }

    public String getNfcTag3Uid() {
        return this.nfcTag3Uid;
    }

    public void setNfcTag1Uid(String uid) {
        this.nfcTag1Uid = uid;
    }

    public void setNfcTag2Uid(String uid) {
        this.nfcTag2Uid = uid;
    }

    public void setNfcTag3Uid(String uid) {
        this.nfcTag3Uid = uid;
    }
}
