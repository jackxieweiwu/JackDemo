package dji.midware.ui.antistatic.spinnerwheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import dji.log.DJILog;
import java.util.Iterator;
import java.util.LinkedList;
import dji.midware.R.styleable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import dji.midware.R;
import dji.midware.ui.antistatic.spinnerwheel.a.AnSpinner_C;

/**
 * Created by jack_xie on 17-5-31.
 */

public abstract class AntiSpinnerA extends View{
    private static int m = -1;
    private final String className;
    protected int numbera;
    protected int numberb;
    protected boolean aBooleanc;
    protected boolean aBooleand;
    protected AntiSpinnerI antiSpinnerI;
    protected boolean aBoolean;
    protected int numberg;
    protected LinearLayout linearLayout;
    protected int i;
    protected AnSpinner_C anSpinner_c;
    protected int k;
    protected int l;
    private AntiSpinnerH antiSpinnerH;
    private List<AntiSpinnerD> antiSpinnerDList;
    private List<AntiSpinnerF> antiSpinnerFList;
    private List<AntiSpinnerE> antiSpinnerEList;
    private DataSetObserver dataSetObserver;

    public AntiSpinnerA(Context var1, AttributeSet var2, int var3) {
        super(var1, var2);
        this.className = AntiSpinnerA.class.getName() + " #" + ++m;
        this.numbera = 0;
        this.antiSpinnerH = new AntiSpinnerH(this);
        this.antiSpinnerDList = new LinkedList();
        this.antiSpinnerFList = new LinkedList();
        this.antiSpinnerEList = new LinkedList();
        this.a(var2, var3);
        this.a(var1);
    }

    protected void a(AttributeSet var1, int var2) {
        TypedArray var3 = this.getContext().obtainStyledAttributes(var1, R.styleable.AbstractWheelView, var2, 0);
        this.numberb = var3.getInt(R.styleable.AbstractWheelView_visibleItems, 4);
        this.aBooleanc = var3.getBoolean(R.styleable.AbstractWheelView_isAllVisible, false);
        this.aBooleand = var3.getBoolean(R.styleable.AbstractWheelView_isCyclic, false);
        var3.recycle();
    }

    protected void a(Context var1) {
        dataSetObserver = new DataSetObserver() {
            public void onChanged() {
                a(false);
            }

            public void onInvalidated() {
                a(true);
            }
        };

        antiSpinnerI = getAntiSpinnerI(new AntiSpinnerI.numberInterface() {

            @Override
            public void oneA(int var1) {
                setNumberC(var1);
                int var2 = getBaseDimension();
                if(numberg > var2) {
                    numberg = var2;
                    antiSpinnerI.c();
                } else if(numberg < -var2) {
                    numberg = -var2;
                    antiSpinnerI.c();
                }
                DJILog.d(className, "OnScroll + " + var1);
            }

            @Override
            public void twoB() {
                spinnerB();
            }

            @Override
            public void threeC() {
                if(!aBoolean) {
                    spinnerC();
                }
            }

            @Override
            public void fourA() {
                aBoolean = true;
                spinnerFlistIterator();
                spinnerA();
            }

            @Override
            public void fiveD() {
                if(aBoolean) {
                    antiSpinnerFIter();
                    aBoolean = false;
                    spinnerD();
                }

                numberg = 0;
                invalidate();
            }

            @Override
            public void sixE() {
                if(Math.abs(numberg) > 1) {
                    antiSpinnerI.b(numberg, 0);
                }
            }
        });
    }

    public Parcelable onSaveInstanceState() {
        Parcelable var1 = super.onSaveInstanceState();
        SavedState var2 = new SavedState(var1);
        var2.numA = this.getCurrentItem();
        return var2;
    }

    public void onRestoreInstanceState(Parcelable var1) {
        if(!(var1 instanceof SavedState)) {
            super.onRestoreInstanceState(var1);
        } else {
            SavedState var2 = (SavedState)var1;
            super.onRestoreInstanceState(var2.getSuperState());
            numbera= var2.numA;
            this.postDelayed(new Runnable() {
                public void run() {
                    a(false);
                }
            }, 100L);
        }
    }

    protected abstract void bitmapA(int var1, int var2);

    protected abstract AntiSpinnerI getAntiSpinnerI(AntiSpinnerI.numberInterface var1);

    protected void spinnerA() {
    }

    protected void spinnerB() {
    }

    protected void spinnerC() {
    }

    protected void spinnerD() {
    }

    public void setInterpolator(Interpolator var1) {
        this.antiSpinnerI.a(var1);
    }

    public void b(int var1, int var2) {
        int var3 = var1 * this.getItemDimension() - numberg;
        this.spinnerB();
        this.antiSpinnerI.b(var3, var2);
    }

