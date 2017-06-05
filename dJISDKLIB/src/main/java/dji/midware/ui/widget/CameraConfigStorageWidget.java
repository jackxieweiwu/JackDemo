package dji.midware.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import dji.common.camera.ResolutionAndFrameRate;
import dji.common.camera.SettingsDefinitions;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBT;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDD;
import dji.midware.ui.d.UiDF;

/**
 * Created by jack_xie on 17-6-1.
 */

public class CameraConfigStorageWidget extends DULFrameLayout {
    private TextView cardInfoValue;
    private TextView imageFormatValue;
    private TextView cardInfoTitle;
    private CameraKey videoResAndFrameRateKey;
    private CameraKey sdcardAvailableRecordingTimeKey;
    private CameraKey cameraModeKey;
    private CameraKey photoFileFormatKey;
    private CameraKey avaiablePhotoCountKey;
    private ResolutionAndFrameRate resolutionAndFrameRate;
    private int secondLeftInSd;
    private SettingsDefinitions.CameraMode cameraMode;
    private long availablePhotoCount;
    private SettingsDefinitions.PhotoFileFormat photoFileFormat;
    private UiCBT widgetAppearances;

    public CameraConfigStorageWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CameraConfigStorageWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CameraConfigStorageWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.resolutionAndFrameRate = null;
        this.secondLeftInSd = 0;
        UiDA.b();
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.cardInfoValue = (TextView)this.getViewById(R.id.TextView_card_info_value);
        this.imageFormatValue = (TextView)this.getViewById(R.id.TextView_image_format_value);
        this.cardInfoTitle = (TextView)this.getViewById(R.id.TextView_card_info_title);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBT();
        }

        return this.widgetAppearances;
    }

    public void initKey() {
        this.videoResAndFrameRateKey = CameraKey.create("ResolutionFrameRate");
        this.sdcardAvailableRecordingTimeKey = CameraKey.create("SDCardAvailableRecordingTimeInSeconds");
        this.cameraModeKey = CameraKey.create("Mode");
        this.photoFileFormatKey = CameraKey.create("PhotoFileFormat");
        this.avaiablePhotoCountKey = CameraKey.create("SDCardAvailableCaptureCount");
        this.addDependentKey(this.videoResAndFrameRateKey);
        this.addDependentKey(this.sdcardAvailableRecordingTimeKey);
        this.addDependentKey(this.cameraModeKey);
        this.addDependentKey(this.photoFileFormatKey);
        this.addDependentKey(this.avaiablePhotoCountKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.videoResAndFrameRateKey)) {
            this.resolutionAndFrameRate = (ResolutionAndFrameRate)var1;
        } else if(var2.equals(this.sdcardAvailableRecordingTimeKey)) {
            this.secondLeftInSd = ((Integer)var1).intValue();
        } else if(var2.equals(this.cameraModeKey)) {
            this.cameraMode = (SettingsDefinitions.CameraMode)var1;
        } else if(var2.equals(this.photoFileFormatKey)) {
            this.photoFileFormat = (SettingsDefinitions.PhotoFileFormat)var1;
        } else if(var2.equals(this.avaiablePhotoCountKey)) {
            this.availablePhotoCount = ((Long)var1).longValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.cameraMode != null && this.cameraMode == SettingsDefinitions.CameraMode.SHOOT_PHOTO) {
            this.imageFormatValue.setText(this.convertPhotoFileFormatToString(this.photoFileFormat));
            this.cardInfoValue.setText(Long.toString(this.availablePhotoCount));
            this.cardInfoTitle.setText(this.getResources().getString(R.string.card_title_capacity));
        } else {
            if(this.resolutionAndFrameRate != null) {
                this.imageFormatValue.setText(this.convertResolutionAndFrameRateToString(this.resolutionAndFrameRate.getResolution(), this.resolutionAndFrameRate.getFrameRate()));
            }

            this.cardInfoValue.setText(this.convertSecondToTimeString(this.secondLeftInSd));
            this.cardInfoTitle.setText(this.getResources().getString(R.string.card_title_time));
        }

    }

    private String convertPhotoFileFormatToString(SettingsDefinitions.PhotoFileFormat var1) {
        String var2 = "";
        if(var1 != null) {
            if(var1.value() == SettingsDefinitions.PhotoFileFormat.RAW.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_raw);
            } else if(var1.value() == SettingsDefinitions.PhotoFileFormat.JPEG.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_jpeg);
            } else if(var1.value() == SettingsDefinitions.PhotoFileFormat.RAW_AND_JPEG.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_jpegraw);
            } else if(var1.value() == SettingsDefinitions.PhotoFileFormat.TIFF_14_BIT.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_tiff);
            } else if(var1.value() == SettingsDefinitions.PhotoFileFormat.RADIOMETRIC_JPEG.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_radiometic_jpeg);
            } else if(var1.value() == SettingsDefinitions.PhotoFileFormat.TIFF_14_BIT_LINEAR_LOW_TEMP_RESOLUTION.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_low_tiff);
            } else if(var1.value() == SettingsDefinitions.PhotoFileFormat.TIFF_14_BIT_LINEAR_HIGH_TEMP_RESOLUTION.value()) {
                var2 = this.getResources().getString(R.string.camera_picture_format_high_tiff);
            } else {
                var2 = var1.toString();
            }
        }

        return var2;
    }

    private String convertSecondToTimeString(int var1) {
        int[] var2 = UiDF.a(var1);
        return this.getContext().getString(R.string.videotime, new Object[]{Integer.valueOf(var2[2]), Integer.valueOf(var2[1]), Integer.valueOf(var2[0])});
    }

    private String convertResolutionAndFrameRateToString(SettingsDefinitions.VideoResolution var1, SettingsDefinitions.VideoFrameRate var2) {
        String var3 = UiDD.a(var1);
        String var4 = UiDD.a(var2);
        return var3 + "/" + var4;
    }
}
