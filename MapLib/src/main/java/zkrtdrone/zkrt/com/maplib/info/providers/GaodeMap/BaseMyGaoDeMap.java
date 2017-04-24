/*
package zkrtdrone.zkrt.com.maplib.info.providers.GaodeMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polygon;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import zkrtdrone.zkrt.com.jackmvvm.base.BaseApplication;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.maplib.R;
import zkrtdrone.zkrt.com.maplib.info.DPmap;
import zkrtdrone.zkrt.com.maplib.info.MarkerInfo;
import zkrtdrone.zkrt.com.maplib.info.location.Location;
import zkrtdrone.zkrt.com.maplib.info.providers.DPMapProvider;
import zkrtdrone.zkrt.com.maplib.info.units.DroneHelper;

*/
/**
 * Created by jack_xie on 17-4-23.
 *//*


public class BaseMyGaoDeMap extends SupportMapFragment implements DPmap {
    private static final String TAG = BaseMyGaoDeMap.class.getSimpleName();

    public static final String PREF_MAP_TYPE = "pref_map_type";
    public static final String MAP_TYPE_SATELLITE = "Satellite";
    public static final String MAP_TYPE_HYBRID = "Hybrid";
    public static final String MAP_TYPE_NORMAL = "Normal";

    private static final float GO_TO_MY_LOCATION_ZOOM = 19f;
    private final HashBiMap<MarkerInfo, Marker> mBiMarkersMap = new HashBiMap<MarkerInfo, Marker>();
    private MapView mMapView;

    private Polyline flightPath;
    private Polyline missionPath;
    private Polyline mDroneLeashPath;

    List<LatLng> mdFlightPathList = new ArrayList<LatLng>();
    */
