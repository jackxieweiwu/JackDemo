package dji.midware.ui.internal.a;

import dji.common.camera.SettingsDefinitions;
import dji.midware.R;
import dji.midware.ui.base.UiBaseMView;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_G  {
    public static int[] a;
    public static byte[][] b;
    public static int[] c;
    public static int[] d;
    public static int[] e;
    public static int[][] f;

    public static int[] a() {
        return new int[]{R.string.camera_photomode_single, R.string.camera_photomode_hdr, R.string.camera_photomode_burst, R.string.camera_photomode_aeb, R.string.camera_photomode_time, R.string.camera_photomode_timelapse, R.string.camera_photomode_panorama};
    }

    public static int[] b() {
        return new int[]{R.drawable.ic_photo_mode_nor, R.drawable.ic_photo_mode_hdr, R.drawable.ic_photo_mode_continuous, R.drawable.ic_photo_mode_aeb, R.drawable.ic_photo_mode_autotimer, R.drawable.ic_photo_mode_autotimer, R.drawable.ic_photo_mode_360};
    }

    public static int[] c() {
        return new int[]{SettingsDefinitions.ShootPhotoMode.SINGLE.value(), SettingsDefinitions.ShootPhotoMode.HDR.value(), SettingsDefinitions.ShootPhotoMode.BURST.value(), SettingsDefinitions.ShootPhotoMode.AEB.value(), SettingsDefinitions.ShootPhotoMode.INTERVAL.value(), SettingsDefinitions.ShootPhotoMode.TIME_LAPSE.value(), SettingsDefinitions.ShootPhotoMode.PANORAMA.value()};
    }

    public static int[][] d() {
        return new int[][]{null, null, {3, 5, 7}, {3, 5}, {10, 20, 30}, null, null};
    }

    public static int a(int var0, int var1) {
        return SettingsDefinitions.ShootPhotoMode.HDR.value() == var0? R.drawable.ic_photo_mode_hdr:(SettingsDefinitions.ShootPhotoMode.BURST.value() == var0?(var1 == 14? R.drawable.ic_photo_mode_continuous_14:(var1 == 10? R.drawable.ic_photo_mode_continuous_10:(var1 == 7? R.drawable.ic_photo_mode_continuous_7:(var1 == 5? R.drawable.ic_photo_mode_continuous_5: R.drawable.ic_photo_mode_continuous_3)))):(SettingsDefinitions.ShootPhotoMode.AEB.value() == var0?(var1 == 7? R.drawable.ic_photo_mode_aeb_continuous_7:(var1 == 5? R.drawable.ic_photo_mode_aeb_continuous_5: R.drawable.ic_photo_mode_aeb_continuous_3)):(SettingsDefinitions.ShootPhotoMode.INTERVAL.value() == var0?(var1 == 60? R.drawable.ic_photo_mode_timepause_60s:(var1 == 30? R.drawable.ic_photo_mode_timepause_30s:(var1 == 20? R.drawable.ic_photo_mode_timepause_20s:(var1 == 15? R.drawable.ic_photo_mode_timepause_15s:(var1 == 10? R.drawable.ic_photo_mode_timepause_10s:(var1 == 7? R.drawable.ic_photo_mode_timepause_7s:(var1 == 3? R.drawable.ic_photo_mode_timepause_3s:(var1 == 2? R.drawable.ic_photo_mode_timepause_2s: R.drawable.ic_photo_mode_timepause_5s)))))))): R.drawable.ic_photo_mode_nor)));
    }

    static {
        a = new int[]{R.drawable.ic_advanced_more_none, R.drawable.ic_advanced_more_ps_landscape, R.drawable.ic_advanced_more_ps_soft, R.drawable.ic_advanced_more_ps_custom};
        b = new byte[][]{{0, 0, 0}, {1, 1, 0}, {-1, 0, 0}, {0, 1, 0}};
        c = new int[]{-3, 3};
        d = new int[]{R.drawable.ic_advanced_more_vsf_mov, R.drawable.ic_advanced_more_vsf_mp4};
        e = new int[]{R.drawable.ic_advanced_more_vt_pal, R.drawable.ic_advanced_more_videotype_ntsc};
        f = new int[][]{{R.drawable.ic_advanced_more_vf_1280x720_24p, R.drawable.ic_advanced_more_vf_1280x720_24p, R.drawable.ic_advanced_more_vf_1280x720_25p, R.drawable.ic_advanced_more_vf_1280x720_30p, R.drawable.ic_advanced_more_vf_1280x720_30p, R.drawable.ic_advanced_more_vf_1280x720_48p, R.drawable.ic_advanced_more_vf_1280x720_48p, R.drawable.ic_advanced_more_vf_1280x720_50p, R.drawable.ic_advanced_more_vf_1280x720_60p, R.drawable.ic_advanced_more_vf_1280x720_60p, R.drawable.ic_advanced_more_vf_1280x720_96p, R.drawable.ic_advanced_more_vf_1280x720_100p, R.drawable.ic_advanced_more_vf_1280x720_120p, R.drawable.ic_advanced_more_vf_1280x720_180p, R.drawable.ic_advanced_more_vf_1280x720_240p}, {R.drawable.ic_advanced_more_vf_1280x720_24p, R.drawable.ic_advanced_more_vf_1280x720_24p, R.drawable.ic_advanced_more_vf_1280x720_25p, R.drawable.ic_advanced_more_vf_1280x720_30p, R.drawable.ic_advanced_more_vf_1280x720_30p, R.drawable.ic_advanced_more_vf_1280x720_48p, R.drawable.ic_advanced_more_vf_1280x720_48p, R.drawable.ic_advanced_more_vf_1280x720_50p, R.drawable.ic_advanced_more_vf_1280x720_60p, R.drawable.ic_advanced_more_vf_1280x720_60p, R.drawable.ic_advanced_more_vf_1280x720_96p, R.drawable.ic_advanced_more_vf_1280x720_100p, R.drawable.ic_advanced_more_vf_1280x720_120p, R.drawable.ic_advanced_more_vf_1280x720_180p, R.drawable.ic_advanced_more_vf_1280x720_240p}, {R.drawable.ic_advanced_more_vf_1280x720_24p, R.drawable.ic_advanced_more_vf_1280x720_24p, R.drawable.ic_advanced_more_vf_1280x720_25p, R.drawable.ic_advanced_more_vf_1280x720_30p, R.drawable.ic_advanced_more_vf_1280x720_30p, R.drawable.ic_advanced_more_vf_1280x720_48p, R.drawable.ic_advanced_more_vf_1280x720_48p, R.drawable.ic_advanced_more_vf_1280x720_50p, R.drawable.ic_advanced_more_vf_1280x720_60p, R.drawable.ic_advanced_more_vf_1280x720_60p, R.drawable.ic_advanced_more_vf_1280x720_96p, R.drawable.ic_advanced_more_vf_1280x720_100p, R.drawable.ic_advanced_more_vf_1280x720_120p, R.drawable.ic_advanced_more_vf_1280x720_180p, R.drawable.ic_advanced_more_vf_1280x720_240p}, {R.drawable.ic_advanced_more_vf_1920x1080_24p, R.drawable.ic_advanced_more_vf_1920x1080_24p, R.drawable.ic_advanced_more_vf_1920x1080_25p, R.drawable.ic_advanced_more_vf_1920x1080_30p, R.drawable.ic_advanced_more_vf_1920x1080_30p, R.drawable.ic_advanced_more_vf_1920x1080_48p, R.drawable.ic_advanced_more_vf_1920x1080_48p, R.drawable.ic_advanced_more_vf_1920x1080_50p, R.drawable.ic_advanced_more_vf_1920x1080_60p, R.drawable.ic_advanced_more_vf_1920x1080_60p, R.drawable.ic_advanced_more_vf_1920x1080_96p, R.drawable.ic_advanced_more_vf_1920x1080_100p, R.drawable.ic_advanced_more_vf_1920x1080_120p, R.drawable.ic_advanced_more_vf_1920x1080_180p, R.drawable.ic_advanced_more_vf_1920x1080_240p}, {R.drawable.ic_advanced_more_vf_2p7k_24p, R.drawable.ic_advanced_more_vf_2p7k_24p, R.drawable.ic_advanced_more_vf_2p7k_25p, R.drawable.ic_advanced_more_vf_2p7k_30p, R.drawable.ic_advanced_more_vf_2p7k_30p, R.drawable.ic_advanced_more_vf_2p7k_48p, R.drawable.ic_advanced_more_vf_2p7k_48p, R.drawable.ic_advanced_more_vf_2p7k_50p, R.drawable.ic_advanced_more_vf_2p7k_60p, R.drawable.ic_advanced_more_vf_2p7k_60p}, {R.drawable.ic_advanced_more_vf_2p7k_24p, R.drawable.ic_advanced_more_vf_2p7k_24p, R.drawable.ic_advanced_more_vf_2p7k_25p, R.drawable.ic_advanced_more_vf_2p7k_30p, R.drawable.ic_advanced_more_vf_2p7k_30p, R.drawable.ic_advanced_more_vf_2p7k_48p, R.drawable.ic_advanced_more_vf_2p7k_48p, R.drawable.ic_advanced_more_vf_2p7k_50p, R.drawable.ic_advanced_more_vf_2p7k_60p, R.drawable.ic_advanced_more_vf_2p7k_60p}, {R.drawable.ic_advanced_more_vf_3840x2160_24p, R.drawable.ic_advanced_more_vf_3840x2160_24p, R.drawable.ic_advanced_more_vf_3840x2160_25p, R.drawable.ic_advanced_more_vf_3840x2160_30p, R.drawable.ic_advanced_more_vf_3840x2160_30p, R.drawable.ic_advanced_more_vf_3840x2160_48p, R.drawable.ic_advanced_more_vf_3840x2160_48p, R.drawable.ic_advanced_more_vf_3840x2160_50p, R.drawable.ic_advanced_more_vf_3840x2160_60p, R.drawable.ic_advanced_more_vf_3840x2160_60p}, {R.drawable.ic_advanced_more_vf_3840x2160_24p, R.drawable.ic_advanced_more_vf_3840x2160_24p, R.drawable.ic_advanced_more_vf_3840x2160_25p, R.drawable.ic_advanced_more_vf_3840x2160_30p, R.drawable.ic_advanced_more_vf_3840x2160_30p, R.drawable.ic_advanced_more_vf_3840x2160_48p, R.drawable.ic_advanced_more_vf_3840x2160_48p, R.drawable.ic_advanced_more_vf_3840x2160_50p, R.drawable.ic_advanced_more_vf_3840x2160_60p, R.drawable.ic_advanced_more_vf_3840x2160_60p}, {R.drawable.ic_advanced_more_vf_4096x2160_24p, R.drawable.ic_advanced_more_vf_4096x2160_24p, R.drawable.ic_advanced_more_vf_4096x2160_25p, R.drawable.ic_advanced_more_vf_4096x2160_30p, R.drawable.ic_advanced_more_vf_4096x2160_30p, R.drawable.ic_advanced_more_vf_4096x2160_48p, R.drawable.ic_advanced_more_vf_4096x2160_48p, R.drawable.ic_advanced_more_vf_4096x2160_50p, R.drawable.ic_advanced_more_vf_4096x2160_60p, R.drawable.ic_advanced_more_vf_4096x2160_60p}, {R.drawable.ic_advanced_more_vf_4608x2160_24p, R.drawable.ic_advanced_more_vf_4608x2160_24p, R.drawable.ic_advanced_more_vf_4608x2160_25p, R.drawable.ic_advanced_more_vf_4608x2160_30p, R.drawable.ic_advanced_more_vf_4608x2160_30p}, {R.drawable.ic_advanced_more_vf_4608x2160_24p, R.drawable.ic_advanced_more_vf_4608x2160_24p, R.drawable.ic_advanced_more_vf_4608x2160_25p, R.drawable.ic_advanced_more_vf_4608x2160_30p, R.drawable.ic_advanced_more_vf_4608x2160_30p}, {R.drawable.ic_advanced_more_vf_4608x2160_24p, R.drawable.ic_advanced_more_vf_4608x2160_24p, R.drawable.ic_advanced_more_vf_4608x2160_25p, R.drawable.ic_advanced_more_vf_4608x2160_30p, R.drawable.ic_advanced_more_vf_4608x2160_30p}};
    }
}
