package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_B extends UiCAB {
    private static final UiCAF a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAG d;
    private static final UiCAA[] e;

    public UiCBA_B() {
    }

    public UiCAG a() {
        return d;
    }

    public UiCAA[] b() {
        return e;
    }

    public int c() {
        return R.layout.list_item_view;
    }

    static {
        a = new UiCAF(604, -214, 85, 18, R.id.list_item_title, "BlackAndWhite", "Roboto-Regular");
        b = new UiCAG(752, -216, 41, 21, R.id.list_item_value_switch_button);
        c = new UiCAG(594, -185, 212, 1, R.id.list_item_divider);
        d = new UiCAG(594, -226, 212, 42);
        e = new UiCAA[]{a, b, c};
    }
}
