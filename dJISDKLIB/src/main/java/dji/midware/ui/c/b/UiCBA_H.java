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

public class UiCBA_H extends UiCAB {
    private static final UiCAG a = new UiCAG(0, 0, 210, 46);
    private static final UiCAF b;
    private static final UiCAE c;
    private static final UiCAE d;
    private static final UiCAE e;
    private static final UiCAA[] f;

    public UiCBA_H() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return f;
    }

    public int c() {
        return R.layout.seek_bar;
    }

    static {
        b = new UiCAF(12, 4, 44, 19, R.id.textview_value, "3200000", "Roboto-Medium");
        c = new UiCAE(24, 24, 16, 16, R.id.imageview_thumb);
        d = new UiCAE(27, 27, 10, 10, R.id.imageview_thumb_disable);
        e = new UiCAE(24, 31, 162, 4, R.id.imageview_track);
        f = new UiCAA[]{c, d, e, b};
    }
}
