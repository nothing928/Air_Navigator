package com.hanke.navi.skyair.pop.msgpop;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.pop.msgpop.msgadapter.HangBanGJListViewAdapter;
import com.hanke.navi.skyair.pop.msgpop.msgadapter.HangBanJTListViewAdapter;
import com.hanke.navi.skyair.pop.msgpop.msgbean.HangBanGJBean;
import com.hanke.navi.skyair.pop.msgpop.msgbean.HangBanJTBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/11/23.
 */
public class MSGPop extends PopupWindow implements View.OnClickListener,Handler.Callback,View.OnTouchListener{


    private Context context;
    private Handler handler;
    private ListView lv_jiaotong,lv_gaojing;
    public List<HangBanJTBean> list_jt;
    public List<HangBanGJBean> list_gj;
    public HangBanJTListViewAdapter hangBanJTListViewAdapter;
    public HangBanGJListViewAdapter hangBanGJListViewAdapter;
    private TextView tv_benji,tv_jiaotong,tv_gaojing;
    private TextView[] textViews;
    private TextView jt_msg_num,gj_msg_num;
    private TextView msg_ed_jingdu,msg_ed_weidu,msg_ed_hangxiang,msg_ed_gaodu,msg_ed_sudu;
    private LinearLayout msg_benji,msg_jiaotong,msg_gaojing;
    private LinearLayout[] linearLayouts;
    private double longitude,latitude,haiba;
    private double speed,height;
    public double bearing;


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

//    public MSGPop(Context context, double longitude, double bearing, int height, int speed) {
//        this(context,null);
//        this.context = context;
//        this.longitude = longitude;
//        this.speed = speed;
//        this.height = height;
//        this.haiba = haiba;
//        this.bearing = bearing;
//        initView();
//    }
    public MSGPop(Context context, double longitude,double latitude,double bearing,double height,double speed) {
        this(context,null);
        this.context = context;
        this.longitude = longitude;
        this.latitude = latitude;
        this.bearing = bearing;
        this.height = height;
        this.speed = speed;
        initView();
    }

    public MSGPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MSGPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(){
        View view = View.inflate(context, R.layout.bt_msg,null);
        this.setContentView(view);
        this.setWidth(5*MyApplication.getWidth()/8);
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(4*MyApplication.getHeight()/8);
//        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
//        this.setBackgroundDrawable(new BitmapDrawable());
        view.setOnTouchListener(this);

        tv_benji = (TextView) view.findViewById(R.id.tv_benji);
        tv_benji.setOnClickListener(this);
        tv_jiaotong = (TextView) view.findViewById(R.id.tv_jiaotong);
        tv_jiaotong.setOnClickListener(this);
        tv_gaojing = (TextView) view.findViewById(R.id.tv_gaojing);
        tv_gaojing.setOnClickListener(this);
        //本机信息
        msg_benji = (LinearLayout) view.findViewById(R.id.msg_benji);
        msg_ed_jingdu = (TextView) view.findViewById(R.id.msg_ed_jingdu);
        msg_ed_weidu = (TextView) view.findViewById(R.id.msg_ed_weidu);
        msg_ed_hangxiang = (TextView) view.findViewById(R.id.msg_ed_hangxiang);
        msg_ed_gaodu = (TextView) view.findViewById(R.id.msg_ed_gaodu);
        msg_ed_sudu = (TextView) view.findViewById(R.id.msg_ed_sudu);
        //交通信息
        msg_jiaotong = (LinearLayout) view.findViewById(R.id.msg_jiaotong);
        msg_jiaotong.setVisibility(View.GONE);
        lv_jiaotong = (ListView) view.findViewById(R.id.lv_jiaotong);
        jt_msg_num = (TextView) view.findViewById(R.id.jt_msg_num);
        //告警信息
        msg_gaojing = (LinearLayout) view.findViewById(R.id.msg_gaojing);
        msg_gaojing.setVisibility(View.GONE);
        lv_gaojing = (ListView) view.findViewById(R.id.lv_gaojing);
        gj_msg_num = (TextView) view.findViewById(R.id.gj_msg_num);
        initData();
    }

    public void initData(){
        textViews = new TextView[]{tv_benji,tv_jiaotong,tv_gaojing};
        linearLayouts = new LinearLayout[]{msg_benji,msg_jiaotong,msg_gaojing};
        msg_ed_jingdu.setText(String.valueOf(longitude));
        msg_ed_weidu.setText(String.valueOf(getLatitude()));
//        msg_ed_weidu.setText(String.valueOf(latitude));
        msg_ed_hangxiang.setText(""+bearing);
        msg_ed_gaodu.setText(""+height);
        msg_ed_sudu.setText(""+speed);
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

    public void setSelect(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setTextColor(Color.BLUE);
                textViews[i].setEnabled(false);
                linearLayouts[i].setVisibility(View.VISIBLE);
            } else {
                textViews[i].setTextColor(Color.BLACK);
                textViews[i].setEnabled(true);
                linearLayouts[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_benji:
                setSelect(0);
                break;
            case R.id.tv_jiaotong:
                //航班号
                list_jt = new ArrayList<HangBanJTBean>();
                String[] arrays = new String[5];// 初始化数据源
                for (int i = 0; i < arrays.length; i++) {
                    HangBanJTBean hangBanJTBean = new HangBanJTBean();
//                    hangBanJTBean.hangbanhao_jt = "航班号"+(i+1);
                    hangBanJTBean.setHangbanhao_jt("航班号"+(i+1));
                    list_jt.add(hangBanJTBean);
                }
                hangBanJTListViewAdapter =new HangBanJTListViewAdapter(context,list_jt);
                lv_jiaotong.setAdapter(hangBanJTListViewAdapter);
                jt_msg_num.setText(hangBanJTListViewAdapter.getItemId(arrays.length)+"");
                //距离

                setSelect(1);
                break;
            case R.id.tv_gaojing:
                //航班号
                list_gj = new ArrayList<HangBanGJBean>();
                String[] arrs = new String[3];// 初始化数据源
                for (int i = 0; i < arrs.length; i++) {
                    HangBanGJBean hangBanGJBean = new HangBanGJBean();
                    hangBanGJBean.hangbanhao_gj = "告警的航机号"+(i+1);
                    list_gj.add(hangBanGJBean);
                }
                hangBanGJListViewAdapter =new HangBanGJListViewAdapter(context,list_gj);
                lv_gaojing.setAdapter(hangBanGJListViewAdapter);
                gj_msg_num.setText(hangBanGJListViewAdapter.getItemId(arrs.length)+"");
                //警告信息

                setSelect(2);
                break;
        }
    }

    int orgX, orgY;
    int offsetX, offsetY;
    int mScreenX=0,mScreenY=0;
    //手指移动pop
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                orgX = (int) event.getRawX();
                orgY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                mScreenX = offsetX;
                mScreenY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
//                offsetX = orgX+(int) event.getRawX();
//                offsetY = orgY+(int) event.getRawY();
                offsetX = (int) event.getRawX()-orgX+mScreenX;
                offsetY = orgY-(int) event.getRawY()+mScreenY;
                this.update(offsetX,-offsetY,-1,-1,true);
                break;
        }
        return true;
    }
}
