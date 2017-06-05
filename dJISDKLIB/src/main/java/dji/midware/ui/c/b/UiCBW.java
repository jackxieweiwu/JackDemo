package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
import dji.midware.ui.c.a.UiCAA;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBW extends UiCAB {
    private static final UiCAF a;
    private static final UiCAF b;
    private static final UiCAF c;
    private static final UiCAG d;
    private static final UiCAA[] e;

    public UiCBW() {
    }

    public UiCAG a() {
        return d;
    }

    public UiCAA[] b() {
        return e;
    }

    public int c() {
        return R.layout.expandable_view_child;
    }

    static {
        a = new UiCAF(1553, 223, 44, 40, R.id.child_value1, "130fps", "Roboto-Regular");
        b = new UiCAF(1624, 223, 44, 40, R.id.child_value2, "130fps", "Roboto-Regular");
        c = new UiCAF(1696, 223, 44, 40, R.id.child_value3, "130fps", "Roboto-Regular");
        d = new UiCAG(1540, 222, 212, 42);
        e = new UiCAA[]{a, b, c};
    }
}
