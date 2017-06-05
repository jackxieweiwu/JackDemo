package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import dji.common.flightcontroller.GPSSignalLevel;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBU;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class GPSSignalWidget extends DULFrameLayout {
    private int currentSignalId;
    private String rtkEnabledStr;
    private ImageView gpsSignal;
    private TextView satelliteNumber;
    private TextView gpsType;
    private UiCAB widgetAppearances;
    private DJIKey gpsSignalStatusKey;
    private DJIKey rtkEnabledKey;
    private DJIKey satelliteCountKey;
    private GPSSignalLevel gpsSignalStatus;
    private boolean isRTKEnabled;
    private int satelliteCount;

    public GPSSignalWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onGPSSignalStatusChange(@Nullable GPSSignalLevel var1) {
        switch(var1.value()) {
            case 0:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_0;
                break;
            case 1:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_1;
                break;
            case 2:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_2;
                break;
            case 3:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_3;
                break;
            case 4:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_4;
                break;
            case 5:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_5;
                break;
            default:
                this.currentSignalId = R.drawable.ic_topbar_signal_level_0;
        }

        this.gpsSignal.setImageResource(this.currentSignalId);
    }

    @MainThread
    @Keep
    public void onRTKEnabled(boolean var1) {
        if(var1) {
            this.rtkEnabledStr = this.getContext().getString(R.string.gps_rtk_enable);
        } else {
            this.rtkEnabledStr = "";
        }

        this.gpsType.setText(this.rtkEnabledStr);
    }

    @MainThread
    @Keep
    public void onSatelliteNumberChange(int var1) {
        String var2 = String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(var1)});
        this.satelliteNumber.setText(var2);
    }

    @Keep
    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.gpsSignal = (ImageView)this.getViewById(R.id.imageview_gps_signal);
        this.satelliteNumber = (TextView)this.getViewById(R.id.textview_satellite_number);
        this.gpsType = (TextView)this.getViewById(R.id.textview_gps_type);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBU();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.gpsSignalStatusKey = FlightControllerKey.create("GPSSignalLevel");
        this.addDependentKey(this.gpsSignalStatusKey);
        this.rtkEnabledKey = FlightControllerKey.create("RTKEnabled");
        this.addDependentKey(this.rtkEnabledKey);
        this.satelliteCountKey = FlightControllerKey.create("SatelliteCount");
        this.addDependentKey(this.satelliteCountKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.gpsSignalStatusKey)) {
            this.gpsSignalStatus = (GPSSignalLevel)var1;
        } else if(var2.equals(this.rtkEnabledKey)) {
            this.isRTKEnabled = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.satelliteCountKey)) {
            this.satelliteCount = ((Integer)var1).intValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.gpsSignalStatusKey)) {
            this.onGPSSignalStatusChange(this.gpsSignalStatus);
        } else if(var1.equals(this.rtkEnabledKey)) {
            this.onRTKEnabled(this.isRTKEnabled);
        } else if(var1.equals(this.satelliteCountKey)) {
            this.onSatelliteNumberChange(this.satelliteCount);
        }

    }
}
