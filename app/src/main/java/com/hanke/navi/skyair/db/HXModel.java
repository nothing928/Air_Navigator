package com.hanke.navi.skyair.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.hanke.navi.skyair.pop.bean.HangXianBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HXModel extends BaseModel {
    public static final String TABLE_NAME = "hxinfo";//航线的表名
    private static Map<String, String> paramsMap = new HashMap<>();

    static {
        paramsMap.put(_ID, "integer primary key autoincrement");
        paramsMap.put("namehx", "TEXT NOT NULL");//航线名
    }

    //插入一条航线
    public void insertHX(HangXianBean hangXianBean){
        if (hangXianBean == null)
            return;
        ContentValues values = new ContentValues();
        values.put("namehx",hangXianBean.hangxian);
        if (isExist(hangXianBean.hangxian)) {//航线名存在
            update(values, "namehx=?", new String[]{hangXianBean.hangxian});
//            Toast.makeText(MyApplication.getMyApplication().getApplicationContext(),"此名称已存在",Toast.LENGTH_SHORT).show();
        } else {//如果没有则插入数据
            insert(values);
        }
    }

    public boolean isExist(String content) {
        Cursor cursor = select("select * from " + TABLE_NAME + " where namehx = '" + content + "'");
        if (cursor != null && cursor.moveToNext())
            return true;
        return false;
    }

    //删除一条航线
    public void deleteHX(int position){
        String content="";
        Cursor cursor = selectAll();
        if (cursor != null) {
            if (cursor.moveToPosition(position)) {
                content = cursor.getString(cursor.getColumnIndex("namehx"));
            }
            cursor.close();
        }
        delete("namehx=?", new String[]{content});
    }

     //删除最后一条航线
    public void deleteLast(String id) {
        String content = "";
        Cursor cursor = selectAllAsc(id);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                content = cursor.getString(cursor.getColumnIndex("namehx"));
            }
            cursor.close();
        }
        delete("namehx=?", new String[]{content});
    }

    //更新一条航线
    public void updadeHX(String id,HangXianBean hangXianBean,String[] whereArgs){
        if (hangXianBean == null)
            return;
        ContentValues values = new ContentValues();
        values.put("namehx",hangXianBean.hangxian);
        update(values,id,whereArgs);
    }

    //查询一条航线
    public HangXianBean getHangXianBeanById(String id) {
        HangXianBean hangXianBean = new HangXianBean();
        String sql = "select * from " + TABLE_NAME + " where namehx=" + id;
        Cursor cursor = select(sql);
        if (cursor != null) {
            if (cursor.moveToNext()) {//找到namehx为XXXX的数据了
                hangXianBean.hangxian = cursor.getString(cursor.getColumnIndex("namehx"));
            }
        }
        return hangXianBean;
    }

    public List<HangXianBean> getAll() {
        List<HangXianBean> hangXianBeanList = null;
        Cursor cursor = selectAllAsc(_ID);
        if (cursor != null) {
            hangXianBeanList = new ArrayList<>();
            while (cursor.moveToNext()) {
                HangXianBean hangXianBean = new HangXianBean();
                hangXianBean.hangxian = cursor.getString(cursor.getColumnIndex("namehx"));
                hangXianBeanList.add(hangXianBean);
            }
            cursor.close();
        }
        return hangXianBeanList;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected Map<String, String> getParamsMap() {
        return paramsMap;
    }
}
