package com.lanou3g.zxw.thewayoftravel.traveldetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.bean.TracelDetailsBean;
import com.lanou3g.zxw.thewayoftravel.utils.NumUtil;
import com.lanou3g.zxw.thewayoftravel.view.NoScrollListView;
import com.lanou3g.zxw.thewayoftravel.view.PinnedHeaderExpandableListView;

import org.w3c.dom.Text;


/**
 * Created by dllo on 16/5/17.
 */
public class TravelDetailHLAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {
    private TracelDetailsBean detailsBean;
    private Context context;
    private PinnedHeaderExpandableListView exListView;

    public TravelDetailHLAdapter( Context context,
                                 PinnedHeaderExpandableListView exListView) {
        this.context = context;
        this.exListView = exListView;
        notifyDataSetChanged();
    }

    public void setDetailsBean(TracelDetailsBean detailsBean) {
        this.detailsBean = detailsBean;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return detailsBean == null ? 0 : detailsBean.getTrip_days().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return detailsBean == null ? 0 : detailsBean.getTrip_days().get(groupPosition).getNodes().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return detailsBean == null ? null : detailsBean.getTrip_days().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return detailsBean == null ? null : detailsBean.getTrip_days().get(groupPosition).getNodes().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FatherViewHolder fHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.travel_detail_hlv_group_item, parent, false);
            fHolder = new FatherViewHolder(convertView);
            convertView.setTag(fHolder);
        }else{
            fHolder = (FatherViewHolder) convertView.getTag();
        }
        TracelDetailsBean.TripDaysBean tripDaysBean =
                detailsBean.getTrip_days().get(groupPosition);
        fHolder.dayTv.setText("DAY" + tripDaysBean.getDay());
        //TODO dateTv 有问题
        if (tripDaysBean.getTrip_date()!=null) {
            fHolder.dateTv.setVisibility(View.VISIBLE);
            fHolder.dateTv.setText(
                    NumUtil.getLineToNianYueRi(tripDaysBean.getTrip_date())
                            + " 周" + NumUtil.getDateToWeekDay(tripDaysBean.getTrip_date()));
        }else {
            fHolder.dateTv.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {
        SonViewHolder sHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.travel_detail_hlv_son_item, parent, false);
            sHolder = new SonViewHolder(convertView);
            convertView.setTag(sHolder);
        }else {
            sHolder = (SonViewHolder) convertView.getTag();
        }
        TracelDetailsBean.TripDaysBean.NodesBean nodesBean =
                detailsBean.getTrip_days().get(groupPosition).
                        getNodes().get(childPosition);
            TravelDetailNoScLvItemLvAdapter lvAdapter =
                    new TravelDetailNoScLvItemLvAdapter(context);
            lvAdapter.setTripDaysBean(
                    detailsBean.getTrip_days().get(groupPosition));
            sHolder.hlNsLv.setAdapter(lvAdapter);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !exListView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        int day = this.detailsBean.getTrip_days().get(groupPosition).getDay();
        ((TextView) header.findViewById(R.id.activity_travel_detail_hlv_group_item_day)).
                setText("DAY" + day);
        String time = this.detailsBean.getTrip_days().get(groupPosition).getTrip_date();
        ((TextView) header.findViewById(R.id.activity_travel_detail_hlv_group_item_date)).
                setText(NumUtil.getLineToNianYueRi(time) + " 周" + NumUtil.getDateToWeekDay(time));
    }

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {

    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        return 0;
    }

    private class FatherViewHolder {
        TextView dayTv, dateTv;

        public FatherViewHolder(View itemFView) {
            dayTv = (TextView) itemFView.findViewById(R.id.activity_travel_detail_hlv_group_item_day);
            dateTv = (TextView) itemFView.findViewById(R.id.activity_travel_detail_hlv_group_item_date);

        }
    }

    private class SonViewHolder {
        NoScrollListView hlNsLv;
        public SonViewHolder(View itemSView) {
            hlNsLv = (NoScrollListView) itemSView.findViewById(R.id.travel_detail_hlv_son_noscroll_lv);
        }
    }
}
