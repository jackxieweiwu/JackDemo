package dji.midware.ui.internal.camera;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.ActionCallback;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.c.UiCC;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.internal.BInterDialog;
import dji.midware.ui.internal.DULSwitchButton;
import dji.midware.ui.internal.a.Inter_A;
import dji.midware.ui.internal.a.Inter_B;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULCameraOtherSettingListView extends InterCamera_A{
    private static final UiCC.a[] a;
    private DJIKey h;
    private DJIKey i;
    private DJIKey j;
    private DJIKey k;
    private boolean l;
    private SettingsDefinitions.FileIndexMode m;
    private SettingsDefinitions.AntiFlickerFrequency n;
    private boolean o;
    private UiCC p;
    private UiCC q;
    private UiCC r;
    private DJIKey s;
    private boolean t;
    private TypedArray u;
    private String[] v;

    public DULCameraOtherSettingListView(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public DULCameraOtherSettingListView(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public DULCameraOtherSettingListView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected void a() {
        this.b = a;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.u = this.getResources().obtainTypedArray(R.array.camera_anti_flicker_img_array);
        this.v = this.getResources().getStringArray(R.array.camera_file_index_name_array);
    }

    protected void b() {
        if(this.c != null) {
            this.p = this.c.c(0);
            this.q = this.c.c(1);
            this.r = this.c.c(2);
        }

    }

    public void initKey() {
        this.h = CameraKey.create("VideoCaptionEnabled");
        this.j = CameraKey.create("AntiFlickerFrequency");
        this.i = CameraKey.create("FileIndexMode");
        this.k = CameraKey.create("IsRecording");
        this.s = CameraKey.create("IsShootingIntervalPhoto");
        this.addDependentKey(this.h);
        this.addDependentKey(this.j);
        this.addDependentKey(this.i);
        this.addDependentKey(this.k);
        this.addDependentKey(this.j);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.h)) {
            this.o = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.j)) {
            this.n = (SettingsDefinitions.AntiFlickerFrequency)var1;
            this.a(this.n);
        } else if(var2.equals(this.i)) {
            this.m = (SettingsDefinitions.FileIndexMode)var1;
            this.a(this.m);
        } else if(var2.equals(this.k)) {
            this.l = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.s)) {
            this.t = ((Boolean)var1).booleanValue();
        }

    }

    private void a(SettingsDefinitions.AntiFlickerFrequency var1) {
        if(var1 != null && var1.value() < this.u.length()) {
            this.a(1, var1.value(), this.u.getResourceId(var1.value(), 0));
        }

    }

    private void a(SettingsDefinitions.FileIndexMode var1) {
        int var2 = var1.value();
        this.a(2, var2, 0);
        if(this.v != null && var2 < this.v.length) {
            this.c.c(2).b(this.v[var2]);
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.h)) {
            this.f();
        } else if(var1.equals(this.j)) {
            this.g();
        } else if(var1.equals(this.i)) {
            this.h();
        }

    }

    public void updateSelectedItem(View var1, int var2) {
        if(var1 instanceof DULSwitchButton) {
            DULSwitchButton var3 = (DULSwitchButton)var1;
            this.a(var3.a());
        } else {
            if(var2 != 1 && var2 != 2) {
                if(var2 == 3) {
                    this.k();
                } else if(var2 == 4) {
                    this.l();
                }
            } else {
                if(var2 == 1) {
                    this.g = new Inter_A(this.getContext());
                } else {
                    this.g = new Inter_B(this.getContext());
                }

                this.d();
            }

        }
    }

    private void a(final boolean var1) {
        if(KeyManager.getInstance() != null) {
            KeyManager.getInstance().setValue(this.h, Boolean.valueOf(var1), new SetCallback() {
                public void onSuccess() {
                    DJILog.d("DULCameraOtherSettingListView", "Camera video caption enabled " + var1 + " successfully");
                }

                public void onFailure(@NonNull DJIError var1x) {
                    DULCameraOtherSettingListView.this.f();
                }
            });
        }
    }

    private void f() {
        if(this.p != null) {
            this.p.a = this.o?1:0;
            this.c.notifyItemChanged(0);
        }

    }

    private void g() {
        if(this.q != null) {
            this.q.a = this.n.value();
            this.c.notifyItemChanged(1);
        }

    }

    private void h() {
        if(this.r != null) {
            this.r.a = this.m.value();
            this.c.notifyItemChanged(2);
        }

    }

    private void i() {
        if(KeyManager.getInstance() != null) {
            CameraKey var1 = CameraKey.create("restoreFactorySettings");
            KeyManager.getInstance().performAction(var1, new ActionCallback() {
                public void onSuccess() {
                    DJILog.d("DULCameraOtherSettingListView", "Camera reset setting successfully");
                }

                public void onFailure(@NonNull DJIError var1) {
                    DULCameraOtherSettingListView.this.a(R.string.camera_setting_reset_busy_title, var1.getDescription());
                    DJILog.d("DULCameraOtherSettingListView", "Failed to set reset Camera Setting");
                }
            }, new Object[0]);
        }
    }

    private void j() {
        if(KeyManager.getInstance() != null) {
            CameraKey var1 = CameraKey.create("FormatSDCard");
            KeyManager.getInstance().performAction(var1, new ActionCallback() {
                public void onSuccess() {
                    DJILog.d("DULCameraOtherSettingListView", "Camera reset setting successfully");
                }

                public void onFailure(@NonNull DJIError var1) {
                    DULCameraOtherSettingListView.this.a(R.string.camera_format_sd_card_busy_title, var1.getDescription());
                    DJILog.d("DULCameraOtherSettingListView", "Failed to set reset Camera Setting");
                }
            }, new Object[0]);
        }
    }

    private void a(int var1) {
        if(var1 == 3) {
            this.i();
        } else if(var1 == 4) {
            this.j();
        }

    }

    private void a(@StringRes int var1, String var2) {
        BInterDialog var3 = new BInterDialog(this.context);
        var3.a(1);
        var3.a(new BInterDialog.dialogInterfaceA() {
            public void onRightBtnClick(DialogInterface var1, int var2) {
            }

            public void onLeftBtnClick(DialogInterface var1, int var2) {
                var1.dismiss();
            }

            public void onCbChecked(DialogInterface var1, boolean var2, int var3) {
            }
        });
        var3.a(8, 0).d(8);
        var3.a(8, "");
        var3.a(this.context.getString(R.string.app_tip));
        var3.b(this.context.getString(var1) + ". " + var2);
        var3.c(8);
        var3.show();
    }

    private void k() {
        if(!this.l && !this.t) {
            this.b(3, this.getContext().getString(R.string.camera_setting_reset_camera_setting_confirm));
        } else {
            this.a(R.string.camera_setting_reset_busy_title, this.context.getString(R.string.camera_setting_reset_busy_tip));
        }

    }

    private void l() {
        this.b(4, this.getContext().getString(R.string.camera_setting_format_sdcard_confirm));
    }

    private void b(final int var1, String var2) {
        BInterDialog var3 = new BInterDialog (this.context);
        var3.a(1);
        var3.a(new BInterDialog.dialogInterfaceA () {
            public void onRightBtnClick(DialogInterface var1x, int var2) {
                var1x.dismiss();
                DULCameraOtherSettingListView.this.a(var1);
            }

            public void onLeftBtnClick(DialogInterface var1x, int var2) {
                var1x.dismiss();
            }

            public void onCbChecked(DialogInterface var1x, boolean var2, int var3) {
            }
        });
        var3.a(8, 0).d(8);
        var3.a(8, "");
        var3.a(this.context.getString(R.string.app_tip));
        var3.b(var2);
        var3.a();
        var3.show();
    }

    static {
        a = new UiCC.a[]{new UiCC.a(R.string.camera_video_caption, UiCC.enum_b.c), new UiCC.a(R.string.camera_anti_flick_name, UiCC.enum_b.a), new UiCC.a(R.string.camera_file_index_name, UiCC.enum_b.a), new UiCC.a(R.string.camera_setting_reset, UiCC.enum_b.b), new UiCC.a(R.string.camera_format_sd_card, UiCC.enum_b.b)};
    }
}
