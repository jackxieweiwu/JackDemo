package zkrtdrone.zkrt.com.jackmvvm.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;

/**
 * Created by jack_xie on 2016/12/17.
 */

public abstract class BaseMvpFragment<VB extends ViewDataBinding> extends AbsFragment<VB> {

    @Override protected void init(Bundle savedInstanceState) {
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState);

    @Override protected void onDelayLoad() {

    }

    @Override protected void dataCallback(int result, Object obj) {

    }
}
