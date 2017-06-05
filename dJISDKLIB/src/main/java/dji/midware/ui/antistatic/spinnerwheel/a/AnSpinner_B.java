package dji.midware.ui.antistatic.spinnerwheel.a;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jack_Xie on 17-5-31.
 */

public abstract class AnSpinner_B extends AnSpinner_A {
    private Typeface f;
    private int g;
    private int h;
    protected Context a;
    protected LayoutInflater b;
    protected int c;
    protected int d;
    protected int e;

    protected AnSpinner_B(Context var1) {
        this(var1, -1);
    }

    protected AnSpinner_B(Context var1, int var2) {
        this(var1, var2, 0);
    }

    protected AnSpinner_B(Context var1, int var2, int var3) {
        this.g = -15724528;
        this.h = 24;
        this.a = var1;
        this.c = var2;
        this.d = var3;
        this.b = (LayoutInflater)var1.getSystemService("layout_inflater");
    }

    public void a(int var1) {
        this.c = var1;
    }

    public void b(int var1) {
        this.d = var1;
    }

    protected abstract CharSequence c(int var1);

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
                this.a(var4);
            }

            return var2;
        } else {
            return null;
        }
    }

    public View a(View var1, ViewGroup var2) {
        if(var1 == null) {
            var1 = this.a(this.e, var2);
        }

        if(var1 instanceof TextView) {
            this.a((TextView)var1);
        }

        return var1;
    }

    protected void a(TextView var1) {
        if(this.c == -1) {
            var1.setTextColor(this.g);
            var1.setGravity(17);
            var1.setTextSize((float)this.h);
            var1.setLines(1);
        }

        if(this.f != null) {
            var1.setTypeface(this.f);
        } else {
            var1.setTypeface(Typeface.SANS_SERIF, 1);
        }

    }

    private TextView a(View var1, int var2) {
        TextView var3 = null;

        try {
            if(var2 == 0 && var1 instanceof TextView) {
                var3 = (TextView)var1;
            } else if(var2 != 0) {
                var3 = (TextView)var1.findViewById(var2);
            }

            return var3;
        } catch (ClassCastException var5) {
            Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a TextView", var5);
        }
    }

    private View a(int var1, ViewGroup var2) {
        switch(var1) {
            case -1:
                return new TextView(this.a);
            case 0:
                return null;
            default:
                return this.b.inflate(var1, var2, false);
        }
    }
}
