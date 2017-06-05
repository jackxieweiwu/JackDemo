package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_I extends UiCAB {
    private static final UiCAF a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAG d;
    private static final UiCAG e;
    private static final UiCAA[] f;

    public UiCBA_I() {
    }

    public UiCAG a() {
        return e;
    }

    public UiCAA[] b() {
        return f;
    }

    public int c() {
        return R.layout.widget_shutter_setting;
    }

    static {
        a = new UiCAF(8, 0, 39, 10, R.id.textview_shutter_title, "Shutter", "Roboto-Regular");
        b = new UiCAG(1, 14, 210, 46, R.id.imageview_camera_settings_shutter_background);
        c = new UiCAG(1, 0, 210, 60, R.id.wheelview_camera_settings_shutter);
        d = new UiCAG(100, 50, 10, 7, R.id.imageview_shutter_wheel_position);
        e = new UiCAG(0, 0, 211, 60);
        f = new UiCAA[]{a, b, c, d};
    }
}
