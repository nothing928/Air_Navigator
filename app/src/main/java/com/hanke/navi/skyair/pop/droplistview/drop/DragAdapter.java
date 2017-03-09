package com.hanke.navi.skyair.pop.droplistview.drop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanke.navi.R;

import java.util.List;

/**
 * Created by Che on 2016/12/16.
 */
public class DragAdapter extends BaseAdapter{

    public Context context;
    public List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }

    public DragAdapter(Context context) {
        this(context,null);
        this.context = context;
    }

    public DragAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null? 0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list ==null? 0 :list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.air_line_item,null);
            viewHolder.tv_air_line_item = (TextView) convertView.findViewById(R.id.tv_air_line_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position)!=null){
//            for (int i=1;i<=5;i++){
                viewHolder.tv_air_line_item.setText("航线"+0);
//            }

        }
        return convertView;
    }

    public List<String> getList(){
        for (int i=1;i<=10;i++){
            list.add("航线"+i);
        }
        return list;
    }

    public class ViewHolder{
        TextView tv_air_line_item;
    }
}
