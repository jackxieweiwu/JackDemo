package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBH extends UiCAB {
    private static final UiCAG a;
    private static final UiCAF b;
    private static final UiCAG c;
    private static final UiCAG d;
    private static final UiCAG e;
    private static final UiCAG f;
    private static final UiCAG g;
    private static final UiCAG h;
    private static final UiCAG i;
    private static final UiCAA[] j;

    public UiCBH() {
    }

    public UiCAG a() {
        return g;
    }

    public UiCAA[] b() {
        return j;
    }

    public int c() {
        return R.layout.widget_camera_action;
    }

    static {
        a = new UiCAG(520, 430, 60, 45, R.id.widget_camera_menu_background);
        b = new UiCAF(534, 446, 32, 14, R.id.widget_camera_menu, "MENU", "Roboto-Regular");
        c = new UiCAG(530, 491, 44, 36, R.id.widget_camera_capture_switch);
        d = new UiCAG(520, 548, 60, 80, R.id.widget_camera_capture);
        e = new UiCAG(520, 641, 60, 45, R.id.widget_camera_exposure_status);
        f = new UiCAG(520, 641, 60, 45, R.id.widget_camera_exposure_status_background);
        g = new UiCAG(520, 430, 60, 256);
        h = new UiCAG(520, 474, 60, 1, R.id.top_line_camera_panel);
        i = new UiCAG(520, 641, 60, 1, R.id.bot_line_camera_panel);
        j = new UiCAA[]{a, b, c, d, e, f, h, i};
    }
}
