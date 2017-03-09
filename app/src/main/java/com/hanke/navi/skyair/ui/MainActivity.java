package com.hanke.navi.skyair.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.framwork.utils.Constants;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.achartengine.one.DiXingPop1;
import com.hanke.navi.skyair.circle.OptionCircleGreenView;
import com.hanke.navi.skyair.circle.OptionCircleRedView;
import com.hanke.navi.skyair.circle.OptionCircleYellowView;
import com.hanke.navi.skyair.pop.HintBPop;
import com.hanke.navi.skyair.pop.tcpop.LayerPop;
import com.hanke.navi.skyair.pop.msgpop.MSGPop;
import com.hanke.navi.skyair.pop.SetCorrPop;
import com.hanke.navi.skyair.pop.navpop.hx.HXPop;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewRight;

public class MainActivity extends BaseActivity {

    private static final String TAG="MainActivity";
    private boolean isBack = true;//用于监听onKeyDown
    private TextView tv_tuceng,tv_daohang,tv_msg,tv_dixing,tv_home,tv_tishiquan,tv_shezhi;
    private Handler handler;
    public static ImageView mm;
    private VerticalScaleScrollViewLeft verticalScaleLeft;//左边指针刻度尺
    private VerticalScaleScrollViewRight verticalScaleRight;//左边指针刻度尺

    public static void setImgWei() {
        //获取这个图片的宽和高
        Bitmap bitmapOrg = BitmapFactory.decodeResource(MyApplication.getAppContext().getResources(), R.mipmap.qfj);
        int width = bitmapOrg.getWidth();
        Log.e("msg", "width=" + width );
        int height = bitmapOrg.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        //旋转图片 动作
        matrix.postRotate(0);
        // 创建新的图片

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0,0, width, height, matrix, true);
        //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        // 设置ImageView的图片为上面转换的图片
        mm.setImageDrawable(bmd);
        //将图片居中显示
        mm.setScaleType(ImageView.ScaleType.CENTER);
    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void beforeInitView() {

        tv_tuceng = findViewByIdNoCast(R.id.tv_tuceng);
        tv_daohang = findViewByIdNoCast(R.id.tv_daohang);
        tv_msg = findViewByIdNoCast(R.id.tv_msg);
        tv_dixing = findViewByIdNoCast(R.id.tv_dixing);
        tv_home = findViewByIdNoCast(R.id.tv_home);
        mm = findViewByIdNoCast(R.id.mm);
//        LinearLayout.LayoutParams lp_home = (LinearLayout.LayoutParams) tv_home.getLayoutParams();
//        lp_home.width = height_include;
//        tv_home.setLayoutParams(lp_home);
        tv_tishiquan = findViewByIdNoCast(R.id.tv_tishiquan);
        tv_shezhi = findViewByIdNoCast(R.id.tv_shezhi);
        circle_green = (OptionCircleGreenView) findViewById(R.id.circle_green);
        circle_red = (OptionCircleRedView) findViewById(R.id.circle_red);
        circle_yellow = (OptionCircleYellowView) findViewById(R.id.circle_yellow);
        handler = new Handler();
        verticalScaleLeft = findViewByIdNoCast(R.id.verticalScaleLeft);
        verticalScaleRight = findViewByIdNoCast(R.id.verticalScaleRight);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                verticalScaleLeft.setSDmCountScale(389.5);//此处传收到的速度值
                verticalScaleRight.setGDmCountScale(1005.69);//此处传收到的高度值
            }
        },200);
    }

    @Override
    public void initView() {
        tv_tuceng.setOnClickListener(this);
        tv_daohang.setOnClickListener(this);
        tv_msg.setOnClickListener(this);
        tv_dixing.setOnClickListener(this);
        tv_home.setOnClickListener(this);
        tv_tishiquan.setOnClickListener(this);
        tv_shezhi.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.tv_tuceng:
                LayerPop layerPop = new LayerPop(getApplicationContext());
                layerPop.showPopWindow(tv_tuceng);
                break;
            case R.id.tv_daohang:
                final HXPop hxPop = new HXPop(getApplicationContext());
                hxPop.showPopWindow(tv_daohang);


//                final NavHXPop navHXPop = new NavHXPop(getApplicationContext());
//                navHXPop.showPopWindow(tv_daohang);
//                navHXPop.setShowPopNavHLCallBack(new NavHXPop.ShowPopNavHLCallBack() {
//                    @Override
//                    public void showNavHLPop(Point point) {
//                        NavHLPop navHLPop = new NavHLPop(MyApplication.getAppContext());
//                        navHLPop.showPopWindow(mapView);
//                        navHXPop.dismiss();
//                    }
//                });
                break;
            case R.id.tv_msg:
                this.speed = VerticalScaleScrollViewLeft.scale_v;
                this.height_ground = VerticalScaleScrollViewRight.scale_h;
//                this.height_ground = Double.parseDouble(SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getGaodu());
//                MSGPop msgPop = new MSGPop(getApplicationContext(),longitude,bearing,height_ground,speed);
                MSGPop msgPop = new MSGPop(getApplicationContext(),longitude,latitude,bearing,height_ground,speed);
                msgPop.showPopWindow(tv_msg);
                break;
            case R.id.tv_dixing:
//                ShanDiPop shanDiPop = new ShanDiPop(getApplicationContext());
//                shanDiPop.showPopWindow(mapView);
                DiXingPop1 diXingPop = new DiXingPop1(getApplicationContext());
                diXingPop.showPopWindow(mapView);
                break;
            case R.id.tv_home:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }).start();
                aMap.removecache();
                aMap.clear();
                if(BaseActivity.circle_nei_ben!=null){
                    BaseActivity.circle_nei_ben.remove();
                }
                if(BaseActivity.circle_wai_ben!=null){
                    BaseActivity.circle_wai_ben.remove();
                }
                circle_green.setVisibility(View.VISIBLE);
                circle_red.setVisibility(View.GONE);
                circle_yellow.setVisibility(View.GONE);
                tv_msg_zy.setVisibility(View.GONE);
                tv_msg_jg.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        initLocation();
//                        startGps();
                        setaMap();
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9));// 设置当前地图显示为当前位置以及比例尺（反比）
//                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.YANLIANG, 9));//14是500m
                    }
                },100);
                break;
            case R.id.tv_tishiquan:
                HintBPop hintBPop = new HintBPop(getApplicationContext());
                hintBPop.showPopWindow(tv_tishiquan);
//                HintPop hintPop = new HintPop(getApplicationContext());
//                hintPop.showPopWindow(tv_tishiquan);
                break;
            case R.id.tv_shezhi:
//                SetPop setPop = new SetPop(MyApplication.getAppContext());
//                setPop.showPopWindow(tv_shezhi);
                SetCorrPop setCorrPop = new SetCorrPop(MyApplication.getAppContext());
                setCorrPop.showPopWindow(tv_shezhi);
                break;
        }
    }

    /**
     * 按下监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {//按下返回键

            if (isBack) {
                Toast.makeText(this,"再点一次退出",Toast.LENGTH_SHORT).show();
                isBack = false;
                handler.sendEmptyMessageDelayed(1, 2000);
            } else {
                //退出app
                System.exit(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {
            case 1:
                isBack = true;
                break;
        }

        return false;
    }



}
