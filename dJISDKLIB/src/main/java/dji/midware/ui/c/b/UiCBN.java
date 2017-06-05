package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBN extends UiCAB {
    private static final UiCAF a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAF d;
    private static final UiCAG e;
    private static final UiCAF f;
    private static final UiCAG g;
    private static final UiCAG h;
    private static final UiCAA[] i;

    public UiCBN() {
    }

    public UiCAG a() {
        return h;
    }

    public UiCAA[] b() {
        return i;
    }

    public int c() {
        return R.layout.widget_ev_setting;
    }

    static {
        a = new UiCAF(8, 0, 21, 10, R.id.textview_ev_title, "EV", "Roboto-Regular");
        b = new UiCAG(1, 14, 210, 46, R.id.imageview_camera_settings_ev_background);
        c = new UiCAG(40, 25, 28, 28, R.id.imagebutton_ev_setting_minus);
        d = new UiCAF(80, 27, 50, 25, R.id.textview_setting_ev_value, "+0.7", "Roboto-Medium");
        e = new UiCAG(143, 25, 28, 28, R.id.imagebutton_ev_setting_plus);
        f = new UiCAF(95, 25, 20, 11, R.id.textview_setting_ev_status_value, "+0.7", "Roboto-Regular");
        g = new UiCAG(63, 40, 84, 10, R.id.stripeview_setting_ev_status);
        h = new UiCAG(0, 0, 211, 60);
        i = new UiCAA[]{b, a, c, d, e, f, g};
    }
}
