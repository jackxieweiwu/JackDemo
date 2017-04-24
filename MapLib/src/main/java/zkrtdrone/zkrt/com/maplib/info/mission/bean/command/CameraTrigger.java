package zkrtdrone.zkrt.com.maplib.info.mission.bean.command;

import android.os.Parcel;

import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItem;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItemType;


/**
 * Created by jack_xie on 11/6/14.
 */
public class CameraTrigger extends MissionItem implements MissionItem.Command, android.os.Parcelable {

    private double triggerDistance;

    public CameraTrigger(){
        super(MissionItemType.CAMERA_TRIGGER);
    }

    public double getTriggerDistance() {
        return triggerDistance;
    }

    public void setTriggerDistance(double triggerDistance) {
        this.triggerDistance = triggerDistance;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(this.triggerDistance);
    }

    private CameraTrigger(Parcel in) {
        super(in);
        this.triggerDistance = in.readDouble();
    }

    public static final Creator<CameraTrigger> CREATOR = new Creator<CameraTrigger>() {
        public CameraTrigger createFromParcel(Parcel source) {
            return new CameraTrigger(source);
        }

        public CameraTrigger[] newArray(int size) {
            return new CameraTrigger[size];
        }
    };
}
