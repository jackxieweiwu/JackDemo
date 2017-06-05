package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBI extends UiCAB {
    private static final UiCAF a;
    private static final UiCAF b;
    private static final UiCAG c;
    private static final UiCAA[] d;

    public UiCBI() {
    }

    public UiCAG a() {
        return c;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_camera_info;
    }

    static {
        a = new UiCAF(167, 1598, 31, 9, R.id.TextView_camera_info_title, "SHUTTER", "Roboto-Regular");
        b = new UiCAF(167, 1608, 45, 13, R.id.TextView_camera_info_value, "UNKNOWN", "Roboto-Regular");
        c = new UiCAG(159, 1594, 60, 30);
        d = new UiCAA[]{a, b};
    }
}
