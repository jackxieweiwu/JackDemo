package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_F extends UiCAB {
    private static final UiCAF a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAA[] d;

    public UiCBA_F() {
    }

    public UiCAG a() {
        return c;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_preflight_status;
    }

    static {
        a = new UiCAF(19, 5, 200, 23, R.id.textview_preflight_status, "SAFE TO FLY  (GPS)", "Roboto-Regular");
        b = new UiCAG(0, 0, 238, 33, R.id.imageview_preflight_color_indicator);
        c = new UiCAG(0, 0, 238, 33);
        d = new UiCAA[]{a, b};
    }
}
