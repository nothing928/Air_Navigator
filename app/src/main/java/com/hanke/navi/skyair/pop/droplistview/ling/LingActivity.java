package com.hanke.navi.skyair.pop.droplistview.ling;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hanke.navi.R;

/**
 * Created by Che on 2016/12/16.
 */


public class LingActivity extends Activity {


    ListView daohang_lv;
    ArrayList<HashMap<String, String>> datalist;
    SimpleAdapter adapter;
    Button bn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_hangxian);


/*
* 设置listview显示数据
*/
//        daohang_lv = (ListView) findViewById(R.id.daohang_lv);
        datalist = new ArrayList<HashMap<String, String>>();
        datalist = getSortData();
        adapter = new SimpleAdapter(LingActivity.this, datalist,
                R.layout.air_line_item,
                new String[] { "sortName" },
                new int[] { R.id.tv_air_line_item });
        daohang_lv.setAdapter(adapter);


        ListviewHelper helper = new ListviewHelper(daohang_lv) {
            @Override
            public void resetListview() {
// TODO Auto-generated method stub
// listview重新显示
                adapter = new SimpleAdapter(LingActivity.this, datalist,
                        R.layout.air_line_item,
                        new String[] { "sortName" },
                        new int[] { R.id.tv_air_line_item });
                lv.setAdapter(adapter);
            }


            @Override
            public void changeItemPosition(int p1, int p2) {
// TODO Auto-generated method stub
// 根据自己listview的类型完成listview的数据源中id为p1和p2的两个item的替换
                HashMap<String, String> tempMap = datalist.get(p2);
                datalist.set(p2, datalist.get(p1));
                datalist.set(p1, tempMap);
                adapter = new SimpleAdapter(LingActivity.this, datalist,
                        R.layout.air_line_item,
                        new String[] { "sortName" },
                        new int[] { R.id.tv_air_line_item });
                lv.setAdapter(adapter);
            }
        };


        helper.enableChangeItems(true);


    }


    /*
    * 交换item
    */
    public void changeItemPosition(int p1, int p2) {
// TODO Auto-generated method stub
        Log.i("res", "交换位置的两项为p1-->" + p1 + "\tp2-->" + p2);
        HashMap<String, String> tempMap = new HashMap<String, String>();
        tempMap = datalist.get(p2);
        datalist.set(p2, datalist.get(p1));
        datalist.set(p1, tempMap);
        adapter = new SimpleAdapter(LingActivity.this, datalist,
                R.layout.air_line_item,
                new String[] { "sortName" },
                new int[] { R.id.tv_air_line_item });
        daohang_lv.setAdapter(null);
        Log.i("res", "changeItemPosition交换位置执行");
        for (int i = 0; i < datalist.size(); i++) {
            Log.i("res",
                    "item" + i + "\tcontent:" + datalist.get(i).get("sortName"));
        }
        daohang_lv.setAdapter(adapter);
    }


    /*
    * 初始化lv
    */
    public ArrayList<HashMap<String, String>> getSortData() {
        ArrayList<HashMap<String, String>> sortList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        map.put("sortName", "家常小炒");
        sortList.add(map);
        map = new HashMap<String, String>();
        map.put("sortName", "经典川菜");
        sortList.add(map);
        map = new HashMap<String, String>();
        map.put("sortName", "瓦罐煨汤");
        sortList.add(map);
        map = new HashMap<String, String>();
        map.put("sortName", "意面焗饭");
        sortList.add(map);
        map = new HashMap<String, String>();
        map.put("sortName", "精美小食");
        sortList.add(map);
        return sortList;


    }
}