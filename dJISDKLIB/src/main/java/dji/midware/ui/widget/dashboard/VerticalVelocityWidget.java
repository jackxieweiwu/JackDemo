package dji.midware.ui.widget.dashboard;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;

import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.midware.R;
import dji.midware.ui.base.UiBaseHView;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDH;

/**
 * Created by jack_xie on 17-6-1.
 */

public class VerticalVelocityWidget extends UiBaseHView {
    private static final String TAG = "VerticalVelocityWidget";
    private DJIKey aircraftVelocityZKey;

    public VerticalVelocityWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public VerticalVelocityWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public VerticalVelocityWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.setDecimalFormat("##.#");
        UiDA.b();
    }

    protected String getTitle() {
        Resources var1 = this.getResources();
        return var1.getString(R.string.vertical_speed_title);
    }

    protected String getUnitString() {
        return UiDH.b(value_Unit_Type).toUpperCase();
    }

    protected double getValueFromMetricValue() {
        return (double)UiDH.b(this.metricValue, value_Unit_Type);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setMetricMaxValue(20.0F);
        this.setMetricMinValue(-20.0F);
    }

    public void initKey() {
        this.aircraftVelocityZKey = FlightControllerKey.create("VelocityZ");
        this.addDependentKey(this.aircraftVelocityZKey);
    }

    private void updateVerticalSpeed(float var1) {
        this.metricValue = var1;
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.aircraftVelocityZKey)) {
            this.updateVerticalSpeed(((Float)var1).floatValue());
        }

    }
}
