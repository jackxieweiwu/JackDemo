package zkrtdrone.zkrt.com.widght;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by jack_xie on 17-4-26.
 */

public class FitsSystemWindowsFrameLayout extends FrameLayout {
    public FitsSystemWindowsFrameLayout(Context context) {
        super(context);
    }

    public FitsSystemWindowsFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitsSystemWindowsFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        final Rect insetsCopy = new Rect(insets);
        super.fitSystemWindows(insetsCopy);
        return false;
    }
}
