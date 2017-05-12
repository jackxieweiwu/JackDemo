package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.jaredrummler.materialspinner.MaterialSpinner;
import butterknife.Bind;
import butterknife.OnClick;
import dji.common.error.DJIError;
import dji.common.flightcontroller.ConnectionFailSafeBehavior;
import dji.common.flightcontroller.FlightOrientationMode;
import dji.common.model.LocationCoordinate2D;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.SettingDroneFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.peploLat;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.peploLng;

/**
 * Created by jack_xie on 17-5-5.
 * Drone
 */

public class DroneSettingFragment extends AbsFragment<SettingDroneFragmentBinding> {
    @Bind(R.id.distance_ios_led) Switch distance_ios_led;
    @Bind(R.id.distance_ios_fly) Switch distance_ios_fly;
    @Bind(R.id.fly_ios) Switch fly_ios;
    @Bind(R.id.edit_setting_drone_gohome_hight) EditText edit_setting_drone_gohome_hight;
    @Bind(R.id.edit_setting_fly_max_hight) EditText edit_setting_fly_max_hight;
    @Bind(R.id.edit_setting_fly_radius_distance) EditText edit_setting_fly_radius_distance;
    @Bind(R.id.spinner_outof) MaterialSpinner spinner_outof;
    @Bind(R.id.spinner_heading) MaterialSpinner spinner_heading;
    @Bind(R.id.rela_dis_number) RelativeLayout rela_dis_number;

    @Override
    protected void init(Bundle savedInstanceState) {

        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            //初始化失控行为的adapter
            if(spinner_outof != null) spinner_outof.setItems("徘徊","降落","回家","未知");
            if(spinner_heading != null) spinner_heading.setItems("机头锁","航向锁","返航锁");
            spinner_outof.isPressed();
            spinner_heading.isPressed();

            //LED
            JackApplication.getAircraftInstance().getFlightController().getLEDsEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(final Boolean aBoolean) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            distance_ios_led.setChecked(aBoolean);
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            //得到反航高度
            JackApplication.getAircraftInstance().getFlightController().getGoHomeHeightInMeters(new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {
                    getBinding().setStrGohomeHiget(aFloat+"");
                }

                @Override
                public void onFailure(DJIError djiError) {
                }
            });

            //获得飞机的最大飞行高度限制。
            JackApplication.getAircraftInstance().getFlightController().getMaxFlightHeight(new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {
                    getBinding().setStrHightDrone(aFloat+"");
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            //获得飞机的最大飞行半径限制。
            getDroneRadius();

            //获得是否开启限制飞行半径
            JackApplication.getAircraftInstance().getFlightController().getMaxFlightRadiusLimitationEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(final Boolean aBoolean) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(aBoolean) getDroneRadius();
                            distance_ios_fly.setChecked(aBoolean);
                            rela_dis_number.setVisibility(aBoolean == true?View.VISIBLE:View.GONE);
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });


