package dji.midware.ui.antistatic.spinnerwheel.a

import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup

/**
 * Created by jack_Xie on 17-5-31.
 */
interface AnSpinner_C {
    abstract fun b(): Int

    abstract fun a(var1: Int, var2: View, var3: ViewGroup): View

    abstract fun a(var1: View, var2: ViewGroup): View

    abstract fun add_DataSetObserverList(var1: DataSetObserver)

    abstract fun remove_DataSetObserverList(var1: DataSetObserver)
}