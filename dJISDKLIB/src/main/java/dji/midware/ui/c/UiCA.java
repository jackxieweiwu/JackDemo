package dji.midware.ui.c;

/**
 * Created by jack_xie on 17-5-31.
 */

public class UiCA {
    public String a = "";
    public int b = 2147483647;
    public int c = 2147483647;
    public boolean d = false;
    public Object e = null;
    public int f = 0;
    public int g = 100;

    public UiCA() {
    }

    public boolean equals(Object var1) {
        boolean var2 = super.equals(var1);
        if(!var2 && var1 instanceof UiCA) {
            UiCA var3 = (UiCA)var1;
            var2 = this.b == var3.b;
        }

        return var2;
    }

    public int hashCode() {
        return this.b;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder(16);
        var1.append("desc[").append(this.a).append("]");
        return var1.toString();
    }
}

