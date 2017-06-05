package dji.midware.ui.panel;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import dji.common.bus.LogicEventBus;
import dji.common.bus.UILibEventBus;
import dji.common.flightcontroller.FlightMode;
import dji.common.remotecontroller.AircraftMappingStyle;
import dji.common.remotecontroller.ChargeRemaining;
import dji.internal.logics.CompassLogic;
import dji.internal.logics.ESCLogic;
import dji.internal.logics.FPVTipLogic;
import dji.internal.logics.GimbalLogic;
import dji.internal.logics.IMULogic;
import dji.internal.logics.LogicManager;
import dji.internal.logics.Message;
import dji.internal.logics.RadioChannelQualityLogic;
import dji.internal.logics.VisionLogic;
import dji.keysdk.BatteryKey;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.RemoteControllerKey;
import dji.midware.R;
import dji.midware.ui.a.UiAB;
import dji.midware.ui.base.UiBaseMView;
import dji.midware.ui.c.UiCC;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.internal.CInter;
import dji.midware.ui.widget.FlightModeWidget;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Action1;
import dji.thirdparty.rx.schedulers.Schedulers;

/**
 * Created by jack_xie on 17-6-1.
 */

public class PreFlightCheckListPanel extends UiBaseMView {
    private static final String UNKNOWN = "Unknown";
    private FlightMode flightMode;
    private DJIKey flightModeKey;
    private DJIKey remoteControllerModeKey;
    private DJIKey remoteControllerBatteryKey;
    private DJIKey aircraftBatteryKey;
    private DJIKey aircraftBatteryTempKey;
    private DJIKey gimbalModeKey;
    private DJIKey sdRemainingSpaceKey;
    private DJIKey isVisionSystemEnableKey;
    private boolean isVisionSystemEnabled;
    private UiCC firmwareItem;
    private UiCC radioItem;
    private UiCC imuItem;
    private UiCC compassItem;
    private UiCC airBatteryItem;
    private UiCC rcBatteryItem;
    private UiCC flightItem;
    private UiCC remoteModeItem;
    private UiCC airBatteryTemp;
    private UiCC sdCardItem;
    private UiCC gimbalItem;
    private int radioQuality;
    private UiCC escItem;
    private UiCC visionItem;
    private UiCC itemToModify;

