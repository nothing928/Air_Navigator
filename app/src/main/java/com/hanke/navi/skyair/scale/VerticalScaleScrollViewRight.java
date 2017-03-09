package com.hanke.navi.skyair.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hanke.navi.R;
import com.hanke.navi.framwork.share.SharepreferenceHelper;
import com.hanke.navi.skyair.MyApplication;

/**
 * Created by Che on 2016/10/27.
 */
public class VerticalScaleScrollViewRight extends BaseScaleView {

    float Yzhou;
    public static double scale_h ;

    public VerticalScaleScrollViewRight(Context context) {
        this(context, null);
    }

    public VerticalScaleScrollViewRight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScaleScrollViewRight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initVar(AttributeSet attrs) {

        TypedArray ta = getContext().obtainStyledAttributes(attrs, ATTR);
        mMin = ta.getInteger(SCALE_MIN, 0);
        mMax = ta.getInteger(SCALE_MAX, 200000);
        mScaleMargin = ta.getDimensionPixelOffset(SCALE_MARGIN, 2);
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
        MyApplication.getMyApplication().setMargins(VerticalScaleScrollViewRight.this,0,0,width_view/32,0);
    }

    @Override
    public void onDrawLine(Canvas canvas, Paint paint) {//画Y轴竖直线
//        canvas.drawLine(0,0,0,mRectHeight,paint);

//        paint.setTextSize(mRectWidth / 20);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setDither(true);//设置防抖动
        paint.setStrokeWidth(width_view / 35);//控制线条的粗细
        float Yzhou=paint.getStrokeWidth();
        this.Yzhou = Yzhou;
        //第二个参数控制Y轴顶上出头部分。第四个参数控制Y轴底部出头部分
        canvas.drawLine(3*width_view / 32, -mScaleMargin - height_view/22, 3*width_view / 32, (mMax+1)*mScaleMargin + height_view/22, paint);
//        canvas.drawLine(mScaleMaxHeight / 2, -mScaleMargin - 10, mScaleMaxHeight / 2, mRectHeight + mScaleMargin + 10, paint);
    }

    @Override
    public void onDrawScale(Canvas canvas, Paint paint) {//画刻度线
//        paint.setTextSize(mRectWidth / 4);
        Paint linepaint = new Paint();
        linepaint.setAntiAlias(true);
        linepaint.setDither(true);
        for (int i = 0, k = mMin; i <= mMax - mMin; i++) {
            if (k<=mMax){

                if (i % 100 == 0) {//整值，第三个参数可以修改线的长短
//                linepaint.setTextSize(mRectWidth / 20);
                    linepaint.setColor(Color.WHITE);
                    linepaint.setStrokeWidth(width_view / 35);//控制线条的粗细
                    canvas.drawLine(3*width_view / 32, i * mScaleMargin, 17*width_view / 32, i * mScaleMargin, linepaint);
//                    canvas.drawLine(mScaleMaxHeight / 2, i * mScaleMargin, 3 * mScaleMaxHeight, i * mScaleMargin, linepaint);
                    //画字的笔
                    Paint textpaint = new Paint();
                    textpaint.setTextSize(13*width_view / 64);
//                    textpaint.setTextSize(7*mRectWidth / 24);
                    textpaint.setColor(Color.WHITE);
                    textpaint.setAntiAlias(true);//抗锯齿
                    textpaint.setDither(true);
//                textpaint.setStrokeWidth(7);
                    //整值文字
                    canvas.drawText(String.valueOf((mMax - i)), 11*width_view / 50, i * mScaleMargin -height_view / 120  , textpaint);
//                    canvas.drawText(String.valueOf((mMax - k)), 4 * mScaleMaxHeight / 5, i * mScaleMargin -paint.getTextSize()/5  , textpaint);
//                    canvas.drawText(String.valueOf((mMax - k)), 10 * mScaleMaxHeight / 5 + 5, i * mScaleMargin + paint.getTextSize() + paint.getTextSize() / 3 - 40, textpaint);
                    k += 100;
//                    Log.e("msg", "k=" + k);

                } else if (i % 50 == 0) {//中刻度线
                    linepaint.setColor(Color.WHITE);
//                    linepaint.setTextSize(mRectWidth / 5);
                    linepaint.setStrokeWidth(width_view / 35);
                    canvas.drawLine(3*width_view / 32, i * mScaleMargin, 13*width_view / 32 , i * mScaleMargin, linepaint);
//                    canvas.drawLine(mScaleMaxHeight / 2, i * mScaleMargin, 2 * mScaleMaxHeight , i * mScaleMargin, linepaint);
//            }else {//小刻度线
//                linepaint.setColor(Color.GRAY);
//                linepaint.setTextSize(mRectWidth/4);
//                linepaint.setStrokeWidth(5);
//                canvas.drawLine(0, i * mScaleMargin, mScaleHeight, i * mScaleMargin, paint);
                }
            }
        }
    }

    @Override
    public void onDrawPointer(Canvas canvas, Paint paint) {//画指针
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(width_view / 18);
        //每一屏幕刻度的个数/2
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
//        canvas.drawLine(3*width_view / 32+Yzhou/2, countScale * mScaleMargin + finalY,
//                //mScaleMaxHeight + mScaleHeight,
//                3 * mScaleMaxHeight,
//                countScale * mScaleMargin + finalY, paint);
//        canvas.drawLine(mScaleMaxHeight / 2+Yzhou/2, countScale * mScaleMargin + finalY,
//                //mScaleMaxHeight + mScaleHeight,
//                3 * mScaleMaxHeight,
//                countScale * mScaleMargin + finalY, paint);

        // 绘制三角形外部
        Paint paint_san_wai = new Paint();
        paint_san_wai.setColor(Color.WHITE);
        paint_san_wai.setStyle(Paint.Style.STROKE);
        paint_san_wai.setStrokeWidth(width_view / 40);
        paint_san_wai.setAntiAlias(true);
        paint_san_wai.setDither(true);
        Path path_wai = new Path();
        path_wai.moveTo(3*width_view / 32+Yzhou/2, countScale * mScaleMargin + finalY);// 此点为多边形的起点
        path_wai.lineTo(8*width_view / 32, countScale * mScaleMargin + finalY-1*height_view/66);
        path_wai.lineTo(8*width_view / 32, countScale * mScaleMargin + finalY+1*height_view/66);
        path_wai.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path_wai, paint_san_wai);

