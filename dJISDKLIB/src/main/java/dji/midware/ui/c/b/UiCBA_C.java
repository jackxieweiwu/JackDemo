package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBA_C extends UiCAB {
    private static final UiCAF a;
    private static final UiCAF b;
    private static final UiCAF c;
    private static final UiCAG d;
    private static final UiCAA[] e;

    public UiCBA_C() {
    }

    public UiCAG a() {
        return d;
    }

    public UiCAA[] b() {
        return e;
    }

    public int c() {
        return R.layout.widget_metric;
    }

    static {
        a = new UiCAF(1137, -408, 19, 11, R.id.textview_metrics_title, "H.S:", "Roboto-Regular");
        b = new UiCAF(1158, -411, 36, 14, R.id.textview_metrics_value, "1399", "Roboto-Regular");
        c = new UiCAF(1189, -408, 19, 11, R.id.textview_metrics_unit, "M/S", "Roboto-Regular");
        d = new UiCAG(1137, -411, 71, 16);
        e = new UiCAA[]{a, b, c};
    }
}
