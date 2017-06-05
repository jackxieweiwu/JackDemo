package dji.midware.ui.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import java.util.List;

import dji.midware.R;
import dji.midware.ui.a.UiAA;
import dji.midware.ui.c.UiCA;
import dji.midware.ui.c.UiCB;

/**
 * Created by jack_Xie on 17-6-1.
 */

public abstract class UiBaseJView extends UiBaseLView {
    protected UiAA a;
    protected ExpandableListView.OnGroupClickListener b;
    protected OnClickListener c;
    protected UiAA.c d;
    protected int e = 2147483647;
    protected int f = 2147483647;
    protected String[] g;
    protected TypedArray h;
    protected LinearLayout i;
    protected int[][] j;
    protected ExpandableListView k;

    public UiBaseJView(Context var1) {
        super(var1, (AttributeSet)null, 0);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        LayoutInflater var4 = (LayoutInflater)var1.getSystemService("layout_inflater");
        var4.inflate(R.layout.widget_expandable_list_view, this);
        this.a();
    }

    private void a() {
        this.b = new ExpandableListView.OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView var1, View var2, int var3, long var4) {
                if(!a(var1, var2, var3, var4)) {
                    ;
                }

                return true;
            }
        };
        this.c = new OnClickListener() {
            public void onClick(View var1) {
                a(var1.getTag());
            }
        };
        d = new UiAA.c() {
            public void a(UiCB var1, UiCA var2) {
                a(var1, var2);
            }

            public void b(UiCB var1, UiCA var2) {
            }

            public void a(UiCB var1, UiCA var2, boolean var3) {
            }
        };
        this.a = new UiAA(this.context);
        this.a.a(this.c);
        this.a.a(this.d);
        this.k = (ExpandableListView)this.findViewById(R.id.expandable_list_view_content);
        this.k.setGroupIndicator((Drawable)null);
        this.k.setOnGroupClickListener(this.b);
        this.k.setAdapter(this.a);
    }

    protected void a(int var1, int var2, int var3) {
        if(null != this.a && !this.a.a().isEmpty()) {
            List var4 = this.a.a();
            int var5 = 0;

            for(int var6 = var4.size(); var5 < var6; ++var5) {
                UiCB var7 = (UiCB)var4.get(var5);
                int var8;
                int var9;
                UiCA var10;
                if(var7.d == var2) {
                    if(!this.k.isGroupExpanded(var5)) {
                        this.k.expandGroup(var5, true);
                        this.k.setSelectedGroup(var5);
                    }

                    var7.a(true);
                    if(var7.f == 2) {
                        var7.e = var3;
                        ((UiCA)var7.uiCAArrayList.get(0)).b = var3;
                    } else {
                        var8 = 0;

                        for(var9 = var7.uiCAArrayList.size(); var8 < var9; ++var8) {
                            var10 = (UiCA)var7.uiCAArrayList.get(var8);
                            if(var10.b == var3) {
                                var10.d = true;
                                var7.c = var10.a;
                                var7.e = var3;
                                if(this.j != null && var2 < this.j.length && var3 < this.j[var2].length) {
                                    var7.a = this.j[var2][var3];
                                }
                            } else {
                                var10.d = false;
                            }
                        }
                    }
                } else if(var7.d == var1) {
                    this.k.collapseGroup(var5);
                    var7.a(false);
                    var8 = 0;

                    for(var9 = var7.uiCAArrayList.size(); var8 < var9; ++var8) {
                        var10 = (UiCA)var7.uiCAArrayList.get(var8);
                        var10.d = false;
                    }
                } else {
                    this.k.collapseGroup(var5);
                    var7.a(false);
                }
            }

            this.a.notifyDataSetChanged();
        }

    }

    protected abstract boolean a(ExpandableListView var1, View var2, int var3, long var4);

    protected void a(Object var1) {
    }

    protected void a(UiCB var1, UiCA var2) {
    }
}
