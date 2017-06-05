package dji.midware.ui.internal.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import dji.midware.R;
import dji.midware.ui.d.UiDJ;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULVisualCompassView extends View {
    private int a = 100;
    private float b = 400.0F;
    private int c = 4;
    private int[] d = new int[]{5, 5, 5, 5, 5, 5, 5};
    private final Paint e = new Paint();
    private int f = 2013265919;
    private int g = 0;
    private final RectF h = new RectF();
    private boolean i = false;

    public DULVisualCompassView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a();
    }

    private void a() {
        if(!this.isInEditMode()) {
            this.e.setAntiAlias(true);
            this.e.setColor(this.getContext().getResources().getColor(R.color.white_transparent));
            this.e.setStyle(Paint.Style.STROKE);
            this.g = UiDJ.a(this.getContext(), 1.0F);
            if(this.g > 2) {
                this.g = 2;
            }

            this.e.setStrokeWidth((float)this.g);
        }
    }

    public void setInterval(int var1) {
        this.a = var1;
        this.postInvalidate();
    }

    public void setDistance(float var1) {
        if(this.b != var1) {
            this.b = var1;
            this.postInvalidate();
        }

    }

    public void setLineDistance(int var1) {
        this.c = var1 / this.a;
        this.postInvalidate();
    }

    public void setLines(int var1) {
        this.c = var1;
        this.postInvalidate();
    }

    public void setHasVisual(boolean var1) {
        this.postInvalidate();
    }

    private float a(float var1) {
        float var2 = (float)(this.a * this.c);
        float var3 = 0.0F;
        if(var1 > var2) {
            var3 = (float)(Math.log((double)(var1 / (float)(this.a * this.c))) / Math.log(2.0D));
            var3 -= (float)((int)var3);
        }

        return var3;
    }

    private int b(float var1) {
        int var2 = this.f;
        int var3 = Color.alpha(var2);
        var3 = (int)((float)var3 * (1.0F - var1));
        var2 = Color.argb(var3, Color.red(this.f), Color.green(this.f), Color.blue(this.f));
        return var2;
    }

    private void a(Canvas var1) {
        float var2 = this.a(this.b);
        int var3 = this.getWidth() - 2;
        float var4 = (float)var3 * 0.5F;
        float var5 = var4 + 1.0F;
        float var6 = var4 / (4.0F * (var2 + 1.0F));
        int var7 = this.b(var2);
        this.e.setStyle(Paint.Style.STROKE);
        this.e.setColor(this.f);
        var1.drawCircle(var5, var5, var4, this.e);

        for(int var9 = 1; (float)var9 * var6 < var4; ++var9) {
            if(var9 % 2 == 0) {
                this.e.setColor(this.f);
            } else {
                this.e.setColor(var7);
            }

            float var8 = (float)var9 * var6;
            var1.drawCircle(var5, var5, var8, this.e);
        }

    }

    protected void onDraw(Canvas var1) {
        super.onDraw(var1);
        this.c(var1);
    }

    private int a(int var1) {
        int var2 = -15998187;
        if(4 == var1) {
            var2 = -1442061547;
        } else if(3 == var1) {
            var2 = 1426842389;
        } else if(2 == var1) {
            var2 = 1439695387;
        } else if(1 == var1) {
            var2 = -1429208549;
        } else if(0 == var1) {
            var2 = -3145189;
        }

        return var2;
    }

    private void b(Canvas var1) {
        int var2 = this.getWidth() - 1;
        RectF var3 = this.h;
        this.e.setStyle(Paint.Style.FILL);

        for(int var4 = 0; var4 < 7; ++var4) {
            this.e.setColor(this.a(this.d[var4]));
            var3.set(1.0F, 1.0F, (float)var2, (float)var2);
            var1.drawArc(var3, -135.0F + (float)var4 * 12.857142F + 1.0F, 13.857142F, true, this.e);
        }

    }

    private void c(Canvas var1) {
        if(this.i) {
            this.b(var1);
        }

        this.a(var1);
    }
}
