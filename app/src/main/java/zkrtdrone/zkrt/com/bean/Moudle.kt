package zkrtdrone.zkrt.com.bean


/**
 * Created by jack_xie on 17-5-18.
 */

class Moudle {
    private var drawableID: Int = 0  //图片
    private var name: String = "" //名称
    private var status: Boolean = false  //状态

    fun MoudleBean(): Unit {}

    fun getDrawableID(): Int {
        return drawableID
    }

    fun setDrawableID(drawableID: Int) {
        this.drawableID = drawableID
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
}
