# witte-mobile-mobile-library-for-android

The WITTE Mobile Library for Android provides additional classes and methods to further assist with the integration of the Tapkey Mobile SDK  (TapkeyMobileLib) in the scope of a mobile app.

## Features
* __flinkey Box ID conversion__: Convert flinkey Box IDs to physical lock IDs format and vice versa.
* __flinkey Box feedback interpretation__: Parse and interpret the 10 byte box feedback in order to determine if the box ahs been opened or closed.

## Getting Started

### Constants
Constants used to initialize the Tapkey Mobile SDK (TapkeyMobileLib).
```java
// "6e65742e-7470-6ba0-0000-060601810057"
String bleAdvertisingFormatV1 = Configuration.BleAdvertisingFormatV1;

// 0x5754
Integer bleAdvertisingFormatV2 = Configuration.BleAdvertisingFormatV2;

// "wma.oauth"
String ipId = Configuration.IpId;

// "wma"
String tenantId = Configuration.TenantId;
```

### flinkey Box ID conversion
A flinkey box is identified by a box ID or a physical Lock ID which basically is just another representation of a box ID. The box ID is actually printed on the box an consists of 4 groups of hexadecimal numbers (e.g. C1-08-F0-94). The physical lock ID is an encoded version of the box Id(e.g. BADBCPCU) which is used in the API fo the Tapkey Mobile Library. This library provides method to convert between both representations.

#### Box ID to physical lock ID
```java
import digital.witte.wittemobilelibrary.box;

String boxId = "C1-08-F0-94";
String physicalLockId = BoxIdConverter.toPhysicalLockId(physicalLockId);
```

#### Physical lock ID to box ID
```java
import digital.witte.wittemobilelibrary.box;

String physicalLockId = "BADBCPCU";
String boxId = BoxIdConverter.toBoxId(physicalLockId);
```
### Box feedback interpretation
The box feedback encapsulates state information of a flinkey box, including its battery and drawer status, as well as NFC tag IDs. 

`BoxFeedbackV3Parser` is provided to parse a byte array containing feedback data and populate a `BoxFeedbackV3` object with this information.

```java
import digital.witte.wittemobilelibrary.box;

byte[] boxFeedbackData = ...

// Parse the feedback data
BoxFeedbackV3 boxFeedback = BoxFeedbackV3Parser.parse(boxFeedbackData);

if (null != feedback) {
    System.out.println("Battery state of charge: " + boxFeedback.getBatteryStateOfCharge() + "%");
    System.out.println("Battery is charging: " + boxFeedback.isBatteryIsCharging());
    System.out.println("Battery charger is connected: " + boxFeedback.isBatteryChargerIsConnected());
    System.out.println("Drawer state: " + (boxFeedback.isDrawerState() ? "Open" : "Closed"));
    System.out.println("Drawer accessibility: " + (boxFeedback.isDrawerAccessibility() ? "Unlocked" : "Locked"));
    System.out.println("NFC Tag 1 UID: " + boxFeedback.getNfcTag1Uid());
    System.out.println("NFC Tag 2 UID: " + boxFeedback.getNfcTag2Uid());
    System.out.println("NFC Tag 3 UID: " + boxFeedback.getNfcTag3Uid());
} 
else {
    System.out.println("Failed to parse feedback data.");
}
```

---

⚠️ **Important Note:** 

If the byte array representing the box feedback data has a length of exactly 10, it indicates that you are dealing with an older version of the flinkey box. For this older version, you should use the `BoxFeedback` class to process the data.

The current version, known as the flinkey BLE box, generates feedback data in byte arrays of lengths other than 10. Therefore ensure that the feedback data byte array does not have a length of 10 when using the `BoxFeedbackV3` class.

---