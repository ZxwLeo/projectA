package com.lanou3g.zxw.thewayoftravel.travelnotes;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.base.BaseFragment;
import com.lanou3g.zxw.thewayoftravel.bean.TravelnotesBean;
import com.lanou3g.zxw.thewayoftravel.net.NetValue;
import com.lanou3g.zxw.thewayoftravel.traveldetail.TravelDetailActivity;
import com.lanou3g.zxw.thewayoftravel.traveldetail.TravelDetailNoScLvItemLvAdapter;
import com.lanou3g.zxw.thewayoftravel.travelnotes.pagerfragment.TravelRvHeadFristFragment;
import com.lanou3g.zxw.thewayoftravel.travelnotes.pagerfragment.TravelRvHeadPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dllo on 16/5/9.
 * tablayout中的遊記頁
 */
public class TravelFragment extends BaseFragment implements OnTravelRvItemListener {
    private RecyclerView travelnotesRv;
    private TravelRvAdapter rvAdapter;
    private RecyclerViewHeader rvHeader;
    private ViewPager rvHeaderViewPager;
    private TabLayout rvHeaderTabLayout;
    private TravelRvHeadPagerAdapter rvHeadPagerAdapter;
    private List<TravelnotesBean> travelBeans;
    private SwipeRefreshLayout travelRefresh;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    private int dataId = 2;

    @Override
    protected int setLayout() {
        return R.layout.fragment_travelnotes;
    }

    @Override
    protected void initView(View view) {
        travelnotesRv = bindView(R.id.travelnotes_rv);
        rvHeader = bindView(R.id.travelnotes_rv_header);
        rvHeaderViewPager = bindView(R.id.travelnotes_rv_header_viewpager);
        rvHeaderTabLayout = bindView(R.id.travelnotes_rv_header_tablayout);
        travelRefresh = bindView(R.id.travelnotes_rv_zxw_refresh);
    }

    @Override
    protected void initData() {

        linearLayoutManager = new LinearLayoutManager(context);
        //设置recyclerview的管理者
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        travelnotesRv.setLayoutManager(linearLayoutManager);
        rvAdapter = new TravelRvAdapter(context);
        //获取网络数据
        initGsonData(NetValue.TRAVEL_URL_FIRST);
        //加入recyclerview头布局
        rvHeader.attachTo(travelnotesRv);
        //头布局加入轮播图
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TravelRvHeadFristFragment());
        fragments.add(new TravelRvHeadSecondFragment());
        rvHeadPagerAdapter =
                new TravelRvHeadPagerAdapter(
                        getChildFragmentManager());
        rvHeadPagerAdapter.setFragments(fragments);
        rvHeaderViewPager.setAdapter(rvHeadPagerAdapter);
        rvHeaderTabLayout.setupWithViewPager(rvHeaderViewPager);
        initRefresh();
        initLoadData();

        rvAdapter.setOnTravelRvItemListener(this);

    }

    //游记页的下拉刷新
    private void initRefresh() {
        travelRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initGsonData(NetValue.TRAVEL_URL_FIRST);
                if (travelBeans != null) {
                    rvAdapter.addItem(travelBeans);
                    travelRefresh.setRefreshing(false);
                } else {
                    travelRefresh.setRefreshing(false);
                }

            }
        });
    }

    //游记页的上拉加载
    private void initLoadData() {
        travelnotesRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!travelRefresh.isRefreshing()) {
                    //滑动到最后的Item时且继续上拉 加载数据
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING &&
                            lastVisibleItem + 1 == rvAdapter.getItemCount()) {
                        //转换为 正在加载
                        rvAdapter.changeMoreStatus(TravelRvAdapter.LOADING_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "上拉", Toast.LENGTH_SHORT).show();
                                //加载数据
                                addGsonItemData(
                                        NetValue.TRAVEL_URL_LOAD + dataId);
                                //加载完成后 转换为读取前的模式
                                rvAdapter.changeMoreStatus(TravelRvAdapter.PULLUP_LOAD_MORE);
                                dataId++;
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取item的数量
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initGsonData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        travelBeans = gson.fromJson(response,
                                new TypeToken<List<TravelnotesBean>>() {
                                }.getType());
                        //设置recyclerview适配器
                        if (travelBeans != null) {
                            rvAdapter.setTratestBeens(travelBeans);
                            travelnotesRv.setAdapter(rvAdapter);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TravelFragment", "error:" + error);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void addGsonItemData(String url) {
        travelBeans = null;
        RequestQueue requestQueueAdd = Volley.newRequestQueue(context);
        StringRequest stringRequestAdd = new StringRequest(url
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        List<TravelnotesBean> beanList = gson.fromJson(response,
                                new TypeToken<List<TravelnotesBean>>() {
                                }.getType());
                        Log.d("Travel旅行", beanList.get(1).getName());
                        //设置recyclerview适配器
                        rvAdapter.addMoreItem(beanList);
                        travelnotesRv.setAdapter(rvAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TravelFragment", "error:" + error);
            }
        });
        requestQueueAdd.add(stringRequestAdd);
    }


    @Override
    public void OnItemClick(int pos) {
        Intent seeDetailIntent = new Intent(context, TravelDetailActivity.class);
        Toast.makeText(context, "点中", Toast.LENGTH_SHORT).show();
        if (travelBeans!=null) {
            seeDetailIntent.putExtra("detailId", travelBeans.get(pos).getId() + "");
            startActivity(seeDetailIntent);
        }
    }
}
