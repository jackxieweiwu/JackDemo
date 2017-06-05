package dji.midware.ui.internal.a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dji.common.camera.ResolutionAndFrameRate;
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

/**
 * Created by jack_xie on 17-6-1.
 */

public class Inter_J extends UiBaseJView {
    private Map<SettingsDefinitions.VideoResolution, ArrayList<SettingsDefinitions.VideoFrameRate>> l;
    private DJIKey m;
    private DJIKey n;
    private ResolutionAndFrameRate o;

    public Inter_J(Context context) {
        super(context);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.l = new HashMap();
        this.o = new ResolutionAndFrameRate(SettingsDefinitions.VideoResolution.RESOLUTION_4096x2160, SettingsDefinitions.VideoFrameRate.FRAME_RATE_30_FPS);
        this.c();
    }

    public void updateTitle(TextView var1) {
        if(var1 != null) {
            var1.setText(R.string.camera_video_size_name);
        }

    }

    private void a(int[] var1) {
        for(int var2 = 0; var2 < var1.length / 2; ++var2) {
            int var3 = var1[var2];
            var1[var2] = var1[var1.length - var2 - 1];
            var1[var1.length - var2 - 1] = var3;
        }

    }

    private int[] a(Map<SettingsDefinitions.VideoResolution, ArrayList<SettingsDefinitions.VideoFrameRate>> var1) {
        if(var1 != null && !var1.keySet().isEmpty()) {
            Object[] var2 = var1.keySet().toArray();
            int[] var3 = new int[var2.length];

            for(int var4 = 0; var4 < var2.length; ++var4) {
                var3[var4] = ((SettingsDefinitions.VideoResolution)var2[var4]).value();
            }

            Arrays.sort(var3);
            this.a(var3);
            return var3;
        } else {
            return null;
        }
    }

    private int[] a(Map<SettingsDefinitions.VideoResolution, ArrayList<SettingsDefinitions.VideoFrameRate>> var1, int var2) {
        if(var1 != null && !var1.keySet().isEmpty()) {
            int[] var3 = null;
            Object[] var4 = var1.keySet().toArray();

            label26:
            for(int var5 = 0; var5 < var4.length; ++var5) {
                if(var2 == ((SettingsDefinitions.VideoResolution)var4[var5]).value()) {
                    ArrayList var6 = (ArrayList)var1.get(SettingsDefinitions.VideoResolution.find(var2));
                    Object[] var7 = var6.toArray();
                    var3 = new int[var7.length];
                    int var8 = 0;

                    while(true) {
                        if(var8 >= var7.length) {
                            break label26;
                        }

                        var3[var8] = ((SettingsDefinitions.VideoFrameRate)var7[var8]).value();
                        ++var8;
                    }
                }
            }

            Arrays.sort(var3);
            this.a(var3);
            return var3;
        } else {
            return null;
        }
    }

    protected List<UiCB> a() {
        ArrayList var1 = new ArrayList();
        this.j = Inter_G.f;
        String[] var2 = this.getResources().getStringArray(R.array.camera_video_resolution_name_array);
        String[] var3 = this.getResources().getStringArray(R.array.camera_video_frame_rate_name_array);
        int[] var5 = null;
        int[] var4;
        if(this.l != null && this.l.size() != 0) {
            var4 = this.a(this.l);
        } else {
            var4 = this.getResources().getIntArray(R.array.camera_video_resolution_default_value_array);
            var5 = this.getResources().getIntArray(R.array.camera_video_frame_rate_value_array);
        }

        if(null != var4 && var4.length > 0) {
            int var6 = 0;

            for(int var7 = var4.length; var6 < var7; ++var6) {
                UiCB var8 = new UiCB();
                var8.d = var4[var6];
                if(var8.d < var2.length) {
                    var8.b = var2[var8.d];
                } else {
                    DJILog.d("LWF", "Unexpected error!");
                }

                if(this.l != null && this.l.size() > 0) {
                    var5 = this.a(this.l, var8.d);
                }

                if(null != var5) {
                    var8.a = this.j[var8.d][var5[0]];
                    int var9 = 0;

                    for(int var10 = var5.length; var9 < var10; ++var9) {
                        UiCA var11 = new UiCA();
                        var11.c = var8.d;
                        var11.b = var5[var9];
                        if(var11.b < var3.length) {
                            var11.a = var3[var11.b];
                        } else {
                            DJILog.d("LWF", "Unexpected error!");
                        }

                        var8.uiCAArrayList.add(var11);
                    }
                }

                var1.add(var8);
            }
        }

        return var1;
    }

