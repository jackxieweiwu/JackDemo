package dji.midware.ui.internal.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import dji.common.camera.PhotoTimeLapseSettings;
import dji.common.camera.SettingsDefinitions;
import dji.common.camera.WhiteBalance;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.midware.R;
import dji.midware.ui.c.UiCC;
import dji.midware.ui.internal.a.Inter_C;
import dji.midware.ui.internal.a.Inter_D;
import dji.midware.ui.internal.a.Inter_E;
import dji.midware.ui.internal.a.Inter_F;
import dji.midware.ui.internal.a.Inter_G;
import dji.midware.ui.internal.a.Inter_H;
import dji.midware.ui.internal.a.Inter_L;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULCameraPhotoSettingListView extends InterCamera_A {
    public static final UiCC.a[] a;
    private DJIKey h;
    private DJIKey i;
    private DJIKey j;
    private DJIKey k;
    private DJIKey l;
    private DJIKey m;
    private String[] n;
    private TypedArray o;
    private TypedArray p;
    private TypedArray q;
    private DJIKey r;
    private DJIKey s;
    private DJIKey t;
    private DJIKey u;
    private SettingsDefinitions.PhotoTimeIntervalSettings v;
    private SettingsDefinitions.PhotoAEBCount w;
    private SettingsDefinitions.PhotoBurstCount x;
    private PhotoTimeLapseSettings y;
    private SettingsDefinitions.ShootPhotoMode z;

    public DULCameraPhotoSettingListView(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public DULCameraPhotoSettingListView(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public DULCameraPhotoSettingListView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected void a() {
        this.b = a;
        this.n = this.getResources().getStringArray(R.array.camera_filter_type);
        this.o = this.getResources().obtainTypedArray(R.array.camera_picture_format_img_res_array);
        this.p = this.getResources().obtainTypedArray(R.array.camera_photo_aspect_ratio_img_array);
        this.q = this.getResources().obtainTypedArray(R.array.camera_white_balance_img_array);
    }

    protected void b() {
        this.a(SettingsDefinitions.ShootPhotoMode.SINGLE);
        this.a(SettingsDefinitions.PhotoAspectRatio.RATIO_4_3);
        this.a(SettingsDefinitions.PhotoFileFormat.JPEG);
        this.a(SettingsDefinitions.WhiteBalancePreset.AUTO, 0);
        this.a(SettingsDefinitions.PictureStylePresetType.STANDARD.value());
        this.a(SettingsDefinitions.DigitalFilter.NONE);
    }

    public void updateSelectedItem(View var1, int var2) {
        switch(var2) {
            case 0:
                this.g = new Inter_F(this.getContext());
                break;
            case 1:
                this.g = new Inter_E(this.getContext());
                break;
            case 2:
                this.g = new Inter_D(this.getContext());
                break;
            case 3:
                this.g = new Inter_L(this.getContext());
                break;
            case 4:
                this.g = new Inter_H(this.getContext());
                break;
            case 5:
                this.g = new Inter_C(this.getContext());
                break;
            default:
                this.g = null;
        }

        this.d();
    }

    public void initKey() {
        this.h = CameraKey.create("ShootPhotoMode");
        this.i = CameraKey.create("PhotoAspectRatio");
        this.j = CameraKey.create("PhotoFileFormat");
        this.k = CameraKey.create("WhiteBalance");
        this.l = CameraKey.create("PictureStylePreset");
        this.m = CameraKey.create("DigitalFilter");
        this.r = CameraKey.create("PhotoTimeIntervalSettings");
        this.s = CameraKey.create("PhotoAEBCount");
        this.t = CameraKey.create("PhotoBurstCount");
        this.u = CameraKey.create("PhotoTimeLapseSettings");
        this.addDependentKey(this.h);
        this.addDependentKey(this.i);
        this.addDependentKey(this.j);
        this.addDependentKey(this.k);
        this.addDependentKey(this.l);
        this.addDependentKey(this.m);
        this.addDependentKey(this.r);
        this.addDependentKey(this.s);
        this.addDependentKey(this.t);
        this.addDependentKey(this.u);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.h)) {
            this.z = (SettingsDefinitions.ShootPhotoMode)var1;
            this.a(this.z);
        } else if(var2.equals(this.i)) {
            this.a((SettingsDefinitions.PhotoAspectRatio)var1);
        } else if(var2.equals(this.j)) {
            this.a((SettingsDefinitions.PhotoFileFormat)var1);
        } else if(var2.equals(this.k)) {
            WhiteBalance var3 = (WhiteBalance)var1;
            this.a(var3.getWhiteBalancePreset(), var3.getColorTemperature());
        } else if(var2.equals(this.l)) {
            SettingsDefinitions.PictureStylePreset var4 = (SettingsDefinitions.PictureStylePreset)var1;
            this.a(var4.presetType().value());
        } else if(var2.equals(this.m)) {
            this.a((SettingsDefinitions.DigitalFilter)var1);
        } else if(var2.equals(this.r)) {
            this.v = (SettingsDefinitions.PhotoTimeIntervalSettings)var1;
            if(this.z == SettingsDefinitions.ShootPhotoMode.INTERVAL) {
                this.a(this.z);
            }
        } else if(var2.equals(this.s)) {
            this.w = (SettingsDefinitions.PhotoAEBCount)var1;
            if(this.z == SettingsDefinitions.ShootPhotoMode.AEB) {
                this.a(this.z);
            }
        } else if(var2.equals(this.t)) {
            this.x = (SettingsDefinitions.PhotoBurstCount)var1;
            if(this.z == SettingsDefinitions.ShootPhotoMode.BURST) {
                this.a(this.z);
            }
        } else if(var2.equals(this.u)) {
            this.y = (PhotoTimeLapseSettings)var1;
            if(this.z == SettingsDefinitions.ShootPhotoMode.TIME_LAPSE) {
                this.a(this.z);
            }
        }

    }

    private void a(SettingsDefinitions.PhotoAspectRatio var1) {
        int var2 = var1.value();
        if(var2 < this.p.length()) {
            this.a(1, var2, this.p.getResourceId(var2, 0));
        }

    }

    private void a(SettingsDefinitions.PhotoFileFormat var1) {
        int var2 = var1.value();
        if(var2 < this.o.length()) {
            this.a(2, var2, this.o.getResourceId(var2, 0));
        }

    }

    private void a(SettingsDefinitions.WhiteBalancePreset var1, int var2) {
        if(var1 != null) {
            int var3 = var1.value();
            if(var3 < this.q.length()) {
                this.a(3, var3, this.q.getResourceId(var3, 0));
            }
        }

    }

    private void a(int var1) {
        if(var1 < Inter_G.a.length) {
            this.a(4, var1, Inter_G.a[var1]);
        }

    }

    private void a(SettingsDefinitions.DigitalFilter var1) {
        int var2 = var1.value();
        if(var2 < this.n.length) {
            this.a(5, var2, 0);
            this.c.c(5).b(this.n[var2]);
        }

    }

    private void a(SettingsDefinitions.ShootPhotoMode var1) {
        if(var1 != null) {
            int var2 = var1.value();
            int var3;
            if(SettingsDefinitions.ShootPhotoMode.BURST.value() == var2 && this.x != null) {
                var3 = Inter_G.a(var2, this.x.value());
            } else if(SettingsDefinitions.ShootPhotoMode.AEB.value() == var2 && this.w != null) {
                var3 = Inter_G.a(var2, this.w.value());
            } else if(SettingsDefinitions.ShootPhotoMode.INTERVAL.value() == var2 && this.v != null) {
                var3 = Inter_G.a(var2, this.v.getTimeIntervalInSeconds());
            } else {
                var3 = Inter_G.a(var2, 0);
            }

            this.a(0, var2, var3);
        }
    }

    static {
        a = new UiCC.a[]{new UiCC.a(R.string.camera_photo_mode, UiCC.enum_b.a), new UiCC.a(R.string.camera_picture_size, UiCC.enum_b.a), new UiCC.a(R.string.camera_photo_format, UiCC.enum_b.a), new UiCC.a(R.string.camera_white_balance, UiCC.enum_b.a), new UiCC.a(R.string.camera_picture_style, UiCC.enum_b.a), new UiCC.a(R.string.camera_filter, UiCC.enum_b.a)};
    }
}
