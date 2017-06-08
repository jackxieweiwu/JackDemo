package zkrtdrone.zkrt.com.view.adapter

import android.content.Context
import com.arialyy.absadapter.listview.AbsSimpleAdapter
import com.arialyy.absadapter.listview.AbsSimpleViewHolder
import zkrtdrone.zkrt.com.R
import zkrtdrone.zkrt.com.bean.Moudle

/**
 * Created by jack_xie on 17-6-6.
 */

class MoudleAdapter(context: Context, mData: List<Moudle>, itemLayoutId: Int) : AbsSimpleAdapter<Moudle>(context, mData, itemLayoutId) {

    override fun convert(helper: AbsSimpleViewHolder, item: Moudle) {
        helper.setText(R.id.txt_moudle_name, item.getName())
        helper.setImageResource(R.id.moudle_img_sx, item.getDrawableID())
    }
}