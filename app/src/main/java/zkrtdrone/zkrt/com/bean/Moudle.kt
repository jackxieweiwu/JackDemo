package zkrtdrone.zkrt.com.bean

import android.graphics.drawable.Drawable

/**
 * Created by jack_xie on 17-5-18.
 */
class Moudle {
    private var bitmap: Drawable? = null  //图片
    private var name: String = "" //名称
    private var status: Boolean = false  //状态

    fun MoudleBean(): Unit {}

    fun MoudleBean(bitmap: Drawable, name: String, status: Boolean): Unit {
        this.bitmap = bitmap
        this.name = name
        this.status = status
    }

    fun getBitmap(): Drawable {
        return bitmap as Drawable
    }

    fun setBitmap(bitmap: Drawable) {
        this.bitmap = bitmap
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getStatus(): Boolean {
        return status
    }

    fun setStatus(status: Boolean) {
        this.status = status
    }

    /*private var DEVICE_TYPE_TEMPERATURE: Int = 0  //温度模块
    private var DEVICE_TYPE_OBSTACLE: Int = 0     //壁障模块
    private var DEVICE_TYPE_CONTROL: Int = 0      //备用
    private var DEVICE_TYPE_CAMERA: Int = 0       //相机单光模块
    private var DEVICE_TYPE_GAS: Int = 0          //毒气模块
    private var DEVICE_TYPE_THROW: Int = 0 //抛投模块
    private var DEVICE_TYPE_STANDBY: Int = 0    //备用
    private var DEVICE_TYPE_PARACHUTE: Int = 0    //降落伞
    private var DEVICE_TYPE_IRRADIATE: Int = 0    // 照射模块
    private var DEVICE_TYPE_MEGAPHONE: Int = 0    // 喊话模块
    private var DEVICE_TYPE_BATTERY: Int = 0    //	电池模块
    private var DEVICE_TYPE_3DMODELING: Int = 0    // 三维模块
    private var DEVICE_TYPE_MULTICAMERA: Int = 0    //双光/三光模块*/
}