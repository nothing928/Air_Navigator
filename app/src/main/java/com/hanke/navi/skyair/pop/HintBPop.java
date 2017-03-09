package com.hanke.navi.skyair.pop;

import android.content.Context;
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
import com.amap.api.maps.model.LatLng;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;

/**
 * Created by Che on 2016/12/19.
 */
public class HintBPop extends PopupWindow implements View.OnClickListener,Handler.Callback{


    private static final String TAG="HintBPop";
    private Context context;
    public TextView tv_open_warning,tv_close_warning,tv_open_attention,tv_close_attention;
    private Handler handler;
    private int popupHeight,popupWidth;
    VerticalScaleScrollViewLeft verticalScaleScrollViewLeft;

    public HintBPop(Context context) {
        this(context,null);
    }

    public HintBPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HintBPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(context);
        initView();
        initData();
    }

    public void initView(){
        View view = View.inflate(context, R.layout.bt_tishiquan_ben,null);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
//tv_open_warning,tv_close_warning,tv_open_attention,tv_close_attention
        tv_open_warning = (TextView) view.findViewById(R.id.tv_open_warning);
        tv_close_warning = (TextView) view.findViewById(R.id.tv_close_warning);
        tv_open_attention = (TextView) view.findViewById(R.id.tv_open_attention);
        tv_close_attention = (TextView) view.findViewById(R.id.tv_close_attention);

        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
        Log.e("popupWidth---"+TAG,"popupWidth="+popupWidth);
        Log.e("popupHeight---"+TAG,"popupHeight="+popupHeight);
    }

    public void initData(){
        tv_open_warning.setOnClickListener(this);
        tv_close_warning.setOnClickListener(this);
        tv_open_attention.setOnClickListener(this);
        tv_close_attention.setOnClickListener(this);

        if (BaseActivity.circle_nei_ben ==null||!BaseActivity.circle_nei_ben.isVisible()){
            tv_open_warning.setVisibility(View.VISIBLE);
            tv_close_warning.setVisibility(View.GONE);
        }else{
            tv_open_warning.setVisibility(View.GONE);
            tv_close_warning.setVisibility(View.VISIBLE);
        }
        if (BaseActivity.circle_wai_ben ==null||!BaseActivity.circle_wai_ben.isVisible()){
            tv_open_attention.setVisibility(View.VISIBLE);
            tv_close_attention.setVisibility(View.GONE);
        }else{
            tv_open_attention.setVisibility(View.GONE);
            tv_close_attention.setVisibility(View.VISIBLE);
        }
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
            showAtLocation(view, Gravity.NO_GRAVITY, location[0]+view.getWidth()/2-popupWidth/2 , location[1]-2*BaseActivity.height_include);
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

    @Override
    public void onClick(View v) {
        //tv_open_warning,tv_close_warning,tv_open_attention,tv_close_attention
        switch (v.getId()){
            case R.id.tv_open_warning://开启警告提示圈
                if (BaseActivity.circle_nei_ben==null||!BaseActivity.circle_nei_ben.isVisible()){
                    BaseActivity.addCircleNeiBen(new LatLng(BaseActivity.latitude,BaseActivity.longitude),BaseActivity.radius_nei);
                    tv_open_warning.setVisibility(View.GONE);
                    tv_close_warning.setVisibility(View.VISIBLE);
                }else {
                    BaseActivity.circle_nei_ben.setVisible(true);
                    tv_open_warning.setVisibility(View.GONE);
                    tv_close_warning.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_close_warning://关闭警告提示圈
                if (BaseActivity.circle_nei_ben!=null||BaseActivity.circle_nei_ben.isVisible()){
                    BaseActivity.circle_nei_ben.setVisible(false);
                    BaseActivity.circle_nei_ben.remove();
                    tv_open_warning.setVisibility(View.VISIBLE);
                    tv_close_warning.setVisibility(View.GONE);
                }else{
                    BaseActivity.circle_nei_ben.setVisible(false);
                    tv_open_warning.setVisibility(View.VISIBLE);
                    tv_close_warning.setVisibility(View.GONE);
                    return;
                }
                break;
            case R.id.tv_open_attention://开启注意提示圈
                if (BaseActivity.circle_wai_ben==null||!BaseActivity.circle_wai_ben.isVisible()){
                    BaseActivity.addCircleWaiBen(new LatLng(BaseActivity.latitude,BaseActivity.longitude),BaseActivity.radius_wai);
                    tv_open_attention.setVisibility(View.GONE);
                    tv_close_attention.setVisibility(View.VISIBLE);
                }else {
                    BaseActivity.circle_wai_ben.setVisible(true);
                    tv_open_attention.setVisibility(View.GONE);
                    tv_close_attention.setVisibility(View.VISIBLE);
                    return;
                }
                break;
            case R.id.tv_close_attention://关闭注意提示圈
                if (BaseActivity.circle_wai_ben!=null||BaseActivity.circle_wai_ben.isVisible()){
                    BaseActivity.circle_wai_ben.setVisible(false);
                    BaseActivity.circle_wai_ben.remove();
                    tv_open_attention.setVisibility(View.VISIBLE);
                    tv_close_attention.setVisibility(View.GONE);
                }else{
                    BaseActivity.circle_wai_ben.setVisible(false);
                    tv_open_attention.setVisibility(View.VISIBLE);
                    tv_close_attention.setVisibility(View.GONE);
                    return;
                }
                break;
        }
    }


}