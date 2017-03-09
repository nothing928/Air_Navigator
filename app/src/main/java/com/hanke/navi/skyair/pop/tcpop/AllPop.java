package com.hanke.navi.skyair.pop.tcpop;

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

import com.amap.api.maps.model.LatLng;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;

/**
 * Created by Asus on 2016/12/30.
 */
public class AllPop extends PopupWindow implements Handler.Callback,CompoundButton.OnCheckedChangeListener{


    private static final String TAG="AllPop";
    private Context context;
    private CheckBox kongyua,kongyub,kongyuc,
            pihao_message,disu_message,gaodu_message,jixing_message,feidaihao_message,
            eta_time,xtk_pianhang;
    private Handler handler;
    private int popupHeight,popupWidth;

    public AllPop(Context context) {
        this(context,null);
    }

    public AllPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AllPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
//        init();
    }

    public void initView(){
        View view = View.inflate(context, R.layout.show_notice,null);
        this.setContentView(view);
        this.setWidth(5 * MyApplication.getWidth() / 8);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        kongyua= (CheckBox) view.findViewById(R.id.kongyua);
        kongyua.setOnCheckedChangeListener(this);
        kongyub= (CheckBox) view.findViewById(R.id.kongyub);
        kongyub.setOnCheckedChangeListener(this);
        kongyuc= (CheckBox) view.findViewById(R.id.kongyuc);
        kongyuc.setOnCheckedChangeListener(this);
        pihao_message= (CheckBox) view.findViewById(R.id.pihao_message);
        pihao_message.setOnCheckedChangeListener(this);
        disu_message= (CheckBox) view.findViewById(R.id.disu_message);
        disu_message.setOnCheckedChangeListener(this);
        gaodu_message= (CheckBox) view.findViewById(R.id.gaodu_message);
        gaodu_message.setOnCheckedChangeListener(this);
        jixing_message= (CheckBox) view.findViewById(R.id.jixing_message);
        jixing_message.setOnCheckedChangeListener(this);
//        feidaihao_message= (CheckBox) view.findViewById(R.id.feidaihao_message);
//        feidaihao_message.setOnCheckedChangeListener(this);
        eta_time= (CheckBox) view.findViewById(R.id.eta_time);
        eta_time.setOnCheckedChangeListener(this);
        xtk_pianhang= (CheckBox) view.findViewById(R.id.xtk_pianhang);
        xtk_pianhang.setOnCheckedChangeListener(this);


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
            this.showAtLocation(view, Gravity.CENTER, 0, 0);//可以显示在指定view的指定位置

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

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch(compoundButton.getId()) {
            case R.id.kongyua:
                if(b){
                    b=false;
                    BaseActivity.setSpaceAir(1, 2500);//开启空域
                }else {
                    b=true;
                    BaseActivity.polyline.remove();//关闭空域
                }
                break;
            case R.id.kongyub:
                if(b){
                    b=false;
                    BaseActivity.setSpaceAir2(1, 3000);//开启空域
                }else {
                    b=true;
                    BaseActivity.polyline2.remove();//关闭空域
                }
                break;
            case R.id.kongyuc:
                if(b){
                    b=false;
                    BaseActivity.setSpaceAir3(1, 3300);//开启空域
                }else {
                    b=true;
                    BaseActivity.polyline3.remove();//关闭空域
                }
                break;
            case R.id.pihao_message:

                break;
            case R.id.disu_message:

                break;
            case R.id.gaodu_message:

                break;
            case R.id.jixing_message:

                break;
//            case R.id.feidaihao_message:
//
//                break;
            case R.id.eta_time:

                break;
            case R.id.xtk_pianhang:

                break;
        }
    }
}
