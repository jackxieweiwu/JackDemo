package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import dji.common.flightcontroller.FlightMode;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.midware.R;
import dji.midware.data.model.P3.DataOsdGetPushCommon;
import dji.midware.data.model.P3.DataOsdGetPushHome;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBR;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class FlightModeWidget extends DULFrameLayout {
    private final String TAG;
    private UiCBR widgetAppearances;
    private TextView valueTextView;
    private FlightMode flightMode;
    private boolean isVisionUsed;
    private int flyModeInStringId;
    private FlightControllerKey flightModeKey;
    private FlightControllerKey visionUsedKey;

    public FlightModeWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public FlightModeWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public FlightModeWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.TAG = "FlyingModeWidget";
        UiDA.b();
    }

    @MainThread
    @Keep
    public void onIsVisionUsedChange(boolean var1) {
    }

    @MainThread
    @Keep
    public void onFlyControllerModeChange(@Nullable FlightMode var1) {
        String var2 = (String)this.getContext().getResources().getText(this.flyModeInStringId);
        this.valueTextView.setText(var2);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.valueTextView = (TextView)this.getViewById(R.id.textview_flight_mode_string);
        this.flyModeInStringId = R.string.string_default_value;
        if(this.valueTextView != null) {
            String var4 = (String)this.getContext().getResources().getText(this.flyModeInStringId);
            this.valueTextView.setText(var4);
        }

    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBR();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.flightModeKey = FlightControllerKey.create("FlightMode");
        this.visionUsedKey = FlightControllerKey.create("IsVisionPositioningSensorBeingUsed");
        this.addDependentKey(this.flightModeKey);
        this.addDependentKey(this.visionUsedKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.flightModeKey)) {
            this.flightMode = (FlightMode)var1;
        } else if(var2.equals(this.visionUsedKey)) {
            this.isVisionUsed = ((Boolean)var1).booleanValue();
        }

        this.flyModeInStringId = getFlycModeResId(this.flightMode, this.isVisionUsed);
    }

    public void updateWidget(DJIKey var1) {
        this.onFlyControllerModeChange(this.flightMode);
        this.onIsVisionUsedChange(this.isVisionUsed);
    }

    private static boolean checkUseNewModeChannel() {
        return checkUseNewModeChannel(DataOsdGetPushCommon.getInstance());
    }

    private static boolean checkUseNewModeChannel(DataOsdGetPushCommon var0) {
        return var0.getFlycVersion() >= 11;
    }

    public static int getFlycModeResId(FlightMode var0, boolean var1) {
        return checkUseNewModeChannel()?getFlycModeResIdAfterP4(var0, var1, false):getFlycModeResIdBeforeP4(var0, var1, false);
    }

    private static int getFlycModeResIdAfterP4(FlightMode var0, boolean var1, boolean var2) {
        int[] var3 = new int[]{R.string.string_default_value, R.string.off};
        if(FlightMode.MANUAL == var0) {
            var3[0] = R.string.ctrl_mode_manual;
        } else if(FlightMode.ATTI == var0) {
            var3[0] = R.string.ctrl_mode_atti;
        } else if(FlightMode.ATTI_COURSE_LOCK == var0) {
            var3[0] = R.string.ctrl_mode_atti;
            var3[1] = R.string.direct_lock_cl;
        } else if(FlightMode.AUTO_LANDING == var0) {
            var3[0] = R.string.ctrl_mode_landing;
        } else if(FlightMode.ASSISTED_TAKEOFF == var0) {
            var3[0] = R.string.ctrl_mode_takeoff;
        } else if(FlightMode.AUTO_TAKEOFF == var0) {
            var3[0] = R.string.ctrl_mode_takeoff;
        } else if(FlightMode.GO_HOME == var0) {
            var3[0] = R.string.ctrl_mode_gohome;
        } else if(FlightMode.GPS_ATTI == var0) {
            var3[0] = R.string.ctrl_mode_gps;
        } else if(FlightMode.GPS_ATTI_WRISTBAND == var0) {
            var3[0] = R.string.ctrl_mode_gps;
        } else if(FlightMode.GPS_COURSE_LOCK == var0) {
            var3[0] = R.string.ctrl_mode_gps;
            var3[1] = R.string.direct_lock_cl;
        } else if(FlightMode.GPS_HOME_LOCK == var0) {
            var3[0] = R.string.ctrl_mode_gps;
            var3[1] = R.string.direct_lock_hl;
        } else if(FlightMode.GPS_HOT_POINT == var0) {
            var3[0] = R.string.ctrl_mode_gps;
            var3[1] = R.string.direct_lock_pl;
        } else if(FlightMode.JOYSTICK == var0) {
            var3[0] = R.string.ctrl_mode_joystick;
        } else if(FlightMode.ACTIVE_TRACK == var0) {
            var3[0] = R.string.ctrl_mode_ftracking;
        } else if(FlightMode.TAP_FLY == var0) {
            var3[0] = R.string.ctrl_mode_fpointing;
        } else if(FlightMode.GPS_SPORT == var0) {
            var3[0] = R.string.ctrl_mode_sport;
        } else if(FlightMode.GPS_NOVICE == var0) {
            var3[0] = R.string.ctrl_mode_novice;
        }

        if(var3[0] == R.string.ctrl_mode_gps && var1) {
            var3[0] = R.string.ctrl_mode_opti;
        }

        return var3[0];
    }

    private static boolean isMultipleModeOpen(boolean var0, boolean var1) {
        return !var0 && var1;
    }

    private static int getFlycModeResIdBeforeP4(FlightMode var0, boolean var1, boolean var2) {
        int[] var3 = new int[]{R.string.string_default_value, R.string.off};
        if(FlightMode.MANUAL == var0) {
            var3[0] = R.string.ctrl_mode_manual;
        } else if(FlightMode.ATTI == var0) {
            var3[0] = R.string.ctrl_mode_atti;
        } else if(FlightMode.ATTI_COURSE_LOCK == var0) {
            var3[0] = R.string.ctrl_mode_atti;
            var3[1] = R.string.direct_lock_cl;
        } else if(FlightMode.AUTO_LANDING == var0) {
            var3[0] = R.string.ctrl_mode_landing;
        } else if(FlightMode.ASSISTED_TAKEOFF == var0) {
            var3[0] = R.string.ctrl_mode_takeoff;
        } else if(FlightMode.AUTO_TAKEOFF == var0) {
            var3[0] = R.string.ctrl_mode_takeoff;
        } else if(FlightMode.GO_HOME == var0) {
            var3[0] = R.string.ctrl_mode_gohome;
        } else if(FlightMode.GPS_ATTI == var0) {
            var3[0] = R.string.ctrl_mode_pgps;
        } else if(FlightMode.GPS_ATTI_WRISTBAND == var0) {
            var3[0] = R.string.ctrl_mode_pgps;
        } else if(FlightMode.GPS_COURSE_LOCK == var0) {
            var3[0] = R.string.ctrl_mode_pgps;
            var3[1] = R.string.direct_lock_cl;
        } else if(FlightMode.GPS_HOME_LOCK == var0) {
            var3[0] = R.string.ctrl_mode_pgps;
            var3[1] = R.string.direct_lock_hl;
        } else if(FlightMode.GPS_HOT_POINT == var0) {
            var3[0] = R.string.ctrl_mode_pgps;
            var3[1] = R.string.direct_lock_pl;
        } else if(FlightMode.JOYSTICK == var0) {
            var3[0] = R.string.ctrl_mode_joystick;
        } else if(FlightMode.ACTIVE_TRACK == var0) {
            var3[0] = R.string.ctrl_mode_ftracking;
        } else if(FlightMode.TAP_FLY == var0) {
            var3[0] = R.string.ctrl_mode_fpointing;
        } else if(FlightMode.GPS_SPORT == var0) {
            var3[0] = R.string.ctrl_mode_sport;
        } else if(FlightMode.GPS_NOVICE == var0) {
            var3[0] = R.string.ctrl_mode_novice;
        }

        if(var3[0] == R.string.ctrl_mode_pgps) {
            if(var1) {
                var3[0] = R.string.ctrl_mode_popti;
            }
        } else if(var3[0] == R.string.ctrl_mode_atti) {
            DataOsdGetPushCommon.RcModeChannel var4 = DataOsdGetPushCommon.getInstance().getModeChannel();
            DataOsdGetPushHome var5 = DataOsdGetPushHome.getInstance();
            boolean var6 = isMultipleModeOpen(var5.isBeginnerMode(), var5.isMultipleModeOpen());
            if(!var6 || var4 == DataOsdGetPushCommon.RcModeChannel.CHANNEL_F || var4 == DataOsdGetPushCommon.RcModeChannel.CHANNEL_P) {
                var3[0] = R.string.ctrl_mode_patti;
            }
        }

        return var3[0];
    }
}
