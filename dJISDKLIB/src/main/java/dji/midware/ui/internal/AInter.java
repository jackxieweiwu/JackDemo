package dji.midware.ui.internal;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import dji.midware.R;
import dji.midware.ui.widget.FocusExposureSwitchWidget.ControlMode;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AInter extends FrameLayout {
    private AnimatorSet animatorSetA;
    private AnimatorSet animatorSetB;
    private ControlMode addListenerC;

    public AInter(Context var1) {
        super(var1);
        this.addListenerC = ControlMode.FOCUS;
        int var2 = (int)(60.0F * var1.getResources().getDisplayMetrics().density);
        this.setLayoutParams(new LayoutParams(var2, var2));
        this.animatorSetA = new AnimatorSet();
        ObjectAnimator var3 = ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0F}).setDuration(50L);
        ObjectAnimator var4 = ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.0F, 1.15F}).setDuration(100L);
        ObjectAnimator var5 = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.0F, 1.15F}).setDuration(100L);
        ObjectAnimator var6 = ObjectAnimator.ofFloat(this, "scaleX", new float[]{1.0F, 1.15F}).setDuration(100L);
        ObjectAnimator var7 = ObjectAnimator.ofFloat(this, "scaleY", new float[]{1.0F, 1.15F}).setDuration(100L);
        var6.setRepeatCount(3);
        var6.setRepeatMode(2);
        var6.setStartDelay(200L);
        var7.setRepeatCount(3);
        var7.setRepeatMode(2);
        var7.setStartDelay(200L);
        this.animatorSetA.play(var4).with(var3).after(400L).with(var5).before(var6).before(var7);
        this.animatorSetA.setInterpolator(new OvershootInterpolator());
        this.animatorSetA.addListener(new a());
        this.animatorSetB = new AnimatorSet();
        ObjectAnimator var8 = ObjectAnimator.ofFloat(this, "alpha", new float[]{1.0F, 0.5F}).setDuration(400L);
        var8.setRepeatCount(3);
        var8.setRepeatMode(2);
        this.animatorSetB.play(var8);
        this.animatorSetB.addListener(new a());
    }

    public void a() {
        saetBackC();
        if(this.addListenerC == ControlMode.FOCUS) {
            this.animatorSetA.start();
        } else {
            this.animatorSetB.start();
        }

    }

    public void setControlMode(ControlMode var1) {
        this.addListenerC = var1;
    }

    private void saetBackC() {
        if(this.addListenerC == ControlMode.FOCUS) {
            this.setBackgroundResource(R.drawable.rectangle_310);
        } else {
            this.setBackgroundResource(R.drawable.oval_120);
        }

    }

    public void b() {
        if(animatorSetA.isRunning()) {
            animatorSetA.end();
        }

        if(animatorSetB.isRunning()) {
            animatorSetB.end();
        }

    }

    private void buildD() {
        int var1 = Build.VERSION.SDK_INT;
        if(var1 < 16) {
            this.setBackgroundDrawable((Drawable)null);
        } else {
            this.setBackground((Drawable)null);
        }

    }

    private class a implements Animator.AnimatorListener {
        private a() {
        }

        public void onAnimationStart(Animator var1) {
            saetBackC();
        }

        public void onAnimationEnd(Animator var1) {
            buildD();
        }

        public void onAnimationCancel(Animator var1) {
        }

        public void onAnimationRepeat(Animator var1) {
        }
    }
}
