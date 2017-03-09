package com.hanke.navi.skyair.pop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanke.navi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/11/23.
 */
public class ListViewAdapter extends BaseAdapter{

    private List<String> list;
    private Context context;
    private ViewHodler viewHodler;

    public ListViewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return list == null? 0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list ==null ? null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHodler =null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = View.inflate(context, R.layout.air_line_item,null);
//            viewHodler.rl_air_line_item = (RelativeLayout) convertView.findViewById(R.id.rl_air_line_item);
            viewHodler.tv_air_line_item = (TextView) convertView.findViewById(R.id.tv_air_line_item);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        viewHodler.tv_air_line_item.setText("这是要的航线");

        return convertView;
    }

    private class ViewHodler {
//        RelativeLayout rl_air_line_item;
        TextView tv_air_line_item;
    }

}
