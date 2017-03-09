package com.hanke.navi.skyair.pop;

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
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.scale.BaseScaleView;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;
import com.hanke.navi.skyair.ui.MainActivity;

/**
 * Created by Asus on 2016/11/28.
 */
public class HintPop extends PopupWindow implements View.OnClickListener,Handler.Callback{


    private static final String TAG="HintPop";
    private Context context;
    private TextView tv_open_quan_jinggao,tv_close_quan_jinggao,
                        tv_open_quan_zhuyi,tv_close_quan_zhuyi,
                    tv_open_benji_quan_jinggao,tv_close_benji_quan_jinggao,
                    tv_open_benji_quan_zhuyi,tv_close_benji_quan_zhuyi;
    private Handler handler;
    private int popupHeight,popupWidth;
    VerticalScaleScrollViewLeft verticalScaleScrollViewLeft;

    public HintPop(Context context) {
        this(context,null);
    }

    public HintPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HintPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(context);
        initView();
//        init();
    }

    public void initView(){
        View view = View.inflate(context, R.layout.bt_tishiquan,null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        tv_open_quan_jinggao = (TextView) view.findViewById(R.id.tv_open_quan_jinggao);
        tv_open_quan_jinggao.setOnClickListener(this);
        tv_close_quan_jinggao = (TextView) view.findViewById(R.id.tv_close_quan_jinggao);
        tv_close_quan_jinggao.setOnClickListener(this);

        tv_open_quan_zhuyi = (TextView) view.findViewById(R.id.tv_open_quan_zhuyi);
        tv_open_quan_zhuyi.setOnClickListener(this);
        tv_close_quan_zhuyi = (TextView) view.findViewById(R.id.tv_close_quan_zhuyi);
        tv_close_quan_zhuyi.setOnClickListener(this);

        tv_open_benji_quan_jinggao = (TextView) view.findViewById(R.id.tv_open_benji_quan_jinggao);
        tv_open_benji_quan_jinggao.setOnClickListener(this);
        tv_close_benji_quan_jinggao = (TextView) view.findViewById(R.id.tv_close_benji_quan_jinggao);
        tv_close_benji_quan_jinggao.setOnClickListener(this);

        tv_open_benji_quan_zhuyi = (TextView) view.findViewById(R.id.tv_open_benji_quan_zhuyi);
        tv_open_benji_quan_zhuyi.setOnClickListener(this);
        tv_close_benji_quan_zhuyi = (TextView) view.findViewById(R.id.tv_close_benji_quan_zhuyi);
        tv_close_benji_quan_zhuyi.setOnClickListener(this);
        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
        Log.e("popupWidth---"+TAG,"popupWidth="+popupWidth);
        Log.e("popupHeight---"+TAG,"popupHeight="+popupHeight);
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
//            VerticalScaleScrollViewLeft verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(MyApplication.getAppContext());

//            showAtLocation(view, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, MyApplication.getHeight());

//            showAtLocation(view, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1] - popupHeight);
            showAtLocation(view, Gravity.NO_GRAVITY, MyApplication.getWidth()/2 , MyApplication.getHeight()-84* view.getHeight()/10);
            Log.e("位置0---"+TAG,"location[0]="+location[0]);
            Log.e("位置1---"+TAG,"location[1]="+location[1]);

//            showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);

//            this.showAtLocation(view, Gravity.NO_GRAVITY, location[0], location[1]-view.getHeight());
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
                handler.sendEmptyMessageDelayed(0,100);
            }
        },100);
//        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (this.context != null && this.isShowing()) {
            this.dismiss();
        }
        return false;
    }
    public void init(){
        if (BaseActivity.circle_nei==null||!BaseActivity.circle_nei.isVisible()){
            tv_open_quan_jinggao.setVisibility(View.VISIBLE);
            tv_open_benji_quan_jinggao.setVisibility(View.VISIBLE);
        }else{
            tv_close_quan_jinggao.setVisibility(View.VISIBLE);
            tv_close_benji_quan_jinggao.setVisibility(View.VISIBLE);
        }

        if (BaseActivity.circle_wai==null||!BaseActivity.circle_wai.isVisible()){
            tv_open_quan_zhuyi.setVisibility(View.VISIBLE);
            tv_open_benji_quan_zhuyi.setVisibility(View.VISIBLE);
        }else{
            tv_close_quan_zhuyi.setVisibility(View.VISIBLE);
            tv_close_benji_quan_zhuyi.setVisibility(View.VISIBLE);
        }
    }
