package zkrtdrone.zkrt.com.maplib.info.mission.bean.command;

import android.os.Parcel;

import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItem;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItemType;

/**
 * Created by fhuya on 11/6/14.
 */
public class ReturnToLaunch extends MissionItem implements MissionItem.Command, android.os.Parcelable {

    private double returnAltitude;

    public ReturnToLaunch(){
        super(MissionItemType.RETURN_TO_LAUNCH);
    }

    public double getReturnAltitude() {
        return returnAltitude;
    }

    public void setReturnAltitude(double returnAltitude) {
        this.returnAltitude = returnAltitude;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(this.returnAltitude);
    }

    private ReturnToLaunch(Parcel in) {
        super(in);
        this.returnAltitude = in.readDouble();
    }

    public static final Creator<ReturnToLaunch> CREATOR = new Creator<ReturnToLaunch>() {
        public ReturnToLaunch createFromParcel(Parcel source) {
            return new ReturnToLaunch(source);
        }

        public ReturnToLaunch[] newArray(int size) {
            return new ReturnToLaunch[size];
        }
    };
}
