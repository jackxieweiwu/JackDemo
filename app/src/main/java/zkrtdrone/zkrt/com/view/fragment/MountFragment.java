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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import dji.sdk.flightcontroller.FlightController;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.bean.Gases;
import zkrtdrone.zkrt.com.bean.MavlinkDjiBean;
import zkrtdrone.zkrt.com.bean.Moudle;
import zkrtdrone.zkrt.com.databinding.FragmentMountBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.CommonRxTask;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.ByteUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.Utils;
import zkrtdrone.zkrt.com.jackmvvm.util.rxutil.RxjavaUtil;
import zkrtdrone.zkrt.com.view.adapter.GasesAdapter;
import zkrtdrone.zkrt.com.view.adapter.MoudleAdapter;
import zkrtdrone.zkrt.com.widght.ExpandableGridView;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.handler;

/**
 * Created by jack_xie on 17-4-27.
 */

public class MountFragment extends AbsFragment<FragmentMountBinding> {
    //@Bind(R.id.horiz_mount) HorizontalBarChart horiz_mount;
    @Bind(R.id.horiz_moudle_seekbar) ListView horiz_moudle_seekbar;
    @Bind(R.id.frame_mount) public FrameLayout frame_mount;
    @Bind(R.id.mount_clear) public ImageView mount_clear;
    @Bind(R.id.bar_char_linear) LinearLayout bar_char_linear;
    @Bind(R.id.grid_view) ExpandableGridView grid_view;
    public RotateAnimation animation,animation1;
    private ImageView imgOpen;
    private MoudleAdapter moudleAdapter;
    private GasesAdapter gasesAdapter;
    private List<Moudle> list = new ArrayList<>();
    private List<Gases> gasesList = new ArrayList<>();
    private MavlinkDjiBean mavlinkDjiBean = null;
    private TextView tishi;

