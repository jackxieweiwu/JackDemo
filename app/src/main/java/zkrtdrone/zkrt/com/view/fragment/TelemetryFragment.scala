package zkrtdrone.zkrt.com.view.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.{LayoutInflater, View, ViewGroup}
import butterknife.Bind
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.{CoordinateConverter, DistanceUtil}
import dji.common.flightcontroller.{Attitude, FlightControllerState}
import zkrtdrone.zkrt.com.{JackApplication, R}
import zkrtdrone.zkrt.com.databinding.FragmentTelemetryBinding
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment
import zkrtdrone.zkrt.com.widght.AttitudeIndicator
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.droneloLat
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.droneloLng
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.peploLat
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.peploLng

/**
 * Created by jack_xie on 17-5-22.
 */
class TelemetryFragment extends AbsFragment[FragmentTelemetryBinding] {
  @Bind(Array(R.id.aiView)) private[fragment] val attitudeIndicator: AttitudeIndicator = null
  private var headingModeFPV: Boolean = false
  //private RotateImageView rotateImageView;
  //private View viewDrone;
  private var gestureMapFragment: GestureMapFragment = null

  override def onStart() {
    super.onStart()
    val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity.getApplicationContext)
    headingModeFPV = prefs.getBoolean("pref_heading_mode", false)
  }

  protected def init(savedInstanceState: Bundle) {
    /*viewDrone = View.inflate(mActivity, R.layout.view_drone, null);
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
            }*/
  }

  protected def onDelayLoad() {
  }

  protected def setLayoutId: Int = R.layout.fragment_telemetry

  protected def dataCallback(result: Int, obj: Any) {
  }

  private def onOrientationUpdate(attitude: Attitude) {
    if (attitude == null) return
    val r: Float = attitude.roll.toFloat
    val p: Float = attitude.pitch.toFloat
    var y: Float = attitude.yaw.toFloat
    if (!headingModeFPV & y < 0) y = 360 + y
    attitudeIndicator.setAttitude(r, p, y)
    getBinding.setStrRoll(String.format("%3.0f\u00B0", r))
    getBinding.setStrPitch(String.format("%3.0f\u00B0", p))
    getBinding.setStrYaw(String.format("%3.0f\u00B0", y))
  }

  private def onSpeedAltitudeAndClimbRateUpdate(flightControllerState: FlightControllerState) {
    var y: Float = flightControllerState.getVelocityY
    var x: Float = flightControllerState.getVelocityX
    var z: Float = 0.0f
    if (y < 0) y = y * -1
    if (x < 0) x = x * -1
    if (x < y) z = y
    else z = x
    if (flightControllerState != null) {
      getBinding.setStrAirSpeed(String.format("%3.1f", z)) //空速
      getBinding.setStrGroundSpeed(String.format("%3.1f", z)) //地速
      getBinding.setStrClimbRate(String.format("%3.1f", flightControllerState.getVelocityZ)) //爬升
      val alt: Double = flightControllerState.getAircraftLocation.getAltitude
      getBinding.setStrAltitude(String.format("%3.1f", alt))
      getBinding.setStrTargetAltitude(String.format("%3.1f", getDiastances(GpstoBaiduLatLng(new LatLng(droneloLat, droneloLng)), new LatLng(peploLat, peploLng))))
    }
  }

  private def getDiastances(latlng1: LatLng, latlng2: LatLng): Double = {
    val distances: Double = DistanceUtil.getDistance(latlng1, latlng2)
    distances
  }

  private def GpstoBaiduLatLng(latLng: LatLng): LatLng = {
    val converter: CoordinateConverter = new CoordinateConverter
    converter.from(CoordinateConverter.CoordType.GPS)
    converter.coord(latLng)
    converter.convert
  }

  /*public Bitmap loadBitmapFromView() {
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
*/ def setGestureMapFragment(gestureMapFragment: GestureMapFragment) {
    this.gestureMapFragment = gestureMapFragment
  }
}