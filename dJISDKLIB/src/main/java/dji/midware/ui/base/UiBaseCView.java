package dji.midware.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import dji.midware.R;
import dji.midware.ui.d.UiDH.dhEnumA;
import dji.midware.ui.internal.BInterDialog;

/**
 * Created by jack_xie on 17-6-1.
 */

public abstract class UiBaseCView extends DULFrameLayout implements View.OnClickListener {
    protected int defaultResId = 0;
    protected ImageButton imageBackground;
    protected ImageView imageForeground;
    protected static dhEnumA value_Unit_Type;

    public static void setWidgetUnitType(dhEnumA var0) {
        value_Unit_Type = var0;
    }

    public UiBaseCView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    protected void showErrorDlg(String var1) {
        Context var2 = this.getContext();
        BInterDialog var3 = new BInterDialog(var2);
        var3.a(3);
        var3.a(new BInterDialog.dialogInterfaceA() {
            public void onRightBtnClick(DialogInterface var1, int var2) {
                var1.dismiss();
            }

            public void onLeftBtnClick(DialogInterface var1, int var2) {
                var1.dismiss();
            }

            public void onCbChecked(DialogInterface var1, boolean var2, int var3) {
            }
        });
        var3.a(8, 0).d(8).c(8);
        var3.a(8, "");
        var3.a(var2.getString(R.string.error));
        var3.b(var1);
        var3.show();
    }

    static {
        value_Unit_Type = dhEnumA.a;
    }
}
