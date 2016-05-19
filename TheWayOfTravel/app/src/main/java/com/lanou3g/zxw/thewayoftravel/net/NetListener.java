package com.lanou3g.zxw.thewayoftravel.net;



import java.util.ArrayList;

/**
 * Created by dllo on 16/5/13.
 */
public interface NetListener<T> {
    void getSuccess(T t);
    void getFailed(String s);
    void getArraySuccessed(ArrayList<T> t);
}
