package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.widget.ImageView;

import dji.keysdk.AirLinkKey;
import dji.keysdk.DJIKey;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_N;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class WiFiSignalWidget extends DULFrameLayout {
    private int currentWifiSignalResId;
    private ImageView wifiSignal;
    private UiCBA_N widgetAppearances;
    private DJIKey wifiSignalKey;
    private int signalValue;

    public WiFiSignalWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onWifiSignalChange(@IntRange(from = 0L,to = 100L) int var1) {
        if(var1 <= 0) {
            this.currentWifiSignalResId = R.drawable.ic_topbar_wifi_level_1;
        } else if(var1 > 75) {
            this.currentWifiSignalResId = R.drawable.ic_topbar_wifi_level_5;
        } else if(var1 > 0 && var1 <= 25) {
            this.currentWifiSignalResId = R.drawable.ic_topbar_wifi_level_2;
        } else if(var1 > 25 && var1 <= 50) {
            this.currentWifiSignalResId = R.drawable.ic_topbar_wifi_level_3;
        } else if(var1 > 50 && var1 <= 75) {
            this.currentWifiSignalResId = R.drawable.ic_topbar_wifi_level_4;
        }

        this.wifiSignal.setImageResource(this.currentWifiSignalResId);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.wifiSignal = (ImageView)this.getViewById(R.id.imageview_signal_icon);
        this.wifiSignal.setImageResource(R.drawable.ic_topbar_wifi_level_1);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA_N();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.wifiSignalKey = AirLinkKey.createWiFiLinkKey("DownlinkSignalQuality");
        this.addDependentKey(this.wifiSignalKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.wifiSignalKey)) {
            this.signalValue = ((Integer)var1).intValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        this.onWifiSignalChange(this.signalValue);
    }
}
