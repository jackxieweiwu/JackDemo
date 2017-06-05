package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBC extends UiCAB {
    private static final UiCAG a = new UiCAG(597, -304, 212, 316);
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAF d;
    private static final UiCAG e;
    private static final UiCAG f;
    private static final UiCAG g;
    private static final UiCAG h;
    private static final UiCAG i;
    private static final UiCAG j;
    private static final UiCAA[] k;

    public UiCBC() {
    }

    public UiCAG a() {
        return a;
    }

    public UiCAA[] b() {
        return k;
    }

    public int c() {
        return R.layout.widget_camera_advanced_setting;
    }

    static {
        b = new UiCAG(597, -304, 212, 35, R.id.camera_setting_title_bar);
        c = new UiCAG(598, -303, 60, 34, R.id.imageview_back);
        d = new UiCAF(662, -296, 85, 20, R.id.textview_title, "File Index Mode", "Roboto-Regular");
        e = new UiCAG(597, -304, 212, 35, R.id.camera_advsetting_tab);
        f = new UiCAG(598, -304, 70, 35, R.id.camera_tab_photo);
        g = new UiCAG(668, -304, 70, 35, R.id.camera_tab_video);
        h = new UiCAG(738, -304, 70, 35, R.id.camera_tab_other);
        i = new UiCAG(598, -271, 70, 2, R.id.camera_tab_indicator);
        j = new UiCAG(597, -269, 212, 281, R.id.camera_setting_content);
        k = new UiCAA[]{b, c, d, e, f, g, h, i, j};
    }
}
