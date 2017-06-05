package dji.midware.ui.b;

import android.content.Context;

import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.UiCC.enum_b;
/**
 * Created by jack_xie on 17-6-1.
 */

public class UiBG {
    public static BaseFrameLayout a(Context context, enum_b var1) {
        return (BaseFrameLayout)(var1 == enum_b.e?new UiBD(context):(var1 == enum_b.b?new UiBA(context):(var1 == enum_b.c?new UIBH(context):(var1 == enum_b.d?new UiBE(context):(var1 == enum_b.f?new UiBI(context):new UiBF(context))))));
    }
}
