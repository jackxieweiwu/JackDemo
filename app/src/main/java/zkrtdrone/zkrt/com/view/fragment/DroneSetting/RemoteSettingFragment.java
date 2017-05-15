package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.jaredrummler.materialspinner.MaterialSpinner;

import butterknife.Bind;
import butterknife.OnClick;
import dji.common.error.DJIError;
import dji.common.remotecontroller.AircraftMappingStyle;
import dji.common.remotecontroller.ChargeMobileMode;
import dji.common.remotecontroller.ChargeRemaining;
import dji.common.remotecontroller.GimbalAxis;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.SettingRemoteFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.view.dialog.DialogPairimg;

/**
 * Created by jack_xie on 17-5-9.
 * 遥控器
 */

public class RemoteSettingFragment extends AbsFragment<SettingRemoteFragmentBinding>{
    @Bind(R.id.edit_remote_name) EditText edit_remote_name;
    @Bind(R.id.edit_remote_pwd) EditText edit_remote_pwd;
    @Bind(R.id.spinner_ios_phone) MaterialSpinner spinner_ios_phone;
    @Bind(R.id.spinner_ios_pyr) MaterialSpinner spinner_ios_pyr;
    @Bind(R.id.spinner_rocker_mode) MaterialSpinner spinner_rocker_mode;  //遥控器模式
    @Bind(R.id.img_rocker_mode) ImageView img_rocker_mode;  //遥控器模式

    @Override
    protected void init(Bundle savedInstanceState) {
        spinner_ios_phone.setItems("不充电","一直充","智能充","未知");
        spinner_ios_pyr.setItems("俯仰","横滚","偏航");
        spinner_rocker_mode.setItems("风格1","风格2","风格3","自定义");
        spinner_rocker_mode.isPressed();
        spinner_ios_phone.isPressed();
        spinner_ios_pyr.isPressed();

        if(ModuleVerificationUtil.isRemoteControllerAvailable()){
            if(JackApplication.getAircraftInstance().getRemoteController().isConnected()){
                //获取遥控器的名称
                JackApplication.getAircraftInstance().getRemoteController().getName(new CommonCallbacks.CompletionCallbackWith<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getBinding().setStrRemoteName(s);
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });

                //获取遥控器的固件版本
                JackApplication.getAircraftInstance().getRemoteController().getFirmwareVersion(new CommonCallbacks.CompletionCallbackWith<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getBinding().setStrRemoteVersion(s);
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });

                //获取遥控器的密码
                JackApplication.getAircraftInstance().getRemoteController().getPassword(new CommonCallbacks.CompletionCallbackWith<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getBinding().setStrRemotePwd(s);
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });

                //获取遥控器电量
                JackApplication.getAircraftInstance().getRemoteController().setChargeRemainingCallback(new ChargeRemaining.Callback() {
                    @Override
                    public void onUpdate(@NonNull ChargeRemaining chargeRemaining) {
                        getBinding().setStrRemoteBattery(chargeRemaining.getRemainingChargeInPercent()+"%");
                    }
                });

                //获取是否给移动设备充电
                JackApplication.getAircraftInstance().getRemoteController().getChargeMobileMode(new CommonCallbacks.CompletionCallbackWith<ChargeMobileMode>() {
                    @Override
                    public void onSuccess(ChargeMobileMode chargeMobileMode) {
                        int num = chargeMobileMode.value();
                        spinner_ios_phone.setSelectedIndex(num);
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });

                //获取左上角驳轮的作用
                JackApplication.getAircraftInstance().getRemoteController().getLeftWheelGimbalControlAxis(new CommonCallbacks.CompletionCallbackWith<GimbalAxis>() {
                    @Override
                    public void onSuccess(final GimbalAxis gimbalAxis) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                spinner_ios_pyr.setSelectedIndex(gimbalAxis.value());
                            }
                        });
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });

                JackApplication.getAircraftInstance().getRemoteController().getAircraftMappingStyle(new CommonCallbacks.CompletionCallbackWith<AircraftMappingStyle>() {
                    @Override
                    public void onSuccess(AircraftMappingStyle aircraftMappingStyle) {
                        final int numb = aircraftMappingStyle.value();
                        spinner_rocker_mode.setSelectedIndex(numb+1);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(numb == 3)
                                    img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_china);
                                else if(numb == 2)
                                    img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_usa);
                                else if(numb == 1)
                                    img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_japan);
                                else if(numb == 255) img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_custom);
                            }
                        });

                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });

                spinner_rocker_mode.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, final int position, long id, Object item) {
                        JackApplication.getAircraftInstance().getRemoteController().setAircraftMappingStyle(AircraftMappingStyle.find(position+1), new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(DJIError djiError) {
                                if(djiError == null)
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(position == 3)
                                            img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_china);
                                        else if(position == 2)
                                            img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_usa);
                                        else if(position == 1)
                                            img_rocker_mode.setImageResource(R.mipmap.setting_ui_rc_japan);
                                    }
                                });
                            }
                        });
                    }
                });

                //设置左上角轮子的作用
                spinner_ios_pyr.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        JackApplication.getAircraftInstance().getRemoteController().setLeftWheelGimbalControlAxis(GimbalAxis.find(position), new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(DJIError djiError) {

                            }
                        });
                    }
                });

                //设置给移动设备充电
                spinner_ios_phone.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        JackApplication.getAircraftInstance().getRemoteController().setChargeMobileMode(ChargeMobileMode.find(position), new CommonCallbacks.CompletionCallback() {
                            @Override
                            public void onResult(DJIError djiError) {

                            }
                        });
                    }
                });

                //设置遥控器的名称
                edit_remote_name.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String remote = getBinding().getStrRemoteName().toString().trim();
                                if(!remote.isEmpty() && !remote.equals("")){
                                    JackApplication.getAircraftInstance().getRemoteController().setName(remote, new CommonCallbacks.CompletionCallback() {
                                        @Override
                                        public void onResult(DJIError djiError) {

                                        }
                                    });
                                }
                            }
                        }, 3000);
                    }
                });

                //设置遥控器的密码
                edit_remote_pwd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String remotePwd = getBinding().getStrRemotePwd().toString().trim();
                                if(!remotePwd.isEmpty() && !remotePwd.equals("")){
                                    JackApplication.getAircraftInstance().getRemoteController().setPassword(remotePwd, new CommonCallbacks.CompletionCallback() {
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
    }

    @OnClick(R.id.txt_remote_frequency)
    public void ParringRemote(View v){
        //进入配对模式
        DialogPairimg dialog = new DialogPairimg();
        dialog.show(JackApplication.fragmentManager, "ip_dialog");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.setting_remote_fragment;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    protected void onDelayLoad() {

    }
}
