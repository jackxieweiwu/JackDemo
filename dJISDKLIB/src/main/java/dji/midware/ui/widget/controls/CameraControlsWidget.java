package dji.midware.ui.widget.controls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import dji.common.bus.UILibEventBus;
import dji.midware.R;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBH;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.d.UiDM;
import dji.midware.ui.internal.CInter;

/**
 * Created by jack_xie on 17-6-1.
 */

public class CameraControlsWidget extends BaseFrameLayout {
    private UiCAB widgetAppearances;
    private ImageView cameraMenuBg;
    private ImageView exposureStatusBg;

    public CameraControlsWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public CameraControlsWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public CameraControlsWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        UiDA.b();
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.setClickable(true);
        this.setBackground(this.getResources().getDrawable(R.drawable.camera_panel_background));
        this.cameraMenuBg = (ImageView)this.getViewById(R.id.widget_camera_menu_background);
        this.exposureStatusBg = (ImageView)this.getViewById(R.id.widget_camera_exposure_status_background);
        this.cameraMenuBg.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                if(CameraControlsWidget.this.cameraMenuBg.isSelected()) {
                    UILibEventBus.getInstance().post(new CInter.a(false));
                } else {
                    UILibEventBus.getInstance().post(new CInter.a(true));
                }

                UILibEventBus.getInstance().post(new CInter.b(false));
                UiDM.a(CameraControlsWidget.this.cameraMenuBg);
                UiDM.b(CameraControlsWidget.this.exposureStatusBg);
            }
        });
        this.exposureStatusBg.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                if(CameraControlsWidget.this.exposureStatusBg.isSelected()) {
                    UILibEventBus.getInstance().post(new CInter.b(false));
                } else {
                    UILibEventBus.getInstance().post(new CInter.b(true));
                }

                UILibEventBus.getInstance().post(new CInter.a(false));
                UiDM.a(CameraControlsWidget.this.exposureStatusBg);
                UiDM.b(CameraControlsWidget.this.cameraMenuBg);
            }
        });
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBH();
        }

        return this.widgetAppearances;
    }
}
