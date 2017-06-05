package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_L extends UiCAB {
    private static final UiCAG a = new UiCAG(202, 850, 38, 22);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAA[] d;

    public UiCBA_L() {
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
        b = new UiCAE(205, 858, 16, 11, R.id.imageview_rc_video_icon);
        c = new UiCAE(223, 858, 14, 11, R.id.imageview_rc_video_signal);
        d = new UiCAA[]{b, c};
    }
}
