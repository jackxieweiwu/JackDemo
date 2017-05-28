package zkrtdrone.zkrt.com.view.fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import dji.sdk.flightcontroller.FlightController;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.bean.Moudle;
import zkrtdrone.zkrt.com.databinding.FragmentMountBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.untils.Utils;
import zkrtdrone.zkrt.com.view.adapter.MoudleAdapter;
import zkrtdrone.zkrt.com.widght.ExpandableGridView;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.handler;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.mActivity;

/**
 * Created by jack_xie on 17-4-27.
 */

public class MountFragment extends AbsFragment<FragmentMountBinding> {
    @Bind(R.id.horiz_mount) HorizontalBarChart horiz_mount;
    @Bind(R.id.frame_mount) public FrameLayout frame_mount;
    @Bind(R.id.mount_clear) public ImageView mount_clear;
    @Bind(R.id.bar_char_linear) LinearLayout bar_char_linear;
    @Bind(R.id.grid_view) ExpandableGridView grid_view;
    public RotateAnimation animation,animation1;
    private ImageView imgOpen;
    private MoudleAdapter moudleAdapter;
    private List<Moudle> list = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        //获取基本的数据
        String[] moudleName = mActivity.getResources().getStringArray(R.array.moudle_item);
        TypedArray moudleImage = mActivity.getResources().obtainTypedArray(R.array.moudle_img);
        int i = 0;
        for (String name: moudleName){
            Moudle moudle = new Moudle();
            moudle.setName(name);
            moudle.setBitmap(moudleImage.getDrawable(i));
            i++;
            moudle.setStatus(false);
            list.add(moudle);
        }

        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            if(JackApplication.getAircraftInstance().getFlightController().isOnboardSDKDeviceAvailable()){
                JackApplication.getAircraftInstance().getFlightController().setOnboardSDKDeviceDataCallback(new FlightController.OnboardSDKDeviceDataCallback() {
                    @Override
                    public void onReceive(byte[] bytes) {
                        T.show(mActivity,"****"+bytes.length);
                        /*MavlinkDjiBean mavlinkDjiBean = (MavlinkDjiBean) JByteUtil.getObject(MavlinkDjiBean.class, bytes);
                        T.show(mActivity,mavlinkDjiBean.data+"****"+mavlinkDjiBean.appID);*/
                    }
                });
            }
        }
        setImgRotateAnimation();
        //点击进入gridView
        moudleAdapter = new MoudleAdapter(mActivity,list);
        grid_view.setAdapter(moudleAdapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name= list.get(position).getName();
                if(name.equals("避温"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_obstace,null),1,0);
                if(name.equals("探照灯"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_searchlight,null),2,0);
                if(name.equals("抛投"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_jettisonin,null),2,0);
                if(name.equals("双光"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_bifocal_camera,null),2,0);
                if(name.equals("避障"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_jettisonin,null),2,0);
                if(name.equals("有毒气体"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_poisonous,null),2,3);
                /*if(name.equals("相机"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_jettisonin,null));*/
            }
        });
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

    @OnClick(R.id.mount_cut)
    public void onClickMoubtOut(View v){//
        bar_char_linear.setVisibility(bar_char_linear.getVisibility() == View.INVISIBLE?View.VISIBLE:View.INVISIBLE);
        grid_view.setVisibility(bar_char_linear.getVisibility() == View.INVISIBLE?View.VISIBLE:View.INVISIBLE);
        /*int w = 0,h = 0;
        if(bar_char_linear.getVisibility() == View.INVISIBLE){w = 750;h=550;}else{w = 700;h = 500;}
        FrameLayout.LayoutParams lay = new FrameLayout.LayoutParams(Utils.dp2px(getContext(), w), Utils.dp2px(getContext(), h));
        frame_mount.setLayoutParams(lay);*/
    }

    private void setImgRotateAnimation(){
        animation =new RotateAnimation(0,180,50f,50f);
        animation.setDuration(1000);//设置动画持续时间
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态

        animation1 =new RotateAnimation(180,0,50f,50f);
        animation1.setDuration(1000);//设置动画持续时间
        animation1.setFillAfter(true);//动画执行完后是否停留在执行完的状态
    }

    //左上部显示动画
    public TranslateAnimation startAnimViewShow(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(1000);
        return mHiddenAction;
    }

    //左上部隐藏动画
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
