package digital.witte.wittemobilelibrary.box;

/**
 * Class representing the box feedback data.
 * This class includes properties for battery state of charge, charging status,
 * charger connection status, drawer state, and drawer accessibility.
 */
public class BoxFeedbackV3 {
    private byte batteryStateOfCharge;

    private boolean batteryIsCharging;

    private boolean batteryChargerIsConnected;

    private boolean drawerState;

    private boolean drawerAccessibility;

    private String nfcTag1Uid;

    private String nfcTag2Uid;

    private String nfcTag3Uid;

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

    /**
     * Gets the UID of the first NFC tag.
     *
     * @return The UID of the first NFC tag.
     */
    public String getNfcTag1Uid() {
        return nfcTag1Uid;
    }

    /**
     * Sets the UID of the first NFC tag.
     *
     * @param nfcTag1Uid The UID of the first NFC tag.
     */
    public void setNfcTag1Uid(String nfcTag1Uid) {
        this.nfcTag1Uid = nfcTag1Uid;
    }

    /**
     * Gets the UID of the second NFC tag.
     *
     * @return The UID of the second NFC tag.
     */
    public String getNfcTag2Uid() {
        return nfcTag2Uid;
    }

    /**
     * Sets the UID of the second NFC tag.
     *
     * @param nfcTag2Uid The UID of the second NFC tag.
     */
    public void setNfcTag2Uid(String nfcTag2Uid) {
        this.nfcTag2Uid = nfcTag2Uid;
    }

    /**
     * Gets the UID of the third NFC tag.
     *
     * @return The UID of the third NFC tag.
     */
    public String getNfcTag3Uid() {
        return nfcTag3Uid;
    }

    /**
     * Sets the UID of the third NFC tag.
     *
     * @param nfcTag3Uid The UID of the third NFC tag.
     */
    public void setNfcTag3Uid(String nfcTag3Uid) {
        this.nfcTag3Uid = nfcTag3Uid;
    }
}
