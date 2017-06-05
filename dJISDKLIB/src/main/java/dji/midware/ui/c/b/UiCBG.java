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

public class UiCBG extends UiCAB {
    private static final UiCAG a = new UiCAG(2018, 0, 60, 80);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAE d;
    private static final UiCAF e;
    private static final UiCAA[] f;

    public UiCBG() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return f;
    }

    public int c() {
        return R.layout.widget_capture;
    }

    static {
        b = new UiCAE(2018, 0, 60, 60, R.id.image_button_background);
        c = new UiCAE(2028, 10, 40, 40, R.id.image_button_foreground);
        d = new UiCAE(2034, 18, 28, 24, R.id.image_button_foreground_top);
        e = new UiCAF(2018, 60, 60, 18, R.id.textview_camera_controll_videotime, "00:00:00", "Roboto-Medium");
        f = new UiCAA[]{b, c, d, e};
    }
}
