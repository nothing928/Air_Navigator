package com.hanke.navi.skyair.achart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hanke.navi.R;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by Che on 2016/11/24.
 */
public class aaaaa extends Activity {

    private GraphicalView mChartView;
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();

    double[] xdata = new double[] { 11, 22, 33, 44, 55, 66, 77, 88, 99, 110 };
    double[] ydata = new double[] { 11, 11, 22, 33, 44, 55, 66, 77, 88, 99 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    /**
     * 得到住渲染器，并对其各项属性进行设置
     *
     * @return
     */
    public XYMultipleSeriesRenderer getRenderer() {
        /** 设置主渲染器的各种属性 */
        // 设置背景色是否启用
        mRenderer.setApplyBackgroundColor(true);
        // 设置背景色
        mRenderer.setBackgroundColor(Color.argb(100, 20, 30, 40));
        // 设置x y轴标题字体大小
        mRenderer.setAxisTitleTextSize(32);
        // 设置表格标题字体大小
        mRenderer.setChartTitleTextSize(20);
        // 设置标签字体大小
        mRenderer.setLabelsTextSize(30);
        // 设置图例字体大小
        mRenderer.setLegendTextSize(30);
        mRenderer.setMargins(new int[] { 20, 20, 20, 20 });//上左下右
        // 设置是否显示放大缩小按钮
        mRenderer.setZoomButtonsVisible(false);
        // 设置图表上显示点的大小
        mRenderer.setPointSize(10);
        mRenderer.setPanEnabled(false,false);

        // create a new renderer for the new series
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        // set some renderer properties
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        renderer.setDisplayChartValues(true);
        renderer.setDisplayChartValuesDistance(10);// 刻度之间的距离
        renderer.setColor(Color.RED);
        renderer.setChartValuesTextSize(30);
        renderer.setChartValuesTextAlign(Paint.Align.RIGHT);
        setSeriesWidgetsEnabled(true);
        mRenderer.addSeriesRenderer(renderer);
        return mRenderer;
    }

    /**
     * 得到住渲染器，并对其各项属性进行设置
     *
     * @return
     */
    public XYMultipleSeriesDataset getDataset() {
        String seriesTitle = "健康数据";
        XYSeries mSeries = new XYSeries(seriesTitle);
        for (int i = 0; i < 10; i++) {
            double x = xdata[i];
            double y = ydata[i];
            // 把坐标添加到当前序列中去
            mSeries.add(x, y);
        }
        mDataset.addSeries(mSeries);
        return mDataset;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getLineChartView(this, getDataset(), getRenderer());
            // enable the chart click events
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            mChartView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // handle the click event on the chart
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(aaaaa.this, "No chart element", Toast.LENGTH_SHORT).show();
                    } else {
                        // display information of the clicked point
                        Toast.makeText(aaaaa.this,
                                "Chart element in series index " + seriesSelection.getSeriesIndex()
                                + " data point index " + seriesSelection.getPointIndex()
                                        + " was clicked" + " closest point value X=" + seriesSelection.getXValue()
                                        + ", Y=" + seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            layout.addView(mChartView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            boolean enabled = mDataset.getSeriesCount() > 0;
            setSeriesWidgetsEnabled(enabled);
        } else {
            mChartView.repaint();
        }

    }

    /**
     * Enable or disable the add data to series widgets
     *
     * @param enabled
     *            the enabled state
     */
    private void setSeriesWidgetsEnabled(boolean enabled) {
        // mX.setEnabled(enabled);
        // mY.setEnabled(enabled);
        // mAdd.setEnabled(enabled);
    }

}
