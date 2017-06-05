package dji.midware.ui.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by jack_xie on 17-6-1.
 */

public class RecyclerListView extends RecyclerView {
    public RecyclerListView(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public RecyclerListView(Context var1, @Nullable AttributeSet var2) {
        super(var1, var2, 0);
    }

    public RecyclerListView(Context var1, @Nullable AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void setAdapter(Adapter var1) {
        this.getItemAnimator().setChangeDuration(0L);
        this.setLayoutManager(new LinearLayoutManager(this.getContext()));
        this.setHasFixedSize(true);
        this.swapAdapter(var1, true);
    }
}
