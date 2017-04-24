package zkrtdrone.zkrt.com.view;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.List;

import dji.sdk.flightcontroller.FlightController;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseLayout;
import zkrtdrone.zkrt.com.maplib.info.EditorMapFragment;
import zkrtdrone.zkrt.com.maplib.info.GestureMapFragment;
import zkrtdrone.zkrt.com.maplib.info.OnEditorInteraction;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;
import zkrtdrone.zkrt.com.view.fragment.HandStateFragment;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.fragmentManager;

/**
 * Created by jack_xie on 17-4-20.
 */

public class MainStart extends RelativeLayout implements GestureMapFragment.OnPathFinishedListener,
        OnEditorInteraction {

    private GestureMapFragment gestureMapFragment;
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
        gestureMapFragment = new GestureMapFragment();
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_start, this);

        fragmentManager.beginTransaction().add(R.id.fragment_hand, handStateFragment).commit();
        fragmentManager.beginTransaction().add(R.id.gestureMapFragment,gestureMapFragment).commit();

        /*gestureMapFragment = ((GestureMapFragment) fragmentManager
                .findFragmentById(R.id.gestureMapFragment));
        gestureMapFragment.setOnPathFinishedListener(this);*/
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
