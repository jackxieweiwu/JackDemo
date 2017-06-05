package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAG;
import dji.midware.ui.c.a.UiCAE;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBS extends UiCAB {
    private static final UiCAG a = new UiCAG(85, 1348, 35, 35);
    private static final UiCAE b;
    private static final UiCAE c;
    private static final UiCAA[] d;

    public UiCBS() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.widget_focus_exposure_switch;
    }

    static {
        b = new UiCAE(85, 1348, 35, 35, R.id.image_button_background);
        c = new UiCAE(93, 1356, 19, 19, R.id.image_button_foreground);
        d = new UiCAA[]{c, b};
    }
}
