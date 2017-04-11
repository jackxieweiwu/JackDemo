package zkrtdrone.zkrt.com.jackmvvm.mvvm.core;

import android.content.Context;
import android.os.Bundle;

import zkrtdrone.zkrt.com.jackmvvm.mvvm.module.AbsModule;

/**
 * Created by jack_xie on 17-4-9.
 * 框架提供的默认的对话框的Module
 */

public class DialogSimpleModule extends AbsModule {
    public DialogSimpleModule(Context context) {
        super(context);
    }

    /**
     * 统一的回调
     */
    public void onDialog(int result, Object data) {
        callback(result, data);
    }

    /**
     * 可设置参数和回调名的回调函数
     */
    @Deprecated public void onDialog(String methodName, Class<?> param, Object data) {
        callback(methodName, param, data);
    }

    /**
     * Dialog的默认回调
     *
     * @param b 需要回调的数据
     */
    @Deprecated public void onDialog(Bundle b) {
        callback("onDialog", Bundle.class, b);
    }
}
