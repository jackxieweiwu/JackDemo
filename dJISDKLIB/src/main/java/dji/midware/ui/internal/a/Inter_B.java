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
import dji.midware.ui.d.UiDG;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_B extends UiBaseMView {
    private int[] a;
    private DJIKey djiKeyb;
    private int c;

    public Inter_B(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.itemNameArray = this.getResources().getStringArray(R.array.camera_file_index_name_array);
        this.itemImageIdArray = null;
        this.a = this.getResources().getIntArray(R.array.camera_file_index_default_value_array);
        this.initAdapter(this.a);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_file_index_name);
        }

    }

    public void initKey() {
        this.djiKeyb = CameraKey.create("FileIndexMode");
        this.addDependentKey(djiKeyb);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(djiKeyb)) {
            SettingsDefinitions.FileIndexMode var3 = (SettingsDefinitions.FileIndexMode)var1;
            this.c = this.adapter.b(var3.value());
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(djiKeyb)) {
            this.adapter.a(this.c);
        }

    }

    public void updateSelectedItem(View var1, int var2) {
        if(KeyManager.getInstance() != null) {
            UiCC var3 = this.adapter.c(var2);
            if(this.a != null) {
                SettingsDefinitions.FileIndexMode var4 = SettingsDefinitions.FileIndexMode.find(var3.a);
                KeyManager.getInstance().setValue(djiKeyb, var4, new SetCallback() {
                    public void onSuccess() {
                        UiDG.a().a(djiKeyb, Inter_B.this);
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("DULCameraFileIndexListWidget", "Failed to set camera file format");
                        UiDG.a().a(djiKeyb, Inter_B.this);
                    }
                });
            }

        }
    }
}
