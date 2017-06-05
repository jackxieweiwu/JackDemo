package dji.midware.ui.a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import dji.midware.R;
import dji.midware.ui.b.UiBB;
import dji.midware.ui.b.UiBC;
import dji.midware.ui.b.UiBD;
import dji.midware.ui.c.UiCA;
import dji.midware.ui.c.UiCB;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-5-31.
 */

public class UiAA extends BaseExpandableListAdapter {
    private static final int[] a;
    private static final int b;
    private List<UiCB> uiCBList = null;
    private UiCB uiCB;
    private View.OnClickListener e = null;
    private UiAA.c f = null;
    private SeekBar.OnSeekBarChangeListener g = null;
    private static Context mContext;

    public UiAA(Context context) {
        this.mContext = context;
        this.g = new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar var1) {
                Object var2 = var1.getTag();
                if(var2 instanceof uiD) {
                    uiD var3 = (uiD)var2;
                    int var4 = (int)(var3.d / 1000L);
                    int var5 = (int)(var3.d % 1000L);
                    Object var6 = UiAA.this.getGroup(var4);
                    if(var6 instanceof UiCB) {
                        int var7 = var1.getProgress();
                        UiCB var8 = (UiCB)var6;
                        UiCA var9 = (UiCA)var8.uiCAArrayList.get(var5);
                        var9.b = var7 + var9.f;
                        if(var9.e instanceof String) {
                            var3.c.setText(String.valueOf(var9.b) + var9.e);
                        } else {
                            var3.c.setText(String.valueOf(var9.b));
                        }

                        UiAA.this.f.a(var8, var9);
                    }
                }

            }

            public void onStartTrackingTouch(SeekBar var1) {
                Object var2 = var1.getTag();
                if(var2 instanceof uiD) {
                    uiD var3 = (uiD)var2;
                    int var4 = (int)(var3.d / 1000L);
                    int var5 = (int)(var3.d % 1000L);
                    Object var6 = UiAA.this.getGroup(var4);
                    if(var6 instanceof UiCB) {
                        UiCB var7 = (UiCB)var6;
                        UiCA var8 = (UiCA)var7.uiCAArrayList.get(var5);
                        UiAA.this.f.b(var7, var8);
                    }
                }

            }

