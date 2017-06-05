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

public class UiCBO extends UiCAB {
    private static final UiCAG a;
    private static final UiCAF b;
    private static final UiCAE c;
    private static final UiCAG d;
    private static final UiCAF e;
    private static final UiCAG f;
    private static final UiCAF g;
    private static final UiCAG h;
    private static final UiCAF i;
    private static final UiCAG j;
    private static final UiCAA[] k;

    public UiCBO() {
    }

    public UiCAG a() {
        return j;
    }

    public UiCAA[] b() {
        return k;
    }

    public int c() {
        return R.layout.widget_exposure_mode_setting;
    }

    static {
        a = new UiCAG(8, 1250, 40, 30, R.id.layout_camera_mode_p);
        b = new UiCAF(17, 1254, 21, 9, R.id.textview_camera_mode_p, "AUTO", "Roboto-Medium");
        c = new UiCAE(20, 1264, 14, 11, R.id.imageview_exposure_automode_icon);
        d = new UiCAG(48, 1250, 40, 30, R.id.layout_camera_mode_s);
        e = new UiCAF(22, 1255, 11, 20, R.id.textview_camera_mode_s, "S", "Roboto-Regular");
        f = new UiCAG(88, 1250, 40, 30, R.id.layout_camera_mode_a);
        g = new UiCAF(22, 1255, 11, 20, R.id.textview_camera_mode_a, "A", "Roboto-Regular");
        h = new UiCAG(128, 1250, 40, 30, R.id.layout_camera_mode_m);
        i = new UiCAF(22, 1255, 15, 20, R.id.textview_camera_mode_m, "M", "Roboto-Regular");
        j = new UiCAG(8, 1250, 160, 30);
        k = new UiCAA[]{a, b, c, d, e, f, g, h, i};
    }
}