    private void c() {
        this.e = 2147483647;
        this.f = 2147483647;
        this.a.a(this.a());
        if(this.i != null && this.i.isShown()) {
            this.i.setVisibility(8);
        }

    }

    public void initKey() {
        this.m = CameraKey.create("VideoResolutionFrameRateRange");
        this.n = CameraKey.create("ResolutionFrameRate");
        this.addDependentKey(this.m);
        this.addDependentKey(this.n);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2.equals(this.n)) {
            this.o = (ResolutionAndFrameRate)var1;
        } else if(var2.equals(this.m)) {
            ResolutionAndFrameRate[] var3 = (ResolutionAndFrameRate[])((ResolutionAndFrameRate[])var1);

            for(int var4 = 0; var4 < var3.length; ++var4) {
                ResolutionAndFrameRate var5 = (ResolutionAndFrameRate)var3[var4];
                if(this.l.keySet().contains(var5.getResolution())) {
                    ((ArrayList)this.l.get(var5.getResolution())).add(var5.getFrameRate());
                } else {
                    ArrayList var6 = new ArrayList();
                    var6.add(var5.getFrameRate());
                    this.l.put(var5.getResolution(), var6);
                }
            }
        }

    }

    public void b() {
        if(this.o != null) {
            int var1 = this.o.getResolution().value();
            int var2 = this.o.getFrameRate().value();
            if(var1 != 2147483647 && var2 != 2147483647 && (var1 != this.e || var2 != this.f)) {
                this.a(this.e, var1, var2);
                this.e = var1;
                this.f = var2;
            }
        }

    }

    public void updateWidget(DJIKey var1) {
        if(var1.equals(this.m)) {
            this.c();
            this.b();
        } else if(var1.equals(this.n)) {
            this.b();
        }

    }

    public float aspectRatio() {
        return 0.0F;
    }

    private void a(SettingsDefinitions.VideoResolution var1, SettingsDefinitions.VideoFrameRate var2) {
        if(KeyManager.getInstance() != null) {
            final ResolutionAndFrameRate var3 = new ResolutionAndFrameRate();
            var3.setResolution(var1);
            var3.setFrameRate(var2);
            KeyManager.getInstance().setValue(this.n, var3, new SetCallback() {
                public void onSuccess() {
                    DJILog.d("DULCameraVideoSizeListWidget", "Camera setting " + var3 + " successfully");
                }

                public void onFailure(@NonNull DJIError var1) {
                    DJILog.d("DULCameraVideoSizeListWidget", "Failed to set Camera resolution and frame rate");
                }
            });
        }
    }

    protected boolean a(ExpandableListView var1, View var2, int var3, long var4) {
        UiCB var6 = (UiCB)this.a.getGroup(var3);
        if(var6.d != this.e) {
            int var7 = ((UiCA)var6.uiCAArrayList.get(0)).b;
            SettingsDefinitions.VideoFrameRate var8 = SettingsDefinitions.VideoFrameRate.find(var6.e);
            if(var8 != SettingsDefinitions.VideoFrameRate.UNKNOWN) {
                var7 = var8.value();
            }

            this.a(SettingsDefinitions.VideoResolution.find(var6.d), SettingsDefinitions.VideoFrameRate.find(var7));
            this.a(this.e, var6.d, var7);
            this.e = var6.d;
            this.f = var7;
        }

        return true;
    }

    protected void a(Object var1) {
        if(var1 instanceof UiCA) {
            UiCA  var2 = (UiCA)var1;
            if(this.e == var2.c && var2.b == this.f) {
                return;
            }

            int var3 = var2.c;
            int var4 = var2.b;
            this.a(SettingsDefinitions.VideoResolution.find(var3), SettingsDefinitions.VideoFrameRate.find(var4));
            this.a(this.e, var2.c, var2.b);
            this.e = var2.c;
            this.f = var2.b;
        }

    }
}