    public PreFlightCheckListPanel(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public PreFlightCheckListPanel(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public PreFlightCheckListPanel(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.aircraft_status);
        }

    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        final Resources var4 = this.getResources();
        this.setBackgroundColor(-16777216);
        this.adapter = new UiAB((UiAB.a)null);
        this.firmwareItem = UiCC.a(R.drawable.ic_checklist_firmware, var4.getString(R.string.overall_status), "Unknown", -7829368);
        this.adapter.a(this.firmwareItem);
        this.flightItem = UiCC.a(R.drawable.ic_topbar_flight_mode, var4.getString(R.string.flight_mode), "Unknown", -7829368);
        this.adapter.a(this.flightItem);
        this.compassItem = UiCC.a(R.drawable.ic_checklist_compass, var4.getString(R.string.compass), "Unknown", -7829368);
        this.adapter.a(this.compassItem);
        this.imuItem = UiCC.a(R.drawable.ic_checklist_imu, var4.getString(R.string.imu), "Unknown", -7829368);
        this.adapter.a(this.imuItem);
        this.escItem = UiCC.a(R.drawable.ic_checklist_esc, var4.getString(R.string.esc_status), "Unknown", -7829368);
        this.adapter.a(this.escItem);
        this.visionItem = UiCC.a(R.drawable.visual_enable, var4.getString(R.string.vision_status), "Unknown", -7829368);
        this.adapter.a(this.visionItem);
        this.radioItem = UiCC.a(R.drawable.ic_checklist_radio_channel_quality, var4.getString(R.string.radio_quality), "Unknown", -7829368);
        this.adapter.a(this.radioItem);
        this.remoteModeItem = UiCC.a(R.drawable.ic_camera_setting_remote, var4.getString(R.string.remote_controller_mode), "Unknown", -7829368);
        this.adapter.a(this.remoteModeItem);
        this.airBatteryItem = UiCC.a(R.drawable.ic_camera_setting_battery, var4.getString(R.string.aircraft_battery), "Unknown", -7829368);
        this.adapter.a(this.airBatteryItem);
        this.rcBatteryItem = UiCC.a(R.drawable.ic_checklist_controler_battery, var4.getString(R.string.remote_controller_battery), "Unknown", -7829368);
        this.adapter.a(this.rcBatteryItem);
        this.airBatteryTemp = UiCC.a(R.drawable.ic_checklist_aircraft_battery_temperature, var4.getString(R.string.aircraft_battery_temperature), "Unknown", -7829368);
        this.adapter.a(this.airBatteryTemp);
        this.sdCardItem = UiCC.a(R.drawable.ic_checklist_sdcard, var4.getString(R.string.sd_card_capacity), "Unknown", -7829368);
        this.adapter.a(this.sdCardItem);
        this.gimbalItem = UiCC.a(R.drawable.ic_checklist_gimbal, var4.getString(R.string.preflight_checklist_gimbal_status), "Unknown", -7829368);
        this.adapter.a(this.gimbalItem);
        this.adapter.a(true);
        this.contentList.setHasFixedSize(true);
        this.contentList.setAdapter(this.adapter);
        this.contentList.setLayoutManager(new LinearLayoutManager(var1));
        LogicManager.getInstance().startFPVTipLogic();
        LogicEventBus.getInstance().register(FPVTipLogic.FPVTipEvent.class).observeOn(Schedulers.computation()).subscribe(new Action1<FPVTipLogic.FPVTipEvent>() {
            @Override
            public void call(FPVTipLogic.FPVTipEvent var1) {
                Message var2 = var1.getMessage();
                updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.firmwareItem, var2, var4);
            }
        });
        LogicManager.getInstance().startGimbalLogic();
        LogicEventBus.getInstance().register(GimbalLogic.GimbalEvent.class).observeOn(Schedulers.computation()).subscribe(new Action1<GimbalLogic.GimbalEvent>() {
            @Override
            public void call(GimbalLogic.GimbalEvent var1) {
                Message var2 = var1.getMessage();
                PreFlightCheckListPanel.this.updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.gimbalItem, var2, var4);
            }
        });
        LogicManager.getInstance().startRadioChannelQualityLogic();
        LogicEventBus.getInstance().register(RadioChannelQualityLogic.RadioChannelQualityEvent.class).
                observeOn(Schedulers.computation()).subscribe(new Action1<RadioChannelQualityLogic.RadioChannelQualityEvent>() {
            @Override
            public void call(RadioChannelQualityLogic.RadioChannelQualityEvent var1) {
                Message var2 = var1.getMessage();
                PreFlightCheckListPanel.this.updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.radioItem, var2, var4);
            }
        });
        LogicManager.getInstance().startIMULogic();
        LogicEventBus.getInstance().register(IMULogic.IMUEvent.class).observeOn(Schedulers.computation()).subscribe(new Action1<IMULogic.IMUEvent>() {
            @Override
            public void call(IMULogic.IMUEvent var1) {
                Message var2 = var1.getMessage();
                PreFlightCheckListPanel.this.updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.imuItem, var2, var4);
            }
        });
        LogicManager.getInstance().startESCLogic();
        LogicEventBus.getInstance().register(ESCLogic.ESCEvent.class).observeOn(Schedulers.computation()).subscribe(new Action1<ESCLogic.ESCEvent>() {
            @Override
            public void call(ESCLogic.ESCEvent var1) {
                Message var2 = var1.getMessage();
                PreFlightCheckListPanel.this.updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.escItem, var2, var4);
            }
        });
        LogicManager.getInstance().startVisionLogic();
        LogicEventBus.getInstance().register(VisionLogic.VisionEvent.class).observeOn(Schedulers.computation()).subscribe(new Action1<VisionLogic.VisionEvent>() {
            @Override
            public void call(VisionLogic.VisionEvent var1) {
                Message var2 = var1.getMessage();
                PreFlightCheckListPanel.this.updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.visionItem, var2, var4);
            }
        });
        LogicManager.getInstance().startCompassLogic();
        LogicEventBus.getInstance().register(CompassLogic.CompassEvent.class).observeOn(Schedulers.computation()).subscribe(new Action1<CompassLogic.CompassEvent>() {
            @Override
            public void call(CompassLogic.CompassEvent var1) {
                Message var2 = var1.getMessage();
                PreFlightCheckListPanel.this.updateListItemUsingLogicMessage(PreFlightCheckListPanel.this.compassItem, var2, var4);
            }
        });
        UILibEventBus.getInstance().register(CInter.c.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<CInter.c>() {
            @Override
            public void call(CInter.c var1) {
                if(PreFlightCheckListPanel.this.getVisibility() == 0) {
                    PreFlightCheckListPanel.this.setVisibility(8);
                } else {
                    PreFlightCheckListPanel.this.setVisibility(0);
                }

            }
        });
    }

    private void updateListItemUsingLogicMessage(UiCC var1, Message var2, Resources var3) {
        if(var2 != null && !var1.d().equals(var2.getTitle())) {
            var1.b(var2.getTitle());
            switch(var1.c()) {
                case 1:
                    var1.a(var3.getColor(R.color.red));
                    break;
                case 2:
                    var1.a(var3.getColor(R.color.yellow));
                    break;
                case 3:
                    var1.a(-7829368);
                    break;
                default:
                    var1.a(-16711936);
            }

            final int var4 = this.adapter.b(var1);
            this.post(new Runnable() {
                public void run() {
                    PreFlightCheckListPanel.this.adapter.notifyItemChanged(var4);
                }
            });
        }

    }

    public void initKey() {
        this.flightModeKey = FlightControllerKey.create("FlightMode");
        this.addDependentKey(this.flightModeKey);
        this.isVisionSystemEnableKey = FlightControllerKey.create("IsVisionPositioningSensorBeingUsed");
        this.addDependentKey(this.isVisionSystemEnableKey);
        this.remoteControllerModeKey = RemoteControllerKey.create("AircraftMappingStyle");
        this.addDependentKey(this.remoteControllerModeKey);
        this.remoteControllerBatteryKey = RemoteControllerKey.create("ChargeRemaining");
        this.addDependentKey(this.remoteControllerBatteryKey);
        this.aircraftBatteryKey = BatteryKey.create("ChargeRemainingInPercent");
        this.addDependentKey(this.aircraftBatteryKey);
        this.aircraftBatteryTempKey = BatteryKey.create("Temperature");
        this.addDependentKey(this.aircraftBatteryTempKey);
        this.sdRemainingSpaceKey = CameraKey.create("SDCardRemainingSpaceInMB");
        this.addDependentKey(this.sdRemainingSpaceKey);
    }

    private String getRemoteControllerModeName(AircraftMappingStyle var1) {
        String var2 = "Unknown";
        if(var1.value() == AircraftMappingStyle.STYLE_1.value()) {
            var2 = "Mode 1";
        } else if(var1.value() == AircraftMappingStyle.STYLE_2.value()) {
            var2 = "Mode 2";
        } else if(var1.value() == AircraftMappingStyle.STYLE_3.value()) {
            var2 = "Mode 3";
        } else if(var1.value() == AircraftMappingStyle.STYLE_CUSTOM.value()) {
            var2 = "Custom";
        }

        return var2;
    }

    public void transformValue(Object var1, DJIKey var2) {
        this.itemToModify = null;
        String var3 = null;
        int var4 = -16711936;
        if(var2.equals(this.flightModeKey)) {
            this.itemToModify = this.flightItem;
            this.flightMode = (FlightMode)var1;
            var3 = this.context.getString(FlightModeWidget.getFlycModeResId(this.flightMode, this.isVisionSystemEnabled));
        } else if(var2.equals(this.isVisionSystemEnableKey)) {
            this.itemToModify = this.flightItem;
            this.isVisionSystemEnabled = ((Boolean)var1).booleanValue();
            var3 = this.context.getString(FlightModeWidget.getFlycModeResId(this.flightMode, this.isVisionSystemEnabled));
        } else if(var2.equals(this.remoteControllerModeKey)) {
            this.itemToModify = this.remoteModeItem;
            var3 = this.getRemoteControllerModeName((AircraftMappingStyle)var1);
        } else if(var2.equals(this.remoteControllerBatteryKey)) {
            this.itemToModify = this.rcBatteryItem;
            ChargeRemaining var5 = (ChargeRemaining)var1;
            var3 = var5.getRemainingChargeInPercent() + "%";
        } else if(var2.equals(this.aircraftBatteryKey)) {
            this.itemToModify = this.airBatteryItem;
            var3 = ((Integer)var1).intValue() + "%";
        } else if(var2.equals(this.aircraftBatteryTempKey)) {
            this.itemToModify = this.airBatteryTemp;
            Integer i = Integer.valueOf((Math.round((Float) var1)));
            var3 = String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(i)}) + "° C";  // new Object[]{Integer.valueOf(((Integer)var1).intValue())}
        } else if(var2.equals(this.sdRemainingSpaceKey)) {
            this.itemToModify = this.sdCardItem;
            var3 = ((Integer)var1).intValue() + "MB";
        }

        if(this.itemToModify != null) {
            if(var3.isEmpty()) {
                var3 = "Unknown";
                var4 = -7829368;
            }

            this.itemToModify.b(var3);
            this.itemToModify.a(var4);
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.itemToModify != null) {
            this.adapter.notifyItemChanged(this.adapter.b(this.itemToModify));
        }

    }

    public void updateSelectedItem(View var1, int var2) {
    }
}
