package com.hanke.navi.skyair.achartengine.one;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.hanke.navi.R;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;

import org.achartengine.GraphicalView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class DiXingPop1 extends PopupWindow {

    private Context context;
    GraphicalView graphicalView;
    public int popupHeight, popupWidth;
    VerticalScaleScrollViewLeft verticalScaleScrollViewLeft;
    ChartService1 chartService1;
    private Timer timer,timer2,timer3;
    private List<String> list,list3 ;
    private double t = 0 ,m=0,n=0;
    private Handler handler ;

    public DiXingPop1(Context context) {
        this(context, null);
    }

    public DiXingPop1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiXingPop1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        list = new ArrayList<String>();
        list3 = new ArrayList<String>();
        verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(context);
        initView();
    }

    public void initView() {


        read();
        chartService1 = new ChartService1(context);
        chartService1.setXYMultipleSeriesRenderer(35, 3000, null, Color.WHITE,0,0,
                13*verticalScaleScrollViewLeft.width_view / 64);

        chartService1.setXYMultipleSeriesDataset();
        chartService1.setXYMultipleSeriesDataset02();
        chartService1.setXYMultipleSeriesDataset03();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                chartService1.updateChart(t, list);
            }
        }, 10, 3000);//延时delay毫秒后周期性的执行task，其中周期是period毫秒

        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                double b =(Math.random()>0.5?1:-1)* 100;
                if (m<10){
                    chartService1.shangmianxian(m,2500-b);
                    m+=1;
                }else{
                    m=10;
                    chartService1.shangmianxian(m,2500);
                }
            }
        }, 10, 1000);//延时10毫秒后周期性的执行task，其中周期是3000毫秒

        timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                list3.add("2500");
                chartService1.zhixian(n,list3);

            }
        }, 10, 3000);//延时delay毫秒后周期性的执行task，其中周期是period毫秒

        this.graphicalView = chartService1.getGraphicalView();
//        View view = View.inflate(context, R.layout.shandi, null);
//        rl = (RelativeLayout) view.findViewById(R.id.rl);
//        rl.addView(graphicalView,RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(5* MyApplication.getHeight() /18-verticalScaleScrollViewLeft.height/105);
        this.setContentView(graphicalView);


        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new ColorDrawable());

        //获取自身的长宽高
        graphicalView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = graphicalView.getMeasuredHeight();
        popupWidth = graphicalView.getMeasuredWidth();




    }

    public void showPopWindow(View view) {
        if (!isShowing()) {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            showAtLocation(view, Gravity.NO_GRAVITY, 0, 13*MyApplication.getHeight() / 18-9*verticalScaleScrollViewLeft.height/210);
        }

    }

    public void dismissPopWindow() {
        if (this.context != null && this.isShowing()) {
            this.dismiss();
        }
    }

    public void read(){
        InputStream inputStream = context.getResources().openRawResource(R.raw.test);
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
//                Log.e("msg","sb = "+sb.toString());
//                String nr[] = sb.toString().split(",");
                Log.e("msg","line = "+line);
                String nr[] = line.split(",");
                Log.e("mg", "按逗号拆分的数据nr[2] = " + nr[2]);
                list.add(nr[2]);
            }
            Log.e("mg", "list.size() = " + list.size());
            for (int i = 0; i < list.size(); i++) {
                Log.e("mg", "list的第 "+ i+" 个值为 = " + list.get(i));
                Log.e("mg", "list的double第 "+ i+" 个值为 = " + Double.parseDouble(list.get(i)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}

