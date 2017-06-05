package dji.midware.ui.internal.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import dji.midware.R;
import dji.midware.ui.a.UiAB;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.base.UiBaseKView;
import dji.midware.ui.base.UiBaseNView;
import dji.midware.ui.c.UiCC;
import dji.midware.ui.internal.DULParentChildrenViewAnimator;
import dji.midware.ui.internal.RecyclerListView;

/**
 * Created by jack_xie on 17-6-1.
 */

public abstract class InterCamera_A extends UiBaseNView implements UiAB.a {
    protected UiCC.a[] b = null;
    protected UiAB c;
    protected DULParentChildrenViewAnimator.a d;
    protected RecyclerListView e;
    protected TextView f;
    protected View g;
    private Animation a;
    private Animation h;
    private Animation i;
    private Animation j;

    public InterCamera_A(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void setTitleTextView(TextView var1) {
        this.f = var1;
    }

    public void setRootViewCallback(DULParentChildrenViewAnimator.a var1) {
        this.d = var1;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        LayoutInflater var4 = (LayoutInflater)var1.getSystemService("layout_inflater");
        var4.inflate(R.layout.widget_list_view, this, true);
        this.e = (RecyclerListView)this.findViewById(R.id.recycle_list_view_content);
        this.f();
        this.c();
        this.g();
    }

    private void f() {
        this.setRootViewCallback(new DULParentChildrenViewAnimator.a() {
            public void onRootViewIsShown(boolean var1) {
                if(d != null) {
                    d.onRootViewIsShown(var1);
                }

            }
        });
    }

    public void setRootViewIsShown(boolean var1) {
        if(this.d != null) {
            this.d.onRootViewIsShown(var1);
        }

        if(var1) {
            this.e.setVisibility(0);
        } else {
            this.e.setVisibility(4);
        }

    }

    protected void c() {
        this.a();
        this.c = new UiAB(this);
        if(this.b != null && this.b.length > 0) {
            for(int var1 = 0; var1 < this.b.length; ++var1) {
                UiCC var2 = new UiCC();
                var2.a(this.getResources().getString(this.b[var1].a));
                var2.d = this.b[var1].b;
                this.c.a(var2);
            }

            this.b();
            this.e.setAdapter(this.c);
        }

    }

    private void g() {
        this.a = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_left_in);
        this.h = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_left_out);
        this.i = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_right_in);
        this.j = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_right_out);
    }

    protected void a(final int var1, int var2, int var3) {
        UiCC var4 = c.c(var1);
        if(var4.a != var2 || var4.c != var3) {
            var4.a = var2;
            var4.c = var3;
            this.post(new Runnable() {
                public void run() {
                    c.notifyItemChanged(var1);
                }
            });
        }

    }

    protected void d() {
        if(this.g != null) {
            ViewGroup.LayoutParams var1 = this.e.getLayoutParams();
            this.g.setLayoutParams(var1);
            this.g.startAnimation(this.i);
            this.f.startAnimation(this.i);
            this.e.startAnimation(this.h);
            this.addView(this.g);
            this.setRootViewIsShown(false);
            UiBaseKView var2 = (UiBaseKView)this.g;
            var2.updateTitle(this.f);
        }

    }

    public void e() {
        if(this.g != null) {
            this.g.startAnimation(this.j);
            this.f.startAnimation(this.j);
            this.e.startAnimation(this.a);
            this.setRootViewIsShown(true);
            this.removeView(this.g);
            this.g = null;
        }

    }

    protected abstract void a();

    protected abstract void b();
}
