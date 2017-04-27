package zkrtdrone.zkrt.com.maplib.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;

/**
 * Created by jack_xie on 17-4-24.
 */

public class EditorMapFragment extends DroneMap implements DPmap.OnMapLongClickListener,
        DPmap.OnMarkerDragListener, DPmap.OnMapClickListener, DPmap.OnMarkerClickListener {
    //private OnEditorInteraction editorListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (!(context instanceof OnEditorInteraction)) {
            throw new IllegalStateException("Parent activity must implement " +
                    OnEditorInteraction.class.getName());
        }*/
        //editorListener = (OnEditorInteraction) (context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(inflater, viewGroup, bundle);
        mMapFragment.setOnMarkerDragListener(this);
        mMapFragment.setOnMarkerClickListener(this);
        mMapFragment.setOnMapClickListener(this);
        mMapFragment.setOnMapLongClickListener(this);
        return view;
    }

    @Override
    public void onMapClick(LatLong coord) {
        //editorListener.onMapClick(coord);
    }

    @Override
    public void onMapLongClick(LatLong coord) {

    }

    @Override
    public boolean onMarkerClick(MarkerInfo markerInfo) {
       /* if (markerInfo instanceof MissionItemMarkerInfo) {
            editorListener.onItemClick(((MissionItemMarkerInfo) markerInfo).getMarkerOrigin(), false);
            return true;
        } else {
            return false;
        }*/

        return false;
    }

    @Override
    public void onMarkerDrag(MarkerInfo markerInfo) {
        checkForWaypointMarkerMoving(markerInfo);
    }

    @Override
    public void onMarkerDragEnd(MarkerInfo markerInfo) {
        checkForWaypointMarker(markerInfo);
    }

    @Override
    public void onMarkerDragStart(MarkerInfo markerInfo) {
        checkForWaypointMarkerMoving(markerInfo);
    }

    private void checkForWaypointMarkerMoving(MarkerInfo markerInfo) {
        /*if (markerInfo instanceof MissionItem.SpatialItem) {
            LatLong position = markerInfo.getPosition();

            // update marker source
            MissionItem.SpatialItem waypoint = (MissionItem.SpatialItem) markerInfo;
            LatLong waypointPosition = waypoint.getCoordinate();
            waypointPosition.setLatitude(position.getLatitude());
            waypointPosition.setLongitude(position.getLongitude());

            // update flight path
            mMapFragment.updateMissionPath(missionProxy);
        }*/
    }

    private void checkForWaypointMarker(MarkerInfo markerInfo) {
        /*if ((markerInfo instanceof MissionItemMarkerInfo)) {
            missionProxy.move(((MissionItemMarkerInfo) markerInfo).getMarkerOrigin(),
                    markerInfo.getPosition());
        }else if ((markerInfo instanceof PolygonMarkerInfo)) {
            PolygonMarkerInfo marker = (PolygonMarkerInfo) markerInfo;
            missionProxy.movePolygonPoint(marker.getSurvey(), marker.getIndex(), markerInfo.getPosition());
        }*/
    }

    public void setDroneBitmap(Bitmap droneBitmap) {
        mMapFragment.setDroneMap(droneBitmap);
    }
}
