package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import dji.common.error.DJIError;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.imu.IMUState;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.SettingBasisFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.view.dialog.DialogCompass;
import zkrtdrone.zkrt.com.view.dialog.DialogIMU;
import zkrtdrone.zkrt.com.view.fragment.baseFragment.BaseIMUFragment;

/**
 * Created by jack_xie on 17-5-5.
 * 基础
 */

public class BasisSettingFragment extends BaseIMUFragment<SettingBasisFragmentBinding> {
    @Bind(R.id.switch_drone_mode) Switch switch_drone_mode;
    @Bind(R.id.txt_btn_Calibrate_compass) TextView txt_btn_Calibrate_compass;
    private DialogCompass dialogCompass = new DialogCompass();
    private DialogIMU dialogIMU = new DialogIMU();

    @Override
    protected void init(Bundle savedInstanceState) {
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            //获取飞行模式
            FlightControllerState flightControllerState = JackApplication.getAircraftInstance().getFlightController().getState();
            switch_drone_mode.setChecked(flightControllerState.isMultipleModeOpen());
            //获取指南针状态
            boolean bool = JackApplication.getAircraftInstance().getFlightController().getCompass().isCalibrating();
            if(bool){
                getBinding().setStrCommposStates("错误");
            }else {
                getBinding().setStrCommposStates("正常");
            }

            //获取IMU数量
            getBinding().setSetImuNumber(JackApplication.getAircraftInstance().getFlightController().getIMUCount()+"");

        }
    }

    @Override
    public void updateCallBack(final IMUState imuState) {
        int imuEmuNum = imuState.getGyroscopeState().value();
        if(imuEmuNum == 255) getBinding().setStrImuStates("IMU未知错误");
        if(imuEmuNum == 1) getBinding().setStrImuStates("IMU与飞行控制器断开连接");
        if(imuEmuNum == 2) getBinding().setStrImuStates("IMU正在校准");
        if(imuEmuNum == 3) getBinding().setStrImuStates("校准IMU失败");
        if(imuEmuNum == 4) getBinding().setStrImuStates("IMU数据异常,校准IMU并重启飞机");
        if(imuEmuNum == 5) getBinding().setStrImuStates("IMU正在升温");
        if(imuEmuNum == 6) getBinding().setStrImuStates("飞机可能不够稳定");
        if(imuEmuNum == 7) getBinding().setStrImuStates("正常");
        if(imuEmuNum == 8) getBinding().setStrImuStates("正常");
        if(imuEmuNum == 9) getBinding().setStrImuStates("需要进行IMU校准");
    }

    //罗盘校准
    @OnClick(R.id.txt_btn_Calibrate_compass)
    public void startCompass(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        if(ModuleVerificationUtil.isCompassAvailable() && ModuleVerificationUtil.isFlightControllerAvailable()){
            if(!JackApplication.getAircraftInstance().getFlightController().isConnected()) {T.show(mActivity,"未与设备连接"); return;}
            dialogCompass.show(JackApplication.fragmentManager,"compass_dialog");
        }
    }

    //IMU校准
    @OnClick(R.id.txt_btn_Calibrate_imu)
    public void startImuCorrect(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            if(!JackApplication.getAircraftInstance().getFlightController().isConnected()) {T.show(mActivity,"未与设备连接"); return;}
            dialogIMU.show(JackApplication.fragmentManager,"imu_dialog");
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
