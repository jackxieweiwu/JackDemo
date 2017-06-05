package zkrtdrone.zkrt.com.moudle;

import android.content.Context;
import android.os.CountDownTimer;

import dji.common.error.DJIError;
import dji.common.remotecontroller.PairingState;
import dji.common.util.CommonCallbacks;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.databinding.DialogPairingBinding;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseModule;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.view.dialog.DialogPairimg;

/**
 * Created by jack_xie on 17-4-26.
 */

public class BindingModule extends BaseModule{
    private static int numberCallBack = -1;
    public BindingModule(Context context) {
        super(context);
    }

    /**
     * Activity binding测试
     */
    public void activityBindingTest() {
        /*HttpUtil util = HttpUtil.getInstance(getContext());
        util.get(IP_URL, new HttpUtil.AbsResponse() {
            @Override public void onResponse(String data) {
                super.onResponse(data);
                try {
                    JSONObject obj = new JSONObject(data);
                    String country = obj.getString("country");
                    String province = obj.getString("province");
                    String city = obj.getString("city");
                    String str =
                            "现在是在Module中调用binding显示数据！！\n你的IP地址是：" + country + " " + province + " " + city;
                    // TODO: 2016/9/16 这里需要你进行Binding的指定，现在可以直接在module里面处理显示了
                    getBinding(ActivityAbsBinding.class).setModuleStr(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override public void onError(Object error) {
                super.onError(error);
            }
        });*/
    }

    public void fragmentBindingTest() {
        //getBinding(FragmentModuleBinding.class).setModuleStr("fragment module binding测试");
    }

    //启动遥控器对频
    public void dialogBindingTest(final DialogPairimg dialogPairimgClass) {
        if(ModuleVerificationUtil.isRemoteControllerAvailable()){
            JackApplication.getAircraftInstance().getRemoteController().startPairing(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                   if(djiError!=null){
                       numberCallBack = 400;
                       getBinding(DialogPairingBinding.class).setStrMessage("启用对频失败!");
                   }else{
                       CountDownTimer cdt = new CountDownTimer(60000, 1000) {
                           @Override
                           public void onTick(long millisUntilFinished) {
                               int i = (int) millisUntilFinished/1000;
                               getBinding(DialogPairingBinding.class).setStrMessage("遥控器处于对频状态，时间为"+i+".在对频率状态下不要关闭飞控与遥控器电源");
                               remoteStates(dialogPairimgClass);
                           }
                           @Override
                           public void onFinish() {
                               dialogPairimgClass.setAddDismiss();
                               dialogRemoteStopPairing(dialogPairimgClass);
                           }
                       };
                       cdt.start();
                   }
                }
            });
        }else{
            numberCallBack = 401;
            getBinding(DialogPairingBinding.class).setStrMessage("请检查遥控器与地面战的连接线路!");
        }
    }

    //获取对频状态
    private void remoteStates(final DialogPairimg dialogPairimgClass){
        JackApplication.getAircraftInstance().getRemoteController().getPairingState(new CommonCallbacks.CompletionCallbackWith<PairingState>() {
            @Override
            public void onSuccess(PairingState pairingState) {
                switch (pairingState.value()){
                    case 0: //ok
                        dialogPairimgClass.setAddDismiss();
                        break;
                    case 1:    //配对中
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onFailure(DJIError djiError) {

            }
        });
    }

    //取消对频率
    public void dialogRemoteStopPairing(final DialogPairimg dialogPairimg){
        if(ModuleVerificationUtil.isRemoteControllerAvailable()){
            JackApplication.getAircraftInstance().getRemoteController().stopPairing(new CommonCallbacks.CompletionCallback() {
                @Override
                public void onResult(DJIError djiError) {
                    numberCallBack = djiError==null?500:501;
                    if(djiError == null){
                        dialogPairimg.setAddDismiss();
                    }else{
                        getBinding(DialogPairingBinding.class).setStrMessage("取消对频失败,请检查遥控器与地面战的连接线路!");
                    }
                }
            });
        }
    }

    public static int getCallBack(){
        return numberCallBack;
    }
}

