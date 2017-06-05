package dji.midware.ui.d;

import android.content.Context;
import android.media.MediaPlayer;

import dji.common.camera.SettingsDefinitions;
import dji.log.DJILog;
import dji.midware.R;

/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDI {
    private final Context mContext;
    private MediaPlayer mediaPlayer;

    public UiDI(Context context) {
        this.mContext = context;
    }

    private int c() {
        return R.raw.shutter_1;
    }

    private int a(int var1) {
        int var2;
        switch(var1) {
            case 3:
                var2 = R.raw.shutter_3;
                break;
            case 4:
            case 6:
            default:
                var2 = R.raw.shutter_3;
                break;
            case 5:
                var2 = R.raw.shutter_5;
                break;
            case 7:
                var2 = R.raw.shutter_7;
        }

        return var2;
    }

    public void a(SettingsDefinitions.ShootPhotoMode shootPhotoMode, int var2) {
        if(shootPhotoMode != null) {
            int var3;
            switch(shootPhotoMode.value()) {
                case 1:
                    var3 = this.c();
                    break;
                case 2:
                    var3 = this.c();
                    break;
                case 3:
                    var3 = this.c();
                    break;
                case 4:
                    var3 = this.a(var2);
                    break;
                case 5:
                    var3 = this.a(var2);
                    break;
                default:
                    var3 = this.c();
            }

            try {
                mediaPlayer = MediaPlayer.create(mContext, var3);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer var1) {
                        var1.release();
                    }
                });
                mediaPlayer.start();
            } catch (Exception var5) {
                DJILog.d("startTakePic", var5.getMessage());
            }

        }
    }

    public void a() {
        try {
            mediaPlayer = MediaPlayer.create(mContext, R.raw.video_voice);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer var1) {
                    mediaPlayer.release();
                }
            });
            mediaPlayer.start();
        } catch (Exception var2) {
            DJILog.d("startVideo", var2.getMessage());
        }

    }

    public void b() {
        try {
            mediaPlayer = MediaPlayer.create(mContext, R.raw.end_video_record);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer var1) {
                    mediaPlayer.release();
                }
            });
            mediaPlayer.start();
        } catch (Exception var2) {
            DJILog.d("stopVideo", var2.getMessage());
        }

    }
}
