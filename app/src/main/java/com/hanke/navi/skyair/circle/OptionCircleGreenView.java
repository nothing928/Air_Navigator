package com.hanke.navi.skyair.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.scale.BaseScaleView;

/**
 * Created by Che on 2016/11/22.
 */
public class OptionCircleGreenView extends ImageView {

    private final Paint paint;
    private final Context context;
    boolean addBackground = false;
    int radius = -1;      // 半径值初始化为-1
    int colorCircle;      // 圆圈颜色
    int colorBackground;  // 背景填充颜色

    public OptionCircleGreenView(Context context) {
        this(context, null);

//        //方法一：获取屏幕宽高
//        width = context.getResources().getDisplayMetrics().widthPixels;
//        height = context.getResources().getDisplayMetrics().heightPixels;
//        Log.e("circle","width = "+width+", height = "+height);
    }

    public OptionCircleGreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        colorCircle = Color.GREEN;// 默认颜色
        colorBackground = colorCircle;// 设定默认参数
    }
    // 属性设置方法
    public void setRadius(int r) {
        this.radius = r;
    }

    public void setColorCircle(int c) {
        this.colorCircle = c;
    }

    public void setColorBackground(int c) {
        this.colorBackground = c;
    }

    public void setAddBackground(boolean add) {
        this.addBackground = add;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 5;// 当前宽度的一半
        int innerCircle = MyApplication.getWidth()/32;       // 默认半径为86
        if (radius > 0) {
            innerCircle = dip2px(context, radius); // 如果没有另外设置半径，取半径86
        }

        Drawable drawable = getDrawable();
        if (addBackground) {
        } else {
            // 画圈圈；被点击后会变成实心的圈圈，默认是空心的
            this.paint.setAntiAlias(true);
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setDither(true);
            this.paint.setColor(Color.BLACK);
            this.paint.setStrokeWidth(BaseScaleView.width_view/35);
//            canvas.drawColor(colorCircle);

            Paint paint_nei=new Paint();
            paint_nei.setAntiAlias(true);
            paint_nei.setStyle(Paint.Style.FILL);
            paint_nei.setColor(colorBackground);
            paint_nei.setDither(true);
//            paint_nei.setStrokeWidth(3f);

            canvas.drawCircle(BaseScaleView.width_view/3, BaseScaleView.height_view/2,
                    MyApplication.getWidth()/40, this.paint);// 画外圆圈
            canvas.drawCircle(BaseScaleView.width_view/3, BaseScaleView.height_view/2,
                    MyApplication.getWidth()/40-BaseScaleView.width_view/90, paint_nei);// 画内圆圈
//            canvas.drawCircle(MyApplication.getWidth()/40+paint_nei.getStrokeWidth(), MyApplication.getHeight()/5,
//                    MyApplication.getWidth()/40, this.paint);// 画圆圈时带上偏移量
//            canvas.drawCircle(MyApplication.getWidth()/40+paint_nei.getStrokeWidth(), MyApplication.getHeight()/5,
//                    MyApplication.getWidth()/40-paint_nei.getStrokeWidth(), paint_nei);// 画圆圈外边框

//            canvas.drawCircle(center + centerOffsetX, center + centerOffsetY,
//                    innerCircle, this.paint);// 画圆圈时带上偏移量
        }
        super.onDraw(canvas);
    }

    /**
     * convert dp to px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置View的宽高
        setMeasuredDimension(MyApplication.getWidth(),MyApplication.getWidth());
        //获取自定义View的宽高
        int width_circle = this.getMeasuredWidth();//自定义View的宽度
        int height_circle = this.getMeasuredHeight();//自定义View的高度
        Log.e("circle","width_circle = "+width_circle+", height_circle = "+height_circle);
        MyApplication.getMyApplication().setMargins(this,BaseScaleView.width_view/5,0,0,0);
    }
}
