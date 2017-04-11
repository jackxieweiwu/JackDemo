package zkrtdrone.zkrt.com.jackmvvm.util;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * Created by jack_xie on 17-4-10.
 * 演示视图的基本所需的接口
 */

public interface PresentableView {
    /**
     * Returns string id for the description of this View. This might be shown inside the View itself.
     */
    @StringRes
    int getDescription();

    /**
     * Return the hint to user on how to find this View in code
     * @return
     */
    @NonNull
    String getHint();
}
