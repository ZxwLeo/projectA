package com.lanou3g.zxw.thewayoftravel.traveldetail;


import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.base.BaseActivity;
import com.lanou3g.zxw.thewayoftravel.bean.TracelDetailsBean;
import com.lanou3g.zxw.thewayoftravel.net.VolleySingle;
import com.lanou3g.zxw.thewayoftravel.view.PinnedHeaderExpandableListView;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/5/14.
 */
public class TravelDetailActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar travelDetailToolbar;
    private ImageView backBtn;
    private PinnedHeaderExpandableListView  expandableListView;
    private TravelDetailHLAdapter detailHLAdapter;
    private String detailId;
    private TextView titleTDTv,timeTDTv;
    private ImageView headerTDImg,backgroundTDTv;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TracelDetailsBean tracelDetailsBean;

    @Override
    protected void initView() {
        travelDetailToolbar = (Toolbar) findViewById(R.id.activity_travel_detail_toolbar);
        backBtn = (ImageView) travelDetailToolbar.findViewById(R.id.activity_travel_detail_back_btn);
        expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.activity_travel_detail_headerlistview);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_travel_detail_collapsinglayout);
        titleTDTv = (TextView) collapsingToolbarLayout.findViewById(R.id.activity_travel_detail_title_tv);
        timeTDTv = (TextView) collapsingToolbarLayout.findViewById(R.id.activity_travel_detail_content_tv);
        headerTDImg = (ImageView) collapsingToolbarLayout.findViewById(R.id.activity_travel_detail_header_img);
        backgroundTDTv = (ImageView) collapsingToolbarLayout.findViewById(R.id.activity_travel_detail_coll_bg_img);

    }

    @Override
    protected void initData() {
        detailId = getIntent().getStringExtra("detailId");
        initHeaderLv();
        initGsonData(detailId);
        setSupportActionBar(travelDetailToolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(
                        R.id.activity_travel_detail_collapsinglayout);
        collapsingToolbarLayout.setTitle("title");
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
        backBtn.setOnClickListener(this);




    }

    private void initGsonData(String detailId) {
        VolleySingle.addRequest("http://chanyouji.com/api/trips/" + detailId + ".json", TracelDetailsBean.class,
                new Response.Listener<TracelDetailsBean>() {
                    @Override
                    public void onResponse(TracelDetailsBean response) {
                        Log.d("TravelDetailActivity", response.getName());
                        detailHLAdapter.setDetailsBean(response);
                        titleTDTv.setText(response.getName());
                        timeTDTv.setText(response.getStart_date()+" "+response.getPhotos_count()+"图");
                        Picasso.with(getApplicationContext()).load(response.getFront_cover_photo_url()).
                                into(backgroundTDTv);
                        Picasso.with(getApplicationContext()).load(response.getUser().getImage()).
                                into(headerTDImg);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

    }


    private void initHeaderLv() {
        detailHLAdapter = new TravelDetailHLAdapter(this, expandableListView);
        View view = LayoutInflater.from(this).
                inflate(R.layout.travel_detail_hlv_group_item, null);

        expandableListView.setAdapter(detailHLAdapter);
        expandableListView.setHeaderView(view);
        expandableListView.setGroupIndicator(null);

        for (int i = 0; i < detailHLAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_travel_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_travel_detail_back_btn:
                onBackPressed();
                break;
        }
    }
}
