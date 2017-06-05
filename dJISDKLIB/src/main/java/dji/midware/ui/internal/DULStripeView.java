package dji.midware.ui.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import dji.midware.R;
import dji.midware.ui.d.UiDJ;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULStripeView extends View {
    final float a = 9.0F;
    final float b = 4.0F;
    final float c = 6.0F;
    final float d = 2.0F;
    final float e = 1.0F;
    final int f = 3;
    final int g = 3;
    private Bitmap h = null;
    private Bitmap i = null;
    private Bitmap j = null;
    private Bitmap k = null;
    private final Paint l = new Paint();
    private int m;
    private int n = 3;
    private int o = 3;
    private int p;
    private float q = 1.0F;
    private float r = 1.0F;

    public DULStripeView(Context var1) {
        super(var1);
    }

    public DULStripeView(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    public DULStripeView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public int getSelectedPosition() {
        return this.m;
    }

    public void setSelectedPosition(int var1) {
        if(this.m != var1) {
            this.m = var1;
            this.postInvalidate();
        }

    }

    public void setMaxUnit(int var1) {
        this.n = var1;
    }

    public int getMaxUnit() {
        return this.n;
    }

    public void setMaxNumberOfBarsPerUnit(int var1) {
        this.o = var1;
    }

    public int getMaxNumberOfBarsPerUnit() {
        return this.o;
    }

    public int getDesignedWidth() {
        int var1 = this.getMaxUnit() * this.getMaxNumberOfBarsPerUnit() * 2 + 1;
        float var2 = (float)var1 * 4.0F;
        return UiDJ.a(this.getContext(), var2);
    }

    public int getDesignedHeight() {
        return UiDJ.a(this.getContext(), 9.0F);
    }

    public void setZeroPosition(int var1) {
        this.p = var1;
    }

    private Bitmap a(boolean var1, boolean var2) {
        Bitmap var3 = Bitmap.createBitmap(UiDJ.a(this.getContext(), 4.0F), UiDJ.a(this.getContext(), 9.0F), Bitmap.Config.ARGB_8888);
        var3.eraseColor(0);
        Canvas var4 = new Canvas(var3);
        Paint var5 = new Paint();
        var5.setAntiAlias(true);
        if(var2) {
            var5.setColor(this.getResources().getColor(R.color.white));
        } else {
            var5.setColor(this.getResources().getColor(R.color.white_half));
        }

        var5.setStyle(Paint.Style.FILL);
        if(var1) {
            var4.drawCircle(UiDJ.b(this.getContext(), 2.0F), UiDJ.b(this.getContext(), 1.0F), UiDJ.b(this.getContext(), 1.0F), var5);
            var4.drawRect(UiDJ.b(this.getContext(), 1.0F), UiDJ.b(this.getContext(), 3.0F), UiDJ.b(this.getContext(), 3.0F), UiDJ.b(this.getContext(), 9.0F), var5);
        } else {
            var4.drawRect(UiDJ.b(this.getContext(), 1.0F), UiDJ.b(this.getContext(), 4.5F), UiDJ.b(this.getContext(), 3.0F), UiDJ.b(this.getContext(), 9.0F), var5);
        }

        return var3;
    }

    @SuppressLint("MissingSuperCall")
    protected void onFinishInflate() {
        if(!this.isInEditMode()) {
            this.h = this.a(false, false);
            this.i = this.a(false, true);
            this.j = this.a(true, false);
            this.k = this.a(true, true);
            this.l.setAntiAlias(true);
            this.l.setFilterBitmap(true);
            this.setWillNotDraw(false);
        }
    }

    private Bitmap a(int var1, int var2, Bitmap var3, boolean var4) {
        Bitmap var5 = var3;
        int var6;
        if(var4) {
            var6 = this.p - var1;
        } else {
            var6 = this.p + var1;
        }

        if(this.m < this.p && var4) {
            if(var6 >= this.m) {
                if(var3 == this.h) {
                    var5 = this.i;
                } else if(var3 == this.j) {
                    var5 = this.k;
                }
            }
        } else if(this.m > this.p && !var4 && var6 <= this.m) {
            if(var3 == this.h) {
                var5 = this.i;
            } else if(var3 == this.j) {
                var5 = this.k;
            }
        }

        return var5;
    }

    protected void onDraw(Canvas var1) {
        float var2 = 0.0F;
        float var3 = (float)this.k.getWidth();
        float var4 = (float)this.k.getHeight();
        int var5 = this.getMaxNumberOfBarsPerUnit();
        float var6 = var3 * this.q;
        float var7 = var4 * this.r;
        int var8 = this.getMaxNumberOfBarsPerUnit() * this.getMaxUnit();
        Rect var9 = new Rect(0, 0, (int)var3, (int)var4);
        new Rect((int)var2, 0, (int)(var2 + var6), (int)var7);

        Rect var10;
        int var11;
        int var12;
        Bitmap var13;
        for(var11 = 0; var11 < this.getMaxUnit(); ++var11) {
            for(var12 = 0; var12 < var5; ++var12) {
                var13 = this.a(var8 - var11 * var5 - var12, var8, var12 == 0?this.j:this.h, true);
                var10 = new Rect((int)var2, 0, (int)(var2 + var6), (int)var7);
                var1.drawBitmap(var13, var9, var10, this.l);
                var2 += var6;
            }
        }

        var10 = new Rect((int)var2, 0, (int)(var2 + var6), (int)var7);
        var1.drawBitmap(this.k, var9, var10, this.l);
        var2 += var3;

        for(var11 = 0; var11 < this.getMaxUnit(); ++var11) {
            for(var12 = 0; var12 < var5; ++var12) {
                var13 = this.a(var11 * var5 + var12 + 1, var8, var12 + 1 != var5?this.h:this.j, false);
                var10 = new Rect((int)var2, 0, (int)(var2 + var6), (int)var7);
                var1.drawBitmap(var13, var9, var10, this.l);
                var2 += var6;
            }
        }

    }

    protected void onMeasure(int var1, int var2) {
        int var3 = MeasureSpec.getSize(var1);
        this.q = (float)var3 / (float)this.getDesignedWidth();
        int var4 = MeasureSpec.getSize(var2);
        this.r = (float)var4 / (float)this.getDesignedHeight();
        this.setMeasuredDimension(var3, var4);
    }
}
