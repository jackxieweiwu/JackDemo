package dji.midware.ui.d;

import android.text.TextUtils;
import android.widget.TextView;

import dji.midware.ui.c.a.UiCAF;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDB {
    public static void a(TextView var0, UiCAF var1) {
        float var2 = 1.0F;
        float var3 = 1.0F;
        String var4 = var1.f();
        float var5;
        if(!TextUtils.isEmpty(var4)) {
            var5 = var0.getPaint().measureText(var4);
            float var6 = (float)var0.getMeasuredWidth();
            var3 = var6 / var5;
        }

        float var7 = (float)var0.getMeasuredHeight();
        var5 = (float)var0.getLineHeight();
        if(var5 > var7) {
            var2 = var7 / var5;
        }

        var2 = Math.min(var2, var3);
        var7 = var0.getTextSize();
        var5 = var7 * var2;
        if(var5 > 0.0F) {
            var0.setTextSize(0, var5);
        }

    }
}
