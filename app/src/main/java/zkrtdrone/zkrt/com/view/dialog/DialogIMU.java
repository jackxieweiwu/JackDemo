package zkrtdrone.zkrt.com.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import butterknife.Bind;
import dji.common.error.DJIError;
import dji.common.flightcontroller.imu.IMUState;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.DialogImuBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsDialogFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by root on 17-5-10.
 */

public class DialogIMU extends AbsDialogFragment<DialogImuBinding> implements View.OnClickListener{
    @Bind(R.id.enter)Button mEnter;
    @Bind(R.id.cancel) Button mCancel;
    @Bind(R.id.seekbar_imu_dialog) SeekBar seekbar_imu_dialog;

    @Override
    protected void init(Bundle savedInstanceState) {
        mCancel.setOnClickListener(this);
        mEnter.setOnClickListener(this);
        mCancel.setVisibility(View.GONE);
        getBinding().setStrImuTitle("IMU校准");
        getBinding().setStrImuMessage("请拆卸螺旋桨\t\n请勿移动飞行器\t\n保持飞行器电源开启，请勿启动电机\t\n正在校准中....");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_imu;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enter:
                mEnter.setVisibility(View.GONE);
                if(ModuleVerificationUtil.isFlightControllerAvailable()){
                    //开始IMU校准
                    JackApplication.getAircraftInstance().getFlightController().startIMUCalibration(new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });

                    //反馈IMU校准的状态
                    JackApplication.getAircraftInstance().getFlightController().setIMUStateCallback(new IMUState.Callback() {
                        @Override
                        public void onUpdate(@NonNull IMUState imuState) {
                            if(!imuState.isConnected()){T.show(mActivity,"IMU设备连接异常"); return;}
                            int imunum = imuState.getCalibrationProgress();
                            int imuStates = imuState.getCalibrationState().value();
                            seekbar_imu_dialog.setProgress(imunum);
                            if(imunum == 100){
                                if(imuStates == 2){
                                    T.show(mActivity,"校准成功");
                                    dismiss();
                                }else if(imuStates == 3){
                                    T.show(mActivity,"校准失败");
                                    dismiss();
                                }
                            }
                        }
                    });
                }
                break;
        }
        dismiss();
    }
}
