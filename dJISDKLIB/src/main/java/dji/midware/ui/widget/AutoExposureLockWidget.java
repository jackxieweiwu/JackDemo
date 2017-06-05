package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AutoExposureLockWidget extends DULFrameLayout implements View.OnClickListener {
    private static final String TAG = "DULAELockWidget";
    private boolean isLocked;
    private ImageView lockIcon;
    private UiCBA widgetAppearances;
    private DJIKey aeLockKey;

    public AutoExposureLockWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public AutoExposureLockWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public AutoExposureLockWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onAELockChange(boolean var1) {
        if(var1) {
            this.lockIcon.setImageResource(R.drawable.camera_controll_aelock_lock);
        } else {
            this.lockIcon.setImageResource(R.drawable.camera_controll_aelock_unlock);
        }

    }

    @MainThread
    @Keep
    public void performAELockAction(boolean var1) {
        if(KeyManager.getInstance() != null) {
            KeyManager.getInstance().setValue(this.aeLockKey, Boolean.valueOf(var1), new SetCallback() {
                public void onSuccess() {
                    AutoExposureLockWidget.this.onAELockActionResult((DJIError)null);
                }

                public void onFailure(@NonNull DJIError var1) {
                    AutoExposureLockWidget.this.onAELockActionResult(var1);
                }
            });
        }
    }

    @MainThread
    @Keep
    public void onAELockActionResult(@Nullable DJIError var1) {
        if(var1 != null) {
            DJILog.d("DULAELockWidget", "Failed to set AE lock/unlock!");
        }

    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        ImageButton var4 = (ImageButton)this.getViewById(R.id.image_button_background);
        this.lockIcon = (ImageView)this.getViewById(R.id.imageview_lock);
        var4.setOnClickListener(this);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.aeLockKey = CameraKey.create("AELock");
        this.addDependentKey(this.aeLockKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.aeLockKey)) {
            this.isLocked = ((Boolean)var1).booleanValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.aeLockKey)) {
            this.onAELockChange(this.isLocked);
        }

    }

    public void onClick(View var1) {
        this.performAELockAction(!this.isLocked);
    }
}
