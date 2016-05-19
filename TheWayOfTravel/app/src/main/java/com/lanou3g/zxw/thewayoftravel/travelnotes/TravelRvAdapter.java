package com.lanou3g.zxw.thewayoftravel.travelnotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.bean.TravelnotesBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dllo on 16/5/9.
 */
public class TravelRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态_默认为0
    private int load_more_status = 0;
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

    private Context context;
    private List<TravelnotesBean> tratestBeens;
//    private TravelnotesBean travelBean;
//    private List<TravelnotesBean> travelnotesBeanList;
    private OnTravelRvItemListener onTravelRvItemListener;

    public TravelRvAdapter(Context context) {
        this.context = context;
    }

    public void setTratestBeens(List<TravelnotesBean> tratestBeens) {
        this.tratestBeens = tratestBeens;
        notifyDataSetChanged();
    }

    public void setOnTravelRvItemListener(OnTravelRvItemListener onTravelRvItemListener) {
        this.onTravelRvItemListener = onTravelRvItemListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new TracelnotesViewHolder(LayoutInflater.
                    from(context).inflate(R.layout.item_travelnotes_recyclerview,
                    parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new TracelFootViewHolder(LayoutInflater.
                    from(context).inflate(R.layout.list_foot_loading,
                    parent, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        switch (itemType) {
            case TYPE_ITEM:
                TravelnotesBean tracelBean = tratestBeens.get(position);
                ((TracelnotesViewHolder) holder).titleTv.setText(tracelBean.getName());
                ((TracelnotesViewHolder) holder).timeTv.setText(
                        tracelBean.getStart_date() + " / " +
                                tracelBean.getDays() + "天, " +
                                tracelBean.getPhotos_count() + "图");
                Picasso.with(context).load(tracelBean.getFront_cover_photo_url())
                        .into(((TracelnotesViewHolder) holder).backgroundImg);
                Picasso.with(context).load(tracelBean.getUser().getImage()).
                        into(((TracelnotesViewHolder) holder).userHeadImg);

                if (tracelBean.isFeatured()) {
                    ((TracelnotesViewHolder) holder).bestImg.setVisibility(View.VISIBLE);
                } else {
                    ((TracelnotesViewHolder) holder).bestImg.setVisibility(View.GONE);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        onTravelRvItemListener.OnItemClick(pos);
                    }
                });
                break;
            case TYPE_FOOTER:

                TracelFootViewHolder footViewHolder = (TracelFootViewHolder) holder;
                switch (load_more_status) {
                    case PULLUP_LOAD_MORE:
                        footViewHolder.loadTv.setText("上拉加载更多美文");
                        footViewHolder.loadPbar.setVisibility(View.GONE);
                        break;
                    case LOADING_MORE:
                        footViewHolder.loadTv.setText("正在加载");
                        footViewHolder.loadPbar.setVisibility(View.VISIBLE);
                        break;
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return tratestBeens == null ? 0 : tratestBeens.size() + 1;
    }

    public class TracelnotesViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv, timeTv;
        private ImageView backgroundImg, bestImg, userHeadImg;


        public TracelnotesViewHolder(View itemView) {
            super(itemView);
            timeTv = (TextView) itemView.findViewById(R.id.item_travel_rv_time);
            titleTv = (TextView) itemView.findViewById(R.id.item_travel_rv_title);
            backgroundImg = (ImageView) itemView.findViewById(R.id.item_tracel_rv_background);
            userHeadImg = (ImageView) itemView.findViewById(R.id.item_travel_rv_head_img);
            bestImg = (ImageView) itemView.findViewById(R.id.best_img);
        }
    }

    public static class TracelFootViewHolder extends RecyclerView.ViewHolder {
        private TextView loadTv;
        private ProgressBar loadPbar;

        public TracelFootViewHolder(View itemView) {
            super(itemView);
            loadTv = (TextView) itemView.findViewById(R.id.travelnotes_rv_foot_load);
            loadPbar = (ProgressBar) itemView.findViewById(R.id.travelnotes_rv_foot_progressbar);
        }
    }

    //刷新数据
    public void addItem(List<TravelnotesBean> travelnotesBeanList) {
        travelnotesBeanList.addAll(tratestBeens);
        tratestBeens.removeAll(tratestBeens);
        tratestBeens.addAll(travelnotesBeanList);
        notifyDataSetChanged();
    }

    //加载数据
    public void addMoreItem(List<TravelnotesBean> travelnotesBeanList) {
        tratestBeens.addAll(travelnotesBeanList);
        notifyDataSetChanged();

    }

    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * //正在加载中
     * LOADING_MORE=1;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }
}
