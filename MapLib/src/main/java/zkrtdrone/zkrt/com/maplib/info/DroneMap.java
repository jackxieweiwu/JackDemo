package zkrtdrone.zkrt.com.maplib.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.MapStatusUpdate;

import java.util.List;
import zkrtdrone.zkrt.com.maplib.R;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;
import zkrtdrone.zkrt.com.maplib.info.providers.DPMapProvider;
import zkrtdrone.zkrt.com.maplib.info.until.DroidPlannerPrefs;
import zkrtdrone.zkrt.com.maplib.info.until.Utils;

/**
 * Created by jack_xie on 17-4-24.
 */

public abstract class DroneMap extends Fragment {
    private final static String TAG = DroneMap.class.getSimpleName();
    protected Context context;
    protected DPmap mMapFragment;
    protected DroidPlannerPrefs mAppPrefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        final View view = inflater.inflate(R.layout.fragment_drone_map, viewGroup, false);
        mAppPrefs = new DroidPlannerPrefs(context);
        updateMapFragment();
        return view;
    }
   /* @Override
    protected void initView(Bundle savedInstanceState) {
        mAppPrefs = new DroidPlannerPrefs(mActivity);
        updateMapFragment();
    }*/

    @Override
    public void onAttach(Context contexts) {
        super.onAttach(contexts);
        context = contexts.getApplicationContext();
    }

    /*@Override
    protected int setLayoutId() {
        return R.layout.fragment_drone_map;
    }*/

    private void updateMapFragment() {
        final DPMapProvider mapProvider = Utils.getMapProvider(context);
        final FragmentManager fm = getChildFragmentManager();
        mMapFragment = (DPmap) fm.findFragmentById(R.id.map_fragment_container);
        if (mMapFragment == null || mMapFragment.getProvider() != mapProvider) {
            final Bundle mapArgs = new Bundle();
            mapArgs.putInt(DPmap.EXTRA_MAX_FLIGHT_PATH_SIZE, getMaxFlightPathSize());
            mMapFragment = mapProvider.getMapFragment();
            ((Fragment) mMapFragment).setArguments(mapArgs);
            fm.beginTransaction().replace(R.id.map_fragment_container, (Fragment) mMapFragment)
                    .commit();
        }
    }

    public void setMapPadding(int left, int top, int right, int bottom) {
        mMapFragment.setMapPadding(left, top, right, bottom);
    }

    public List<LatLong> projectPathIntoMap(List<LatLong> path) {
        return mMapFragment.projectPathIntoMap(path);
    }

    /**
     * Move the map to the user location.
     */
    public void goToMyLocation() {
        mMapFragment.goToMyLocation();
    }

    public void goTomapType(int i) {
        mMapFragment.mapType(i);
    }

    /**
     * Move the map to the drone location.
     */
    public void goToDroneLocation() {
        mMapFragment.goToDroneLocation();
    }

    public void setmapZoom(MapStatusUpdate mapStatusUpdate){
        mMapFragment.setZoomStates(mapStatusUpdate);
    }

    //清除地图表面marker
    public void clearMapMarker(){
        mMapFragment.clearMarkers();
        mMapFragment.clearFlightPath();
    }

    /**
     * Update the map rotation.
     *
     * @param bearing
     */
    public void updateMapBearing(float bearing) {
        mMapFragment.updateCameraBearing(bearing);
    }

    /**
     * Ignore marker clicks on the map and instead report the event as a
     * mapClick
     *
     * @param skip
     *            if it should skip further events
     */
    public void skipMarkerClickEvents(boolean skip) {
        mMapFragment.skipMarkerClickEvents(skip);
    }

    protected int getMaxFlightPathSize() {
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMapFragment();
    }


}
