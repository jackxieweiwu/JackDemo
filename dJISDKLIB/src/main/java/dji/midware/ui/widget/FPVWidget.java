package dji.midware.ui.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;

import java.util.List;

import dji.keysdk.DJIKey;
import dji.midware.ui.base.UiBaseFView;
import dji.midware.ui.d.UiDA;
import dji.sdk.base.BaseProduct;
import dji.sdk.camera.Camera;
import dji.sdk.camera.VideoFeeder;
import dji.sdk.codec.DJICodecManager;
import dji.sdk.sdkmanager.DJISDKManager;

/**
 * Created by jack_xie on 17-6-1.
 */

public class FPVWidget extends UiBaseFView implements TextureView.SurfaceTextureListener {
    private static final String TAG = "DULFpvWidget";
    DJICodecManager codecManager;
    VideoFeeder.VideoDataCallback videoDataCallback;
    boolean doneSettingCallback;

    public FPVWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public FPVWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public FPVWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.codecManager = null;
        this.videoDataCallback = null;
        this.init();
    }

    private void init() {
        if(!this.isInEditMode()) {
            this.setSurfaceTextureListener(this);
            this.videoDataCallback = new VideoFeeder.VideoDataCallback() {
                public void onReceive(byte[] var1, int var2) {
                    if(FPVWidget.this.codecManager != null) {
                        FPVWidget.this.codecManager.sendDataToDecoder(var1, var2);
                    }

                }
            };
            UiDA.b();
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture var1, int var2, int var3) {
        if(this.codecManager == null) {
            this.codecManager = new DJICodecManager(this.getContext(), var1, var2, var3);
        }

    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture var1, int var2, int var3) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture var1) {
        if(this.codecManager != null) {
            this.codecManager.cleanSurface();
            this.codecManager = null;
        }

        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture var1) {
    }

    private void registerLiveVideoKey() {
        if(!this.doneSettingCallback && this.videoDataCallback != null) {
            BaseProduct var1 = DJISDKManager.getInstance().getProduct();
            if(var1 != null) {
                Camera var2 = var1.getCamera();
                List var3 = VideoFeeder.getInstance().getVideoFeeds();
                if(var3 != null && !var3.isEmpty()) {
                    ((VideoFeeder.VideoFeed)VideoFeeder.getInstance().getVideoFeeds().get(0)).setCallback(this.videoDataCallback);
                }
            }
        }

    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        this.doneSettingCallback = false;
    }

    public void initKey() {
    }

    public void transformValue(Object var1, DJIKey var2) {
        this.registerLiveVideoKey();
    }

    public List<DJIKey> getDependentKeys() {
        return null;
    }

    public void registerDependentKeys() {
    }
}
