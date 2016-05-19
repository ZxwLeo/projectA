package com.lanou3g.zxw.thewayoftravel.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lanou3g.zxw.thewayoftravel.base.MyApplication;

/**
 * Created by dllo on 16/5/13.
 */
public class SingleQueue {
    private static SingleQueue singleQueue;
    private RequestQueue queue;

    private SingleQueue(){
        queue = Volley.newRequestQueue(MyApplication.getContext()); }

    public RequestQueue getQueue() {
        return queue;
    }
    public static SingleQueue getInstance(){
        if (singleQueue == null){
            synchronized (SingleQueue.class){
                if (singleQueue ==null){
                    singleQueue = new SingleQueue();
                }
            }
        }
        return singleQueue;
    }
}
