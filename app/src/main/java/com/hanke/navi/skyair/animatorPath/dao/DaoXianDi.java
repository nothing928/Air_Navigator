package com.hanke.navi.skyair.animatorPath.dao;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TextOptions;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.animatorPath.AnimatorPath;
import com.hanke.navi.skyair.animatorPath.PathEvaluator;
import com.hanke.navi.skyair.animatorPath.PathPoint;
import com.hanke.navi.skyair.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 2017/1/4.
 */
public class DaoXianDi {
    public Context context;
    public static Polyline daoxian;
    public static AnimatorPath path;//声明动画集合

    public DaoXianDi(Context context) {
        this.context = context;
    }

    public static Point point0,point1,point2,point3,point4,point5;
    public static int xx0,yy0,xx1,yy1,xx2,yy2,xx3,yy3,xx4,yy4,xx5,yy5;
    public void DaoXian(){
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<Polyline> lp = new ArrayList<Polyline>();
        latLngs.add(new LatLng(BaseActivity.latitude,BaseActivity.longitude));
        BaseActivity.aMap.addText(new TextOptions().position(new LatLng(34.498705, 109.505039)).text("线路-渭南")
                .backgroundColor(Color.parseColor("#00ffffff")).fontSize(30).align(100,20).rotate(0).fontColor(Color.RED)
        .typeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)));
        point0=BaseActivity.aMap.getProjection().toScreenLocation(new LatLng(BaseActivity.latitude,BaseActivity.longitude));//从地图位置转换来的屏幕位置
        xx0=point0.x;
        yy0=point0.y;
        Log.e("msg","xx0="+xx0+",   yy0="+yy0);
        latLngs.add(new LatLng(34.498705, 109.505039));//渭南
        point1=BaseActivity.aMap.getProjection().toScreenLocation(new LatLng(34.498705, 109.505039));//从地图位置转换来的屏幕位置
        xx1=point1.x;
        yy1=point1.y;
        Log.e("msg","xx1="+xx1+",   yy1="+yy1);
        latLngs.add(new LatLng(34.796438, 109.943312));//大荔县
        point2=BaseActivity.aMap.getProjection().toScreenLocation(new LatLng(34.796438, 109.943312));//从地图位置转换来的屏幕位置
        xx2=point2.x;
        yy2=point2.y;
        Log.e("msg","xx2="+xx2+",   yy2="+yy2);
        latLngs.add(new LatLng(34.954963, 109.586411));//蒲城县
        point3=BaseActivity.aMap.getProjection().toScreenLocation(new LatLng(34.954963, 109.586411));//从地图位置转换来的屏幕位置
        xx3=point3.x;
        yy3=point3.y;
        Log.e("msg","xx3="+xx3+",   yy3="+yy3);
        latLngs.add(new LatLng(35.177385, 109.590853));//白水县
        point4=BaseActivity.aMap.getProjection().toScreenLocation(new LatLng(35.177385, 109.590853));//从地图位置转换来的屏幕位置
        xx4=point4.x;
        yy4=point4.y;
        Log.e("msg","xx4="+xx4+",   yy4="+yy4);
        latLngs.add(new LatLng(34.915973, 108.978371));//铜川
        point5=BaseActivity.aMap.getProjection().toScreenLocation(new LatLng(34.915973, 108.978371));//从地图位置转换来的屏幕位置
        xx5=point5.x;
        yy5=point5.y;
        Log.e("msg","xx5="+xx5+",   yy5="+yy5);
        daoxian = BaseActivity.aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth() / 200).color(Color.WHITE).geodesic(true));
        lp.add(BaseActivity.polyline);
    }

    /*设置动画路径*/
    public static void setPath() {
        path = new AnimatorPath();
        path.moveTo(xx0, yy0);
//        path.lineTo(orgX, orgY);
//        Log.e("msg", "orgX11=" + orgX + ", orgY11=" + orgY);
        path.lineTo(xx1, yy1);
        path.lineTo(xx2, yy2);
        path.lineTo(xx3, yy3);
        path.lineTo(xx4, yy4);
        path.lineTo(xx5, yy5);
//        path.secondBesselCurveTo(600, 200, 800, 400); //订单
//        path.thirdBesselCurveTo(100,600,900,1000,200,1200);


//        path = new AnimatorPath();
//        path.moveTo(0,0);
//        path.lineTo(400,400);
//        path.secondBesselCurveTo(600, 200, 800, 400); //订单
//        path.thirdBesselCurveTo(100,600,900,1000,200,1200);
    }

    /**
     * 设置动画
     *
     * @param view         使用动画的View
     * @param propertyName 属性名字
     * @param path         动画路径集合
     */
    public static void startAnimatorPath(View view, String propertyName, AnimatorPath path) {
        ObjectAnimator anim = ObjectAnimator.ofObject(MyApplication.getAppContext(), propertyName, new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(10000);
        anim.start();
    }

    /**
     * 设置View的属性通过ObjectAnimator.ofObject()的反射机制来调用
     *
     * @param newLoc
     */
    public void setFab(PathPoint newLoc) {
        MainActivity.mm.setTranslationX(newLoc.mX);
        MainActivity.mm.setTranslationY(newLoc.mY);
    }
}
