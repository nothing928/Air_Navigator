package com.hanke.navi.skyair.infowindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;

/**
 * Created by Che on 2016/12/22.
 */
public class InfoWindowPop extends PopupWindow implements Handler.Callback {

    private static final String TAG = "InfoWindowPop";
    private Context context;
    private Handler handler;
    private TextView infowin_hangbanhao,
                    infowin_jingdu,
                    infowin_weidu,
                    infowin_sudu,
                    infowin_gaodu,
                    infowin_juli,
                    infowin_shengjiang,
                    infowin_gaojing;
    private int popupHeight, popupWidth;

    public InfoWindowPop(Context context) {
        this(context, null);
    }

    public InfoWindowPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoWindowPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
//        initData();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.infowindow_pop, null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        infowin_hangbanhao = (TextView) view.findViewById(R.id.infowin_hangbanhao);
        infowin_jingdu = (TextView) view.findViewById(R.id.infowin_jingdu);
        infowin_weidu = (TextView) view.findViewById(R.id.infowin_weidu);
        infowin_sudu = (TextView) view.findViewById(R.id.infowin_sudu);
        infowin_gaodu = (TextView) view.findViewById(R.id.infowin_gaodu);
        infowin_juli = (TextView) view.findViewById(R.id.infowin_juli);
        infowin_shengjiang = (TextView) view.findViewById(R.id.infowin_shengjiang);
        infowin_gaojing = (TextView) view.findViewById(R.id.infowin_gaojing);

        infowin_hangbanhao.setText("航班号");
        infowin_jingdu.setText(BaseActivity.longitude_clc+"°");
        infowin_weidu.setText(BaseActivity.latitude_clc+"°");
        infowin_sudu.setText("速度");
        infowin_gaodu.setText("高度");
        double dist = BaseActivity.distance;
        if (dist>=1000){
            infowin_juli.setText(dist/1000+"km");
        }else{
            infowin_juli.setText(dist+"m");
        }
        infowin_shengjiang.setText("升降");

        if (dist<BaseActivity.radius_nei){
            infowin_gaojing.setText("有飞机进入警示区");
            infowin_gaojing.setTextColor(Color.RED);
        }else if (dist>BaseActivity.radius_nei&&dist<=BaseActivity.radius_wai){
            infowin_gaojing.setText("有飞机进入避碰区");
            infowin_gaojing.setTextColor(Color.YELLOW);
        }else{
            infowin_gaojing.setText("无");
        }

        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
        Log.e("popupWidth---", "popupWidth=" + popupWidth);
        Log.e("popupHeight---", "popupHeight=" + popupHeight);
    }

    public void initData() {

    }

    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            showAtLocation(view, Gravity.NO_GRAVITY, location[0]+view.getWidth()/2-popupWidth/2, location[1]-popupHeight);
            Log.e("位置0---"+TAG,"location[0]="+location[0]);
            Log.e("位置1---"+TAG,"location[1]="+location[1]);
        }
    }

    /**
     * 结束popwindow
     */
    public void dismissPopWindow() {
        handler = new Handler(this);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0, 100);
            }
        }, 100);
//        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (this.context != null && this.isShowing()) {
            this.dismiss();
        }
        return false;
    }

}