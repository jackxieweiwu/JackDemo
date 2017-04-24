package zkrtdrone.zkrt.com.maplib.info.mission.coordinate;

import android.os.Bundle;
import android.os.Parcelable;

import zkrtdrone.zkrt.com.maplib.info.mission.bean.Survey;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.command.CameraTrigger;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.command.ChangeSpeed;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.command.EpmGripper;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.command.ReturnToLaunch;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.command.SetServo;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.command.Takeoff;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil.Circle;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil.Land;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil.RegionOfInterest;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil.SplineWaypoint;
import zkrtdrone.zkrt.com.maplib.info.mission.bean.spatil.Waypoint;
import zkrtdrone.zkrt.com.maplib.info.until.ParcelableUtils;

/**
 * Created by jack_xie on 17-4-24.
 * List of mission item types.
 */

public enum MissionItemType {
    WAYPOINT("Waypoint") {
        @Override
        public MissionItem getNewItem() {
            return new Waypoint();
        }

        @Override
        protected Parcelable.Creator<Waypoint> getMissionItemCreator() {
            return Waypoint.CREATOR;
        }
    },

    SPLINE_WAYPOINT("Spline Waypoint") {
        @Override
        public MissionItem getNewItem() {
            return new SplineWaypoint();
        }

        @Override
        protected Parcelable.Creator<SplineWaypoint> getMissionItemCreator() {
            return SplineWaypoint.CREATOR;
        }
    },

    TAKEOFF("Takeoff") {
        @Override
        public MissionItem getNewItem() {
            return new Takeoff();
        }

        @Override
        protected Parcelable.Creator<Takeoff> getMissionItemCreator() {
            return Takeoff.CREATOR;
        }
    },

    CHANGE_SPEED("Change Speed") {
        @Override
        public MissionItem getNewItem() {
            return new ChangeSpeed();
        }

        @Override
        protected Parcelable.Creator<ChangeSpeed> getMissionItemCreator(){
            return ChangeSpeed.CREATOR;
        }
    },

    CAMERA_TRIGGER("Camera Trigger") {
        @Override
        public MissionItem getNewItem() {
            return new CameraTrigger();
        }

        @Override
        protected Parcelable.Creator<CameraTrigger> getMissionItemCreator() {
            return CameraTrigger.CREATOR;
        }
    },

    EPM_GRIPPER("EPM Gripper") {
        @Override
        public MissionItem getNewItem() {
            return new EpmGripper();
        }

        @Override
        protected Parcelable.Creator<EpmGripper> getMissionItemCreator() {
            return EpmGripper.CREATOR;
        }

    },

    RETURN_TO_LAUNCH("Return to Launch") {
        @Override
        public MissionItem getNewItem() {
            return new ReturnToLaunch();
        }

        @Override
        protected Parcelable.Creator<ReturnToLaunch> getMissionItemCreator() {
            return ReturnToLaunch.CREATOR;
        }
    },

    LAND("Land") {
        @Override
        public MissionItem getNewItem() {
            return new Land();
        }

        @Override
        protected Parcelable.Creator<Land> getMissionItemCreator() {
            return Land.CREATOR;
        }
    },

    CIRCLE("Circle") {
        @Override
        public MissionItem getNewItem() {
            return new Circle();
        }

        @Override
        protected Parcelable.Creator<Circle> getMissionItemCreator() {
            return Circle.CREATOR;
        }
    },

    REGION_OF_INTEREST("Region of Interest") {
        @Override
        public MissionItem getNewItem() {
            return new RegionOfInterest();
        }

        @Override
        protected Parcelable.Creator<RegionOfInterest> getMissionItemCreator() {
            return RegionOfInterest.CREATOR;
        }
    },

    SURVEY("Survey") {
        @Override
        public MissionItem getNewItem() {
            return new Survey();
        }

        @Override
        protected Parcelable.Creator<Survey> getMissionItemCreator() {
            return Survey.CREATOR;
        }
    },

    /*STRUCTURE_SCANNER("Structure Scanner") {
        @Override
        public MissionItem getNewItem() {
            return new StructureScanner();
        }

        @Override
        protected Parcelable.Creator<StructureScanner> getMissionItemCreator() {
            return StructureScanner.CREATOR;
        }
    },*/

    SET_SERVO("Set Servo") {
        @Override
        public MissionItem getNewItem() {
            return new SetServo();
        }

        @Override
        protected Parcelable.Creator<SetServo> getMissionItemCreator() {
            return SetServo.CREATOR;
        }
    };

    /*YAW_CONDITION("Set Yaw"){
        @Override
        public MissionItem getNewItem(){
            return new YawCondition();
        }

        @Override
        protected Parcelable.Creator<YawCondition> getMissionItemCreator() {
            return YawCondition.CREATOR;
        }
    };*/

    private final static String EXTRA_MISSION_ITEM_TYPE = "extra_mission_item_type";
    private final static String EXTRA_MISSION_ITEM = "extra_mission_item";

    private final String label;

    private MissionItemType(String label){
        this.label = label;
    }

    public String getLabel(){
        return this.label;
    }

    public abstract MissionItem getNewItem();

    public final Bundle storeMissionItem(MissionItem item){
        Bundle bundle = new Bundle(2);
        storeMissionItem(item, bundle);
        return bundle;
    }

    public void storeMissionItem(MissionItem missionItem, Bundle bundle) {
        bundle.putString(EXTRA_MISSION_ITEM_TYPE, name());
        bundle.putByteArray(EXTRA_MISSION_ITEM, ParcelableUtils.marshall(missionItem));
    }

    protected abstract <T extends MissionItem> Parcelable.Creator<T> getMissionItemCreator();

    public static <T extends MissionItem> T restoreMissionItemFromBundle(Bundle bundle){
        if(bundle == null)
            return null;

        String typeName = bundle.getString(EXTRA_MISSION_ITEM_TYPE);
        byte[] marshalledItem = bundle.getByteArray(EXTRA_MISSION_ITEM);
        if(typeName == null || marshalledItem == null)
            return null;

        MissionItemType type = MissionItemType.valueOf(typeName);
        if(type == null)
            return null;

        T missionItem = ParcelableUtils.unmarshall(marshalledItem, type.<T>getMissionItemCreator());
        return missionItem;
    }

}
