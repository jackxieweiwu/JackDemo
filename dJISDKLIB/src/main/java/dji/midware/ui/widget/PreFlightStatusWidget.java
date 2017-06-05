package dji.midware.ui.widget;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import dji.common.bus.LogicEventBus;
import dji.common.bus.UILibEventBus;
import dji.internal.logics.FPVTipLogic;
import dji.internal.logics.LogicManager;
import dji.internal.logics.Message;
import dji.midware.R;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.base.MarqueeTextView;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBA_B;
import dji.midware.ui.c.b.UiCBA_F;
import dji.midware.ui.d.UiDA;
import dji.midware.ui.internal.CInter;
import dji.thirdparty.rx.android.schedulers.AndroidSchedulers;
import dji.thirdparty.rx.functions.Action1;

/**
 * Created by jack_xie on 17-6-1.
 */

public class PreFlightStatusWidget extends BaseFrameLayout {
    private UiCAB widgetAppearances;
    private MarqueeTextView statusText;
    private ImageView statusBackground;
    private PreFlightStatusWidget.StatusType backGroundType;
    private String textStatus;

    public PreFlightStatusWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public PreFlightStatusWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public PreFlightStatusWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.textStatus = "";
        UiDA.b();
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBA_F();
        }

        return this.widgetAppearances;
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        this.statusText = (MarqueeTextView)this.getViewById(R.id.textview_preflight_status);
        this.statusText.setDelay(0);
        this.statusBackground = (ImageView)this.getViewById(R.id.imageview_preflight_color_indicator);
        this.statusText.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                UILibEventBus.getInstance().post(new CInter());
            }
        });
        this.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
                UILibEventBus.getInstance().post(new CInter());
            }
        });
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!this.isInEditMode()) {
            LogicManager.getInstance().startFPVTipLogic();
            LogicEventBus.getInstance().register(FPVTipLogic.FPVTipEvent.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<FPVTipLogic.FPVTipEvent>() {
                @Override
                public void call(FPVTipLogic.FPVTipEvent var1) {
                    Message var2 = var1.getMessage();
                    PreFlightStatusWidget.this.updateView(var2);
                }
            });
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogicManager.getInstance().stopFPVTipLogic();
    }

    @MainThread
    @Keep
    public void onStatusChange(String var1, PreFlightStatusWidget.StatusType var2, boolean var3) {
        if(this.backGroundType != var2) {
            this.backGroundType = var2;
            if(var2 == PreFlightStatusWidget.StatusType.GOOD) {
                this.statusBackground.setBackgroundResource(R.drawable.connected_status);
            } else if(var2 == PreFlightStatusWidget.StatusType.WARNING) {
                this.statusBackground.setBackgroundResource(R.drawable.warning_status);
            } else if(var2 == PreFlightStatusWidget.StatusType.ERROR) {
                this.statusBackground.setBackgroundResource(R.drawable.error_status);
            } else {
                this.statusBackground.setBackgroundResource(R.drawable.disconnected_status);
            }
        }

        if(!this.textStatus.equals(var1)) {
            this.textStatus = var1;
            this.statusText.setText(var1);
        }

    }

    private PreFlightStatusWidget.StatusType convertFromMessageType(Message.Type var1) {
        return var1.getValue() == Message.Type.OFFLINE.getValue()?PreFlightStatusWidget.StatusType.OFFLINE:(var1.getValue() == Message.Type.GOOD.getValue()?PreFlightStatusWidget.StatusType.GOOD:(var1.getValue() == Message.Type.WARNING.getValue()?PreFlightStatusWidget.StatusType.WARNING:PreFlightStatusWidget.StatusType.ERROR));
    }

    @MainThread
    private void updateView(Message var1) {
        if(var1 != null) {
            PreFlightStatusWidget.StatusType var2 = this.convertFromMessageType(var1.getType());
            boolean var3 = var1.shouldBlink();
            String var4 = var1.getTitle();
            this.onStatusChange(var4, var2, var3);
        }
    }

    public static enum StatusType {
        OFFLINE(0),
        GOOD(1),
        WARNING(2),
        ERROR(3);

        private final int value;

        private StatusType(int var3) {
            this.value = var3;
        }

        public int getValue() {
            return this.value;
        }
    }
}
