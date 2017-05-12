package zkrtdrone.zkrt.com.view.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.DialogPairingBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsDialogFragment;
import zkrtdrone.zkrt.com.moudle.BindingModule;

/**
 * Created by jack_xie on 17-4-26.
 */

@SuppressLint("ValidFragment")
public class DialogPairimg extends AbsDialogFragment<DialogPairingBinding> implements View.OnClickListener {
    @Bind(R.id.enter)Button mEnter;
    @Bind(R.id.cancel) Button mCancel;

    @Override
    protected void init(Bundle savedInstanceState) {
        getModule(BindingModule.class).dialogBindingTest(this);
        mEnter.setOnClickListener(this);
        mEnter.setVisibility(View.GONE);
        mCancel.setOnClickListener(this);
        getBinding().setStrTitle("遥控器对频");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_pairing;
    }

    @Override
    protected void dataCallback(int result, Object data) {}

    @Override
    public void onClick(View v) {
        int numberCallback = getModule(BindingModule.class).getCallBack();
        switch (v.getId()) {
            case R.id.enter:
                //将数据回调给寄主
                //getSimplerModule().onDialog(Constance.KEY.IP_DIALOG,"对话框确认");

                break;
            case R.id.cancel:
                //getSimplerModule().onDialog(Constance.KEY.IP_DIALOG, "对话框取消");
                getModule(BindingModule.class).dialogRemoteStopPairing(this);
                break;
        }
        dismiss();
    }


    public void setAddDismiss(){
        dismiss();
    }
}
