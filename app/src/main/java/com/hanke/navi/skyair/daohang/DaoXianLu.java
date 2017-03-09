package com.hanke.navi.skyair.daohang;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.framwork.utils.Constants;
import com.hanke.navi.skyair.db.HLModel;

import java.util.ArrayList;
import java.util.List;

public class DaoXianLu {
    private Context context;
    private Polyline mVirtureRoad;
    private Marker mMoveMarker;

    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 2;
    private static final double DISTANCE = 0.0001;

    public DaoXianLu(Context context) {
        this.context = context;
    }

    public void initRoadData() {
//        PolylineOptions polylineOptions = new PolylineOptions();
//        polylineOptions.add(Constants.YANLIANG);
//        polylineOptions.add(new LatLng(34.498705, 109.505039));//渭南
//        polylineOptions.add(new LatLng(34.796438, 109.943312));//大荔
//        polylineOptions.add(new LatLng(34.954963, 109.586411));//蒲城
//        polylineOptions.add(new LatLng(35.177385, 109.590853));//白水
//        polylineOptions.add(new LatLng(34.915973, 108.978371));//铜川
//
//        polylineOptions.width(10);
//        polylineOptions.color(Color.CYAN);
//        mVirtureRoad = BaseActivity.aMap.addPolyline(polylineOptions);

        List<LatLng> list = new ArrayList<LatLng>();
        list.add(new LatLng(BaseActivity.latitude,BaseActivity.longitude));
        //pop中有一个ListView1，每点击一个item都弹出一个布局相同的pop（里面又有ListView2）,在此pop的ListView2下可以新添加数据，数据库进行保存，这个数据库的表应该咋个设置
        list.add(new LatLng(34.498705, 109.505039));//渭南
        list.add(new LatLng(34.796438, 109.943312));//大荔
        list.add(new LatLng(34.954963, 109.586411));//蒲城
        list.add(new LatLng(35.177385, 109.590853));//白水
        list.add(new LatLng(34.915973, 108.978371));//铜川
        PolylineOptions polylineOptions = new PolylineOptions();
        mVirtureRoad = BaseActivity.aMap.addPolyline(polylineOptions.addAll(list).width(10).color(Color.CYAN));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);//设置标志是否贴地显示
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.zfj));
        Log.e("size","polylineOptions.getPoints().size() = "+polylineOptions.getPoints().size());
        markerOptions.position(polylineOptions.getPoints().get(0));
        mMoveMarker = BaseActivity.aMap.addMarker(markerOptions);
        mMoveMarker.setRotateAngle((float) getAngle(0));//设置标记的旋转角度,从正北开始,逆时针计算

    }

    /**
     * 根据点获取图标转的角度
     */
    private double getAngle(int startIndex) {
        if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
            Log.e("size","mVirtureRoad.getPoints().size() = "+mVirtureRoad.getPoints().size());
//            return 0;
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
        LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
        return getAngle(startPoint, endPoint);
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 5;
            } else {
                return 185;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 175;
        }
        else if ((toPoint.latitude - fromPoint.latitude) * slope == 0){
            if(toPoint.longitude - fromPoint.longitude<0)
                deltAngle=-90;
            if (toPoint.longitude - fromPoint.longitude>0)
                deltAngle=90;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 85;
        return angle;
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {

        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 算取斜率
     */
    private double getSlope(int startIndex) {
        if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
        LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
        return getSlope(startPoint, endPoint);
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;
    }

    /**
     * 计算每次移动的距离
     */
    private double getMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE||slope==0) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 判断是否为反序
     * */
    private boolean isReverse(LatLng startPoint,LatLng endPoint,double slope){
        if(slope==0){
            return	startPoint.longitude>endPoint.longitude;
        }
        return (startPoint.latitude > endPoint.latitude);
    }

    /**
     * 获取循环初始值大小
     * */
    private double getStart(LatLng startPoint,double slope){
        if(slope==0){
            return	startPoint.longitude;
        }
        return  startPoint.latitude;
    }

    /**
     * 获取循环结束大小
     * */
    private double getEnd(LatLng endPoint,double slope){
        if(slope==0){
            return	endPoint.longitude;
        }
        return  endPoint.latitude;
    }



    /**
     * 循环进行移动逻辑
     */
    boolean b =true;
    public void moveLooper() {
        new Thread() {
            public void run() {
                while (b) {
                    for (int i = 0; i < mVirtureRoad.getPoints().size() - 1; i++) {

                        LatLng startPoint = mVirtureRoad.getPoints().get(i);
                        LatLng endPoint = mVirtureRoad.getPoints().get(i + 1);
                        mMoveMarker.setPosition(startPoint);
                        mMoveMarker.setRotateAngle((float) getAngle(startPoint, endPoint));

                        double slope = getSlope(startPoint, endPoint);
                        boolean isReverse =isReverse(startPoint, endPoint, slope);
                        double moveDistance = isReverse ? getMoveDistance(slope) : -1 * getMoveDistance(slope);
                        double intercept = getInterception(slope, startPoint);
                        for(double j=getStart(startPoint, slope); (j > getEnd(endPoint, slope))==isReverse;j = j - moveDistance){
                            LatLng latLng = null;
                            if(slope==0){
                                latLng = new LatLng(startPoint.latitude, j);
                            }
                            else if (slope == Double.MAX_VALUE) {
                                latLng = new LatLng(j, startPoint.longitude);
                            }
                            else {
                                latLng = new LatLng(j, (j - intercept) / slope);
                            }
                            mMoveMarker.setPosition(latLng);
                            try {
                                Thread.sleep(TIME_INTERVAL);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    b=false;
                }
            }
        }.start();
    }

}
