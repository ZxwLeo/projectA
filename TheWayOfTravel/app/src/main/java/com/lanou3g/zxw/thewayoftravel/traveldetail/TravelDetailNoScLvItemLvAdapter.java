package com.lanou3g.zxw.thewayoftravel.traveldetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.bean.TracelDetailsBean;
import com.lanou3g.zxw.thewayoftravel.view.NoScrollListView;

/**
 * Created by dllo on 16/5/16.
 */
public class TravelDetailNoScLvItemLvAdapter extends BaseAdapter {
    private Context context;
    private TracelDetailsBean.TripDaysBean tripDaysBean;

    public TravelDetailNoScLvItemLvAdapter(Context context) {
        this.context = context;
    }

    public void setTripDaysBean(TracelDetailsBean.TripDaysBean tripDaysBean) {
        this.tripDaysBean = tripDaysBean;
    }

    @Override
    public int getCount() {
        return tripDaysBean.getNodes().size();
    }

    @Override
    public Object getItem(int position) {
        return tripDaysBean.getNodes().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.travel_detail_hlv_lv_item, parent, false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder.contentTv.setText(tripDaysBean.getNodes().get(position).getComment());
            holder.palceTv.setText(tripDaysBean.getNodes().get(position).getEntry_name().toString());
            TravelDetailNoScLvItemLvLvAdapter lvAdapter =
                    new TravelDetailNoScLvItemLvLvAdapter(context);
            lvAdapter.setNotesBean(tripDaysBean.getNodes().get(position));
            holder.noScrollListView.setAdapter(lvAdapter);
        }
        return convertView;
    }

    private class MyViewHolder {
        NoScrollListView noScrollListView;
        TextView palceTv,contentTv;
        public MyViewHolder(View itemView) {
            noScrollListView = (NoScrollListView)
                    itemView.findViewById(R.id.tracel_detail_hlv_lv);
            palceTv = (TextView) itemView.
                    findViewById(R.id.travel_detail_hlv_lv_titleorplace);
            contentTv = (TextView) itemView.
                    findViewById(R.id.travel_detail_hlv_lv_content);
        }
    }
}
