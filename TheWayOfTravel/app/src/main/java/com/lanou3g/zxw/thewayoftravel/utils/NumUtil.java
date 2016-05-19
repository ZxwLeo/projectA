package com.lanou3g.zxw.thewayoftravel.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dllo on 16/5/18.
 */
public class NumUtil {

    private static String weekOfDay;

    //将-号转换为 年月日
    public static String getLineToNianYueRi(String date) {
        if (date != null) {
            final String year = date.substring(0, 5);
            final String month = date.substring(5, 8);
            final String day = date.substring(8, 10);
            String newDate = year + "年" + month + "月" + day + "日";
            return newDate;
        } else {
            return "";
        }
    }

    //将阿拉伯数字转换为中文数字
    public static String getNumToChineseNum(int d) {
        String chineseNum = null;
        String[] str = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String ss[] = new String[]{"", "十", "百", "千", "万", "十", "百", "千", "亿"};
        String s = String.valueOf(d);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            String index = String.valueOf(s.charAt(i));
            sb = sb.append(str[Integer.parseInt(index)]);
        }
        String sss = String.valueOf(sb);
        int i = 0;
        for (int j = sss.length(); j > 0; j--) {
            chineseNum = String.valueOf(sb.insert(j, ss[i++]));
        }
        return chineseNum;
    }

    //根据日期找星期
    public static String getDateToWeekDay(String date) {
        if (date!=null){
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;}else {
            return "";
        }
    }


}
