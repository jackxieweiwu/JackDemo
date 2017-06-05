package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import butterknife.Bind;
import dji.common.LightbridgeSecondaryVideoFormat;
import dji.common.airlink.ChannelInterference;
import dji.common.airlink.ChannelSelectionMode;
import dji.common.airlink.LightbridgeAntennaRSSI;
import dji.common.airlink.LightbridgeDataRate;
import dji.common.airlink.LightbridgeSecondaryVideoOutputPort;
import dji.common.airlink.LightbridgeTransmissionMode;
import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.sdk.airlink.LightbridgeLink;
import dji.thirdparty.gson.Gson;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.SettingHdFragmentBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by jack_xie on 17-5-5.
 * HD Camera
 */

public class HdSettingFragment extends AbsFragment<SettingHdFragmentBinding> implements View.OnClickListener{
    @Bind(R.id.txt_camera_hd_custom) TextView txt_camera_hd_custom;  //自定义
    @Bind(R.id.txt_camera_hd_motion) TextView txt_camera_hd_motion;  //自动选择
    @Bind(R.id.txt_camera_mode_delay) TextView txt_camera_mode_delay;  //高画质
    @Bind(R.id.txt_camera_mode_quality) TextView txt_camera_mode_quality;  //低延迟
    @Bind(R.id.txt_camera_hd_hdmi) TextView txt_camera_hd_hdmi;  //HDMI
    @Bind(R.id.txt_camera_sdi) TextView txt_camera_sdi;  //SDI
    @Bind(R.id.spinner_camera_hd) MaterialSpinner spinner_camera_hd;
    @Bind(R.id.spinner_setting_video_mode) MaterialSpinner spinner_setting_video_mode;  //视频输出模式
    @Bind(R.id.spinner_camera_format) MaterialSpinner spinner_camera_format;  //视频输出格式
    @Bind(R.id.setting_hd_camera_seek) SeekBar setting_hd_camera_seek;  //图传码率
    @Bind(R.id.setting_bandwidth_seek) SeekBar setting_bandwidth_seek;  //带宽分配
    @Bind(R.id.switch_ios_camera_ext) Switch switch_ios_camera_ext;  //开启EXT端口
    @Bind(R.id.switch_ios_camera) Switch switch_ios_camera;  //HDMI/SDI视频输出
    @Bind(R.id.recy_list_route) RecyclerView recy_list_route;
    @Bind(R.id.rela_xinhao_one) RelativeLayout rela_xinhao_one;
    @Bind(R.id.rela_xinhao_two) RelativeLayout rela_xinhao_two;
    @Bind(R.id.ext_rela) RelativeLayout ext_rela;
    @Bind(R.id.linear_video_in) LinearLayout linear_video_in;

    private String[] hdmiSdi = {"720P_60FPS","720P_50FPS",
            "1080I_60FPS","1080I_50FPS","1080P_60FPS","1080P_50FPS","1080P_30FPS","1080P_24FPS","1080P_25FPS","720P_30FPS","720P_25FPS","720P_24FPS"};

