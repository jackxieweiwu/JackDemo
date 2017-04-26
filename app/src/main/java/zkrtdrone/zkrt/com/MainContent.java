package zkrtdrone.zkrt.com;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.squareup.otto.Subscribe;
import dji.sdk.base.BaseProduct;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ViewWrapper;
import zkrtdrone.zkrt.com.view.MainStart;
import zkrtdrone.zkrt.com.view.dialog.DialogPairimg;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.boolRemote;

/**
 * Created by jack_xie on 15/12/18.
 */
public class MainContent extends RelativeLayout {
    public static final String TAG = MainContent.class.getName();
    public MainContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private BaseProduct mProduct;
    private Button ftb_start,ftb_frequency;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        JackApplication.getEventBus().register(this);
        initUI();
    }

    private void initUI() {
        ftb_start = (Button) findViewById(R.id.ftb_start);
        ftb_frequency = (Button) findViewById(R.id.ftb_frequency);
        ftb_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralUtils.isFastDoubleClick()) return;
                JackApplication.getEventBus().post(new ViewWrapper(new MainStart(JackApplication.mActivity),
                                                          R.string.activity_component_list));
            }
        });

        ftb_frequency.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralUtils.isFastDoubleClick()) return;
                //进入配对模式
                DialogPairimg dialog = new DialogPairimg(this);
                dialog.show(JackApplication.fragmentManager, "ip_dialog");
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        refreshSDKRelativeUI();
        super.onAttachedToWindow();
    }

    @Subscribe
    public void onConnectivityChange(JackApplication.ConnectivityChangeEvent event) {
        post(new Runnable() {
            @Override
            public void run() {
                refreshSDKRelativeUI();
            }
        });
    }

    private void refreshSDKRelativeUI() {
        mProduct = JackApplication.getProductInstance();
        //检查与遥控器连接是否正常
        if(JackApplication.isAircraftConnected()){
            boolRemote = JackApplication.getAircraftInstance().getRemoteController().isConnected();
            if(!boolRemote){
                ftb_start.setVisibility(GONE);
                ftb_frequency.setVisibility(GONE);
            }
        }

        if (null != mProduct && mProduct.isConnected()) {
            ftb_start.setVisibility(VISIBLE);
            ftb_frequency.setVisibility(GONE);
            BaseApplication.bool = false;
            /*if (null != mProduct.getModel()) {
                mTextProduct.setText("" + mProduct.getModel().getDisplayName());
            } else {
                mTextProduct.setText(R.string.product_information);
            }*/
        } else {
            ftb_start.setVisibility(VISIBLE);
            BaseApplication.bool = true; //需要遥控器配对的
            ftb_frequency.setVisibility(GONE);
        }
    }
}