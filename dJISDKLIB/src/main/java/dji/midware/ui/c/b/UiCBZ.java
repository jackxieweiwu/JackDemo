package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBZ extends UiCAB {
    private static final UiCAG a;
    private static final UiCAF b;
    private static final UiCAG c;
    private static final UiCAG d;
    private static final UiCAA[] e;

    public UiCBZ() {
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
        a = new UiCAG(613, -220, 30, 30, R.id.list_item_title_icon);
        b = new UiCAF(651, -214, 100, 18, R.id.list_item_title, "Radiometric JPEG", "Roboto-Regular");
        c = new UiCAG(594, -185, 212, 1, R.id.list_item_divider);
        d = new UiCAG(594, -226, 212, 42);
        e = new UiCAA[]{a, b, c};
    }
}
