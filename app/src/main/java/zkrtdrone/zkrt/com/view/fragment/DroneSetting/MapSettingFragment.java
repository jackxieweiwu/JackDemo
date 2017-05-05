package zkrtdrone.zkrt.com.view.fragment.DroneSetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import zkrtdrone.zkrt.com.JackApplication;
import zkrtdrone.zkrt.com.R;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.core.AbsFragment;
import zkrtdrone.zkrt.com.jackmvvm.mvvm.util.show.T;
import zkrtdrone.zkrt.com.view.adapter.MapCityAdapter;

/**
 * Created by root on 17-5-5.
 */

public class MapSettingFragment extends AbsFragment implements MKOfflineMapListener{
    private MKOfflineMap mOffline = null;
    @Bind(R.id.allcitylist)ListView allcitylist;
    @Bind(R.id.localmaplist)ListView localMapListView;
    @Bind(R.id.citylist_layout)LinearLayout cl;
    @Bind(R.id.localmap_layout)LinearLayout lm;
    /**
     * 已下载的离线地图信息列表
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;
    private LocalMapAdapter lAdapter = null;
    private int cityid;
    private MapCityAdapter mapCityAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        // 获取所有支持离线地图的城市
        //ArrayList<String> allCities = new ArrayList<String>();
        //final ArrayList<String> allCityNames = new ArrayList<String>();
        final ArrayList<MKOLSearchRecord> records2 = mOffline.getOfflineCityList();
        /*if (records2 != null) {
            for (MKOLSearchRecord r : records2) {
                allCities.add(r.cityName + "(" + r.cityID + ")" + "   --"
                        + this.formatDataSize(r.size));
                allCityNames.add(r.cityName);
            }
        }*/
        mapCityAdapter = new MapCityAdapter(mActivity,records2);
        allcitylist.setAdapter(mapCityAdapter);
        allcitylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //cityNameView.setText(allCityNames.get(i));
                cityid = records2.get(i).cityID;
            }
        });
        lm.setVisibility(View.GONE);
        cl.setVisibility(View.VISIBLE);

        // 获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<MKOLUpdateElement>();
        }

        lAdapter = new LocalMapAdapter();
        localMapListView.setAdapter(lAdapter);
    }

    public String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }

    @Override
    protected int setLayoutId() {
        mOffline = new MKOfflineMap();
        mOffline.init(this);
        return R.layout.setting_map_fragment;
    }

    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<MKOLUpdateElement>();
        }
        lAdapter.notifyDataSetChanged();
    }


    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    updateView();
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }
    }

    /**
     * 切换至城市列表
     *
     * @param view
     */
    @OnClick(R.id.clButton)
    public void clickCityListButton(View view) {
        lm.setVisibility(View.GONE);
        cl.setVisibility(View.VISIBLE);
    }

    /**
     * 切换至下载管理列表
     *
     * @param view
     */
    @OnClick(R.id.localButton)
    public void clickLocalMapListButton(View view) {
        lm.setVisibility(View.VISIBLE);
        cl.setVisibility(View.GONE);
    }

    /**
     * 开始下载
     *
     * @param view
     */
    @OnClick(R.id.start)
    public void start(View view) {
        //int cityid = Integer.parseInt(citView.getText().toString());
        mOffline.start(cityid);
        clickLocalMapListButton(null);
        T.show(mActivity,"开始下载离线地图. cityid: " + cityid);
        updateView();
    }

    /**
     * 暂停下载
     *
     * @param view
     */
    @OnClick(R.id.stop)
    public void stop(View view) {
        //int cityid = Integer.parseInt(citView.getText().toString());
        mOffline.pause(cityid);
        T.show(mActivity,"暂停下载离线地图. cityid: " + cityid);
        updateView();
    }

    /**
     * 删除离线地图
     *
     * @param view
     */
    @OnClick(R.id.del)
    public void remove(View view) {
        //int cityid = Integer.parseInt(citView.getText().toString());
        mOffline.remove(cityid);
        T.show(mActivity,"删除离线地图. cityid: " + cityid);
        updateView();
    }

    @Override
    public void onPause() {
        //int cityid = Integer.parseInt(citView.getText().toString());
        MKOLUpdateElement temp = mOffline.getUpdateInfo(cityid);
        if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
            mOffline.pause(cityid);
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        mOffline.destroy();
        super.onDestroyView();
    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

    @Override
    protected void onDelayLoad() {

    }

    /**
     * 离线地图管理列表适配器
     */
    public class LocalMapAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localMapList.size();
        }

        @Override
        public Object getItem(int index) {
            return localMapList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
            view = View.inflate(JackApplication.mActivity,
                    R.layout.offline_localmap_list, null);
            initViewItem(view, e);
            return view;
        }

        void initViewItem(View view, final MKOLUpdateElement e) {
            Button remove = (Button) view.findViewById(R.id.remove);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            TextView ratio = (TextView) view.findViewById(R.id.ratio);
            ratio.setText(e.ratio + "%");
            title.setText(e.cityName);
            if (e.update) {
                update.setText("可更新");
            } else {
                update.setText("最新");
            }

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mOffline.remove(e.cityID);
                    updateView();
                }
            });
        }
    }
}
