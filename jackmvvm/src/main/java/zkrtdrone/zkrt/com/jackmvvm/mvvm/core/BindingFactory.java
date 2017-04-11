package zkrtdrone.zkrt.com.jackmvvm.mvvm.core;

import android.databinding.ViewDataBinding;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jack_xie on 17-4-9.
 * Binding工厂
 */

public class BindingFactory {
    private final String TAG = "BindingFactory";

    private Map<Integer, ViewDataBinding> mBindings = new ConcurrentHashMap<>();

    private BindingFactory() {

    }

    /**
     * 需要保证每个对象都有独立的享元工厂
     */
    public static BindingFactory newInstance() {
        return new BindingFactory();
    }

    /**
     * 获取Binding
     *
     * @param obj Module寄主
     */
    public <VB extends ViewDataBinding> VB getBinding(Object obj, Class<VB> clazz) {
        VB vb = (VB) mBindings.get(clazz.hashCode());
        if (vb == null) {
            vb = loadBind(obj, clazz);
        }
        return vb;
    }

    /**
     * 从其它组件中加载binding
     *
     * @param obj Module寄主
     * @param clazz 具体的Binding
     * @param <VB> ViewDataBinding
     */
    private <VB extends ViewDataBinding> VB loadBind(Object obj, Class<VB> clazz) {
        VB vb = null;
        if (obj instanceof AbsActivity) {
            vb = (VB) ((AbsActivity) obj).getBinding();
        } else if (obj instanceof AbsFragment) {
            vb = (VB) ((AbsFragment) obj).getBinding();
        } else if (obj instanceof AbsDialogFragment) {
            vb = (VB) ((AbsDialogFragment) obj).getBinding();
        }
        mBindings.put(clazz.hashCode(), vb);
        return vb;
    }
}
