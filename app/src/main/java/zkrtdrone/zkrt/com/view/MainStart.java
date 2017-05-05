package zkrtdrone.zkrt.com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.List;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.maplib.info.EditorMapFragment;
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment;
import zkrtdrone.zkrt.com.maplib.info.OnEditorInteraction;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;
import zkrtdrone.zkrt.com.view.fragment.HandStateFragment;
import zkrtdrone.zkrt.com.view.fragment.MapMountFragment;
import zkrtdrone.zkrt.com.view.fragment.MountFragment;
import zkrtdrone.zkrt.com.view.fragment.TelemetryFragment;
import zkrtdrone.zkrt.com.widght.BaseFpvView;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.fragmentManager;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.getScreenHeigh;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.getScreenWidth;
import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.handler;

/**
 * Created by jack_xie on 17-4-20.
 */

public class MainStart extends RelativeLayout implements GestureMapFragment.OnPathFinishedListener,
        OnEditorInteraction,View.OnClickListener {
    private ImageView img_common_exchange,img_common_location,
    img_common_clear_pryline,img_common_exchange2,img_common_conceal3,img_common_conceal;
    private FrameLayout start_common_map_video;
    //private SlidingDrawer slidingDrawer;
    private TelemetryFragment telemetryFragment;
    private MountFragment mountFragment;
    private GestureMapFragment gestureMapFragment;
    private HandStateFragment handStateFragment;
    private MapMountFragment mapMountFragment;
    private ImageView mount_open;
    private int numberMapView = 0; //0为camera / map is min     1为 Map/camera
    //切换窗口
    private BaseFpvView fpvCamera;
    private FrameLayout fpvCameraView;
    private FrameLayout mapMain;
    private RelativeLayout relayout_id_moudle;

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
        relayout_id_moudle = (RelativeLayout) view.findViewById(R.id.relayout_id_moudle);
        img_common_exchange = (ImageView) view.findViewById(R.id.img_common_exchange);
        img_common_location = (ImageView) view.findViewById(R.id.img_common_location);
        img_common_conceal = (ImageView) view.findViewById(R.id.img_common_conceal);
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

        //切换窗口
        mapMain = (FrameLayout) view.findViewById(R.id.mapMain);
        fpvCamera = (BaseFpvView) view.findViewById(R.id.fpvCamera);
        fpvCameraView = (FrameLayout) view.findViewById(R.id.fpvCameraView);

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
        if (telemetryFragment == null) {
            telemetryFragment = new TelemetryFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.telemetryFragment, telemetryFragment)
                    .commit();
        }
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
        mountFragment.setImgOpen(mount_open);
        mapMountFragment.GestureMapFragment(gestureMapFragment);
        telemetryFragment.setGestureMapFragment(gestureMapFragment);

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
        switch (v.getId()){
            case R.id.img_common_exchange2:
            case R.id.img_common_exchange:  //切换窗口
                if(numberMapView == 0) {
                    numberMapView = 1;
                    img_common_exchange.setVisibility(GONE);
                    RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(mapMain.getWidth(), mapMain.getHeight());
                    lay.setMargins(getScreenWidth()-mapMain.getWidth(),getScreenHeigh()-mapMain.getHeight(),0,0);
                    fpvCameraView.setLayoutParams(lay);
                    mapMain.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    mapMain.bringToFront();
                    fpvCameraView.bringToFront();
                }else {
                    img_common_exchange.setVisibility(VISIBLE);
                    numberMapView = 0;
                    RelativeLayout.LayoutParams lay = new RelativeLayout.LayoutParams(fpvCameraView.getWidth(), fpvCameraView.getHeight());
                    lay.setMargins(getScreenWidth()-fpvCameraView.getWidth(),getScreenHeigh()-fpvCameraView.getHeight(),0,0);
                    mapMain.setLayoutParams(lay);
                    fpvCameraView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    fpvCameraView.bringToFront();
                    mapMain.bringToFront();
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
}
