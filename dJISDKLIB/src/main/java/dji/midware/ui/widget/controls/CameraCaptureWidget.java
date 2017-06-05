package dji.midware.ui.widget.controls;

import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.ActionCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.UiBaseCView;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBG;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDF;
import dji.midware.ui.d.UiDI;
import dji.midware.ui.d.UiDK;
import dji.midware.ui.internal.a.Inter_G;

/**
 * Created by jack_xie on 17-6-1.
 */

public class CameraCaptureWidget extends UiBaseCView {
    private static final String TAG = "CameraCaptureWidget";
    private static final int DEFAULT_NULL_RES = 0;
    private SettingsDefinitions.CameraMode cameraMode;
    private SettingsDefinitions.ShootPhotoMode shootPhotoMode;
    private SettingsDefinitions.PhotoAEBCount aebParam;
    private SettingsDefinitions.PhotoBurstCount photoBurstCount;
    private SettingsDefinitions.PhotoTimeIntervalSettings photoIntervalParam;
    private boolean isStoringPhoto;
    private boolean isShootingIntervalPhoto;
    private boolean isShootingPhotoEnabled;
    private Integer currentBackgroundResId;
    private Integer currentForegroundBackResId;
    private Integer currentForegroundTopResId;
    private int recordingTime;
    private boolean isRecording;
    private Animation savingAnim;
    private UiDI captureSound;
    private boolean isShootingPhoto;
    private TextView textviewVideoTime;
    protected ImageView imageForegroundTop;
    private UiCAB widgetAppearances;
    private DJIKey cameraModeKey;
    private DJIKey shootPhotoModeKey;
    private DJIKey photoAEBParamKey;
    private DJIKey photoBurstCountKey;
    private DJIKey photoIntervalParamKey;
    private DJIKey isStoringPhotoKey;
    private DJIKey isShootingPhotoEnabledKey;
    private DJIKey isShootingIntervalPhotoKey;
    private DJIKey isShootingPhotoKey;
    private DJIKey isRecordingKey;
    private DJIKey recordingTimeKey;

    public CameraCaptureWidget(Context var1) {
        super(var1, null, 0);
    }

