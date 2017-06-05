package dji.midware.ui.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import dji.midware.R;

/**
 * Created by jack_xie on 17-6-1.
 */

public class SlideAndFillSeekBar extends SeekBar {
    private Paint a;
    private Paint b;
    private Paint c;
    private OnSeekBarChangeListener d;
    private int e;
    private int f;
    private int g;
    private int h;

    public SlideAndFillSeekBar(Context var1) {
        this(var1, (AttributeSet)null);
    }

    public SlideAndFillSeekBar(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public SlideAndFillSeekBar(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.e = 0;
        this.a();
        this.a(var1);
    }

    private void a() {
        OnSeekBarChangeListener var1 = new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
                SlideAndFillSeekBar.this.e = var2;
                if(SlideAndFillSeekBar.this.d != null) {
                    SlideAndFillSeekBar.this.d.onProgressChanged(SlideAndFillSeekBar.this, var2, var3);
                }

            }

            public void onStartTrackingTouch(SeekBar var1) {
                if(SlideAndFillSeekBar.this.d != null) {
                    SlideAndFillSeekBar.this.d.onStartTrackingTouch(SlideAndFillSeekBar.this);
                }

            }

            public void onStopTrackingTouch(SeekBar var1) {
                if(SlideAndFillSeekBar.this.d != null) {
                    SlideAndFillSeekBar.this.d.onStopTrackingTouch(SlideAndFillSeekBar.this);
                }

            }
        };
        super.setOnSeekBarChangeListener(var1);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener var1) {
        this.d = var1;
    }

    private void a(Context var1) {
        this.f = var1.getResources().getColor(R.color.gray_highlight);
        this.g = var1.getResources().getColor(R.color.white);
        this.h = var1.getResources().getColor(R.color.green);
        this.a = new Paint(1);
        this.a.setColor(this.h);
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setStrokeWidth((float)this.getMeasuredHeight());
        this.a.setStrokeCap(Paint.Cap.ROUND);
        this.c = new Paint(1);
        this.c.setColor(this.h);
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setStrokeWidth((float)this.getMeasuredHeight());
        this.c.setStrokeCap(Paint.Cap.ROUND);
        this.b = new Paint(1);
        this.b.setColor(this.g);
        this.b.setStyle(Paint.Style.STROKE);
        this.b.setStrokeWidth((float)this.getMeasuredHeight());
        this.b.setStrokeCap(Paint.Cap.ROUND);
    }

    protected synchronized void onDraw(Canvas var1) {
        Drawable var2 = this.getThumb();
        Rect var3 = var2.getBounds();
        this.a.setStrokeWidth((float)var2.getIntrinsicHeight());
        this.c.setStrokeWidth((float)var2.getIntrinsicHeight());
        this.b.setStrokeWidth((float)var2.getIntrinsicHeight());
        if(this.e > 0) {
            var1.drawLine((float)(var2.getIntrinsicWidth() / 2), (float)var3.centerY(), (float)var3.centerX(), (float)var3.centerY(), this.a);
            var1.drawPoint((float)(var2.getIntrinsicWidth() / 2), (float)var3.centerY(), this.c);
        }

        if(this.e != 0 && this.e != 100) {
            this.b.setColor(this.f);
        } else {
            this.b.setColor(this.g);
        }

        var1.drawPoint((float)var3.centerX(), (float)var3.centerY(), this.b);
    }
}
