package com.lanou3g.zxw.thewayoftravel.traveldetail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.base.MyApplication;
import com.lanou3g.zxw.thewayoftravel.bean.TracelDetailsBean;
import com.lanou3g.zxw.thewayoftravel.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/5/16.
 */
public class TravelDetailNoScLvItemLvAdapter extends BaseAdapter {
    private Context context;
    private TracelDetailsBean tracelDetailsBean;
    private int dayIndex;

    public TravelDetailNoScLvItemLvAdapter(Context context,int dayIndex) {
        this.context = context;
        this.dayIndex = dayIndex;
    }

    public void setTripDaysBean(TracelDetailsBean tracelDetailsBean) {
        this.tracelDetailsBean = tracelDetailsBean;
        notifyDataSetChanged();
//        Log.d("hhhhhh", "Entry_name():" + tripDaysBean.getNodes().get(1).getEntry_name());
    }

    @Override
    public int getCount() {
        return tracelDetailsBean.getTrip_days().get(dayIndex).getNodes().size();
    }

    @Override
    public Object getItem(int position) {
        return tracelDetailsBean.getTrip_days().get(dayIndex).getNodes().get(position);
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
        } else {
            if (tracelDetailsBean.getTrip_days().get(dayIndex) != null) {
                try {
                    TracelDetailsBean.TripDaysBean.NodesBean nodesBean =
                            tracelDetailsBean.getTrip_days().get(dayIndex).getNodes().get(position);
                    if (nodesBean.getComment() != null) {
                        holder.contentTv.setText(nodesBean.getComment().toString());
                    }
                    if (nodesBean.getEntry_name() != null) {
                        holder.palceTv.setText(nodesBean.getEntry_name().toString());
                    }
                    Log.d("hhhhhhhh", "nodesBean.getEntry_name():" + nodesBean.getEntry_name());
                    TravelDetailNoScLvItemLvLvAdapter lvAdapter =
                            new TravelDetailNoScLvItemLvLvAdapter(MyApplication.getContext());
                    lvAdapter.setNotesBean(
                            tracelDetailsBean.getTrip_days().get(dayIndex).getNodes().get(position));
                    holder.noScrollListView.setAdapter(lvAdapter);
                } catch (Exception e) {

                }
            }

        }
        return convertView;
    }

    private class MyViewHolder {
        NoScrollListView noScrollListView;
        TextView palceTv, contentTv;

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
