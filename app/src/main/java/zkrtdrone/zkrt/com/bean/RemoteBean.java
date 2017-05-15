package zkrtdrone.zkrt.com.bean;

import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.JByte;

/**
 * Created by jack_xie on 17-5-15.
 * 遥感实体类
 */

public class RemoteBean {
    @JByte(index = 0)
    private int oneBtn;
    private int twoBtn;
    private int threeBtn;
    private int fourBtn;
    private int fiveBtn;
    private int sixBtn;
    private int sevenBtn;
    private int nightBtn;
    private int upOrDow;
    private int leftOrRight;
    private int zoom;
}
