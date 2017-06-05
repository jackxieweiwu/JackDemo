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
import dji.midware.ui.c.UiCC;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_A extends UiBaseMView {
    private int[] intA;
    private DJIKey b;
    private DJIKey c;
    private SettingsDefinitions.AntiFlickerFrequency d;

    public Inter_A(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.itemNameArray = this.getResources().getStringArray(R.array.camera_anti_flicker_name_array);
        this.itemImageIdArray = this.getResources().obtainTypedArray(R.array.camera_anti_flicker_img_array);
        this.intA = this.getResources().getIntArray(R.array.camera_anti_flicker_default_value_array);
        this.initAdapter(intA);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_anti_flick_name);
        }

    }

    public void initKey() {
        this.b = CameraKey.create("CameraAntiFlickerRange");
        this.c = CameraKey.create("AntiFlickerFrequency");
        this.addDependentKey(this.b);
        this.addDependentKey(this.c);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.c)) {
            this.d = (SettingsDefinitions.AntiFlickerFrequency)var1;
        } else if(var2.equals(this.b)) {
            SettingsDefinitions.AntiFlickerFrequency[] var3 = (SettingsDefinitions.AntiFlickerFrequency[])((SettingsDefinitions.AntiFlickerFrequency[])var1);
            this.intA = new int[var3.length];

            for(int var4 = 0; var4 < var3.length; ++var4) {
                this.intA[var4] = ((SettingsDefinitions.AntiFlickerFrequency)var3[var4]).value();
            }
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.b)) {
            this.initAdapter(intA);
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
        if(KeyManager.getInstance() != null) {
            UiCC var3 = this.adapter.c(var2);
            if(intA != null) {
                final SettingsDefinitions.AntiFlickerFrequency var4 = SettingsDefinitions.AntiFlickerFrequency.find(var3.a);
                KeyManager.getInstance().setValue(this.c, var4, new SetCallback() {
                    public void onSuccess() {
                        DJILog.d("DULCameraAntiFlickerListWidget", "Camera setting " + var4.name() + " successfully");
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("DULCameraAntiFlickerListWidget", "Failed to set camera AntiFlicker " + var1.getDescription());
                    }
                });
            }

        }
    }
}
