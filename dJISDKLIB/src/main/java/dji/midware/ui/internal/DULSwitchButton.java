package dji.midware.ui.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import dji.midware.R;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_K;
/**
 * Created by jack_xie on 17-6-1.
 */

public class DULSwitchButton extends BaseFrameLayout implements View.OnTouchListener {
    private UiCAB a;
    private ImageView b;
    private boolean c;
    private DULSwitchButton.a d;
    private ImageView e;
    private float f;
    private int g;
    private int h;
    private int i;
    private int j;
    private float k;
    private float l;
    private int m;
    private int n;
    private int o;
    private Paint p;
    private Paint q;
    private Paint r;

    public void setOnCheckedListener(DULSwitchButton.a var1) {
        this.d = var1;
    }

    public DULSwitchButton(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public DULSwitchButton(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public DULSwitchButton(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setWillNotDraw(false);
        this.e = (ImageView)this.getViewById(R.id.layout_track);
        this.b = (ImageView)this.getViewById(R.id.imageview_left);
        this.a(var1, this.e);
        this.setOnTouchListener(this);
        this.e.setSelected(false);
        this.setChecked(false);
        this.i = 0;
    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        this.g = this.e.getLeft();
        this.h = this.e.getRight() - this.b.getWidth();
        this.j = (int)Math.floor((double)((this.h - this.g) / 10));
    }

    protected UiCAB getWidgetAppearances() {
        if(this.a == null) {
            this.a = new UiCBA_K();
        }
        return this.a;
    }

    private void a(Context var1, ImageView var2) {
        this.n = var1.getResources().getColor(R.color.green);
        this.m = var1.getResources().getColor(R.color.gray);
        this.o = var1.getResources().getColor(R.color.white);
        this.p = new Paint(1);
        this.p.setColor(this.n);
        this.p.setStyle(Paint.Style.STROKE);
        this.p.setStrokeCap(Paint.Cap.ROUND);
        this.q = new Paint(1);
        this.q.setColor(this.m);
        this.q.setStyle(Paint.Style.STROKE);
        this.q.setStrokeCap(Paint.Cap.ROUND);
        this.r = new Paint(1);
        this.r.setColor(this.o);
        this.r.setStyle(Paint.Style.STROKE);
        this.r.setStrokeCap(Paint.Cap.ROUND);
    }

    protected void onDraw(Canvas var1) {
        this.e.setVisibility(4);
        this.b.setVisibility(4);
        float var2 = (float)this.e.getHeight();
        this.p.setStrokeWidth(var2);
        this.q.setStrokeWidth(var2);
        this.r.setStrokeWidth(var2);
        float var3 = this.e.getY() + var2 / 2.0F;
        var1.drawLine(var2 / 2.0F, var3, (float)this.e.getWidth() - var2 / 2.0F, var3, this.q);
        var1.drawLine(var2 / 2.0F, var3, this.b.getX() + var2 / 2.0F, var3, this.p);
        var1.drawPoint(this.b.getX() + var2 / 2.0F, var3, this.r);
    }

    public void a(View var1) {
        this.setChecked(!this.c);
        if(this.d != null) {
            this.d.onCheckedChanged(this.c);
        }

    }

    public boolean a() {
        return this.c;
    }

    public synchronized void setChecked(final boolean var1) {
        this.c = var1;
        (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() {
                DULSwitchButton.this.setProgress(var1?10:0);
                DULSwitchButton.this.b.setX(var1?(float)DULSwitchButton.this.h:(float)DULSwitchButton.this.g);
                DULSwitchButton.this.invalidate();
            }
        });
    }

    public boolean onTouch(View var1, MotionEvent var2) {
        switch(var2.getAction()) {
            case 0:
                this.a(var2);
                break;
            case 1:
                if(Math.abs(this.l) < 10.0F) {
                    this.a(var1);
                } else {
                    this.b();
                }
                break;
            case 2:
                this.b(var2);
        }

        return true;
    }

    private void a(MotionEvent var1) {
        this.e.setSelected(false);
        this.f = var1.getX();
        this.k = this.b.getX();
    }

    private void b(MotionEvent var1) {
        this.l = var1.getX() - this.f;
        float var2 = this.k + this.l;
        this.a(var2);
        if(this.j != 0) {
            this.setProgress((int)(var2 - (float)this.g) / this.j);
        }

        this.invalidate();
    }

    private void b() {
        boolean var1 = this.c;
        if(this.i >= 5) {
            this.setChecked(true);
        } else {
            this.setChecked(false);
        }

        if(this.d != null && var1 != this.c) {
            this.d.onCheckedChanged(this.c);
        }

    }

    private void a(float var1) {
        if(var1 < (float)this.g) {
            var1 = (float)this.g;
        } else if(var1 > (float)this.h) {
            var1 = (float)this.h;
        }

        this.b.setX(var1);
    }

    void setProgress(int var1) {
        if(var1 >= 10) {
            var1 = 10;
        } else if(var1 < 0) {
            var1 = 0;
        }

        if(var1 != this.i) {
            this.i = var1;
        }

    }

    public interface a {
        void onCheckedChanged(boolean var1);
    }
}
