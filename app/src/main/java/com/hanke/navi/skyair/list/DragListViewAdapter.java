package com.hanke.navi.skyair.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanke.navi.R;

import java.util.List;

/**
 * Created by Asus on 2016/12/25.
 */
public class DragListViewAdapter extends BaseAdapter {

    private Context context;
    List<BodyBean> items;

    public DragListViewAdapter(Context context, List<BodyBean> list) {
        this.context = context;
        this.items = list;
    }

    @Override
    public int getCount() {
        return items==null? 0:items.size();
    }

    @Override
    public Object getItem(int position) {
        return items == null? 0:items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {//删除指定位置的item
        items.remove(position);
        this.notifyDataSetChanged();
    }

    public void insert(BodyBean item, int position) {//在指定位置插入item
        items.add(position, item);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BodyBean item = (BodyBean) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.air_line_item,null);
            viewHolder.tv_air_line_item = (TextView) convertView.findViewById(R.id.tv_air_line_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_air_line_item.setText(item.wenzi);
        return convertView;
    }



    class ViewHolder {
        TextView tv_air_line_item;
    }
}
