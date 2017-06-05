package dji.midware.ui.a;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import dji.midware.R;
import dji.midware.ui.base.BaseFrameLayout;
import dji.midware.ui.c.UiCC;
import dji.midware.ui.b.UiBG;
import dji.midware.ui.internal.DULSwitchButton;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by root on 17-5-31.
 */

public class UiAB extends Adapter<UiAB.UiAbViewHolder> {
    private UiAB.a a;
    private ArrayList<UiCC> uiCCArrayList = new ArrayList();
    private int c = -1;
    private boolean d;

    public void a(boolean var1) {
        this.d = var1;
    }

    public UiAB(UiAB.a a) {
        this.a = a;
    }

    public void a(int var1) {
        if(var1 != -1) {
            int var2 = this.c;
            if(var1 != this.c) {
                this.c = var1;
                UiCC var3;
                if(var2 != -1) {
                    var3 = (UiCC)this.uiCCArrayList.get(var2);
                    var3.a(false);
                    this.notifyItemChanged(var2);
                }

                var3 = (UiCC)this.uiCCArrayList.get(this.c);
                var3.a(true);
                this.notifyItemChanged(this.c);
            }
        }

    }

    @Override
    public UiAbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseFrameLayout var3 = UiBG.a(parent.getContext(), UiCC.enum_b.a(viewType));
        var3.setBackgroundResource(R.drawable.selector_list_item);
        UiAbViewHolder var4 = new UiAbViewHolder(var3, this);
        var3.setTag(var4);
        return var4;
    }

    @Override
    public void onBindViewHolder(UiAbViewHolder holder, int position) {
        UiCC var3 = (UiCC)this.uiCCArrayList.get(position);
        holder.a(var3);
    }

    public int getItemViewType(int var1) {
        if(var1 < this.uiCCArrayList.size()) {
            UiCC var2 = this.c(var1);
            if(var2.d != null) {
                return var2.d.g;
            }
        }
        return 0;
    }

    public int getItemCount() {
        return this.uiCCArrayList.size();
    }

    public void a(UiCC var1) {
        this.uiCCArrayList.add(var1);
        this.notifyItemInserted(this.uiCCArrayList.size());
    }

    public int b(UiCC var1) {
        return this.uiCCArrayList.indexOf(var1);
    }

    public int b(int var1) {
        for(int var2 = 0; var2 < this.uiCCArrayList.size(); ++var2) {
            if(((UiCC)this.uiCCArrayList.get(var2)).a == var1) {
                return var2;
            }
        }

        return -1;
    }

    public UiCC c(int var1) {
        return (UiCC)this.uiCCArrayList.get(var1);
    }

    static final class UiAbViewHolder extends ViewHolder implements View.OnClickListener,DULSwitchButton.a {
        private ImageView a;
        private TextView b;
        private DULSwitchButton c;
        private ImageView d;
        private TextView e;
        private ImageView f;
        private UiAB g;

        UiAbViewHolder(View var1, UiAB var2) {
            super(var1);
            this.a = (ImageView)var1.findViewById(R.id.list_item_title_icon);
            this.b = (TextView)var1.findViewById(R.id.list_item_title);
            this.d = (ImageView)var1.findViewById(R.id.list_item_value_icon);
            this.c = (DULSwitchButton)var1.findViewById(R.id.list_item_value_switch_button);
            this.e = (TextView)var1.findViewById(R.id.list_item_value);
            this.f = (ImageView)var1.findViewById(R.id.list_item_arrow);
            this.g = var2;
            if(!var2.d) {
                var1.setOnClickListener(this);
                if(this.c != null) {
                    this.c.setOnCheckedListener(this);
                }
            }

        }

        void a(UiCC var1) {
            if(null != var1) {
                if(var1.b == 0) {
                    this.a.setVisibility(8);
                } else {
                    this.a.setVisibility(0);
                    this.a.setImageResource(var1.b);
                }

                if(var1.b().isEmpty()) {
                    this.b.setVisibility(8);
                } else {
                    this.b.setVisibility(0);
                    this.b.setText(var1.b());
                }

                if(this.d != null) {
                    if(var1.c == 0) {
                        this.d.setVisibility(8);
                    } else {
                        this.d.setVisibility(0);
                        this.d.setImageResource(var1.c);
                    }
                }

                if(this.e != null) {
                    if(TextUtils.isEmpty(var1.d())) {
                        this.e.setVisibility(8);
                    } else {
                        this.e.setVisibility(0);
                        this.e.setText(var1.d());
                        if(var1.c() != 0) {
                            this.e.setTextColor(var1.c());
                        }
                    }
                }

                if(var1.d == UiCC.enum_b.a) {
                    this.f.setVisibility(0);
                } else {
                    this.f.setVisibility(8);
                }

                if(this.c != null) {
                    if(var1.d == UiCC.enum_b.c) {
                        this.c.setVisibility(0);
                        this.c.setChecked(var1.a != 0);
                    } else {
                        this.c.setVisibility(8);
                    }
                }

                this.itemView.setSelected(var1.a());
            }

        }

        public void onClick(View var1) {
            this.a(var1);
        }

        public void onCheckedChanged(boolean var1) {
            this.a((View)this.c);
        }

        private void a(View var1) {
            if(this.g.a != null) {
                int var2 = this.getAdapterPosition();
                this.g.a.updateSelectedItem(var1, var2);
            }

        }
    }

    public interface a {
        void updateSelectedItem(View var1, int var2);
    }
}
