package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
import dji.midware.ui.c.b.UiCBB;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class FocusModeWidget extends DULFrameLayout implements View.OnClickListener {
    private static final String TAG = "FocusModeWidget";
    private UiCAB widgetAppearances;
    private SettingsDefinitions.FocusMode FocusMode;
    private ImageButton backgroundButton;
    private TextView afText;
    private TextView mfText;
    private TextView separator;
    private DJIKey cameraFocusModeKey;

    public FocusModeWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public FocusModeWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public FocusModeWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onFocusModeChange(SettingsDefinitions.FocusMode var1) {
        if(var1 != null) {
            this.enableWidget();
            if(var1 == FocusMode.AUTO) {
                this.afText.setTextColor(this.getResources().getColor(R.color.green_af));
                this.separator.setTextColor(this.getResources().getColor(R.color.green_af));
                this.mfText.setTextColor(this.getResources().getColor(R.color.white_half));
            } else if(var1 == FocusMode.MANUAL) {
                this.afText.setTextColor(this.getResources().getColor(R.color.white_half));
                this.separator.setTextColor(this.getResources().getColor(R.color.green_af));
                this.mfText.setTextColor(this.getResources().getColor(R.color.green_af));
            } else {
                this.afText.setTextColor(this.getResources().getColor(R.color.white_half));
                this.separator.setTextColor(this.getResources().getColor(R.color.white_half));
                this.mfText.setTextColor(this.getResources().getColor(R.color.white_half));
            }

            DJILog.d("FocusModeWidget", "Update widget FocusMode is " + var1);
        } else {
            this.disableWidget();
        }

    }

    @MainThread
    @Keep
    public void performFocusModeAction(SettingsDefinitions.FocusMode var1) {
        if(KeyManager.getInstance() != null) {
            if(var1 != null) {
                KeyManager.getInstance().setValue(this.cameraFocusModeKey, var1, new SetCallback() {
                    public void onSuccess() {
                        FocusModeWidget.this.onFocusModeActionResult((DJIError)null);
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        FocusModeWidget.this.onFocusModeActionResult(var1);
                    }
                });
            }
        }
    }

    @MainThread
    @Keep
    public void onFocusModeActionResult(@Nullable DJIError var1) {
        if(var1 != null) {
            DJILog.d("FocusModeWidget", "Failed to set AF/MF:" + var1.getDescription());
        }

    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.backgroundButton = (ImageButton)this.getViewById(R.id.image_button_background);
        this.afText = (TextView)this.getViewById(R.id.textview_camera_control_af);
        this.separator = (TextView)this.getViewById(R.id.textview_camera_control_seperator);
        this.mfText = (TextView)this.getViewById(R.id.textview_camera_control_mf);
        this.backgroundButton.setOnClickListener(this);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBB();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.cameraFocusModeKey = CameraKey.create("FocusMode");
        this.addDependentKey(this.cameraFocusModeKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.cameraFocusModeKey)) {
            this.FocusMode = (SettingsDefinitions.FocusMode)var1;
        }

        DJILog.d("FocusModeWidget", "FocusMode pushed is " + var1);
    }

    private void enableWidget() {
        this.backgroundButton.setEnabled(true);
        this.afText.setAlpha(1.0F);
        this.separator.setAlpha(1.0F);
        this.mfText.setAlpha(1.0F);
    }

    private void disableWidget() {
        this.backgroundButton.setEnabled(false);
        this.afText.setAlpha(0.5F);
        this.separator.setAlpha(0.5F);
        this.mfText.setAlpha(0.5F);
    }

    public void updateWidget(DJIKey var1) {
        this.onFocusModeChange(this.FocusMode);
    }

    public void onClick(View var1) {
        SettingsDefinitions.FocusMode var10001 = this.FocusMode;
        if(this.FocusMode == FocusMode.AUTO) {
            var10001 = this.FocusMode;
            this.FocusMode = FocusMode.MANUAL;
        } else {
            var10001 = this.FocusMode;
            this.FocusMode = FocusMode.AUTO;
        }

        this.performFocusModeAction(this.FocusMode);
    }
}
