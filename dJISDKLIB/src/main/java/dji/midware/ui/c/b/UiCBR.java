package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAG;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAF;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBR extends UiCAB {
    private static final UiCAG a = new UiCAG(182, 0, 103, 22);
    private static final UiCAE b;
    private static final UiCAF c;
    private static final UiCAA[] d;

    public UiCBR() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_flightmode;
    }

    static {
        b = new UiCAE(184, 2, 18, 18, R.id.imageview_flight_mode_icon);
        c = new UiCAF(207, 4, 76, 15, R.id.textview_flight_mode_string, "WIFI-Joystick", "Roboto-Medium");
        d = new UiCAA[]{b, c};
    }
}
