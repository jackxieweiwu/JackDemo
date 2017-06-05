package dji.midware.ui.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import dji.midware.R;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULTabBarView extends FrameLayout {
    private ImageView f;
    protected ImageView[] a = new ImageView[0];
    protected DULTabBarView.a b = null;
    protected OnClickListener c = null;
    protected int d = -1;
    private int g;
    private int h = 1;
    protected boolean e = true;

    public DULTabBarView(Context var1) {
        super(var1);
        this.a(var1);
    }

    public DULTabBarView(Context var1, AttributeSet var2) {
        super(var1, var2);
        this.a(var1);
    }

    public DULTabBarView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.a(var1);
    }

    public int getCurrentTabIndex() {
        return this.d;
    }

    private void a(Context var1) {
        this.g = var1.getResources().getInteger(R.integer.translate_duration);
    }

    public void setStageChangedCallback(DULTabBarView.a var1) {
        this.b = var1;
    }

    public void a(ImageView[] var1, ImageView var2, boolean var3) {
        this.e = var3;
        this.a = var1;
        this.f = var2;
        this.a();
    }

    protected void a(int var1) {
        int var2 = this.f.getMeasuredWidth();
        if(this.e) {
            this.f.animate().translationX((float)(var1 * var2)).setDuration((long)(this.g * this.h)).setInterpolator(new AccelerateDecelerateInterpolator()).start();
            this.f.setTranslationX((float)(var1 * var2));
        } else {
            this.f.setTranslationX((float)(var1 * var2));
        }

    }

    protected void a(int var1, int var2) {
        for(int var3 = 0; var3 < this.a.length; ++var3) {
            this.a[var3].setSelected(var1 == var3);
        }

    }

    public void b(int var1) {
        if(var1 != this.d) {
            int var2 = this.d;
            this.d = var1;
            this.a(var1, var2);
            this.h = Math.abs(var1 - var2);
            this.a(var1);
            if(this.b != null) {
                this.b.onStageChange(var1);
            }

        }
    }

    protected void a() {
        if(!this.isInEditMode()) {
            this.c = new OnClickListener() {
                public void onClick(View var1) {
                    int var2 = 0;

                    for(int var3 = DULTabBarView.this.a.length; var2 < var3; ++var2) {
                        if(var1 == DULTabBarView.this.a[var2]) {
                            if(var2 != DULTabBarView.this.d) {
                                DULTabBarView.this.b(var2);
                            }
                            break;
                        }
                    }

                }
            };
            int var1 = 0;

            for(int var2 = this.a.length; var1 < var2; ++var1) {
                this.a[var1].setOnClickListener(this.c);
            }

        }
    }

    public boolean onTouchEvent(MotionEvent var1) {
        return true;
    }

    public interface a {
        void onStageChange(int var1);
    }
}
