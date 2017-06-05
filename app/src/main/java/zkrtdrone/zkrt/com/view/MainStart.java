package zkrtdrone.zkrt.com.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import java.util.List;

import dji.common.error.DJIError;
import dji.common.util.CommonCallbacks;
import dji.sdk.base.BaseProduct;
import dji.midware.ui.widget.FPVWidget;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.bean.RemoteBean;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.ScreenUtil;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.jackmvvm.util.GeneralUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.ModuleVerificationUtil;
import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.ByteUtils;
import zkrtdrone.zkrt.com.jackmvvm.util.byteUtil.Utils;
import zkrtdrone.zkrt.com.maplib.info.EditorMapFragment;
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment;
import zkrtdrone.zkrt.com.maplib.info.OnEditorInteraction;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;
import zkrtdrone.zkrt.com.untils.ClientThread;
import zkrtdrone.zkrt.com.view.fragment.HandStateFragment;
import zkrtdrone.zkrt.com.view.fragment.MapMountFragment;
import zkrtdrone.zkrt.com.view.fragment.MountFragment;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.fragmentManager;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.handler;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.mActivity;

/**
 * Created by jack_xie on 17-4-20.
 */

public class MainStart extends RelativeLayout implements GestureMapFragment.OnPathFinishedListener,
        OnEditorInteraction,View.OnClickListener {
    private ImageView img_common_exchange,img_common_location,
    img_common_clear_pryline,img_common_exchange2,img_common_conceal3,img_common_conceal;
    private FrameLayout start_common_map_video;
    //private SlidingDrawer slidingDrawer;
    //private TelemetryFragment telemetryFragment;
    private MountFragment mountFragment;
    private GestureMapFragment gestureMapFragment;
    private HandStateFragment handStateFragment;
    private MapMountFragment mapMountFragment;
    //private DroneFragment droneFragment;
    private ImageView mount_open,img_clean_click;
    private int numberMapView = 0; //0为camera / map is min     1为 Map/camera
    //切换窗口
    //private BaseFpvView fpvCamera;
    private FPVWidget fpvCamera,fpvCamera2;
    private FrameLayout fpvCameraView,framefpvcamera;
    private FrameLayout mapMain;
    private RelativeLayout relayout_id_moudle,PreflightCheckView;
    private TextView txt_tishi;

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
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_start, this);
        //slidingDrawer = (SlidingDrawer) view.findViewById(R.id.slidingDrawerRight);
        start_common_map_video = (FrameLayout) view.findViewById(R.id.start_common_map_video);
        txt_tishi = (TextView) view.findViewById(R.id.txt_tishi);
        relayout_id_moudle = (RelativeLayout) view.findViewById(R.id.relayout_id_moudle);
        PreflightCheckView = (RelativeLayout) view.findViewById(R.id.PreflightCheckView);
        img_common_exchange = (ImageView) view.findViewById(R.id.img_common_exchange);
        img_common_location = (ImageView) view.findViewById(R.id.img_common_location);
        img_common_conceal = (ImageView) view.findViewById(R.id.img_common_conceal);
        img_clean_click = (ImageView) view.findViewById(R.id.img_clean_click);
        //img_common_conceal2 = (ImageView) view.findViewById(R.id.img_common_conceal2);
        img_common_exchange2 = (ImageView) view.findViewById(R.id.img_common_exchange2);
        img_common_conceal3 = (ImageView) view.findViewById(R.id.img_common_conceal3);
        img_common_clear_pryline = (ImageView) view.findViewById(R.id.img_common_clear_pryline);
        mount_open = (ImageView) view.findViewById(R.id.mount_open);
        img_common_exchange.setOnClickListener(this);
        img_common_location.setOnClickListener(this);
        img_common_conceal.setOnClickListener(this);
        //img_common_conceal2.setOnClickListener(this);
        img_common_exchange2.setOnClickListener(this);
        img_common_conceal3.setOnClickListener(this);
        img_common_clear_pryline.setOnClickListener(this);
        mount_open.setOnClickListener(this);
        img_clean_click.setOnClickListener(this);

        //切换窗口
        mapMain = (FrameLayout) view.findViewById(R.id.mapMain);
        fpvCamera = (FPVWidget) view.findViewById(R.id.fpvCamera);
        //fpvCamera2 = (FPVWidget) view.findViewById(R.id.fpvCamera2);
        fpvCameraView = (FrameLayout) view.findViewById(R.id.fpvCameraView);
        //framefpvcamera = (FrameLayout) view.findViewById(R.id.framefpvcamera);

        if (handStateFragment == null) {
            handStateFragment = new HandStateFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_hand, handStateFragment,"HandStateFragment")
                    .commit();
        }
        if (gestureMapFragment == null) {
            gestureMapFragment = new GestureMapFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.gestureMapFragment, gestureMapFragment)
                    .commit();
        }
        /*if (telemetryFragment == null) {
            telemetryFragment = new TelemetryFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.telemetryFragment, telemetryFragment)
                    .commit();
        }*/
        if (mountFragment == null) {
            mountFragment = new MountFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.module_value, mountFragment)
                    .commit();
        }
        if (mapMountFragment == null) {
            mapMountFragment = new MapMountFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_map, mapMountFragment)
                    .commit();
        }
        /*if (droneFragment == null) {
            droneFragment = new DroneFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.drone_value, droneFragment)
                    .commit();
        }*/
        mountFragment.setImgOpen(mount_open);
        mapMountFragment.GestureMapFragment(gestureMapFragment);
        handStateFragment.GestureMapFragment(gestureMapFragment);
        //telemetryFragment.setGestureMapFragment(gestureMapFragment);

        /*fragmentManager.findFragmentById(R.id.fragment_hand).getView().
                findViewById(R.id.hand_setting).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initDrawerLayout();
            }
        });*/
        /*if(slidingDrawer != null) {
            slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
                @Override
                public void onDrawerClosed() {
                    final int slidingDrawerWidth = slidingDrawer.getContent().getWidth();
                    final boolean isSlidingDrawerOpened = slidingDrawer.isOpened();
                    //updateLocationButtonsMargin(isSlidingDrawerOpened, slidingDrawerWidth);
                }
            });

            slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
                @Override
                public void onDrawerOpened() {
                    final int slidingDrawerWidth = slidingDrawer.getContent().getWidth();
                    final boolean isSlidingDrawerOpened = slidingDrawer.isOpened();
                    //updateLocationButtonsMargin(isSlidingDrawerOpened, slidingDrawerWidth);
                }
            });
        }*/

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0x123){
                    byte[] bRec = msg.getData().getByteArray("buff");
                    RemoteBean remoteBean = ByteUtils.toObject(bRec, RemoteBean.class);
                    goneExitText(remoteBean);
                }
            }
        };

        ClientThread clientThread = new ClientThread(handler);
        new Thread(clientThread).start();
        if(clientThread.isConnect()){

        }else{
            T.show(mActivity,"遥感控制版连接失败",1000);
        }
    }

    //解析遥感以及地面站的机械按钮的实际操作
    private void goneExitText(RemoteBean remoteBean) {
        //取遥感上下
        int upOrDownNum = remoteBean.getThree();  //up -56  center 105-107   dow 0
        //取遥感左右
        int leftOrRightNum = remoteBean.getTwo(); //left -56  center  105-107    right 0
        //取遥感变焦值
        int zoomNum = remoteBean.getFour();  //right -56  center 102-107  left  0
        //取按钮的值 remoteBean.getSix()

        String buttonnum = Utils.toBinary(remoteBean.getSix());
        int nighteBtn = Integer.parseInt(buttonnum.substring(7, 8));
        int sevenBtn = Integer.parseInt(buttonnum.substring(6, 7));
        int sixBtn = Integer.parseInt(buttonnum.substring(5, 6));
        int fiveBtn = Integer.parseInt(buttonnum.substring(4, 5));
        int fourBtn = Integer.parseInt(buttonnum.substring(3, 4));
        int threeBtn = Integer.parseInt(buttonnum.substring(2, 3));
        int twoBtn = Integer.parseInt(buttonnum.substring(1, 2));
        int oneBtn = Integer.parseInt(buttonnum.substring(0, 1));

        txt_tishi.setText(remoteBean.getSix()+"取遥感上下:"+upOrDownNum
                +"取遥感左右:"+leftOrRightNum
                +"取遥感变焦值:"+zoomNum
                +"起飞:"+oneBtn
                +"降落:"+twoBtn
                +"返航:"+threeBtn
                +"悬停:"+fourBtn
                +"拍照:"+fiveBtn
                +"录像:"+sixBtn
                +"照射:"+sevenBtn
                +"抛投:"+nighteBtn);

        //执行下面的动作
        //起飞
        if(remoteBean.getSix() == -128){
            if(ModuleVerificationUtil.isFlightControllerAvailable()){
                JackApplication.getAircraftInstance().getFlightController().startTakeoff(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        T.show(mActivity,djiError == null?"起飞":djiError.getDescription(),20);
                    }
                });
            }else{
                T.show(mActivity,"断开连接",20);
            }
        }

        //降落
        if(remoteBean.getSix() == 64){
            if(ModuleVerificationUtil.isFlightControllerAvailable()){
                JackApplication.getAircraftInstance().getFlightController().startLanding(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        T.show(mActivity,djiError == null?"降落":djiError.getDescription(),20);
                    }
                });
            }else {
                T.show(mActivity,"断开连接",20);
            }
        }

        //返航
        if(remoteBean.getSix() == 32){
            if(ModuleVerificationUtil.isFlightControllerAvailable()){
                JackApplication.getAircraftInstance().getFlightController().startGoHome(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        T.show(mActivity,djiError == null?"返航":djiError.getDescription(),20);
                    }
                });
            }else{
                T.show(mActivity,"断开连接",20);
            }
        }

        //悬停
        if(remoteBean.getSix() == 16){
            if(ModuleVerificationUtil.isFlightControllerAvailable()){
                JackApplication.getAircraftInstance().getFlightController().cancelLanding(new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onResult(DJIError djiError) {
                        T.show(mActivity,djiError == null?"悬停":djiError.getDescription(),20);
                    }
                });
            }else {
                T.show(mActivity,"断开连接",20);
            }
        }

        //拍照
        if(remoteBean.getSix() == 8){
            T.show(mActivity,"拍照",10);
        }

        //录像
        if(remoteBean.getSix() == 3){
            T.show(mActivity,"抛投",10);
        }

        //照射
        if(remoteBean.getSix() == 2){
            T.show(mActivity,"照射",10);
        }

        //抛投
        if(remoteBean.getSix() == 1){
            T.show(mActivity,"抛投",10);
        }
    }

    /*private void updateLocationButtonsMargin(boolean isOpened, int drawerWidth) {
        final ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) start_common_map_video
                .getLayoutParams();
        final int rightMargin = isOpened ? marginLp.leftMargin + drawerWidth : marginLp.leftMargin;
        marginLp.setMargins(marginLp.leftMargin, marginLp.topMargin,rightMargin,marginLp.bottomMargin);
        start_common_map_video.requestLayout();
    }*/

    @Override
    public void onClick(View v) {
        if (GeneralUtils.isFastDoubleClick()) return;
        switch (v.getId()){
            case R.id.img_common_exchange2:
            case R.id.img_common_exchange:  //切换窗口
                if(numberMapView == 0) {
                    numberMapView = 1;
                    img_common_exchange.setVisibility(GONE);
                    RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(mapMain.getWidth(), mapMain.getHeight());
                    //lay.setMargins(getScreenWidth()-mapMain.getWidth(),getScreenHeigh()-mapMain.getHeight(),0,0);
                    lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    lay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    fpvCameraView.setLayoutParams(lay);
                    mapMain.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    mapMain.bringToFront();
                    fpvCameraView.bringToFront();
                    /*fpvCameraView.setVisibility(GONE);
                    framefpvcamera.setVisibility(VISIBLE);*/
                    //framefpvcamera.bringToFront();
                }else {
                    img_common_exchange.setVisibility(VISIBLE);
                    numberMapView = 0;
                    RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(fpvCameraView.getWidth(), fpvCameraView.getHeight());
                    //lay.setMargins(getScreenWidth()-fpvCameraView.getWidth(),getScreenHeigh()-fpvCameraView.getHeight(),0,0);
                    lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                    lay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    mapMain.setLayoutParams(lay);
                    fpvCameraView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    /*framefpvcamera.setVisibility(GONE);
                    fpvCameraView.setVisibility(VISIBLE);*/
                    fpvCameraView.bringToFront();

                    mapMain.bringToFront();
                    Log.d("MainStart","切换窗口");
                }
                img_common_exchange2.setVisibility(img_common_exchange.getVisibility() == GONE? VISIBLE:GONE);
                img_common_conceal3.setVisibility(img_common_exchange.getVisibility() == GONE? VISIBLE:GONE);
                img_common_conceal.setVisibility(img_common_exchange.getVisibility() == GONE? GONE:VISIBLE);
                img_common_location.setVisibility(img_common_exchange.getVisibility() == GONE? GONE:VISIBLE);
                img_common_clear_pryline.setVisibility(img_common_exchange.getVisibility() == GONE? GONE:VISIBLE);
                mapMountFragment.setCheckGoneViesbile(img_common_exchange.getVisibility() == GONE? true:false);
                relayout_id_moudle.bringToFront();
                gestureMapFragment.getMapFragment().goToMyLocation();
                invalidate();
                break;
            case R.id.img_common_location:  //定位用户位置
                gestureMapFragment.getMapFragment().goToMyLocation();
                break;
            case R.id.img_common_conceal3://隐藏
                if(fpvCamera.getVisibility() == VISIBLE) {
                    fpvCamera.startAnimation(startAnimViewGone());
                    fpvCamera.setVisibility(GONE);
                    img_common_exchange2.setVisibility(GONE);
                }else{
                    fpvCamera.startAnimation(startAnimViewShow());
                    fpvCamera.setVisibility(VISIBLE);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            img_common_exchange2.setVisibility(VISIBLE);
                        }
                    }, 500);
                }
                break;

            case R.id.img_common_conceal:  //隐藏
                if(start_common_map_video.getVisibility()==VISIBLE) {
                    start_common_map_video.startAnimation(startAnimViewGone());
                    start_common_map_video.setVisibility(GONE);

                }else{
                    start_common_map_video.startAnimation(startAnimViewShow());
                    start_common_map_video.setVisibility(VISIBLE);
                }
                break;
            /*case R.id.img_common_conceal2:
                break;*/
            case R.id.img_common_clear_pryline:  //清除航线
                break;
            case R.id.mount_open:  //显示与隐藏毒气值
                if(mountFragment.frame_mount.getVisibility() == GONE) {
                    mountFragment.frame_mount.setVisibility(View.VISIBLE);
                    mount_open.setVisibility(View.GONE);
                    mountFragment.mount_clear.startAnimation(mountFragment.animation1);
                    mountFragment.frame_mount.startAnimation(mountFragment.startAnimViewShow());
                }else{
                    mountFragment.frame_mount.setVisibility(View.GONE);
                    mount_open.setVisibility(View.VISIBLE);
                    mountFragment.mount_clear.startAnimation(mountFragment.animation);
                    mountFragment.frame_mount.startAnimation(mountFragment.startAnimViewGone());
                }
                break;
            case R.id.img_clean_click:
                PreflightCheckView.setVisibility(GONE);
                break;
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
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    @Override
    public void onPathFinished(List<LatLong> path) {
        final EditorMapFragment planningMapFragment = gestureMapFragment.getMapFragment();
        List<LatLong> points = planningMapFragment.projectPathIntoMap(path);
        /*switch (getTool()) {
            case DRAW:
                if(missionProxy != null) {
                    if (mIsSplineEnabled) {
                        missionProxy.addSplineWaypoints(points);
                    } else {
                        missionProxy.addWaypoints(points);
                    }
                }
                break;

            case POLY:
                if (missionProxy != null && path.size() > 2) {
                    missionProxy.addSurveyPolygon(points);
                } else {
                    editorToolsFragment.setTool(EditorTools.POLY);
                    return;
                }
                break;

            default:
                break;
        }*/
    }

    @Override
    public void onMapClick(LatLong coord) {
        //enableMultiEdit(false);
        /*if(missionProxy == null) return;

        missionProxy.selection.clearSelection();
        switch (getTool()) {
            case MARKER:
                if (mIsSplineEnabled) {
                    missionProxy.addSplineWaypoint(point);
                } else {
                    missionProxy.addWaypoint(point);
                }
                break;

            default:
                break;
        }*/
    }

    /*public EditorTools getTool() {
        return editorToolsFragment.getTool();
    }*/
    @Override
    public void onListVisibilityChanged() {

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        refreshSDKRelativeUI();
    }

    private void refreshSDKRelativeUI() {
        if(ModuleVerificationUtil.isRemoteControllerAvailable()){
            final BaseProduct mProduct = JackApplication.getProductInstance();
            final boolean bool = JackApplication.getAircraftInstance().getRemoteController().isConnected();
            JackApplication.mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(bool){
                        if (null != mProduct && mProduct.isConnected()){}else{
                            handStateFragment.isRemoteDrone(false,400);
                            ScreenUtil.getInstance().setGreyScale(getRootView(),false);
                        }
                    }else{
                        handStateFragment.isRemoteDrone(bool,401);
                        ScreenUtil.getInstance().setGreyScale(getRootView(),bool);
                    }
                }
            });
        }
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
}
