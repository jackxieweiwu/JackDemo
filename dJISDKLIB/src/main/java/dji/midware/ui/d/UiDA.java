package dji.midware.ui.d;

import java.util.HashMap;
import java.util.Map;

import dji.common.bus.UILibEventBus.UILibEvent;
import dji.common.bus.UILibEventBus;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiDA {
    public static void a() {
        UILibEvent var0 = new UILibEvent("UILibraryIncludedWithSDK", "Session", (Map)null);
        UILibEventBus.getInstance().post(var0);
    }

    public static void b() {
        StackTraceElement[] var0 = Thread.currentThread().getStackTrace();

        for(int var1 = 3; var1 < 6; ++var1) {
            StackTraceElement var2 = var0[var1];
            String var3 = var2.getClassName();
            if(var3.startsWith("dji.ui.widget")) {
                a(var3, "WidgetUsed");
                return;
            }

            if(var3.startsWith("dji.ui.panel")) {
                a(var3, "PanelUsed");
                return;
            }
        }

    }

    private static void a(String var0, String var1) {
        String var2 = var0.substring(var0.lastIndexOf(46) + 1);
        HashMap var3 = new HashMap();
        var3.put("type", var2);
        UILibEventBus.UILibEvent var4 = new UILibEventBus.UILibEvent(var1, "InterfaceUsage", var3);
        UILibEventBus.getInstance().post(var4);
    }
}
