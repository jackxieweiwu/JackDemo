package dji.midware.ui.widget.controls;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_D;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.internal.DULSwitchButton;

/**
 * Created by jack_xie on 17-6-1.
 */

public class PictureVideoSwitch extends DULFrameLayout implements View.OnClickListener, DULSwitchButton.a {
    private static final String TAG = "DULCaptureSwitchWidget";
    private SettingsDefinitions.CameraMode curCameraMode;
    private DULSwitchButton switchButton;
    private ImageView videoIcon;
    private ImageView photoIcon;
    private DJIKey cameraModeKey;
    private UiCBA_D widgetAppearances;

    public PictureVideoSwitch(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public PictureVideoSwitch(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public PictureVideoSwitch(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA_D();
        }
        return this.widgetAppearances;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.switchButton = (DULSwitchButton)this.getViewById(R.id.switch_button);
        this.photoIcon = (ImageView)this.getViewById(R.id.imageview_picture_icon);
        this.videoIcon = (ImageView)this.getViewById(R.id.imageview_video_icon);
        this.videoIcon.setAlpha(0.5F);
        this.photoIcon.setAlpha(0.5F);
        this.switchButton.setOnCheckedListener(this);
        this.setOnClickListener(this);
    }

    public void initKey() {
        this.cameraModeKey = CameraKey.create("Mode");
        this.addDependentKey(this.cameraModeKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.cameraModeKey)) {
            this.curCameraMode = (SettingsDefinitions.CameraMode)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        this.onCameraModeChange(this.curCameraMode != null && this.curCameraMode == SettingsDefinitions.CameraMode.SHOOT_PHOTO);
    }

    @MainThread
    private void onCameraModeChange(boolean var1) {
        if(var1) {
            this.switchButton.setEnabled(true);
            if(this.switchButton.a()) {
                this.switchButton.setChecked(false);
            }

            this.videoIcon.setAlpha(0.5F);
            this.photoIcon.setAlpha(1.0F);
        } else {
            this.switchButton.setEnabled(true);
            if(!this.switchButton.a()) {
                this.switchButton.setChecked(true);
            }

            this.videoIcon.setAlpha(1.0F);
            this.photoIcon.setAlpha(0.5F);
        }

    }

    private void performUpdateCameraModeAction(SettingsDefinitions.CameraMode var1, SetCallback var2) {
        if(KeyManager.getInstance() != null) {
            KeyManager.getInstance().setValue(this.cameraModeKey, var1, var2);
        }
    }

    public void onCheckedChanged(final boolean var1) {
        SettingsDefinitions.CameraMode var2 = var1? SettingsDefinitions.CameraMode.RECORD_VIDEO: SettingsDefinitions.CameraMode.SHOOT_PHOTO;
        if(this.curCameraMode != var2) {
            this.performUpdateCameraModeAction(var2, new SetCallback() {
                public void onSuccess() {
                }

                public void onFailure(@NonNull DJIError var1x) {
                    PictureVideoSwitch.this.switchButton.setChecked(!var1);
                }
            });
        }
    }

    public void onClick(View var1) {
        if(this.switchButton != null) {
            this.switchButton.a(var1);
        }

    }
}
