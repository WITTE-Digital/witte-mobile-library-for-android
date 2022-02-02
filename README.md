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
In order to determine if the box has been opened or closed one needs to evaluate the 10 byte box feedback that is part of the result of a triggerLockAsync method call. This library provides a parser for the 10 byte box feedback that allows to determine the state of the box.

```java
import digital.witte.wittemobilelibrary.box;

// the 10 byte box feedback as returned by triggerLockAsync(...)
byte[] bytes = ByteUtils.toByteArray("4C420FFA5008003700D8")

BoxFeedback boxFeedback = BoxFeedback.create(bytes);
if(BoxState.DRAWER_OPEN == boxFeedback.getBoxState()){
    // the drawer of the box is opened
}
else if(BoxState.LOCKED == boxFeedback.getBoxState()){
    // the box has been locked
}
else if(BoxState.UNLOCKED == boxFeedback.getBoxState()){
    // the box has been opened
}
```
