package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBU extends UiCAB {
    private static final UiCAG a = new UiCAG(185, 125, 42, 22);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAF d;
    private static final UiCAF e;
    private static final UiCAA[] f;

    public UiCBU() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return f;
    }

    public int c() {
        return R.layout.widget_gps_signal;
    }

    static {
        b = new UiCAE(189, 129, 16, 15, R.id.imageview_gps_icon);
        c = new UiCAE(206, 133, 14, 11, R.id.imageview_gps_signal);
        d = new UiCAF(205, 129, 10, 7, R.id.textview_satellite_number, "12", "Roboto-Medium");
        e = new UiCAF(221, 137, 4, 7, R.id.textview_gps_type, "R", "Roboto-Medium");
        f = new UiCAA[]{b, c, d, e};
    }
}
