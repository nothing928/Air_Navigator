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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;

/**
 * Created by Che on 2016/12/2.
 */
public class SetCorrPop extends PopupWindow implements Handler.Callback,View.OnClickListener {

    private static final String TAG = "SetCorrPop";
    private Context context;
    private Handler handler;
    private TextView set_gaojing,set_jichang;
    public static int popupHeight, popupWidth;

    public SetCorrPop(Context context) {
        this(context, null);
    }

    public SetCorrPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetCorrPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
//        initData();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.bt_set, null);
        set_gaojing = (TextView) view.findViewById(R.id.set_gaojing);
        set_gaojing.setOnClickListener(this);
        set_jichang = (TextView) view.findViewById(R.id.set_jichang);
        set_jichang.setOnClickListener(this);
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

    public void initData() {

    }

    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
//            this.showAsDropDown(view);//显示在view的下方
//            this.showAtLocation(view, Gravity.CENTER, 0, 0);//可以显示在指定view的指定位置
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set_gaojing:
                GaoJingPop gaoJingPop = new GaoJingPop(context);
                gaoJingPop.showPopWindow(BaseActivity.mapView);
                break;
            case R.id.set_jichang:
                JiChangPop jiChangPop=new JiChangPop(context);
                jiChangPop.showPopWindow(BaseActivity.mapView);
                break;

        }
    }
}

