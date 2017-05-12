package zkrtdrone.zkrt.com.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.arialyy.absadapter.viewpager.SimpleViewPagerAdapter;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import butterknife.Bind;
import butterknife.OnClick;
import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.FlightControllerState;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.databinding.FragmentMapBinding;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.DensityUtils;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.CommonRxTask;
import zkrtdrone.zkrt.com.jackmvvm.rxbean.IOTask;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.jackmvvm.util.rxutil.RxjavaUtil;
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment;
import zkrtdrone.zkrt.com.view.fragment.CamSetting.CameraSetting;
import zkrtdrone.zkrt.com.view.fragment.CamSetting.SetSetting;
import zkrtdrone.zkrt.com.view.fragment.CamSetting.VideoSetting;
import zkrtdrone.zkrt.com.widght.RotateImageView;

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
    @Bind(R.id.relayout_map) RelativeLayout relayout_map;
    //@Bind(R.id.relayout_camera) LinearLayout relayout_camera;
    @Bind(R.id.relayout_camera) RelativeLayout relayout_camera;
    @Bind(R.id.map_type_radiogroup) RadioGroup map_type_radiogroup;
    @Bind(R.id.location_map_radiogroup) RadioGroup location_map_radiogroup;
    private RotateImageView rotateImageView;
    private View viewDrone;
    private Bitmap droneBit;
    //Camera
    /*@Bind(R.id.txt_fpv_came_menu) TextView txt_fpv_came_menu;
    @Bind(R.id.switch1) Switch switch1;
    @Bind(R.id.img_fpv_camera_rec_take) ImageView img_fpv_camera_rec_take;
    @Bind(R.id.img_fpv_camera_sdcard2) ImageView img_fpv_camera_sdcard2;
    @Bind(R.id.img_fpv_camera_setting2) ImageView img_fpv_camera_setting2;*/
    //private int numCamera = -1;

    //camera setting
   /* @Bind(R.id.camera_setting_vp) ViewPager camera_setting_vp;
    @Bind(R.id.tool_bar) TabLayout mTb;
    @Bind(R.id.rela_camera_setting) RelativeLayout rela_camera_setting;*/

    @Override
    protected void init(Bundle savedInstanceState) {
        map_type_radiogroup.setOnCheckedChangeListener(this);
        location_map_radiogroup.setOnCheckedChangeListener(this);

        /*switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                img_fpv_camera_rec_take.setBackgroundResource(isChecked?R.mipmap.camera_controll_video_icon_start:R.mipmap.camera_controll_takephoto_icon1);
            }
        });*/
        viewDrone = View.inflate(mActivity, R.layout.view_drone, null);
        rotateImageView = (RotateImageView) viewDrone.findViewById(R.id.img_drone);
        if(ModuleVerificationUtil.isFlightControllerAvailable()){
            JackApplication.getAircraftInstance().getFlightController().setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull final FlightControllerState flightControllerState) {
                    JackApplication.droneloLat = flightControllerState.getAircraftLocation().getLatitude();
                    JackApplication.droneloLng = flightControllerState.getAircraftLocation().getLongitude();

                    RxjavaUtil.executeRxTask(new CommonRxTask<Object>() {
                        @Override
                        public void doInIOThread() {
                            rotateImageView.setAttitude(flightControllerState.getAttitude().yaw);
                            droneBit = loadBitmapFromView();
                        }

                        @Override
                        public void doInUIThread() {
                            if(droneBit !=null && gestureMapFragment !=null){
                                gestureMapFragment.getMapFragment().setDroneBitmap(droneBit);
                            }
                        }
                    });
                }
            });
        }
    }

    public Bitmap loadBitmapFromView() {
        if (viewDrone == null) {
            return null;
        }
        viewDrone.measure(View.MeasureSpec.makeMeasureSpec(DensityUtils.dip2px(mActivity, 40f),
                View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(
                DensityUtils.dip2px(mActivity, 45f), View.MeasureSpec.EXACTLY));
        viewDrone.layout(0, 0, viewDrone.getMeasuredWidth(), viewDrone.getMeasuredHeight());
        viewDrone.setDrawingCacheEnabled(true);
        viewDrone.buildDrawingCache();
        return viewDrone.getDrawingCache();
    }

    @Override
    protected void onDelayLoad() {

    }

    @OnClick(R.id.img_maptype)
    public void maptype(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        if(map_type_radiogroup.getVisibility() == View.VISIBLE){
            map_type_radiogroup.startAnimation(startAnimViewGone());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    map_type_radiogroup.setVisibility(View.GONE);
                }
            },200);
        }else{
            map_type_radiogroup.startAnimation(startAnimViewShow());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    map_type_radiogroup.setVisibility(View.VISIBLE);
                }
            },200);
        }
    }

    //camera is srtat
    /*@OnClick(R.id.txt_fpv_came_menu)
    public void setCameraMenu(View view){
        if(numCamera == 0){
            rela_camera_setting.setVisibility(rela_camera_setting.getVisibility() == View.VISIBLE?View.GONE:View.VISIBLE);
        }else{
            rela_camera_setting.setVisibility(View.GONE);
            rela_camera_setting.setVisibility(View.VISIBLE);
        }
        txt_fpv_came_menu.setBackgroundResource(rela_camera_setting.getVisibility() == View.VISIBLE?R.color.load_blue:android.R.color.transparent);
        img_fpv_camera_setting2.setBackgroundResource(android.R.color.transparent);
        img_fpv_camera_sdcard2.setBackgroundResource(android.R.color.transparent);
        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(JackApplication.fragmentManager);
        adapter.addFrag(new CameraSetting(), "相机");
        adapter.addFrag(new VideoSetting(), "视频");
        adapter.addFrag(new SetSetting(), "设置");
        camera_setting_vp.setAdapter(adapter);
        camera_setting_vp.setOffscreenPageLimit(3);
        mTb.setupWithViewPager(camera_setting_vp);
        numCamera = 0;
    }

    @OnClick(R.id.img_fpv_camera_sdcard)
    public void setCameraPhtotFile(View view){
        if(numCamera == 1){

        }else{

        }
        rela_camera_setting.setVisibility(View.GONE);
        img_fpv_camera_sdcard2.setBackgroundResource(rela_camera_setting.getVisibility() == View.VISIBLE?R.color.load_blue:android.R.color.transparent);
        img_fpv_camera_setting2.setBackgroundResource(android.R.color.transparent);
        txt_fpv_came_menu.setBackgroundResource(android.R.color.transparent);
        numCamera = 1;
    }

    @OnClick(R.id.img_fpv_camera_setting)
    public void setCameraSetting(View v){
        if(numCamera == 2){
            rela_camera_setting.setVisibility(rela_camera_setting.getVisibility() == View.VISIBLE?View.GONE:View.VISIBLE);
        }else{
            rela_camera_setting.setVisibility(View.GONE);
            rela_camera_setting.setVisibility(View.VISIBLE);
        }
        img_fpv_camera_setting2.setBackgroundResource(rela_camera_setting.getVisibility() == View.VISIBLE?R.color.load_blue:android.R.color.transparent);
        img_fpv_camera_sdcard2.setBackgroundResource(android.R.color.transparent);
        txt_fpv_came_menu.setBackgroundResource(android.R.color.transparent);
        SimpleViewPagerAdapter adapter = new SimpleViewPagerAdapter(JackApplication.fragmentManager);
        adapter.addFrag(new CameraSetting(), "A");
        adapter.addFrag(new VideoSetting(), "S");
        adapter.addFrag(new SetSetting(), "M");
        camera_setting_vp.setAdapter(adapter);
        camera_setting_vp.setOffscreenPageLimit(4);
        mTb.setupWithViewPager(camera_setting_vp);
        numCamera = 2;
    }*/
    //start is end

    @OnClick(R.id.img_location)
    public void mapLocation(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        if(location_map_radiogroup.getVisibility() == View.VISIBLE){
            location_map_radiogroup.startAnimation(startAnimViewGone());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    location_map_radiogroup.setVisibility(View.GONE);
                }
            },200);
        }else{
            location_map_radiogroup.startAnimation(startAnimViewShow());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    location_map_radiogroup.setVisibility(View.VISIBLE);
                }
            },200);
        }
    }

    public void setCheckGoneViesbile(boolean bool){
        if(bool){
            relayout_map.setVisibility(View.VISIBLE);
            relayout_camera.setVisibility(View.GONE);
        }else{
            relayout_map.setVisibility(View.GONE);
            relayout_camera.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.img_mapzomm_min)
    public void mapZoomMin(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        gestureMapFragment.getMapFragment().setmapZoom(MapStatusUpdateFactory.zoomOut());
    }

    @OnClick(R.id.img_mapzomm_max)
    public void mapZoomMax(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        gestureMapFragment.getMapFragment().setmapZoom(MapStatusUpdateFactory.zoomIn());
    }

    @OnClick(R.id.img_mappoy_clear)
    public void mapMarkerClear(View v){
        if (GeneralUtils.isFastDoubleClick()) return;
        gestureMapFragment.getMapFragment().clearMapMarker();
    }

    //显示动画
    private TranslateAnimation startAnimViewShow(){
        int[] location = new int[2];
        map_type_radiogroup.getLocationOnScreen(location);
        //TranslateAnimation mHiddenAction = new TranslateAnimation(location[0], 0.0f,location[1], 0.0f);
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    //隐藏动画
    private TranslateAnimation startAnimViewGone(){
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
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
