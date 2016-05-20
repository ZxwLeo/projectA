package com.lanou3g.zxw.thewayoftravel.strategy;

import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanou3g.zxw.thewayoftravel.R;
import com.lanou3g.zxw.thewayoftravel.base.BaseFragment;
import com.lanou3g.zxw.thewayoftravel.bean.StrategyBean;
import com.lanou3g.zxw.thewayoftravel.net.NetValue;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by dllo on 16/5/9.
 * tablayout中的攻略頁
 */
public class StrategyFragment extends BaseFragment {
    private ListView strategyLv;
    private StrategyLvAdapter strategyLvAdapter;
    private List<StrategyBean> strategyBeanList;

    @Override
    protected int setLayout() {
        return R.layout.fragment_strategy;
    }

    @Override
    protected void initView(View view) {
        strategyLv = bindView(R.id.strategy_listview);
    }

    @Override
    protected void initData() {
        strategyLvAdapter = new StrategyLvAdapter(context);
        initGsonData();
        strategyLv.setAdapter(strategyLvAdapter);
    }

    private void initGsonData() {
        //获取网络数据
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                NetValue.TRAVEL_STRATEGY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //利用Gson解析
                        Gson gson = new Gson();
                        //解析为数组
                        strategyBeanList = gson.fromJson(response,
                                new TypeToken<List<StrategyBean>>(){}.getType());
                        //载入listView适配器
                        strategyLvAdapter.setStrategyBeens(strategyBeanList);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);

    }


}
