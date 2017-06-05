package dji.midware.ui.internal.exposure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

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
import dji.midware.ui.c.b.UiCBO;

/**
 * Created by root on 17-6-1.
 */

public class DULCameraExposureModeSettingWidget extends DULFrameLayout implements View.OnClickListener {
    private SettingsDefinitions.ExposureMode a;
    private SettingsDefinitions.ExposureMode b;
    private FrameLayout c;
    private DJIKey d;
    private FrameLayout e;
    private FrameLayout f;
    private FrameLayout g;
    private DJIKey h;
    private SettingsDefinitions.ExposureMode[] i;
    private UiCBO j;

    public DULCameraExposureModeSettingWidget(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public DULCameraExposureModeSettingWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public DULCameraExposureModeSettingWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.j == null) {
            this.j = new UiCBO();
        }
        return this.j;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setBackgroundResource(R.drawable.camera_exposure_mode_setting_background);
        this.c = (FrameLayout)this.getViewById(R.id.layout_camera_mode_p);
        this.e = (FrameLayout)this.getViewById(R.id.layout_camera_mode_s);
        this.f = (FrameLayout)this.getViewById(R.id.layout_camera_mode_a);
        this.g = (FrameLayout)this.getViewById(R.id.layout_camera_mode_m);
        this.c.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.c.setSelected(true);
    }

    public void initKey() {
        this.d = CameraKey.create("ExposureMode");
        this.addDependentKey(this.d);
        this.h = CameraKey.create("ExposureModeRange");
        this.addDependentKey(this.h);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.d)) {
            this.a = (SettingsDefinitions.ExposureMode)var1;
        } else if(var2.equals(this.h)) {
            this.i = (SettingsDefinitions.ExposureMode[])((SettingsDefinitions.ExposureMode[])var1);
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.d)) {
            this.a(this.a);
        } else if(var1.equals(this.h)) {
            this.a(this.i);
        }

    }

    private boolean a(SettingsDefinitions.ExposureMode[] var1, SettingsDefinitions.ExposureMode var2) {
        SettingsDefinitions.ExposureMode[] var3 = var1;
        int var4 = var1.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            SettingsDefinitions.ExposureMode var6 = var3[var5];
            if(var6.equals(var2)) {
                return true;
            }
        }

        return false;
    }

    private void a(SettingsDefinitions.ExposureMode[] var1) {
        this.f.setEnabled(this.a(var1, SettingsDefinitions.ExposureMode.APERTURE_PRIORITY));
        this.e.setEnabled(this.a(var1, SettingsDefinitions.ExposureMode.SHUTTER_PRIORITY));
        this.g.setEnabled(this.a(var1, SettingsDefinitions.ExposureMode.MANUAL));
        this.c.setEnabled(this.a(var1, SettingsDefinitions.ExposureMode.PROGRAM));
    }

    private void a(SettingsDefinitions.ExposureMode var1) {
        this.c.setSelected(false);
        this.g.setSelected(false);
        this.e.setSelected(false);
        this.f.setSelected(false);
        if(var1 == SettingsDefinitions.ExposureMode.PROGRAM) {
            if(!this.c.isSelected()) {
                this.c.setSelected(true);
            }
        } else if(var1 == SettingsDefinitions.ExposureMode.SHUTTER_PRIORITY) {
            if(!this.e.isSelected()) {
                this.e.setSelected(true);
            }
        } else if(var1 == SettingsDefinitions.ExposureMode.APERTURE_PRIORITY) {
            if(!this.f.isSelected()) {
                this.f.setSelected(true);
            }
        } else if(var1 == SettingsDefinitions.ExposureMode.MANUAL && !this.g.isSelected()) {
            this.g.setSelected(true);
        }

    }

    public void onClick(View var1) {
        this.b = this.a;
        int var2 = var1.getId();
        if(var2 == R.id.layout_camera_mode_p) {
            this.a = SettingsDefinitions.ExposureMode.PROGRAM;
        } else if(var2 == R.id.layout_camera_mode_a) {
            this.a = SettingsDefinitions.ExposureMode.APERTURE_PRIORITY;
        } else if(var2 == R.id.layout_camera_mode_s) {
            this.a = SettingsDefinitions.ExposureMode.SHUTTER_PRIORITY;
        } else if(var2 == R.id.layout_camera_mode_m) {
            this.a = SettingsDefinitions.ExposureMode.MANUAL;
        }

        if(!this.a.equals(this.b)) {
            this.a(this.a);
            if(KeyManager.getInstance() != null) {
                KeyManager.getInstance().setValue(this.d, this.a, new SetCallback() {
                    public void onSuccess() {
                        DJILog.d("DULCameraExposureModeSettingWidget", "Camera Exposure Mode set successfully");
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DULCameraExposureModeSettingWidget.this.a();
                        DJILog.d("DULCameraExposureModeSettingWidget", "Failed to set Camera Exposure Mode");
                    }
                });
            }
        }
    }

    private void a() {
        if(KeyManager.getInstance() != null) {
            Object var1 = KeyManager.getInstance().getValue(this.d);
            if(var1 != null) {
                this.a = (SettingsDefinitions.ExposureMode)var1;
                this.a(this.a);
            }

        }
    }
}
