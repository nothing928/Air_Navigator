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
import com.hanke.navi.skyair.pop.TianJiaPop;

/**
 * Created by Asus on 2016/12/14.
 */
public class ZengOrRemovePop extends PopupWindow implements Handler.Callback,View.OnClickListener{

    private Context context;
    private Handler handler;
    public static int popupHeight,popupWidth;
    private TextView bianji,shanchu;

    public ZengOrRemovePop(Context context) {
        this(context,null);
    }

    public ZengOrRemovePop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZengOrRemovePop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
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
            this.showAtLocation(view, Gravity.CENTER, point.x+2* MyApplication.getWidth()/8, point.y);//可以显示在指定view的指定位置
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
                TianJiaZhiPop tianJiaZhiPop = new TianJiaZhiPop(MyApplication.getAppContext());
                tianJiaZhiPop.showPopWindow(BaseActivity.mapView);
                dismissPopWindow();
                break;
            case R.id.shanchu:
                NavHLPop.deleteItem();
                this.dismissPopWindow();
                break;
        }
    }
}
