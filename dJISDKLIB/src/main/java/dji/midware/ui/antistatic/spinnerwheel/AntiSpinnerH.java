package dji.midware.ui.antistatic.spinnerwheel;

import android.view.View;
import android.widget.LinearLayout;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AntiSpinnerH {
    private static final String a = AntiSpinnerH.class.getName();
    private List<View> listb;
    private List<View> listc;
    private AntiSpinnerA antiSpinnerd;

    public AntiSpinnerH(AntiSpinnerA var1) {
        this.antiSpinnerd = var1;
    }

    public int a(LinearLayout var1, int var2, AntiSpinnerC var3) {
        int var4 = var2;

        for(int var5 = 0; var5 < var1.getChildCount(); ++var4) {
            if(!var3.a(var4)) {
                this.a(var1.getChildAt(var5), var4);
                var1.removeViewAt(var5);
                if(var5 == 0) {
                    ++var2;
                }
            } else {
                ++var5;
            }
        }

        return var2;
    }

    public View a() {
        return this.a(this.listb);
    }

    public View b() {
        return this.a(this.listc);
    }

    public void c() {
        if(this.listb != null) {
            this.listb.clear();
        }

        if(this.listc != null) {
            this.listc.clear();
        }

    }

    private List<View> a(View var1, List<View> var2) {
        if(var2 == null) {
            var2 = new LinkedList();
        }

        ((List)var2).add(var1);
        return (List)var2;
    }

    private void a(View var1, int var2) {
        int var3 = antiSpinnerd.getViewAdapter().b();
        if((var2 < 0 || var2 >= var3) && !antiSpinnerd.g()) {
            this.listc = this.a(var1, this.listc);
        } else {
            while(true) {
                if(var2 >= 0) {
                    int var1000 = var2 % var3;
                    this.listb = this.a(var1, this.listb);
                    break;
                }

                var2 += var3;
            }
        }

    }

    private View a(List<View> var1) {
        if(var1 != null && var1.size() > 0) {
            View var2 = (View)var1.get(0);
            var1.remove(0);
            return var2;
        } else {
            return null;
        }
    }
}
