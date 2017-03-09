package com.hanke.navi.skyair.pop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.skyair.pop.bean.HangXianBean;
import java.util.List;

/**
 * Created by Asus on 2017/1/5.
 */
public class HangXianListViewAdapter extends BaseAdapter{
    private Context context;
    List<HangXianBean> items_hangxian;
    private int  selectHangXianItem=-1;

    public HangXianListViewAdapter(Context context, List<HangXianBean> items_hanxian) {
        this.context = context;
        this.items_hangxian = items_hanxian;
    }

    public  void setSelectHangXianItem(int selectHangXianItem) {
        this.selectHangXianItem = selectHangXianItem;
    }

    @Override
    public int getCount() {
        return items_hangxian==null?0:items_hangxian.size();
    }

    @Override
    public Object getItem(int position) {
        return items_hangxian==null?0:items_hangxian.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HangXianBean item = (HangXianBean) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.air_line_item, null);
            viewHolder.tv_air_line_item = (TextView) convertView.findViewById(R.id.tv_air_line_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_air_line_item.setText(item.hangxian);
        if (position == selectHangXianItem) {
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

}
