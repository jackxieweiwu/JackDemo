package dji.midware.ui.d;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDE extends UiDL {
    private int a;
    private int b;
    private int c;
    private int d;

    private UiDE(int var1, int var2, int var3, int var4) {
        this.a = var1;
        this.b = var2;
        this.c = var3;
        this.d = var4;
    }

    protected float a(float var1) {
        return var1 * (float)this.c / (float)this.a;
    }

    protected float b(float var1) {
        return var1 * (float)this.d / (float)this.b;
    }

    public static class a {
        private int a;
        private int b;
        private int c;
        private int d;

        public a() {
        }

        public UiDE.a a(int var1) {
            this.a = var1;
            return this;
        }

        public UiDE.a b(int var1) {
            this.b = var1;
            return this;
        }

        public UiDE.a c(int var1) {
            this.c = var1;
            return this;
        }

        public UiDE.a d(int var1) {
            this.d = var1;
            return this;
        }

        public UiDE a() {
            return new UiDE(this.a, this.b, this.c, this.d);
        }
    }
}
