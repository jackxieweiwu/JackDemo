package zkrtdrone.zkrt.com.jackmvvm.mvvm.temp;

import android.view.View;

/**
 * Created by jack_xie on 17-4-9.
 */

public interface OnTempBtClickListener {
    /**
     * @param type {@link ITempView#ERROR}, {@link ITempView#DATA_NULL}, {@link ITempView#LOADING}
     */
    public void onBtTempClick(View view, int type);
}
