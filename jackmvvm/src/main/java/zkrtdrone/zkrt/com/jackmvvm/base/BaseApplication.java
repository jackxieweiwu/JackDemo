package zkrtdrone.zkrt.com.jackmvvm.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import dji.common.error.DJIError;
import dji.common.error.DJISDKError;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import dji.sdk.products.Aircraft;
import dji.sdk.products.HandHeld;
import dji.sdk.sdkmanager.DJISDKManager;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFrame;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.DensityUtils;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.IOTask;
import zkrtdrone.zkrt.com.jackmvvm.util.rxutil.RxjavaUtil;

/**
 * Created by jack_xie on 16-12-22.
 */

public abstract class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getName();
    private static BaseProduct product;
    public static FragmentManager fragmentManager;
    public static  boolean settingBool = false;
    public static double peploLat=0;
    public static double peploLng=0;
    public static  boolean rightMenu = true;
    private static Bus bus = new Bus(ThreadEnforcer.ANY);
    private static BaseApplication instance;
    public static Activity mActivity;
    public static Handler handler;
    private RefWatcher refWatcher;
    private int screenWidth,screenHeigh;

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static synchronized BaseProduct getProductInstance() {
        if (null == product) {
            product = DJISDKManager.getInstance().getProduct();
        }
        return product;
    }

    public static boolean isAircraftConnected() {
        return getProductInstance() != null && getProductInstance() instanceof Aircraft;
    }

    public static boolean isHandHeldConnected() {
        return getProductInstance() != null && getProductInstance() instanceof HandHeld;
    }

    public static synchronized Aircraft getAircraftInstance() {
        if (!isAircraftConnected()) return null;
        return (Aircraft) getProductInstance();
    }

    public static synchronized HandHeld getHandHeldInstance() {
        if (!isHandHeldConnected()) return null;
        return (HandHeld) getProductInstance();
    }

    public int getScreenWidth(){
        return screenWidth;
    }

    public int getScreenHeigh(){
        return screenHeigh;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        AbsFrame.init(this);
        //CrashHandler.getInstance(this);
        //如果你需要打开异常捕获模块，去掉下面语句的注释，将两个参数改为你的接口和key，便可以将崩溃信息上传到后台服务器
        //AbsFrame.init(this).openCrashHandler("http://192.168.2.183/server.php", "params");
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);

        RxjavaUtil.doInIOThread(new IOTask<Object>() {
            @Override
            public void doInIOThread() throws Exception {
                Thread.sleep(3500);
                int number = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_PHONE_STATE);
                if (number == 0 || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    DJISDKManager.getInstance().registerApp(getApplicationContext(), mDJISDKManagerCallback);
                }
            }
        });

        initView();
        instance = this;
        int[] widhHight = DensityUtils.getScreenWidthAndHeight(this);
        screenWidth = widhHight[0];
        screenHeigh = widhHight[1];
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static Bus getEventBus() {
        return bus;
    }

    private DJISDKManager.SDKManagerCallback mDJISDKManagerCallback = new DJISDKManager.SDKManagerCallback() {
        @Override
        public void onRegister(DJIError error) {
            if (error == DJISDKError.REGISTRATION_SUCCESS) {
                DJISDKManager.getInstance().startConnectionToProduct();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        T.show(getApplicationContext(),"验证成功");
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        T.show(getApplicationContext(),"SDK 注册失败。请检查绑定 ID 和您的网络连接");
                    }
                });
            }
        }

        @Override
        public void onProductChange(BaseProduct oldProduct, BaseProduct newProduct) {
            product = newProduct;
            if (product != null) {
                product.setBaseProductListener(mDJIBaseProductListener);
            }
            notifyStatusChange();
        }

        private BaseProduct.BaseProductListener mDJIBaseProductListener = new BaseProduct.BaseProductListener() {

            @Override
            public void onComponentChange(BaseProduct.ComponentKey key,
                                          BaseComponent oldComponent,
                                          BaseComponent newComponent) {
                if (newComponent != null) {
                    newComponent.setComponentListener(mDJIComponentListener);
                }
                notifyStatusChange();
            }

            @Override
            public void onConnectivityChange(boolean isConnected) {
                notifyStatusChange();
            }
        };

        private BaseComponent.ComponentListener mDJIComponentListener = new BaseComponent.ComponentListener() {

            @Override
            public void onConnectivityChange(boolean isConnected) {
                notifyStatusChange();
            }
        };

        private void notifyStatusChange() {
            bus.post(new ConnectivityChangeEvent());
        }
    };

    protected abstract void initView();

    public static class ConnectivityChangeEvent {
    }
}
