package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;

/**
 * Created by Che on 2016/11/23.
 */
public class AddOrDelPop extends PopupWindow implements Handler.Callback,View.OnClickListener{

    private Context context;
    private Handler handler;
    public int popupHeight,popupWidth;
    private TextView bianji,shanchu;
    private NavHXPop.ShowPopNavHLCallBack showPopNavHLCallBack;

    public AddOrDelPop(Context context) {
        this(context,null);
    }

    public AddOrDelPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AddOrDelPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void setShowPopNavHLCallBack(NavHXPop.ShowPopNavHLCallBack showPopNavHLCallBack) {
        this.showPopNavHLCallBack = showPopNavHLCallBack;
    }

    public void initView(){
        View view = View.inflate(context, R.layout.add_or_del,null);
        bianji = (TextView) view.findViewById(R.id.bianji);
        bianji.setOnClickListener(this);
        shanchu = (TextView) view.findViewById(R.id.shanchu);
        shanchu.setOnClickListener(this);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
    }


    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
//            this.showAsDropDown(view);//显示在view的下方
            int x = (int) view.getX();
            int y = (int) view.getY();
            Point point = new Point(x, y);
            this.showAtLocation(view, Gravity.CENTER, point.x+2*MyApplication.getWidth()/8, point.y);//可以显示在指定view的指定位置
//            this.showAtLocation(view, Gravity.CENTER, point.x+3*popupWidth, point.y-popupHeight/2);//可以显示在指定view的指定位置
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//            VerticalScaleScrollViewLeft verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(MyApplication.getAppContext());
//            showAtLocation(view, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, MyApplication.getHeight());
//            showAtLocation(view, Gravity.NO_GRAVITY, (location[0]) - popupWidth / 2, location[1] - popupHeight);
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
        switch (v.getId()){
            case R.id.bianji:
                int x = (int) v.getX();
                int y = (int) v.getY();
                Point point = new Point(x,y);
                if(showPopNavHLCallBack!=null){
                    showPopNavHLCallBack.showNavHLPop(point);
                }
                this.dismissPopWindow();
//                NavHLPop navHLPop = new NavHLPop(context);
//                navHLPop.showPopWindow(BaseActivity.mapView);
//                NavHXPop navHXPop = new NavHXPop(context);
//                navHXPop.dismissPopWindow();
//                TianJiaPop tianJiaPop = new TianJiaPop(MyApplication.getAppContext());
//                tianJiaPop.showPopWindow(BaseActivity.mapView);
                break;
            case R.id.shanchu:
                NavHXPop.deleteItem();
                this.dismissPopWindow();
                break;
        }
    }
}
