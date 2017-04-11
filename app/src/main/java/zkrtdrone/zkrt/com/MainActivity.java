package zkrtdrone.zkrt.com;

import android.os.Bundle;
import android.support.annotation.NonNull;

import butterknife.Bind;
import dji.common.error.DJIError;
import dji.log.DJILog;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKManager;
import zkrtdrone.zkrt.com.databinding.ActivityMainBinding;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseMvpActivity;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.IOTask;
import zkrtdrone.zkrt.com.jackmvvm.util.rxutil.RxjavaUtil;
import zkrtdrone.zkrt.com.widght.LauncherView;

public class MainActivity extends BaseMvpActivity<ActivityMainBinding> {
    @Bind(R.id.load_view ) LauncherView launcherView;

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        RxjavaUtil.doInIOThread(new IOTask<Object>() {
            @Override
            public void doInIOThread() throws Exception {
                JackApplication.handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launcherView.start();
                    }
                },500);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        DJISDKManager.getInstance().registerApp(this, new DJISDKManager.SDKManagerCallback() {
            @Override
            public void onRegister(DJIError djiError) {
                DJILog.e("App registration", djiError == null ? "null" : djiError.getDescription());
            }

            @Override
            public void onProductChange(BaseProduct baseProduct, BaseProduct baseProduct1) {
                // DO nothing.
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }
}
