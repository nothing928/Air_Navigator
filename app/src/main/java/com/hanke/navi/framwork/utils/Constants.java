package com.hanke.navi.framwork.utils;

import com.amap.api.maps.model.LatLng;
import com.hanke.navi.framwork.share.SharepreferenceHelper;
import com.hanke.navi.skyair.MyApplication;

public class Constants {
//    static String swd = SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getWeidu();
//    static String sjd = SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getJingdu();
//    static double wd = Double.parseDouble(swd);
//    static double jd = Double.parseDouble(sjd);
    public static final LatLng WEIZHI = new LatLng(Double.parseDouble(SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getWeidu())/100,
            Double.parseDouble(SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getJingdu())/100);// 阎良区经纬度
    public static final LatLng YANLIANG = new LatLng(34.657679, 109.239685);// 阎良区经纬度
    public static final LatLng XIAN = new LatLng(34.268149, 108.917019);// 西安经纬度
}
