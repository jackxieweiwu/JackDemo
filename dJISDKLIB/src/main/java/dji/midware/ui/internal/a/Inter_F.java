package dji.midware.ui.internal.a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dji.common.camera.PhotoTimeLapseSettings;
import dji.common.camera.SettingsDefinitions;
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
import dji.midware.ui.d.UiDG;

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_F extends UiBaseJView {
    private SettingsDefinitions.ShootPhotoMode l;
    private int[] m;
    private int[] n;
    private int[][] o;
    private DJIKey p;
    private DJIKey q;
    private DJIKey r;
    private boolean s;
    private DJIKey t;
    private DJIKey u;
    private DJIKey v;
    private DJIKey w;
    private SettingsDefinitions.PhotoTimeIntervalSettings x;
    private DJIKey y;
    private SettingsDefinitions.PhotoAEBCount z;
    private SettingsDefinitions.PhotoBurstCount A;
    private PhotoTimeLapseSettings B;
    private UiCB C;

    public Inter_F(Context var1) {
        super(var1);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.b((int[])null, (int[][])null);
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_photo_name);
        }

    }

    protected List<UiCB> a(int[] var1, int[][] var2) {
        ArrayList var3 = new ArrayList();
        this.m = Inter_G.a();
        int[] var4 = Inter_G.b();
        if(var1 == null) {
            this.n = Inter_G.c();
        } else {
            this.n = var1;
        }

        if(var2 == null) {
            this.o = Inter_G.d();
        } else {
            this.o = var2;
        }

        if(null != this.n && this.n.length > 0) {
            int var5 = 0;

            for(int var6 = this.n.length; var5 < var6; ++var5) {
                UiCB var7 = new UiCB();
                var7.d = this.n[var5];
                var7.b = this.getContext().getString(this.m[var7.d]);
                var7.a = var4[var7.d];
                int[] var8 = this.o[this.n[var5]];
                if(null != var8) {
                    int var9 = 0;

                    for(int var10 = var8.length; var9 < var10; ++var9) {
                        UiCA var11 = new UiCA();
                        int var10000 = this.n[var5];
                        SettingsDefinitions.ShootPhotoMode var10001 = this.l;
                        if(var10000 == SettingsDefinitions.ShootPhotoMode.INTERVAL.value()) {
                            var11.a = var8[var9] + "s";
                        } else {
                            var11.a = String.valueOf(var8[var9]);
                        }

                        var11.c = this.n[var5];
                        var11.b = var8[var9];
                        var7.uiCAArrayList.add(var11);
                    }
                }

                var3.add(var7);
            }
        }

        return var3;
    }

    private void b(int[] var1, int[][] var2) {
        this.e = 2147483647;
        this.f = 2147483647;
        this.a.a(this.a(var1, var2));
        if(this.i != null && this.i.isShown()) {
            this.i.setVisibility(8);
        }

    }

    public void initKey() {
        this.p = CameraKey.create("ShootPhotoModeRange");
        this.t = CameraKey.create("ShootPhotoChildRange");
        this.q = CameraKey.create("IsShootingIntervalPhoto");
        this.r = CameraKey.create("ShootPhotoMode");
        this.u = CameraKey.create("PhotoTimeIntervalSettings");
        this.v = CameraKey.create("PhotoAEBCount");
        this.w = CameraKey.create("PhotoBurstCount");
        this.y = CameraKey.create("PhotoTimeLapseSettings");
        this.addDependentKey(this.p);
        this.addDependentKey(this.q);
        this.addDependentKey(this.r);
        this.addDependentKey(this.t);
        this.addDependentKey(this.u);
        this.addDependentKey(this.v);
        this.addDependentKey(this.w);
        this.addDependentKey(this.y);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.r)) {
            this.l = (SettingsDefinitions.ShootPhotoMode)var1;
            SettingsDefinitions.ShootPhotoMode var10001 = this.l;
            if(this.l == SettingsDefinitions.ShootPhotoMode.BURST) {
                this.getBurstCount();
            } else {
                var10001 = this.l;
                if(this.l == SettingsDefinitions.ShootPhotoMode.TIME_LAPSE) {
                    this.getTimeLapseParam();
                }
            }
        } else if(var2.equals(this.p)) {
            SettingsDefinitions.ShootPhotoMode[] var3 = (SettingsDefinitions.ShootPhotoMode[])((SettingsDefinitions.ShootPhotoMode[])var1);
            this.n = new int[var3.length];

            for(int var4 = 0; var4 < var3.length; ++var4) {
                this.n[var4] = ((SettingsDefinitions.ShootPhotoMode)var3[var4]).value();
            }
        } else if(var2.equals(this.q)) {
            this.s = ((Boolean)var1).booleanValue();
        } else if(var2.equals(this.t)) {
            this.o = (int[][])((int[][])var1);
        } else if(var2.equals(this.u)) {
            this.x = (SettingsDefinitions.PhotoTimeIntervalSettings)var1;
        } else if(var2.equals(this.v)) {
            this.z = (SettingsDefinitions.PhotoAEBCount)var1;
        } else if(var2.equals(this.w)) {
            this.A = (SettingsDefinitions.PhotoBurstCount)var1;
        } else if(var2.equals(this.y)) {
            this.B = (PhotoTimeLapseSettings)var1;
        }

    }

    private void getBurstCount() {
        UiDG.a().a(this.w, this);
    }

    private void getTimeLapseParam() {
        UiDG.a().a(this.y, this);
    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.p)) {
            this.b(this.n, this.o);
            this.a();
        } else if(var1.equals(this.r)) {
            this.a();
        } else if(var1.equals(this.t)) {
            this.b(this.n, this.o);
            this.a();
        }

    }

    private void a() {
        if(this.l != null) {
            this.C = this.a.a(this.l.value());
            this.C.e = this.b(this.l, 0);
            if(this.C != null) {
                this.a.a(this.C);
            }
        }

    }

    private void a(final SettingsDefinitions.ShootPhotoMode var1, int var2) {
        if(KeyManager.getInstance() != null) {
            Object var3 = null;
            DJIKey var4 = null;
            SettingsDefinitions.ShootPhotoMode var10001 = this.l;
            if(var1 == SettingsDefinitions.ShootPhotoMode.BURST) {
                var4 = this.w;
                var3 = SettingsDefinitions.PhotoBurstCount.find(var2);
            } else {
                var10001 = this.l;
                if(var1 == SettingsDefinitions.ShootPhotoMode.AEB) {
                    var4 = this.v;
                    var3 = SettingsDefinitions.PhotoAEBCount.find(var2);
                } else {
                    var10001 = this.l;
                    int var5;
                    if(var1 == SettingsDefinitions.ShootPhotoMode.INTERVAL) {
                        var4 = this.u;
                        if(this.x == null) {
                            var5 = 255;
                        } else {
                            var5 = this.x.getCaptureCount();
                        }

                        var3 = new SettingsDefinitions.PhotoTimeIntervalSettings(var5, var2);
                    } else {
                        var10001 = this.l;
                        if(var1 == SettingsDefinitions.ShootPhotoMode.TIME_LAPSE) {
                            var4 = this.y;
                            var5 = 0;
                            SettingsDefinitions.PhotoTimeLapseFileFormat var6 = SettingsDefinitions.PhotoTimeLapseFileFormat.JPEG_AND_VIDEO;
                            if(this.x != null) {
                                var6 = this.B.getFileFormat();
                                var5 = this.B.getDuration();
                            }

                            var3 = new PhotoTimeLapseSettings(var2, var5, var6);
                        }
                    }
                }
            }

            if(var4 != null) {
                KeyManager.getInstance().setValue(var4, var3, new SetCallback() {
                    public void onSuccess() {
                        a(var1);
                    }

                    public void onFailure(@NonNull DJIError var1x) {
                        DJILog.d("CameraPhotoModeListWidget", "Failed to set Camera Photo Mode " + var1x.getDescription());
                    }
                });
            } else {
                this.a(var1);
            }

        }
    }

    private void a(final SettingsDefinitions.ShootPhotoMode var1) {
        if(KeyManager.getInstance() != null) {
            CameraKey var2 = CameraKey.create("ShootPhotoMode");
            KeyManager.getInstance().setValue(var2, var1, new SetCallback() {
                public void onSuccess() {
                    DJILog.d("CameraPhotoModeListWidget", "Camera mode " + var1.name() + "value " + var1.value() + " set successfully");
                }

                public void onFailure(@NonNull DJIError var1x) {
                    DJILog.d("CameraPhotoModeListWidget", "Camera mode " + var1.name() + " set failed");
                }
            });
        }
    }

    private int b(SettingsDefinitions.ShootPhotoMode var1, int var2) {
        int var3 = var2;
        SettingsDefinitions.ShootPhotoMode var10001 = this.l;
        if(var1 == SettingsDefinitions.ShootPhotoMode.BURST && this.A != null) {
            var3 = this.A.value();
        } else {
            var10001 = this.l;
            if(var1 == SettingsDefinitions.ShootPhotoMode.AEB && this.z != null) {
                var3 = this.z.value();
            } else {
                var10001 = this.l;
                if(var1 == SettingsDefinitions.ShootPhotoMode.INTERVAL && this.x != null) {
                    var3 = this.x.getTimeIntervalInSeconds();
                } else {
                    var10001 = this.l;
                    if(var1 == SettingsDefinitions.ShootPhotoMode.TIME_LAPSE && this.B != null) {
                        var3 = this.B.getInterval();
                    }
                }
            }
        }

        return var3;
    }

    protected boolean a(ExpandableListView var1, View var2, int var3, long var4) {
        UiCB var6 = (UiCB)this.a.getGroup(var3);
        if(var6.d != this.e && !this.s) {
            SettingsDefinitions.ShootPhotoMode var10000 = this.l;
            SettingsDefinitions.ShootPhotoMode var7 = SettingsDefinitions.ShootPhotoMode.find(var6.d);
            int var8 = 0;
            if(!var6.uiCAArrayList.isEmpty()) {
                var8 = ((UiCA)var6.uiCAArrayList.get(0)).b;
            }

            int var9 = this.b(var7, var8);
            this.a(var7, var9);
            this.a(this.e, var6.d, var9);
            this.e = var6.d;
            this.f = var9;
        }

        return true;
    }

    protected void a(Object var1) {
        if(var1 instanceof UiCA) {
            UiCA var2 = (UiCA)var1;
            if(this.e == var2.c && var2.b == this.f) {
                return;
            }

            if(!this.s) {
                SettingsDefinitions.ShootPhotoMode var10001 = this.l;
                this.a(SettingsDefinitions.ShootPhotoMode.find(var2.c), var2.b);
                this.a(this.e, var2.c, var2.b);
                this.e = var2.c;
                this.f = var2.b;
            }
        }

    }
}