            //设置是否处于新手模式
            fly_ios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //高度限制在30m，距离限制在100m
                    setMaxHightDrone(30f);
                    setMaxRadiusDrone(50f);
                }
            });

            //设置失控行为
            spinner_outof.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    JackApplication.getAircraftInstance().getFlightController().setConnectionFailSafeBehavior(ConnectionFailSafeBehavior.find(position), new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });
                }
            });

            //设置飞行器方向锁
            spinner_heading.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    int number = -1;
                    if(position == 0)  number = 255;
                    else number = position;
                    JackApplication.getAircraftInstance().getFlightController().setFlightOrientationMode(FlightOrientationMode.find(number), new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });
                }
            });

            //设置LED是否闪烁
            distance_ios_led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    JackApplication.getAircraftInstance().getFlightController().setLEDsEnabled(isChecked, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });
                }
            });

            distance_ios_fly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    JackApplication.getAircraftInstance().getFlightController().setMaxFlightRadiusLimitationEnabled(isChecked, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(final DJIError djiError) {
                            T.show(mActivity,djiError+"****/");
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rela_dis_number.setVisibility(djiError == null?View.VISIBLE:View.GONE);
                                    if(djiError == null)getDroneRadius();
                                }
                            });
                        }
                    });
                }
            });

            //设置反航高度
            edit_setting_drone_gohome_hight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String goHomeHiget = edit_setting_drone_gohome_hight.getText().toString().trim();
                    if(goHomeHiget.isEmpty() && goHomeHiget.equals("")) {getBinding().setStrGohomeHiget(20+"");goHomeHiget = "20";}
                    float gohome_h = Float.parseFloat(goHomeHiget);
                    if(gohome_h >500){gohome_h = 500f; getBinding().setStrGohomeHiget(500+"");}
                    else if(gohome_h<20){gohome_h = 20f; getBinding().setStrGohomeHiget(20+"");}
                    final float finalGohome_h = gohome_h;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JackApplication.getAircraftInstance().getFlightController().setGoHomeHeightInMeters(finalGohome_h, new CommonCallbacks.CompletionCallback() {
                                @Override
                                public void onResult(DJIError djiError) {

                                }
                            });
                        }
                    },3000);
                }
            });

            //设置飞行高度限制
            edit_setting_fly_max_hight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    String goMaxHiget = edit_setting_fly_max_hight.getText().toString().trim();
                    if(goMaxHiget.isEmpty() && goMaxHiget.equals("")) {getBinding().setStrHightDrone(20+"");goMaxHiget = "20";}
                    float goMax_h = Float.parseFloat(goMaxHiget);
                    if(goMax_h >500){goMax_h = 500f; getBinding().setStrHightDrone(500+"");}
                    else if(goMax_h<20){goMax_h = 20f; getBinding().setStrHightDrone(20+"");}
                    final float finalGoMax_h = goMax_h;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setMaxHightDrone(finalGoMax_h);
                        }
                    },3000);
                }
            });

            //设定飞机的最大飞行半径限制
            edit_setting_fly_radius_distance.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String goHomeRadius = edit_setting_fly_radius_distance.getText().toString().trim();
                    if(goHomeRadius.isEmpty() && goHomeRadius.equals("")) {getBinding().setStrDroneRadiusBooleanNumber(15+"");goHomeRadius = "15";}
                    float gomax_r = Float.parseFloat(goHomeRadius);
                    if(gomax_r >8000){gomax_r = 8000f; getBinding().setStrDroneRadiusBooleanNumber(8000+"");}
                    else if(gomax_r<15){gomax_r = 15f; getBinding().setStrDroneRadiusBooleanNumber(15+"");}
                    final float finalGomax_r = gomax_r;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setMaxRadiusDrone(finalGomax_r);
                        }
                    }, 3000);
                }
            });
        }
    }

    //获取最大的飞行半径限制
    private void getDroneRadius(){
        JackApplication.getAircraftInstance().getFlightController().getMaxFlightRadius(new CommonCallbacks.CompletionCallbackWith<Float>() {
            @Override
            public void onSuccess(Float aFloat) {
                int number = (int) ((aFloat*10)/10);
                getBinding().setStrDroneRadiusBooleanNumber(number+"");
            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    //设置飞行高度限制
    private void setMaxHightDrone(float f){
        JackApplication.getAircraftInstance().getFlightController().setMaxFlightHeight(f, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    //设置最大的飞行半径
    private void setMaxRadiusDrone(Float f){
        JackApplication.getAircraftInstance().getFlightController().setMaxFlightRadius(f, new CommonCallbacks.CompletionCallback() {
            @Override
            public void onResult(DJIError djiError) {

            }
        });
    }

    //定义反航点（操作者当前的位置）
    @OnClick(R.id.img_gohome_setting_peplo)
    public void onClickGoHomePeploGPS(View v){
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            JackApplication.getAircraftInstance().getFlightController().setHomeLocation(new LocationCoordinate2D(peploLat,peploLng), new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    //定义反航点（飞行器当前的位置为反航点）
    @OnClick(R.id.img_gohome_setting_drone)
    public void onClickGOHomeDroneGPS(View v){
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            JackApplication.getAircraftInstance().getFlightController().setHomeLocationUsingAircraftCurrentLocation(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {

                }
            });
        }
    }

    @Override
    protected void onDelayLoad() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.setting_drone_fragment;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }
}
