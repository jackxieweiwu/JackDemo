package dji.midware.ui.internal.a;

import android.content.Context;
import android.content.DialogInterface;
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
import dji.midware.ui.internal.BInterDialog;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_K extends UiBaseMView {
    private DJIKey a;
    private int[] b;
    private int c;

    public Inter_K(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.itemNameArray = this.getResources().getStringArray(R.array.camera_video_standard_name_array);
        this.itemImageIdArray = this.getResources().obtainTypedArray(R.array.camera_video_standard_img_array);
        this.b = this.getResources().getIntArray(R.array.camera_video_standard_default_value_array);
        this.initAdapter(this.b);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_video_standard_name);
        }

    }

    public void initKey() {
        this.a = CameraKey.create("VideoStandard");
        this.addDependentKey(this.a);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.a)) {
            SettingsDefinitions.VideoStandard var3 = (SettingsDefinitions.VideoStandard)var1;
            this.c = this.adapter.b(var3.value());
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.a)) {
            this.adapter.a(this.c);
        }

    }

    private void a(String var1, final int var2) {
        Context var3 = this.getContext();
        BInterDialog var4 = new BInterDialog(var3);
        var4.a(1);
        var4.a(new BInterDialog.dialogInterfaceA() {
            public void onRightBtnClick(DialogInterface var1, int var2x) {
                var1.dismiss();
                interKA(var2);
            }

            public void onLeftBtnClick(DialogInterface var1, int var2x) {
                var1.dismiss();
            }

            public void onCbChecked(DialogInterface var1, boolean var2x, int var3) {
            }
        });
        var4.a(8, 0).d(8);
        var4.a(8, "");
        var4.a(var3.getString(R.string.camera_video_standard_setting_tip));
        var4.b(var3.getString(R.string.camera_video_standard_setting_tip_desc, new Object[]{var1}));
        var4.show();
    }

    public void updateSelectedItem(View var1, int var2) {
        UiCC var3 = this.adapter.c(var2);
        this.a(this.itemNameArray[var3.a], var2);
    }

    private void interKA(int var1) {
        if(KeyManager.getInstance() != null) {
            UiCC var2 = this.adapter.c(var1);
            var2.a(true);
            if(this.b != null) {
                final SettingsDefinitions.VideoStandard var3 = SettingsDefinitions.VideoStandard.find(var2.a);
                KeyManager.getInstance().setValue(this.a, var3, new SetCallback() {
                    public void onSuccess() {
                        DJILog.d("DULCameraVideoStandardListWidget", "Camera setting " + var3.name() + " successfully");
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DJILog.d("DULCameraVideoStandardListWidget", "Failed to set camera file format");
                    }
                });
            }

        }
    }
}
