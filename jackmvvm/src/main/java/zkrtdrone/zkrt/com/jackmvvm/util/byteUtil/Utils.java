package zkrtdrone.zkrt.com.jackmvvm.util.byteUtil;

import java.lang.reflect.Method;

/**
 * Created by jack_xie on 17-5-17.
 */

public class Utils {
    private Utils() {
    }
    static Method getAccessibleMethod(String methodName, Class<?> clazz) {
        try {
            Method ms = clazz.getDeclaredMethod(methodName);
            ms.setAccessible(true);
            return ms;
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 10->2
     * @param n
     * @return
     */
    public static String toBinary(int n){
        n|=256;
        String str=Integer.toBinaryString(n);
        int len=str.length();
        return str.substring(len-8,len);
    }

    /**
     10->16
     将给定的十进制整数除以基数16，余数便是等值的16进制的最低位。
     将上一步的商再除以基数16，余数便是等值的16进制数的次低位。
     重复上一步骤，直到最后所得的商等于0为止。各次除得的余数，便是16进制各位的数，最后一次的余数是最高位
     */
    public static String toHex(int n){
        /*思路：除16取余*/
        StringBuilder temp = new StringBuilder();
        while(n/16 >= 1){
            int aa = n/16;
            int bb = n%16;
            //0123456789 10 11 12 13 14 15
            //0123456789 A  B  C  D  E  F
            String str = "";
            if(bb == 10){
                str = "A";
            }else if(bb == 11){
                str = "B";
            }else if(bb == 12){
                str = "C";
            }else if(bb == 13){
                str = "D";
            }else if(bb == 14){
                str = "E";
            }else if(bb == 15){
                str = "F";
            }else{
                str = bb+"";
            }
            temp.append(str);
            n = aa;
            if(n/16 < 1){//补上最后一位
                temp.append(n);
            }
        }
        return temp.reverse().toString();
    }
}
