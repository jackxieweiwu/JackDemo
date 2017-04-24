package zkrtdrone.zkrt.com.maplib.info.mission.bean.command;

import android.os.Parcel;

import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItem;
import zkrtdrone.zkrt.com.maplib.info.mission.coordinate.MissionItemType;


/**
 * Created by jack_xie on 11/6/14.
 */
public class ChangeSpeed extends MissionItem implements MissionItem.Command, android.os.Parcelable {

    private double speed;

    public ChangeSpeed(){
        super(MissionItemType.CHANGE_SPEED);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(this.speed);
    }

    private ChangeSpeed(Parcel in) {
        super(in);
        this.speed = in.readDouble();
    }

    public static final Creator<ChangeSpeed> CREATOR = new Creator<ChangeSpeed>() {
        public ChangeSpeed createFromParcel(Parcel source) {
            return new ChangeSpeed(source);
        }

        public ChangeSpeed[] newArray(int size) {
            return new ChangeSpeed[size];
        }
    };
}
