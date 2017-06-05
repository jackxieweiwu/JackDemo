package dji.midware.ui.d;

import dji.common.camera.SettingsDefinitions.Aperture;
import dji.common.camera.SettingsDefinitions.ExposureCompensation;
import dji.common.camera.SettingsDefinitions.ISO;
import dji.common.camera.SettingsDefinitions.ShutterSpeed;
import dji.common.camera.SettingsDefinitions.VideoFrameRate;
import dji.common.camera.SettingsDefinitions.VideoResolution;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDD {
    public static String a(ShutterSpeed var0) {
        String var1 = var0.name();
        var1 = var1.replace("SHUTTER_SPEED_1_", "").replaceAll("(\\d+)_DOT_(\\d+)", "$1.$2").replaceAll("SHUTTER_SPEED_([0-9.]+)", "$1\"").replaceAll("DOT_(\\d+)", "1.$1\"");
        return var1;
    }

    public static String a(Aperture var0) {
        String var1 = var0.name();
        String var2 = var1.replace("F_", "").replaceAll("_DOT_0", "").replaceAll("_DOT_", ".");
        return var2;
    }

    public static String a(ISO var0) {
        String var1 = var0.name();
        String var2 = var1.replace("ISO_", "");
        return var2;
    }

    public static String a(ExposureCompensation var0) {
        String var1 = var0.name();
        String var2 = var1.replace("N_0_0", "0").replaceAll("N_", "-").replaceAll("P_", "+").replaceAll("_", ".");
        return var2;
    }

    public static String a(VideoResolution videoResolution) {
        String var1 = "Null";
        if(videoResolution != null) {
            switch(videoResolution.value()) {
                case 1:
                    var1 = "480P";
                    break;
                case 2:
                    var1 = "512P";
                    break;
                case 3:
                    var1 = "720P";
                    break;
                case 4:
                    var1 = "1080P";
                    break;
                case 5:
                case 6:
                    var1 = "2.7K";
                    break;
                case 7:
                case 8:
                case 9:
                    var1 = "4K";
                    break;
                case 10:
                case 11:
                    var1 = "4.5K";
                    break;
                case 12:
                    var1 = "5K";
                    break;
                default:
                    var1 = "Unknown";
            }
        }

        return var1;
    }

    public static String a(VideoFrameRate var0) {
        String var1 = var0.toString();
        Matcher var3 = Pattern.compile("FRAME_RATE_(\\d{2,3})_DOT_.*").matcher(var1);
        String var2;
        if(var3.find()) {
            String var4 = var3.group(1);
            var2 = Integer.toString(Integer.valueOf(var4).intValue() + 1);
        } else {
            var3 = Pattern.compile("FRAME_RATE_(\\d{2,3})_FPS").matcher(var1);
            if(var3.find()) {
                var2 = var3.group(1);
            } else {
                var2 = "Null";
            }
        }

        return var2;
    }
}
