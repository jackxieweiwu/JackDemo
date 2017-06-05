package dji.midware.ui.widget.dashboard;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import dji.common.remotecontroller.GPSData;
import dji.common.util.MobileGPSLocationUtil;
import dji.keysdk.DJIKey;
import dji.keysdk.FlightControllerKey;
import dji.keysdk.GimbalKey;
import dji.keysdk.RemoteControllerKey;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.base.UiBaseGView;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBM;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDF;
import dji.midware.ui.internal.compass.DULGimbalYawView;
import dji.midware.ui.internal.compass.DULVisualCompassView;
import dji.thirdparty.rx.Observable;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Action1;

/**
 * Created by jack_xie on 17-6-1.
 */

public class CompassWidget extends DULFrameLayout implements SensorEventListener {
    private static final String TAG = "DULCompassWidget";
    private static final int SENSOR_SENSITIVE_PARAM = 2;
    private float aircraftHeading;
    private float aircraftPitch;
    private float aircraftRoll;
    private int gimbalHeading;
    private double aircraftLatitude;
    private double aircraftLongitude;
    private double rcOrMobileLatitude;
    private double rcOrMobileLongitude;
    private double homeLatitude;
    private double homeLongitude;
    private SensorManager phoneSensorManager;
    private static final int MAX_DISTANCE = 400;
    private static final int MAX_SCALE_DISTANCE = 2000;
    private static final float MIN_SCALE = 0.6F;
    private ImageView homeImage;
    private ImageView rcImage;
    private ImageView aircraftImage;
    private ImageView gimbalYawImage;
    private ImageView innerStaticCycles;
    private ProgressBar compassSea;
    private DULVisualCompassView compassAnimateView;
    private DULGimbalYawView gimbalYawView;
    private float phoneAzimuth;
    private Display display;
    private static final int TYPE_RC_MOBILE_GPS = 0;
    private static final int TYPE_HOME_GPS = 1;
    private int centerType;
    private float aircraftAngle;
    private float aircraftDistance;
    private float rcHomeAngle;
    private float rcHomeDistance;
    private DJIKey attitudePitchKey;
    private DJIKey attitudeRollKey;
    private DJIKey attitudeYawKey;
    private DJIKey homeLatitudeKey;
    private DJIKey homeLongitudeKey;
    private DJIKey aircraftLatitudeKey;
    private DJIKey aircraftLongitudeKey;
    private DJIKey rcGpsDataKey;
    private DJIKey gimbalYawKey;
    private UiCAB widgetAppearances;
    private ImageView secondGPSImage;
    private Sensor rotationVector;
    private ImageView northImg;
    private float halfNorthIcoWidth;
    private float halfAttitudeBallWidth;
    private ImageView compassBackground;
    private float padding;
    private MobileGPSLocationUtil mobileGpsLocationUtil;
    private float[] values;
    private float[] rotations;
    private double tmpLastSensor;

