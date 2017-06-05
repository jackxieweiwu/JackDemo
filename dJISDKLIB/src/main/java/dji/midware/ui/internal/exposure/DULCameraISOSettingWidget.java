package dji.midware.ui.internal.exposure;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
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
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBV;
import dji.midware.ui.d.UiDC;
import dji.midware.ui.d.UiDD;
import dji.midware.ui.internal.DULSeekBar;

/**
 * Created by jack_xie on 17-6-1.
 */

public class DULCameraISOSettingWidget extends DULFrameLayout implements View.OnClickListener,DULSeekBar.a {
    private UiCAB a;
    private DJIKey b;
    private SettingsDefinitions.ISO c;
    private SettingsDefinitions.ISO[] d;
    private DULSeekBar e;
    private ImageView f;
    private SettingsDefinitions.ISO g;
    private boolean h;
    private DJIKey i;
    private DJIKey j;
    private SettingsDefinitions.ISO k;
    private boolean l;

    public DULCameraISOSettingWidget(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public DULCameraISOSettingWidget(Context var1, AttributeSet var2) {
        super(var1, var2, 0);
    }

    public DULCameraISOSettingWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.e = (DULSeekBar)this.findViewById(R.id.seekbar_iso);
        this.f = (ImageView)this.findViewById(R.id.button_iso_auto);
        this.e.setMax(0);
        this.e.setProgress(0);
        this.e.a(false);
        this.e.setOnSeekBarChangeListener(this);
        this.f.setOnClickListener(this);
        this.f.setEnabled(true);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.a == null) {
            this.a = new UiCBV();
        }
        return this.a;
    }

    public void initKey() {
        this.b = CameraKey.create("ISO");
        this.addDependentKey(this.b);
        this.j = CameraKey.create("ExposureSettings");
        this.addDependentKey(this.j);
        this.i = CameraKey.create("ISORange");
        this.addDependentKey(this.i);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.b)) {
            this.c = (SettingsDefinitions.ISO)var1;
        } else if(var2.equals(this.i)) {
            this.b((Object[])((SettingsDefinitions.ISO[])((SettingsDefinitions.ISO[])var1)));
        } else if(var2.equals(this.j)) {
            ExposureSettings var3 = (ExposureSettings)var1;
            this.k = var3.getISO();
        }

    }

    private boolean a(Object[] var1) {
        for(int var2 = 0; var2 < var1.length; ++var2) {
            if(var1[var2] == SettingsDefinitions.ISO.AUTO) {
                return true;
            }
        }

        return false;
    }

    private void b(Object[] var1) {
        this.h = this.a(var1);
        SettingsDefinitions.ISO[] var2;
        if(this.h) {
            var2 = new SettingsDefinitions.ISO[var1.length - 1];
        } else {
            var2 = new SettingsDefinitions.ISO[var1.length];
        }

        int var3 = 0;

        for(int var4 = 0; var3 < var1.length; ++var3) {
            if(var1[var3] != SettingsDefinitions.ISO.AUTO) {
                var2[var4] = (SettingsDefinitions.ISO)var1[var3];
                ++var4;
            }
        }

        this.d = var2;
    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.j)) {
            this.a();
        } else if(var1.equals(this.i)) {
            this.b();
        }

    }

    private void a() {
        if(this.c == SettingsDefinitions.ISO.AUTO) {
            this.f.setSelected(true);
            this.a(false);
        } else {
            this.f.setSelected(false);
            this.a(true);
        }

        if(!this.l) {
            this.b(this.k);
        }

    }

    private void b() {
        this.a(true);
        this.b(this.h);
    }

    private void a(boolean var1) {
        if(var1) {
            if(this.d != null && this.d.length > 0) {
                this.e.setMax(this.d.length - 1);
                this.e.a(true);
            } else {
                this.e.setMax(0);
                this.e.a(false);
            }
        } else {
            this.e.a(false);
        }

    }

    private void b(boolean var1) {
        if(var1) {
            if(this.d != null && this.d.length != 0) {
                this.f.setVisibility(0);
                this.f.setEnabled(var1);
            } else {
                this.f.setVisibility(8);
            }
        } else {
            this.f.setVisibility(8);
        }

    }

    public int a(SettingsDefinitions.ISO var1) {
        int var2 = -1;
        if(this.d != null) {
            for(int var3 = 0; var3 < this.d.length; ++var3) {
                if(var1 == this.d[var3]) {
                    var2 = var3;
                    break;
                }
            }
        }

        return var2;
    }

    public void onClick(View var1) {
        if(var1 == this.f) {
            if(this.c == SettingsDefinitions.ISO.AUTO) {
                this.setAutoISO(false);
            } else {
                this.setAutoISO(true);
            }
        }

    }

    public void a(int var1, boolean var2) {
        if(this.d != null && var1 < this.d.length) {
            this.e.setText(UiDD.a(this.d[var1]));
        }

    }

    public void a(int var1) {
        this.l = true;
    }

    public void b(int var1) {
        this.l = false;
        this.c(var1);
    }

    private void c(int var1) {
        if(this.d != null && this.d.length > 0) {
            UiDC.a(this.getContext());
            SettingsDefinitions.ISO var2 = this.d[var1];
            this.c(var2);
        }

    }

    private void c(final SettingsDefinitions.ISO var1) {
        if(KeyManager.getInstance() != null) {
            KeyManager.getInstance().setValue(this.b, var1, new SetCallback() {
                public void onSuccess() {
                    DJILog.d("DULISOSettingWidget", "Camera ISO " + var1.name() + " set successfully");
                }

                public void onFailure(@NonNull DJIError var1x) {
                    DULCameraISOSettingWidget.this.e.a();
                    DJILog.d("DULISOSettingWidget", "Failed to set Camera Exposure Mode");
                }
            });
        }
    }

    private void setAutoISO(boolean var1) {
        SettingsDefinitions.ISO var2;
        if(var1) {
            var2 = SettingsDefinitions.ISO.AUTO;
        } else if(this.g != SettingsDefinitions.ISO.UNKNOWN) {
            var2 = this.g;
        } else {
            var2 = this.d[this.e.getProgress()];
        }

        this.c(var2);
    }

    public void b(SettingsDefinitions.ISO var1) {
        this.g = var1;
        int var2 = this.a(var1);
        if(var2 >= 0) {
            this.e.setProgress(var2);
        }

    }
}
