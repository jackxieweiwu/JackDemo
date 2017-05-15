package zkrtdrone.zkrt.com.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.arialyy.absadapter.viewpager.SimpleViewPagerAdapter;
import butterknife.Bind;
import butterknife.OnClick;
import dji.common.airlink.SignalQualityCallback;
import dji.common.battery.BatteryState;
import dji.common.error.DJIError;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.flightcontroller.imu.IMUState;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentHandBinding;
import zkrtdrone.zkrt.com.databinding.SettingBasisFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.CalendarUtils;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.BasisSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.BatterySettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.DroneSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.HdSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.HolderSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.MapSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.RecordSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.DroneSetting.RemoteSettingFragment;
import zkrtdrone.zkrt.com.view.fragment.baseFragment.BaseIMUFragment;
import zkrtdrone.zkrt.com.widght.XCSlideView;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.batteryS;

/**
 * Created by jack_xie on 17-4-20.
 */

public class HandStateFragment extends AbsFragment<FragmentHandBinding> {
    @Bind(R.id.img_gps_status) ImageView img_gps_status;
    @Bind(R.id.img_yao_status) ImageView img_yao_status;
    @Bind(R.id.img_hd_status) ImageView img_hd_status;
    @Bind(R.id.txt_state_drone) TextView txt_state_drone;
    @Bind(R.id.hand_battery) ImageView hand_battery;
    @Bind(R.id.hand_setting) ImageView hand_setting;
    private FlightControllerState flightControllerState;

    private TabLayout drone_tool_bar;
    private ViewPager drone_setting_pager;
    private int status =-1;  //0 电量低  1GPS错误  2 IMU错误

    private XCSlideView mSlideViewRight;
    @OnClick(R.id.hand_setting)
    public void droneSetting(View view){
        if (!mSlideViewRight.isShow())
            mSlideViewRight.show();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        DroneSettingFragment droneSettingFragment = new DroneSettingFragment();
        BatterySettingFragment batterySettingFragment = new BatterySettingFragment();
        HolderSettingFragment holderSettingFragment = new HolderSettingFragment();
        BasisSettingFragment basisSettingFragment = new BasisSettingFragment();
        MapSettingFragment mapSettingFragment = new MapSettingFragment();
        RecordSettingFragment recordSettingFragment = new RecordSettingFragment();
        RemoteSettingFragment remoteSettingFragment = new RemoteSettingFragment();
        HdSettingFragment hdSettingFragment = new HdSettingFragment();

        View menuViewLeft = LayoutInflater.from(JackApplication.mActivity).inflate(R.layout.setting_drone,null);
        drone_tool_bar = (TabLayout) menuViewLeft.findViewById(R.id.drone_tool_bar);
        drone_setting_pager = (ViewPager) menuViewLeft.findViewById(R.id.drone_setting_pager);
        mSlideViewRight = XCSlideView.create(JackApplication.mActivity, XCSlideView.Positon.RIGHT);
        mSlideViewRight.setMenuView(JackApplication.mActivity, menuViewLeft);
        mSlideViewRight.setMenuWidth(JackApplication.getScreenWidth() * 7 / 15);

        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(JackApplication.fragmentManager);
        adapter.addFrag(basisSettingFragment, "基础");
        adapter.addFrag(droneSettingFragment, "飞机");
        adapter.addFrag(hdSettingFragment, "相机");
        adapter.addFrag(batterySettingFragment, "电源");
        adapter.addFrag(remoteSettingFragment, "遥控");
        if(ModuleVerificationUtil.isGimbalModuleAvailable()){
            if(JackApplication.getAircraftInstance().getGimbal().isConnected()){
                adapter.addFrag(holderSettingFragment, "云台");
            }
        }
        adapter.addFrag(mapSettingFragment, "地图");
        adapter.addFrag(recordSettingFragment, "记录");
        drone_setting_pager.setAdapter(adapter);
        drone_tool_bar.setupWithViewPager(drone_setting_pager);

        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            //Battery
            JackApplication.getAircraftInstance().getBattery().setNumberOfCells(batteryS, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });

