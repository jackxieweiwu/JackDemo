package dji.midware.ui.d;

import android.location.Location;

import java.util.Arrays;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDF {
    private static final float[] a = new float[2];
    private static final float[] b = new float[2];

    public static boolean a(double var0) {
        double var2 = Math.abs(var0);
        return 1.0E-6D < var2 && var2 <= 180.0D;
    }

    public static boolean b(double var0) {
        double var2 = Math.abs(var0);
        return 1.0E-6D < var2 && var2 <= 90.0D;
    }

    public static float a(double var0, double var2, double var4, double var6) {
        Arrays.fill(a, 0.0F);
        Location.distanceBetween(var0, var2, var4, var6, a);
        if(a[0] <= 0.0F || a[0] > 100000.0F) {
            a[0] = 0.0F;
        }

        return a[0];
    }

    public static float[] a(double var0, double var2, double var4, double var6, boolean var8) {
        b[0] = 0.0F;
        b[1] = 0.0F;
        float var9 = a(var0, var2, var4, var6);
        if(var9 <= 0.0F) {
            b[0] = 0.0F;
        } else {
            float var10 = a(var0, var6, var4, var6);
            double var11 = Math.toDegrees(Math.asin((double)(var10 / var9)));
            if(var4 > var0) {
                if(var6 <= var2) {
                    var11 = 180.0D - var11;
                }
            } else if(var6 > var2) {
                var11 = 360.0D - var11;
            } else {
                var11 += 180.0D;
            }

            if(Double.isNaN(var11)) {
                var11 = 0.0D;
            }

            b[0] = (float)var11;
        }

        b[1] = var9;
        return b;
    }

    public static int[] a(int var0) {
        int[] var1 = new int[]{var0 % 60, 0, 0};
        var0 /= 60;
        var1[1] = var0 % 60;
        var0 /= 60;
        var1[2] = var0;
        return var1;
    }
}
