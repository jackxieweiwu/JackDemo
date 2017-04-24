package zkrtdrone.zkrt.com.maplib.info.mission.coordinate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jack_xie on 17-4-24.
 */

public abstract class MissionItem implements Parcelable {
    public interface Command {}

    public interface SpatialItem {
        LatLongAlt getCoordinate();

        void setCoordinate(LatLongAlt coordinate);
    }

    public interface ComplexItem<T extends MissionItem> {
        void copy(T source);
    }

    private final MissionItemType type;

    protected MissionItem(MissionItemType type) {
        this.type = type;
    }

    public MissionItemType getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type.ordinal());
    }

    protected MissionItem(Parcel in){
        this.type = MissionItemType.values()[in.readInt()];
    }
}
