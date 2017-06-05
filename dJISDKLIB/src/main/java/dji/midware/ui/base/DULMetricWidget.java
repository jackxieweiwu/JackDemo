package dji.midware.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.DecimalFormat;

import dji.keysdk.DJIKey;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_C;
import dji.midware.ui.d.UiDH;

/**
 * Created by jack_xie on 17-6-1.
 */

public abstract class DULMetricWidget extends DULFrameLayout {
    static final String TAG = "DULMetricWidget";
    static final String NOT_A_NUMBER = "NaN";
    protected static UiDH.dhEnumA value_Unit_Type;
    protected a widgetStyle;
    private UiCBA_C widgetAppearances;
    protected TextView titleTextView;
    protected TextView valueTextView;
    protected TextView unitTextView;
    protected float metricMaxValue;
    protected float metricMinValue;
    protected float metricValue;
    private DecimalFormat decimalFormat;
    private static String STATE_METRICS_VALUE;
    private static String STATE_SUPER_CLASS;

    public static void setWidgetUnitType(UiDH.dhEnumA var0) {
        value_Unit_Type = var0;
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA_C();
        }

        return this.widgetAppearances;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.titleTextView = (TextView)this.getViewById(R.id.textview_metrics_title);
        this.valueTextView = (TextView)this.getViewById(R.id.textview_metrics_value);
        this.unitTextView = (TextView)this.getViewById(R.id.textview_metrics_unit);
        if(this.titleTextView != null) {
            this.titleTextView.setText(this.getTitle());
        }

        if(this.unitTextView != null) {
            this.unitTextView.setText(this.getUnitString());
        }

        if(this.valueTextView != null) {
            this.valueTextView.setText("0");
        }

    }

    public DULMetricWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.widgetStyle = a.a;
        this.decimalFormat = new DecimalFormat("####.##");
    }

    public void setWidgetStyle(a var1) {
        this.widgetStyle = var1;
    }

    public void setMetricMaxValue(float var1) {
        this.metricMaxValue = var1;
    }

    public void setMetricMinValue(float var1) {
        this.metricMinValue = var1;
    }

    protected String getTitle() {
        return null;
    }

    protected double getValueFromMetricValue() {
        if(value_Unit_Type == UiDH.dhEnumA.a) {
            return (double)this.metricValue;
        } else {
            DJILog.d("DULMetricWidget", "Error: base class cannot help to convert the value, please check your code logic");
            return 0.0D;
        }
    }

    protected String getUnitString() {
        return null;
    }

    public void setDecimalFormat(String var1) {
        this.decimalFormat = new DecimalFormat(var1);
    }

    private String getValue() {
        double var1 = this.getValueFromMetricValue();
        return this.decimalFormat.format(var1);
    }

    protected Parcelable onSaveInstanceState() {
        Bundle var1 = new Bundle();
        var1.putParcelable(STATE_SUPER_CLASS, super.onSaveInstanceState());
        var1.putFloat(STATE_METRICS_VALUE, this.metricValue);
        return var1;
    }

    protected void onRestoreInstanceState(Parcelable var1) {
        if(var1 instanceof Bundle) {
            Bundle var2 = (Bundle)var1;
            super.onRestoreInstanceState(var2.getParcelable(STATE_SUPER_CLASS));
            this.metricValue = var2.getFloat(STATE_METRICS_VALUE);
        } else {
            super.onRestoreInstanceState(var1);
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.valueTextView != null) {
            String var2 = this.getValue();
            if(var2.equals("NaN")) {
                this.valueTextView.setText("0");
            } else {
                this.valueTextView.setText(this.getValue());
            }
        }

    }

    static {
        value_Unit_Type = UiDH.dhEnumA.a;
        STATE_METRICS_VALUE = "Matrics_Current_Value";
        STATE_SUPER_CLASS = "DJIMatricWidget";
    }

    public static enum a {
        a,
        b;

        private a() {
        }
    }
}
