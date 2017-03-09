package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.framwork.share.SharepreferenceHelper;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.pop.adapter.HangXianListViewAdapter;
import com.hanke.navi.skyair.pop.bean.HangXianBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/11/23.
 */
public class NavHXPop extends PopupWindow implements View.OnClickListener, Handler.Callback {

    private Context context;
    private Handler handler;
    public static ListView lv_hangxian;
    public static HangXianListViewAdapter hangXianListViewAdapter;
    public static List<HangXianBean> list;
    private TextView hangxian_jiahao,tv_air_line_item;
    private ShowPopNavHLCallBack showPopNavHLCallBack;

    public NavHXPop(Context context) {
        this(context, null);
    }

    public NavHXPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavHXPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        init();
    }

    public void setShowPopNavHLCallBack(ShowPopNavHLCallBack showPopNavHLCallBack) {
        this.showPopNavHLCallBack = showPopNavHLCallBack;
    }


    int orgX, orgY;
    int offsetX, offsetY;
    int mScreenX=0,mScreenY=0;

    public void initView() {
        View view = View.inflate(context, R.layout.bt_hangxian, null);
        lv_hangxian = (ListView) view.findViewById(R.id.lv_hangxian);
        tv_air_line_item= (TextView) view.findViewById(R.id.tv_air_line_item);
        hangxian_jiahao = (TextView) view.findViewById(R.id.hangxian_jiahao);
        hangxian_jiahao.setOnClickListener(this);

        list = new ArrayList<HangXianBean>();
        String[] arrays = new String[10];// 初始化数据源
        for (int i = 0; i < arrays.length; i++) {
            HangXianBean hangXianBean = new HangXianBean();
            hangXianBean.hangxian = "航线"+(i+1);
            list.add(hangXianBean);
        }
        hangXianListViewAdapter=new HangXianListViewAdapter(context,list);
        lv_hangxian.setAdapter(hangXianListViewAdapter);

        view.setOnTouchListener(new View.OnTouchListener() {
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
                        offsetX = (int) event.getRawX()-orgX+mScreenX;
                        offsetY = orgY-(int) event.getRawY()+mScreenY;
                        update(offsetX,-offsetY,-1,-1,true);
                        break;
                }
                return true;
            }
        });

        this.setContentView(view);
        this.setWidth(5 * MyApplication.getWidth() / 8);
        this.setHeight(12* MyApplication.getWidth()/16);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());

    }
//    private void addItem() {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("title", "标题");
//        map.put("text", "要显示的内容");
//        list.add(map);
//        hangXianListViewAdapter.notifyDataSetChanged();
//    }

    public static void deleteItem() {
        if( list.size() > 0 ) {
            list.remove(index);
            hangXianListViewAdapter.notifyDataSetChanged();
        }
    }

    private float OldListY = -1;
    public static int index;
    public void init() {
        lv_hangxian.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                String mu = daohang_lv.getItemAtPosition(position)+"";
//                Toast.makeText(context,"wwwwww"+mu,Toast.LENGTH_SHORT).show();
//                view.setBackgroundColor(Color.parseColor("#ff6E6782"));

                index=(int)parent.getItemIdAtPosition(position);
                HangXianBean zhi = (HangXianBean) hangXianListViewAdapter.getItem(position);
                String item =zhi.getHangxian();
                Log.e("item","item = "+item);
                SharepreferenceHelper.getInstence(context).setHXitem(item);
                AddOrDelPop addOrDelPop = new AddOrDelPop(context);
                int[] location = new int[2];
                view.getLocationOnScreen(location);

                int Pos[] = { -1, -1 };  //保存当前坐标的数组
                view.getLocationOnScreen(Pos);  //获取选中的 Item 在屏幕中的位置，以左上角为原点 (0, 0)
                OldListY = (float) Pos[1];  //只取 Y 坐标就行

                int heng = location[0]+view.getWidth()-addOrDelPop.popupWidth;
                int shu = (int) OldListY-addOrDelPop.popupHeight/4;
                addOrDelPop.showAtLocation(BaseActivity.mapView,Gravity.NO_GRAVITY,heng,shu);
                addOrDelPop.setShowPopNavHLCallBack(new ShowPopNavHLCallBack() {
                    @Override
                    public void showNavHLPop(Point point) {
                        NavHLPop navHLPop = new NavHLPop(MyApplication.getAppContext());
                        navHLPop.showPopWindow(BaseActivity.mapView);
                        dismissPopWindow();
                    }
                });

                return true;
            }
        });

        lv_hangxian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String,String> map=(HashMap<String,String>)lv_hangxian.getItemAtPosition(position);

                index=(int)parent.getItemIdAtPosition(position);
                TextView tv = (TextView) view.findViewById(R.id.tv_air_line_item);
                Log.e("shuliang","parent.getCount() = "+parent.getCount());
                //设背景第一种方法
                hangXianListViewAdapter.setSelectHangXianItem(position);
                hangXianListViewAdapter.notifyDataSetInvalidated();
                //设背景第二种方法
//                for(int i=0;i<parent.getCount();i++){
//                    View v=parent.getChildAt(i);
//                    if (position == i) {
//                        v.setBackgroundColor(Color.RED);
//                    } else {
//                        v.setBackgroundColor(Color.TRANSPARENT);
//                    }
//                }

            }
        });

        lv_hangxian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String,String> map=(HashMap<String,String>)daohang_lv.getItemAtPosition(position);
//                info=map.get("name");
//                daohang_lv.getChildAt(position-daohang_lv.getFirstVisiblePosition()).setBackgroundColor(Color.RED);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hangxian_jiahao:
                int x = (int) v.getX();
                int y = (int) v.getY();
                Point point = new Point(x,y);
                if(showPopNavHLCallBack!=null){
                    showPopNavHLCallBack.showNavHLPop(point);
                }
                break;
        }
    }

    public interface ShowPopNavHLCallBack {
        void showNavHLPop(Point point);
    }

}
