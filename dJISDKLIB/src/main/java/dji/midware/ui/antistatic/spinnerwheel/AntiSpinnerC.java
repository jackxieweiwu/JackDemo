package dji.midware.ui.antistatic.spinnerwheel;

/**
 * Created by jack_xie on 17-6-1.
 */

public class AntiSpinnerC {
    private int a;
    private int b;

    public AntiSpinnerC() {
        this(0, 0);
    }

    public AntiSpinnerC(int var1, int var2) {
        this.a = var1;
        this.b = var2;
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.a() + this.c() - 1;
    }

    public int c() {
        return this.b;
    }

    public boolean a(int var1) {
        return var1 >= this.a() && var1 <= this.b();
    }
}
