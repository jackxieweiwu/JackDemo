package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_K extends UiCAB {
    private static final UiCAG a = new UiCAG(0, 0, 40, 20);
    private static final UiCAE b;
    private static final UiCAG c;
    private static final UiCAA[] d;

    public UiCBA_K() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.switch_button;
    }

    static {
        b = new UiCAE(0, 0, 20, 20, R.id.imageview_left);
        c = new UiCAG(0, 0, 40, 20, R.id.layout_track);
        d = new UiCAA[]{b, c};
    }
}
