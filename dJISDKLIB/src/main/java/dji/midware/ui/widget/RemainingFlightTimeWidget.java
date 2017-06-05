package dji.midware.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;

import dji.keysdk.BatteryKey;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.ProductKey;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class RemainingFlightTimeWidget extends DULFrameLayout {
    private static final DJIKey CONNECTIVITY_KEY = ProductKey.create("Connection");
    private static final DJIKey CHARGE_REMAINING_KEY = BatteryKey.create("ChargeRemainingInPercent");
    private static final DJIKey BATTERY_NEEDED_TO_LAND_KEY = FlightControllerKey.create("CurrentLandImmediatelyBattery");
    private static final DJIKey BATTERY_NEEDED_TO_GO_HOME_KEY = FlightControllerKey.create("BatteryPercentageNeededToGoHome");
    private static final DJIKey LAND_IMMEDIATELY_BATTERY_THRESHOLD_KEY = FlightControllerKey.create("LandImmediatelyBatteryThreshold");
    private static final DJIKey GO_HOME_BATTERY_THRESHOLD_KEY = FlightControllerKey.create("GoHomeBatteryThreshold");
    private static final DJIKey REMAINING_FLIGHT_TIME_KEY = FlightControllerKey.create("RemainingFlightTime");
    private Paint redPaint;
    private Paint greenPaint;
    private Paint yellowPaint;
    private Paint flightTimeRoundedWhiteBackgroundPaint;
    private Paint flightTimeTextPaint;
    private Paint whiteDotPaint;
    private Paint homeYellowBackgroundPaint;
    private Paint homeLetterPaint;
    private static final String HOME_LETTER = "H";
    private static final String FLIGHT_TIME_FORMAT_STRING = "%02d:%02d";
    private float redPercentage;
    private float greenPercentage;
    private float yellowPercentage;
    private float whiteDot1Percentage;
    private float whiteDot2Percentage;
    private float viewHeight;
    private float usableViewWidth;
    private float homeLetterWidth;
    private String flightTimeText;
    private boolean isConnected;
    private boolean shouldRedraw;
    Rect flightTimeTextBounds;
    Rect homeLetterBounds;

    public RemainingFlightTimeWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public RemainingFlightTimeWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public RemainingFlightTimeWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.redPercentage = 0.0F;
        this.greenPercentage = 0.0F;
        this.yellowPercentage = 0.0F;
        this.whiteDot1Percentage = 0.0F;
        this.whiteDot2Percentage = 0.0F;
        this.viewHeight = 0.0F;
        this.usableViewWidth = 0.0F;
        this.flightTimeText = "--:--";
        this.shouldRedraw = false;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        UiDA.b();
        this.initPaint();
    }

    protected UiCBA_B getWidgetAppearances() {
        return null;
    }

    public void initKey() {
        this.addDependentKey(CONNECTIVITY_KEY);
        this.addDependentKey(CHARGE_REMAINING_KEY);
        this.addDependentKey(BATTERY_NEEDED_TO_LAND_KEY);
        this.addDependentKey(BATTERY_NEEDED_TO_GO_HOME_KEY);
        this.addDependentKey(LAND_IMMEDIATELY_BATTERY_THRESHOLD_KEY);
        this.addDependentKey(GO_HOME_BATTERY_THRESHOLD_KEY);
        this.addDependentKey(REMAINING_FLIGHT_TIME_KEY);
    }

    private void initPaint() {
        this.redPaint = new Paint();
        this.redPaint.setColor(-65536);
        this.redPaint.setStyle(Paint.Style.STROKE);
        this.redPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.greenPaint = new Paint();
        this.greenPaint.setColor(-16711936);
        this.greenPaint.setStyle(Paint.Style.STROKE);
        this.greenPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.yellowPaint = new Paint();
        this.yellowPaint.setColor(-256);
        this.yellowPaint.setStyle(Paint.Style.STROKE);
        this.yellowPaint.setStrokeCap(Paint.Cap.SQUARE);
        this.flightTimeRoundedWhiteBackgroundPaint = new Paint(1);
        this.flightTimeRoundedWhiteBackgroundPaint.setColor(-1);
        this.flightTimeRoundedWhiteBackgroundPaint.setStyle(Paint.Style.STROKE);
        this.flightTimeRoundedWhiteBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        this.flightTimeTextPaint = new Paint(1);
        this.flightTimeTextPaint.setColor(-16777216);
        this.flightTimeTextPaint.setStyle(Paint.Style.FILL);
        this.whiteDotPaint = new Paint(1);
        this.whiteDotPaint.setColor(-1);
        this.whiteDotPaint.setStyle(Paint.Style.STROKE);
        this.whiteDotPaint.setStrokeCap(Paint.Cap.ROUND);
        this.homeLetterPaint = new Paint(1);
        this.homeLetterPaint.setColor(-16777216);
        this.homeLetterPaint.setStyle(Paint.Style.FILL);
        this.homeLetterPaint.setTypeface(Typeface.create(Typeface.DEFAULT, 1));
        this.homeYellowBackgroundPaint = new Paint(1);
        this.homeYellowBackgroundPaint.setColor(-256);
        this.homeYellowBackgroundPaint.setStyle(Paint.Style.STROKE);
        this.homeYellowBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    protected synchronized void onDraw(Canvas var1) {
        if(this.viewHeight == 0.0F) {
            this.viewHeight = (float)this.getHeight();
            this.usableViewWidth = (float)this.getWidth() - this.viewHeight / 2.0F;
            this.redPaint.setStrokeWidth(this.viewHeight / 6.0F);
            this.greenPaint.setStrokeWidth(this.viewHeight / 6.0F);
            this.yellowPaint.setStrokeWidth(this.viewHeight / 6.0F);
            this.flightTimeRoundedWhiteBackgroundPaint.setStrokeWidth(this.viewHeight);
            this.whiteDotPaint.setStrokeWidth(this.viewHeight / 2.4F);
            this.homeYellowBackgroundPaint.setStrokeWidth(this.viewHeight / 1.6F);
            this.flightTimeTextBounds = new Rect();
            this.flightTimeTextPaint.setTextSize(this.viewHeight / 1.5F);
            this.homeLetterPaint.setTextSize(this.viewHeight / 2.5F);
            this.homeLetterBounds = new Rect();
            this.homeLetterWidth = this.homeLetterPaint.measureText("H");
            this.homeLetterPaint.getTextBounds("H", 0, 1, this.homeLetterBounds);
        } else if(this.isConnected) {
            if(this.redPercentage + this.yellowPercentage + this.greenPercentage > 100.0F) {
                this.greenPercentage = 100.0F - (this.redPercentage + this.yellowPercentage);
            }

            var1.drawLine(this.usableViewWidth * this.yellowPercentage / 100.0F, this.viewHeight / 2.0F, this.usableViewWidth * this.greenPercentage / 100.0F, this.viewHeight / 2.0F, this.greenPaint);
            var1.drawLine(this.usableViewWidth * this.redPercentage / 100.0F, this.viewHeight / 2.0F, this.usableViewWidth * this.yellowPercentage / 100.0F, this.viewHeight / 2.0F, this.yellowPaint);
            var1.drawLine(0.0F, this.viewHeight / 2.0F, this.usableViewWidth * this.redPercentage / 100.0F, this.viewHeight / 2.0F, this.redPaint);
            if(this.whiteDot1Percentage <= this.greenPercentage) {
                var1.drawPoint(this.usableViewWidth * this.whiteDot1Percentage / 100.0F, this.viewHeight / 2.0F, this.whiteDotPaint);
            }

            if(this.whiteDot2Percentage <= this.greenPercentage) {
                var1.drawPoint(this.usableViewWidth * this.whiteDot2Percentage / 100.0F, this.viewHeight / 2.0F, this.whiteDotPaint);
            }

            if(this.yellowPercentage != 0.0F || this.redPercentage != 0.0F) {
                var1.drawPoint(this.usableViewWidth * this.yellowPercentage / 100.0F, this.viewHeight / 2.0F, this.homeYellowBackgroundPaint);
                var1.drawText("H", this.usableViewWidth * this.yellowPercentage / 100.0F - this.homeLetterWidth / 2.0F, this.viewHeight / 2.0F + (float)this.homeLetterBounds.height() / 2.0F, this.homeLetterPaint);
            }

            if(this.greenPercentage > this.yellowPercentage) {
                this.flightTimeTextPaint.getTextBounds(this.flightTimeText, 0, 1, this.flightTimeTextBounds);
                float var2 = this.flightTimeTextPaint.measureText(this.flightTimeText);
                this.flightTimeRoundedWhiteBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);
                var1.drawPoint(this.usableViewWidth * this.greenPercentage / 100.0F - var2, this.viewHeight / 2.0F, this.flightTimeRoundedWhiteBackgroundPaint);
                var1.drawPoint(this.usableViewWidth * this.greenPercentage / 100.0F, this.viewHeight / 2.0F, this.flightTimeRoundedWhiteBackgroundPaint);
                var1.drawLine(this.usableViewWidth * this.greenPercentage / 100.0F - var2, this.viewHeight / 2.0F, this.usableViewWidth * this.greenPercentage / 100.0F, this.viewHeight / 2.0F, this.flightTimeRoundedWhiteBackgroundPaint);
                var1.drawText(this.flightTimeText, this.usableViewWidth * this.greenPercentage / 100.0F - var2, this.viewHeight / 2.0F + (float)this.flightTimeTextBounds.height() / 2.0F, this.flightTimeTextPaint);
            }
        }

    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(CHARGE_REMAINING_KEY)) {
            if((float)((Integer)var1).intValue() != this.greenPercentage) {
                this.shouldRedraw = true;
                this.greenPercentage = (float)((Integer)var1).intValue();
            }
        } else if(var2.equals(BATTERY_NEEDED_TO_LAND_KEY)) {
            if((float)((Integer)var1).intValue() != this.redPercentage) {
                this.shouldRedraw = true;
                this.redPercentage = (float)((Integer)var1).intValue();
            }
        } else if(var2.equals(BATTERY_NEEDED_TO_GO_HOME_KEY)) {
            if((float)((Integer)var1).intValue() != this.yellowPercentage) {
                this.shouldRedraw = true;
                this.yellowPercentage = (float)((Integer)var1).intValue();
            }
        } else if(var2.equals(LAND_IMMEDIATELY_BATTERY_THRESHOLD_KEY)) {
            if((float)((Integer)var1).intValue() != this.whiteDot1Percentage) {
                this.shouldRedraw = true;
                this.whiteDot1Percentage = (float)((Integer)var1).intValue();
            }
        } else if(var2.equals(GO_HOME_BATTERY_THRESHOLD_KEY)) {
            if((float)((Integer)var1).intValue() != this.whiteDot2Percentage) {
                this.shouldRedraw = true;
                this.whiteDot2Percentage = (float)((Integer)var1).intValue();
            }
        } else if(var2.equals(REMAINING_FLIGHT_TIME_KEY)) {
            String var3 = String.format("%02d:%02d", new Object[]{Integer.valueOf(((Integer)var1).intValue() / 60), Integer.valueOf(((Integer)var1).intValue() % 60)});
            if(!var3.equals(this.flightTimeText)) {
                this.shouldRedraw = true;
                this.flightTimeText = var3;
            }
        } else if(var2.equals(CONNECTIVITY_KEY) && ((Boolean)var1).booleanValue() != this.isConnected) {
            this.shouldRedraw = true;
            this.isConnected = ((Boolean)var1).booleanValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.shouldRedraw) {
            this.shouldRedraw = false;
            this.invalidate();
        }

    }
}
