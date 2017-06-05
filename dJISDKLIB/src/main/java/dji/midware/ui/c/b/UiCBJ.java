package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBJ extends UiCAB {
    private static final UiCAF a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAA[] d;

    public UiCBJ() {
    }

    public UiCAG a() {
        return c;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.list_item_view;
    }

    static {
        a = new UiCAF(604, -214, 137, 18, R.id.list_item_title, "Reset Camera Settings", "Roboto-Regular");
        b = new UiCAG(594, -185, 212, 1, R.id.list_item_divider);
        c = new UiCAG(594, -226, 212, 42);
        d = new UiCAA[]{a, b};
    }
}
