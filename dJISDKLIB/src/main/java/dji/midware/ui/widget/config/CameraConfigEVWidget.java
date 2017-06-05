package dji.midware.ui.widget.config;

import android.content.Context;
import android.util.AttributeSet;

import dji.common.camera.ExposureSettings;
import dji.common.camera.SettingsDefinitions;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.midware.ui.base.UiBaseBView;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDD;

/**
 * Created by jack_xie on 17-6-1.
 */

public class CameraConfigEVWidget extends UiBaseBView {
    private SettingsDefinitions.ExposureCompensation currentExposureValue;
    private CameraKey currentExposureValueKey;
    private CameraKey evValueKey;
    private CameraKey exposureModeKey;
    private SettingsDefinitions.ExposureMode exposureMode;
    private SettingsDefinitions.ExposureCompensation evValue;

    public CameraConfigEVWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CameraConfigEVWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CameraConfigEVWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    protected String getTitle() {
        return "EV";
    }

    public void initKey() {
        this.currentExposureValueKey = CameraKey.create("ExposureSettings");
        this.exposureModeKey = CameraKey.create("ExposureMode");
        this.evValueKey = CameraKey.create("ExposureCompensation");
        this.addDependentKey(this.currentExposureValueKey);
        this.addDependentKey(this.exposureModeKey);
        this.addDependentKey(this.evValueKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.currentExposureValueKey)) {
            ExposureSettings var3 = (ExposureSettings)var1;
            this.currentExposureValue = var3.getExposureCompensation();
        } else if(var2.equals(this.exposureModeKey)) {
            this.exposureMode = (SettingsDefinitions.ExposureMode)var1;
        } else if(var2.equals(this.evValueKey)) {
            this.evValue = (SettingsDefinitions.ExposureCompensation)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.exposureMode != SettingsDefinitions.ExposureMode.MANUAL) {
            if(var1.equals(this.evValueKey)) {
                this.valueText.setText(UiDD.a(this.evValue));
            }
        } else if(var1.equals(this.currentExposureValueKey)) {
            this.valueText.setText(UiDD.a(this.currentExposureValue));
        }

    }
}
