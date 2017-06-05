package dji.midware.ui.c;

import java.util.ArrayList;

import dji.midware.ui.c.UiCA;

/**
 * Created by jack_xie on 17-5-31.
 */

public class UiCB {
    public int a = 0;
    public String b = "";
    public String c = "";
    public int d = 2147483647;
    public int e = 2147483647;
    public int f = 1;
    public final ArrayList<UiCA> uiCAArrayList = new ArrayList();
    private boolean aBoolean = false;

    public UiCB() {
    }

    public boolean equals(Object var1) {
        boolean var2 = super.equals(var1);
        if(!var2 && var1 instanceof UiCB) {
            UiCB var3 = (UiCB)var1;
            var2 = this.a == var3.a;
        }

        return var2;
    }

    public int hashCode() {
        return this.a;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder(24);
        var1.append("group[").append(this.b).append("]");
        var1.append("child[").append(String.valueOf(this.uiCAArrayList.size())).append("]");
        return var1.toString();
    }

    public void a(boolean var1) {
        this.aBoolean = var1;
    }

    public boolean a() {
        return this.aBoolean;
    }


}