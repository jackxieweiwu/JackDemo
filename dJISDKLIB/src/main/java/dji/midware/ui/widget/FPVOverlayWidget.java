package dji.midware.ui.widget;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import dji.common.bus.UILibEventBus;
import dji.common.camera.SettingsDefinitions;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.internal.AInter;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Action1;

/**
 * Created by jack_xie on 17-6-1.
 */

public class FPVOverlayWidget extends DULFrameLayout implements View.OnTouchListener {
    private float targetX;
    private float targetY;
    private float absTargetX;
    private float absTargetY;
    private AInter crosshair;
    private static final DJIKey METERING_MODE_KEY = CameraKey.create("MeteringMode");
    private static final DJIKey FOCUS_MODE_KEY = CameraKey.create("FocusMode");
    private static final DJIKey FOCUS_TARGET_KEY = CameraKey.create("FocusTarget");
    private static final DJIKey METERING_TARGET_KEY = CameraKey.create("SpotMeteringTarget");
    private SettingsDefinitions.MeteringMode meteringMode;
    private SettingsDefinitions.FocusMode focusMode;
    private FocusExposureSwitchWidget.ControlMode controlMode;
    private static final Integer[] METER_ROW_INDEX = new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7)};
    private static final Integer[] METER_COL_INDEX = new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11)};

    public FPVOverlayWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public FPVOverlayWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public FPVOverlayWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.meteringMode = null;
        this.focusMode = null;
        this.controlMode = FocusExposureSwitchWidget.ControlMode.FOCUS;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        UiDA.b();
        this.setOnTouchListener(this);
        this.crosshair = new AInter(var1);
        this.addView(this.crosshair);
        UILibEventBus.getInstance().register(FocusExposureSwitchWidget.ControlMode.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<FocusExposureSwitchWidget.ControlMode>() {
            @Override
            public void call(FocusExposureSwitchWidget.ControlMode var1) {
                FPVOverlayWidget.this.controlMode = var1;
                FPVOverlayWidget.this.crosshair.setControlMode(var1);
            }
        });
    }

    public boolean onTouch(View var1, MotionEvent var2) {
        switch(var2.getAction()) {
            case 0:
                this.absTargetX = var2.getX();
                this.absTargetY = var2.getY();
                this.targetX = this.absTargetX / (float)var1.getWidth();
                this.targetY = this.absTargetY / (float)var1.getHeight();
                break;
            case 1:
                this.absTargetX = var2.getX();
                this.absTargetY = var2.getY();
                this.targetX = this.absTargetX / (float)var1.getWidth();
                this.targetY = this.absTargetY / (float)var1.getHeight();
                this.showCrossHair(this.absTargetX, this.absTargetY);
                this.updateCameraTargetIfNeeded(this.targetX, this.targetY);
                break;
            case 2:
                if(this.calcManhattanDistance((double)this.absTargetX, (double)this.absTargetY, (double)var2.getX(), (double)var2.getY()) < 20.0D) {
                    return true;
                }
        }

        return true;
    }

    private void updateCameraTargetIfNeeded(float var1, float var2) {
        if(this.controlMode == FocusExposureSwitchWidget.ControlMode.FOCUS) {
            if(this.focusMode != null && this.focusMode == SettingsDefinitions.FocusMode.AUTO) {
                KeyManager.getInstance().setValue(FOCUS_TARGET_KEY, new PointF(var1, var2), (SetCallback)null);
            }
        } else {
            int var3 = METER_COL_INDEX[(int)(var1 * (float)METER_COL_INDEX.length)].intValue();
            int var4 = METER_ROW_INDEX[(int)(var2 * (float)METER_ROW_INDEX.length)].intValue();
            if(this.meteringMode != null && this.meteringMode == SettingsDefinitions.MeteringMode.SPOT) {
                KeyManager.getInstance().setValue(METERING_TARGET_KEY, new Point(var3, var4), (SetCallback)null);
            }
        }

    }

    private double calcManhattanDistance(double var1, double var3, double var5, double var7) {
        return Math.abs(var1 - var5) + Math.abs(var3 - var7);
    }

    private void showCrossHair(float var1, float var2) {
        if(this.crosshair != null) {
            this.crosshair.setTranslationX(var1 - (float)(this.crosshair.getWidth() / 2));
            this.crosshair.setTranslationY(var2 - (float)(this.crosshair.getHeight() / 2));
            this.crosshair.a();
        }

    }

    private void hideCrossHair() {
        this.crosshair.b();
    }

    public void initKey() {
        this.addDependentKey(METERING_MODE_KEY);
        this.addDependentKey(FOCUS_MODE_KEY);
        this.addDependentKey(FOCUS_TARGET_KEY);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(METERING_MODE_KEY)) {
            this.meteringMode = (SettingsDefinitions.MeteringMode)var1;
        } else if(var2.equals(FOCUS_MODE_KEY)) {
            this.focusMode = (SettingsDefinitions.FocusMode)var1;
        }

    }

    protected UiCBA_B getWidgetAppearances() {
        return null;
    }

    public void updateWidget(DJIKey var1) {
    }
}
