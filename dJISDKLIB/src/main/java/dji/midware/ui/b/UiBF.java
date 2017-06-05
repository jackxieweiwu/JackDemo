package dji.midware.ui.b;

import android.content.Context;
import android.util.AttributeSet;

import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_A;
import dji.midware.ui.c.b.UiCBA_B;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiBF extends BaseFrameLayout {
    private UiCBA_A a;

    public UiBF(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public UiBF(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.a == null) {
            this.a = new UiCBA_A();
        }

        return this.a;
    }
}
