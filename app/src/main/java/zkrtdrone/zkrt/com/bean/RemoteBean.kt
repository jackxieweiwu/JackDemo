package zkrtdrone.zkrt.com.bean

import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.ToByte

/**
 * Created by jack_xie on 17-5-19.
 */
class RemoteBean {
    @ToByte(order = 0, description = "f")
    private val one: Byte = 0
    @ToByte(order = 1, description = "a")
    private val two: Byte = 0
    @ToByte(order = 2, description = "b")
    private val three: Byte = 0
    @ToByte(order = 3, description = "c")
    private val four: Byte = 0
    @ToByte(order = 4, description = "d")
    private val five: Byte = 0
    @ToByte(order = 5, description = "e")
    private val six: Byte = 0
    @ToByte(order = 6, description = "f")
    private val seven: Byte = 0
    @ToByte(order = 7, description = "crc")
    private val night: Byte = 0

    fun getOne(): Byte {
        return one
    }

    fun getTwo(): Byte {
        return two
    }

    fun getThree(): Byte {
        return three
    }

    fun getFour(): Byte {
        return four
    }

    fun getFive(): Byte {
        return five
    }

    fun getSix(): Byte {
        return six
    }

    fun getSeven(): Byte {
        return seven
    }

    fun getNight(): Byte {
        return night
    }
}