/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hanke.navi.skyair.achart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.FillOutsideLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Sales comparison demo chart.
 */
public class SalesComparisonChart extends AbstractDemoChart {

  public String getName() {
    return "Sales comparison";
  }


  public String getDesc() {
    return "Monthly sales advance for 2 years (interpolated line and area charts)";
  }

  public Intent execute(Context context) {
    String[] titles = new String[] { "2008 年销售额", "2007 年销售额","2008年销售额与2007年对比" };
    /* 初始化数据集 */
//    values.add(new double[] {0, 700, 1100, 900, 600, 400, 350, 1100, 2000, 2300,2500, 2100,1000,1300,1500,500,600,700,0 }) ;

        /* 初始化数据集 */
    List<double[]> values = new ArrayList<double[]>();
    /* 2008年销售额 */
    values.add(new double[] { 14230, 12300, 14240, 15244, 14900, 12200, 11030, 12000, 12500, 15500,14600, 15000,14000,13000,12000,11000,10000,9000,8000 });
    /* 2007年销售额 */
    values.add(new double[] { 14230, 11600, 13140, 14344, 14300, 11800, 10680, 10900, 10500, 13200,12100, 12900,13000,11700,10500,10500,9400,8300,8000 });
    /* 计算出两年销售额的对比差 2008年 减去 2007年 */
    int length = values.get(0).length;
    double[] diff = new double[length];
    for (int i = 0; i < length; i++) {
      diff[i] = values.get(0)[i] - values.get(1)[i];
    }
    values.add(diff);

//    /* 初始化数据集 */
//    List<double[]> values = new ArrayList<double[]>();
//    /* 2008年销售额 */
//    values.add(new double[] { 14230, 12300, 14240, 15244, 14900, 12200, 11030, 12000, 12500, 15500,14600, 15000 });
//    /* 2007年销售额 */
//    values.add(new double[] { 10230, 10900, 11240, 12540, 13500, 14200, 12530, 11200, 10500, 12500,11600, 13500 });
//    /* 计算出两年销售额的对比差 2008年 减去 2007年 */
//    int length = values.get(0).length;
//    double[] diff = new double[length];
//    for (int i = 0; i < length; i++) {
//      diff[i] = values.get(0)[i] - values.get(1)[i];
//    }
//    values.add(diff);


    /* 第一条线 蓝色 08年销售额, 第二条线 蓝绿色 07年销售额, 第三个面积图 绿色 两年销售额对比 */
    int[] colors = new int[] { 0, 0, Color.GREEN };
//    int[] colors = new int[] { Color.BLUE, Color.CYAN, Color.GREEN };
    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT, PointStyle.POINT };
    /* 创建图表渲染器 */
    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
    setChartSettings(renderer,      /* 渲染器 */
            null,/* 图表标题 */
            null,/* x轴标题 */
            null,/* y轴标题 */
            0,/* x轴最小值 */
            Integer.MAX_VALUE, /* x轴最大值 */
            0,  /* y轴最小值 */
            3000,/* y轴最大值 */
            Color.WHITE,/* x,y坐标轴颜色 */
            0);/* 标签颜色 标签即 图表标题 xy轴标题 */
    renderer.setMargins(new int[] { 50, 50, 50, 50 });
    renderer.setXLabels(7);/* 设置 x 轴刻度个数 */
    renderer.setXAxisMax(35,0);//设置X轴的最大值为35
    renderer.setYLabels(3);/* 设置 y 轴刻度个数 */
//    renderer.setBarWidth(1000);
//    renderer.setChartTitleTextSize(200);/* 设置表格标题字体大小 */
//    renderer.setTextTypeface("sans_serif", Typeface.BOLD);/* 设置字体 */
    renderer.setLabelsTextSize(30f);//设置xy轴刻度文字大小
    renderer.setAxisTitleTextSize(15);/*设置 xy 轴标题字体大小*/
//    renderer.setLegendTextSize(15);//设置说明文字大小
    renderer.setShowLegend(false);
//    renderer.setFitLegend(true);
    renderer.setApplyBackgroundColor(true);
    renderer.setBackgroundColor(Color.parseColor("#7f6E6782"));
    //设置点的大小: setPointSize(int size);
    //添加单曲线渲染器 : addSeriesRenderer(render);
//    XYSeriesRenderer 相关方法介绍 :
//    创建对象 : XYSeriesRenderer r = new XYSeriesRenderer();
//    设置单个曲线颜色 : r.setColor(colors[i]);
//    设置单个曲线绘制风格 : r.setPointStyle(styles[i]);
    length = renderer.getSeriesRendererCount();
    Log.e("258","xxxxx="+length);

    for (int i = 0; i < length; i++) {
      /* 获取具体的 渲染器 */
      XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
      if (i == length - 1) {
        /* 单独对面积图渲染器进行设置 */
        FillOutsideLine fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ALL);
        fill.setColor(Color.parseColor("#7f6cc17a"));
        seriesRenderer.addFillOutsideLine(fill);
      }
      /* 设置折线图渲染器 */
      seriesRenderer.setLineWidth(2.5f);//设置画的线条的宽度
//      seriesRenderer.setDisplayChartValues(false);//设置线条上点的文字
//      seriesRenderer.setChartValuesTextSize(20f);//设置线条上点的文字的大小
    }
    return ChartFactory.getCubicLineChartIntent(context, buildBarDataset(titles, values), renderer,0.5f);
//    return ChartFactory.getCubicLineChartIntent(context, buildBarDataset(titles, values), renderer,0.5f);
  }
}
