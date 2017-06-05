package dji.midware.ui.panel;

import android.content.Context;
import android.util.AttributeSet;

import dji.common.bus.UILibEventBus;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBP;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.internal.CInter;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Action1;

/**
 * Created by jack_xie on 17-6-1.
 */

public class CameraSettingExposurePanel extends BaseFrameLayout {
    private UiCAB widgetAppearances;

    public CameraSettingExposurePanel(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CameraSettingExposurePanel(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CameraSettingExposurePanel(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setClickable(true);
        UILibEventBus.getInstance().register(CInter.b.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CInter.b>() {
            @Override
            public void call(CInter.b var1) {
                if(var1.a()) {
                    CameraSettingExposurePanel.this.setVisibility(0);
                } else {
                    CameraSettingExposurePanel.this.setVisibility(4);
                }
            }
        });
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBP();
        }

        return this.widgetAppearances;
    }
}
