package zkrtdrone.zkrt.com.jackmvvm.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import dji.sdk.flightcontroller.FlightController;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;

/**
 * Created by jack_xie on 17-4-20.
 */

public class BaseLayout extends RelativeLayout {
    public BaseLayout(Context context) {
        super(context);
    }

    public BaseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
