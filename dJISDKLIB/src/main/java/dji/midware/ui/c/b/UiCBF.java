package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAE;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBF extends UiCAB {
    private static final UiCAG a = new UiCAG(0, 0, 54, 54);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAA[] d;

    public UiCBF() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_button;
    }

    static {
        b = new UiCAE(1, 1, 52, 52, R.id.image_button_background);
        c = new UiCAE(12, 13, 30, 28, R.id.image_button_foreground);
        d = new UiCAA[]{b, c};
    }
}
