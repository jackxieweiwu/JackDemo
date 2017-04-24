package zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil;

import android.os.Parcel;

import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItemType;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.LatLongAlt;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItem;

/**
 * Created by jack_xie on 17-4-24.
 */

public abstract class BaseSpatialItem extends MissionItem implements MissionItem.SpatialItem, android.os.Parcelable {

    private LatLongAlt coordinate;

    protected BaseSpatialItem(MissionItemType type) {
        super(type);
    }

    @Override
    public LatLongAlt getCoordinate() {
        return coordinate;
    }

    @Override
    public void setCoordinate(LatLongAlt coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.coordinate, flags);
    }

    protected BaseSpatialItem(Parcel in) {
        super(in);
        this.coordinate = in.readParcelable(LatLongAlt.class.getClassLoader());
    }
}
