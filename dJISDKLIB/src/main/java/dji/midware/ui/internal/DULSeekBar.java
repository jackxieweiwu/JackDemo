package dji.midware.ui.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import dji.midware.R;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_H;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULSeekBar extends BaseFrameLayout implements View.OnTouchListener {
    private UiCAB uiCABA;
    private ImageView b;
    private ImageView c;
    private TextView d;
    private List<DULSeekBar.a> e;
    private int f;
    private int g;
    private int h;
    private float i;
    private float j;
    private float k;
    private float l;
    private ImageView m;
    private ImageView n;

    public DULSeekBar(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public DULSeekBar(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public DULSeekBar(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.b = (ImageView)this.getViewById(R.id.imageview_track);
        this.m = (ImageView)this.getViewById(R.id.imageview_thumb);
        this.n = (ImageView)this.getViewById(R.id.imageview_thumb_disable);
        this.d = (TextView)this.getViewById(R.id.textview_value);
    }

    protected UiCAB getWidgetAppearances() {
        if(uiCABA == null) {
            uiCABA = new UiCBA_H();
        }
        return uiCABA;
    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        this.i = (float)this.b.getLeft();
        this.j = (float)this.b.getRight();
        this.c = this.m;
    }

    public synchronized int getMax() {
        return this.f;
    }

    public synchronized void setMax(int var1) {
        this.f = var1;
    }

    public void a(boolean var1) {
        this.d.setEnabled(var1);
        if(var1) {
            this.c = this.m;
            this.m.setVisibility(0);
            this.n.setVisibility(4);
            this.setOnTouchListener(this);
        } else {
            this.setOnTouchListener((OnTouchListener)null);
            this.c = this.n;
            this.m.setVisibility(4);
            this.n.setVisibility(0);
        }

    }

    public void setOnSeekBarChangeListener(DULSeekBar.a var1) {
        if(this.e == null) {
            this.e = new LinkedList();
        }

        this.e.add(var1);
    }

    public void setText(String var1) {
        this.d.setText(var1);
    }

    public synchronized void setProgress(int var1) {
        this.a(var1, true);
    }

    public int getProgress() {
        return this.g;
    }

    public void a() {
        this.setProgress(this.h);
        this.a(this.h);
    }

    public void a(int var1) {
        float var2 = this.i + (float)(this.getIncreasement() * var1);
        this.a(var2);
    }

    public void a(float var1) {
        if(var1 < this.i) {
            var1 = this.i;
        } else if(var1 > this.j) {
            var1 = this.j;
        }

        this.setSeekbarTextPostion(var1);
        this.setSeekbarThumbPostion(var1);
    }

    private void setSeekbarTextPostion(float var1) {
        this.d.setX(var1 - (float)(this.d.getWidth() / 2));
    }

    private void setSeekbarThumbPostion(float var1) {
        this.n.setX(var1 - (float)(this.n.getWidth() / 2));
        this.m.setX(var1 - (float)(this.m.getWidth() / 2));
    }

    synchronized boolean a(int var1, boolean var2) {
        if(var1 >= this.f) {
            var1 = this.f;
        } else if(var1 < 0) {
            var1 = 0;
        }

        if(var1 == this.g) {
            return false;
        } else {
            this.g = var1;
            if(var2) {
                this.a(this.g);
            }

            if(this.e != null) {
                for(int var3 = 0; var3 < this.e.size(); ++var3) {
                    ((DULSeekBar.a)this.e.get(var3)).a(this.g, false);
                }
            }

            return true;
        }
    }

    private int getIncreasement() {
        return (int)Math.floor((double)((this.j - this.i) / (float)(this.f + 1)));
    }

    public boolean onTouch(View var1, MotionEvent var2) {
        switch(var2.getAction()) {
            case 0:
                this.a(var2);
                break;
            case 1:
                this.b();
                break;
            case 2:
                this.b(var2);
        }

        return true;
    }

    private void a(MotionEvent var1) {
        this.l = var1.getX();
        this.k = this.c.getX() + (float)(this.c.getWidth() / 2);
        this.h = this.g;
        if(this.e != null) {
            for(int var2 = 0; var2 < this.e.size(); ++var2) {
                ((DULSeekBar.a)this.e.get(var2)).a(this.g);
            }
        }

    }

    private void b(MotionEvent var1) {
        float var2 = var1.getX() - this.l;
        float var3 = this.k + var2;
        this.a(var3);
        this.a((int)(var3 - this.i) / this.getIncreasement(), false);
    }

    private void b() {
        if(this.e != null) {
            for(int var1 = 0; var1 < this.e.size(); ++var1) {
                ((DULSeekBar.a)this.e.get(var1)).b(this.g);
            }
        }

    }

    public interface a {
        void a(int var1, boolean var2);

        void a(int var1);

        void b(int var1);
    }
}
