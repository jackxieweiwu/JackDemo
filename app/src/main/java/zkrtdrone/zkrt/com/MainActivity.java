package zkrtdrone.zkrt.com;

import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import com.squareup.otto.Subscribe;
import java.util.Stack;
import butterknife.Bind;
import dji.common.error.DJIError;
import dji.sdk.base.BaseProduct;
import dji.sdk.sdkmanager.DJISDKManager;
import zkrtdrone.zkrt.com.databinding.ActivityMainBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsActivity;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.IOTask;
import zkrtdrone.zkrt.com.jackmvvm.util.ViewWrapper;
import zkrtdrone.zkrt.com.jackmvvm.util.rxutil.RxjavaUtil;
import zkrtdrone.zkrt.com.widght.LauncherView;

public class MainActivity extends AbsActivity<ActivityMainBinding> {
    @Bind(R.id.load_view ) LauncherView launcherView;
    private FrameLayout contentFrameLayout;
    private Stack<ViewWrapper> stack;
    private ObjectAnimator pushInAnimator;
    private ObjectAnimator pushOutAnimator;
    private ObjectAnimator popInAnimator;
    private LayoutTransition popOutTransition;
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        contentFrameLayout = (FrameLayout) findViewById(R.id.activity_main);
        initParams();
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

    private void initParams() {
        setupInAnimations();
        stack = new Stack<ViewWrapper>();
        View view = contentFrameLayout.getChildAt(0);
        stack.push(new ViewWrapper(view, R.string.activity_component_list));
    }

    private void setupInAnimations() {
        pushInAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.slide_in_right);
        pushOutAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fade_out);
        popInAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fade_in);
        ObjectAnimator popOutAnimator =
                (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.slide_out_right);
        pushOutAnimator.setStartDelay(100);
        popOutTransition = new LayoutTransition();
        popOutTransition.setAnimator(LayoutTransition.DISAPPEARING, popOutAnimator);
        popOutTransition.setDuration(popOutAnimator.getDuration());
    }

    private void pushView(ViewWrapper wrapper) {
        if (stack.size() <= 0) return;
        contentFrameLayout.setLayoutTransition(null);
        View showView = wrapper.getView();
        View preView = stack.peek().getView();
        stack.push(wrapper);
        contentFrameLayout.addView(showView);
        pushOutAnimator.setTarget(preView);
        pushOutAnimator.start();
        pushInAnimator.setTarget(showView);
        pushInAnimator.setFloatValues(contentFrameLayout.getWidth(), 0);
        pushInAnimator.start();
    }

    private void popView() {
        if (stack.size() <= 1) {
            finish();
            return;
        }
        ViewWrapper removeWrapper = stack.pop();
        View showView = stack.peek().getView();
        View removeView = removeWrapper.getView();
        contentFrameLayout.setLayoutTransition(popOutTransition);
        contentFrameLayout.removeView(removeView);
        popInAnimator.setTarget(showView);
        popInAnimator.start();
    }

    @Override
    public void onBackPressed() {
        if (stack.size() > 1) {
            popView();
        } else {
            super.onBackPressed();
        }
    }

    public static class RequestStartFullScreenEvent {}

    public static class RequestEndFullScreenEvent {}

    @Subscribe
    public void onReceiveStartFullScreenRequest(RequestStartFullScreenEvent event) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Subscribe
    public void onReceiveEndFullScreenRequest(RequestEndFullScreenEvent event) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Subscribe
    public void onPushView(final ViewWrapper wrapper) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pushView(wrapper);
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
                //DJILog.e("App registration", djiError == null ? "null" : djiError.getDescription());
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
