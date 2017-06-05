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

public class CameraConfigApertureWidget extends UiBaseBView {
    private SettingsDefinitions.Aperture aperture;
    private CameraKey currentExposureValueKey;

    public CameraConfigApertureWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CameraConfigApertureWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CameraConfigApertureWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    protected String getTitle() {
        return "F";
    }

    public void initKey() {
        this.currentExposureValueKey = CameraKey.create("ExposureSettings");
        this.addDependentKey(this.currentExposureValueKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.currentExposureValueKey)) {
            ExposureSettings var3 = (ExposureSettings)var1;
            this.aperture = var3.getAperture();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.aperture != null) {
            this.valueText.setText(UiDD.a(this.aperture));
        }

    }
}
