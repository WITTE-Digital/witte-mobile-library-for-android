package digital.witte.wittemobilelibrary.box;

import static org.junit.Assert.assertEquals;

import android.os.Build;
import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(sdk = {Build.VERSION_CODES.P})
@RunWith(RobolectricTestRunner.class)
public class BoxIdConverterTest {
    private final Pair<String, String> BoxId2LockId = new Pair<>("C1-08-F0-94", "BADBCPCU");

    @Test
    public void toBoxId_isCorrect() {
        String boxId = BoxIdConverter.toBoxId(BoxId2LockId.second);
        assertEquals(BoxId2LockId.first, boxId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toBoxId_invalidArgument_Null() {
        BoxIdConverter.toBoxId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toBoxId_invalidArgument_Empty() {
        BoxIdConverter.toBoxId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void toBoxId_invalidArgument_Malformed() {
        BoxIdConverter.toBoxId("AB-CD-EF-FH-IJ");
    }

    @Test
    public void toPhysicalLockId_isCorrect() {
        String physicalLockId = BoxIdConverter.toPhysicalLockId(BoxId2LockId.first);
        assertEquals(BoxId2LockId.second, physicalLockId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toPhysicalLockId_invalidArgument_Null() {
        BoxIdConverter.toPhysicalLockId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toPhysicalLockId_invalidArgument_Empty() {
        BoxIdConverter.toPhysicalLockId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void toPhysicalLockId_invalidArgument_Malformed() {
        BoxIdConverter.toPhysicalLockId("BADBCPCUFOO");
    }
}
