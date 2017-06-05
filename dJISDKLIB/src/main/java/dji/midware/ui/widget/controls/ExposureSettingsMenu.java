package dji.midware.ui.widget.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import dji.common.camera.SettingsDefinitions;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.midware.R;
import dji.midware.ui.base.UiBaseCView;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBQ;
import dji.midware.ui.d.UiDA;

/**
 * Created by root on 17-6-1.
 */

public class ExposureSettingsMenu extends UiBaseCView {
    static final String TAG = "DULExposureSettingWidget";
    private int defaultForegroundResId;
    SettingsDefinitions.ExposureMode exposureMode;
    private UiCBQ widgetAppearances;
    private CameraKey exposureModeKey;

    public ExposureSettingsMenu(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public ExposureSettingsMenu(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public ExposureSettingsMenu(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBQ();
        }

        return this.widgetAppearances;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.imageForeground = (ImageView)this.getViewById(R.id.image_button_foreground);
    }

    public void initKey() {
        this.exposureModeKey = CameraKey.create("ExposureMode");
        this.addDependentKey(this.exposureModeKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.exposureModeKey)) {
            this.exposureMode = (SettingsDefinitions.ExposureMode)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.exposureMode == null) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
            if(this.exposureMode == SettingsDefinitions.ExposureMode.PROGRAM) {
                this.defaultForegroundResId = R.drawable.camera_controll_exposure_mode_p;
            } else if(this.exposureMode == SettingsDefinitions.ExposureMode.SHUTTER_PRIORITY) {
                this.defaultForegroundResId = R.drawable.camera_controll_exposure_mode_s;
            } else if(this.exposureMode == SettingsDefinitions.ExposureMode.APERTURE_PRIORITY) {
                this.defaultForegroundResId = R.drawable.camera_controll_exposure_mode_a;
            } else if(this.exposureMode == SettingsDefinitions.ExposureMode.MANUAL) {
                this.defaultForegroundResId = R.drawable.camera_controll_exposure_mode_m;
            }

            this.imageForeground.setImageResource(this.defaultForegroundResId);
        }

    }

    public void onClick(View var1) {
    }
}
