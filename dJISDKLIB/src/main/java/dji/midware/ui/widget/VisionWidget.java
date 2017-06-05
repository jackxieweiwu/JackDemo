package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.widget.ImageView;

import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_M;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class VisionWidget extends DULFrameLayout {
    private UiCAB widgetAppearances;
    private int currentIconResId;
    private ImageView visionIcon;
    private DJIKey visionUsedKey;
    private DJIKey visionSensorWorkKey;
    private DJIKey visionSensorEnableKey;
    private boolean isVisionWorking = false;
    private boolean visionIsUsing;
    private boolean visionSensorEnabled;
    private boolean visionSensorWork;

    public VisionWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onIsVisionUsedChange(boolean var1) {
        if(var1) {
            this.currentIconResId = R.drawable.visual_enable;
        } else {
            this.currentIconResId = R.drawable.visual;
        }

        this.visionIcon.setImageResource(this.currentIconResId);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.visionIcon = (ImageView)this.getViewById(R.id.imageview_signal_icon);
        this.visionIcon.setImageResource(R.drawable.visual);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA_M();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.visionUsedKey = FlightControllerKey.create("IsVisionPositioningSensorBeingUsed");
        this.addDependentKey(this.visionUsedKey);
        this.visionSensorWorkKey = FlightControllerKey.create("IsVisionPositioningSensorBeingUsed");
        this.addDependentKey(this.visionSensorWorkKey);
        this.visionSensorEnableKey = FlightControllerKey.createFlightAssistantKey("VisionPositioningEnabled");
        this.addDependentKey(this.visionSensorEnableKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.visionUsedKey)) {
            this.visionIsUsing = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.visionSensorEnableKey)) {
            this.visionSensorEnabled = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.visionSensorWorkKey)) {
            this.visionSensorWork = ((Boolean)var1).booleanValue();
        }

        this.isVisionWorking = this.visionSensorEnabled && this.visionSensorWork && this.visionIsUsing;
    }

    public void updateWidget(DJIKey var1) {
        this.onIsVisionUsedChange(this.isVisionWorking);
    }
}
