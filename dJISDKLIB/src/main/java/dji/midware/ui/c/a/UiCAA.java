package dji.midware.ui.c.a;

/**
 * Created by jack_xie on 17-5-31.
 */

public class UiCAA {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;

    public UiCAA(int var1) {
        this.e = var1;
    }

    public UiCAA(int var1, int var2, int var3, int var4) {
        this(var1, var2, var3, var4, -2147483648);
    }

    public UiCAA(int var1, int var2, int var3, int var4, int var5) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
        this.e = var5;
    }

    public int a() {
        return this.e;
    }

    public int b() {
        return this.c;
    }

    public int c() {
        return this.d;
    }

    public int d() {
        return this.a;
    }

    public int e() {
        return this.b;
    }
}
