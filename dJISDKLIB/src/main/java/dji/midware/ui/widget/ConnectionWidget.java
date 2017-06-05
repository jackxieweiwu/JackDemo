package dji.midware.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import dji.keysdk.DJIKey;
import dji.keysdk.ProductKey;
import dji.midware.R;
import dji.midware.ui.base.UiBaseNView;
import dji.midware.ui.d.UiDA;

/**
 * Created by jack_xie on 17-6-1.
 */

public class ConnectionWidget extends UiBaseNView {
    private boolean isConnected;
    private ProductKey connectionKey;
    private Bitmap bitmap;

    public ConnectionWidget(Context var1) {
        this(var1, (AttributeSet)null, 0);
    }

    public ConnectionWidget(Context var1, AttributeSet var2) {
        this(var1, var2, 0);
    }

    public ConnectionWidget(Context var1, AttributeSet var2, int var3) {
        super(var1, var2, var3);
        this.isConnected = false;
        UiDA.b();
    }

    public void initView(Context var1, AttributeSet var2, int var3) {
        this.setWillNotDraw(false);
    }

    public void initKey() {
        this.connectionKey = ProductKey.create("Connection");
        this.addDependentKey(this.connectionKey);
    }

    public void transformValue(Object var1, DJIKey var2) {
        if(var2 != null && var2.equals(this.connectionKey) && var1 != null && var1 instanceof Boolean) {
            this.isConnected = ((Boolean)var1).booleanValue();
        }

    }

    protected void onDraw(Canvas var1) {
        if(this.bitmap != null) {
            var1.drawBitmap(this.bitmap, 0.0F, 0.0F, (Paint)null);
        }

    }

    public void updateWidget(DJIKey var1) {
        if(this.isConnected) {
            this.bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.connected);
        } else {
            this.bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.disconnected);
        }

        this.invalidate();
    }
}
