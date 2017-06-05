package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBL extends UiCAB {
    private static final UiCAG a = new UiCAG(1034, -480, 405, 91);
    private static final UiCAG b;
    private static final UiCAE c;
    private static final UiCAG d;
    private static final UiCAG e;
    private static final UiCAG f;
    private static final UiCAG g;
    private static final UiCAA[] h;

    public UiCBL() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return h;
    }

    public int c() {
        return R.layout.dashboard_compass;
    }

    static {
        b = new UiCAG(1034, -480, 87, 87, R.id.widget_compass);
        c = new UiCAE(1083, -417, 357, 26, R.id.imageview_info_bar_background);
        d = new UiCAG(1132, -413, 71, 16, R.id.widget_altitude);
        e = new UiCAG(1203, -413, 71, 16, R.id.widget_distance);
        f = new UiCAG(1274, -413, 71, 16, R.id.widget_h_speed);
        g = new UiCAG(1354, -413, 71, 16, R.id.widget_v_speed);
        h = new UiCAA[]{b, c, d, e, f, g};
    }
}
