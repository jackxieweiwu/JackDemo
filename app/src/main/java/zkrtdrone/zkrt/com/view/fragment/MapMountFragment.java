package zkrtdrone.zkrt.com.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import com.baidu.mapapi.map.BaiduMap;
import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentMapBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.handler;

/**
 * Created by root on 17-4-27.
 */

public class MapMountFragment extends AbsFragment<FragmentMapBinding> implements RadioGroup.OnCheckedChangeListener {
    private GestureMapFragment gestureMapFragment;
    @Bind(R.id.img_maptype) public ImageButton img_maptype;
    @Bind(R.id.img_location) public ImageButton img_location;
    @Bind(R.id.img_mapzomm_min) public ImageButton img_mapzomm_min;
    @Bind(R.id.img_mapzomm_max) public ImageButton img_mapzomm_max;
    @Bind(R.id.img_mappoy_clear) public ImageButton img_mappoy_clear;
    @Bind(R.id.map_type_radiogroup) RadioGroup map_type_radiogroup;
    @Bind(R.id.location_map_radiogroup) RadioGroup location_map_radiogroup;

    @Override
    protected void init(Bundle savedInstanceState) {
        map_type_radiogroup.setOnCheckedChangeListener(this);
        location_map_radiogroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDelayLoad() {

    }

    @OnClick(R.id.img_maptype)
    public void maptype(View v){
        if(map_type_radiogroup.getVisibility() == View.VISIBLE){
            map_type_radiogroup.startAnimation(startAnimViewGone());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    map_type_radiogroup.setVisibility(View.GONE);
                }
            },500);
        }else{
            map_type_radiogroup.startAnimation(startAnimViewShow());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    map_type_radiogroup.setVisibility(View.VISIBLE);
                }
            },500);
        }
    }

    //显示动画
    private TranslateAnimation startAnimViewShow(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    //隐藏动画
    private TranslateAnimation startAnimViewGone(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio_btn_common:  //普通
                gestureMapFragment.getMapFragment().goTomapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.radio_btn_satellite:  //卫星
                gestureMapFragment.getMapFragment().goTomapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.radio_btn_mylocation:  //自我
                gestureMapFragment.getMapFragment().goToMyLocation();
                break;
            case R.id.radio_btn_dronelocation:  //飞机
                gestureMapFragment.getMapFragment().goToDroneLocation();
                break;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    public void GestureMapFragment(GestureMapFragment gestureMapFragments) {
        this.gestureMapFragment = gestureMapFragments;
    }
}
