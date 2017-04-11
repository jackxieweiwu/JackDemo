package zkrtdrone.zkrt.com.jackmvvm.mvvm.core;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Window;

import butterknife.ButterKnife;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.module.AbsModule;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.module.IOCProxy;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.StringUtil;

/**
 * Created by jack_xie on 17-4-9.
 * 继承Dialog
 */

public abstract class AbsDialog extends Dialog {
    protected String TAG = "";
    private Object mObj;    //被观察者
    private IOCProxy mProxy;
    private DialogSimpleModule mSimpleModule;
    private ModuleFactory mModuleF;

    public AbsDialog(Context context) {
        this(context, null);
    }

    /**
     * @param obj Dialog的寄主
     */
    public AbsDialog(Context context, Object obj) {
        super(context);
        mObj = obj;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        initDialog();
    }

    private void initDialog() {
        TAG = StringUtil.getClassName(this);
        mProxy = IOCProxy.newInstance(this);
        mModuleF = ModuleFactory.newInstance();
        if (mObj != null) {
            mSimpleModule = new DialogSimpleModule(getContext());
            IOCProxy.newInstance(mObj, mSimpleModule);
        }
    }

    /**
     * 获取简单打Moduel回调，这个一般用于回调数据给寄主
     */
    protected DialogSimpleModule getSimplerModule() {
        if (mObj == null) {
            throw new NullPointerException("必须设置寄主对象");
        }
        return mSimpleModule;
    }

    /**
     * 获取Module
     *
     * @param clazz {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(Class<M> clazz) {
        M module = mModuleF.getModule(getContext(), clazz);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 获取Module
     *
     * @param clazz Module class0
     * @param callback Module回调函数
     * @param <M> {@link AbsModule}
     */
    protected <M extends AbsModule> M getModule(@NonNull Class<M> clazz,
                                                @NonNull AbsModule.OnCallback callback) {
        M module = mModuleF.getModule(getContext(), clazz);
        module.setCallback(callback);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 设置资源布局
     */
    protected abstract int setLayoutId();

    /**
     * 数据回调
     */
    protected abstract void dataCallback(int result, Object obj);
}
