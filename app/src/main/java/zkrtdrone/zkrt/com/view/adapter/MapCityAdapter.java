package zkrtdrone.zkrt.com.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arialyy.absadapter.common.AbsHolder;
import com.arialyy.absadapter.listview.AbsLvAdapter;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;

import java.util.List;
import butterknife.Bind;
import zkrtdrone.zkrt.com.R;

/**
 * Created by jack_xie on 17-5-6.
 */

public class MapCityAdapter extends AbsLvAdapter<MKOLSearchRecord,MapCityAdapter.MapCityHolder>{

    private int mSelect = -1;
    public MapCityAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int setLayoutId(int type) {
        return R.layout.city_list_item;
    }

    public void setIndItem(int miss){
        mSelect = miss;
    }

    @Override
    public void bindData(int position, MapCityHolder holder, final MKOLSearchRecord item) {
        if(mSelect==position){
            holder.relayout_item_color.setBackgroundResource(R.color.air_speed_units);  //选中项背景
        }else{
            holder.relayout_item_color.setBackgroundResource(android.R.color.transparent);  //其他项背景
        }
        holder.map_city_txt.setText(item.cityName+"");
    }

    @Override
    public MapCityHolder getViewHolder(View convertView) {
        return new MapCityHolder(convertView);
    }

    /**
     * 创建相应的Holder
     */
    public class MapCityHolder extends AbsHolder {
        @Bind(R.id.map_city_txt) TextView map_city_txt;
        @Bind(R.id.relayout_item_color) RelativeLayout relayout_item_color;
        //@Bind(R.id.mao_city_img) ImageView mao_city_img;

        public MapCityHolder(View itemView) {
            super(itemView);
        }
    }
}
