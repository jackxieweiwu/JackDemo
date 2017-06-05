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

public class UiCBB extends UiCAB {
    private static final UiCAG a = new UiCAG(85, 1664, 35, 35);
    private static final UiCAE b;
    private static final UiCAF c;
    private static final UiCAF d;
    private static final UiCAF e;
    private static final UiCAA[] f;

    public UiCBB() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return f;
    }

    public int c() {
        return R.layout.widget_af_mf_switch;
    }

    static {
        b = new UiCAE(85, 1664, 35, 35, R.id.image_button_background);
        c = new UiCAF(87, 1675, 12, 11, R.id.textview_camera_control_af, "MF", "Roboto-Medium");
        d = new UiCAF(106, 1675, 12, 11, R.id.textview_camera_control_mf, "MF", "Roboto-Medium");
        e = new UiCAF(99, 1675, 7, 11, R.id.textview_camera_control_seperator, "/", "Roboto-Medium");
        f = new UiCAA[]{b, c, e, d};
    }
}
