package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_G extends UiCAB {
    private static final UiCAG a = new UiCAG(200, 666, 38, 22);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAA[] d;

    public UiCBA_G() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_rc_video_signal;
    }

    static {
        b = new UiCAE(205, 670, 13, 15, R.id.imageview_rc_video_icon);
        c = new UiCAE(221, 674, 14, 11, R.id.imageview_rc_video_signal);
        d = new UiCAA[]{b, c};
    }
}
