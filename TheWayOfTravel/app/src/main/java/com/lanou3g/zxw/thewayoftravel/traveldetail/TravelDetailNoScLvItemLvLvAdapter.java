package com.lanou3g.zxw.thewayoftravel.traveldetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.bean.TracelDetailsBean;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/5/16.
 */
public class TravelDetailNoScLvItemLvLvAdapter extends BaseAdapter {
    private Context context;
    private TracelDetailsBean.TripDaysBean.NodesBean notesBean;

    public TravelDetailNoScLvItemLvLvAdapter(Context context) {
        this.context = context;
    }

    public void setNotesBean(
            TracelDetailsBean.TripDaysBean.NodesBean notesBean) {
        this.notesBean = notesBean;
    }

    @Override
    public int getCount() {
        return notesBean.getNotes().size();
    }

    @Override
    public Object getItem(int position) {
        return notesBean.getNotes().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.travel_detail_hlv_lv_lv_item,
                            parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
        TracelDetailsBean.TripDaysBean.NodesBean.NotesBean notesBeans =
                notesBean.getNotes().get(position);
        if (notesBeans.getRow_order()!=0) {
            holder.placeTv.setText(notesBean.getEntry_name().toString());
            holder.placeDetailTv.setText(notesBeans.getDescription());
            Picasso.with(context).load(notesBeans.getPhotoBean().getUrl()).
                    resize(notesBeans.getPhotoBean().getImage_width(),
                            notesBeans.getPhotoBean().getImage_height())
                    .into(holder.placePhotoImg);
        }else {
            holder.itemView.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class MyViewHolder {
        ImageView placePhotoImg;
        TextView placeDetailTv,placeTv;
        View itemView;
        public MyViewHolder(View itemView) {
            placePhotoImg = (ImageView)
                    itemView.findViewById(
                            R.id.travel_detail_hlv_lv_img);
            placeDetailTv = (TextView)
                    itemView.findViewById(
                            R.id.travel_detail_hlv_lv_tv);
            placeTv = (TextView)
                    itemView.findViewById(
                            R.id.travel_detail_hlv_lv_place);
            this.itemView = itemView;


        }
    }
}
