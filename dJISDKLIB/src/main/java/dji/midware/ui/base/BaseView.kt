package dji.midware.ui.base

import android.content.Context
import android.util.AttributeSet
import dji.keysdk.DJIKey
import dji.thirdparty.rx.Observable

/**
 * Created by jack_Xie on 17-5-31.
 */
interface BaseView {
    abstract fun initView(var1: Context, var2: AttributeSet, var3: Int)

    abstract fun initKey()

    abstract fun transformValueObservable(var1: Any, var2: DJIKey): Observable<Boolean>

    abstract fun transformValue(var1: Any, var2: DJIKey)

    abstract fun updateWidgetObservable(var1: DJIKey): Observable<Boolean>

    abstract fun getDependentKeys(): List<DJIKey>
}