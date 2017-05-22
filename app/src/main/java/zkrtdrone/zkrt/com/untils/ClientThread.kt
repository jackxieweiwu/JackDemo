package zkrtdrone.zkrt.com.untils

import android.os.Bundle
import android.os.Handler
import android.os.Message
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by jack_xie on 17-5-19.
 */
class ClientThread(private val receiveHandler: Handler) : Runnable {
    private var socket: Socket? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    var isConnect = false

    override fun run() {
        try {
            socket = Socket("192.168.1.7", 26)
            isConnect = socket!!.isConnected
            inputStream = socket!!.getInputStream()
            outputStream = socket!!.getOutputStream()

            object : Thread() {
                override fun run() {
                    val buffer = ByteArray(8)
                    try {
                        while (socket!!.isConnected) {
                            val readSize = inputStream!!.read(buffer)
                            if (readSize == -1) {
                                inputStream!!.close()
                                outputStream!!.close()
                            }
                            if (readSize == 0) continue
                            if (readSize > 0) {
                                val msg = Message()
                                msg.what = 0x123
                                val bundle = Bundle()
                                bundle.putByteArray("buff", buffer)
                                msg.data = bundle
                                receiveHandler.sendMessage(msg)
                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }.start()
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
        } catch (e: UnknownHostException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }
}