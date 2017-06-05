package dji.midware.ui.b;

import android.content.Context;
import android.util.AttributeSet;

import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBZ;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiBE extends BaseFrameLayout {
    private UiCBZ a;

    public UiBE(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.a == null) {
            this.a = new UiCBZ();
        }

        return a;
    }
}
