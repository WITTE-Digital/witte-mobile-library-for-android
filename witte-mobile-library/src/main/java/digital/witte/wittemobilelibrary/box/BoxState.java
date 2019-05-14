package digital.witte.wittemobilelibrary.box;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({BoxState.UNDEFINED, BoxState.LOCKED, BoxState.UNLOCKED, BoxState.DRAWER_OPEN})
public @interface BoxState {
    /**
     * The state of the flinkey box is unknown.
     */
    int UNDEFINED = 0;
    /**
     * The flinkey box is locked.
     */
    int LOCKED = 1;
    /**
     * The flinkey box is unlocked.
     */
    int UNLOCKED = 2;
    /**
     * The drawer of the flinkey box is open.
     */
    int DRAWER_OPEN = 3;
}
