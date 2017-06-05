package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import dji.common.battery.BatteryConnectionState;
import dji.common.flightcontroller.BatteryThresholdBehavior;
import dji.keysdk.BatteryKey;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBE;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class BatteryWidget extends DULFrameLayout {
    private static final String TAG = "DULBatteryWidget";
    private ImageView iconImageView;
    private TextView valueTextView;
    private int batteryPercentage;
    private BatteryConnectionState batteryConnectionState;
    private BatteryThresholdBehavior batteryWarningLevel;
    private String batteryPerInString;
    private int goHomeBattery;
    private int currentBatteryIconId;
    private int currentTextColorId;
    private DJIKey batteryEnergyRemainPercentageKey;
    private DJIKey batteryConnectionStateKey;
    private DJIKey batteryRemainFlightKey;
    private DJIKey batteryNeedToGoHomeKey;
    private UiCBE widgetAppearances;
    private int preIconId;
    private int preTextColorId;

    public BatteryWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public BatteryWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public BatteryWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.batteryPercentage = 0;
        this.batteryConnectionState = BatteryConnectionState.NORMAL;
        this.batteryWarningLevel = BatteryThresholdBehavior.FLY_NORMALLY;
        this.batteryPerInString = "N/A";
        this.goHomeBattery = 0;
        this.currentBatteryIconId = R.drawable.ic_topbar_battery_nor;
        this.currentTextColorId = R.color.green_battery;
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onBatteryPercentageChange(@IntRange(from = 0L,to = 100L) int var1) {
        this.batteryPerInString = this.getContext().getString(R.string.battery_percent, new Object[]{Integer.valueOf(var1)});
        this.valueTextView.setText(this.batteryPerInString);
        this.updateUIResources();
    }

    @MainThread
    @Keep
    public void onBatteryConnectionStateChange(@Nullable BatteryConnectionState var1) {
        this.updateUIResources();
    }

    @MainThread
    @Keep
    public void onRemainingBatteryStateChange(@Nullable BatteryThresholdBehavior var1) {
        this.updateUIResources();
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.iconImageView = (ImageView)this.getViewById(R.id.imageview_battery_icon);
        this.valueTextView = (TextView)this.getViewById(R.id.textview_battery_value);
        if(this.valueTextView != null) {
            this.valueTextView.setText(R.string.string_default_value);
        }

    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBE();
        }

        return this.widgetAppearances;
    }

    private void updateUIResources() {
        if(this.iconImageView != null && this.preIconId != this.currentBatteryIconId) {
            this.preIconId = this.currentBatteryIconId;
            this.iconImageView.setImageResource(this.currentBatteryIconId);
        }

        if(this.valueTextView != null && this.preTextColorId != this.currentTextColorId) {
            this.preTextColorId = this.currentTextColorId;
            this.valueTextView.setTextColor(this.getResources().getColor(this.currentTextColorId));
        }

    }

    public void initKey() {
        this.batteryEnergyRemainPercentageKey = BatteryKey.create("ChargeRemainingInPercent");
        this.batteryConnectionStateKey = BatteryKey.create("ConnectionState");
        this.batteryRemainFlightKey = FlightControllerKey.create("RemainingBattery");
        this.batteryNeedToGoHomeKey = FlightControllerKey.create("BatteryPercentageNeededToGoHome");
        this.addDependentKey(this.batteryEnergyRemainPercentageKey);
        this.addDependentKey(this.batteryConnectionStateKey);
        this.addDependentKey(this.batteryRemainFlightKey);
        this.addDependentKey(this.batteryNeedToGoHomeKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.batteryEnergyRemainPercentageKey)) {
            DJILog.d("Batman", "Got percentage");
            this.batteryPercentage = ((Integer)var1).intValue();
        } else if(var2.equals(this.batteryConnectionStateKey)) {
            this.batteryConnectionState = (BatteryConnectionState)var1;
        } else if(var2.equals(this.batteryRemainFlightKey)) {
            this.batteryWarningLevel = (BatteryThresholdBehavior)var1;
        } else if(var2.equals(this.batteryNeedToGoHomeKey)) {
            this.goHomeBattery = ((Integer)var1).intValue();
        }

        DJILog.d("DULBatteryWidget", "Battery percentage before call update is " + this.batteryPercentage);
        this.updateCurrentResources();
    }

    private void updateCurrentResources() {
        if(this.batteryConnectionState == BatteryConnectionState.NORMAL) {
            if(this.batteryPercentage > this.goHomeBattery && this.batteryWarningLevel != BatteryThresholdBehavior.GO_HOME) {
                if(this.batteryWarningLevel == BatteryThresholdBehavior.LAND_IMMEDIATELY) {
                    this.currentBatteryIconId = R.drawable.ic_topbar_battery_thunder;
                    this.currentTextColorId = R.color.red;
                    DJILog.d("DULBatteryWidget", "Battery warning level is 2");
                } else {
                    this.currentBatteryIconId = R.drawable.ic_topbar_battery_nor;
                    this.currentTextColorId = R.color.green_battery;
                }
            } else {
                this.currentBatteryIconId = R.drawable.ic_topbar_battery_dangerous;
                this.currentTextColorId = R.color.red;
                DJILog.d("DULBatteryWidget", "Battery percentage less than goHomeBattery or warning level is 1" + this.batteryPercentage + " " + this.goHomeBattery + " " + this.batteryWarningLevel);
            }
        } else {
            this.currentBatteryIconId = R.drawable.ic_topbar_battery_error;
            if(this.batteryPercentage > this.goHomeBattery && this.batteryWarningLevel != BatteryThresholdBehavior.GO_HOME && this.batteryWarningLevel != BatteryThresholdBehavior.LAND_IMMEDIATELY) {
                this.currentTextColorId = R.color.green_battery;
            } else {
                this.currentTextColorId = R.color.red;
                DJILog.d("DULBatteryWidget", "Battery error and battery percentage is less than goHomeBattery or level is 1 or 2");
            }
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.batteryEnergyRemainPercentageKey)) {
            this.onBatteryPercentageChange(this.batteryPercentage);
        } else if(var1.equals(this.batteryConnectionStateKey)) {
            this.onBatteryConnectionStateChange(this.batteryConnectionState);
        } else if(var1.equals(this.batteryRemainFlightKey)) {
            this.onRemainingBatteryStateChange(this.batteryWarningLevel);
        } else {
            this.updateUIResources();
        }
    }
}