//    tv_open_quan_jinggao,tv_close_quan_jinggao,
//    tv_open_quan_zhuyi,tv_close_quan_zhuyi,
//    tv_open_benji_quan_jinggao,tv_close_benji_quan_jinggao,
//    tv_open_benji_quan_zhuyi,tv_close_benji_quan_zhuyi;
//    Color.parseColor("#09cc0d")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_open_quan_jinggao:
                if (BaseActivity.circle_nei==null||!BaseActivity.circle_nei.isVisible()){
                    BaseActivity.addCircleNei(new LatLng(BaseActivity.latitude_clc,BaseActivity.longitude_clc),BaseActivity.radius_nei);
                    tv_open_quan_jinggao.setVisibility(View.GONE);
                    tv_close_quan_jinggao.setVisibility(View.VISIBLE);
                }else {
                    BaseActivity.circle_nei.setVisible(true);
                    tv_open_quan_jinggao.setVisibility(View.GONE);
                    tv_close_quan_jinggao.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_close_quan_jinggao:
                if (BaseActivity.circle_nei!=null||BaseActivity.circle_nei.isVisible()){
                    BaseActivity.circle_nei.setVisible(false);
                    BaseActivity.circle_nei.remove();
                    tv_close_quan_jinggao.setVisibility(View.GONE);
                    tv_open_quan_jinggao.setVisibility(View.VISIBLE);
                }else{
                    BaseActivity.circle_nei.setVisible(false);
                    tv_close_quan_jinggao.setVisibility(View.GONE);
                    tv_open_quan_jinggao.setVisibility(View.VISIBLE);
                    return;
                }

                break;
            case R.id.tv_open_quan_zhuyi:
                if (BaseActivity.circle_wai==null||!BaseActivity.circle_wai.isVisible()){
                    BaseActivity.addCircleWai(new LatLng(BaseActivity.latitude_clc,BaseActivity.longitude_clc),BaseActivity.radius_wai);
                    tv_open_quan_zhuyi.setVisibility(View.GONE);
                    tv_close_quan_zhuyi.setVisibility(View.VISIBLE);
                }else {
                    BaseActivity.circle_wai.setVisible(true);
                    tv_open_quan_zhuyi.setVisibility(View.GONE);
                    tv_close_quan_zhuyi.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_close_quan_zhuyi:
                if (BaseActivity.circle_wai!=null||BaseActivity.circle_wai.isVisible()){
                    BaseActivity.circle_wai.setVisible(false);
                    BaseActivity.circle_wai.remove();
                    tv_close_quan_zhuyi.setVisibility(View.GONE);
                    tv_open_quan_zhuyi.setVisibility(View.VISIBLE);
                }else{
                    BaseActivity.circle_wai.setVisible(false);
                    tv_close_quan_zhuyi.setVisibility(View.GONE);
                    tv_open_quan_zhuyi.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_open_benji_quan_jinggao:
                if (BaseActivity.circle_nei_ben==null||!BaseActivity.circle_nei_ben.isVisible()){
                    BaseActivity.addCircleNeiBen(new LatLng(BaseActivity.latitude,BaseActivity.longitude),BaseActivity.radius_nei);
                    tv_open_benji_quan_jinggao.setVisibility(View.GONE);
                    tv_close_benji_quan_jinggao.setVisibility(View.VISIBLE);
                }else {
                    BaseActivity.circle_nei_ben.setVisible(true);
                    tv_open_benji_quan_jinggao.setVisibility(View.GONE);
                    tv_close_benji_quan_jinggao.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_close_benji_quan_jinggao:
                if (BaseActivity.circle_nei_ben!=null||BaseActivity.circle_nei_ben.isVisible()){
                    BaseActivity.circle_nei_ben.setVisible(false);
                    BaseActivity.circle_nei_ben.remove();
                    tv_close_benji_quan_jinggao.setVisibility(View.GONE);
                    tv_open_benji_quan_jinggao.setVisibility(View.VISIBLE);
                }else{
                    BaseActivity.circle_nei_ben.setVisible(false);
                    tv_close_benji_quan_jinggao.setVisibility(View.GONE);
                    tv_open_benji_quan_jinggao.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_open_benji_quan_zhuyi:
                if (BaseActivity.circle_wai_ben==null||!BaseActivity.circle_wai_ben.isVisible()){
                    BaseActivity.addCircleWaiBen(new LatLng(BaseActivity.latitude,BaseActivity.longitude),BaseActivity.radius_wai);
                    tv_open_benji_quan_zhuyi.setVisibility(View.GONE);
                    tv_close_benji_quan_zhuyi.setVisibility(View.VISIBLE);
                }else {
                    BaseActivity.circle_wai_ben.setVisible(true);
                    tv_open_benji_quan_zhuyi.setVisibility(View.GONE);
                    tv_close_benji_quan_zhuyi.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_close_benji_quan_zhuyi:
                if (BaseActivity.circle_wai_ben!=null||BaseActivity.circle_wai_ben.isVisible()){
                    BaseActivity.circle_wai_ben.setVisible(false);
                    BaseActivity.circle_wai_ben.remove();
                    tv_close_benji_quan_zhuyi.setVisibility(View.GONE);
                    tv_open_benji_quan_zhuyi.setVisibility(View.VISIBLE);
                }else{
                    BaseActivity.circle_wai_ben.setVisible(false);
                    tv_close_benji_quan_zhuyi.setVisibility(View.GONE);
                    tv_open_benji_quan_zhuyi.setVisibility(View.VISIBLE);
                    return;
                }
                break;
        }
    }


}