    public CameraCaptureWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public CameraCaptureWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        imageBackground = (ImageButton)getViewById(R.id.image_button_background);
        imageForeground = (ImageView)getViewById(R.id.image_button_foreground);
        imageForegroundTop = (ImageView)getViewById(R.id.image_button_foreground_top);
        textviewVideoTime = (TextView)getViewById(R.id.textview_camera_controll_videotime);
        currentForegroundTopResId = Integer.valueOf(0);
        imageBackground.setOnClickListener(this);
        savingAnim = AnimationUtils.loadAnimation(var1, R.anim.storing_rotate);
        savingAnim.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation var1) {
                DJILog.d("CameraCaptureWidget", "savingAnim start", false, true);
            }

            public void onAnimationRepeat(Animation var1) {
            }

            public void onAnimationEnd(Animation var1) {
            }
        });
        captureSound = new UiDI(getContext());
    }

    protected UiCAB getWidgetAppearances() {
        if(widgetAppearances == null) {
            widgetAppearances = new UiCBG();
        }
        return widgetAppearances;
    }

    public void initKey() {
        cameraModeKey = CameraKey.create("Mode");
        addDependentKey(cameraModeKey);
        shootPhotoModeKey = CameraKey.create("ShootPhotoMode");
        addDependentKey(shootPhotoModeKey);
        photoAEBParamKey = CameraKey.create("PhotoAEBCount");
        addDependentKey(photoAEBParamKey);
        photoBurstCountKey = CameraKey.create("PhotoBurstCount");
        addDependentKey(photoBurstCountKey);
        photoIntervalParamKey = CameraKey.create("PhotoTimeIntervalSettings");
        addDependentKey(photoIntervalParamKey);
        isStoringPhotoKey = CameraKey.create("IsStoringPhoto");
        addDependentKey(isStoringPhotoKey);
        isShootingPhotoEnabledKey = CameraKey.create("IsShootPhotoEnabled");
        addDependentKey(isShootingPhotoEnabledKey);
        isShootingIntervalPhotoKey = CameraKey.create("IsShootingIntervalPhoto");
        addDependentKey(isShootingIntervalPhotoKey);
        isShootingPhotoKey = CameraKey.create("IsShootingPhoto");
        addDependentKey(isShootingPhotoKey);
        isRecordingKey = CameraKey.create("IsRecording");
        addDependentKey(isRecordingKey);
        recordingTimeKey = CameraKey.create("CurrentVideoRecordingTimeInSeconds");
        addDependentKey(recordingTimeKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(cameraModeKey)) {
            cameraMode = (SettingsDefinitions.CameraMode)var1;
            setCaptureBackground(cameraMode);
            setForegroundRes(shootPhotoMode);
        } else if(var2.equals(shootPhotoModeKey)) {
            shootPhotoMode = (SettingsDefinitions.ShootPhotoMode)(var1);
            setForegroundRes(shootPhotoMode);
        } else if(var2.equals(photoAEBParamKey)) {
            aebParam = (SettingsDefinitions.PhotoAEBCount)(var1);
            if(shootPhotoMode == SettingsDefinitions.ShootPhotoMode.AEB) {
                setForegroundRes(shootPhotoMode);
            }
        } else if(var2.equals(photoBurstCountKey)) {
            photoBurstCount = (SettingsDefinitions.PhotoBurstCount)(var1);
            if(shootPhotoMode == SettingsDefinitions.ShootPhotoMode.BURST) {
                setForegroundRes(shootPhotoMode);
            }
        } else if(var2.equals(photoIntervalParamKey)) {
            photoIntervalParam = (SettingsDefinitions.PhotoTimeIntervalSettings)(var1);
            if(shootPhotoMode == SettingsDefinitions.ShootPhotoMode.INTERVAL) {
                setForegroundRes(shootPhotoMode);
            }
        } else if(var2.equals(isStoringPhotoKey)) {
            isStoringPhoto = ((Boolean)(var1)).booleanValue();
            setCaptureBackground(cameraMode);
        } else if(var2.equals(isShootingIntervalPhotoKey)) {
            isShootingIntervalPhoto = ((Boolean)(var1)).booleanValue();
            setForegroundRes(shootPhotoMode);
        } else if(var2.equals(isShootingPhotoKey)) {
            isShootingPhoto = ((Boolean)(var1)).booleanValue();
        } else if(var2.equals(isShootingPhotoEnabledKey)) {
            isShootingPhotoEnabled = ((Boolean)(var1)).booleanValue();
        } else if(var2.equals(isRecordingKey)) {
            isRecording = ((Boolean)(var1)).booleanValue();
            setCaptureBackground(cameraMode);
        } else if(var2.equals(recordingTimeKey)) {
            recordingTime = ((Integer)(var1)).intValue();
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(cameraModeKey)) {
            this.onCameraModeChange(cameraMode);
        } else if(var1.equals(isShootingPhotoKey)) {
            this.onIsShootingPhotoEnable(isShootingPhoto);
        } else if(var1.equals(isRecordingKey)) {
            this.onIsRecordingEnable(isRecording);
        } else if(var1.equals(recordingTimeKey)) {
            this.onRecordingTimeChange(recordingTime);
        } else if(var1.equals(isStoringPhotoKey)) {
            this.onIsStoringPhotoEnable(isStoringPhoto);
        } else if(var1.equals(isShootingIntervalPhotoKey)) {
            this.onIsShootingIntervalPhotoEnable(isShootingIntervalPhoto);
        } else {
            this.updateBackgroundAndForeground();
        }

    }

    @MainThread
    private void onCameraModeChange(SettingsDefinitions.CameraMode var1) {
        if(var1 == null || var1 != SettingsDefinitions.CameraMode.SHOOT_PHOTO && var1 != SettingsDefinitions.CameraMode.RECORD_VIDEO) {
            setEnabled(false);
        } else {
            setEnabled(true);
            updateBackgroundAndForeground();
        }

    }

    @MainThread
    private void onIsShootingPhotoEnable(boolean var1) {
        if(var1) {
            captureSound.a(shootPhotoMode, getPictureNumber());
        }

    }

    @MainThread
    private void onIsRecordingEnable(boolean var1) {
        if(var1) {
            captureSound.a();
            textviewVideoTime.setEnabled(true);
        } else {
            captureSound.b();
        }

        if(var1) {
            textviewVideoTime.setVisibility(0);
        } else {
            textviewVideoTime.setVisibility(8);
        }

    }

    @MainThread
    private void onRecordingTimeChange(int var1) {
        String var2 = this.formatVideoTime(Integer.valueOf(var1));
        textviewVideoTime.setText(var2);
    }

    @MainThread
    private void onIsStoringPhotoEnable(boolean var1) {
        if(var1) {
            imageBackground.startAnimation(this.savingAnim);
        } else {
            imageBackground.clearAnimation();
            savingAnim.cancel();
            savingAnim.reset();
            updateBackgroundAndForeground();
        }

    }

    @MainThread
    private void onIsShootingIntervalPhotoEnable(boolean var1) {
        this.updateBackgroundAndForeground();
    }

    private void updateBackgroundAndForeground() {
        if(currentBackgroundResId != null) {
            imageBackground.setBackgroundResource(currentBackgroundResId.intValue());
        }

        if(currentForegroundBackResId != null) {
            imageForeground.setImageResource(currentForegroundBackResId.intValue());
            if(cameraMode == SettingsDefinitions.CameraMode.SHOOT_PHOTO && currentForegroundTopResId.intValue() != 0) {
                imageForegroundTop.setVisibility(0);
                imageForegroundTop.setImageDrawable(UiDK.a(context, currentForegroundTopResId.intValue()));
            } else {
                imageForegroundTop.setVisibility(4);
            }
        }

    }

    private String formatVideoTime(Integer var1) {
        if(var1 == null) {
            var1 = Integer.valueOf(0);
        }

        int[] var2 = UiDF.a(var1.intValue());
        return this.getContext().getString(R.string.videotime, new Object[]{Integer.valueOf(var2[2]), Integer.valueOf(var2[1]), Integer.valueOf(var2[0])});
    }

    private int getPictureNumber() {
        if(shootPhotoMode == SettingsDefinitions.ShootPhotoMode.AEB) {
            if(aebParam != null) {
                return aebParam.value();
            }
        } else if(shootPhotoMode == SettingsDefinitions.ShootPhotoMode.BURST && photoBurstCount != null) {
            return photoBurstCount.value();
        }

        return 0;
    }

    private void setCaptureBackground(SettingsDefinitions.CameraMode var1) {
        if(var1 != null) {
            switch(var1.value()) {
                case 1:
                    if(isStoringPhoto) {
                        currentBackgroundResId = Integer.valueOf(R.drawable.btn_cam_capture_progress);
                    } else {
                        currentBackgroundResId = Integer.valueOf(R.drawable.btn_cam_capture_border);
                    }
                    break;
                case 2:
                    if(isRecording) {
                        currentBackgroundResId = Integer.valueOf(R.drawable.selector_camera_control_stopvideo);
                    } else {
                        currentBackgroundResId = Integer.valueOf(R.drawable.selector_camera_control_recordvideo);
                    }
            }

        }
    }

    private void setForegroundRes(SettingsDefinitions.ShootPhotoMode var1) {
        if(this.cameraMode == SettingsDefinitions.CameraMode.RECORD_VIDEO) {
            this.currentForegroundBackResId = Integer.valueOf(0);
        } else if(var1 != null) {
            this.currentForegroundBackResId = Integer.valueOf(R.drawable.btn_cam_capture_released);
            this.currentForegroundTopResId = Integer.valueOf(0);
            int var2;
            switch(var1.value()) {
                case 1:
                    break;
                case 2:
                    this.currentForegroundBackResId = Integer.valueOf(R.drawable.camera_controll_takephoto_icon_hdr);
                    break;
                case 3:
                    if(this.isShootingIntervalPhoto) {
                        this.currentForegroundBackResId = Integer.valueOf(R.drawable.selector_camera_control_stopintervalphoto);
                        this.currentForegroundTopResId = Integer.valueOf(0);
                    } else {
                        this.currentBackgroundResId = Integer.valueOf(R.drawable.btn_cam_capture_border);
                        this.currentForegroundTopResId = Integer.valueOf(Inter_G.a(SettingsDefinitions.ShootPhotoMode.INTERVAL.value(), this.photoIntervalParam.getTimeIntervalInSeconds()));
                    }
                    break;
                case 4:
                    if(this.photoBurstCount != null) {
                        var2 = this.photoBurstCount.value();
                        this.currentForegroundTopResId = Integer.valueOf(Inter_G.a(SettingsDefinitions.ShootPhotoMode.BURST.value(), var2));
                    }
                    break;
                case 5:
                    if(this.aebParam != null) {
                        var2 = this.aebParam.value();
                        this.currentForegroundTopResId = Integer.valueOf(Inter_G.a(SettingsDefinitions.ShootPhotoMode.AEB.value(), var2));
                    }
                    break;
                default:
                    this.currentForegroundTopResId = Integer.valueOf(0);
            }

        }
    }

    public void onClick(View var1) {
        if(this.cameraMode == SettingsDefinitions.CameraMode.SHOOT_PHOTO) {
            this.onClickPhoto();
        } else if(this.cameraMode == SettingsDefinitions.CameraMode.RECORD_VIDEO) {
            this.onClickVideo();
        }

    }

    private void onClickVideo() {
        if(KeyManager.getInstance() != null) {
            CameraKey var1;
            if(!this.isRecording) {
                var1 = CameraKey.create("StartRecordVideo");
                KeyManager.getInstance().performAction(var1, new ActionCallback() {
                    public void onSuccess() {
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("CameraCaptureWidget", "Failed to start record video: " + var1.getDescription());
                    }
                }, new Object[0]);
            } else {
                var1 = CameraKey.create("StopRecordVideo");
                KeyManager.getInstance().performAction(var1, new ActionCallback() {
                    public void onSuccess() {
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("CameraCaptureWidget", "Failed to stop record video: " + var1.getDescription());
                    }
                }, new Object[0]);
            }

        }
    }

    private void onClickPhoto() {
        if(KeyManager.getInstance() != null) {
            CameraKey var1;
            if(this.shootPhotoMode == SettingsDefinitions.ShootPhotoMode.INTERVAL && this.isShootingIntervalPhoto) {
                this.captureSound.b();
                var1 = CameraKey.create("StopShootPhoto");
                KeyManager.getInstance().performAction(var1, new ActionCallback() {
                    public void onSuccess() {
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("CameraCaptureWidget", "Failed to stop shoot photo: " + var1.getDescription());
                    }
                }, new Object[0]);
            } else {
                var1 = CameraKey.create("StartShootPhoto");
                KeyManager.getInstance().performAction(var1, new ActionCallback() {
                    public void onSuccess() {
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("CameraCaptureWidget", "Failed to start shoot photo:" + var1.getDescription());
                    }
                }, new Object[0]);
            }

        }
    }
}
