package dji.midware.ui.c.b;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.c.a.UiCAG;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiCBX extends UiCAB {
    private static final UiCAF a;
    private static final UiCAG b;
    private static final UiCAG c;
    private static final UiCAA[] d;

    public UiCBX() {
    }

    public UiCAG a() {
        return c;
    }

    public UiCAA[] b() {
        return d;
    }

    public int c() {
        return R.layout.expandable_view_child_seekbar;
    }

    static {
        a = new UiCAF(1613, 222, 66, 16, R.id.expandable_child_sb_tv, "16000k", "Roboto-Regular");
        b = new UiCAG(1556, 238, 180, 26, R.id.expandable_child_sb);
        c = new UiCAG(1540, 222, 212, 42);
        d = new UiCAA[]{a, b};
    }
}
