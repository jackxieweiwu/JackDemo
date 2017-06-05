package dji.midware.ui.b;

import android.content.Context;
import android.util.AttributeSet;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBW;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiBB extends BaseFrameLayout {
    private UiCBW a;

    public UiBB(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public UiBB(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.a == null) {
            this.a = new UiCBW();
        }

        return this.a;
    }

    protected void onMeasure(int var1, int var2) {
        super.onMeasure(var1, 0);
    }
}
