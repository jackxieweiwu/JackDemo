package zkrtdrone.zkrt.com.view.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import butterknife.Bind;
import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.FlightControllerState;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentTelemetryBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.SharePreUtil;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.widght.AttitudeIndicator;

/**
 * Created by jack_xie on 17-4-26.
 */

public class TelemetryFragment extends AbsFragment<FragmentTelemetryBinding> {
    @Bind(R.id.aiView) AttitudeIndicator attitudeIndicator;
    private boolean headingModeFPV;


    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()
                .getApplicationContext());
        headingModeFPV = prefs.getBoolean("pref_heading_mode", false);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            JackApplication.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState flightControllerState) {
                    Attitude attitude = flightControllerState.getAttitude();
                    onOrientationUpdate(attitude);
                    onSpeedAltitudeAndClimbRateUpdate(attitude);
                }
            });
        }
    }

    @Override
    protected void onDelayLoad() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_telemetry;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    public void onOrientationUpdate(Attitude attitude) {
        if(attitude == null)
            return;

        float r = (float) attitude.roll;
        float p = (float) attitude.pitch;
        float y = (float) attitude.yaw;

        if (!headingModeFPV & y < 0) {
            y = 360 + y;
        }
        attitudeIndicator.setAttitude(r, p, y);
        getBinding().setStrRoll(String.format("%3.0f\u00B0", r));
        getBinding().setStrPitch(String.format("%3.0f\u00B0", p));
        getBinding().setStrYaw(String.format("%3.0f\u00B0", y));
    }

    public void onSpeedAltitudeAndClimbRateUpdate(Attitude attitude) {
       /* if(speed != null) {
            airSpeed.setText(String.format("%3.1f", speed.getAirSpeed()));
            groundSpeed.setText(String.format("%3.1f", speed.getGroundSpeed()));
            climbRate.setText(String.format("%3.1f", speed.getVerticalSpeed()));
        }

        if(attitude != null) {
            double alt = attitude.getAltitude();
            double targetAlt = attitude.getTargetAltitude();

            getBinding().setStrAltitude(String.format("%3.1f", alt));
            targetAltitude.setText(String.format("%3.1f", targetAlt));
        }*/
    }
}
