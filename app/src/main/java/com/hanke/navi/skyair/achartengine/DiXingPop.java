package com.hanke.navi.skyair.achartengine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.hanke.navi.R;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.scale.BaseScaleView;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Che on 2017/2/28.
 */
public class DiXingPop extends PopupWindow {

    private Context context;
    GraphicalView graphicalView;
    public int popupHeight, popupWidth;
    VerticalScaleScrollViewLeft verticalScaleScrollViewLeft;
    ChartService chartService;
    private Timer timer;
    private List<String> list ;
    private double t = 0 ,a;
    private Handler handler ;

    public DiXingPop(Context context) {
        this(context, null);
    }

    public DiXingPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiXingPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        list = new ArrayList<String>();
        verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(context);
        initView();
    }

    public void initView() {


        read();
        chartService = new ChartService(context);
        chartService.setXYMultipleSeriesDataset("");
        chartService.setXYMultipleSeriesRenderer(35, 2000, null, null, null,
                Color.WHITE, 0, Color.parseColor("#af92D050"), 0,
                13*verticalScaleScrollViewLeft.width_view / 64);

        handler = new Handler() {
            @Override
            //更新地形图
            public void handleMessage(Message msg) {
//                a =Math.random()*(Math.random()>0.1?1:-1)* 2000;
//                if (a<0) {
//                    a= 0.0;
//                }
//                chartService.updateChart(t, a);
//                t += 2.5;

                chartService.updateChart(t, list);
            }
        };

        this.graphicalView = chartService.getGraphicalView();
//        View view = View.inflate(context, R.layout.shandi, null);
//        rl = (RelativeLayout) view.findViewById(R.id.rl);
//        rl.addView(graphicalView,RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(5*MyApplication.getHeight() /18-verticalScaleScrollViewLeft.height/105);
        this.setContentView(graphicalView);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(handler.obtainMessage());
            }
        }, 10, 3000);//延时delay毫秒后周期性的执行task，其中周期是period毫秒

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
