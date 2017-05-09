package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import dji.common.error.DJIError;
import dji.common.flightcontroller.ControlMode;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.imu.IMUState;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.SettingBasisFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by jack_xie on 17-5-5.
 * 基础
 */

public class BasisSettingFragment extends AbsFragment<SettingBasisFragmentBinding>{
    @Bind(R.id.switch_drone_mode) Switch switch_drone_mode;
    @Bind(R.id.txt_btn_Calibrate_compass) TextView txt_btn_Calibrate_compass;

    @Override
    protected void init(Bundle savedInstanceState) {
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            //获取飞行模式
            JackApplication.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState flightControllerState) {
                    switch_drone_mode.setChecked(flightControllerState.isMultipleModeOpen());

                    //罗盘
                    boolean bool = JackApplication.getAircraftInstance().getFlightController().getCompass().isCalibrating();
                    if(bool){
                        getBinding().setStrCommposStates("错误");
                        txt_btn_Calibrate_compass.setVisibility(View.VISIBLE);
                    }else {
                        getBinding().setStrCommposStates("正常");
                        txt_btn_Calibrate_compass.setVisibility(View.GONE);
                    }
                }
            });

            getBinding().setSetImuNumber(JackApplication.getAircraftInstance().getFlightController().getIMUCount()+"");
        }
    }

    //罗盘校准
    @OnClick(R.id.txt_btn_Calibrate_compass)
    public void startCompass(View v){
        if(ModuleVerificationUtil.isCompassAvailable()){
            if(!JackApplication.getAircraftInstance().getFlightController().getCompass().isCalibrating()){
                JackApplication.getAircraftInstance().getFlightController().getCompass().startCalibration(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {

                    }
                });
            }else{
                T.show(mActivity,"罗盘当前正在校准");
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.setting_basis_fragment;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    protected void onDelayLoad() {

    }

}
