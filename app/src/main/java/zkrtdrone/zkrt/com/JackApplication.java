package zkrtdrone.zkrt.com;
import android.content.Context;
import android.support.multidex.MultiDex;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication;

/**
 * Created by jack_xie on 17-4-11.
 */

public class JackApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    @Override
    protected void initView() {
        /*mySharedPreferences = getSharedPreferences("gases", Activity.MODE_PRIVATE);
        jettisoninSharedPreferences = getSharedPreferences("jettisonin", Activity.MODE_PRIVATE);*/
        /*networkReceiver = new NetworkReceiver();
        networkReceiver.regist(this);*/
        //GpsUtil.checkGPSDevice();
    }
}
