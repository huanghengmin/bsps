package com.hzih.bsps.web.Brokenlinefigure;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 13-2-1
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
public class MyTest {
    public static void main(String args[]){

        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
//        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);


    }

}
