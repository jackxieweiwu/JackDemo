package zkrtdrone.zkrt.com.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.absadapter.viewpager.SimpleViewPagerAdapter;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.OnClick;
import dji.common.airlink.SignalQualityCallback;
import dji.common.battery.BatteryState;
import dji.common.error.DJIError;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentHandBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.CalendarUtils;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.ScreenUtil;
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
import zkrtdrone.zkrt.com.widght.XCSlideView;

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

    private TabLayout drone_tool_bar;
    private ViewPager drone_setting_pager;

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
        adapter.addFrag(holderSettingFragment, "云台");
        adapter.addFrag(mapSettingFragment, "地图");
        adapter.addFrag(recordSettingFragment, "记录");
        drone_setting_pager.setAdapter(adapter);
        drone_tool_bar.setupWithViewPager(drone_setting_pager);

        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            //Battery
            JackApplication.getAircraftInstance().getBattery().setNumberOfCells(12, new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });

            JackApplication.getAircraftInstance().getBattery().getLevel1CellVoltageThreshold(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    batteryWaring1 = integer*12;
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            JackApplication.getAircraftInstance().getBattery().getLevel2CellVoltageThreshold(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                @Override
                public void onSuccess(Integer integer) {
                    batteryLow2 = integer*12;
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            JackApplication.getAircraftInstance().getBattery().setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState batteryState) {
                    final int mv = batteryState.getVoltage();
                    getBinding().setBatteryStr(String.format("%.2f", mv/1000.0f)+"V");

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mv<=batteryWaring1 && mv>batteryLow2){
                                setPreateBatteryWaring(R.mipmap.osd_electric_warning);
                                setTextState(Color.RED,"电量过低");txt_state_drone.setTextColor(Color.WHITE);
                            }else if(mv<=batteryLow2){
                                setPreateBatteryWaring(R.mipmap.osd_electric_low);
                                setTextState(Color.RED,"电量严重过低");txt_state_drone.setTextColor(Color.WHITE);
                            }else{
                                setTextState(Color.GREEN,"null");
                                setPreateBatteryWaring(R.mipmap.osd_electric_btn_normal);
                            }
                        }
                    });
                }
            });

            //FlightControlle

            JackApplication.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState flightControllerState) {
                    getBinding().setDroneMode(flightControllerState.getFlightModeString()+"");
                    getBinding().setGPSSignal(flightControllerState.getSatelliteCount()+"");
                    GPSSignalLevel gpsSignalLevel = flightControllerState.getGPSSignalLevel();
                    getBinding().setDroneTime(CalendarUtils.cal(flightControllerState.getFlightTimeInSeconds()));
                    setState(gpsSignalLevel.value());
                }
            });

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
        }
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
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(number == 0) {setTextState(Color.RED,"GPS几乎没有信号");img_gps_status.setImageResource(R.mipmap.osd_singal_level0);
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 1) {setTextState(Color.YELLOW,"GPS信号非常弱");img_gps_status.setImageResource(R.mipmap.osd_singal_level1);
                    txt_state_drone.setTextColor(Color.RED);}
                if(number == 2) {setTextState(Color.YELLOW,"GPS信号较弱");img_gps_status.setImageResource(R.mipmap.osd_singal_level2);
                    txt_state_drone.setTextColor(Color.RED);}
                if(number == 3) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level3);
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 4) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level4);
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 5) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level5);
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 255) {setTextState(Color.RED,"没有GPS信号");img_gps_status.setImageResource(R.mipmap.osd_singal_level0);
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 400){setTextState(Color.RED,"与飞行器断开连接");img_gps_status.setImageResource(R.mipmap.osd_singal_level0);
                    txt_state_drone.setTextColor(Color.WHITE);}
                if(number == 401){setTextState(Color.RED,"与遥控断开断开连接");img_gps_status.setImageResource(R.mipmap.osd_singal_level0);
                    txt_state_drone.setTextColor(Color.WHITE);}
            }
        });
    }

    private void setTextState(int number,String name){
        txt_state_drone.setBackgroundColor(number);
        if(!name.equals("null"))getBinding().setDronestate(name);
        else getBinding().setDronestate("正常");
    }
}