    public CompassWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CompassWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CompassWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.aircraftHeading = 0.0F;
        this.aircraftPitch = 0.0F;
        this.aircraftRoll = 0.0F;
        this.gimbalHeading = 0;
        this.aircraftLatitude = 0.0D;
        this.aircraftLongitude = 0.0D;
        this.rcOrMobileLatitude = 0.0D;
        this.rcOrMobileLongitude = 0.0D;
        this.homeLatitude = 0.0D;
        this.homeLongitude = 0.0D;
        this.phoneAzimuth = 0.0F;
        this.display = null;
        this.centerType = 1;
        this.aircraftAngle = 0.0F;
        this.aircraftDistance = 0.0F;
        this.rcHomeAngle = 0.0F;
        this.rcHomeDistance = 0.0F;
        this.secondGPSImage = null;
        this.values = new float[3];
        this.rotations = new float[9];
        this.tmpLastSensor = 0.0D;
        UiDA.b();
    }

    private void initMobileGPSTracker() {
        LocationListener var1 = new LocationListener() {
            public void onLocationChanged(Location var1) {
                if(var1 != null) {
                    CompassWidget.this.centerType = 0;
                    CompassWidget.this.rcOrMobileLatitude = var1.getLatitude();
                    CompassWidget.this.rcOrMobileLongitude = var1.getLongitude();
                    CompassWidget.this.calculateAngleAndDistanceBetweenRCAndHome();
                    CompassWidget.this.calculateAircraftAngleAndDistanceFromCenterLocation();
                    Observable.just(Boolean.valueOf(true)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean var1) {
                            CompassWidget.this.updateHomePointAndRC();
                            CompassWidget.this.updateAircraftLocation();
                        }
                    });
                }

            }

            public void onStatusChanged(String var1, int var2, Bundle var3) {
            }

            public void onProviderEnabled(String var1) {
            }

            public void onProviderDisabled(String var1) {
            }
        };
        this.mobileGpsLocationUtil = new MobileGPSLocationUtil(this.context, var1);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setWidgetStyle(UiBaseGView.b);
        this.setWillNotDraw(false);
        if(!this.isInEditMode()) {
            this.phoneSensorManager = (SensorManager)var1.getSystemService("sensor");
            this.initMobileGPSTracker();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!this.isInEditMode()) {
            this.registerListener(2);
            this.mobileGpsLocationUtil.startUpdateLocation();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.unregisterListener();
        this.mobileGpsLocationUtil.stopUpdateLocation();
    }

    public void initKey() {
        this.attitudePitchKey = FlightControllerKey.create("AttitudePitch");
        this.attitudeRollKey = FlightControllerKey.create("AttitudeRoll");
        this.attitudeYawKey = FlightControllerKey.create("AttitudeYaw");
        this.homeLatitudeKey = FlightControllerKey.create("HomeLocationLatitude");
        this.homeLongitudeKey = FlightControllerKey.create("HomeLocationLongitude");
        this.aircraftLatitudeKey = FlightControllerKey.create("AircraftLocationLatitude");
        this.aircraftLongitudeKey = FlightControllerKey.create("AircraftLocationLongitude");
        this.rcGpsDataKey = RemoteControllerKey.create("GPSData");
        this.gimbalYawKey = GimbalKey.create("YawAngleWithAircraftInDegree");
        this.addDependentKey(this.attitudePitchKey);
        this.addDependentKey(this.attitudeRollKey);
        this.addDependentKey(this.attitudeYawKey);
        this.addDependentKey(this.homeLatitudeKey);
        this.addDependentKey(this.homeLongitudeKey);
        this.addDependentKey(this.aircraftLatitudeKey);
        this.addDependentKey(this.aircraftLongitudeKey);
        this.addDependentKey(this.rcGpsDataKey);
        this.addDependentKey(this.gimbalYawKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.attitudePitchKey)) {
            this.aircraftPitch = ((Double)var1).floatValue();
        } else if(var2.equals(this.attitudeYawKey)) {
            this.aircraftHeading = ((Double)var1).floatValue();
        } else if(var2.equals(this.attitudeRollKey)) {
            this.aircraftRoll = ((Double)var1).floatValue();
        } else {
            double var3;
            if(var2.equals(this.aircraftLatitudeKey)) {
                var3 = ((Double)var1).doubleValue();
                if(UiDF.b(var3)) {
                    this.aircraftLatitude = var3;
                    this.calculateAircraftAngleAndDistanceFromCenterLocation();
                }
            } else if(var2.equals(this.aircraftLongitudeKey)) {
                var3 = ((Double)var1).doubleValue();
                if(UiDF.a(var3)) {
                    this.aircraftLongitude = var3;
                    this.calculateAircraftAngleAndDistanceFromCenterLocation();
                }
            } else if(var2.equals(this.homeLatitudeKey)) {
                this.homeLatitude = ((Double)var1).doubleValue();
                this.calculateAngleAndDistanceBetweenRCAndHome();
                this.calculateAircraftAngleAndDistanceFromCenterLocation();
            } else if(var2.equals(this.homeLongitudeKey)) {
                this.homeLongitude = ((Double)var1).doubleValue();
                this.calculateAngleAndDistanceBetweenRCAndHome();
                this.calculateAircraftAngleAndDistanceFromCenterLocation();
            } else if(var2.equals(this.rcGpsDataKey)) {
                GPSData var5 = (GPSData)var1;
                if(var5 != null && var5.isValid()) {
                    this.centerType = 0;
                    this.rcOrMobileLatitude = var5.getLocation().getLatitude();
                    this.rcOrMobileLongitude = var5.getLocation().getLongitude();
                    this.mobileGpsLocationUtil.stopUpdateLocation();
                    this.calculateAngleAndDistanceBetweenRCAndHome();
                    this.calculateAircraftAngleAndDistanceFromCenterLocation();
                }
            } else if(var2.equals(this.gimbalYawKey)) {
                this.gimbalHeading = ((Integer)var1).intValue();
            }
        }

    }

    public void updateWidget(DJIKey var1) {
        if(!var1.equals(this.homeLongitudeKey) && !var1.equals(this.homeLatitudeKey)) {
            if(var1.equals(this.rcGpsDataKey)) {
                this.updateHomePointAndRC();
                this.updateAircraftLocation();
            } else if(!var1.equals(this.aircraftLongitudeKey) && !var1.equals(this.aircraftLatitudeKey)) {
                if(var1.equals(this.attitudeYawKey)) {
                    this.updateAircraftHeading();
                } else if(var1.equals(this.gimbalYawKey)) {
                    this.updateGimbalHeading((float)this.gimbalHeading);
                } else if(var1.equals(this.attitudePitchKey)) {
                    this.updateAircraftInPitch(this.aircraftPitch);
                } else if(var1.equals(this.attitudeRollKey)) {
                    this.updateAircraftInRoll(this.aircraftRoll);
                }
            } else {
                this.updateAircraftLocation();
            }
        } else {
            this.updateHomePointAndRC();
            this.updateAircraftLocation();
        }

    }

    private void registerListener(int var1) {
        if(this.phoneSensorManager != null) {
            this.rotationVector = this.phoneSensorManager.getDefaultSensor(11);
            if(this.rotationVector != null) {
                this.phoneSensorManager.registerListener(this, this.rotationVector, var1);
            }
        }

    }

    private void unregisterListener() {
        this.phoneSensorManager.unregisterListener(this, this.rotationVector);
    }

    private static void getRotationMatrixFromVector(float[] var0, float[] var1) {
        float var3 = var1[0];
        float var4 = var1[1];
        float var5 = var1[2];
        float var2;
        if(var1.length == 4) {
            var2 = var1[3];
        } else {
            var2 = 1.0F - var3 * var3 - var4 * var4 - var5 * var5;
            var2 = var2 > 0.0F?(float)Math.sqrt((double)var2):0.0F;
        }

        float var6 = 2.0F * var3 * var3;
        float var7 = 2.0F * var4 * var4;
        float var8 = 2.0F * var5 * var5;
        float var9 = 2.0F * var3 * var4;
        float var10 = 2.0F * var5 * var2;
        float var11 = 2.0F * var3 * var5;
        float var12 = 2.0F * var4 * var2;
        float var13 = 2.0F * var4 * var5;
        float var14 = 2.0F * var3 * var2;
        if(var0.length == 9) {
            var0[0] = 1.0F - var7 - var8;
            var0[1] = var9 - var10;
            var0[2] = var11 + var12;
            var0[3] = var9 + var10;
            var0[4] = 1.0F - var6 - var8;
            var0[5] = var13 - var14;
            var0[6] = var11 - var12;
            var0[7] = var13 + var14;
            var0[8] = 1.0F - var6 - var7;
        } else if(var0.length == 16) {
            var0[0] = 1.0F - var7 - var8;
            var0[1] = var9 - var10;
            var0[2] = var11 + var12;
            var0[3] = 0.0F;
            var0[4] = var9 + var10;
            var0[5] = 1.0F - var6 - var8;
            var0[6] = var13 - var14;
            var0[7] = 0.0F;
            var0[8] = var11 - var12;
            var0[9] = var13 + var14;
            var0[10] = 1.0F - var6 - var7;
            var0[11] = 0.0F;
            var0[12] = var0[13] = var0[14] = 0.0F;
            var0[15] = 1.0F;
        }

    }

    public void onSensorChanged(SensorEvent var1) {
        if(var1.sensor.getType() == 11) {
            getRotationMatrixFromVector(this.rotations, var1.values);
            SensorManager.getOrientation(this.rotations, this.values);
            float var2 = (float)Math.toDegrees((double)this.values[0]);
            boolean var3 = Math.abs((double)var2 - this.tmpLastSensor) > 2.0D;
            if(var3) {
                this.tmpLastSensor = (double)var2;
                int var4 = this.getDisplayRotation();
                if(var4 == 3) {
                    var2 += 180.0F;
                }

                this.phoneAzimuth = var2 + 90.0F;
                this.updateNorthHeading();
                this.updateAircraftHeading();
                this.updateSecondGPSLocation();
                this.updateAircraftLocation();
            }
        }

    }

    private void updateNorthHeading() {
        double var1 = (double)((360.0F - this.phoneAzimuth) % 360.0F);
        double var3 = Math.toRadians(var1);
        final float var5 = (float)((double)(this.halfAttitudeBallWidth + this.padding / 2.0F) + (double)this.halfAttitudeBallWidth * Math.sin(var3));
        final float var6 = (float)((double)(this.halfAttitudeBallWidth + this.padding / 2.0F) - (double)this.halfAttitudeBallWidth * Math.cos(var3));
        this.post(new Runnable() {
            public void run() {
                float var1 = var5;
                float var2 = var6;
                var1 -= CompassWidget.this.halfNorthIcoWidth;
                var2 -= CompassWidget.this.halfNorthIcoWidth;
                CompassWidget.this.northImg.setX(var1);
                CompassWidget.this.northImg.setY(var2);
            }
        });
    }

    public void onAccuracyChanged(Sensor var1, int var2) {
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.compassBackground = (ImageView)this.findViewById(R.id.background_imageview);
        this.homeImage = (ImageView)this.findViewById(R.id.compass_home_img);
        this.rcImage = (ImageView)this.findViewById(R.id.imageview_compass_rc);
        this.northImg = (ImageView)this.findViewById(R.id.north_imageview);
        this.innerStaticCycles = (ImageView)this.findViewById(R.id.imageview_inner_circles);
        this.aircraftImage = (ImageView)this.findViewById(R.id.imageview_compass_aircraft);
        this.gimbalYawImage = (ImageView)this.findViewById(R.id.imageview_gimbal_heading);
        this.compassSea = (ProgressBar)this.findViewById(R.id.progressbar_compass_sea);
        this.compassAnimateView = (DULVisualCompassView)this.findViewById(R.id.visual_compass_view);
        this.gimbalYawView = (DULGimbalYawView)this.findViewById(R.id.gimbalyawview_compass);
        this.initState();
    }

    private synchronized void resetGimbalPivot() {
        this.gimbalYawImage.setPivotX((float)(this.gimbalYawImage.getMeasuredWidth() / 2));
        this.gimbalYawImage.setPivotY((float)this.gimbalYawImage.getMeasuredHeight());
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBM();
        }

        return this.widgetAppearances;
    }

    protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
        super.onLayout(var1, var2, var3, var4, var5);
        this.resetGimbalPivot();
        this.halfNorthIcoWidth = (float)this.northImg.getWidth() / 2.0F;
        this.halfAttitudeBallWidth = (float)this.compassBackground.getWidth() / 2.0F;
        this.padding = (float)this.getWidth() - (float)this.compassBackground.getWidth();
    }

    private void initState() {
        this.updateAircraftInPitch(0.0F);
        this.updateAircraftInRoll(0.0F);
        this.gimbalYawView.setYaw((float)this.gimbalHeading);
        this.clearLocation();
    }

    private void clearLocation() {
        this.rcOrMobileLatitude = 0.0D;
        this.rcOrMobileLongitude = 0.0D;
        this.aircraftLatitude = 0.0D;
        this.aircraftLongitude = 0.0D;
        this.homeLatitude = 0.0D;
        this.homeLongitude = 0.0D;
        this.centerType = 1;
        this.aircraftAngle = 0.0F;
        this.aircraftDistance = 0.0F;
        this.rcHomeAngle = 0.0F;
        this.rcHomeDistance = 0.0F;
    }

    private float calculateScale(float var1) {
        float var2 = 1.0F;
        if(var1 >= 2000.0F) {
            var2 = 0.6F;
        } else if(var1 > 400.0F) {
            var2 = 0.39999998F + (2000.0F - var1) / 1600.0F * 0.6F;
        }

        return var2;
    }

    private void updateAircraftLocation() {
        float var1 = this.calculateScale(this.aircraftDistance);
        float var2 = this.getMaxDistance();
        this.updateAircraftGimbalSizeAndHeading(var2, var1);
        this.updateCompassAnimateView(var2);
    }

    private void updateAircraftHeading() {
        if(this.aircraftImage != null) {
            this.aircraftImage.setRotation(this.aircraftHeading - this.phoneAzimuth);
        }

        if(this.gimbalYawImage != null) {
            this.gimbalYawImage.setRotation((float)this.gimbalHeading + this.aircraftHeading - this.phoneAzimuth);
        }

    }

    private void updateAircraftGimbalSizeAndHeading(float var1, float var2) {
        int var3 = this.getMeasuredWidth();
        float var4 = ((float)var3 - this.padding - (float)this.aircraftImage.getWidth()) / 2.0F;
        float var5 = ((float)var3 - this.padding - (float)this.aircraftImage.getHeight()) / 2.0F;
        float var6 = this.padding / 2.0F;
        double var7 = Math.toRadians((double)(this.aircraftAngle + this.phoneAzimuth));
        float var9;
        float var10;
        if(this.aircraftDistance >= var1) {
            var9 = (float)((double)(var6 + var4) + Math.cos(var7) * (double)var4);
            var10 = (float)((double)(var6 + var5) - Math.sin(var7) * (double)var5);
        } else {
            var9 = (float)((double)(var6 + var4) + (double)this.aircraftDistance * Math.cos(var7) * (double)var4 / (double)var1);
            var10 = (float)((double)(var6 + var5) - (double)this.aircraftDistance * Math.sin(var7) * (double)var5 / (double)var1);
        }

        this.aircraftImage.setX(var9);
        this.aircraftImage.setY(var10);
        this.aircraftImage.setScaleX(var2);
        this.aircraftImage.setScaleY(var2);
        this.gimbalYawImage.setX(this.aircraftImage.getX() + (float)(this.aircraftImage.getWidth() / 2) - (float)(this.gimbalYawImage.getWidth() / 2));
        this.gimbalYawImage.setY(this.aircraftImage.getY() + (float)(this.aircraftImage.getHeight() / 2) - (float)this.gimbalYawImage.getHeight());
        this.gimbalYawImage.setScaleX(var2);
        this.gimbalYawImage.setScaleY(var2);
    }

    private float getMaxDistance() {
        float var1 = this.aircraftDistance;
        if(this.aircraftDistance < this.rcHomeDistance) {
            var1 = this.rcHomeDistance;
        }

        if(var1 < 400.0F) {
            var1 = 400.0F;
        }

        return var1;
    }

    private void updateHomePointAndRC() {
        ImageView var1;
        if(this.centerType == 1) {
            var1 = this.homeImage;
            this.secondGPSImage = this.rcImage;
        } else {
            var1 = this.rcImage;
            this.secondGPSImage = this.homeImage;
        }

        var1.setVisibility(0);
        LayoutParams var2 = (LayoutParams)var1.getLayoutParams();
        var2.leftMargin = 0;
        var2.topMargin = 0;
        var1.setLayoutParams(var2);
        this.updateSecondGPSLocation();
    }

    private void updateSecondGPSLocation() {
        if(this.secondGPSImage != null) {
            if(this.centerType != 1) {
                this.secondGPSImage.setVisibility(0);
                int var1 = this.getMeasuredWidth();
                float var2 = ((float)var1 - this.padding - (float)this.secondGPSImage.getWidth()) / 2.0F;
                float var3 = ((float)var1 - this.padding - (float)this.secondGPSImage.getHeight()) / 2.0F;
                float var4 = this.padding / 2.0F;
                double var5 = Math.toRadians((double)(this.rcHomeAngle + this.phoneAzimuth));
                float var7 = this.getMaxDistance();
                float var8;
                float var9;
                if(this.rcHomeDistance == var7) {
                    var8 = (float)((double)(var4 + var2) + Math.cos(var5) * (double)var2);
                    var9 = (float)((double)(var4 + var3) - Math.sin(var5) * (double)var3);
                } else {
                    var8 = (float)((double)(var4 + var2) + (double)this.rcHomeDistance * Math.cos(var5) * (double)var2 / (double)var7);
                    var9 = (float)((double)(var4 + var3) - (double)this.rcHomeDistance * Math.sin(var5) * (double)var3 / (double)var7);
                }

                this.secondGPSImage.setX(var8);
                this.secondGPSImage.setY(var9);
            } else {
                this.secondGPSImage.setVisibility(8);
            }

        }
    }

    private void calculateAircraftAngleAndDistanceFromCenterLocation() {
        float[] var1;
        if(this.centerType == 1) {
            if(UiDF.b(this.homeLatitude) && UiDF.a(this.homeLongitude)) {
                var1 = UiDF.a(this.homeLatitude, this.homeLongitude, this.aircraftLatitude, this.aircraftLongitude, true);
                this.aircraftAngle = var1[0];
                this.aircraftDistance = var1[1];
            }
        } else if(this.centerType == 0 && UiDF.b(this.rcOrMobileLatitude) && UiDF.a(this.rcOrMobileLongitude)) {
            var1 = UiDF.a(this.rcOrMobileLatitude, this.rcOrMobileLongitude, this.aircraftLatitude, this.aircraftLongitude, true);
            this.aircraftAngle = var1[0];
            this.aircraftDistance = var1[1];
        }

    }

    private void calculateAngleAndDistanceBetweenRCAndHome() {
        if(this.centerType != 1) {
            float[] var1 = UiDF.a(this.rcOrMobileLatitude, this.rcOrMobileLongitude, this.homeLatitude, this.homeLongitude, false);
            this.rcHomeAngle = var1[0];
            this.rcHomeDistance = var1[1];
        }

    }

    private void updateAircraftInPitch(float var1) {
        float var2 = -var1 + 90.0F;
        int var3 = (int)(var2 * 100.0F / 180.0F);
        if(var3 < 0) {
            var3 = 0;
        } else if(var3 > 100) {
            var3 = 100;
        }

        if(this.compassSea != null) {
            int var4 = this.compassSea.getProgress();
            if(var4 != var3) {
                this.compassSea.setProgress(var3);
            }
        }

    }

    private void updateAircraftInRoll(float var1) {
        if(this.compassSea != null) {
            this.compassSea.setRotation(var1);
        }

    }

    private void updateGimbalHeading(float var1) {
        this.gimbalYawView.setYaw(var1);
    }

    private void updateCompassAnimateView(float var1) {
        if(this.compassAnimateView != null) {
            this.compassAnimateView.setVisibility(0);
            this.innerStaticCycles.setVisibility(8);
            this.compassAnimateView.setDistance(var1);
        }

    }

    private int getDisplayRotation() {
        if(null == this.display) {
            WindowManager var1 = (WindowManager)this.context.getSystemService("window");
            this.display = var1.getDefaultDisplay();
        }

        return this.display.getRotation();
    }
}
