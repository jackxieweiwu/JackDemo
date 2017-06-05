package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBP extends UiCAB {
    private static final UiCAG a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAG d;
    private static final UiCAG e;
    private static final UiCAG f;
    private static final UiCAG g;
    private static final UiCAA[] h;

    public UiCBP() {
    }

    public UiCAG a() {
        return f;
    }

    public UiCAA[] b() {
        return h;
    }

    public int c() {
        return R.layout.widget_camera_exposure_setting;
    }

    static {
        a = new UiCAG(487, 1250, 159, 30, R.id.widget_camera_exposure_mode);
        b = new UiCAG(462, 1287, 211, 60, R.id.widget_camera_iso_setting);
        c = new UiCAG(462, 1356, 211, 60, R.id.widget_camera_aperture_setting);
        d = new UiCAG(462, 1426, 211, 60, R.id.widget_camera_shutter_setting);
        e = new UiCAG(462, 1495, 211, 64, R.id.widget_camera_ev_setting);
        f = new UiCAG(462, 1243, 211, 316);
        g = new UiCAG(462, 1243, 211, 316, R.id.background_exposure_setting);
        h = new UiCAA[]{a, b, c, d, e, g};
    }
}
