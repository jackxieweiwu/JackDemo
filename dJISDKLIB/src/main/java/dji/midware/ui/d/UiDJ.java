package dji.midware.ui.d;

import android.content.Context;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDJ {
    public static int a(Context context, float var1) {
        float var2 = context.getResources().getDisplayMetrics().density;
        return (int)(var1 * var2 + 0.5F);
    }

    public static float b(Context context, float var1) {
        float var2 = context.getResources().getDisplayMetrics().density;
        return var1 * var2;
    }
}
