package dji.midware.ui.antistatic.spinnerwheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.example.animatorlibrary.animation.Animator;
import com.example.animatorlibrary.animation.ObjectAnimator;
import dji.midware.R;

/**
 * Created by jack_xie on 17-5-31.
 */

public abstract class AntiSpinnerB extends AntiSpinnerA {
    private static int y = -1;
    private final String z;
    protected int numM;
    protected int numN;
    protected int numO;
    protected int numP;
    protected int numQ;
    protected Drawable drawableR;
    protected Paint paintS;
    protected Paint paintT;
    protected Animator animatorU;
    protected Animator animatorV;
    protected Bitmap bitmapW;
    protected Bitmap bitmapX;

    public AntiSpinnerB(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.z = AntiSpinnerB.class.getName() + " #" + ++y;
    }

    protected void a(AttributeSet var1, int var2) {
        super.a(var1, var2);
        TypedArray var3 = this.getContext().obtainStyledAttributes(var1, R.styleable.AbstractWheelView, var2, 0);
        this.numM = var3.getInt(R.styleable.AbstractWheelView_itemsDimmedAlpha, 50);
        this.numN = var3.getInt(R.styleable.AbstractWheelView_selectionDividerActiveAlpha, 70);
        this.numO = var3.getInt(R.styleable.AbstractWheelView_selectionDividerDimmedAlpha, 70);
        this.numP = var3.getInt(R.styleable.AbstractWheelView_itemOffsetPercent, 10);
        this.numQ = var3.getDimensionPixelSize(R.styleable.AbstractWheelView_itemsPadding, 10);
        this.drawableR = var3.getDrawable(R.styleable.AbstractWheelView_selectionDivider);
        var3.recycle();
    }

    protected void a(Context var1) {
        super.a(var1);
        this.animatorU = ObjectAnimator.ofFloat(this, "selectorPaintCoeff", new float[]{1.0F, 0.5F});
        this.animatorV = ObjectAnimator.ofInt(this, "separatorsPaintAlpha", new int[]{this.numN, this.numN});
        this.paintT = new Paint();
        this.paintT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.paintT.setAlpha(this.numO);
        this.paintS = new Paint();
        this.paintS.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    protected void bitmapA(int var1, int var2) {
        if(var1 == 0) {
            var1 = var2;
        }

        this.bitmapW = Bitmap.createBitmap(var1, var2, Bitmap.Config.ARGB_8888);
        this.bitmapX = Bitmap.createBitmap(var1, var2, Bitmap.Config.ARGB_8888);
        this.setSelectorPaintCoeff(0.4F);
    }

    public void setSeparatorsPaintAlpha(int var1) {
        this.paintT.setAlpha(var1);
        this.invalidate();
    }

    public abstract void setSelectorPaintCoeff(float var1);

    public void setSelectionDivider(Drawable var1) {
        this.drawableR = var1;
    }

    protected void spinnerB() {  //
        this.animatorU.cancel();
        this.animatorV.cancel();
        this.setSelectorPaintCoeff(1.0F);
        this.setSeparatorsPaintAlpha(this.numN);
    }

    protected void spinnerC() {   //spinnerC
        super.spinnerC();
        this.a(750L);
        this.b(750L);
    }

    protected void spinnerD() {  //spinnerD
        this.a(500L);
        this.b(500L);
    }

    private void a(long var1) {
        this.animatorU.setDuration(var1);
        this.animatorU.start();
    }

    private void b(long var1) {
        this.animatorV.setDuration(var1);
        this.animatorV.start();
    }

    protected abstract void k();

    protected void onDraw(Canvas var1) {
        super.onDraw(var1);
        if(anSpinner_c != null && anSpinner_c.b() > 0) {
            if(this.antSpj()) {
                this.k();
            }

            this.f();
            this.a(var1);
        }

    }

    protected abstract void a(Canvas var1);
}
