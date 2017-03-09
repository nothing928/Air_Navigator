package com.hanke.navi.skyair.pop.navpop.hx;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.skyair.pop.bean.HangXianBean;

import java.util.List;

public class HXAdapter extends BaseAdapter {

    private Context context;
    private List<HangXianBean> item_hx;
    private int selectHXItem = -1;

    public HXAdapter(Context context) {
        this.context = context;
    }

    public HXAdapter(Context context, List<HangXianBean> item_hx) {
        this.context = context;
        this.item_hx = item_hx;
    }

    public  void setSelectHXItem(int selectHXItem) {
        this.selectHXItem = selectHXItem;
    }

    @Override
    public int getCount() {
        return item_hx==null?0:item_hx.size();
    }

    @Override
    public Object getItem(int position) {
        return item_hx==null?0:item_hx.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.air_line_item, null);
            viewHolder.tv_air_line_item = (TextView) convertView.findViewById(R.id.tv_air_line_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_air_line_item.setText(item_hx.get(position).hangxian);
        if (position == selectHXItem) {
            viewHolder.tv_air_line_item.setTextColor(Color.YELLOW);
            convertView.setBackgroundColor(Color.parseColor("#ef6d6a6b"));
        }
        else {
            viewHolder.tv_air_line_item.setTextColor(Color.parseColor("#26cfe9"));
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_air_line_item;
    }

    public void setHXData(List<HangXianBean> data_hx){
        this.item_hx = data_hx;
    }
}
