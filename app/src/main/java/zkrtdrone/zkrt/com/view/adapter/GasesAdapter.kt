package zkrtdrone.zkrt.com.view.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import com.arialyy.absadapter.listview.AbsSimpleAdapter
import com.arialyy.absadapter.listview.AbsSimpleViewHolder
import zkrtdrone.zkrt.com.R
import zkrtdrone.zkrt.com.bean.Gases
import zkrtdrone.zkrt.com.widght.NumberSeekbar

/**
 * Created by jack_xie on 17-6-8.
 */
class GasesAdapter(context: Context, mData: List<Gases>, itemLayoutId: Int) : AbsSimpleAdapter<Gases>(context, mData, itemLayoutId) {

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun convert(helper: AbsSimpleViewHolder, item: Gases) {
        helper.setText(R.id.txt_moudle_name, item.gasName)
        val dulSeekBar = helper.getView<NumberSeekbar>(R.id.moudle_seekbar)
        dulSeekBar.max = item.gasMaxNum
        dulSeekBar.setTextSize(20)// 设置字体大小
        dulSeekBar.setBitmap(Color.RED)
        dulSeekBar.setTextColor(Color.WHITE)// 颜色
        dulSeekBar.setProgress(item.gasNum, true)
        dulSeekBar.setImagePadding(0, 5)
    }
}