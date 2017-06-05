package dji.midware.ui.c;

/**
 * Created by jack_xie on 17-5-31.
 */

public class UiCC {
    public int a = 2147483647;
    public int b = 0;
    private String e = "";
    public int c = 0;
    private String f = "";
    private int g = 0;
    public UiCC.enum_b d;
    private boolean h = false;

    public UiCC() {
    }

    public static UiCC a(int var0, String var1, String var2, int var3) {
        UiCC var4 = new UiCC();
        var4.d = UiCC.enum_b.f;
        var4.b = var0;
        var4.e = var1;
        var4.f = var2;
        var4.g = var3;
        return var4;
    }

    public boolean equals(Object var1) {
        boolean var2 = super.equals(var1);
        if(!var2 && var1 instanceof UiCC) {
            UiCC var3 = (UiCC)var1;
            var2 = this.e.equals(var3.e) && this.b == var3.b;
        }

        return var2;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder(20);
        var1.append("tId").append(String.valueOf(this.e)).append("]");
        var1.append("vId").append(String.valueOf(this.a)).append("]");
        return var1.toString();
    }

    public void a(boolean var1) {
        this.h = var1;
    }

    public boolean a() {
        return this.h;
    }

    public void a(String var1) {
        this.e = var1;
    }

    public String b() {
        return this.e;
    }

    public int c() {
        return this.g;
    }

    public void a(int var1) {
        this.g = var1;
    }

    public String d() {
        return this.f;
    }

    public void b(String var1) {
        this.f = var1;
    }

    public static class a {
        public int a;
        public UiCC.enum_b b;

        public a(int var1, UiCC.enum_b var2) {
            this.a = var1;
            this.b = var2;
        }
    }

    public static enum enum_b {
        a(0),
        b(1),
        c(2),
        d(3),
        e(4),
        f(5);

        public final int g;

        private enum_b(int var3) {
            this.g = var3;
        }

        public static UiCC.enum_b a(int var0) {
            switch(var0) {
                case 0:
                default:
                    return a;
                case 1:
                    return b;
                case 2:
                    return c;
                case 3:
                    return d;
                case 4:
                    return e;
                case 5:
                    return f;
            }
        }
    }
}
