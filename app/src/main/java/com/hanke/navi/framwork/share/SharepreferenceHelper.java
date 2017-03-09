package com.hanke.navi.framwork.share;

import android.content.Context;

import com.amap.api.maps.model.MarkerOptions;

public class SharepreferenceHelper extends PreferenceWrapper{

    private static SharepreferenceHelper sharepreferenceHelper;

    private SharepreferenceHelper(Context context) {
        super(context);
    }

    public static SharepreferenceHelper getInstence(Context context){
        if(sharepreferenceHelper == null)
            sharepreferenceHelper = new SharepreferenceHelper(context);
        return sharepreferenceHelper;
    }

    //设置航线item
    public void setHXitem(String hXitem){
        putString("hXitem",hXitem);
    }
    public String getHXitem(){
        return getString("hXitem");
    }
    //设置航路item
    public void setHLitem(String hLitem){
        putString("hLitem",hLitem);
    }
    public String getHLitem(){
        return getString("hLitem");
    }

    //航路点添加
    public void setHLumingcheng(String HLumingcheng){
        putString("HLumingcheng",HLumingcheng);
    }
    public String getHLumingcheng(){
        return getString("HLumingcheng");
    }
    public void setHLuweidu(String HLuweidu){
        putString("HLuweidu",HLuweidu);
    }
    public String getHLuweidu(){
        return getString("HLuweidu");
    }
    public void setHLujingdu(String HLujingdu){
        putString("HLujingdu",HLujingdu);
    }
    public String getHLujingdu(){
        return getString("HLujingdu");
    }
    public void setHLugaodu(String HLugaodu){
        putString("HLugaodu",HLugaodu);
    }
    public String getHLugaodu(){
        return getString("HLugaodu");
    }

    //航路点添加2
    public void setChangeHLumingcheng(String change_mingcheng){
        putString("change_mingcheng",change_mingcheng);
    }
    public String getChangeHLumingcheng(){
        return getString("change_mingcheng");
    }

    //接收的数据
    public void setWeidu(String weidus){
        putString("weidus",weidus);
    }
    public String getWeidu(){
        return getString("weidus");
    }
    public void setJingdu(String jingdus){
        putString("jingdus",jingdus);
    }
    public String getJingdu(){
        return getString("jingdus");
    }
    public void setGaodu(String gaodus){
        putString("gaodus",gaodus);
    }
    public String getGaodu(){
        return getString("gaodus");
    }

    public void setMc(String mc){
        putString("mc",mc);
    }
    public String getMc(){
        return getString("mc");
    }

}
