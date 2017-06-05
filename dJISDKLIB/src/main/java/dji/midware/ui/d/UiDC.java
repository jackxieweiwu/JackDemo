package dji.midware.ui.d;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import dji.log.DJILog;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDC {
    private MediaPlayer a;

    public UiDC() {
    }

    public void a(Context context, int var2) {
        try {
            this.a = MediaPlayer.create(context, var2);
            this.a.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer var1) {
                    UiDC.this.a.release();
                }
            });
            AudioManager var3 = (AudioManager)context.getSystemService("audio");
            float var4 = (float)var3.getStreamMaxVolume(3);
            float var5 = (float)var3.getStreamVolume(3);
            float var6 = var5 / var4;
            if(var6 < 0.3F) {
                var6 = 0.3F;
            }

            this.a.setVolume(var6, var6);
            this.a.start();
        } catch (Exception var7) {
            DJILog.d("PlaySound", var7.getMessage());
        }

    }

    public static void a(Context var0) {
    }
}
