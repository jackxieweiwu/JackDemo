package dji.midware.ui.internal.compass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import dji.midware.R;
import dji.midware.ui.d.UiDJ;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULGimbalYawView extends View {
    private Paint a = null;
    private final RectF b = new RectF();
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private boolean h = false;
    private float i = 0.0F;
    private float j = 0.0F;
    private float k = 0.0F;
    private float l = 0.0F;
    private float m = 0.0F;
    private float n = 0.0F;

    public DULGimbalYawView(Context var1, AttributeSet var2) {
        super(var1, var2);
        if(!this.isInEditMode()) {
            this.a();
        }
    }

    public DULGimbalYawView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        if(!this.isInEditMode()) {
            this.a();
        }
    }

    public DULGimbalYawView(Context var1) {
        super(var1);
        if(!this.isInEditMode()) {
            this.a();
        }
    }

    private void a() {
        if(this.isInEditMode()) {
            ;
        }

        Context var1 = this.getContext();
        this.c = UiDJ.a(var1, 1.0F);
        this.a = new Paint();
        this.a.setStrokeWidth((float)this.c);
        this.a.setStyle(Paint.Style.STROKE);
        this.a.setAntiAlias(true);
        this.e = var1.getResources().getColor(R.color.blue_light);
        this.f = var1.getResources().getColor(R.color.red);
        this.g = var1.getResources().getColor(R.color.red_dark);
    }

    public void setYaw(float var1) {
        if(this.i != var1) {
            this.i = var1;
            this.j = var1 >= 0.0F?var1:0.0F - var1;
            if(this.j >= 190.0F) {
                this.h = true;
            } else if(this.j < 90.0F) {
                this.h = false;
            }

            this.k = 0.0F;
            this.l = 0.0F;
            this.m = 0.0F;
            this.n = 30.0F;
            if(this.i < 0.0F) {
                this.k = this.i;
                this.l = 0.0F - this.i;
            } else {
                this.m = -30.0F;
                this.l = this.i;
            }

            this.postInvalidate();
        }

    }

    protected void onDraw(Canvas var1) {
        if(!this.isInEditMode()) {
            float var2 = (float)this.getWidth();
            float var3 = (float)this.c / 2.0F;
            this.b.set(var3, var3, var2 - var3, var2 - var3);
            float var4 = var2 / 2.0F;
            var1.save();
            var1.translate(var4, var4);
            var1.rotate(-90.0F);
            var1.translate(-var4, -var4);
            if(this.j >= 270.0F) {
                this.d = this.d == this.f?this.g:this.f;
                this.a.setColor(this.d);
                var1.drawArc(this.b, this.m, this.n, false, this.a);
                this.postInvalidateDelayed(200L);
            } else if(this.h) {
                this.d = this.f;
                this.a.setColor(this.f);
                var1.drawArc(this.b, this.m, this.n, false, this.a);
            }

            this.a.setColor(this.e);
            var1.drawArc(this.b, this.k, this.l, false, this.a);
            var1.restore();
        }
    }
}
