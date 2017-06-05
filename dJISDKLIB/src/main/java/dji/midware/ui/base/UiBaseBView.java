package dji.midware.ui.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import dji.midware.R;
import dji.midware.ui.c.a.UiCAB;
import dji.midware.ui.c.b.UiCBI;

/**
 * Created by jack_xie on 17-6-1.
 */

public abstract class UiBaseBView extends DULFrameLayout{
    protected TextView valueText;
    private UiCBI widgetAppearances;

    public UiBaseBView(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        super.initView(var1, var2, var3);
        TextView var4 = (TextView)this.getViewById(R.id.TextView_camera_info_title);
        this.valueText = (TextView)this.getViewById(R.id.TextView_camera_info_value);
        var4.setText(this.getTitle());
    }

    protected UiCAB getWidgetAppearances() {
        if(this.widgetAppearances == null) {
            this.widgetAppearances = new UiCBI();
        }

        return this.widgetAppearances;
    }

    protected abstract String getTitle();
}
