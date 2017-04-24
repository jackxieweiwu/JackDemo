package zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil;

import android.os.Parcel;

import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItemType;


/**
 * Created by jack_xie on 11/6/14.
 */
public class RegionOfInterest extends BaseSpatialItem implements android.os.Parcelable {

    public RegionOfInterest(){
        super(MissionItemType.REGION_OF_INTEREST);
    }

    private RegionOfInterest(Parcel in) {
        super(in);
    }

    public static final Creator<RegionOfInterest> CREATOR = new Creator<RegionOfInterest>() {
        public RegionOfInterest createFromParcel(Parcel source) {
            return new RegionOfInterest(source);
        }

        public RegionOfInterest[] newArray(int size) {
            return new RegionOfInterest[size];
        }
    };
}