    private void setNumberC(int var1) {
        numberg+= var1;
        int var2 = this.getItemDimension();
        int var3 = numberg / var2;
        int var4 = numbera - var3;
        int var5 = this.anSpinner_c.b();
        int var6 = numberg % var2;
        if(Math.abs(var6) <= var2 / 2) {
            var6 = 0;
        }

        if(aBooleand && var5 > 0) {
            if(var6 > 0) {
                --var4;
                ++var3;
            } else if(var6 < 0) {
                ++var4;
                --var3;
            }

            while(var4 < 0) {
                var4 += var5;
            }

            var4 %= var5;
        } else if(var4 < 0) {
            var3 = numbera;
            var4 = 0;
        } else if(var4 >= var5) {
            var3 = numbera - var5 + 1;
            var4 = var5 - 1;
        } else if(var4 > 0 && var6 > 0) {
            --var4;
            ++var3;
        } else if(var4 < var5 - 1 && var6 < 0) {
            ++var4;
            --var3;
        }

        int var7 = numberg;
        if(var4 != numbera) {
            this.a(var4, false);
        } else {
            this.invalidate();
        }

        int var8 = this.getBaseDimension();
        numberg = var7 - var3 * var2;
        if(numberg > var8) {
            numberg = numberg % var8 + var8;
        }

    }

    protected abstract int getBaseDimension();

    protected abstract int getItemDimension();

    protected abstract float a(MotionEvent var1);

    protected abstract void e();

