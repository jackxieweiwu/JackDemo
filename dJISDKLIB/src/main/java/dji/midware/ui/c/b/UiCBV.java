package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAD;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBV extends UiCAB {
    private static final UiCAF b;
    private static final UiCAG c;
    public static final UiCAD a;
    private static final UiCAD d;
    private static final UiCAG e;
    private static final UiCAA[] f;

    public UiCBV() {
    }

    public UiCAG a() {
        return e;
    }

    public UiCAA[] b() {
        return f;
    }

    public int c() {
        return R.layout.widget_iso_setting;
    }

    static {
        b = new UiCAF(8, 0, 16, 10, R.id.textview_iso_title, "ISO", "Roboto-Regular");
        c = new UiCAG(0, 14, 211, 46, R.id.seekbar_layout);
        a = new UiCAD(R.id.seekbar_iso);
        d = new UiCAD(R.id.button_iso_auto);
        e = new UiCAG(0, 0, 211, 60);
        f = new UiCAA[]{c, b, a, d};
    }
}
