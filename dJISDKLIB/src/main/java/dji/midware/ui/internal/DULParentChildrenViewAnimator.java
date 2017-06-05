package dji.midware.ui.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;

import dji.midware.R;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULParentChildrenViewAnimator extends ViewAnimator {
    private Animation a;
    private Animation b;
    private Animation c;
    private Animation d;
    private DULParentChildrenViewAnimator.a e;

    public void setRootViewCallback(DULParentChildrenViewAnimator.a var1) {
        this.e = var1;
    }

    public DULParentChildrenViewAnimator(Context var1) {
        super(var1);
    }

    public DULParentChildrenViewAnimator(Context var1, AttributeSet var2) {
        super(var1, var2);
    }

    protected void onVisibilityChanged(@NonNull View var1, int var2) {
        super.onVisibilityChanged(var1, var2);
        if(this.c == null) {
            this.a = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_left_in);
            this.b = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_left_out);
            this.c = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_right_in);
            this.d = AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_right_out);
        }

    }

    public void setDisplayedChild(int var1) {
        if(this.e != null) {
            this.e.onRootViewIsShown(var1 == 0);
        }

        int var2 = this.getDisplayedChild();
        if(var2 > var1) {
            this.setInAnimation(this.a);
            this.setOutAnimation(this.d);
        } else if(var2 < var1) {
            this.setInAnimation(this.c);
            this.setOutAnimation(this.b);
        } else {
            this.setInAnimation((Animation)null);
            this.setOutAnimation((Animation)null);
        }

        super.setDisplayedChild(var1);
    }

    public interface a {
        void onRootViewIsShown(boolean var1);
    }
}
