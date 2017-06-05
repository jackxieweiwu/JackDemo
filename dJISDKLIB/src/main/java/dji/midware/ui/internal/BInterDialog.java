package dji.midware.ui.internal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import dji.midware.R;
import dji.midware.ui.d.UiDH;

/**
 * Created by jack_xie on 17-6-1.
 */

public class BInterDialog extends Dialog implements View.OnClickListener {
    private int b;
    private int c;
    private Context d;
    private ImageView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private LinearLayout i;
    private EditText j;
    private TextView k;
    private RelativeLayout l;
    private TextView m;
    private SeekBar n;
    private TextView o;
    private TextView p;
    private ImageView q;
    private SeekBar.OnSeekBarChangeListener r;
    protected static UiDH.dhEnumA a;
    private dialogInterfaceA dialogInterfaceAS;
    private int t;

    public BInterDialog(Context var1) {
        this(var1, R.dimen.dul_sliding_dialog_width);
    }

    public BInterDialog(Context var1, int var2) {
        super(var1);
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.dialogInterfaceAS = null;
        this.d = var1;
        this.t = var2;
        this.c();
    }

    public BInterDialog a(dialogInterfaceA var1) {
        this.dialogInterfaceAS = var1;
        return this;
    }

    public BInterDialog a(int var1) {
        if(0 == var1) {
            this.e.setBackgroundResource(R.drawable.leftmenu_popup_alert);
            this.f.setTextColor(this.d.getResources().getColor(R.color.green));
            this.g.setTextColor(this.d.getResources().getColor(R.color.green));
        } else if(1 == var1) {
            this.e.setBackgroundResource(R.drawable.leftmenu_popup_alert);
            this.f.setTextColor(this.d.getResources().getColor(R.color.yellow_medium));
            this.g.setTextColor(this.d.getResources().getColor(R.color.yellow_medium));
        } else if(2 == var1) {
            this.e.setBackgroundResource(R.drawable.leftmenu_popup_warning);
            this.f.setTextColor(this.d.getResources().getColor(R.color.red_light));
            this.g.setTextColor(this.d.getResources().getColor(R.color.red_light));
        } else if(3 == var1) {
            this.e.setBackgroundResource(R.drawable.leftmenu_popup_warning);
            this.f.setTextColor(this.d.getResources().getColor(R.color.red_light));
            this.g.setTextColor(this.d.getResources().getColor(R.color.red_light));
        } else if(4 == var1) {
            this.e.setBackgroundResource(R.drawable.leftmenu_popup_greencheck);
            this.f.setTextColor(this.d.getResources().getColor(R.color.green));
            this.g.setTextColor(this.d.getResources().getColor(R.color.green));
        } else if(6 == var1) {
            this.e.setVisibility(8);
            this.f.setTextColor(this.d.getResources().getColor(R.color.white));
            this.g.setVisibility(8);
        }

        return this;
    }

    public BInterDialog b(int var1) {
        this.e.setBackgroundResource(var1);
        return this;
    }

    public BInterDialog a(String var1) {
        this.f.setText(var1);
        return this;
    }

    public BInterDialog a(int var1, String var2) {
        this.g.setVisibility(var1);
        this.g.setText(var2);
        return this;
    }

    public BInterDialog b(String var1) {
        this.h.setText(var1);
        return this;
    }

    public BInterDialog c(int var1) {
        this.p.setVisibility(var1);
        this.q.setVisibility(var1);
        return this;
    }

    public BInterDialog a(int var1, int var2) {
        this.i.setVisibility(var1);
        if(a == UiDH.dhEnumA.b) {
            this.k.setText(R.string.setting_foot);
            var2 = (int)UiDH.b((float)var2);
        } else if(a == UiDH.dhEnumA.a) {
            this.k.setText(R.string.setting_metric);
        }

        this.j.setText(String.valueOf(var2));
        return this;
    }

    public BInterDialog c(String var1) {
        this.m.setText(var1);
        return this;
    }

    public BInterDialog d(int var1) {
        this.n.setProgress(0);
        this.l.setVisibility(var1);
        return this;
    }

    private void b() {
        this.r = new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar var1) {
                d();
            }

            public void onStartTrackingTouch(SeekBar var1) {
            }

            public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
            }
        };
    }

    public void show() {
        this.getWindow().setFlags(8, 8);
        super.show();
    }

    private void c() {
        this.requestWindowFeature(1);
        this.b();
        this.setContentView(R.layout.widget_sliding_dlg);
        this.e = (ImageView)this.findViewById(R.id.imageview_dlg_title_icon);
        this.f = (TextView)this.findViewById(R.id.textview_dlg_title);
        this.g = (TextView)this.findViewById(R.id.textview_dlg_little_title);
        this.h = (TextView)this.findViewById(R.id.textview_dlg_desc);
        this.i = (LinearLayout)this.findViewById(R.id.linearlayout_dlg_edit_text);
        this.j = (EditText)this.findViewById(R.id.edittext_value);
        this.k = (TextView)this.findViewById(R.id.textview_value_unit);
        this.l = (RelativeLayout)this.findViewById(R.id.relativelayout_slidebar);
        this.m = (TextView)this.findViewById(R.id.textview_slidertitle);
        this.n = (SeekBar)this.findViewById(R.id.seekbar_slider);
        this.o = (TextView)this.findViewById(R.id.textview_button_cancel);
        this.p = (TextView)this.findViewById(R.id.textview_button_ok);
        this.q = (ImageView)this.findViewById(R.id.imageview_divider);
        this.o.setOnClickListener(this);
        this.p.setOnClickListener(this);
        this.n.setOnSeekBarChangeListener(this.r);
        this.n.setPadding(0, 0, 0, 0);
    }

    private void d() {
        int var1 = this.n.getProgress();
        if(var1 >= 95) {
            this.n.setProgress(100);
            this.a(true);
        } else {
            this.n.setProgress(0);
            this.a(false);
        }

    }

    private void a(boolean var1) {
        if(null != dialogInterfaceAS) {
            this.dialogInterfaceAS.onCbChecked(this, var1, 0);
        }

    }

    private void e() {
        if(null != dialogInterfaceAS) {
            this.dialogInterfaceAS.onLeftBtnClick(this, 0);
        }

    }

    private void f() {
        if(null != dialogInterfaceAS) {
            this.dialogInterfaceAS.onRightBtnClick(this, 0);
        }

    }

    protected void onCreate(Bundle var1) {
        this.a((int)this.getContext().getResources().getDimension(this.t), -2, 0, 17, true, false);
    }

    public void a(int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
        WindowManager.LayoutParams var7 = this.getWindow().getAttributes();
        this.b = var1;
        this.c = var2;
        var7.width = var1;
        var7.height = var2;
        var7.y = var3;
        var7.flags &= -3;
        var7.gravity = var4;
        this.getWindow().setAttributes(var7);
        this.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        this.setCancelable(var5);
        this.setCanceledOnTouchOutside(var6);
    }

    public void onClick(View var1) {
        int var2 = var1.getId();
        if(var2 == R.id.textview_button_cancel) {
            this.e();
        } else if(var2 == R.id.textview_button_ok) {
            this.f();
        }

    }

    public void a() {
        this.o.setText(R.string.app_no);
        this.p.setText(R.string.app_yes);
    }

    static {
        a = UiDH.dhEnumA.a;
    }

    public interface dialogInterfaceA {
        void onLeftBtnClick(DialogInterface var1, int var2);

        void onRightBtnClick(DialogInterface var1, int var2);

        void onCbChecked(DialogInterface var1, boolean var2, int var3);
    }
}
