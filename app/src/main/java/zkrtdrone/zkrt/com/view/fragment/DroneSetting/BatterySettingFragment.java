package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;

import com.jaredrummler.materialspinner.MaterialSpinner;
import butterknife.Bind;
import dji.common.battery.LowVoltageBehavior;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.SettingBatteryFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.UITask;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.jackmvvm.util.rxutil.RxjavaUtil;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.batteryS;

/**
 * Created by jack_xie on 17-5-5.
 * Battery
 */

public class BatterySettingFragment extends AbsFragment<SettingBatteryFragmentBinding> implements MaterialSpinner.OnItemSelectedListener {
    @Bind(R.id.low_battery_spinner) MaterialSpinner low_battery_spinner;
    @Bind(R.id.kg_low_battery_spinner) MaterialSpinner kg_low_battery_spinner;
    @Bind(R.id.s_battery_spinner) MaterialSpinner s_battery_spinner;
    @Bind(R.id.battert_seekbar_one) SeekBar battert_seekbar_one;
    @Bind(R.id.battert_seekbar_two) SeekBar battert_seekbar_two;

    @Override
    protected void init(Bundle savedInstanceState) {
        if(low_battery_spinner !=null) low_battery_spinner.setItems("LED","返航","降落","未知");
        if(kg_low_battery_spinner !=null) kg_low_battery_spinner.setItems("LED","返航","降落","未知");
        if(s_battery_spinner !=null) s_battery_spinner.setItems("3S","4S","5S","6S","7S","8S","9S","10S","11S","12S");
        low_battery_spinner.setOnItemSelectedListener(this);
        kg_low_battery_spinner.setOnItemSelectedListener(this);
        battert_seekbar_one.isPressed();
        battert_seekbar_two.isPressed();
        low_battery_spinner.isPressed();
        kg_low_battery_spinner.isPressed();
        s_battery_spinner.isPressed();
        s_battery_spinner.setSelectedIndex(9);

        if(ModuleVerificationUtil.isAircraft()){
            RxjavaUtil.doInUIThread(new UITask<Object>() {
                @Override
                public void doInUIThread() {
                    //计算获取低电压
                    getBinding().setStrBatteryLow(batteryWaring1+"");
                    battert_seekbar_one.setProgress(batteryWaring1*100);

                    //计算获取严重地电压
                    getBinding().setStrBatteryKgLow(batteryLow2+"");
                    battert_seekbar_two.setProgress(batteryLow2*100);
                }
            });

            //获取低电压动作
            if(JackApplication.getAircraftInstance().getBattery().isConnected()){
                JackApplication.getAircraftInstance().getBattery().getLevel1CellVoltageBehavior(new CommonCallbacks.CompletionCallbackWith<LowVoltageBehavior>() {
                    @Override
                    public void onSuccess(LowVoltageBehavior lowVoltageBehavior) {
                        low_battery_spinner.setSelectedIndex(lowVoltageBehavior.value());
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }

            //获取严重低电压动作
            if(JackApplication.getAircraftInstance().getBattery().isConnected()){
                JackApplication.getAircraftInstance().getBattery().getLevel2CellVoltageBehavior(new CommonCallbacks.CompletionCallbackWith<LowVoltageBehavior>() {
                    @Override
                    public void onSuccess(final LowVoltageBehavior lowVoltageBehavior) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                kg_low_battery_spinner.setSelectedIndex(lowVoltageBehavior.value());
                            }
                        });
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }

            s_battery_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    batteryS = position+3;
                    JackApplication.getAircraftInstance().getBattery().setNumberOfCells(batteryS, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });
                }
            });

            //设置低电压报警值
            battert_seekbar_one.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {
                    final int batteryLow = seekBar.getProgress();
                    getBinding().setStrBatteryLow((batteryLow/100f)+"");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(ModuleVerificationUtil.isAircraft() && JackApplication.getAircraftInstance().getBattery().isConnected()){
                                JackApplication.getAircraftInstance().getBattery().setLevel1CellVoltageThreshold(batteryLow, new CommonCallbacks.CompletionCallback() {
                                    @Override
                                    public void onResult(DJIError djiError) {

                                    }
                                });
                            }
                        }
                    }, 3000);
                }
            });
            //设置严重低电压报警值
            battert_seekbar_two.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {
                    final int kgbatteryLow = seekBar.getProgress();
                    getBinding().setStrBatteryKgLow((kgbatteryLow/100f)+"");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(ModuleVerificationUtil.isAircraft() && JackApplication.getAircraftInstance().getBattery().isConnected()){
                                JackApplication.getAircraftInstance().getBattery().setLevel2CellVoltageThreshold(kgbatteryLow, new CommonCallbacks.CompletionCallback() {
                                    @Override
                                    public void onResult(DJIError djiError) {

                                    }
                                });
                            }
                        }
                    }, 3000);
                }
            });
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        int number = -1;
        if(position == 3) number = 255; else number = position;
        switch (view.getId()){
            case R.id.low_battery_spinner://低电压动作
                if(ModuleVerificationUtil.isAircraft() && JackApplication.getAircraftInstance().getBattery().isConnected()){
                   JackApplication.getAircraftInstance().getBattery().setLevel1CellVoltageBehavior(LowVoltageBehavior.find(number), new CommonCallbacks.CompletionCallback() {
                       @Override
                       public void onResult(DJIError djiError) {

                       }
                   });
                }
                break;
            case R.id.kg_low_battery_spinner: //严重低电压动作
                if(ModuleVerificationUtil.isAircraft() && JackApplication.getAircraftInstance().getBattery().isConnected()){
                    JackApplication.getAircraftInstance().getBattery().setLevel2CellVoltageBehavior(LowVoltageBehavior.find(number), new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.setting_battery_fragment;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }
    @Override
    protected void onDelayLoad() {

    }
}
