package dji.midware.ui.d;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDK {
    private static final float[] a = new float[]{-1.0F, 0.0F, 0.0F, 0.0F, 255.0F, 0.0F, -1.0F, 0.0F, 0.0F, 255.0F, 0.0F, 0.0F, -1.0F, 0.0F, 255.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F};

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Drawable a(Context context, @DrawableRes int var1) {
        Drawable var2 = context.getDrawable(var1);
        var2 = var2.mutate();
        var2.setColorFilter(new ColorMatrixColorFilter(a));
        return var2;
    }
}
