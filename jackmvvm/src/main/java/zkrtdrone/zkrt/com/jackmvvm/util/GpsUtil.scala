package zkrtdrone.zkrt.com.jackmvvm.util

import java.util

import android.app.Activity
import android.content.{Context, Intent}
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication

/**
 * Created by jack_xie on 17-4-11.
 */
object GpsUtil {
  def getLocationSetingIntent: Intent = {
    val intent: Intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    return intent
  }

  var hasGPSDevice: Boolean = true

  def isGPSOpen: Boolean = {
    val lm: LocationManager = asInstanceOf[BaseApplication].getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
  }

  def setGPS(context: Activity, requestCode: Int) {
    try {
      context.startActivityForResult(getLocationSetingIntent, requestCode)
    } catch {
      case e: Exception => Log.e("", "setGPS  " + e.toString)
    }
  }

  def checkGPSDevice {
    val mgr: LocationManager = asInstanceOf[BaseApplication].getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]
    if (mgr == null) hasGPSDevice = false
    val providers: util.List[String] = mgr.getAllProviders
    if (providers == null) {
      hasGPSDevice = false
    }
    hasGPSDevice = providers.contains(LocationManager.GPS_PROVIDER)
  }
}
