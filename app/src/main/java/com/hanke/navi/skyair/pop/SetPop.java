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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;

import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;

/**
 * Created by Che on 2016/12/2.
 */
public class SetPop extends PopupWindow implements Handler.Callback,CompoundButton.OnCheckedChangeListener {

    private Context context;
    private Handler handler;
    public static int popupHeight, popupWidth;
    public CheckBox kongyuyi,kongyuer,kongyusan;

    public SetPop(Context context) {
        this(context, null);
    }

    public SetPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
//        initData();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.set_page, null);
        kongyuyi = (CheckBox) view.findViewById(R.id.kongyuyi);
        kongyuyi.setOnCheckedChangeListener(this);
        kongyuer = (CheckBox) view.findViewById(R.id.kongyuer);
        kongyuer.setOnCheckedChangeListener(this);
        kongyusan = (CheckBox) view.findViewById(R.id.kongyusan);
        kongyusan.setOnCheckedChangeListener(this);

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

    public void initData() {
        if (BaseActivity.polyline == null || !BaseActivity.polyline.isVisible()) {
            kongyuyi.setChecked(false);
        } else {
            kongyuyi.setChecked(true);
        }
        if (BaseActivity.polyline2 == null || !BaseActivity.polyline2.isVisible()) {
            kongyuer.setChecked(false);
        } else {
            kongyuer.setChecked(true);
        }
        if (BaseActivity.polyline3 == null || !BaseActivity.polyline3.isVisible()) {
            kongyusan.setChecked(false);
        } else {
            kongyusan.setChecked(true);
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
            this.showAtLocation(view, Gravity.CENTER, 0, 0);//可以显示在指定view的指定位置
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch(compoundButton.getId()) {
            case R.id.kongyuyi:
                if(b){
                    b=false;
                    BaseActivity.setSpaceAir(1, 2500);//开启空域
                }else {
                    b=true;
                    BaseActivity.polyline.remove();//关闭空域
                }
                break;
            case R.id.kongyuer:
                if(b){
                    b=false;
                    BaseActivity.setSpaceAir2(1, 3000);//开启空域
                }else {
                    b=true;
                    BaseActivity.polyline2.remove();//关闭空域
                }
                break;
            case R.id.kongyusan:
                if(b){
                    b=false;
                    BaseActivity.setSpaceAir3(1, 3300);//开启空域
                }else {
                    b=true;
                    BaseActivity.polyline3.remove();//关闭空域
                }
                break;
        }
    }


}

