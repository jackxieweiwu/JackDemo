package zkrtdrone.zkrt.com.maplib.info;

import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLong;

/**
 * Created by jack_xie on 17-4-24.
 */

public interface OnEditorInteraction {
   /* public boolean onItemLongClick(MissionItemProxy item);

    public void onItemClick(MissionItemProxy item, boolean zoomToFit);*/

    public void onMapClick(LatLong coord);

    public void onListVisibilityChanged();
}
