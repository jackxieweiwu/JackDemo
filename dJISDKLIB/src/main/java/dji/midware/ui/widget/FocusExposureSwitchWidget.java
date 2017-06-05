package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import dji.common.bus.UILibEventBus;
import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBS;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class FocusExposureSwitchWidget extends DULFrameLayout implements View.OnClickListener {
    private static final String TAG = "DULFocusExposureSwitchWidget";
    private UiCAB widgetAppearances;
    private ImageView foregroundImage;
    private int foregroundResId;
    private int meteringForegroundResId;
    private int focusForegroundResId;
    private SettingsDefinitions.FocusMode focusMode = null;
    private SettingsDefinitions.MeteringMode meteringMode = null;
    private DJIKey meteringModeKey;
    private DJIKey cameraFocusModeKey;
    private DJIKey lenIsFocusAssistantWorkingKey;

    public FocusExposureSwitchWidget(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public FocusExposureSwitchWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public FocusExposureSwitchWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        UiDA.b();
        this.foregroundImage = (ImageView)this.getViewById(R.id.image_button_foreground);
        this.meteringForegroundResId = R.drawable.rectangle_1112_metering_icon;
        this.focusForegroundResId = R.drawable.rectangle_314_copy_2;
        this.foregroundImage.setOnClickListener(this);
        this.switchToFocusMode();
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBS();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.meteringModeKey = CameraKey.create("MeteringMode");
        this.cameraFocusModeKey = CameraKey.create("FocusMode");
        this.lenIsFocusAssistantWorkingKey = CameraKey.create("LensIsFocusAssistantWorking");
        this.addDependentKey(this.meteringModeKey);
        this.addDependentKey(this.cameraFocusModeKey);
        this.addDependentKey(this.lenIsFocusAssistantWorkingKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.meteringModeKey)) {
            this.meteringMode = (SettingsDefinitions.MeteringMode)var1;
        } else if(var2.equals(this.cameraFocusModeKey)) {
            this.focusMode = (SettingsDefinitions.FocusMode)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        this.foregroundImage.setImageResource(this.foregroundResId);
    }

    public void onClick(View var1) {
        DJILog.d("DULFocusExposureSwitchWidget", "onClick foregroundResId = " + this.foregroundResId);
        if(KeyManager.getInstance() != null) {
            if(this.foregroundResId == this.focusForegroundResId) {
                this.switchToMeterMode();
                KeyManager.getInstance().setValue(this.meteringModeKey, SettingsDefinitions.MeteringMode.SPOT, new SetCallback() {
                    public void onSuccess() {
                        DJILog.d("DULFocusExposureSwitchWidget", "Spot metering set successfully!");
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("DULFocusExposureSwitchWidget", "Failed to set Spot metering:" + var1.getDescription());
                    }
                });
            } else {
                this.switchToFocusMode();
            }

        }
    }

    private void switchToMeterMode() {
        this.signalMeteringMode();
        this.foregroundResId = this.meteringForegroundResId;
        this.updateWidget((DJIKey)null);
    }

    private void switchToFocusMode() {
        this.signalFocusMode();
        this.foregroundResId = this.focusForegroundResId;
        this.updateWidget((DJIKey)null);
    }

    private void signalMeteringMode() {
        UILibEventBus.getInstance().post(FocusExposureSwitchWidget.ControlMode.METER);
    }

    private void signalFocusMode() {
        UILibEventBus.getInstance().post(FocusExposureSwitchWidget.ControlMode.FOCUS);
    }

    public static enum ControlMode {
        METER,
        FOCUS;

        private ControlMode() {
        }
    }
}
