package zkrtdrone.zkrt.com.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import butterknife.Bind;
import butterknife.OnClick;
import dji.common.flightcontroller.FlightControllerState;
import dji.sdk.flightcontroller.FlightController;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.bean.MavlinkDjiBean;
import zkrtdrone.zkrt.com.databinding.FragmentMountBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.JByteUtil;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.handler;

/**
 * Created by jack_xie on 17-4-27.
 */

public class MountFragment extends AbsFragment<FragmentMountBinding> {
    @Bind(R.id.horiz_mount) HorizontalBarChart horiz_mount;
    @Bind(R.id.frame_mount) public FrameLayout frame_mount;
    @Bind(R.id.mount_clear) public ImageView mount_clear;
    public RotateAnimation animation,animation1;
    private ImageView imgOpen;

    @Override
    protected void init(Bundle savedInstanceState) {
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            if(JackApplication.getAircraftInstance().getFlightController().isOnboardSDKDeviceAvailable()){
                JackApplication.getAircraftInstance().getFlightController().setOnboardSDKDeviceDataCallback(new FlightController.OnboardSDKDeviceDataCallback() {
                    @Override
                    public void onReceive(byte[] bytes) {
                        T.show(mActivity,"****"+bytes.length);
                        MavlinkDjiBean mavlinkDjiBean = (MavlinkDjiBean) JByteUtil.getObject(MavlinkDjiBean.class, bytes);
                        T.show(mActivity,mavlinkDjiBean.data+"****"+mavlinkDjiBean.appID);
                    }
                });
            }
        }
        setImgRotateAnimation();
    }

    @OnClick(R.id.mount_clear)
    public void onClickMountClear(View v){
        if(frame_mount.getVisibility() == View.VISIBLE){
            mount_clear.startAnimation(animation);
            frame_mount.startAnimation(startAnimViewGone());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    frame_mount.setVisibility(View.GONE);
                    if(imgOpen !=null)imgOpen.setVisibility(View.VISIBLE);
                }
            },1000);
        }else{
            mount_clear.startAnimation(animation1);
            frame_mount.startAnimation(startAnimViewShow());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    frame_mount.setVisibility(View.VISIBLE);
                    if(imgOpen !=null)imgOpen.setVisibility(View.GONE);
                }
            },1000);
        }
    }

    private void setImgRotateAnimation(){
        animation =new RotateAnimation(0,180,50f,50f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态

        animation1 =new RotateAnimation(180,0,50f,50f);
        animation1.setDuration(1000);//设置动画持续时间
        animation1.setFillAfter(true);//动画执行完后是否停留在执行完的状态
    }

    //显示动画
    public TranslateAnimation startAnimViewShow(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(1000);
        return mHiddenAction;
    }

    //隐藏动画
    public TranslateAnimation startAnimViewGone(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(1000);
        return mHiddenAction;
    }

    @Override
    protected void onDelayLoad() {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mount;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    public void setImgOpen(ImageView imgOpen) {
        this.imgOpen = imgOpen;
    }
}
