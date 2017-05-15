package zkrtdrone.zkrt.com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.JByte;

/**
 * Created by jack_xie on 17-5-15.
 */

public class DemoListBean {
    @JByte(index = 1, lenght = 6)
    ArrayList<Integer> integers = new ArrayList<>();

    @JByte(index = 2, lenght = 6)
    int[] a = new int[10];

    public String getArrayString(List<?> list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(i);
            sb.append('=');
            sb.append(list.get(i));
            sb.append(',');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "DemoListBean{" +
                "a=" + Arrays.toString(a) +
                ", integers=" + getArrayString(integers) +
                '}';
    }
}
