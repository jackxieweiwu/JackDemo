package dji.midware.ui.widget.dashboard;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.midware.R;
import dji.midware.ui.base.DULMetricWidget;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDH;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AltitudeWidget extends DULMetricWidget {
    private DJIKey aircraftAltitudeKey;

    public AltitudeWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public AltitudeWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public AltitudeWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.setDecimalFormat("####");
        UiDA.b();
    }

    protected String getTitle() {
        Resources var1 = this.getResources();
        return var1.getString(R.string.altitude_title);
    }

    protected String getUnitString() {
        return UiDH.a(value_Unit_Type).toUpperCase();
    }

    protected double getValueFromMetricValue() {
        return (double)UiDH.a(this.metricValue, value_Unit_Type);
    }

    public void initKey() {
        this.aircraftAltitudeKey = FlightControllerKey.create("Altitude");
        this.addDependentKey(this.aircraftAltitudeKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.aircraftAltitudeKey)) {
            this.metricValue = ((Float)var1).floatValue();
        }

    }
}
