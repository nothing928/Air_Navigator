package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hanke.navi.R;
import com.hanke.navi.skyair.MyApplication;

/**
 * Created by Asus on 2016/12/1.
 */
public class PianZhiPop extends PopupWindow implements Handler.Callback {

    private Context context;
    private Handler handler;
    private int popupHeight, popupWidth;


    public PianZhiPop(Context context) {
        this(context, null);
    }

    public PianZhiPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PianZhiPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.pianzhi_direction, null);
        this.setContentView(view);
        this.setWidth(4* MyApplication.getWidth()/8);
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
            this.showAtLocation(view, Gravity.CENTER,0, 0);//可以显示在指定view的指定位置
//            this.showAtLocation(view, Gravity.CENTER,point.x-2*popupWidth, point.y);//可以显示在指定view的指定位置

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

