package com.lanou3g.zxw.thewayoftravel.strategy;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.bean.StrategyBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dllo on 16/5/12.
 * 攻略页的listView嵌套gridview-listView的Adapter
 */
public class StrategyLvAdapter extends BaseAdapter {
    private List<StrategyBean> strategyBeens;
    private Context context;
    private StrategyGvAdapter strategyGvAdapter;

    public StrategyLvAdapter(Context context) {
        this.context = context;
    }

    //设置set数据
    public void setStrategyBeens(List<StrategyBean> strategyBeens) {
        this.strategyBeens = strategyBeens;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strategyBeens == null ? 0 : strategyBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return strategyBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LvViewHolder holder = null;
        if (convertView == null){
        convertView = LayoutInflater.from(context).
                inflate(R.layout.item_strategy_listview,parent,false);
            holder = new LvViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (LvViewHolder) convertView.getTag();
        }
        //listview的每个item的标题
        //没有网络数据 使用hashmap写入固定数据
        Map map = new HashMap();
        map.put("1","国外 · 亚洲");
        map.put("2","国外 · 欧洲");
        map.put("3","美洲、大洋洲、非洲和南极洲");
        map.put("99","国内 · 港澳台");
        map.put("999","国内 · 大陆");
        if (strategyBeens != null){
            StrategyBean strategyBeen = strategyBeens.get(position);
            //向view设置数据
            holder.areaTv.setText(map.get(strategyBeen.getCategory()).toString());
            //载入item中gridview的Adapter
            strategyGvAdapter = new StrategyGvAdapter(context);
            //传入gridview的数据
            strategyGvAdapter.setStrategyBean(strategyBeen);
            holder.itemGv.setAdapter(strategyGvAdapter);
        }

        return convertView;
    }

    class LvViewHolder {
        private TextView areaTv;
        private GridView itemGv;

        public LvViewHolder(View itemView) {
            areaTv = (TextView) itemView.findViewById(R.id.item_strategy_listview_area_tv);
            itemGv = (GridView) itemView.findViewById(R.id.item_strategy_listview_gridview);
        }
    }
}
