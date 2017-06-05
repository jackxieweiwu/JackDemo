package dji.midware.ui.widget.dashboard;

import android.content.Context;
import android.util.AttributeSet;

import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBL;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DashboardWidget extends BaseFrameLayout {
    private UiCAB widgetAppearances;

    public DashboardWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public DashboardWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public DashboardWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBL();
        }
        return this.widgetAppearances;
    }
}
