package dji.midware.ui.c.a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dji.midware.R;
import dji.midware.ui.antistatic.spinnerwheel.a.AnSpinner_B;

/**
 * Created by jack_xie on 17-5-31.
 */

public class UiCAC<T> extends AnSpinner_B {
    private T[] f;
    private final int[] g = new int[]{-2147483648, 2147483647};
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 2147483647;
    private int m = -1;
    private boolean n = true;

    public UiCAC(Context var1, T[] var2) {
        super(var1);
        this.f = var2;
        this.l = var2.length;
        this.h = var1.getResources().getColor(R.color.red);
        this.i = var1.getResources().getColor(R.color.white);
        this.k = var1.getResources().getColor(R.color.white);
        this.j = var1.getResources().getColor(R.color.blue_highlight);
    }

    public void a(boolean var1) {
        if(this.n != var1) {
            this.n = var1;
            //this.a();
        }

    }

    public void d(int var1) {
        if(this.m != var1) {
            this.m = var1;
            //this.a();
        }

    }

    public int b() {
        return this.l != -1?this.l:this.f.length;
    }

    protected CharSequence c(int var1) {
        if(var1 >= 0 && var1 < this.b()) {
            Object var2 = this.f[var1];
            return (CharSequence)(var2 instanceof CharSequence?(CharSequence)var2:var2.toString());
        } else {
            return null;
        }
    }

    public View a(View var1, ViewGroup var2) {
        return null;
    }

    public View a(int var1, View var2, ViewGroup var3) {
        if(var1 >= 0 && var1 < this.b()) {
            if(var2 == null) {
                var2 = this.a(this.c, var3);
            }

            TextView var4 = this.a(var2, this.d);
            if(var4 != null) {
                Object var5 = this.c(var1);
                if(var5 == null) {
                    var5 = "";
                }

                var4.setText((CharSequence)var5);
                this.a(var4, var1);
            }

            return var2;
        } else {
            return null;
        }
    }

    protected void a(TextView var1, int var2) {
        if(!this.n) {
            var1.setTextColor(this.k);
        } else if(var2 >= this.g[0] && var2 <= this.g[1]) {
            if(this.j != 0 && var2 == this.m) {
                var1.setTextColor(this.j);
            } else {
                var1.setTextColor(this.i);
            }
        } else {
            var1.setTextColor(this.h);
        }

    }

    private TextView a(View var1, int var2) {
        return (TextView)var1.findViewById(var2);
    }

    private View a(int var1, ViewGroup var2) {
        return this.b.inflate(var1, var2, false);
    }
}
