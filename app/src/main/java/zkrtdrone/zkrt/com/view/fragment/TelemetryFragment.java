package zkrtdrone.zkrt.com.view.fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import butterknife.Bind;
import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.FlightControllerState;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentTelemetryBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.DensityUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment;
import zkrtdrone.zkrt.com.widght.AttitudeIndicator;
import zkrtdrone.zkrt.com.widght.RotateImageView;

/**
 * Created by jack_xie on 17-4-26.
 */

public class TelemetryFragment extends AbsFragment<FragmentTelemetryBinding> {
    @Bind(R.id.aiView) AttitudeIndicator attitudeIndicator;
    private boolean headingModeFPV;
    private RotateImageView rotateImageView;
    private View viewDrone;
    private GestureMapFragment gestureMapFragment;

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity()
                .getApplicationContext());
        headingModeFPV = prefs.getBoolean("pref_heading_mode", false);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        viewDrone = View.inflate(mActivity, R.layout.view_drone, null);
        rotateImageView = (RotateImageView) viewDrone.findViewById(R.id.img_drone);
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            JackApplication.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState flightControllerState) {
                    Attitude attitude = flightControllerState.getAttitude();
                    onOrientationUpdate(attitude);
                    JackApplication.droneloLat = flightControllerState.getAircraftLocation().getLatitude();
                    JackApplication.droneloLng = flightControllerState.getAircraftLocation().getLongitude();
                    onSpeedAltitudeAndClimbRateUpdate(flightControllerState);
                    rotateImageView.setAttitude(flightControllerState.getAttitude().yaw);
                    gestureMapFragment.getMapFragment().setDroneBitmap(loadBitmapFromView());
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

    private void onOrientationUpdate(Attitude attitude) {
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

    private void onSpeedAltitudeAndClimbRateUpdate(FlightControllerState flightControllerState) {
        float y = flightControllerState.getVelocityY();
        float x = flightControllerState.getVelocityX();
        float z;
        if(y<0) y = y*-1;
        if(x<0) x = x*-1;
        if(x<y) z = y; else z = x;

        if(flightControllerState != null) {
            getBinding().setStrAirSpeed(String.format("%3.1f", z));   //空速
            getBinding().setStrGroundSpeed(String.format("%3.1f", z));  //地速
            getBinding().setStrClimbRate(String.format("%3.1f", flightControllerState.getVelocityZ()));  //爬升
            double alt = flightControllerState.getAircraftLocation().getAltitude();
            getBinding().setStrAltitude(String.format("%3.1f", alt));
            getBinding().setStrTargetAltitude(String.format("%3.1f",getDiastances(
                    GpstoBaiduLatLng(new LatLng(JackApplication.droneloLat,JackApplication.droneloLng))
                    ,new LatLng(JackApplication.peploLat,JackApplication.peploLng))));


        }
    }

    private static double getDiastances(LatLng latlng1, LatLng latlng2) {
        double distances = DistanceUtil.getDistance(latlng1, latlng2);
        return distances;
    }

    private LatLng GpstoBaiduLatLng(LatLng latLng){
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(latLng);
        return converter.convert();
    }

    public Bitmap loadBitmapFromView() {
        if (viewDrone == null) {
            return null;
        }
        viewDrone.measure(View.MeasureSpec.makeMeasureSpec(DensityUtils.dip2px(mActivity, 40f),
                View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                DensityUtils.dip2px(mActivity, 45f), View.MeasureSpec.EXACTLY));
        viewDrone.layout(0, 0, viewDrone.getMeasuredWidth(), viewDrone.getMeasuredHeight());
        viewDrone.setDrawingCacheEnabled(true);
        viewDrone.buildDrawingCache();
        return viewDrone.getDrawingCache();
    }

    public void setGestureMapFragment(GestureMapFragment gestureMapFragment) {
        this.gestureMapFragment = gestureMapFragment;
    }
}