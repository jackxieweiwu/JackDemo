package zkrtdrone.zkrt.com.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arialyy.absadapter.listview.AbsLvAdapter;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;

import java.util.List;

import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.view.adapter.holder.MapHolder;

/**
 * Created by jack_xie on 17-5-6.
 */

public class MapCityAdapter extends AbsLvAdapter<MKOLSearchRecord,MapHolder>{
    TextView map_city_txt;
    ImageView mao_city_img;
    public MapCityAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int setLayoutId(int type) {
        return R.layout.city_list_item;
    }

    @Override
    public void bindData(int position, MapHolder holder, MKOLSearchRecord item) {
        map_city_txt.setText(item.cityName+"");
    }

    @Override
    public MapHolder getViewHolder(View convertView) {
        map_city_txt = (TextView) convertView.findViewById(R.id.map_city_txt);
        mao_city_img = (ImageView) convertView.findViewById(R.id.mao_city_img);
        return new MapHolder(convertView);
    }
}
