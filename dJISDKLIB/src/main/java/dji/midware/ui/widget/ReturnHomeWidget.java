package dji.midware.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import dji.common.error.DJIError;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.ActionCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.UiBaseCView;
import dji.midware.ui.c.UiCA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBF;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDH;
import dji.midware.ui.internal.BInterDialog;

/**
 * Created by jack_xie on 17-6-1.
 */

public class ReturnHomeWidget extends UiBaseCView {
    private static final String TAG = "DULGoHomeWidget";
    private static final float DISABLE_ALPHA = 0.5F;
    private static final float ENABLE_ALPHA = 1.0F;
    private boolean isFlying;
    private boolean isGoingHome;
    private boolean areMotorsOn;
    private int cancelGoingHomeResId;
    private int goingHomeResId;
    private int currentResId;
    private UiCBF widgetAppearances;
    private DJIKey isGoingHomeKey;
    private DJIKey areMotorsOnKey;
    private DJIKey isFlyingKey;
    private DJIKey isLandingKey;
    private boolean isAutoLanding;
    private BInterDialog slideDialog;
    private static final int OPERATE_TYPE_NONE = 0;
    private static final int OPERATE_TYPE_GOHOME = 2;
    private int operateType;

    public ReturnHomeWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public ReturnHomeWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public ReturnHomeWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.isFlying = false;
        this.isGoingHome = false;
        this.areMotorsOn = false;
        this.slideDialog = null;
        this.operateType = 0;
        UiDA.b();
    }

    @MainThread
    @Keep
    public void performReturnHomeAction() {
        FlightControllerKey var1 = FlightControllerKey.create("GoHome");
        if(KeyManager.getInstance() != null) {
            KeyManager.getInstance().performAction(var1, new ActionCallback() {
                public void onSuccess() {
                    ReturnHomeWidget.this.onReturnHomeActionResult((DJIError)null);
                }

                public void onFailure(@NonNull DJIError var1) {
                    ReturnHomeWidget.this.onReturnHomeActionResult(var1);
                }
            }, new Object[0]);
        }

    }

    @MainThread
    @Keep
    public void onReturnHomeActionResult(@Nullable DJIError var1) {
        if(var1 != null) {
            this.showErrorDlg(var1.getDescription());
        }

    }

    @MainThread
    @Keep
    public void performCancelReturnHomeAction() {
        FlightControllerKey var1 = FlightControllerKey.create("CancelGoHome");
        if(KeyManager.getInstance() != null) {
            KeyManager.getInstance().performAction(var1, new ActionCallback() {
                public void onSuccess() {
                    ReturnHomeWidget.this.onCancelReturnHomeActionResult((DJIError)null);
                }

                public void onFailure(@NonNull DJIError var1) {
                    ReturnHomeWidget.this.onCancelReturnHomeActionResult((DJIError)null);
                }
            }, new Object[0]);
        }

    }

    @MainThread
    @Keep
    public void onCancelReturnHomeActionResult(@Nullable DJIError var1) {
        if(var1 != null) {
            this.showErrorDlg(var1.getDescription());
        }

    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.goingHomeResId = R.drawable.leftmenu_dlg_gohome;
        this.cancelGoingHomeResId = R.drawable.leftmenu_dlg_cancel;
        this.imageForeground = (ImageView)this.getViewById(R.id.image_button_foreground);
        this.imageForeground.setImageResource(this.goingHomeResId);
        this.imageBackground = (ImageButton)this.getViewById(R.id.image_button_background);
        this.imageBackground.setOnClickListener(this);
    }

    protected void onMeasure(int var1, int var2) {
        super.onMeasure(var1, var2);
    }

    public void initKey() {
        this.isGoingHomeKey = FlightControllerKey.create("IsGoingHome");
        this.isFlyingKey = FlightControllerKey.create("IsFlying");
        this.areMotorsOnKey = FlightControllerKey.create("AreMotorsOn");
        this.isLandingKey = FlightControllerKey.create("IsAutoLanding");
        this.addDependentKey(this.isLandingKey);
        this.addDependentKey(this.isFlyingKey);
        this.addDependentKey(this.isGoingHomeKey);
        this.addDependentKey(this.areMotorsOnKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.isFlyingKey)) {
            this.isFlying = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.isGoingHomeKey)) {
            this.isGoingHome = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.areMotorsOnKey)) {
            this.areMotorsOn = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.isLandingKey)) {
            this.isAutoLanding = ((Boolean)var1).booleanValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.isFlying && this.areMotorsOn && !this.isAutoLanding) {
            if(this.isGoingHome) {
                this.onCancelReturnHomeEnable(true);
            } else {
                this.onReturnHomeEnable(true);
            }
        } else {
            this.onReturnHomeEnable(false);
        }

    }

    @MainThread
    public void onReturnHomeEnable(boolean var1) {
        this.setEnabled(var1);
        this.imageForeground.setImageResource(this.goingHomeResId);
        this.currentResId = this.goingHomeResId;
        this.imageBackground.setEnabled(var1);
        if(var1) {
            this.imageForeground.setAlpha(1.0F);
        } else {
            this.imageForeground.setAlpha(0.5F);
        }

    }

    @MainThread
    public void onCancelReturnHomeEnable(boolean var1) {
        if(var1) {
            this.imageForeground.setImageResource(this.cancelGoingHomeResId);
            this.currentResId = this.cancelGoingHomeResId;
        } else {
            this.imageForeground.setImageResource(this.goingHomeResId);
            this.currentResId = this.goingHomeResId;
        }

    }

    public void onClick(View var1) {
        DJILog.d("DULGoHomeWidget", "button is clicked");
        if(this.currentResId == this.cancelGoingHomeResId) {
            this.hideSlideDialog();
            this.cancelReturnHomeAction();
        } else if(this.currentResId == this.goingHomeResId) {
            this.showTipDialog(2);
        }

    }

    private void showTipDialog(int var1) {
        if(null == this.slideDialog) {
            this.slideDialog = new BInterDialog(this.getContext());
            this.slideDialog.a(new BInterDialog.dialogInterfaceA() {
                public void onLeftBtnClick(DialogInterface var1, int var2) {
                    ReturnHomeWidget.this.handleTipDlgLeftBtnClick();
                }

                public void onRightBtnClick(DialogInterface var1, int var2) {
                    ReturnHomeWidget.this.handleTipDlgRightBtnClick();
                }

                public void onCbChecked(DialogInterface var1, boolean var2, int var3) {
                    ReturnHomeWidget.this.handleTipDlgCbChecked(var2);
                }
            });
            this.slideDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface var1) {
                    ReturnHomeWidget.this.operateType = 0;
                }
            });
        }

        if(null != this.slideDialog && !this.slideDialog.isShowing()) {
            this.operateType = var1;
            if(var1 == 2) {
                this.updateReturnHomeAlertDlg();
            }

            this.slideDialog.show();
        }

    }

    private void hideSlideDialog() {
        if(null != this.slideDialog && this.slideDialog.isShowing()) {
            this.slideDialog.dismiss();
        }

    }

    private void cancelReturnHomeAction() {
        this.performCancelReturnHomeAction();
    }

    private void handleTipDlgRightBtnClick() {
        this.hideSlideDialog();
        this.returnHomeAction();
    }

    private void handleTipDlgLeftBtnClick() {
        this.hideSlideDialog();
    }

    private void handleTipDlgCbChecked(boolean var1) {
        if(var1 && 2 == this.operateType) {
            this.returnHomeAction();
            this.hideSlideDialog();
        }

    }

    private void updateReturnHomeAlertDlg() {
        if(this.cancelGoingHomeResId == this.currentResId) {
            this.slideDialog.a(1);
            this.slideDialog.b(R.drawable.leftmenu_dlg_cancel);
            this.slideDialog.a(this.getContext().getString(R.string.left_menu_cancel_gohome));
            this.slideDialog.b(this.getContext().getString(R.string.left_menu_cancel_gohome_desc));
            this.slideDialog.a(8, 0);
            this.slideDialog.d(8);
            this.slideDialog.c(0);
        } else {
            this.slideDialog.a(1);
            this.slideDialog.b(R.drawable.leftmenu_dlg_gohome);
            this.slideDialog.a(this.getContext().getString(R.string.left_menu_gohome));
            FlightControllerKey var1 = FlightControllerKey.create("GoHomeAltitude");
            FlightControllerKey var2 = FlightControllerKey.create("HomeLocationLatitude");
            FlightControllerKey var3 = FlightControllerKey.create("HomeLocationLongitude");
            FlightControllerKey var4 = FlightControllerKey.create("Altitude");
            FlightControllerKey var5 = FlightControllerKey.create("AircraftLocationLatitude");
            FlightControllerKey var6 = FlightControllerKey.create("AircraftLocationLongitude");
            KeyManager var7 = KeyManager.getInstance();
            float var8 = 0.0F;
            float var9 = 0.0F;
            double var10 = 0.0D / 0.0;
            double var12 = 0.0D / 0.0;
            double var14 = 0.0D;
            double var16 = 0.0D;
            if(var7 != null) {
                Object var18 = var7.getValue(var4);
                if(var18 != null) {
                    var8 = ((Float)var18).floatValue();
                }

                var18 = var7.getValue(var1);
                if(var18 != null) {
                    var9 = ((Float)var18).floatValue();
                }

                var18 = var7.getValue(var2);
                if(var18 != null) {
                    var10 = ((Double)var18).doubleValue();
                }

                var18 = var7.getValue(var3);
                if(var18 != null) {
                    var12 = ((Double)var18).doubleValue();
                }

                var18 = var7.getValue(var5);
                if(var18 != null) {
                    var14 = ((Double)var18).doubleValue();
                }

                var18 = var7.getValue(var6);
                if(var18 != null) {
                    var16 = ((Double)var18).doubleValue();
                }
            }

            float[] var19 = new float[1];
            if(var10 != 0.0D / 0.0 && var12 != 0.0D / 0.0) {
                Location.distanceBetween(var10, var12, var14, var16, var19);
            }

            if(var19[0] < 20.0F) {
                this.slideDialog.b(this.getContext().getString(R.string.left_menu_gohome_inner_desc));
            } else if(20.0F <= var9 && var9 <= 500.0F && var8 < var9) {
                this.slideDialog.b(this.getContext().getString(R.string.left_menu_gohome_below_desc, new Object[]{Float.valueOf(var9), Float.valueOf(UiDH.a(var9))}));
            } else {
                this.slideDialog.b(this.getContext().getString(R.string.left_menu_gohome_above_desc, new Object[]{Float.valueOf(var8), Float.valueOf(UiDH.a(var8))}));
            }

            this.slideDialog.a(8, 0);
            this.slideDialog.d(0);
            this.slideDialog.c(this.getContext().getString(R.string.left_menu_gohome_switch));
            this.slideDialog.c(8);
        }

    }

    private void returnHomeAction() {
        this.performReturnHomeAction();
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBF();
        }

        return this.widgetAppearances;
    }
}
