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

public class CameraConfigISOWidget extends UiBaseBView {
    private SettingsDefinitions.ISO realISO;
    private CameraKey currentExposureValueKey;
    private CameraKey currentISOKey;
    private SettingsDefinitions.ISO iso;

    public CameraConfigISOWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CameraConfigISOWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CameraConfigISOWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    protected String getTitle() {
        return "ISO";
    }

    public void initKey() {
        this.currentExposureValueKey = CameraKey.create("ExposureSettings");
        this.currentISOKey = CameraKey.create("ISO");
        this.addDependentKey(this.currentExposureValueKey);
        this.addDependentKey(this.currentISOKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.currentExposureValueKey)) {
            ExposureSettings var3 = (ExposureSettings)var1;
            this.realISO = var3.getISO();
        } else if(var2.equals(this.currentISOKey)) {
            this.iso = (SettingsDefinitions.ISO)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.realISO != null) {
            if(this.iso != null && this.iso.value() == SettingsDefinitions.ISO.AUTO.value()) {
                this.valueText.setText(SettingsDefinitions.ISO.AUTO.name());
            } else {
                this.valueText.setText(UiDD.a(this.realISO));
            }
        }

    }
}
