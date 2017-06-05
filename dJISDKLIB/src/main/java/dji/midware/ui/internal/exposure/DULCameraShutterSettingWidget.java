package dji.midware.ui.internal.exposure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

import dji.common.camera.ExposureSettings;
import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.antistatic.spinnerwheel.AntiSpinnerA;
import dji.midware.ui.antistatic.spinnerwheel.AntiSpinnerD;
import dji.midware.ui.antistatic.spinnerwheel.AntiSpinnerF;
import dji.midware.ui.antistatic.spinnerwheel.WheelHorizontalView;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.UiCA;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.a.UiCAC;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_I;
import dji.midware.ui.d.UiDC;
import dji.midware.ui.d.UiDD;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULCameraShutterSettingWidget extends DULFrameLayout implements AntiSpinnerD,AntiSpinnerF {
    private DJIKey b;
    private CameraKey c;
    private CameraKey d;
    private UiCBA_I e;
    private WheelHorizontalView f;
    private ImageView g;
    private DJIKey h;
    private SettingsDefinitions.ShutterSpeed i;
    DULCameraShutterSettingWidget.a a;
    private int j = 0;
    private boolean k;
    private SettingsDefinitions.ExposureMode l;
    private Object[] m;
    private String[] n;

    protected UiCAB getWidgetAppearances() {
        if(this.e == null) {
            this.e = new UiCBA_I();
        }
        return this.e;
    }

    public DULCameraShutterSettingWidget(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public DULCameraShutterSettingWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public DULCameraShutterSettingWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.f = (WheelHorizontalView)this.getViewById(R.id.wheelview_camera_settings_shutter);
        this.g = (ImageView)this.getViewById(R.id.imageview_shutter_wheel_position);
        this.n = this.getResources().getStringArray(R.array.camera_shutter_names);
        this.a();
    }

    public void initKey() {
        this.h = CameraKey.create("ShutterSpeed");
        this.b = CameraKey.create("ExposureSettings");
        this.c = CameraKey.create("ExposureMode");
        this.d = CameraKey.create("ShutterSpeedRange");
        this.addDependentKey(this.b);
        this.addDependentKey(this.h);
        this.addDependentKey(this.c);
        this.addDependentKey(this.d);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.c)) {
            this.l = (SettingsDefinitions.ExposureMode)var1;
        } else if(var2.equals(this.h)) {
            this.i = (SettingsDefinitions.ShutterSpeed)var1;
        } else if(var2.equals(this.b)) {
            ExposureSettings var3 = (ExposureSettings)var1;
            this.i = var3.getShutterSpeed();
        } else if(var2.equals(this.d)) {
            this.a((Object[])((SettingsDefinitions.ShutterSpeed[])((SettingsDefinitions.ShutterSpeed[])var1)));
        }

    }

    private void a(Object[] var1) {
        this.m = var1;
        String[] var2 = new String[var1.length];

        for(int var3 = 0; var3 < var1.length; ++var3) {
            String var4 = UiDD.a((SettingsDefinitions.ShutterSpeed)var1[var3]);
            var2[var3] = var4;
        }

        this.n = var2;
    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.c)) {
            this.b();
        } else if(var1.equals(this.b)) {
            this.a(this.i);
        } else if(var1.equals(this.h)) {
            this.a(this.i);
        } else if(var1.equals(this.d)) {
            this.a();
        }

    }

    private void a() {
        this.a = new DULCameraShutterSettingWidget.a(this.getContext(), this.n);
        this.a.a(R.layout.wheel_item_camera_set_shutter);
        this.a.b(R.id.camera_settings_wheel_text);
        this.f.a((AntiSpinnerD) this);
        this.f.a((AntiSpinnerD) this);
        this.f.setViewAdapter(this.a);
        this.f.setCurrentItem(this.j);
    }

    private int b(SettingsDefinitions.ShutterSpeed var1) {
        byte var2 = 0;
        if(this.m != null) {
            for(int var3 = 0; var3 < this.m.length; ++var3) {
                if(this.m[var3] == var1) {
                    return var3;
                }
            }
        } else if(this.n != null) {
            String var5 = UiDD.a(var1);

            for(int var4 = 0; var4 < this.n.length; ++var4) {
                if(this.n[var4].equalsIgnoreCase(var5)) {
                    return var4;
                }
            }
        }

        return var2;
    }

    public void a(SettingsDefinitions.ShutterSpeed var1) {
        if(var1 != null && !this.k) {
            this.j = this.b(var1);
            this.f.setCurrentItem(this.j);
            this.a.d(this.j);
        }

    }

    private void b() {
        label23: {
            SettingsDefinitions.ExposureMode var10001 = this.l;
            if(this.l != SettingsDefinitions.ExposureMode.PROGRAM) {
                var10001 = this.l;
                if(this.l != SettingsDefinitions.ExposureMode.APERTURE_PRIORITY) {
                    var10001 = this.l;
                    if(this.l != SettingsDefinitions.ExposureMode.SHUTTER_PRIORITY) {
                        var10001 = this.l;
                        if(this.l != SettingsDefinitions.ExposureMode.MANUAL) {
                            break label23;
                        }
                    }

                    this.f.setEnabled(true);
                    this.f.setAlpha(1.0F);
                    this.g.setVisibility(0);
                    this.f.setVisibleItems(7);
                    break label23;
                }
            }

            this.f.setAlpha(0.7F);
            this.f.setEnabled(false);
            this.g.setVisibility(4);
            this.f.setVisibleItems(1);
        }

        this.a.a(this.f.getVisibleItems() > 1);
    }

    public void a(AntiSpinnerA var1) {
        this.k = true;
        DJILog.d("DULCameraShutterSettingWidget", "shutter scroll start");
    }

    public void b(AntiSpinnerA var1) {
        DJILog.d("DULCameraShutterSettingWidget", "shutter scroll finish");
        this.k = false;
        this.a(var1.getCurrentItem());
    }

    public void a(AntiSpinnerA var1, int var2, int var3) {
        if(this.k) {
            this.a.d(var3);
            this.f.setCurrentItem(var3);
            DJILog.d("DULCameraShutterSettingWidget", "shutter scroll changed");
        }

    }

    private void a(int var1) {
        if(KeyManager.getInstance() != null) {
            if(this.m != null) {
                UiDC.a(this.getContext());
                this.j = var1;
                final SettingsDefinitions.ShutterSpeed var2 = (SettingsDefinitions.ShutterSpeed)this.m[this.j];
                this.a(var2);
                KeyManager.getInstance().setValue(this.h, var2, new SetCallback() {
                    public void onSuccess() {
                        DJILog.d("DULCameraShutterSettingWidget", "Camera shutter " + var2.name() + " set successfully");
                    }

                    public void onFailure(@NonNull DJIError var1) {
                        DULCameraShutterSettingWidget.this.c();
                        DJILog.d("DULCameraShutterSettingWidget", "Failed to set Camera shutter");
                    }
                });
            }

        }
    }

    private void c() {
        if(this.i != null) {
            this.a(this.i);
        }

    }

    class a extends UiCAC<String> {
        public a(Context var2, String[] var3) {
            super(var2, var3);
        }

        protected CharSequence c(int var1) {
            String var2 = (String)super.c(var1);
            if(!DULCameraShutterSettingWidget.this.f.isEnabled() && !var2.contains("\"")) {
                var2 = "1/" + var2 + "\"";
            }

            return var2;
        }
    }
}
