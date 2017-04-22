package zkrtdrone.zkrt.com.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.DecimalFormat;

import butterknife.Bind;
import dji.common.battery.BatteryState;
import dji.common.error.DJIError;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.remotecontroller.Information;
import dji.common.remotecontroller.PairingState;
import dji.common.util.CommonCallbacks;
import dji.sdk.base.BaseComponent;
import dji.sdk.base.BaseProduct;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentHandBinding;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseMvpFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.CalendarUtils;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by jack_xie on 17-4-20.
 */

public class HandStateFragment extends BaseMvpFragment<FragmentHandBinding> {
    @Bind(R.id.img_gps_status) ImageView img_gps_status;
    @Bind(R.id.img_yao_status) ImageView img_yao_status;
    @Bind(R.id.img_hd_status) ImageView img_hd_status;
    @Bind(R.id.txt_state_drone) TextView txt_state_drone;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_hand;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            //Battery
            JackApplication.getAircraftInstance().getBattery().setStateCallback(new BatteryState.Callback() {
                @Override
                public void onUpdate(BatteryState batteryState) {
                    getBinding().setBatteryStr(new DecimalFormat("##0.0").format(batteryState.getVoltage()/1000)+"V");  //+batteryState.getCellVoltageLevel()
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
            JackApplication.getProductInstance().setBaseProductListener(new BaseProduct.BaseProductListener() {
                @Override
                public void onComponentChange(BaseProduct.ComponentKey componentKey, BaseComponent baseComponent, BaseComponent baseComponent1) {

                }

                @Override
                public void onConnectivityChange(boolean b) {

                }
            });
        }
    }

    private void setState(final int number){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(number == 0) {setTextState(Color.RED,"GPS几乎没有信号");img_gps_status.setImageResource(R.mipmap.osd_singal_level0);}
                if(number == 1) {setTextState(Color.YELLOW,"GPS信号非常弱");img_gps_status.setImageResource(R.mipmap.osd_singal_level1);}
                if(number == 2) {setTextState(Color.YELLOW,"GPS信号较弱");img_gps_status.setImageResource(R.mipmap.osd_singal_level2);}
                if(number == 3) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level3);}
                if(number == 4) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level4);}
                if(number == 5) {setTextState(Color.GREEN,"null");img_gps_status.setImageResource(R.mipmap.osd_singal_level5);}
                if(number == 255) {setTextState(Color.RED,"没有GPS信号");img_gps_status.setImageResource(R.mipmap.osd_singal_level0);}
            }
        });
    }

    private void setTextState(int number,String name){
        txt_state_drone.setBackgroundColor(number);
        if(!name.equals("null"))getBinding().setDronestate(name);
    }
}
