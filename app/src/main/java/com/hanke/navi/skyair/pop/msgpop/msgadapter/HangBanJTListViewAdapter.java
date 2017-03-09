package com.hanke.navi.skyair.pop.msgpop.msgadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.hanke.navi.R;
import com.hanke.navi.skyair.pop.msgpop.msgbean.HangBanJTBean;

import java.util.List;

/**
 * Created by Che on 2017/1/23.
 */
public class HangBanJTListViewAdapter extends BaseAdapter {

    private Context context;
    List<HangBanJTBean> items_hangbanjt;

    public HangBanJTListViewAdapter(Context context, List<HangBanJTBean> items_hangbanjt) {
        this.context = context;
        this.items_hangbanjt = items_hangbanjt;
    }

    @Override
    public int getCount() {
        return items_hangbanjt==null?0:items_hangbanjt.size();
    }

    @Override
    public Object getItem(int position) {
        return items_hangbanjt==null?0:items_hangbanjt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HangBanJTBean item = (HangBanJTBean) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.hangban,null);
            //交通信息
            viewHolder.tv_jt_hangbanhao = (TextView) convertView.findViewById(R.id.tv_hangbanhao);
            viewHolder.tv_jt_juli = (TextView) convertView.findViewById(R.id.tv_xinxi);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //交通信息
        viewHolder.tv_jt_hangbanhao.setText(item.hangbanhao_jt);
        viewHolder.tv_jt_juli.setText(item.juli_jt);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_jt_hangbanhao,tv_jt_juli;
    }
}
