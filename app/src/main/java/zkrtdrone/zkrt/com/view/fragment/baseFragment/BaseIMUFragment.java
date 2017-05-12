package zkrtdrone.zkrt.com.view.fragment.baseFragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;

import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.flightcontroller.imu.IMUState;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.CalendarUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by root on 17-5-10.
 */

public abstract class BaseIMUFragment<VB extends ViewDataBinding> extends AbsFragment<VB> {
    @Override
    protected void init(Bundle savedInstanceState) {
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            JackApplication.getAircraftInstance().getFlightController().setIMUStateCallback(new IMUState.Callback() {
                @Override
                public void onUpdate(@NonNull IMUState imuState) {
                    updateCallBack(imuState);
                }
            });
        }
    }

    public void updateCallBack(IMUState imuState){}

    @Override
    protected void onDelayLoad() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }
}