            public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
                Object var4 = var1.getTag();
                if(var4 instanceof uiD) {
                    uiD var5 = (uiD)var4;
                    int var6 = (int)(var5.d / 1000L);
                    int var7 = (int)(var5.d % 1000L);
                    Object var8 = UiAA.this.getGroup(var6);
                    if(var8 instanceof UiCB) {
                        UiCB var9 = (UiCB)var8;
                        UiCA var10 = (UiCA)var9.uiCAArrayList.get(var7);
                        if(var3) {
                            if(var10.e instanceof String) {
                                var5.c.setText(String.valueOf(var2 + var10.f) + var10.e);
                            } else {
                                var5.c.setText(String.valueOf(var2 + var10.f));
                            }
                        }

                        UiAA.this.f.a(var9, var10, var3);
                    }
                }

            }
        };
    }

    public void a(List<UiCB> var1) {
        this.uiCBList = var1;
        this.notifyDataSetChanged();
    }

    public List<UiCB> a() {
        return this.uiCBList;
    }

    public void a(View.OnClickListener var1) {
        this.e = var1;
    }

    public void a(UiAA.c var1) {
        this.f = var1;
    }

    public int getGroupCount() {
        return this.uiCBList != null?this.uiCBList.size():0;
    }

    public int getChildrenCount(int var1) {
        int var2 = 0;
        if(this.uiCBList != null && !this.uiCBList.isEmpty() && 0 <= var1 && var1 < this.uiCBList.size()) {
            UiCB var3 = (UiCB)this.uiCBList.get(var1);
            var2 = var3.uiCAArrayList.size();
            if(0 != var2 && var3.f == 1) {
                var2 = (var2 - 1) / b + 1;
            }
        }

        return var2;
    }

    public Object getGroup(int var1) {
        UiCB var2 = null;
        if(this.uiCBList != null && !this.uiCBList.isEmpty() && 0 <= var1 && var1 < this.uiCBList.size()) {
            var2 = (UiCB)this.uiCBList.get(var1);
        }

        return var2;
    }

    public Object getChild(int var1, int var2) {
        return null;
    }

    public long getGroupId(int var1) {
        return (long)var1;
    }

    public long getChildId(int var1, int var2) {
        return (long)(var1 * 1000 + var2);
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getGroupView(int var1, boolean var2, View var3, ViewGroup var4) {
        Object var5 = this.getGroup(var1);
        if(var5 instanceof UiCB) {
            uiB var6;
            if(null != var3 && ((View)var3).getTag() != null) {
                var6 = (uiB)((View)var3).getTag();
            } else {
                var6 = new uiB();
                var3 = new UiBD(var4.getContext());
                var6.a = ((View)var3).findViewById(R.id.expendable_group_layout);
                var6.b = (ImageView)((View)var3).findViewById(R.id.expandable_group_icon);
                var6.c = (TextView)((View)var3).findViewById(R.id.expandable_group_name);
                var6.d = (TextView)((View)var3).findViewById(R.id.expandable_group_value);
                var6.e = ((View)var3).findViewById(R.id.expandable_group_value_bg);
                ((View)var3).setTag(var6);
            }

            var6.a((UiCB)var5);
        }

        return (View)var3;
    }

    public View getChildView(int var1, int var2, boolean var3, View var4, ViewGroup var5) {
        Object var6 = this.getGroup(var1);
        if(var6 instanceof UiCB) {
            UiCB var7 = (UiCB)var6;
            if(1 == var7.f) {
                uiA var8;
                if(null != var4) {
                    var8 = (uiA)((View)var4).getTag();
                } else {
                    var8 = new uiA();
                    var4 = new UiBB(var5.getContext());
                    var8.view = ((View)var4).findViewById(R.id.expandable_child_layout);

                    for(int var9 = 0; var9 < b; ++var9) {
                        var8.textView= (TextView)((View)var4).findViewById(a[var9]);
                        var8.textView.setOnClickListener(this.e);
                    }

                    ((View)var4).setTag(var8);
                }

                var8.a(var7, var2);
            } else if(2 == var7.f) {
                uiD var10;
                if(null == var4) {
                    var10 = new uiD();
                    var4 = new UiBC(var5.getContext());
                    var10.b = (SeekBar)((View)var4).findViewById(R.id.expandable_child_sb);
                    var10.c = (TextView)((View)var4).findViewById(R.id.expandable_child_sb_tv);
                    ((View)var4).setTag(var10);
                    var10.b.setOnSeekBarChangeListener(this.g);
                } else {
                    var10 = (uiD)((View)var4).getTag();
                }

                var10.a(var7, var1, var2);
            }
        }

        return (View)var4;
    }

    public boolean isChildSelectable(int var1, int var2) {
        return true;
    }

    public UiCB a(int var1) {
        UiCB var2 = null;
        if(this.uiCBList != null) {
            Iterator var3 = this.uiCBList.iterator();

            while(var3.hasNext()) {
                UiCB var4 = (UiCB)var3.next();
                if(var4.d == var1) {
                    var2 = var4;
                    break;
                }
            }
        }

        return var2;
    }

    public void a(UiCB var1) {
        if(var1 != null && var1 != this.uiCB) {
            if(this.uiCB != null) {
                this.uiCB.a(false);
            }

            var1.a(true);
            this.uiCB = var1;
            this.notifyDataSetChanged();
        }
    }

    static {
        a = new int[]{R.id.child_value1, R.id.child_value2, R.id.child_value3};
        b = a.length;
    }

    private final class uiD {
        private SeekBar b;
        private TextView c;
        private long d;

        private uiD() {
            this.b = null;
            this.c = null;
            this.d = 0L;
        }

        public void a(UiCB var1, int var2, int var3) {
            this.d = UiAA.this.getChildId(var2, var3);
            UiCA var4 = (UiCA)var1.uiCAArrayList.get(var3);
            this.b.setTag(this);
            this.b.setMax(var4.g - var4.f);
            this.b.setProgress(var4.b - var4.f);
            if(var4.e instanceof String) {
                this.c.setText(String.valueOf(var4.b) + var4.e);
            } else {
                this.c.setText(String.valueOf(var4.b));
            }

        }
    }

    private static final class uiA {
        public View view;
        public TextView textView;

        private uiA() {
            this.view = null;
            this.textView = new TextView(mContext);
        }

        public void a(UiCB uiCB, int var2) {
            int var3 = 0;
            var2 *= UiAA.b;
            ArrayList var4 = uiCB.uiCAArrayList;

            for(int var5 = 0; var5 < UiAA.b; ++var5) {
                this.textView.setVisibility(View.INVISIBLE);
            }

            while(var2 + var3 < var4.size() && var3 < UiAA.b) {
                UiCA var6 = (UiCA)var4.get(var2 + var3);
                this.textView.setTag(var6);
                this.textView.setVisibility(View.VISIBLE);
                this.textView.setSelected(var6.d);
                this.textView.setText(var6.a);
                ++var3;
            }

        }
    }

    private static final class uiB {
        public View a;
        public ImageView b;
        public TextView c;
        public TextView d;
        public View e;

        private uiB() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
        }

        public void a(UiCB var1) {
            this.a.setSelected(var1.a());
            if(var1.a() && !var1.uiCAArrayList.isEmpty() && var1.f == 1) {
                this.d.setVisibility(View.VISIBLE);
                this.e.setVisibility(View.VISIBLE);
            } else {
                this.d.setVisibility(View.INVISIBLE);
                this.e.setVisibility(View.GONE);
            }

            if(var1.a == 0) {
                this.b.setVisibility(View.GONE);
            } else {
                this.b.setVisibility(View.VISIBLE);
                this.b.setImageResource(var1.a);
            }

            this.c.setText(var1.b);
            this.d.setText(var1.c);
        }
    }

    public interface c {
        void a(UiCB var1, UiCA var2);

        void b(UiCB var1, UiCA var2);

        void a(UiCB var1, UiCA var2, boolean var3);
    }
}
