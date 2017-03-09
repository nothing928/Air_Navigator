package com.hanke.navi.skyair.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hanke.navi.skyair.MyApplication;

public class VerticalScaleScrollViewLeft extends BaseScaleView {

    float Yzhou;
    public static int scale_v;

    public VerticalScaleScrollViewLeft(Context context) {
        this(context,null);
    }

    public VerticalScaleScrollViewLeft(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScaleScrollViewLeft(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initVar(AttributeSet attrs) {

        //获取自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs, ATTR);
        mMin = ta.getInteger(SCALE_MIN, 0);
        mMax = ta.getInteger(SCALE_MAX, 3000);
        mScaleMargin = ta.getDimensionPixelOffset(SCALE_MARGIN, 20);
        mScaleHeight = ta.getDimensionPixelOffset(SCALE_HEIGHT, 20);

//        setBackgroundDrawable(R.drawable.scale_background);
        ta.recycle();
        mRectHeight = (mMax - mMin) * mScaleMargin;//总高度
        mRectWidth = mScaleHeight * 8;//总宽度
        mScaleMaxHeight = mScaleHeight * 2;//整刻度线的高度
        // 设置layoutParams
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(mRectWidth, mScaleHeight);
//        this.setLayoutParams(lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width = MeasureSpec.makeMeasureSpec(mRectWidth, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mScaleScrollViewRange = getMeasuredHeight();
        Log.e("msg","mScaleScrollViewRange = "+mScaleScrollViewRange);
        mTempScale = mScaleScrollViewRange / mScaleMargin / 2 + mMin;//用于判断滑动方向
        mMidCountScale = mScaleScrollViewRange / mScaleMargin / 2 + mMin;//中间刻度
        MyApplication.getMyApplication().setMargins(VerticalScaleScrollViewLeft.this,width_view/32,0,0,0);
    }

    @Override
    public void onDrawLine(Canvas canvas, Paint paint) {//画Y轴竖直线
//        paint.setTextSize(mRectWidth/20);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setDither(true);//设置防抖动
        paint.setStrokeWidth(width_view / 35);//控制线条的粗细
        float Yzhou=paint.getStrokeWidth();
        this.Yzhou = Yzhou;
//        canvas.drawLine(mScaleMaxHeight/4,-mScaleMargin-10,mScaleMaxHeight/4,mRectHeight+mScaleMargin+10,paint);
        canvas.drawLine(29*width_view / 32, -mScaleMargin - height_view/22, 29*width_view / 32, (mMax+1)* mScaleMargin + height_view/22, paint);
//        canvas.drawLine(width_view - mScaleMaxHeight / 2, -mScaleMargin - 10, width_view - mScaleMaxHeight / 2, mRectHeight + mScaleMargin + 10, paint);
    }

    @Override
    public void onDrawScale(Canvas canvas, Paint paint) {//画刻度线
//        paint.setTextSize(mRectWidth / 4);
        Paint linepaint = new Paint();
        linepaint.setAntiAlias(true);
        linepaint.setDither(true);
        for (int i = 0, k = mMin; i <= mMax - mMin; i++) {
            if (i % 10 == 0) {//整值，第三个参数可以修改线的长短

//                linepaint.setTextSize(mRectWidth/20);
                //整值刻度线
                linepaint.setColor(Color.WHITE);
                linepaint.setStrokeWidth(width_view / 35);//控制线条的粗细

//                canvas.drawLine(width_view-mScaleMaxHeight/2,i*mScaleMargin,width-(2*mScaleMaxHeight/3+mScaleMaxHeight/4),i*mScaleMargin,linepaint);
                canvas.drawLine(29*width_view / 32, i * mScaleMargin, 15*width_view / 32, i * mScaleMargin, linepaint);
//                canvas.drawLine(width_view - mScaleMaxHeight / 2, i * mScaleMargin, width_view - 3 * mScaleMaxHeight, i * mScaleMargin, linepaint);
                //画字的笔
                Paint textpaint = new Paint();
                textpaint.setTextSize(13*width_view / 64);
                textpaint.setColor(Color.WHITE);
                textpaint.setAntiAlias(true);//抗锯齿
                textpaint.setDither(true);
                textpaint.setTextAlign(Paint.Align.RIGHT);
//                textpaint.setStrokeWidth(7);
                //整值文字
                canvas.drawText(String.valueOf(mMax - k), 39* width_view / 50, i * mScaleMargin - height_view / 120, textpaint);
//                canvas.drawText(String.valueOf(mMax-k),width_view-4 * mScaleMaxHeight / 5,i * mScaleMargin -paint.getTextSize()/3,textpaint);
//                canvas.drawText(String.valueOf(mMax-k),1*mScaleMaxHeight/2,i*mScaleMargin+paint.getTextSize()+paint.getTextSize()/3-47,textpaint);
                k += 10;

            } else if (i % 5 == 0) {//中刻度线
                linepaint.setColor(Color.WHITE);
//                linepaint.setTextSize(mRectWidth / 5);
                linepaint.setStrokeWidth(width_view / 35);
                canvas.drawLine(29*width_view / 32, i * mScaleMargin, 19*width_view / 32, i * mScaleMargin, linepaint);
//                canvas.drawLine(width_view-mScaleMaxHeight/2, i *mScaleMargin, width-(mScaleMaxHeight/3+mScaleMaxHeight/4), i * mScaleMargin, linepaint);
//            }else {//小刻度线
//                linepaint.setColor(Color.GRAY);
//                linepaint.setTextSize(mRectWidth/4);
//                linepaint.setStrokeWidth(5);
//                canvas.drawLine(0, i * mScaleMargin, mScaleHeight, i * mScaleMargin, paint);
            }
        }
    }

    @Override
    public void onDrawPointer(Canvas canvas, Paint paint) {//画指针
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(width_view / 18);
        //每一屏幕刻度的个数/2，实际上是控制指刻度的指针在哪个位置
        int countScale = mScaleScrollViewRange / mScaleMargin / 2;
        //根据滑动的距离，计算指针的位置【指针始终位于屏幕中间】
        int finalY = mScroller.getFinalY();
        int tmpCountScale = 0;
        //滑动的刻度
        tmpCountScale = (int) Math.rint((double) finalY / (double) mScaleMargin); //四舍五入取整
        //总刻度  countScale
        mCountScale = tmpCountScale + countScale + mMin;
        if (mScrollListener != null) { //回调方法
            mScrollListener.onScaleScroll(mMax - mCountScale);
        }
//        //绿线
//        canvas.drawLine(29*width_view / 32-Yzhou/2, countScale * mScaleMargin + finalY,
////                mScaleMaxHeight + mScaleHeight,
//                width_view - 3 * mScaleMaxHeight,
////                width_view - ((mScaleMaxHeight + mScaleHeight) / 2 + mScaleMaxHeight / 5),
//                countScale * mScaleMargin + finalY, paint);

        // 绘制三角形外部
        Paint paint_san_wai = new Paint();
        paint_san_wai.setColor(Color.WHITE);
        paint_san_wai.setStyle(Paint.Style.STROKE);
        paint_san_wai.setStrokeWidth(width_view / 40);
        paint_san_wai.setAntiAlias(true);
        paint_san_wai.setDither(true);
        Path path_wai = new Path();
        path_wai.moveTo(29*width_view / 32-Yzhou/2, countScale * mScaleMargin + finalY);// 此点为多边形的起点
        path_wai.lineTo(24*width_view / 32, countScale * mScaleMargin + finalY-1*height_view/66);
        path_wai.lineTo(24*width_view / 32, countScale * mScaleMargin + finalY+1*height_view/66);
        path_wai.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path_wai, paint_san_wai);

        // 绘制三角形内部
        Paint paint_san_nei = new Paint();
        paint_san_nei.setColor(Color.parseColor("#22d640"));
        paint_san_nei.setAntiAlias(true);
        paint_san_nei.setDither(true);
        paint_san_nei.setStrokeWidth(width_view / 60);
        Path path_nei = new Path();
        path_nei.moveTo(29*width_view / 32-Yzhou/2-paint_san_nei.getStrokeWidth(), countScale * mScaleMargin + finalY);// 此点为多边形的起点
        path_nei.lineTo(24*width_view / 32+paint_san_nei.getStrokeWidth(), countScale * mScaleMargin + finalY-1*height_view/66+paint_san_nei.getStrokeWidth());
        path_nei.lineTo(24*width_view / 32+paint_san_nei.getStrokeWidth(), countScale * mScaleMargin + finalY+1*height_view/66-paint_san_nei.getStrokeWidth());
        path_nei.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path_nei, paint_san_nei);

        //画圆角矩形外边框
        Paint paint_kuang = new Paint();
        paint_kuang.setStyle(Paint.Style.STROKE);//充满
        paint_kuang.setStrokeWidth(width_view / 40);
        paint_kuang.setColor(Color.WHITE);
        paint_kuang.setAntiAlias(true);// 设置画笔的锯齿效果
        paint_kuang.setDither(true);
        RectF oval_kuang = new RectF(2*Yzhou,  //左
                countScale * mScaleMargin + finalY-1*height_view/22, //上
                20*width_view / 25, //右
//                30*width_view / 32, //右
                countScale * mScaleMargin + finalY+1*height_view/22);// 设置个新的长方形
        canvas.drawRoundRect(oval_kuang, width_view/7, width_view/7, paint_kuang);//第二个参数是x半径，第三个参数是y半径

        //画圆角矩形内部填充色
        Paint paint_nei = new Paint();
        paint_nei.setStyle(Paint.Style.FILL);//充满
        paint_nei.setColor(Color.parseColor("#22d640"));
        paint_nei.setAntiAlias(true);// 设置画笔的锯齿效果
        paint_nei.setDither(true);
//        paint_nei.setAlpha(127);
        paint_nei.setStrokeWidth(width_view / 100);
        RectF oval_nei = new RectF(2*Yzhou+paint_nei.getStrokeWidth(),
                countScale * mScaleMargin + finalY-1*height_view/22+paint_nei.getStrokeWidth(),
                20*width_view / 25-paint_nei.getStrokeWidth(),
                countScale * mScaleMargin + finalY+1*height_view/22-paint_nei.getStrokeWidth());// 设置个新的长方形
        canvas.drawRoundRect(oval_nei, width_view/7, width_view/7, paint_nei);//第二个参数是x半径，第三个参数是y半径

//        //方框
//        Paint fang_paint = new Paint();
//        fang_paint.setAntiAlias(true);
//        fang_paint.setDither(true);
//        fang_paint.setColor(Color.parseColor("#22d640"));
//        canvas.drawRect(2*width_view / 32,
//                countScale * mScaleMargin + finalY-1*height_view/22,
////                countScale * mScaleMargin + finalY-mRectWidth/3,
//                21*width_view / 25,
//                countScale * mScaleMargin + finalY+1*height_view/22,
////                countScale * mScaleMargin + finalY+mRectWidth / 4,
//                fang_paint);
        //方框里的字
        Paint middle_textpaint = new Paint();
        middle_textpaint.setAntiAlias(true);
        middle_textpaint.setDither(true);
        middle_textpaint.setColor(Color.WHITE);
        middle_textpaint.setTextSize(7*width_view/32);
        middle_textpaint.setTextAlign(Paint.Align.RIGHT);
        if (mMax - mCountScale>= mMax){
            canvas.drawText(mMax+"",49*width_view/64,countScale * mScaleMargin + finalY+1*height_view/44,middle_textpaint);
            scale_v = mMax;
        }else if (mMax - mCountScale<=mMin){
            canvas.drawText(mMin+"",49*width_view/64,countScale * mScaleMargin + finalY+1*height_view/44,middle_textpaint);
            scale_v = mMin;
        }
        canvas.drawText(mMax - mCountScale+"",49*width_view/64,countScale * mScaleMargin + finalY+1*height_view/44,middle_textpaint);
//        canvas.drawText(mMax - mCountScale+"",mScaleMaxHeight / 2,countScale * mScaleMargin + finalY+mRectWidth/10,middle_textpaint);
        scale_v = mMax - mCountScale;
//        Intent intent = new Intent(MyApplication.getAppContext(), MainActivity.class);
//        intent.putExtra("key",scale);
//        Log.e("123","33333333="+scale_v);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller != null && !mScroller.isFinished()) {
                    mScroller.abortAnimation();//停止动画
                }
                mScrollLastX = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                int dataY = mScrollLastX - y;
                if (mCountScale - mTempScale < 0) { //向下边滑动
                    if (mCountScale <= mMin && dataY <= 0) //禁止继续向下滑动
                        return super.onTouchEvent(event);
                } else if (mCountScale - mTempScale > 0) { //向上边滑动
                    if (mCountScale >= mMax && dataY >= 0) //禁止继续向上滑动
                        return super.onTouchEvent(event);
                }
                smoothScrollBy(0, dataY);
                mScrollLastX = y;
                postInvalidate();
                mTempScale = mCountScale;
                return true;
            case MotionEvent.ACTION_UP:
                if (mCountScale <= mMin)
                    mCountScale = mMin;
                if (mCountScale >= mMax)
                    mCountScale = mMax;
                int finalY = (mCountScale - mMidCountScale) * mScaleMargin;
                mScroller.setFinalY(finalY); //纠正指针位置
                postInvalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 指针滑动的刻度位置
     * @param mCountScale
     */
    public void setSDmCountScale(double mCountScale) {
        if (mCountScale > mMax)
            mCountScale = mMax;
        if (mCountScale < 0)
            mCountScale = 0;
        //每一屏幕刻度的个数/2，实际上是控制指刻度的指针在哪个位置
        double countScale = mScaleScrollViewRange / mScaleMargin / 2;
        double scale = mMax-(mCountScale+countScale);
        double finalY = scale * mScaleMargin;
        mScroller.setFinalY((int)finalY); //纠正指针位置
    }

}
