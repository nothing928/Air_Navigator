package com.hanke.navi.skyair.pop.msgpop.msgadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.skyair.pop.msgpop.msgbean.HangBanGJBean;
import com.hanke.navi.skyair.pop.msgpop.msgbean.HangBanJTBean;

import java.util.List;

/**
 * Created by Che on 2017/1/23.
 */
public class HangBanGJListViewAdapter extends BaseAdapter {

    private Context context;
    List<HangBanGJBean> items_hangbangj;

    public HangBanGJListViewAdapter(Context context, List<HangBanGJBean> items_hangbangj) {
        this.context = context;
        this.items_hangbangj = items_hangbangj;
    }

    @Override
    public int getCount() {
        return items_hangbangj==null?0:items_hangbangj.size();
    }

    @Override
    public Object getItem(int position) {
        return items_hangbangj==null?0:items_hangbangj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HangBanGJBean item = (HangBanGJBean) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.hangban,null);
            //警告信息
            viewHolder.tv_gj_hangbanhao = (TextView) convertView.findViewById(R.id.tv_hangbanhao);
            viewHolder.tv_gj_jinggaoxinxi = (TextView) convertView.findViewById(R.id.tv_xinxi);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //警告信息
        viewHolder.tv_gj_hangbanhao.setText(item.hangbanhao_gj);
        viewHolder.tv_gj_jinggaoxinxi.setText(item.jinggaoxinxi_gj);
        return convertView;
    }

    private class ViewHolder {
        TextView tv_gj_hangbanhao,tv_gj_jinggaoxinxi;
    }
}
