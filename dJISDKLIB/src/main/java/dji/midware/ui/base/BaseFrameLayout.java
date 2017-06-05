package dji.midware.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import dji.midware.ui.c.a.UiCAA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAD;
import dji.midware.ui.c.a.UiCAF;
import dji.midware.ui.d.UiDB;

/**
 * Created by root on 17-5-31.
 */

public abstract class BaseFrameLayout extends FrameLayout {
    private ArrayList<View> viewList;
    protected static final String TAG = "BaseFrameLayout";
    protected Context context;

    protected abstract UiCAB getWidgetAppearances();

    protected View getViewById(int var1) {
        if(this.viewList.isEmpty() && !this.isInEditMode()) {
            throw new RuntimeException("Empty view list, forgot to specify ID in Appearances ?");
        } else {
            Iterator var2 = this.viewList.iterator();

            View var3;
            do {
                if(!var2.hasNext()) {
                    if(!this.isInEditMode()) {
                        throw new RuntimeException("Given ID didn\'t not match any view");
                    }

                    return null;
                }

                var3 = (View)var2.next();
            } while(var3.getId() != var1);

            return var3;
        }
    }

    protected void onMeasure(int var1, int var2) {
        UiCAB var3 = this.getWidgetAppearances();
        int var4 = View.MeasureSpec.getSize(var1);
        int var5 = View.MeasureSpec.getSize(var2);
        if((var4 != 0 || var5 != 0) && var3 != null && this.viewList != null) {
            if(var4 == 0) {
                var4 = var3.a(var5);
            }

            if(var5 == 0) {
                var5 = var3.b(var4);
            }

            int var6 = this.getPaddingLeft() + this.getPaddingRight();
            int var7 = this.getPaddingTop() + this.getPaddingBottom();
            var3.a(var4, var5);
            UiCAA[] var8 = var3.b();

            for(int var9 = 0; var9 < this.viewList.size(); ++var9) {
                UiCAA var10 = var8[var9];
                if(!(var10 instanceof UiCAD)) {
                    View var11 = (View)this.viewList.get(var9);
                    this.measureChild(var3, var11, var10, var1, var6, var2, var7);
                    if(var11 instanceof TextView && var10 instanceof UiCAF) {
                        UiDB.a((TextView)var11, (UiCAF)var10);
                    }
                }
            }

            this.setMeasuredDimension(resolveSize(var4, var1), resolveSize(var5, var2));
        } else {
            super.onMeasure(var1, var2);
        }

    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        UiCAB var6 = this.getWidgetAppearances();
        if(var6 != null && this.viewList != null) {
            UiCAA[] var7 = var6.b();
            if(var7 != null) {
                for(int var8 = 0; var8 < this.viewList.size(); ++var8) {
                    UiCAA var9 = var7[var8];
                    if(!(var9 instanceof UiCAD)) {
                        View var10 = (View)this.viewList.get(var8);
                        var6.a(var10, var9);
                    }
                }
            }
        } else {
            super.onLayout(var1, var2, var3, var4, var5);
        }

    }

    private void measureChild(UiCAB var1, View var2, UiCAA var3, int var4, int var5, int var6, int var7) {
        ViewGroup.MarginLayoutParams var8 = (ViewGroup.MarginLayoutParams)var2.getLayoutParams();
        int var9 = var1.a(var3);
        int var10 = var1.b(var3);
        int var11 = getChildMeasureSpec(var4, var5 + var8.leftMargin + var8.rightMargin, var9);
        int var12 = getChildMeasureSpec(var6, var7 + var8.topMargin + var8.bottomMargin, var10);
        var2.measure(var11, var12);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        this.viewList = new ArrayList();
        UiCAB var4 = this.getWidgetAppearances();
        if(var4 != null && var4.c() != -2147483648) {
            LayoutInflater var5 = LayoutInflater.from(var1);
            View var6 = var5.inflate(var4.c(), this);
            UiCAA[] var7 = var4.b();
            if(var7 != null) {
                UiCAA[] var8 = var7;
                int var9 = var7.length;

                for(int var10 = 0; var10 < var9; ++var10) {
                    UiCAA var11 = var8[var10];
                    if(var11.a() != -2147483648 && var6 != null) {
                        View var12 = var6.findViewById(var11.a());
                        this.viewList.add(var12);
                    }
                }
            }
        }

    }

    public BaseFrameLayout(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.context = var1;
        this.initView(var1, var2, var3);
    }

    public float aspectRatio() {
        UiCAB var1 = this.getWidgetAppearances();
        return var1 != null?var1.d():1.0F;
    }
}
