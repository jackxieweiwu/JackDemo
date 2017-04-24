package zkrtdrone.zkrt.com.maplib.info;

import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.List;
import zkrtdrone.zkrt.com.maplib.R;
import zkrtdrone.zkrt.com.jackmvvm.base.BaseMvpFragment;
import zkrtdrone.zkrt.com.maplib.databinding.FragmentGestureMapBinding;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;
import zkrtdrone.zkrt.com.maplib.info.units.MathUtils;

import static zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication.fragmentManager;

/**
 * Created by jack_xie on 17-4-24.  fragment_gesture_map
 */

public class GestureMapFragment extends BaseMvpFragment<FragmentGestureMapBinding> implements GestureOverlayView.OnGestureListener {
    EditorMapFragment mapFragment;
    GestureOverlayView overlay;

    private static final int TOLERANCE = 15;
    private static final int STROKE_WIDTH = 3;
    private double toleranceInPixels;
    public interface OnPathFinishedListener {
        void onPathFinished(List<LatLong> path);
    }
    private OnPathFinishedListener listener;

    @Override
    protected void initView(Bundle savedInstanceState) {
        /*mapFragment = ((EditorMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.editor_map_fragment));*/
        mapFragment = new EditorMapFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.editor_map_fragment, mapFragment);
        fragmentTransaction.commit();

        overlay = (GestureOverlayView) mRootView.findViewById(R.id.overlay1);
        overlay.addOnGestureListener(this);
        overlay.setEnabled(false);
        toleranceInPixels = scaleDpToPixels(TOLERANCE);
    }

    @Override
    public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
        overlay.setEnabled(false);
        List<LatLong> path = decodeGesture();
        if (path.size() > 1) {
            path = MathUtils.simplify(path, toleranceInPixels);
        }
        listener.onPathFinished(path);
    }

    private List<LatLong> decodeGesture() {
        List<LatLong> path = new ArrayList<LatLong>();
        extractPathFromGesture(path);
        return path;
    }

    private int scaleDpToPixels(double value) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) Math.round(value * scale);
    }

    public void enableGestureDetection() {
        overlay.setEnabled(true);
    }

    public void disableGestureDetection() {
        overlay.setEnabled(false);
    }

    public void setOnPathFinishedListener(OnPathFinishedListener listener) {
        this.listener = listener;
    }


    private void extractPathFromGesture(List<LatLong> path) {
        float[] points = overlay.getGesture().getStrokes().get(0).points;
        for (int i = 0; i < points.length; i += 2) {
            path.add(new LatLong((int) points[i], (int) points[i + 1]));
        }
    }

    public EditorMapFragment getMapFragment(){
        return mapFragment;
    }

    @Override
    public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_gesture_map;
    }

    @Override
    public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
    }

    @Override
    public void onGesture(GestureOverlayView overlay, MotionEvent event) {
    }
}
