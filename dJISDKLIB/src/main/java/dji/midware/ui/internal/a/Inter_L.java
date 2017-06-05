package dji.midware.ui.internal.a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dji.common.camera.SettingsDefinitions;
import dji.common.camera.WhiteBalance;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.UiBaseJView;
import dji.midware.ui.c.UiCA;
import dji.midware.ui.c.UiCB;
import dji.midware.ui.c.a.UiCAB;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_L extends UiBaseJView {
    private int[] l;
    private DJIKey m;
    private DJIKey n;
    private DJIKey o;
    private int[] p;
    private UiCB q;
    private WhiteBalance r;

    public Inter_L(Context var1) {
        super(var1);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.g = this.getResources().getStringArray(R.array.camera_white_balance_name_array);
        this.h = this.getResources().obtainTypedArray(R.array.camera_white_balance_img_array);
        this.l = this.getResources().getIntArray(R.array.camera_white_balance_default_value_array);
        this.p = this.getResources().getIntArray(R.array.camera_white_balance_Interval_value_boundary);
        this.b(this.l, this.p);
        this.q = this.a.a(SettingsDefinitions.WhiteBalancePreset.AUTO.value());
        this.a.a(this.q);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_white_balance_name);
        }

    }

    private void b(int[] var1, int[] var2) {
        this.e = 2147483647;
        this.f = 2147483647;
        this.a.a(this.a(var1, var2));
        if(this.i != null && this.i.isShown()) {
            this.i.setVisibility(8);
        }

    }

    protected List<UiCB> a(int[] var1, int[] var2) {
        if(var1 == null) {
            return null;
        } else {
            ArrayList var3 = new ArrayList();
            int[] var4 = var1;
            int var5 = var1.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                int var7 = var4[var6];
                UiCB var8 = new UiCB();
                var8.d = var7;
                var8.b = this.g[var8.d];
                var8.a = this.h.getResourceId(var8.d, 0);
                if(var8.d == SettingsDefinitions.WhiteBalancePreset.CUSTOM.value() && var2 != null) {
                    var8.f = 2;
                    UiCA var9 = new UiCA();
                    var9.f = var2[0];
                    var9.g = var2[1];
                    var9.c = var7;
                    var9.b = var2[0];
                    var9.e = "00K";
                    var8.uiCAArrayList.add(var9);
                }

                var3.add(var8);
            }

            return var3;
        }
    }

    public void initKey() {
        this.m = CameraKey.create("WhiteBalancePresentRange");
        this.o = CameraKey.create("WhiteBalance");
        this.n = CameraKey.create("WhiteBalanceCustomColorTemperatureRange");
        this.addDependentKey(this.m);
        this.addDependentKey(this.n);
        this.addDependentKey(this.o);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.n)) {
            this.p = (int[])((int[])var1);
        } else if(var2.equals(this.m)) {
            SettingsDefinitions.WhiteBalancePreset[] var3 = (SettingsDefinitions.WhiteBalancePreset[])((SettingsDefinitions.WhiteBalancePreset[])var1);
            this.l = new int[var3.length];

            for(int var4 = 0; var4 < var3.length; ++var4) {
                this.l[var4] = ((SettingsDefinitions.WhiteBalancePreset)var3[var4]).value();
            }
        } else if(var2.equals(this.o)) {
            this.r = (WhiteBalance)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.m)) {
            this.b(this.l, this.p);
            this.a();
        } else if(var1.equals(this.o)) {
            this.a();
        }

    }

    private void a() {
        if(this.r != null && this.r.getWhiteBalancePreset() != null) {
            this.q = this.a.a(this.r.getWhiteBalancePreset().value());
            if(this.q != null) {
                this.a.a(this.q);
            }
        }

    }

    protected void a(final int var1, final int var2) {
        if(KeyManager.getInstance() != null) {
            if(this.l != null) {
                UiCB var3 = this.a.a(var1);
                this.a.a(var3);
                SettingsDefinitions.WhiteBalancePreset var4 = SettingsDefinitions.WhiteBalancePreset.find(var1);
                WhiteBalance var5;
                if(var4 == SettingsDefinitions.WhiteBalancePreset.CUSTOM) {
                    var5 = new WhiteBalance(var2);
                } else {
                    var5 = new WhiteBalance(var4);
                }

                KeyManager.getInstance().setValue(this.o, var5, new SetCallback() {
                    public void onSuccess() {
                        a(e, var1, var2);
                        e = var1;
                    }

                    public void onFailure(@NonNull DJIError var1x) {
                        DJILog.d("DULCameraWhiteBalanceListWidget", "Failed to set camera white balance");
                    }
                });
            }

        }
    }

    protected boolean a(ExpandableListView var1, View var2, int var3, long var4) {
        UiCB var6 = (UiCB)this.a.getGroup(var3);
        if(var6.d != this.e) {
            if(var6.d == SettingsDefinitions.WhiteBalancePreset.CUSTOM.value() && (var6.e < this.p[0] || var6.e > this.p[1])) {
                if(this.r != null && this.r.getWhiteBalancePreset() == SettingsDefinitions.WhiteBalancePreset.CUSTOM) {
                    var6.e = this.r.getColorTemperature();
                } else {
                    var6.e = this.p[0];
                }
            }

            this.a(var6.d, var6.e);
        }

        return true;
    }

    protected void a(UiCB var1, UiCA var2) {
        this.a(var2.c, var2.b);
        this.e = var2.c;
        this.f = var2.b;
    }
}