            JackApplication.getAircraftInstance().getBattery().setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState batteryState) {

                    JackApplication.getAircraftInstance().getBattery().getLevel1CellVoltageThreshold(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            batteryWaring1 = integer*batteryS;
                        }

                        @Override
                        public void onFailure(DJIError djiError) {

                        }
                    });

                    JackApplication.getAircraftInstance().getBattery().getLevel2CellVoltageThreshold(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            batteryLow2 = integer*batteryS;
                        }

                        @Override
                        public void onFailure(DJIError djiError) {

                        }
                    });

                    final int mv = batteryState.getVoltage();
                    getBinding().setBatteryStr(String.format("%.2f", mv/1000.0f)+"V");
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mv<=batteryWaring1 && mv>batteryLow2){
                                setPreateBatteryWaring(R.mipmap.osd_electric_warning);
                                setTextState(Color.RED,"电量过低");txt_state_drone.setTextColor(Color.WHITE);
                                status = 0;
                            }else if(mv<=batteryLow2){
                                status = 0;
                                setPreateBatteryWaring(R.mipmap.osd_electric_low);
                                setTextState(Color.RED,"电量严重过低");txt_state_drone.setTextColor(Color.WHITE);
                            }else{
                                status = -1;
                                setTextState(Color.GREEN,"null");
                                setPreateBatteryWaring(R.mipmap.osd_electric_btn_normal);
                            }
                        }
                    });

                    //FlightControlle
                    if(flightControllerState == null) flightControllerState = JackApplication.getAircraftInstance().getFlightController().getState();
                    getBinding().setDroneMode(flightControllerState.getFlightModeString()+"");
                    getBinding().setGPSSignal(flightControllerState.getSatelliteCount()+"");
                    GPSSignalLevel gpsSignalLevel = flightControllerState.getGPSSignalLevel();
                    getBinding().setDroneTime(CalendarUtils.cal(flightControllerState.getFlightTimeInSeconds()));
                    setState(gpsSignalLevel.value());
                }
            });

            //FlightControlle
            /*JackApplication.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState flightControllerState) {
                    getBinding().setDroneMode(flightControllerState.getFlightModeString()+"");
                    getBinding().setGPSSignal(flightControllerState.getSatelliteCount()+"");
                    GPSSignalLevel gpsSignalLevel = flightControllerState.getGPSSignalLevel();
                    getBinding().setDroneTime(CalendarUtils.cal(flightControllerState.getFlightTimeInSeconds()));
                    setState(gpsSignalLevel.value());
                }
            });*/

            //HD
            JackApplication.getProductInstance().getAirLink().setUplinkSignalQualityCallback(new SignalQualityCallback() {
                @Override
                public void onUpdate(int i) {
                    setLinkStates(true,i);
                    setLinkStates(false,i);
                    getBinding().setVideoSignal(i+"");
                    getBinding().setRemoteSignal(i+"");
                }
            });

            //remote
            JackApplication.getProductInstance().getAirLink().setDownlinkSignalQualityCallback(new SignalQualityCallback() {
                @Override
                public void onUpdate(int i) {
                    setLinkStates(false,i);
                    getBinding().setRemoteSignal(i+"");
                }
            });

            //IMU
            JackApplication.getAircraftInstance().getFlightController().setIMUStateCallback(new IMUState.Callback() {
                @Override
                public void onUpdate(@NonNull IMUState imuState) {
                    updateCallBack(imuState);
                }
            });
        }
    }

    //IMU
    public void updateCallBack(final IMUState imuState) {
        if(status == 1) return;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(imuState.isConnected()){
                    int imuEmuNum = imuState.getGyroscopeState().value();
                    if(imuEmuNum == 255) {setTextState(Color.RED,"IMU未知错误"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 1){setTextState(Color.RED,"IMU与飞行控制器断开连接"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 2){setTextState(Color.YELLOW,"IMU正在校准"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 3){setTextState(Color.YELLOW,"校准IMU失败"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 4){setTextState(Color.RED,"IMU数据异常,校准IMU并重启飞机"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 5){setTextState(Color.RED,"IMU正在升温"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 6){setTextState(Color.YELLOW,"飞机可能不够稳定"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 7){setTextState(Color.GREEN,"飞机可以安全起飞"); status = -1;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 8){setTextState(Color.GREEN,"飞机可以安全起飞"); status = -1;
                        txt_state_drone.setTextColor(Color.WHITE);}
                    if(imuEmuNum == 9){setTextState(Color.RED,"需要进行IMU校准"); status = 2;
                        txt_state_drone.setTextColor(Color.WHITE);}
                }
            }
        });
    }

    public void isRemoteDrone(final boolean bool, final int number){
        //ScreenUtil.getInstance().setGreyScale(mRootView,bool);
        setLinkStates(true,0);
        setLinkStates(false,0);
        getBinding().setVideoSignal(0+"");
        getBinding().setRemoteSignal(0+"");
        setState(number);
    }

    @Override
    protected void onDelayLoad() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_hand;
    }

    @Override
    protected void dataCallback(int result, Object obj) {
    }

    private void setPreateBatteryWaring(final int i){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hand_battery.setBackgroundResource(i);
            }
        });
    }

    private void setLinkStates(final boolean bool, final int i){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bool){  //HD
                    if(i<20)img_hd_status.setImageResource(R.mipmap.osd_singal_level0);
                    if(i>=20 && i<40)img_hd_status.setImageResource(R.mipmap.osd_singal_level1);
                    if(i>=40 && i<60)img_hd_status.setImageResource(R.mipmap.osd_singal_level2);
                    if(i>=60 && i<80)img_hd_status.setImageResource(R.mipmap.osd_singal_level3);
                    if(i>=80 && i<100)img_hd_status.setImageResource(R.mipmap.osd_singal_level4);
                    if(i>=100)img_hd_status.setImageResource(R.mipmap.osd_singal_level5);
                }else{
                    if(i<20)img_yao_status.setImageResource(R.mipmap.osd_singal_level0);
                    if(i>=20 && i<40)img_yao_status.setImageResource(R.mipmap.osd_singal_level1);
                    if(i>=40 && i<60)img_yao_status.setImageResource(R.mipmap.osd_singal_level2);
                    if(i>=60 && i<80)img_yao_status.setImageResource(R.mipmap.osd_singal_level3);
                    if(i>=80 && i<100)img_yao_status.setImageResource(R.mipmap.osd_singal_level4);
                    if(i>=100)img_yao_status.setImageResource(R.mipmap.osd_singal_level5);
                }
            }
        });
    }

    private void setState(final int number){
        if(status == 0) return;
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(number == 0) {setTextState(Color.RED,"GPS几乎没有信号");img_gps_status.setImageResource(R.mipmap.osd_singal_level0); status = 1;
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 1) {setTextState(Color.YELLOW,"GPS信号非常弱");img_gps_status.setImageResource(R.mipmap.osd_singal_level1); status = 1;
                    txt_state_drone.setTextColor(Color.RED);}
                if(number == 2) {setTextState(Color.YELLOW,"GPS信号较弱");img_gps_status.setImageResource(R.mipmap.osd_singal_level2); status = 1;
                    txt_state_drone.setTextColor(Color.RED);}
                if(number == 3) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level3); status = -1;
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 4) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level4); status = -1;
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 5) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level5); status = -1;
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 255) {setTextState(Color.RED,"没有GPS信号");img_gps_status.setImageResource(R.mipmap.osd_singal_level0); status = 1;
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 400){setTextState(Color.RED,"与飞行器断开连接");img_gps_status.setImageResource(R.mipmap.osd_singal_level0); status = 1;
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 401){setTextState(Color.RED,"与遥控断开断开连接");img_gps_status.setImageResource(R.mipmap.osd_singal_level0); status = 1;
                    txt_state_drone.setTextColor(Color.WHITE);}
            }
        });
    }

    private void setTextState(final int number, final String name){
        if(status != -1){
            txt_state_drone.setBackgroundColor(number);
            getBinding().setDronestate(name);
        }else{
            getBinding().setDronestate("正常");
        }
    }
}