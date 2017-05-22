package zkrtdrone.zkrt.com.view.fragment

import android.os.Bundle
import android.view.{LayoutInflater, View, ViewGroup}
import android.widget.ImageView
import butterknife.{Bind, OnClick}
import zkrtdrone.zkrt.com.R
import zkrtdrone.zkrt.com.databinding.FragmentDroneBinding
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils

/**
 * Created by jack_xie on 17-5-22.
 */
class DroneFragment extends AbsFragment[FragmentDroneBinding] {
  /*override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    null
  }*/
  @Bind(Array(R.id.img_drone_takeoff)) private[fragment] val img_drone_takeoff: ImageView = null
  @Bind(Array(R.id.img_drone_land)) private[fragment] val img_drone_land: ImageView = null
  @Bind(Array(R.id.img_drone_gohome)) private[fragment] val img_drone_gohome: ImageView = null
  @Bind(Array(R.id.img_drone_hover)) private[fragment] val img_drone_hover: ImageView = null
  @Bind(Array(R.id.img_drone_waypoint)) private[fragment] val img_drone_waypoint: ImageView = null

  protected def init(savedInstanceState: Bundle) {
  }

  protected def setLayoutId: Int = {
    return R.layout.fragment_drone
  }

  @OnClick(Array(R.id.img_drone_takeoff)) def imageBtnTakeOffLang(v: View) {
    if (GeneralUtils.isFastDoubleClick) return
  }

  protected def dataCallback(result: Int, obj: Any) {
  }

  protected def onDelayLoad() {
  }
}