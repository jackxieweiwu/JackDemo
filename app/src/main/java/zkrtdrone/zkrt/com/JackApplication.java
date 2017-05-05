package zkrtdrone.zkrt.com;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication;
import zkrtdrone.zkrt.com.maplib.info.until.DroidPlannerPrefs;
import zkrtdrone.zkrt.com.view.MainStart;

/**
 * Created by jack_xie on 17-4-11.
 */

public class JackApplication extends BaseApplication {
    private DroidPlannerPrefs dpPrefs;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        super.onCreate();
        dpPrefs = new DroidPlannerPrefs(getApplicationContext());
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
