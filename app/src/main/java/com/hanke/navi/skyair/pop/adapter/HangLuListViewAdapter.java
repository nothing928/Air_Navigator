package com.hanke.navi.skyair.pop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.skyair.pop.bean.HangLuBean;

import java.util.List;

/**
 * Created by Asus on 2017/1/5.
 */
public class HangLuListViewAdapter extends BaseAdapter {

    private Context context;
    public List<HangLuBean> items_hanglu;
    private int  selectHangLuItem=-1;

    public HangLuListViewAdapter(Context context) {
        this.context = context;
    }
    public HangLuListViewAdapter(Context context, List<HangLuBean> items_hanglu) {
        this.context = context;
        this.items_hanglu = items_hanglu;
    }

    public  void setSelectHangLuItem(int selectHangLuItem) {
        this.selectHangLuItem = selectHangLuItem;
    }

    public List<HangLuBean> getItems_hanglu() {
        return items_hanglu;
    }

    public void setItems_hanglu(List<HangLuBean> items_hanglu) {
        this.items_hanglu = items_hanglu;
    }

    @Override
    public int getCount() {
        return items_hanglu==null?0:items_hanglu.size();
    }

    @Override
    public Object getItem(int position) {
        return items_hanglu==null?0:items_hanglu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HangLuBean item = (HangLuBean) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.air_way_item, null);
            viewHolder.tv_air_way_item = (TextView) convertView.findViewById(R.id.tv_air_way_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_air_way_item.setText(item.hanglu);
        int size= (int) viewHolder.tv_air_way_item.getTextSize();
//        Log.e("zihao","字体size = "+size);
        if (position == selectHangLuItem) {
            viewHolder.tv_air_way_item.setTextColor(Color.YELLOW);
//            viewHolder.tv_air_way_item.setTextSize(TypedValue.COMPLEX_UNIT_SP,size+1);
            convertView.setBackgroundColor(Color.parseColor("#ef6d6a6b"));
        }
        else {
            viewHolder.tv_air_way_item.setTextColor(Color.parseColor("#26cfe9"));
//            viewHolder.tv_air_way_item.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_air_way_item;
    }
}
