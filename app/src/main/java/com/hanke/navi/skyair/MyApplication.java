package com.hanke.navi.skyair;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Circle;
import com.hanke.navi.skyair.socket.ClientTask;
import com.hanke.navi.skyair.socket.ToClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/11/15.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    public static int width,height;
    public static AMap aMap;
    public boolean isSelect = true;
    private List<Activity> activities = new ArrayList<>();

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        ClientTask clientTask = new ClientTask(this);
        clientTask.execute();
//        ToClient toClient = new ToClient();
//        toClient.initData();
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public static Context getAppContext(){
        return myApplication.getApplicationContext();
    }

    public static AMap getAMap(){
        return aMap;
    }

    public static int getWidth(){
        //方法一：获取屏幕宽高
        width = getAppContext().getResources().getDisplayMetrics().widthPixels;
        Log.e("APP屏幕宽","width_app = "+width);
        //方法二：获取屏幕宽高
//        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        int width123 = wm.getDefaultDisplay().getWidth();
//        int height123 = wm.getDefaultDisplay().getHeight();
//        Log.e("msg屏幕宽高","width123 = "+width123+", height123 = "+height123);
        return width;
    }

    public static int getHeight(){
        //方法一：获取屏幕宽高
        height = getAppContext().getResources().getDisplayMetrics().heightPixels;
        Log.e("APP屏幕高","height_app = "+height);
        return height;
    }

    //设置间距
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 添加Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null)
            activities.add(activity);
    }

}
