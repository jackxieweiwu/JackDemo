package zkrtdrone.zkrt.com.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import dji.common.error.DJIError;
import dji.common.flightcontroller.CompassCalibrationState;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.DialogCompassBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsDialogFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by jack_xie on 17-5-10.
 */

public class DialogCompass extends AbsDialogFragment<DialogCompassBinding> implements View.OnClickListener{
    @Bind(R.id.cancel) Button mCancel;
    @Bind(R.id.img_compass_dialog) ImageView img_compass_dialog;
    @Override
    protected void init(Bundle savedInstanceState) {
        mCancel.setOnClickListener(this);
        getBinding().setStrCompassTitle("指南针校准");

        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            if(JackApplication.getAircraftInstance().getFlightController().isConnected()){
                //开始指南针校准
                JackApplication.getAircraftInstance().getFlightController().getCompass().startCalibration(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                    }
                });

                //指南针校准的状态反馈
                JackApplication.getAircraftInstance().getFlightController().getCompass().setCalibrationStateCallback(new CompassCalibrationState.Callback() {
                    @Override
                    public void onUpdate(@NonNull CompassCalibrationState compassCalibrationState) {
                        int compassnum = compassCalibrationState.value();
                        if(compassnum == 0){
                            getBinding().setStrCompassMessage("成功进入校准指南针模式，请远离金属或带有强电物体等，并在离地面1.5(4.9ft)左右，水平旋转飞行器360度");
                            img_compass_dialog.setBackgroundResource(R.mipmap.fpv_compass_a3_horizontal);
                        }else if(compassnum == 1){
                            getBinding().setStrCompassMessage("将飞行器机头朝上，垂直于地面并将飞机旋转360度");
                            img_compass_dialog.setBackgroundResource(R.mipmap.fpv_compass_a3_vertical);
                        }else if(compassnum == 2){
                            T.show(mActivity,"指南针校准成功");
                            dismiss();
                        }else if(compassnum == 3){
                            T.show(mActivity,"指南针校准失败");
                            dismiss();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if(ModuleVerificationUtil.isFlightControllerAvailable()){
                    if(JackApplication.getAircraftInstance().getFlightController().isConnected()){
                        //开始指南针校准
                        JackApplication.getAircraftInstance().getFlightController().getCompass().stopCalibration(new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(DJIError djiError) {
                            }
                        });
                    }
                }
                break;
        }
        dismiss();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_compass;
    }

    @Override
    protected void dataCallback(int result, Object data) {

    }
}
