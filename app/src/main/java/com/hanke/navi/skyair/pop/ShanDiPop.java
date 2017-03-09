package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/12/8.
 */
public class ShanDiPop extends PopupWindow implements Handler.Callback {

    private Context context;
    private Handler handler;
    GraphicalView graphicalView;
    public int popupHeight, popupWidth;
    VerticalScaleScrollViewLeft verticalScaleScrollViewLeft;

    public ShanDiPop(Context context) {
        this(context, null);
    }

    public ShanDiPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShanDiPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(context);
        initView();
    }

    public void initView() {
        String[] titles = new String[]{"2008 null", "2007 null", "对比 null"};

    /* 初始化数据集 */
//    values.add(new double[] {0, 700, 1100, 900, 600, 400, 350, 1100, 2000, 2300,2500, 2100,1000,1300,1500,500,600,700,0 }) ;


        /* 初始化数据集 */
        List<double[]> values = new ArrayList<double[]>();
    /* 2008 */
        values.add(new double[]{14230, 12300, 14240, 15244, 14900, 12200, 11030, 12000, 12500, 15500, 14600, 15000, 14000, 13000, 12000, 11000, 10000, 9000, 8000});
    /* 2007 */
        values.add(new double[]{14230, 11600, 13140, 14344, 14300, 11800, 10680, 10900, 10500, 13200, 12100, 12900, 13000, 11700, 10500, 10500, 9400, 8300, 8000});
    /* 对比*/
        int length = values.get(0).length;
        double[] diff = new double[length];
        for (int i = 0; i < length; i++) {
            diff[i] = values.get(0)[i] - values.get(1)[i];
        }
        values.add(diff);

        int[] colors = new int[]{0, 0, 0};//画的三条线
//        int[] colors = new int[]{0,0, Color.parseColor("#af92D050")};//画的三条线
        PointStyle[] styles = new PointStyle[]{PointStyle.POINT, PointStyle.POINT, PointStyle.POINT};
    /* 创建图表渲染器 */
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        setChartSettings(renderer,      /* 渲染器 */
                null,/* 图表标题 */
                "距离",/* x轴标题 */
                "高度",/* y轴标题 */
                0,/* x轴最小值 */
                20, /* x轴最大值 */
                0,  /* y轴最小值 */
                3000,/* y轴最大值 */
                Color.WHITE,/* x,y坐标轴颜色 */
                0);/* 标签颜色 标签即 图表标题 xy轴标题 */

        renderer.setMargins(new int[]{20,28* BaseScaleView.width_view / 32,0,28* BaseScaleView.width_view / 32});//参数一：上    参数二：左    参数三：下    参数四：右
//        renderer.setMargins(new int[] { CHART_MARGINS_TOP, CHART_MARGINS_LEFT, chart_margins_bottom, CHART_MARGINS_RIGHT });
        renderer.setXLabels(7);/* 设置 x 轴刻度个数 */
//        renderer.setXAxisMax(35, 0);//设置X轴的最大值为35
        renderer.setYLabels(3);/* 设置 y 轴刻度个数 */
//    renderer.setBarWidth(1000);
//    renderer.setChartTitleTextSize(200);/* 设置表格标题字体大小 */
//    renderer.setTextTypeface("sans_serif", Typeface.BOLD);/* 设置字体 */
        renderer.setLabelsTextSize(13*verticalScaleScrollViewLeft.width_view / 64);//设置xy轴刻度文字大小
        renderer.setXLabelsColor(Color.WHITE);//设置x轴刻度文字颜色
        renderer.setYLabelsColor(0,Color.WHITE);
        renderer.setAxisTitleTextSize(15);/*设置 xy 轴标题字体大小*/
//    renderer.setLegendTextSize(15);//设置说明文字大小
        renderer.setShowLegend(false);
//    renderer.setFitLegend(true);
        renderer.setApplyBackgroundColor(true);//设置是否应用背景色
        renderer.setBackgroundColor(Color.parseColor("#9f000000"));//图表刻度当中的背景颜色
        renderer.setMarginsColor(Color.parseColor("#00ffffff"));//设置整个的背景颜色，默认为黑色
        renderer.setAxesColor(Color.WHITE);//设置XY轴的颜色
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setXLabelsPadding(10);
        renderer.setYLabelsPadding(20);
        renderer.setYLabelsVerticalPadding(-renderer.getLabelsTextSize()/2);
//        renderer.setPanLimits(new double[]{0,Integer.MAX_VALUE,0, Integer.MAX_VALUE});
        renderer.setPanLimits(new double[]{Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE, 0});
//        renderer.setPanLimits(new double[] { minX, maxX, minY, maxY});
//        renderer.setBarSpacing(10);//图形的显示大小  数值越小 面积越大
//        renderer.setPanLimits(new double[]{0,(XLabel.size()) * 2, 0, 0});
//        renderer.setLabelsColor(Color.GREEN);//设置Y轴的颜色
//        renderer.setLegendHeight(20);
//        renderer.setBarWidth(10);
        //设置点的大小: setPointSize(int size);
        //添加单曲线渲染器 : addSeriesRenderer(render);
//    XYSeriesRenderer 相关方法介绍 :
//    创建对象 : XYSeriesRenderer r = new XYSeriesRenderer();
//    设置单个曲线颜色 : r.setColor(colors[i]);
//    设置单个曲线绘制风格 : r.setPointStyle(styles[i]);
        length = renderer.getSeriesRendererCount();
        Log.e("258", "xxxxx=" + length);

        for (int i = 0; i < length; i++) {
      /* 获取具体的 渲染器 */
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            if (i == length - 1) {
        /* 单独对面积图渲染器进行设置 */
                XYSeriesRenderer.FillOutsideLine fill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                fill.setColor(Color.parseColor("#af92D050"));//山体填充区的颜色
//                fill.setColor(Color.parseColor("#7f6cc17a"));//山体填充区的颜色
                seriesRenderer.addFillOutsideLine(fill);
            }
      /* 设置折线图渲染器 */
            seriesRenderer.setLineWidth(2.5f);//设置画的线条的宽度
//      seriesRenderer.setDisplayChartValues(false);//设置线条上点的文字
//      seriesRenderer.setChartValuesTextSize(20f);//设置线条上点的文字的大小
        }

        //画虚线
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        PathEffect effects = new DashPathEffect(new float[] {5, 5, 5, 5 }, 1);//设置虚线的间隔和点的长度
        paint.setPathEffect(effects);
        paint.setColor(Color.RED);
//        paint.setColor(renderer.getGridColor());
        Canvas canvas = new Canvas();
        canvas.drawLine(0, 5*MyApplication.getHeight()/6, MyApplication.getWidth(),5*MyApplication.getHeight()/6, paint);

//        Bundle extras = this.getIntent().getExtras();
        this.graphicalView = ChartFactory.getCubeLineChartView(context,buildBarDataset(titles, values), renderer, 0.45f);
//        View view = View.inflate(context, R.layout.shandi, null);
//        rl = (RelativeLayout) view.findViewById(R.id.rl);
//        rl.addView(graphicalView,RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(5*MyApplication.getHeight() /18-verticalScaleScrollViewLeft.height/105);
        this.setContentView(graphicalView);
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(DisplayUtil.getDensity_Height(context));

        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new ColorDrawable());

        //获取自身的长宽高
        graphicalView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = graphicalView.getMeasuredHeight();
        popupWidth = graphicalView.getMeasuredWidth();




    }
    boolean isBac;
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
            showAtLocation(view, Gravity.NO_GRAVITY, 0, 13*MyApplication.getHeight() / 18-9*verticalScaleScrollViewLeft.height/210);
//            showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - view.getHeight()/2-popupHeight);
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


    /**
     * 几个方法
     */
    //
    public static XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    //
    public static void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
        renderer.setMargins(new int[]{20, 30, 15, 20});
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
    }

    //
    public static void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                                        String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
                                        int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    //
    public static XYMultipleSeriesDataset buildBarDataset(String[] titles, List<double[]> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            double[] v = values.get(i);
            int seriesLength = v.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(v[k]);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }
}
