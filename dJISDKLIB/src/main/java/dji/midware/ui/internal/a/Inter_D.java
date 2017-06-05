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
 * Created by root on 17-6-1.
 */

public class Inter_D extends UiBaseMView {
    private int[] a;
    private DJIKey b;
    private DJIKey c;
    private SettingsDefinitions.PhotoFileFormat d;

    public Inter_D(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.itemNameArray = this.getResources().getStringArray(R.array.camera_picture_format_array);
        this.itemImageIdArray = this.getResources().obtainTypedArray(R.array.camera_picture_format_img_res_array);
        this.a = this.getResources().getIntArray(R.array.camera_picture_format_value_array);
        this.initAdapter(this.a);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_image_format_name);
        }

    }

    public void initKey() {
        this.b = CameraKey.create("PhotoFileFormatRange");
        this.c = CameraKey.create("PhotoFileFormat");
        this.addDependentKey(this.b);
        this.addDependentKey(this.c);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.c)) {
            this.d = (SettingsDefinitions.PhotoFileFormat)var1;
        } else if(var2.equals(this.b)) {
            this.a((SettingsDefinitions.PhotoFileFormat[])((SettingsDefinitions.PhotoFileFormat[])var1));
        }

    }

    private void a(Object[] var1) {
        if(var1 != null && var1.length > 0) {
            this.a = new int[var1.length];

            for(int var2 = 0; var2 < var1.length; ++var2) {
                this.a[var2] = ((SettingsDefinitions.PhotoFileFormat)var1[var2]).value();
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
        UiCC var3 = this.adapter.c(var2);
        if(this.a != null) {
            final SettingsDefinitions.PhotoFileFormat var4 = SettingsDefinitions.PhotoFileFormat.find(var3.a);
            KeyManager.getInstance().setValue(this.c, var4, new SetCallback() {
                public void onSuccess() {
                    DJILog.d("CameraImageFormatListWidget", "Camera setting " + var4.name() + " successfully");
                }

                public void onFailure(@NonNull DJIError var1) {
                    DJILog.d("CameraImageFormatListWidget", "Failed to set camera file format");
                }
            });
        }

    }
}
