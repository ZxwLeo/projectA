package com.lanou3g.zxw.thewayoftravel.strategy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.bean.StrategyBean;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/5/12.
 * 攻略页的listView嵌套gridview-gridview的适配器
 * 适配器与listView相似
 */
public class StrategyGvAdapter extends BaseAdapter {
    private Context context;
    private StrategyBean strategyBean;

    public StrategyGvAdapter(Context context) {
        this.context = context;
    }

    //设置set数据
    public void setStrategyBean(StrategyBean strategyBean) {
        this.strategyBean = strategyBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strategyBean == null ? 0 : strategyBean.getDestinations().size();
    }

    @Override
    public Object getItem(int position) {
        return strategyBean.getDestinations().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GvViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_strategy_gridview, parent, false);
            holder = new GvViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GvViewHolder) convertView.getTag();
        }
        if (strategyBean != null) {
            StrategyBean.DestinationsBean destinationsBean =
                    strategyBean.getDestinations().get(position);
            //view载入数据
            holder.countryCnTv.setText(destinationsBean.getName_zh_cn());
            holder.countryEnTv.setText(destinationsBean.getName_en());
            holder.placeTv.setText(destinationsBean.getPoi_count() + "  旅行地");
            Picasso.with(context).load(destinationsBean.getImage_url()).
                    into(holder.itemBgImg);
        }
        return convertView;
    }

    class GvViewHolder {
        private TextView countryCnTv, countryEnTv, placeTv;
        private ImageView itemBgImg;

        public GvViewHolder(View itemView) {
            countryCnTv = (TextView) itemView.findViewById(R.id.item_strategy_gridview_country_cn);
            countryEnTv = (TextView) itemView.findViewById(R.id.item_strategy_gridview_country_en);
            placeTv = (TextView) itemView.findViewById(R.id.item_strategy_gridview_place_tv);
            itemBgImg = (ImageView) itemView.findViewById(R.id.item_strategy_gridview_background);
        }
    }


}
