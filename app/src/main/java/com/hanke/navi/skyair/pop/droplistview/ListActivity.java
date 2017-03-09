package com.hanke.navi.skyair.pop.droplistview;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanke.navi.R;

public class ListActivity extends Activity {

    private static List<String> list = new ArrayList<String>();
    private static List<String> keyGroup = new ArrayList<String>();

    private List<String> navList = new ArrayList<String>();

    private DragListAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_main);
        //初始化数据
        initData();
        ListView dragView = (ListView) this.findViewById(R.id.dragView);
        adapter = new DragListAdapter(this, list);
        dragView.setAdapter(adapter);
    }

    //初始化数据
    public void initData(){
        for(int i = 1;i<= 10; i++){
            navList.add("A组Data0"+i);
        }
        list.addAll(navList);
    }



    //渲染不同的view
//    public static class DragListAdapter extends BaseAdapter{
    public static class DragListAdapter extends ArrayAdapter<String>{
        public DragListAdapter(Context context,List<String> objects) {
            super(context, 0, objects);
        }
        public List<String> getList(){
            return list;
        }


        //检查数据项是否为标题项然后标记是否可以更改
        @Override
        public boolean isEnabled(int position) {
            if(keyGroup.contains(getItem(position))){
                return false;
            }
            return super.isEnabled(position);
        }

        //利用模板布局不同的listview
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if(keyGroup.contains(getItem(position))){
                //标题组
                view = LayoutInflater.from(getContext()).inflate(R.layout.textview, null);
            }else{
                //图片组
                view = LayoutInflater.from(getContext()).inflate(R.layout.textandimage, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.headtext);
            textView.setText(getItem(position));
            return view;
        }
    }
}
