package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA extends UiCAB {
    private static final UiCAG a = new UiCAG(85, 1506, 35, 35);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAF d;
    private static final UiCAA[] e;

    public UiCBA() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return e;
    }

    public int c() {
        return R.layout.widget_aelock;
    }

    static {
        b = new UiCAE(85, 1506, 35, 35, R.id.image_button_background);
        c = new UiCAE(89, 1518, 8, 11, R.id.imageview_lock);
        d = new UiCAF(100, 1517, 16, 14, R.id.textview_lock_title, "AE", "Roboto-Medium");
        e = new UiCAA[]{b, c, d};
    }
}