/*
     * DP Map listeners
     *//*

    private DPmap.OnMapClickListener mMapClickListener;
    private DPmap.OnMapLongClickListener mMapLongClickListener;
    private DPmap.OnMarkerClickListener mMarkerClickListener;
    private DPmap.OnMarkerDragListener mMarkerDragListener;
    private Location.LocationReceiver mLocationListener;

    protected boolean useMarkerClickAsMapClick = false;

    private List<Polygon> polygonsPaths = new ArrayList<Polygon>();

    protected BaseApplication bsApp;
    private Polygon footprintPoly;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bsApp = (BaseApplication) activity.getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle bundle) {

        final FragmentActivity activity = getActivity();
        final Context context = activity.getApplicationContext();


        final View view = super.onCreateView(inflater, viewGroup, bundle);

        final AMap.OnMapClickListener onMapClickListener = (new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMapClickListener.onMapClick(DroneHelper.BaiduLatLngToCoord(latLng));
            }
        });

        getMap().setOnMapClickListener(onMapClickListener);

        getMap().setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            public void onMapLongClick(LatLng point) {
                if (mMapLongClickListener != null) {
                    mMapLongClickListener.onMapLongClick((DroneHelper.BaiduLatLngToCoord(point)));
                }
            }
        });

        getMap().setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker){
                if (useMarkerClickAsMapClick) {
                    onMapClickListener.onMapClick(marker.getPosition());
                    return true;
                }
                if (mMarkerClickListener != null) {
                    return mMarkerClickListener.onMarkerClick(mBiMarkersMap.getKey(marker));
                }
                return false;
            }
        });

        getMap().setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            public void onMarkerDrag(Marker marker){
                if (mMarkerDragListener != null) {
                    final MarkerInfo markerInfo = mBiMarkersMap.getKey(marker);
                    markerInfo.setPosition((DroneHelper.BaiduLatLngToCoord(marker.getPosition())));
                    mMarkerDragListener.onMarkerDrag(markerInfo);
                }
            }
            public void onMarkerDragStart(Marker marker){
                if (mMarkerDragListener != null) {
                    final MarkerInfo markerInfo = mBiMarkersMap.getKey(marker);
                    markerInfo.setPosition((DroneHelper.BaiduLatLngToCoord(marker.getPosition())));
                    mMarkerDragListener.onMarkerDragStart(markerInfo);
                }
            }

            public void  onMarkerDragEnd(Marker marker){
                if (mMarkerDragListener != null) {
                    final MarkerInfo markerInfo = mBiMarkersMap.getKey(marker);
                    markerInfo.setPosition((DroneHelper.BaiduLatLngToCoord(marker.getPosition())));
                    mMarkerDragListener.onMarkerDragEnd(markerInfo);
                }
            }
        });

        updateCamera(new LatLng(30.3250427246094,120.063011169434), GO_TO_MY_LOCATION_ZOOM);
        mMapView = getMapView();
        getMap().setMapType(AMap.MAP_TYPE_NORMAL);
        getMap().setMyLocationEnabled(true);
        return view;//inflater.inflate(R.layout.fragment_baidu_map, viewGroup, false);
    }

    @Override
    public void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        if(mMapView != null)
            mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if(mMapView != null)
            mMapView.onResume();
        super.onResume();
    }

    @Override
    public void clearMarkers() {

    }

    @Override
    public void clearFlightPath() {
        if (flightPath != null) {
            mdFlightPathList.clear();
            flightPath.remove();
            flightPath = null;
        }
    }

    @Override
    public LatLng getMapCenter() {
        return DroneHelper.BaiduLatLngToCoord(getMap().getMapStatus().target);
    }

    @Override
    public float getMapZoomLevel() {
        return getMap() .getMapStatus().zoom;
    }

    @Override
    public Set<MarkerInfo> getMarkerInfoList() {
        return new HashSet<MarkerInfo>(mBiMarkersMap.keySet());
    }

    @Override
    public float getMaxZoomLevel() {
        return getMap().getMaxZoomLevel();
    }

    @Override
    public float getMinZoomLevel() {
        return getMap().getMinZoomLevel();
    }

    @Override
    public DPMapProvider getProvider() {
        return DPMapProvider.高德地图;
    }

    @Override
    public void goToDroneLocation() {
        Drone dpApi = getDroneApi();
        if (!dpApi.isConnected())
            return;

        Gps gps = dpApi.getGps();
        if (!gps.isValid()) {
            T.showShort(BaseApplication.mActivity,R.string.drone_no_location);
            return;
        }

        final float currentZoomLevel = getBaiduMap().getMapStatus().zoom;
        final LatLng droneLocation = gps.getPosition();
        updateCamera(droneLocation, (int) currentZoomLevel);
    }



    @Override
    public void goToMyLocation() {

    }

    @Override
    public List<LatLng> projectPathIntoMap(List<LatLng> pathPoints) {
        List<LatLng> coords = new ArrayList<LatLng>();
        Projection projection = getMap().getProjection();

        for (LatLng point : pathPoints) {
            LatLng coord = projection.fromScreenLocation(new Point((int) point
                    .latitude, (int) point.longitude));
            coords.add(DroneHelper.BaiduLatLngToCoord(coord));
        }
        return coords;
    }

    @Override
    public void removeMarkers(Collection<MarkerInfo> markerInfoList) {
        if (markerInfoList == null || markerInfoList.isEmpty()) {
            return;
        }

        for (MarkerInfo markerInfo : markerInfoList) {
            Marker marker = mBiMarkersMap.getValue(markerInfo);
            if (marker != null) {
                marker.remove();
                mBiMarkersMap.removeKey(markerInfo);
            }
        }
    }


    @Override
    public void setMapPadding(int left, int top, int right, int bottom) {

    }

    @Override
    public void setOnMapClickListener(OnMapClickListener listener) {
        mMapClickListener = listener;
    }

    @Override
    public void setOnMapLongClickListener(OnMapLongClickListener listener) {
        mMapLongClickListener = listener;
    }

    @Override
    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        mMarkerClickListener = listener;
    }

    @Override
    public void setOnMarkerDragListener(OnMarkerDragListener listener) {
        mMarkerDragListener = listener;
    }

    @Override
    public void setLocationListener(Location.LocationReceiver listener) {
        mLocationListener = listener;
    }


    @Override
    public void updateCamera(LatLng coord, float zoomLevel) {
        if(coord != null){
            final float zoomLevel = getMap().getMapStatus().zoom;
            getMap().animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(DroneHelper.CoordToBaiduLatLang(coord), zoomLevel));
        }
    }

    @Override
    public void updateCameraBearing(float bearing) {
        MapStatus ms = new MapStatus.Builder(getBaiduMap().getMapStatus()).rotate(bearing).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
        getBaiduMap().animateMapStatus(u);
    }

    @Override
    public void updateDroneLeashPath(PathSource pathSource) {
        List<LatLng> pathCoords = pathSource.getPathPoints();

        final List<LatLng> pathPoints = new ArrayList<LatLng>(pathCoords.size());
        for (LatLng coord : pathCoords) {
            pathPoints.add(DroneHelper.CoordToBaiduLatLang(coord));
        }

        if (pathPoints.size() <2)
        {
            if(mDroneLeashPath != null)
            {
                mDroneLeashPath.remove();
                mDroneLeashPath = null;
            }
            return;
        }

        if (mDroneLeashPath == null) {
            PolylineOptions flightPath = new PolylineOptions();
            flightPath.color(DRONE_LEASH_DEFAULT_COLOR).width(
                    DroneHelper.scaleDpToPixels(DRONE_LEASH_DEFAULT_WIDTH,
                            getResources()));
            flightPath.points(pathPoints);
            mDroneLeashPath = (Polyline)getMap().addOverlay(flightPath);
        }
        mDroneLeashPath.setPoints(pathPoints);
    }

    @Override
    public void updateMarker(MarkerInfo markerInfo) {
        updateMarker(markerInfo, markerInfo.isDraggable());
    }

    @Override
    public void updateMarker(MarkerInfo markerInfo, boolean isDraggable) {
// if the drone hasn't received a gps signal yet
        final LatLng coord = markerInfo.getPosition();
        if (coord == null) {
            return;
        }
        final LatLng position = DroneHelper.CoordToBaiduLatLang(coord);
        Marker marker = mBiMarkersMap.getValue(markerInfo);
        if (marker == null) {
            // Generate the marker
            generateMarker(markerInfo, position, isDraggable);
        } else {
            // Update the marker
            updateMarker(marker, markerInfo, position, isDraggable);
        }
    }

    private void generateMarker(MarkerInfo markerInfo, LatLng position, boolean isDraggable) {

        final MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .draggable(isDraggable)
                .anchor(markerInfo.getAnchorU(), markerInfo.getAnchorV())
                .title(markerInfo.getSnippet()).title(markerInfo.getTitle());

        final Bitmap markerIcon = markerInfo.getIcon(getResources());
        if (markerIcon != null) {
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(markerIcon));
        }
        else
        {
            markerOptions.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_marker_white));
        }

        Marker marker = (Marker)getMap().addOverlay(markerOptions);
        mBiMarkersMap.put(markerInfo, marker);
    }

    @Override
    public void updateMarkers(List<MarkerInfo> markersInfos) {
        for (MarkerInfo info : markersInfos) {
            updateMarker(info);
        }
    }

    @Override
    public void updateMarkers(List<MarkerInfo> markersInfos, boolean isDraggable) {
        for (MarkerInfo info : markersInfos) {
            updateMarker(info, isDraggable);
        }
    }

    @Override
    public void updateMissionPath(PathSource pathSource) {

    }

    @Override
    public void updatePolygonsPaths(List<List<LatLng>> paths) {

    }

    @Override
    public void zoomToFit(List<LatLng> coords) {

    }

    @Override
    public void zoomToFitMyLocation(List<LatLng> coords) {

    }

    @Override
    public void skipMarkerClickEvents(boolean skip) {

    }
}
*/
