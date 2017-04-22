package zkrtdrone.zkrt.com.jackmvvm.util

import android.content.res.Resources
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication

/**
 * Created by jack_xie on 17-4-11.
 */
object App{
  def getResources: Resources = {
    return asInstanceOf[BaseApplication].getResources
  }
}
