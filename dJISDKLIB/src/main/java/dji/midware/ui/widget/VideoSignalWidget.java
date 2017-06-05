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
import dji.midware.ui.c.b.UiCBA_L;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class VideoSignalWidget extends DULFrameLayout {
    private ImageView hdSignal;
    private int signalValue;
    private int currentHDSignalResId;
    private UiCBA_L widgetAppearances;
    private AirLinkKey videoSignalStrengthKey;
    private AirLinkKey ocuVideoSignalStrengthKey;

    public VideoSignalWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onVideoSignalStrengthChange(@IntRange(from = 0L,to = 100L) int var1) {
        this.hdSignal.setImageResource(this.currentHDSignalResId);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA_L();
        }

        return this.widgetAppearances;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        ImageView var4 = (ImageView)this.getViewById(R.id.imageview_rc_video_icon);
        this.hdSignal = (ImageView)this.getViewById(R.id.imageview_rc_video_signal);
        var4.setImageResource(R.drawable.ic_topbar_hd_nor);
        this.hdSignal.setImageResource(R.drawable.ic_topbar_signal_level_0);
    }

    public void initKey() {
        this.videoSignalStrengthKey = AirLinkKey.createLightbridgeLinkKey("DownlinkSignalQuality");
        this.ocuVideoSignalStrengthKey = AirLinkKey.createOcuSyncLinkKey("DownlinkSignalQuality");
        this.addDependentKey(this.videoSignalStrengthKey);
        this.addDependentKey(this.ocuVideoSignalStrengthKey);
    }

    private void updateResourceBasedOnValue(int var1) {
        if(var1 <= 0) {
            this.currentHDSignalResId = R.drawable.ic_topbar_signal_level_0;
        } else if(var1 > 0 && var1 <= 20) {
            this.currentHDSignalResId = R.drawable.ic_topbar_signal_level_1;
        } else if(var1 > 20 && var1 <= 40) {
            this.currentHDSignalResId = R.drawable.ic_topbar_signal_level_2;
        } else if(var1 > 40 && var1 <= 60) {
            this.currentHDSignalResId = R.drawable.ic_topbar_signal_level_3;
        } else if(var1 > 60 && var1 <= 80) {
            this.currentHDSignalResId = R.drawable.ic_topbar_signal_level_4;
        } else {
            this.currentHDSignalResId = R.drawable.ic_topbar_signal_level_5;
        }

    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.videoSignalStrengthKey)) {
            this.signalValue = ((Integer)var1).intValue();
            this.updateResourceBasedOnValue(this.signalValue);
        } else if(var2.equals(this.ocuVideoSignalStrengthKey)) {
            this.signalValue = ((Integer)var1).intValue();
            this.updateResourceBasedOnValue(this.signalValue);
        }

    }

    public void updateWidget(DJIKey var1) {
        this.onVideoSignalStrengthChange(this.signalValue);
    }
}
