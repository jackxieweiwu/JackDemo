package zkrtdrone.zkrt.com.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.OnClick;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;

/**
 * Created by jack_xie on 17-5-8.
 */

public class DroneFragment extends AbsFragment{
    @Bind(R.id.img_drone_takeoff) ImageView img_drone_takeoff;
    @Bind(R.id.img_drone_land) ImageView img_drone_land;
    @Bind(R.id.img_drone_gohome) ImageView img_drone_gohome;
    @Bind(R.id.img_drone_hover) ImageView img_drone_hover;
    @Bind(R.id.img_drone_waypoint) ImageView img_drone_waypoint;

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_drone;
    }

    @OnClick(R.id.img_drone_takeoff)
    public void imageBtnTakeOffLang(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    protected void onDelayLoad() {

    }

}
