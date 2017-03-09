package com.hanke.navi.skyair.pop.tcpop;

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

import com.amap.api.maps.AMap;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;

/**
 * Created by Asus on 2016/11/21.
 */
public class LayerPop extends PopupWindow implements View.OnClickListener, Handler.Callback {


    private static final String TAG = "LayerPop";
    private Context context;
    private TextView tv_map_putong, tv_map_weixing, tv_map_yejing, tv_show_notice,tv_open_air_space, tv_close_air_space;
    private TextView[] textViews;
    private Handler handler;
    private int popupHeight, popupWidth;

    public LayerPop(Context context) {
        this(context, null);
    }

    public LayerPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayerPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        handler = new Handler(this);
        initView();
        initData();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.bt_tuceng, null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        tv_map_putong = (TextView) view.findViewById(R.id.tv_map_putong);
        tv_map_weixing = (TextView) view.findViewById(R.id.tv_map_weixing);
        tv_map_yejing = (TextView) view.findViewById(R.id.tv_map_yejing);
        tv_show_notice = (TextView) view.findViewById(R.id.tv_show_notice);
        tv_open_air_space = (TextView) view.findViewById(R.id.tv_open_air_space);
        tv_close_air_space = (TextView) view.findViewById(R.id.tv_close_air_space);


        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
        Log.e("popupWidth" + TAG, "popupWidth=" + popupWidth);
        Log.e("popupHeight" + TAG, "popupHeight=" + popupHeight);

    }

    public void initData() {
        textViews = new TextView[]{tv_map_putong, tv_map_weixing, tv_map_yejing};
        tv_map_putong.setOnClickListener(this);
        tv_map_weixing.setOnClickListener(this);
        tv_map_yejing.setOnClickListener(this);
        tv_show_notice.setOnClickListener(this);
        tv_open_air_space.setOnClickListener(this);
        tv_close_air_space.setOnClickListener(this);
        if ((BaseActivity.polyline == null || !BaseActivity.polyline.isVisible())
                &&(BaseActivity.polyline2 == null || !BaseActivity.polyline2.isVisible())
                &&(BaseActivity.polyline3 == null || !BaseActivity.polyline3.isVisible())){
            tv_open_air_space.setVisibility(View.VISIBLE);
            tv_close_air_space.setVisibility(View.GONE);
        }else{
            if (BaseActivity.polyline != null || BaseActivity.polyline.isVisible()){
                tv_open_air_space.setVisibility(View.GONE);
                tv_close_air_space.setVisibility(View.VISIBLE);
            }
            if (BaseActivity.polyline2 != null || BaseActivity.polyline2.isVisible()){
                tv_open_air_space.setVisibility(View.GONE);
                tv_close_air_space.setVisibility(View.VISIBLE);
            }
            if (BaseActivity.polyline3 != null || BaseActivity.polyline3.isVisible()){
                tv_open_air_space.setVisibility(View.GONE);
                tv_close_air_space.setVisibility(View.VISIBLE);
            }
        }



//        if (BaseActivity.polyline == null || !BaseActivity.polyline.isVisible()) {
//            tv_open_air_space.setVisibility(View.VISIBLE);
//            tv_close_air_space.setVisibility(View.GONE);
//        } else {
//            tv_open_air_space.setVisibility(View.GONE);
//            tv_close_air_space.setVisibility(View.VISIBLE);
//        }
//        if (BaseActivity.polyline2 == null || !BaseActivity.polyline2.isVisible()) {
//            tv_open_air_space.setVisibility(View.VISIBLE);
//            tv_close_air_space.setVisibility(View.GONE);
//        } else {
//            tv_open_air_space.setVisibility(View.GONE);
//            tv_close_air_space.setVisibility(View.VISIBLE);
//        }
//        if (BaseActivity.polyline3 == null || !BaseActivity.polyline3.isVisible()) {
//            tv_open_air_space.setVisibility(View.VISIBLE);
//            tv_close_air_space.setVisibility(View.GONE);
//        } else {
//            tv_open_air_space.setVisibility(View.GONE);
//            tv_close_air_space.setVisibility(View.VISIBLE);
//        }

    }

    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