    @Override
    protected void init(Bundle savedInstanceState) {
        //获取基本的数据
        String[] moudleName = mActivity.getResources().getStringArray(R.array.moudle_item);
        TypedArray moudleImage = mActivity.getResources().obtainTypedArray(R.array.moudle_img);
        int i = 0;
        for (String name: moudleName){
            Moudle moudle = new Moudle();
            moudle.setName(name);
            moudle.setDrawableID(moudleImage.getResourceId(i,0));  //moudleImage.getDrawable(i)
            i++;
            moudle.setStatus(false);
            list.add(moudle);
        }

        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            if(JackApplication.getAircraftInstance().getFlightController().isOnboardSDKDeviceAvailable()){
                JackApplication.getAircraftInstance().getFlightController().setOnboardSDKDeviceDataCallback(new FlightController.OnboardSDKDeviceDataCallback() {
                    @Override
                    public void onReceive(final byte[] bytes) {
                        RxjavaUtil.executeRxTask(new CommonRxTask<Object>() {
                            @Override
                            public void doInIOThread() {
                                mavlinkDjiBean = ByteUtils.toObject(bytes,MavlinkDjiBean.class);
                                if(Byte.toString(mavlinkDjiBean.getUavIDFour()).equals("102")) {
                                    //避障数据
                                    getBinding().setStrFount("前:"+(((mavlinkDjiBean.getStatus2()& 0xFF)<<8)+(mavlinkDjiBean.getTemperaturE1Two()& 0xFF)));
                                    getBinding().setStrBack("后:"+(((mavlinkDjiBean.getTemperaturE2_LowTwo()& 0xFF)<<8)+(mavlinkDjiBean.getTemperaturE2_LowOne()& 0xFF)));
                                    getBinding().setStrLeft("左:"+(((mavlinkDjiBean.getTemperaturE2_HighTwo() & 0xFF)<<8)+(mavlinkDjiBean.getTemperaturE2_HighOne()  & 0xFF)));
                                    getBinding().setStrRight("右:"+(((mavlinkDjiBean.getTemperaturE2Two() & 0xFF)<<8)+ (mavlinkDjiBean.getTemperaturE2One() & 0xFF)));

                                    //tishi.setText("*****"+(((mavlinkDjiBean.getGas_Value4_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value4_One() & 0xFF)));

                                    if(gasesList.size()==8){
                                        gasesList.get(4).setGasNum((((mavlinkDjiBean.getGas_Value3_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value3_One() & 0xFF))/10);   //CL2
                                        gasesList.get(5).setGasNum((((mavlinkDjiBean.getGas_Value4_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value4_One() & 0xFF))/10);   //SO2
                                        gasesList.get(6).setGasNum((((mavlinkDjiBean.getDevice_Status_Three() & 0xFF)<<8)+(mavlinkDjiBean.getDevice_Status_Two() & 0xFF))/10);   //VOC
                                        gasesList.get(7).setGasNum((((mavlinkDjiBean.getSet_feedback_Three() & 0xFF)<<8)+(mavlinkDjiBean.getSet_feedback_Two() & 0xFF))*100);   //CH4
                                    }
                                }else{
                                    //温度 TEMPERATURE1
                                    getBinding().setStrTempTwo(((mavlinkDjiBean.getTemperaturE1Two()<<8)+(mavlinkDjiBean.getTemperaturE1One()))/10f+"");
                                    //毒气
                                    hexListViewGases(Utils.toBinary(mavlinkDjiBean.getGas_Status()));
                                    if(gasesList.size()>=4){
                                       gasesList.get(0).setGasNum((((mavlinkDjiBean.getGas_Value1_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value1_One() & 0xFF)));   //CO
                                       gasesList.get(1).setGasNum((((mavlinkDjiBean.getGas_Value2_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value2_One() & 0xFF))/100);   //H2S
                                       gasesList.get(2).setGasNum((((mavlinkDjiBean.getGas_Value3_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value3_One() & 0xFF)));   //NH3
                                       gasesList.get(3).setGasNum((((mavlinkDjiBean.getGas_Value4_Two() & 0xFF)<<8)+(mavlinkDjiBean.getGas_Value4_One() & 0xFF)));   //CO2
                                    }
                                    //模块是否在线
                                    HexOneMode(Utils.toBinary(mavlinkDjiBean.getDevice_Status_Three())
                                            +Utils.toBinary(mavlinkDjiBean.getDevice_Status_Two())
                                            +Utils.toBinary(mavlinkDjiBean.getDevice_Status_One()));
                                }
                            }

                            @Override
                            public void doInUIThread() {
                                moudleAdapter.setData(list);
                                gasesAdapter.setData(gasesList);
                                mavlinkDjiBean = null;
                            }
                        });
                    }
                });
            }
        }
        setImgRotateAnimation();
        //毒气显示值
        gasesAdapter = new GasesAdapter(mActivity,gasesList,R.layout.item_seekbar);
        horiz_moudle_seekbar.setAdapter(gasesAdapter);
        horiz_moudle_seekbar.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //点击进入gridView
        moudleAdapter = new MoudleAdapter(mActivity,list,R.layout.item_moudle);
        grid_view.setAdapter(moudleAdapter);
        grid_view.setSelector(new ColorDrawable(Color.TRANSPARENT));
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Moudle moudle = list.get(position);
                if(moudle.getName().equals("避温") && moudle.getStatus())
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_obstace,null),1,0);
                if(moudle.getName().equals("探照灯") && moudle.getStatus())
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_searchlight,null),2,0);
                if(moudle.getName().equals("抛投") && moudle.getStatus())
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_jettisonin,null),2,0);
                if(moudle.getName().equals("双光红外") && moudle.getStatus())
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_bifocal_camera,null),2,0);
                if(moudle.getName().equals("避障") && moudle.getStatus())
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_jettisonin,null),2,0);
                if(moudle.getName().equals("有毒气体") && moudle.getStatus())
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_poisonous,null),2,3);
                /*if(name.equals("相机"))
                    grid_view.expandGridViewAtView(view,LayoutInflater.from(mActivity).inflate(R.layout.moudle_jettisonin,null));*/
            }
        });
    }

    public MavlinkDjiBean getMavlinkDjiBean(){
        return mavlinkDjiBean;
    }

    @OnClick(R.id.mount_clear)
    public void onClickMountClear(View v){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    @OnClick(R.id.mount_cut)
    public void onClickMoubtOut(View v){//
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar_char_linear.setVisibility(bar_char_linear.getVisibility() == View.INVISIBLE?View.VISIBLE:View.INVISIBLE);
                grid_view.setVisibility(bar_char_linear.getVisibility() == View.INVISIBLE?View.VISIBLE:View.INVISIBLE);
            }
        });
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

    public void setImgOpen(ImageView imgOpen, TextView txt_tishi) {
        this.imgOpen = imgOpen;
        this.tishi = txt_tishi;
    }

    private void hexListViewGases(String gases){
        gasesList.clear();
        for (int i=0;i<gases.length();i++){
            String gasesMoudle = gases.substring((8-(i+1)),(8-i));
            switch (8-i){
                case 8:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("CO",0,100));
                    }
                    break;
                case 7:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("H2S",0,50));
                    }
                    break;
                case 6:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("NH3",0,100));
                    }
                    break;
                case 5:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("CO2",0,20000));
                    }
                    break;
                case 4:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("CL2",0,20));
                    }
                    break;
                case 3:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("SO2",0,20));
                    }
                    break;
                case 2:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("VOC",0,200));
                    }
                    break;
                case 1:
                    if(gasesMoudle.equals("1")){
                        gasesList.add(new Gases("CH4",0,50000));
                    }
                    break;
            }
        }
    }

    private void HexOneMode(final String name){
        for(int i=0; i<name.length();++i){
            //(name.indexOf("1",i))
            int drawableId;
            boolean bool = false;
            String numMoudle = name.substring((24-(i+1)),(24-i));
            switch (24-i){
                case 24:  //温度模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_temperature_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_temperature_unline;bool = false;
                    }
                    list.get(6).setDrawableID(drawableId);
                    list.get(6).setStatus(bool);
                    break;
                case 23:  //壁障模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_tof_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_tof_unline;bool = false;
                    }
                    list.get(5).setDrawableID(drawableId);
                    list.get(5).setStatus(bool);
                    break;
                case 22:  //备用
                    break;
                case 21:  //相机模块
                    if(numMoudle.equals("1")){ drawableId = R.mipmap.pic_radar_online; bool = true;
                    }else{ drawableId = R.mipmap.pic_radar_unline; bool = false;
                    }
                    list.get(11).setDrawableID(drawableId);
                    list.get(11).setStatus(bool);
                    break;
                case 20:  //毒气模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_gas_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_gas_unline;bool = false;
                    }
                    list.get(3).setDrawableID(drawableId);
                    list.get(3).setStatus(bool);
                    break;
                case 19:  //抛投模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_toss_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_toss_unline;bool = false;
                    }
                    list.get(2).setDrawableID(drawableId);
                    list.get(2).setStatus(bool);
                    break;
                case 18:  //备用
                    break;
                case 17:  //降落伞
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_parachute_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_parachute_unline;bool = false;
                    }
                    list.get(4).setDrawableID(drawableId);
                    list.get(4).setStatus(bool);
                    break;
                case 16:  //照射模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_searchlight_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_searchlight_unline;bool = false;
                    }
                    list.get(10).setDrawableID(drawableId);
                    list.get(10).setStatus(bool);
                    break;
                case 15:  //喊话模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_horn_online;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_horn_unline;bool = false;
                    }
                    list.get(1).setDrawableID(drawableId);
                    list.get(1).setStatus(bool);
                    break;
                case 14:  //电池模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_battery_onling;bool = true;
                    }else{
                        drawableId = R.mipmap.pic_battery_unling;bool = false;
                    }
                    list.get(0).setDrawableID(drawableId);
                    list.get(0).setStatus(bool);
                    break;
                case 13:  //3DModel
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_3d_online; bool = true;
                    }else{
                        drawableId = R.mipmap.pic_3d_unline; bool = false;
                    }
                    list.get(8).setDrawableID(drawableId);
                    list.get(8).setStatus(bool);
                    break;
                case 12:  //双光/三光模块
                    if(numMoudle.equals("1")){
                        drawableId = R.mipmap.pic_infrared_online; bool = true;
                    }else{
                        drawableId = R.mipmap.pic_infrared_unline; bool = false;
                    }
                    list.get(7).setDrawableID(drawableId);
                    list.get(7).setStatus(bool);
                    break;
            }
        }
    }
}