    @Override
    protected void init(Bundle savedInstanceState) {
        final String[] list = {"4Mbps(3km)","6Mbps(2km)","8Mbps(1.5km)","10Mbps(0.7km)"};
        getBinding().setStrTransmissionModeSw(false);
        getBinding().setStrHdmiSdiVideo(false);
        spinner_camera_format.setItems(hdmiSdi);
        spinner_camera_hd.isPressed();
        switch_ios_camera_ext.isPressed();
        setting_hd_camera_seek.isPressed();
        txt_camera_hd_hdmi.isPressed();
        txt_camera_sdi.isPressed();
        switch_ios_camera.isPressed();
        spinner_setting_video_mode.isPressed();
        spinner_camera_format.isPressed();
        //设置信道是否自动
        txt_camera_hd_motion.setOnClickListener(this);
        txt_camera_hd_custom.setOnClickListener(this);
        txt_camera_mode_quality.setOnClickListener(this);
        txt_camera_mode_delay.setOnClickListener(this);
        txt_camera_sdi.setOnClickListener(this);
        txt_camera_hd_hdmi.setOnClickListener(this);
        if(ModuleVerificationUtil.isAirlinkAvailable()){

            //获取是否自动信道
            getChannelSelect();

            //获取所有的下行信道
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getChannelRange(new CommonCallbacks.CompletionCallbackWith<Integer[]>() {
                @Override
                public void onSuccess(final Integer[] integers) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinner_camera_hd.setItems(integers);
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            //获取图传码率
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getDataRate(new CommonCallbacks.CompletionCallbackWith<LightbridgeDataRate>() {
                @Override
                public void onSuccess(LightbridgeDataRate lightbridgeDataRate) {
                    final int codeRateNum = lightbridgeDataRate.value();
                    if(codeRateNum == 255) return;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getBinding().setStrCodeRate(list[codeRateNum-1]);
                            setting_hd_camera_seek.setProgress(codeRateNum-1);
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            //获取是否开启了EXT端口
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getEXTVideoInputPortEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                @Override
                public void onSuccess(final Boolean aBoolean) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getBinding().setStrTransmissionModeSw(aBoolean);
                            ext_rela.setVisibility(aBoolean?View.VISIBLE:View.GONE);
                            getBandWithAllocation(aBoolean);//获取贷款分配
                        }
                    });
                    if(aBoolean)getCameraQuaily();
                }

                @Override
                public void onFailure(DJIError djiError) {
                }
            });

            //检查辅助视频输出是否支持
            if(JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().isSecondaryVideoOutputSupported()){
                //获取是否HDMI/SDI视频输出
                JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getSecondaryVideoOutputEnabled(new CommonCallbacks.CompletionCallbackWith<Boolean>() {
                    @Override
                    public void onSuccess(final Boolean aBoolean) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getBinding().setStrHdmiSdiVideo(aBoolean);
                                linear_video_in.setVisibility(aBoolean?View.VISIBLE:View.GONE);
                            }
                        });
                    }

