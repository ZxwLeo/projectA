package com.lanou3g.zxw.thewayoftravel.net;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dllo on 16/5/13.
 */
public class NetHelper {
    RequestQueue requestQueue;
    NetListener netListener;

    public NetHelper() {
        SingleQueue singleQueue = SingleQueue.getInstance().getInstance();
        requestQueue = singleQueue.getQueue();
    }

    public <T> void getDataFromNet(String url, final Map head, final NetListener<T> netListener,
                                   final Class<T> clazz) {
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                T t = gson.fromJson(response.toString(), clazz);
                netListener.getSuccess(t);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netListener.getFailed("拉取失败");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (head != null) {
                    return head;
                }
                return super.getHeaders();
            }
        };
        requestQueue.add(request);
    }



    public <T> void getInfo(String head, final String id, String line, String bottom, final Class<T> cls, final NetListener listener) {
        String url = head + id + line + bottom;
        final ArrayList<T> list = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray(id);
                    Log.d("NetHelper", id);
                    for (int i = 0; i < array.length(); i++) {
                        T t = new Gson().fromJson(array.getJSONObject(i).toString(), cls);
                        Log.d("NetHelper", t.toString());
                        list.add(t);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listener.getArraySuccessed(list);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.getFailed("网络延迟");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
