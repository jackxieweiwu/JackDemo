package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_A extends UiCAB{
    private static final UiCAG a;
    private static final UiCAF b;
    private static final UiCAG c;
    private static final UiCAF d;
    private static final UiCAG e;
    private static final UiCAG f;
    private static final UiCAG g;
    private static final UiCAA[] h;

    public UiCBA_A() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return h;
    }

    public int c() {
        return R.layout.list_item_view;
    }

    static {
        a = new UiCAG(613, -220, 30, 30, R.id.list_item_title_icon);
        b = new UiCAF(604, -214, 85, 18, R.id.list_item_title, "BlackAndWhite", "Roboto-Regular");
        c = new UiCAG(754, -220, 30, 30, R.id.list_item_value_icon);
        d = new UiCAF(699, -214, 85, 18, R.id.list_item_value, "BlackAndWhite", "Roboto-Regular");
        e = new UiCAG(791, -210, 6, 10, R.id.list_item_arrow);
        f = new UiCAG(594, -185, 212, 1, R.id.list_item_divider);
        g = new UiCAG(594, -226, 212, 42);
        h = new UiCAA[]{a, b, c, d, e, f};
    }
}
