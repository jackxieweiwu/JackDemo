package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_D extends UiCAB {
    private static final UiCAG a = new UiCAG(2025, -230, 44, 36);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAE d;
    private static final UiCAA[] e;

    public UiCBA_D() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return e;
    }

    public int c() {
        return R.layout.widget_capture_switch;
    }

    static {
        b = new UiCAE(2027, -215, 40, 20, R.id.switch_button);
        c = new UiCAE(2025, -229, 10, 8, R.id.imageview_picture_icon);
        d = new UiCAE(2057, -229, 11, 7, R.id.imageview_video_icon);
        e = new UiCAA[]{b, c, d};
    }
}
