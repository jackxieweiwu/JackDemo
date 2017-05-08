package zkrtdrone.zkrt.com.jackmvvm.mvvm.core;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.module.AbsModule;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.module.IOCProxy;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.permission.OnPermissionCallback;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.permission.PermissionManager;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.temp.AbsTempView;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.temp.OnTempBtClickListener;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.temp.TempView;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.StringUtil;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.ViewUtil;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.settingBool;

/**
 * Created by jack_xie on 17-4-9.
 * 所有的 Activity都应该继承这个类
 */

public abstract class AbsActivity <VB extends ViewDataBinding>
        extends AppCompatActivity implements OnTempBtClickListener {
    protected String TAG = "";
    protected AbsFrame mAm;
    protected View mRootView;
    protected AbsTempView mTempView;
    protected boolean useTempView = true;
    private VB mBind;
    private IOCProxy mProxy;
    /**
     * 第一次点击返回的系统时间
     */
    private long mFirstClickTime = 0;
    private ModuleFactory mModuleF;

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BaseApplication.getRefWatcher(this).watch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Hide both the navigation bar and the status bar.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(flag, flag);
        //ViewUtil.hideVirtualKey(this);
        super.onCreate(savedInstanceState);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionManager.getInstance().requestPermission(this, new OnPermissionCallback() {
                @Override
                public void onSuccess(String... permissions) {
                    //T.showShort(AbsActivity.this, "权限" + Arrays.toString(permissions) + " 申请成功");
                }

                @Override
                public void onFail(String... permissions) {
                    //T.showShort(AbsActivity.this, "权限" + Arrays.toString(permissions) + " 申请失败");
                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CHANGE_NETWORK_STATE,Manifest.permission.WRITE_SETTINGS,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED
            });
        }
        BaseApplication.fragmentManager = getSupportFragmentManager();
        BaseApplication.getEventBus().register(this);
        BaseApplication.mActivity = this;
        initialization();
        init(savedInstanceState);
    }

    private void initialization() {
        mAm = AbsFrame.getInstance();
        mAm.addActivity(this);
        mBind = DataBindingUtil.setContentView(this, setLayoutId());
        mProxy = IOCProxy.newInstance(this);
        TAG = StringUtil.getClassName(this);
        mModuleF = ModuleFactory.newInstance();
        ButterKnife.bind(this);
        mRootView = mBind.getRoot();
        if (useTempView) {
            mTempView = new TempView(this);
            mTempView.setBtListener(this);
        }
    }

    protected void reNewModule() {
        if (mModuleF == null) {
            mModuleF = ModuleFactory.newInstance();
        }
    }

    /**
     * 获取填充View
     */
    protected AbsTempView getTempView() {
        return mTempView;
    }

    /**
     * 是否使用填充界面
     */
    protected void setUseTempView(boolean useTempView) {
        this.useTempView = useTempView;
    }

    /**
     * 设置自定义的TempView
     */
    protected void setCustomTempView(AbsTempView tempView) {
        mTempView = tempView;
        mTempView.setBtListener(this);
    }

    /**
     * 显示占位布局
     *
     * @param type {@link TempView#ERROR}
     * {@link TempView#DATA_NULL}
     * {@link TempView#LOADING}
     */
    protected void showTempView(int type) {
        if (mTempView == null || !useTempView) {
            return;
        }
        mTempView.setVisibility(View.VISIBLE);
        mTempView.setType(type);
        setContentView(mTempView);
    }

    public ModuleFactory getModuleFactory() {
        return mModuleF;
    }

    /**
     * 关闭占位布局
     */
    protected void hintTempView() {
        hintTempView(0);
    }

    /**
     * 延时关闭占位布局
     */
    protected void hintTempView(int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                if (mTempView == null || !useTempView) {
                    return;
                }
                mTempView.clearFocus();
                mTempView.setVisibility(View.GONE);
                setContentView(mRootView);
            }
        }, delay);
    }

    @Override public void onBtTempClick(View view, int type) {}

    @Override protected void onDestroy() {
        BaseApplication.getEventBus().unregister(this);
        super.onDestroy();
        settingBool = false;
    }

    protected void init(Bundle savedInstanceState) {}

    @Override public void finish() {
        super.finish();
        mAm.removeActivity(this);
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置资源布局
     */
    protected abstract int setLayoutId();

    /**
     * 获取binding对象
     */
    protected VB getBinding() {
        return mBind;
    }

    /**
     * 获取Module
     * @param clazz {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz) {
        M module = mModuleF.getModule(this, clazz);
        module.setHost(this);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 获取Module
     * @param clazz Module class0
     * @param callback Module回调函数
     * @param <M> {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz,
                                                @NonNull AbsModule.OnCallback callback) {
        M module = mModuleF.getModule(this, clazz);
        module.setCallback(callback);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 数据回调
     */
    protected abstract void dataCallback(int result, Object data);

    /**
     * 双击退出
     */
    private boolean onDoubleClickExit(long timeSpace) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - mFirstClickTime > timeSpace) {
            T.showShort(this, "再按一次退出");
            mFirstClickTime = currentTimeMillis;
            return false;
        } else {
            return true;
        }
    }

    /**
     * 双击退出，间隔时间为2000ms
     */
    public boolean onDoubleClickExit() {
        return onDoubleClickExit(2000);
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行,如果为true则为后台运行
     */
    public void exitApp(Boolean isBackground) {
        mAm.exitApp(isBackground);
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        mAm.exitApp(false);
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelp.getInstance().handlePermissionCallback(requestCode, permissions, grantResults);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PermissionHelp.getInstance()
                .handleSpecialPermissionCallback(this, requestCode, resultCode, data);
    }
}
