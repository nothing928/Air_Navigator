package com.hanke.navi.skyair.infowindow;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.icu.text.Normalizer2;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.model.Marker;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;

/**
 * Created by Che on 2016/12/22.
 */
public class InfoWinAdapter extends BaseAdapter implements InfoWindowAdapter {

    private Context context;
    private ViewHolder viewHolder;
    private SharedPreferences preferences;

    public InfoWinAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = null;
        preferences = context.getSharedPreferences("infowinadapter", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,R.layout.infowin,null);
            viewHolder.ifw_jingdu = (TextView)convertView.findViewById(R.id.ifw_jingdu);
            viewHolder.ifw_weidu = (TextView)convertView.findViewById(R.id.ifw_weidu);
            viewHolder.ifw_juli = (TextView)convertView.findViewById(R.id.ifw_juli);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        editor.putString("longitude_clc",BaseActivity.longitude_clc+"°");
        editor.commit();
        String jingdu = preferences.getString("longitude_clc","");
        viewHolder.ifw_jingdu.setText(jingdu);
//        viewHolder.ifw_jingdu.setText(BaseActivity.longitude_clc+"°");
        viewHolder.ifw_weidu.setText(BaseActivity.latitude_clc+"°");
        if (BaseActivity.distance>=1000){
            viewHolder.ifw_juli.setText(BaseActivity.distance/1000+"km");
        }else{
            viewHolder.ifw_juli.setText(BaseActivity.distance+"m");
        }
        return convertView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view  =  getView(0,null,null);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    public class ViewHolder{
        TextView ifw_jingdu,ifw_weidu,ifw_juli;
    }
}
