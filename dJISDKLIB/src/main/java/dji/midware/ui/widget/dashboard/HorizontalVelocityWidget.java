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

public class HorizontalVelocityWidget extends UiBaseHView {
    private float speedX;
    private float speedY;
    private DJIKey aircraftVelocityXKey;
    private DJIKey aircraftVelocityYKey;

    public HorizontalVelocityWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public HorizontalVelocityWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public HorizontalVelocityWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.speedX = 0.0F;
        this.speedY = 0.0F;
        this.setDecimalFormat("##.#");
        UiDA.b();
    }

    protected String getTitle() {
        Resources var1 = this.getResources();
        return var1.getString(R.string.horizontal_speed_title);
    }

    protected String getUnitString() {
        return UiDH.b(value_Unit_Type).toUpperCase();
    }

    protected double getValueFromMetricValue() {
        return (double)UiDH.b(this.metricValue, value_Unit_Type);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setMetricMinValue(0.0F);
        this.setMetricMaxValue(50.0F);
    }

    public void initKey() {
        this.aircraftVelocityXKey = FlightControllerKey.create("VelocityX");
        this.aircraftVelocityYKey = FlightControllerKey.create("VelocityY");
        this.addDependentKey(this.aircraftVelocityXKey);
        this.addDependentKey(this.aircraftVelocityYKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.aircraftVelocityXKey)) {
            this.speedX = ((Float)var1).floatValue();
        } else if(var2.equals(this.aircraftVelocityYKey)) {
            this.speedY = ((Float)var1).floatValue();
        }

        this.metricValue = (float)Math.sqrt((double)(this.speedX * this.speedX + this.speedY * this.speedY));
    }
}
