package com.hanke.navi.skyair.pop.navpop.hl;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.callback.DeleteCallback;
import com.hanke.navi.skyair.pop.bean.HangLuBean;

public class HLZengShanPop extends PopupWindow implements View.OnClickListener{

    private Context context;
    public static int popupHeight,popupWidth;
    private TextView bianji,shanchu;
    private DeleteCallback deleteCallback;
    private int position;
    private HangLuBean hangLuBean;

    public HLZengShanPop(Context context, int position) {
        this(context,null);
        this.context = context;
        this.position = position;
        initView();
    }

    public void setHangLuBean(HangLuBean hangLuBean) {
        this.hangLuBean = hangLuBean;
    }

    public HLZengShanPop(Context context) {
        this(context,null);
    }

    public HLZengShanPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HLZengShanPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public void showPopWindow(View view) {
        if (!isShowing()) {
            int x = (int) view.getX();
            int y = (int) view.getY();
            Point point = new Point(x, y);
            this.showAtLocation(view, Gravity.CENTER, point.x+2* MyApplication.getWidth()/8, point.y);//可以显示在指定view的指定位置
        }
    }

    public void dismissPopWindow() {
        if (this.context != null && this.isShowing()) {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bianji:
                HLBianJiPop hlBianJiPop = new HLBianJiPop(context);
                hlBianJiPop.setHangLuBean(hangLuBean);
                hlBianJiPop.showPopWindow(BaseActivity.mapView);
                dismissPopWindow();
                break;
            case R.id.shanchu:
                deleteCallback.deletePosition(position);
                dismissPopWindow();
                break;
        }
    }

    public void setDeleteCallback(DeleteCallback deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

}