    protected abstract void f();

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        if(var1) {
            int var6 = var4 - var2;
            int var7 = var5 - var3;
            this.f();
            if(this.l != var6 || this.k != var7) {
                this.bitmapA(this.getMeasuredWidth(), this.getMeasuredHeight());
            }

            this.l = var6;
            this.k = var7;
        }

    }

    public void a(boolean var1) {
        if(var1) {
            this.antiSpinnerH.c();
            if(linearLayout != null) {
                linearLayout.removeAllViews();
            }

            numberg = 0;
        } else if(linearLayout != null) {
            this.antiSpinnerH.a(linearLayout, this.i, new AntiSpinnerC());
        }

        this.invalidate();
    }

    public int getVisibleItems() {
        return this.numberb;
    }

    public void setVisibleItems(int var1) {
        this.numberb = var1;
    }

    public void setAllItemsVisible(boolean var1) {
        this.aBooleanc = var1;
        this.a(false);
    }

    public AnSpinner_C getViewAdapter() {
        return anSpinner_c;
    }

    public void setViewAdapter(AnSpinner_C var1) {
        if(anSpinner_c != null) {
            anSpinner_c.remove_DataSetObserverList(dataSetObserver);
        }

        this.anSpinner_c = var1;
        if(anSpinner_c != null) {
            anSpinner_c.add_DataSetObserverList(dataSetObserver);
        }

        this.a(true);
    }

    public int getCurrentItem() {
        return numbera;
    }

    public void a(int var1, boolean var2) {
        if(this.anSpinner_c != null && this.anSpinner_c.b() != 0) {
            int var3 = this.anSpinner_c.b();
            if(var1 < 0 || var1 >= var3) {
                if(!aBooleand) {
                    return;
                }
                while(var1 < 0) {
                    var1 += var3;
                }
                var1 %= var3;
            }

            if(var1 != numbera) {
                int var4;
                if(var2) {
                    var4 = var1 - numbera;
                    if(aBooleand) {
                        int var5 = var3 + Math.min(var1, numbera) - Math.max(var1, numbera);
                        if(var5 < Math.abs(var4)) {
                            var4 = var4 < 0?var5:-var5;
                        }
                    }

                    this.b(var4, 0);
                } else {
                    numberg = 0;
                    var4 = numbera;
                    numbera = var1;
                    this.c(var4, numbera);
                    this.invalidate();
                }
            }

        }
    }

    public void setCurrentItem(int var1) {
        this.a(var1, false);
    }

    public boolean g() {
        return aBooleand;
    }

    public void setCyclic(boolean var1) {
        this.aBooleand = var1;
        this.a(false);
    }

    public void a(AntiSpinnerD var1) {
        antiSpinnerDList.add(var1);
    }

    protected void c(int var1, int var2) {
        Iterator var3 = antiSpinnerDList.iterator();

        while(var3.hasNext()) {
            AntiSpinnerD var4 = (AntiSpinnerD)var3.next();
            var4.a(this, var1, var2);
        }

    }

    public void a(AntiSpinnerF var1) {
        antiSpinnerFList.add(var1);
    }

    protected void spinnerFlistIterator() {
        Iterator var1 = antiSpinnerFList.iterator();
        while(var1.hasNext()) {
            AntiSpinnerF var2 = (AntiSpinnerF)var1.next();
            var2.a(this);
        }

    }

    protected void antiSpinnerFIter() {
        Iterator var1 = antiSpinnerFList.iterator();
        while(var1.hasNext()) {
            AntiSpinnerF var2 = (AntiSpinnerF)var1.next();
            var2.b(this);
        }

    }

    protected void a(int var1) {
        Iterator var2 = antiSpinnerEList.iterator();

        while(var2.hasNext()) {
            AntiSpinnerE var3 = (AntiSpinnerE)var2.next();
            var3.a(this, var1);
        }

    }

    protected boolean antSpj() {
        AntiSpinnerC var2 = this.getItemsRange();
        boolean var1;
        int var3;
        if(linearLayout != null) {
            var3 = antiSpinnerH.a(linearLayout, this.i, var2);
            var1 = this.i != var3;
            this.i = var3;
        } else {
            this.e();
            var1 = true;
        }

        if(!var1) {
            var1 = this.i != var2.a() || linearLayout.getChildCount() != var2.c();
        }

        if(this.i > var2.a() && this.i <= var2.b()) {
            for(var3 = this.i - 1; var3 >= var2.a() && this.b(var3, true); this.i = var3--) {
                ;
            }
        } else {
            this.i = var2.a();
        }

        var3 = this.i;

        for(int var4 = linearLayout.getChildCount(); var4 < var2.c(); ++var4) {
            if(!this.b(this.i + var4, false) && linearLayout.getChildCount() == 0) {
                ++var3;
            }
        }

        this.i = var3;
        return var1;
    }

    private AntiSpinnerC getItemsRange() {
        int var1;
        int var2;
        if(aBooleanc) {
            var1 = this.getBaseDimension();
            var2 = this.getItemDimension();
            if(var2 != 0) {
                numberb = var1 / var2 + 1;
            }
        }

        var1 = numbera - numberb / 2;
        var2 = var1 + numberb - (numberb % 2 == 0?0:1);
        if(numberg != 0) {
            if(numberg > 0) {
                --var1;
            } else {
                ++var2;
            }
        }

        if(!this.g()) {
            if(var1 < 0) {
                var1 = 0;
            }

            if(this.anSpinner_c == null) {
                var2 = 0;
            } else if(var2 > this.anSpinner_c.b()) {
                var2 = this.anSpinner_c.b();
            }
        }

        return new AntiSpinnerC(var1, var2 - var1 + 1);
    }

    protected boolean b(int var1) {
        return this.anSpinner_c != null && this.anSpinner_c.b() > 0 && (aBooleand || var1 >= 0 && var1 < this.anSpinner_c.b());
    }

    private boolean b(int var1, boolean var2) {
        View var3 = this.d(var1);
        if(var3 != null) {
            if(var2) {
                linearLayout.addView(var3, 0);
            } else {
                linearLayout.addView(var3);
            }

            return true;
        } else {
            return false;
        }
    }

    private View d(int var1) {
        if(this.anSpinner_c != null && this.anSpinner_c.b() != 0) {
            int var2 = this.anSpinner_c.b();
            if(!this.b(var1)) {
                return this.anSpinner_c.a(this.antiSpinnerH.b(), linearLayout);
            } else {
                while(var1 < 0) {
                    var1 += var2;
                }

                var1 %= var2;
                return this.anSpinner_c.a(var1, this.antiSpinnerH.a(), linearLayout);
            }
        } else {
            return null;
        }
    }

    public boolean onTouchEvent(MotionEvent var1) {
        if(this.isEnabled() && this.getViewAdapter() != null) {
            switch(var1.getAction()) {
                case 0:
                case 2:
                    if(this.getParent() != null) {
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
                case 1:
                    if(!aBoolean) {
                        int var2 = (int)this.a(var1) - this.getBaseDimension() / 2;
                        if(var2 > 0) {
                            var2 += this.getItemDimension() / 2;
                        } else {
                            var2 -= this.getItemDimension() / 2;
                        }

                        int var3 = var2 / this.getItemDimension();
                        if(var3 != 0 && this.b(numbera+ var3)) {
                            this.a(numbera + var3);
                        }
                    }
            }

            return this.antiSpinnerI.b(var1);
        } else {
            return true;
        }
    }

    static class SavedState extends BaseSavedState {
        int numA;
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        SavedState(Parcelable var1) {
            super(var1);
        }

        private SavedState(Parcel var1) {
            super(var1);
            this.numA = var1.readInt();
        }

        public void writeToParcel(Parcel var1, int var2) {
            super.writeToParcel(var1, var2);
            var1.writeInt(this.numA);
        }
    }
}
