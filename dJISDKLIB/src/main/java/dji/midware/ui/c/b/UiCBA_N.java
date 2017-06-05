package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_N extends UiCAB {
    private static final UiCAG a = new UiCAG(205, 308, 22, 20);
    private static final UiCAE b;
    private static final UiCAA[] c;

    public UiCBA_N() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return c;
    }

    public int c() {
        return R.layout.widget_signal;
    }

    static {
        b = new UiCAE(208, 314, 16, 11, R.id.imageview_signal_icon);
        c = new UiCAA[]{b};
    }
}