        // 绘制三角形内部
        Paint paint_san_nei = new Paint();
        paint_san_nei.setColor(Color.parseColor("#22d640"));
        paint_san_nei.setAntiAlias(true);
        paint_san_nei.setDither(true);
        paint_san_nei.setStrokeWidth(width_view / 60);
        Path path_nei = new Path();
        path_nei.moveTo(3*width_view / 32+Yzhou/2+paint_san_nei.getStrokeWidth(), countScale * mScaleMargin + finalY);// 此点为多边形的起点
        path_nei.lineTo(8*width_view / 32-paint_san_nei.getStrokeWidth(), countScale * mScaleMargin + finalY-1*height_view/66+paint_san_nei.getStrokeWidth());
        path_nei.lineTo(8*width_view / 32-paint_san_nei.getStrokeWidth(), countScale * mScaleMargin + finalY+1*height_view/66-paint_san_nei.getStrokeWidth());
        path_nei.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path_nei, paint_san_nei);

        //画圆角矩形外边框
        Paint paint_kuang = new Paint();
        paint_kuang.setStyle(Paint.Style.STROKE);//充满
        paint_kuang.setStrokeWidth(width_view / 40);
        paint_kuang.setColor(Color.WHITE);
        paint_kuang.setAntiAlias(true);// 设置画笔的锯齿效果
        paint_kuang.setDither(true);
        RectF oval_kuang = new RectF(5*width_view / 25,  //左
                countScale * mScaleMargin + finalY-1*height_view/22, //上
                width_view-2*Yzhou, //右
//                30*width_view / 32, //右
                countScale * mScaleMargin + finalY+1*height_view/22);// 设置个新的长方形
        canvas.drawRoundRect(oval_kuang, width_view/7, width_view/7, paint_kuang);//第二个参数是x半径，第三个参数是y半径

        //画圆角矩形内部填充色
        Paint paint_nei = new Paint();
        paint_nei.setStyle(Paint.Style.FILL);//充满
        paint_nei.setColor(Color.parseColor("#22d640"));
        paint_nei.setAntiAlias(true);// 设置画笔的锯齿效果
        paint_nei.setDither(true);
        paint_nei.setStrokeWidth(width_view / 100);
        RectF oval_nei = new RectF(5*width_view / 25+paint_nei.getStrokeWidth(),
                countScale * mScaleMargin + finalY-1*height_view/22+paint_nei.getStrokeWidth(),
                width_view-2*Yzhou-paint_nei.getStrokeWidth(),
                countScale * mScaleMargin + finalY+1*height_view/22-paint_nei.getStrokeWidth());// 设置个新的长方形
        canvas.drawRoundRect(oval_nei, width_view/7, width_view/7, paint_nei);//第二个参数是x半径，第三个参数是y半径

        //方框
//        Paint fang_paint = new Paint();
//        fang_paint.setAntiAlias(true);
//        fang_paint.setDither(true);
//        fang_paint.setColor(Color.parseColor("#22d640"));
//        canvas.drawRect(4*width_view / 25,
//                countScale * mScaleMargin + finalY-1*height_view/22,
////                countScale * mScaleMargin + finalY-mRectWidth/3,
//                30*width_view / 32,
//                countScale * mScaleMargin + finalY+1*height_view/22,
////                countScale * mScaleMargin + finalY+mRectWidth / 4,
//                fang_paint);

        //方框里的字
//        Bitmap bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.green_bgd));
//        Canvas canvas1 = new Canvas(bitmap);
//        canvas1.drawBitmap(bitmap,4 * mScaleMaxHeight / 5,);
        Paint middle_textpaint = new Paint();
        middle_textpaint.setAntiAlias(true);
        middle_textpaint.setDither(true);
        middle_textpaint.setColor(Color.WHITE);
        middle_textpaint.setTextSize(7*width_view/32);
        if (mMax - mCountScale>= mMax){
            canvas.drawText(mMax+"",15*width_view/64,countScale * mScaleMargin + finalY+1*height_view/44,middle_textpaint);
            scale_h = mMax;
        }else if (mMax - mCountScale<=mMin){
            canvas.drawText(mMin+"",15*width_view/64,countScale * mScaleMargin + finalY+1*height_view/44,middle_textpaint);
            scale_h = mMin;
        }
        canvas.drawText(mMax - mCountScale+"",15*width_view/64,countScale * mScaleMargin + finalY+1*height_view/44,middle_textpaint);
//        canvas.drawText(mMax - mCountScale+"",mScaleMaxHeight,countScale * mScaleMargin + finalY+mRectWidth/10,middle_textpaint);
        scale_h = mMax - mCountScale;
//        scale_h = Double.parseDouble(SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getGaodu());
//        if (mMax - mCountScale>= mMax){
//            scale_h = mMax;
//        }else if (mMax - mCountScale<=mMin){
//            scale_h = mMin;
//        }else{
//            scale_h = mMax - mCountScale;
//        }
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
    public void setGDmCountScale(double mCountScale) {
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
