package zkrtdrone.zkrt.com.jackmvvm.util.byteUtil;

/**
 * Created by jack_Xie on 17-5-15.
 */

public class JByteUtil {
    /**
     * 将byte[]解析成Object
     * @param cls
     * @param bytes
     * @return
     */
    public static Object getObject(Class<?> cls, byte... bytes){
        return JByteToObj.getObject(cls, bytes);
    }

    /**
     * 将对象转成byte[]
     * @param object
     * @return
     */
    public static byte[] getBytes(Object object){
        return JObjToByte.getBytes(object);
    }
}
