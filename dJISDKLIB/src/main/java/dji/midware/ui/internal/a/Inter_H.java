package dji.midware.ui.internal.a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import dji.common.camera.SettingsDefinitions;
import dji.common.error.DJIError;
import dji.keysdk.CameraKey;
import dji.keysdk.DJIKey;
import dji.keysdk.KeyManager;
import dji.keysdk.callback.SetCallback;
import dji.log.DJILog;
import dji.midware.R;
import dji.midware.ui.base.DULFrameLayout;
import dji.midware.ui.base.UiBaseKView;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_J;
import dji.midware.ui.d.UiDC;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_H extends DULFrameLayout implements UiBaseKView {
    private DJIKey a;
    private SettingsDefinitions.PictureStylePreset b;
    private static final int[] c;
    private UiCBA_J d;
    private FrameLayout[] e;
    private View f;
    private View viewG;
    private View h;
    private FrameLayout i;
    private OnClickListener j;
    private int k;
    private int l;
    private int m;
    private int numN;
    private int numO = -1;
    private int[] p;
    private int[] q;
    private int[] r;
    private TextView s;
    private TextView t;
    private TextView u;

    public Inter_H(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.e();
        this.b();
        this.c();
        this.d();
    }

    private void b() {
        this.e = new FrameLayout[c.length];

        for(int var1 = 0; var1 < c.length; ++var1) {
            FrameLayout var2 = (FrameLayout)this.findViewById(c[var1]);
            var2.setOnClickListener(this.j);
            this.e[var1] = var2;
        }

        this.s = (TextView)this.findViewById(R.id.camera_style_custom_item_sharpness);
        this.t = (TextView)this.findViewById(R.id.camera_style_custom_item_contrast);
        this.u = (TextView)this.findViewById(R.id.camera_style_custom_item_saturation);
        this.f = this.findViewById(R.id.camera_style_custom_sharpness);
        this.viewG = this.findViewById(R.id.camera_style_custom_contrast);
        this.h = this.findViewById(R.id.camera_style_custom_saturation);
        this.i = (FrameLayout)this.findViewById(R.id.camera_style_custom_layout);
        ImageView var3 = (ImageView)this.findViewById(R.id.custom_min_img);
        ImageView var4 = (ImageView)this.findViewById(R.id.custom_max_img);
        this.f.setOnClickListener(this.j);
        this.viewG.setOnClickListener(this.j);
        this.h.setOnClickListener(this.j);
        var3.setOnClickListener(this.j);
        var4.setOnClickListener(this.j);
    }

    private void c() {
        this.p = Inter_G.c;
        this.q = Inter_G.c;
        this.r = Inter_G.c;
    }

    private void d() {
        byte[] var1 = Inter_G.b[SettingsDefinitions.PictureStylePresetType.CUSTOM.value()];
        this.b(var1[0], var1[1], var1[2]);
    }

    protected UiCAB getWidgetAppearances() {
        if(this.d == null) {
            this.d = new UiCBA_J();
        }
        return this.d;
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_picture_style);
        }

    }

    private void e() {
        this.j = new OnClickListener() {
            public void onClick(View var1) {
                int var2 = var1.getId();
                if(R.id.camera_style_custom_sharpness == var2) {
                    if(numO != 0) {
                        numO = 0;
                        inteHI();
                    }
                } else if(R.id.camera_style_custom_contrast == var2) {
                    if(numO != 1) {
                        numO = 1;
                        inteHI();
                    }
                } else if(R.id.camera_style_custom_saturation == var2) {
                    if(2 != numO) {
                        numO = 2;
                        inteHI();
                    }
                } else if(R.id.custom_min_img == var2) {
                    interG();
                } else if(R.id.custom_max_img == var2) {
                    interH();
                } else {
                    for(int var3 = 0; var3 < c.length; ++var3) {
                        if(var2 == c[var3]) {
                            numN = var3;
                            if(numN == SettingsDefinitions.PictureStylePresetType.CUSTOM.value()) {
                                intetF();
                            } else {
                                interC(var3);
                            }
                            break;
                        }
                    }
                }

            }
        };
    }

    private String a(int var1) {
        return String.format("%+d", new Object[]{Integer.valueOf(var1)});
    }

    public void initKey() {
        this.a = CameraKey.create("PictureStylePreset");
        this.addDependentKey(this.a);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.a)) {
            this.b = (SettingsDefinitions.PictureStylePreset)var1;
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.a)) {
            this.a(this.b);
        }

    }

    private void a(SettingsDefinitions.PictureStylePreset var1) {
        int var2 = var1.getSharpness();
        int var3 = var1.getContrast();
        int var4 = var1.getSaturation();
        int var5 = var1.presetType().value();
        if(numN == SettingsDefinitions.PictureStylePresetType.CUSTOM.value()) {
            this.b(var5);
            this.i.setVisibility(0);
            if(-1 == numO) {
                numO = 0;
            }

            inteHI();
            this.b(var2, var3, var4);
        } else {
            numN = var5;
            this.b(var5);
            if(var5 == SettingsDefinitions.PictureStylePresetType.CUSTOM.value()) {
                this.i.setVisibility(0);
                if(-1 == numO) {
                    numO = 0;
                    inteHI();
                }

                this.b(var2, var3, var4);
            } else {
                this.i.setVisibility(8);
            }
        }

    }

    private void b(int var1) {
        for(int var2 = 0; var2 < this.e.length; ++var2) {
            this.e[var2].setSelected(false);
        }

        this.e[var1].setSelected(true);
    }

    private int a(int var1, int var2, int var3) {
        if(var3 < var1) {
            var3 = var1;
        } else if(var3 > var2) {
            var3 = var2;
        }

        return var3;
    }

    private void b(int var1, int var2, int var3) {
        this.k = this.a(this.p[0], this.p[1], var1);
        this.l = this.a(this.q[0], this.q[1], var2);
        this.m = this.a(this.r[0], this.r[1], var3);
        this.s.setText(this.a(this.k));
        this.t.setText(this.a(this.l));
        this.u.setText(this.a(this.m));
    }

    private void b(final SettingsDefinitions.PictureStylePreset var1) {
        if(KeyManager.getInstance() != null) {
            if(var1 != null) {
                KeyManager.getInstance().setValue(this.a, var1, new SetCallback() {
                    public void onSuccess() {
                        DJILog.d("DULCameraStyleListWidget", "Camera setting " + var1 + " successfully");
                    }

                    public void onFailure(@NonNull DJIError var1x) {
                        DJILog.d("DULCameraStyleListWidget", "Failed to set Camera Exposure Mode");
                    }
                });
            }

        }
    }

    private void intetF() {
        FrameLayout var1 = this.e[SettingsDefinitions.PictureStylePresetType.CUSTOM.value()];
        if(!var1.isSelected()) {
            SettingsDefinitions.PictureStylePreset.Builder var2 = new SettingsDefinitions.PictureStylePreset.Builder();
            var2.sharpness(this.k).contrast(this.l).saturation(this.m);
            this.b(var2.build());
        }

    }

    private void interC(int var1) {
        if(!this.e[var1].isSelected()) {
            byte[] var2 = Inter_G.b[var1];
            SettingsDefinitions.PictureStylePreset.Builder var3 = new SettingsDefinitions.PictureStylePreset.Builder();
            var3.sharpness(var2[0]).contrast(var2[1]).saturation(var2[2]);
            this.b(var3.build());
        }

    }

    private void interG() {
        UiDC.a(this.getContext());
        SettingsDefinitions.PictureStylePreset.Builder var1 = new SettingsDefinitions.PictureStylePreset.Builder();
        var1.sharpness(this.k).contrast(this.l).saturation(this.m);
        int var2 = -2147483648;
        if(numO == 0) {
            if(this.k > this.p[0]) {
                var2 = this.k - 1;
                var1.sharpness(var2);
            }
        } else if(numO == 1) {
            if(this.l > this.q[0]) {
                var2 = this.l - 1;
                var1.contrast(var2);
            }
        } else if(numO == 2 && this.m > this.r[0]) {
            var2 = this.m - 1;
            var1.saturation(var2);
        }

        if(var2 != -2147483648) {
            this.b(var1.build());
        }

    }

    private void interH() {
        UiDC.a(this.getContext());
        int var1 = -2147483648;
        SettingsDefinitions.PictureStylePreset.Builder var2 = new SettingsDefinitions.PictureStylePreset.Builder();
        var2.sharpness(this.k).contrast(this.l).saturation(this.m);
        if(numO == 0) {
            if(this.k < this.p[1]) {
                var1 = this.k + 1;
                var2.sharpness(var1);
            }
        } else if(numO == 1) {
            if(this.l < this.q[1]) {
                var1 = this.l + 1;
                var2.contrast(var1);
            }
        } else if(numO == 2 && this.m < this.r[1]) {
            var1 = this.m + 1;
            var2.saturation(var1);
        }

        if(var1 != -2147483648) {
            this.b(var2.build());
        }

    }

    private void inteHI() {
        this.f.setSelected(false);
        this.viewG.setSelected(false);
        this.h.setSelected(false);
        if(numO == 0) {
            this.f.setSelected(true);
        } else if(numO == 1) {
            this.viewG.setSelected(true);
        } else if(numO == 2) {
            this.h.setSelected(true);
        }

    }

    static {
        c = new int[]{R.id.camera_style_standard, R.id.camera_style_landscape, R.id.camera_style_soft, R.id.camera_style_custom};
    }
}
