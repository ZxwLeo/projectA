package com.lanou3g.zxw.thewayoftravel.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lanou3g.zxw.thewayoftravel.base.MyApplication;

/**
 * Created by dllo on 16/5/16.
 */
public class VolleySingle {
    private static Context context;
    private RequestQueue queue;//请求队列

    private VolleySingle(Context context) {
        this.context = MyApplication.getContext();
        queue = getQueue();
    }

    private static VolleySingle ourInstance = new VolleySingle(context);

    //获取单例的对象
    public static VolleySingle getInstance() {
        return ourInstance;
    }

    //提供一个方法 新建请求队列
    public RequestQueue getQueue() {
        if (queue == null){
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public static final String TAG = "VolleySingleton";

    public <T> void _addRequest(Request<T> request){
        request.setTag(TAG);//为请求队列加标签,便于管理
        queue.add(request);//将请求添加到队列中
    }

    public <T> void _addRequest(Request<T> request,Object tag){
        request.setTag(tag);//为请求队列加标签,便于管理
        queue.add(request);//将请求添加到队列中
    }

    public void _addRequest(String url,
                            Response.Listener<String> listener,
                            //这里为成功时的回调加上String类型的泛型
                            Response.ErrorListener errorListener){
        //创建StringRequset 三个参数 分别为
        // url 接口网址
        // 成功时的回调
        // 失败时的回调
        StringRequest stringRequest = new StringRequest(url,listener,errorListener);
        //将请求加入到队列
        _addRequest(stringRequest);
    }

    public <T> void _addRequest(String url,Class<T> mClass,
                                Response.Listener<T> listener,
                                Response.ErrorListener errorListener){
        GsonRequset gsonRequset = new GsonRequset(
                Request.Method.GET,
                url,
                errorListener,
                listener,mClass);
        _addRequest(gsonRequset);
    }

    //这个方法 将请求队列移除
    public void removeRequset(Object tag){
        queue.cancelAll(tag);//根据不同的tag移除队列
    }

    public static void addRequest(String url,
                                  Response.Listener<String> listener,
                                  Response.ErrorListener errorListener){
        //获取单例的对象,调用添加请求的方法(这个是StringRequest的请求)
        getInstance()._addRequest(url, listener, errorListener);
    }

    public static  <T> void addRequest(String url,
                                       Class<T> mCLass,
                                       Response.Listener<T> listener,
                                       Response.ErrorListener errorListener){
        getInstance()._addRequest(url,mCLass,listener,errorListener);
    }
}
