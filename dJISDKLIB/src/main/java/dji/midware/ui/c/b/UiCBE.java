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

public class UiCBE extends UiCAB {
    private static final UiCAG a = new UiCAG(201, 1043, 56, 22);
    private static final UiCAF b;
    private static final UiCAE c;
    private static final UiCAA[] d;

    public UiCBE() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_battery;
    }

    static {
        b = new UiCAF(221, 1046, 34, 16, R.id.textview_battery_value, "100%", "Roboto-Medium");
        c = new UiCAE(203, 1045, 18, 18, R.id.imageview_battery_icon);
        d = new UiCAA[]{c, b};
    }
}
