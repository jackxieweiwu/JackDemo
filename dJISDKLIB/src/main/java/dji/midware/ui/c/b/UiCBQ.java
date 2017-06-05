package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAG;
import dji.midware.ui.c.a.UiCAE;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBQ extends UiCAB {
    private static final UiCAG a = new UiCAG(0, 0, 60, 45);
    private static final UiCAE b;
    private static final UiCAA[] c;

    public UiCBQ() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return c;
    }

    public int c() {
        return R.layout.widget_exposuresetting;
    }

    static {
        b = new UiCAE(20, 11, 21, 21, R.id.image_button_foreground);
        c = new UiCAA[]{b};
    }
}
