package dji.midware.ui.antistatic.spinnerwheel;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AntiSpinnerJ extends AntiSpinnerI {
    public AntiSpinnerJ(Context context, numberInterface var2) {
        super(context, var2);
    }

    protected int protA() {
        return this.scrollerA.getCurrY();
    }

    protected int protB() {
        return this.scrollerA.getFinalY();
    }

    protected float a(MotionEvent var1) {
        return var1.getY();
    }

    protected void protAA(int var1, int var2) {
        this.scrollerA.startScroll(0, 0, 0, var1, var2);
    }

    protected void protAA(int var1, int var2, int var3) {
        int var4 = 2147483647;
        int var5 = -2147483647;
        this.scrollerA.fling(0, var1, 0, -var3, 0, 0, -2147483647, 2147483647);
    }
}
