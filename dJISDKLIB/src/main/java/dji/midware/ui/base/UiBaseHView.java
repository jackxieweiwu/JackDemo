package dji.midware.ui.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.text.ParseException;

import dji.log.DJILog;
import dji.midware.ui.d.UiDE;
import dji.midware.ui.d.UiDL;

/**
 * Created by jack_xie on 17-6-1.
 */

public abstract class UiBaseHView extends DULMetricWidget {
    private static final String svgPathUp = "M7.5,46.5 L14.5,46.5 L14.5,17.5 L7.5,46.5";
    private static final String svgPathDown = "M7.5,51.5 L14.5,51.5 L14.5,80.5 L7.5,51.5";
    private Path goingUpPath = null;
    private Path goingDownPath = null;
    private static final int originalWidth = 140;
    private static final int originalHeight = 100;
    private int viewWidth;
    private int viewHeight;
    private Paint gaugePaint;
    private Paint fillPaint;
    private static final int startAngle = 90;
    private static final int totalAngle = 225;

    public UiBaseHView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.context = var1;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setWidgetStyle(a.a);
        this.setWillNotDraw(false);
        this.initFillPaint();
    }

    private void initFillPaint() {
        this.gaugePaint = new Paint();
        this.gaugePaint.setStyle(Paint.Style.STROKE);
        this.fillPaint = new Paint();
        this.fillPaint.setAntiAlias(true);
        this.fillPaint.setStyle(Paint.Style.FILL);
        this.fillPaint.setColor(-16711936);
    }

    protected void onSizeChanged(int var1, int var2, int var3, int var4) {
        super.onSizeChanged(var1, var2, var3, var4);
        this.viewWidth = var1;
        this.viewHeight = var2;
        this.buildPathData();
    }

    private void buildPathData() {
        UiDL var1 = this.getPathParser();

        try {
            this.goingDownPath = var1.a("M7.5,51.5 L14.5,51.5 L14.5,80.5 L7.5,51.5");
            this.goingUpPath = var1.a("M7.5,46.5 L14.5,46.5 L14.5,17.5 L7.5,46.5");
        } catch (ParseException var3) {
            DJILog.d("BatteryWidget", "Failed to add the cell paths");
        }

    }

    private UiDL getPathParser() {
        UiDE.a var1 = new UiDE.a();
        return var1.a(140).b(100).c(this.viewWidth).d(this.viewHeight).a();
    }

    protected void onDraw(Canvas var1) {
        super.onDraw(var1);
        if(this.widgetStyle == a.b) {
            this.drawAnimation(var1);
        }

    }

    private void drawAnimation(Canvas var1) {
        int var2 = this.getHeight() / 2;
        int var3 = this.getWidth() / 2;
        int var4 = dip2px(this.context, 43.0F);
        int var5 = dip2px(this.context, 12.0F);
        RectF var6 = new RectF((float)(var3 - var4), (float)(var2 - var4), (float)(var3 + var4), (float)(var2 + var4));
        this.gaugePaint.setStrokeWidth((float)var5);
        this.gaugePaint.setColor(-7829368);
        var1.drawArc(var6, 90.0F, 225.0F, false, this.gaugePaint);
        float var7 = Math.abs(this.metricValue);
        if(var7 > this.metricMaxValue) {
            var7 = this.metricMaxValue;
        }

        this.gaugePaint.setColor(-16711936);
        var1.drawArc(var6, 90.0F, 225.0F * var7 / this.metricMaxValue, false, this.gaugePaint);
        if(this.metricMinValue < 0.0F) {
            this.drawUpOrDown(var1);
        }

    }

    public static int dip2px(Context var0, float var1) {
        float var2 = var0.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F);
    }

    private void drawUpOrDown(Canvas var1) {
        if(this.metricValue < 0.0F) {
            var1.drawPath(this.goingDownPath, this.fillPaint);
        } else if(this.metricValue > 0.0F) {
            var1.drawPath(this.goingUpPath, this.fillPaint);
        }

    }
}
