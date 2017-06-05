package dji.midware.ui.b;

import android.content.Context;
import android.util.AttributeSet;

import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.b.UiCBA_B;

/**
 * Created by root on 17-6-1.
 */

public class UIBH extends BaseFrameLayout {
    private UiCBA_B a;

    public UIBH(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    protected UiCBA_B getWidgetAppearances() {
        if(this.a == null) {
            this.a = new UiCBA_B();
        }

        return this.a;
    }
}