                    @Override
                    public void onFailure(DJIError djiError) {

                    }
                });
            }else{
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        linear_video_in.setVisibility(View.GONE);
                    }
                });
            }

            //辅助视频输出的通道
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getSecondaryVideoOutputPort(new CommonCallbacks.CompletionCallbackWith<LightbridgeSecondaryVideoOutputPort>() {
                @Override
                public void onSuccess(LightbridgeSecondaryVideoOutputPort lightbridgeSecondaryVideoOutputPort) {
                    final int number = lightbridgeSecondaryVideoOutputPort.value();
                    if(number ==255) return;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(number == 0){ //HDMI
                                txt_camera_hd_hdmi.setTextColor(Color.GREEN);
                            }else if(number == 1){  //SDI
                                txt_camera_sdi.setTextColor(Color.GREEN);
                            }else{
                                txt_camera_sdi.setTextColor(Color.WHITE);
                                txt_camera_hd_hdmi.setTextColor(Color.WHITE);
                            }
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });

            //获取遥控器与天空端的RSSI
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setAircraftAntennaRSSICallback(new LightbridgeLink.AntennaRSSICallback() {
                @Override
                public void onUpdate(LightbridgeAntennaRSSI lightbridgeAntennaRSSI) {
                    getBinding().setStrAntennaOne(lightbridgeAntennaRSSI.getAntenna1()+"dBm");
                    getBinding().setStrAntennatwo(lightbridgeAntennaRSSI.getAntenna2()+"dBm");
                }
            });

            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setRemoteControllerAntennaRSSICallback(new LightbridgeLink.AntennaRSSICallback() {
                @Override
                public void onUpdate(LightbridgeAntennaRSSI lightbridgeAntennaRSSI) {
                    getBinding().setStrAntennaThree(lightbridgeAntennaRSSI.getAntenna1()+"dBm");
                    getBinding().setStrAntennaFour(lightbridgeAntennaRSSI.getAntenna2()+"dBm");
                }
            });

            //获取通道干扰量
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setChannelInterferenceCallback(new LightbridgeLink.ChannelInterferenceCallback() {
                @Override
                public void onResult(ChannelInterference[] channelInterferences) {
                    T.show(mActivity,channelInterferences.length+"---*");
                }
            });


            //设置下行通道
            spinner_camera_hd.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setChannelNumber(position, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {

                        }
                    });
                }
            });

            //设置图传码率
            setting_hd_camera_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int number = seekBar.getProgress()+1;
                            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setDataRate(LightbridgeDataRate.find(number), new CommonCallbacks.CompletionCallback() {
                                @Override
                                public void onResult(DJIError djiError) {

                                }
                            });
                        }
                    }, 3000);
                }
            });

            //设置带宽分配
            setting_bandwidth_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {
                    final int number = seekBar.getProgress();
                    final boolean bool = getBinding().getStrTransmissionModeSw();
                    ext_rela.setVisibility(number == 10?View.VISIBLE:View.GONE);
                    if(bool)getBinding().setStrHdmiAv("LB:"+number+"% EXT:"+(10-number)+"%");
                    else getBinding().setStrHdmiAv("HDMI:"+number+"% AV:"+(10-number)+"%");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(bool){
                                JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setBandwidthAllocationForLBVideoInputPort(number/10f, new CommonCallbacks.CompletionCallback() {
                                    @Override
                                    public void onResult(DJIError djiError) {
                                        if(djiError == null) getBandWithAllocation(bool);
                                    }
                                });
                            }else{
                                JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setBandwidthAllocationForHDMIVideoInputPort(number/10f, new CommonCallbacks.CompletionCallback() {
                                    @Override
                                    public void onResult(DJIError djiError) {
                                        if(djiError == null) getBandWithAllocation(bool);
                                    }
                                });
                            }
                        }
                    }, 3000);
                }
            });

            //开启或者关闭EXT端口
            switch_ios_camera_ext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setEXTVideoInputPortEnabled(isChecked, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {
                            if(djiError == null){
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getBandWithAllocation(isChecked);
                                        getBinding().setStrTransmissionModeSw(isChecked);
                                        ext_rela.setVisibility(isChecked?View.VISIBLE:View.GONE);
                                    }
                                });
                                if(isChecked)getCameraQuaily();
                            }
                        }
                    });
                }
            });

            //是否开启HDMI/SDI端口
            switch_ios_camera.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setSecondaryVideoOSDEnabled(isChecked, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(final DJIError djiError) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getBinding().setStrHdmiSdiVideo(djiError == null?true:false);
                                    linear_video_in.setVisibility(djiError==null?View.VISIBLE:View.GONE);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (GeneralUtils.isFastDoubleClick()) return;
        if(ModuleVerificationUtil.isAirlinkAvailable() && JackApplication.getAircraftInstance().getAirLink().isConnected()){
            switch (v.getId()){
                case R.id.txt_camera_hd_custom://自定义
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setChannelSelectionMode(ChannelSelectionMode.MANUAL, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(final DJIError djiError) {
                            if(djiError == null){
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rela_xinhao_one.setVisibility(djiError == null?View.VISIBLE:View.GONE);
                                        rela_xinhao_two.setVisibility(djiError == null?View.VISIBLE:View.GONE);
                                    }
                                });
                                getChannelSelect();

                                //获取自定义的下行通道数
                                JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getChannelNumber(new CommonCallbacks.CompletionCallbackWith<Integer>() {
                                    @Override
                                    public void onSuccess(Integer integer) {
                                        spinner_camera_hd.setSelectedIndex(integer);
                                    }

                                    @Override
                                    public void onFailure(DJIError djiError) {

                                    }
                                });
                            }
                        }
                    });
                    break;

                case R.id.txt_camera_hd_motion://自动选择
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setChannelSelectionMode(ChannelSelectionMode.AUTO, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(final DJIError djiError) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rela_xinhao_one.setVisibility(djiError == null?View.VISIBLE:View.GONE);
                                    rela_xinhao_two.setVisibility(djiError == null?View.VISIBLE:View.GONE);
                                }
                            });

                            if(djiError == null)getChannelSelect();
                        }
                    });
                    break;

                case R.id.txt_camera_mode_quality:  //低延迟
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setTransmissionMode(LightbridgeTransmissionMode.LOW_LATENCY, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {
                            if(djiError == null){
                                getCameraQuaily();
                            }
                        }
                    });
                    break;

                case R.id.txt_camera_mode_delay:  //高画质
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setTransmissionMode(LightbridgeTransmissionMode.HIGH_QUALITY, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {
                            if(djiError == null){
                                getCameraQuaily();
                            }
                        }
                    });
                    break;

                case R.id.txt_camera_hd_hdmi:
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setSecondaryVideoOutputPort(LightbridgeSecondaryVideoOutputPort.HDMI, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {
                            if(djiError == null){
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt_camera_hd_hdmi.setTextColor(Color.GREEN);
                                        txt_camera_sdi.setTextColor(Color.WHITE);
                                    }
                                });

                            }
                        }
                    });
                    break;
                case R.id.txt_camera_sdi:
                    JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().setSecondaryVideoOutputPort(LightbridgeSecondaryVideoOutputPort.SDI, new CommonCallbacks.CompletionCallback() {
                        @Override
                        public void onResult(DJIError djiError) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt_camera_sdi.setTextColor(Color.GREEN);
                                    txt_camera_hd_hdmi.setTextColor(Color.WHITE);
                                }
                            });

                        }
                    });
                    break;
            }
        }
    }

    //获取是否自动信道
    private void getChannelSelect(){
        JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getChannelSelectionMode(new CommonCallbacks.CompletionCallbackWith<ChannelSelectionMode>() {
            @Override
            public void onSuccess(ChannelSelectionMode channelSelectionMode) {
                final int airlinkNumber = channelSelectionMode.value();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(airlinkNumber == 0){  //auto
                            txt_camera_hd_motion.setTextColor(Color.GREEN);
                        }else if(airlinkNumber == 1){
                            txt_camera_hd_custom.setTextColor(Color.GREEN);
                        }else{
                            txt_camera_hd_custom.setTextColor(Color.WHITE);
                            txt_camera_hd_motion.setTextColor(Color.WHITE);
                        }
                    }
                });
            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    //获取是否是低延迟还是高画质
    private void getCameraQuaily(){
        JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getTransmissionMode(new CommonCallbacks.CompletionCallbackWith<LightbridgeTransmissionMode>() {
            @Override
            public void onSuccess(LightbridgeTransmissionMode lightbridgeTransmissionMode) {
                final int number = lightbridgeTransmissionMode.value();
                if(number == 255) return;
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(number == 0){
                            txt_camera_mode_delay.setTextColor(Color.GREEN);
                        }else if(number == 1){
                            txt_camera_mode_quality.setTextColor(Color.GREEN);
                        }else{
                            txt_camera_mode_delay.setTextColor(Color.WHITE);
                            txt_camera_mode_quality.setTextColor(Color.WHITE);
                        }
                    }
                });
            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    //获取贷款分配
    private void getBandWithAllocation(boolean bool){
        if(bool){ // LB/Ext
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getBandwidthAllocationForLBVideoInputPort(new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {
                    final int fnum = (int) (aFloat*10);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getBinding().setStrHdmiAv("LB:"+fnum+"% EXT:"+(10-fnum)+"%");
                            setting_bandwidth_seek.setProgress(fnum);
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }else{ //AV/HDMI
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getBandwidthAllocationForHDMIVideoInputPort(new CommonCallbacks.CompletionCallbackWith<Float>() {
                @Override
                public void onSuccess(Float aFloat) {
                    final int fnum = (int) (aFloat*10);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getBinding().setStrHdmiAv("HDMI:"+fnum+"% AV:"+(10-fnum)+"%");
                            setting_bandwidth_seek.setProgress(fnum);
                        }
                    });
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    //获取辅助视频输出格式与模式
    private void getAuxiliaryVideoMode(boolean bool){
        if(bool){ //hdmi
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getSecondaryVideoOutputFormatForPort(LightbridgeSecondaryVideoOutputPort.HDMI, new CommonCallbacks.CompletionCallbackWith<LightbridgeSecondaryVideoFormat>() {
                @Override
                public void onSuccess(LightbridgeSecondaryVideoFormat lightbridgeSecondaryVideoFormat) {
                    spinner_camera_format.setSelectedIndex(lightbridgeSecondaryVideoFormat.value());
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }else { //SDI
            JackApplication.getAircraftInstance().getAirLink().getLightbridgeLink().getSecondaryVideoOutputFormatForPort(LightbridgeSecondaryVideoOutputPort.SDI, new CommonCallbacks.CompletionCallbackWith<LightbridgeSecondaryVideoFormat>() {
                @Override
                public void onSuccess(LightbridgeSecondaryVideoFormat lightbridgeSecondaryVideoFormat) {
                    spinner_camera_format.setSelectedIndex(lightbridgeSecondaryVideoFormat.value());
                }

                @Override
                public void onFailure(DJIError djiError) {

                }
            });
        }
    }

    @Override
    protected void onDelayLoad() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.setting_hd_fragment;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }
}
