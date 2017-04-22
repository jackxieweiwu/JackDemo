package zkrtdrone.zkrt.com.view;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import dji.sdk.flightcontroller.FlightController;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseLayout;
import zkrtdrone.zkrt.com.view.fragment.HandStateFragment;

/**
 * Created by jack_xie on 17-4-20.
 */

public class MainStart extends RelativeLayout {

    private HandStateFragment handStateFragment;
    public MainStart(Context context) {
        super(context);
        initView(context);
    }
    public MainStart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MainStart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        handStateFragment = new HandStateFragment();
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_start, this);
        FragmentTransaction fragmentTransaction = JackApplication.fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_hand, handStateFragment);
        fragmentTransaction.commit();
    }
}
