package dji.midware.ui.antistatic.spinnerwheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import dji.midware.R;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AntiSpinnerK extends AntiSpinnerB {
    private static int z = -1;
    protected int y;
    private int A;

    //no
    public AntiSpinnerK(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected void AntiSpinnerK(AttributeSet var1, int var2) {
        super.a(var1, var2);
        TypedArray var3 = this.getContext().obtainStyledAttributes(var1, R.styleable.WheelVerticalView, var2, 0);
        this.y = var3.getDimensionPixelSize(R.styleable.WheelVerticalView_selectionDividerHeight, 2);
        var3.recycle();
    }

    public void setSelectorPaintCoeff(float var1) {
        int var3 = this.getMeasuredHeight();
        int var4 = this.getItemDimension();
        float var5 = (1.0F - (float)var4 / (float)var3) / 2.0F;
        float var6 = (1.0F + (float)var4 / (float)var3) / 2.0F;
        float var7 = (float)this.numM * (1.0F - var1);
        float var8 = var7 + 255.0F * var1;
        LinearGradient var2;
        if(this.numberb == 2) {
            int var9 = Math.round(var8) << 24;
            int var10 = Math.round(var7) << 24;
            int[] var11 = new int[]{var10, var9, -16777216, -16777216, var9, var10};
            float[] var12 = new float[]{0.0F, var5, var5, var6, var6, 1.0F};
            var2 = new LinearGradient(0.0F, 0.0F, 0.0F, (float)var3, var11, var12, Shader.TileMode.CLAMP);
        } else {
            float var19 = (1.0F - (float)(var4 * 3) / (float)var3) / 2.0F;
            float var20 = (1.0F + (float)(var4 * 3) / (float)var3) / 2.0F;
            float var21 = 255.0F * var19 / var5;
            float var22 = var21 * var1;
            float var13 = var7 + var22;
            int var14 = Math.round(var8) << 24;
            int var15 = Math.round(var13) << 24;
            int var16 = Math.round(var22) << 24;
            int[] var17 = new int[]{0, var16, var15, var14, -16777216, -16777216, var14, var15, var16, 0};
            float[] var18 = new float[]{0.0F, var19, var19, var5, var5, var6, var6, var20, var20, 1.0F};
            var2 = new LinearGradient(0.0F, 0.0F, 0.0F, (float)var3, var17, var18, Shader.TileMode.CLAMP);
        }

        this.paintS.setShader(var2);
        this.invalidate();
    }

    @Override
    protected AntiSpinnerI getAntiSpinnerI(AntiSpinnerI.numberInterface var1) {
        return new AntiSpinnerJ(this.getContext(), var1);
    }

    protected float a(MotionEvent var1) {
        return var1.getY();
    }


    protected int getBaseDimension() {
        return this.getHeight();
    }

    protected int getItemDimension() {
        if(this.A != 0) {
            return this.A;
        } else if(this.linearLayout != null && this.linearLayout.getChildAt(0) != null) {
            this.A = this.linearLayout.getChildAt(0).getMeasuredHeight();
            return this.A;
        } else {
            return this.getBaseDimension() / this.numberb;
        }
    }

    protected void e() {
        if(this.linearLayout == null) {
            this.linearLayout = new LinearLayout(this.getContext());
            this.linearLayout.setOrientation(1);
        }

    }

    protected void f() {
        this.linearLayout.layout(0, 0, this.getMeasuredWidth() - 2 * this.numQ, this.getMeasuredHeight());
    }

    protected void k() {
        this.linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.linearLayout.measure(MeasureSpec.makeMeasureSpec(this.getWidth() - 2 * this.numQ, 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
    }

    protected void onMeasure(int var1, int var2) {
        int var3 = MeasureSpec.getMode(var1);
        int var4 = MeasureSpec.getMode(var2);
        int var5 = MeasureSpec.getSize(var1);
        int var6 = MeasureSpec.getSize(var2);
        this.antSpj();
        int var7 = this.d(var5, var3);
        int var8;
        if(var4 == 1073741824) {
            var8 = var6;
        } else {
            var8 = Math.max(this.getItemDimension() * (this.numberb - this.numP / 100), this.getSuggestedMinimumHeight());
            if(var4 == -2147483648) {
                var8 = Math.min(var8, var6);
            }
        }

        this.setMeasuredDimension(var7, var8);
    }

    private int d(int var1, int var2) {
        this.linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.linearLayout.measure(MeasureSpec.makeMeasureSpec(var1, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int var3 = this.linearLayout.getMeasuredWidth();
        if(var2 == 1073741824) {
            var3 = var1;
        } else {
            var3 += 2 * this.numQ;
            var3 = Math.max(var3, this.getSuggestedMinimumWidth());
            if(var2 == -2147483648 && var1 < var3) {
                var3 = var1;
            }
        }

        this.linearLayout.measure(MeasureSpec.makeMeasureSpec(var3 - 2 * this.numQ, 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
        return var3;
    }

    protected void a(Canvas var1) {
        var1.save();
        int var2 = this.getMeasuredWidth();
        int var3 = this.getMeasuredHeight();
        int var4 = this.getItemDimension();
        this.bitmapW.eraseColor(0);
        Canvas var5 = new Canvas(this.bitmapW);
        Canvas var6 = new Canvas(this.bitmapW);
        int var7 = (this.numbera - this.i) * var4 + (var4 - this.getHeight()) / 2;
        var5.translate((float)this.numQ, (float)(-var7 + this.numberg));
        this.linearLayout.draw(var5);
        this.bitmapX.eraseColor(0);
        Canvas var8 = new Canvas(this.bitmapX);
        if(this.drawableR != null) {
            int var9 = (this.getHeight() - var4 - this.y) / 2;
            int var10 = var9 + this.y;
            this.drawableR.setBounds(0, var9, var2, var10);
            this.drawableR.draw(var8);
            int var11 = var9 + var4;
            int var12 = var10 + var4;
            this.drawableR.setBounds(0, var11, var2, var12);
            this.drawableR.draw(var8);
        }

        var6.drawRect(0.0F, 0.0F, (float)var2, (float)var3, this.paintS);
        var8.drawRect(0.0F, 0.0F, (float)var2, (float)var3, this.paintT);
        var1.drawBitmap(this.bitmapW, 0.0F, 0.0F, (Paint)null);
        var1.drawBitmap(this.bitmapX, 0.0F, 0.0F, (Paint)null);
        var1.restore();
    }
}