//            this.showAsDropDown(view);//显示在view的下方
//            this.showAtLocation(view, Gravity.BOTTOM, 0, 0);//可以显示在指定view的指定位置

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            Log.e("位置0" + TAG, "location[0]=" + location[0]);
            Log.e("位置1" + TAG, "location[1]=" + location[1]);

            showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() / 2 - popupWidth / 2, location[1] - popupHeight - (BaseActivity.height_include - view.getHeight()) / 2);

        }
    }

    /**
     * 结束popwindow
     */
    public void dismissPopWindow() {

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

    public void setSelect(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setTextColor(Color.GREEN);
                textViews[i].setEnabled(false);
            } else {
                textViews[i].setTextColor(Color.WHITE);
                textViews[i].setEnabled(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_map_putong:
                setSelect(0);
                BaseActivity.aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case R.id.tv_map_weixing:
                setSelect(1);
                BaseActivity.aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.tv_map_yejing:
                setSelect(2);
                BaseActivity.aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                break;
            case R.id.tv_show_notice:
                AllPop allPop=new AllPop(context);
                allPop.showPopWindow(BaseActivity.mapView);
                this.dismissPopWindow();
                break;
            case R.id.tv_open_air_space:
                if (BaseActivity.polyline == null || !BaseActivity.polyline.isVisible()) {
                    BaseActivity.setSpaceAir(1, 2500);//开启空域
//                    BaseActivity.setAirSpace();//开启空域
                    tv_open_air_space.setTextColor(Color.GREEN);//绿色
                    tv_open_air_space.setVisibility(View.GONE);
                    tv_close_air_space.setVisibility(View.VISIBLE);
                    tv_close_air_space.setTextColor(Color.WHITE);
                } else {
                    tv_open_air_space.setTextColor(Color.GREEN);//绿色
                    tv_open_air_space.setVisibility(View.GONE);
                    tv_close_air_space.setVisibility(View.VISIBLE);
                    tv_close_air_space.setTextColor(Color.WHITE);
                    return;
                }
                if (BaseActivity.polyline2 == null || !BaseActivity.polyline2.isVisible()) {
                    BaseActivity.setSpaceAir2(1, 3000);//开启空域
                    tv_open_air_space.setTextColor(Color.GREEN);//绿色
                    tv_open_air_space.setVisibility(View.GONE);
                    tv_close_air_space.setVisibility(View.VISIBLE);
                    tv_close_air_space.setTextColor(Color.WHITE);
                } else {
                    tv_open_air_space.setTextColor(Color.GREEN);//绿色
                    tv_open_air_space.setVisibility(View.GONE);
                    tv_close_air_space.setVisibility(View.VISIBLE);
                    tv_close_air_space.setTextColor(Color.WHITE);
                    return;
                }
                if (BaseActivity.polyline3 == null || !BaseActivity.polyline3.isVisible()) {
                    BaseActivity.setSpaceAir3(1, 3300);//开启空域
                    tv_open_air_space.setTextColor(Color.GREEN);//绿色
                    tv_open_air_space.setVisibility(View.GONE);
                    tv_close_air_space.setVisibility(View.VISIBLE);
                    tv_close_air_space.setTextColor(Color.WHITE);
                } else {
                    tv_open_air_space.setTextColor(Color.GREEN);//绿色
                    tv_open_air_space.setVisibility(View.GONE);
                    tv_close_air_space.setVisibility(View.VISIBLE);
                    tv_close_air_space.setTextColor(Color.WHITE);
                    return;
                }

                break;
            case R.id.tv_close_air_space:
                if (BaseActivity.polyline != null || BaseActivity.polyline.isVisible()) {
                    BaseActivity.polyline.setVisible(false);
                    BaseActivity.polyline.remove();//关闭空域
                    if (BaseActivity.text != null || BaseActivity.text.isVisible()) {
                        BaseActivity.text.destroy();
                    }else{
                        return;
                    }

                } else {
                    return;
                }
                if (BaseActivity.polyline2 != null || BaseActivity.polyline2.isVisible()) {
                    BaseActivity.polyline2.remove();//关闭空域
                    if (BaseActivity.text2 != null || BaseActivity.text2.isVisible()) {
                        BaseActivity.text2.destroy();
                    }else{
                        return;
                    }
                } else {
                    return;
                }
                if (BaseActivity.polyline3 != null || BaseActivity.polyline3.isVisible()) {
                    BaseActivity.polyline3.remove();//关闭空域
                    if (BaseActivity.text3 != null || BaseActivity.text3.isVisible()) {
                        BaseActivity.text3.destroy();
                    }else{
                        return;
                    }
                } else {
                    return;
                }
                tv_close_air_space.setTextColor(Color.parseColor("#09cc0d"));//绿色
                tv_close_air_space.setVisibility(View.GONE);
                tv_open_air_space.setVisibility(View.VISIBLE);
                tv_open_air_space.setTextColor(Color.WHITE);
                break;
        }
    }


}
