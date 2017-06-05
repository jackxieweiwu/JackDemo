package dji.midware.ui.antistatic.spinnerwheel;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by jack_xie on 17-6-1.
 */

public abstract class AntiSpinnerI {
    private numberInterface numberInterface;
    private Context c;
    private GestureDetector d;
    protected Scroller scrollerA;
    private int numE;
    private float f;
    private boolean g;
    private final int h = 0;
    private final int i = 1;
    private Handler handlerJ = new Handler() {
        public void handleMessage(Message var1) {
            scrollerA.computeScrollOffset();
            int var2 = protA();
            int var3 = numE - var2;
            numE = var2;
            if(var3 != 0) {
                numberInterface.oneA(var3);
            }

            if(Math.abs(var2 - protB()) < 1) {
                scrollerA.forceFinished(true);
            }

            if(!scrollerA.isFinished()) {
                handlerJ.sendEmptyMessage(var1.what);
            } else if(var1.what == 0) {
                antiSpinIF();
            } else {
                antiSpinID();
            }

        }
    };

    public AntiSpinnerI(Context var1, numberInterface var2) {
        this.d = new GestureDetector(var1, new GestureDetector.SimpleOnGestureListener() {
            public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
                return true;
            }

            public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
                numE = 0;
                protAA(numE, (int)var3, (int)var4);
                antiSpinIA(0);
                return true;
            }
        });
        this.d.setIsLongpressEnabled(false);
        this.scrollerA = new Scroller(var1);
        this.numberInterface = var2;
        this.c = var1;
    }

    public void a(Interpolator var1) {
        this.scrollerA.forceFinished(true);
        this.scrollerA = new Scroller(this.c, var1);
    }

    public void b(int var1, int var2) {
        this.scrollerA.forceFinished(true);
        this.numE = 0;
        this.protAA(var1, var2 != 0?var2:400);
        this.antiSpinIA(0);
        this.antiSpinIG();
    }

    public void c() {
        this.scrollerA.forceFinished(true);
    }

    public boolean b(MotionEvent var1) {
        switch(var1.getAction()) {
            case 0:
                this.f = this.a(var1);
                this.scrollerA.forceFinished(true);
                antiSpinIE();
                this.numberInterface.twoB();
                break;
            case 1:
                if(this.scrollerA.isFinished()) {
                    this.numberInterface.threeC();
                }
                break;
            case 2:
                int var2 = (int)(this.a(var1) - this.f);
                if(var2 != 0) {
                    antiSpinIG();
                    this.numberInterface.oneA(var2);
                    this.f = this.a(var1);
                }
        }

        if(!this.d.onTouchEvent(var1) && var1.getAction() == 1) {
            antiSpinIF();
        }

        return true;
    }

    private void antiSpinIA(int var1) {
        this.antiSpinIE();
        this.handlerJ.sendEmptyMessage(var1);
    }

    private void antiSpinIE() {
        this.handlerJ.removeMessages(0);
        this.handlerJ.removeMessages(1);
    }

    private void antiSpinIF() {
        this.numberInterface.sixE();
        this.antiSpinIA(1);
    }

    private void antiSpinIG() {
        if(!this.g) {
            this.g = true;
            this.numberInterface.fourA();
        }

    }

    protected void antiSpinID() {
        if(this.g) {
            this.numberInterface.fiveD();
            this.g = false;
        }

    }

    protected abstract int protA();

    protected abstract int protB();

    protected abstract float a(MotionEvent var1);

    protected abstract void protAA(int var1, int var2);

    protected abstract void protAA(int var1, int var2, int var3);

    public interface numberInterface {
        void oneA(int var1);

        void twoB();

        void threeC();

        void fourA();

        void fiveD();

        void sixE();
    }
}
