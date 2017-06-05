package dji.midware.ui.internal.a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.UiBaseMView;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_C extends UiBaseMView {
    private int[] a;
    private DJIKey b;
    private DJIKey c;
    private SettingsDefinitions.DigitalFilter d;

    public Inter_C(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.itemNameArray = this.getResources().getStringArray(R.array.camera_filter_type);
        this.a = this.getResources().getIntArray(R.array.camera_filter_default);
        this.initAdapter(this.a);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_filter_name);
        }

    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.adapter.a(0);
    }

    public void initKey() {
        this.b = CameraKey.create("DigitalFilterRange");
        this.c = CameraKey.create("DigitalFilter");
        this.addDependentKey(this.b);
        this.addDependentKey(this.c);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.c)) {
            this.d = (SettingsDefinitions.DigitalFilter)var1;
        } else if(var2.equals(this.b)) {
            SettingsDefinitions.DigitalFilter[] var3 = (SettingsDefinitions.DigitalFilter[])((SettingsDefinitions.DigitalFilter[])var1);
            this.a = new int[var3.length];

            for(int var4 = 0; var4 < var3.length; ++var4) {
                this.a[var4] = ((SettingsDefinitions.DigitalFilter)var3[var4]).value();
            }
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.b)) {
            this.initAdapter(this.a);
            this.a();
        } else if(var1.equals(this.c)) {
            this.a();
        }

    }

    private void a() {
        if(this.d != null) {
            int var1 = this.adapter.b(this.d.value());
            this.adapter.a(var1);
        }

    }

    public void updateSelectedItem(View var1, int var2) {
        if(this.a != null) {
            final SettingsDefinitions.DigitalFilter var3 = SettingsDefinitions.DigitalFilter.find(this.a[var2]);
            KeyManager.getInstance().setValue(this.c, var3, new SetCallback() {
                public void onSuccess() {
                    DJILog.d("DULCameraFilterListWidget", "Camera ISO " + var3.name() + " set successfully");
                }

                public void onFailure(@NonNull DJIError var1) {
                    DJILog.d("DULCameraFilterListWidget", "Failed to set Camera Exposure Mode");
                }
            });
        }

    }
}
