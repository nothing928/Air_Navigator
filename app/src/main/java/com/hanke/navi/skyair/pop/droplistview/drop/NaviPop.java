package com.hanke.navi.skyair.pop.droplistview.drop;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.pop.AddOrDelPop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/12/16.
 */
public class NaviPop extends PopupWindow implements View.OnClickListener, Handler.Callback {

    private Context context;
    private Handler handler;
    private TextView daohang_jiahao;
    private ShowPopRouteCallBack showPopRouteCallBack;

    public DragOwnView drag_lv;
    public static DragAdapter dragAdapter=null;

    public static List<String> list=new ArrayList<String>();
    public static int index;
    public static String info;

    public NaviPop(Context context) {
        this(context, null);
    }

    public NaviPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NaviPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        init();
    }

    public void setShowPopRouteCallBack(ShowPopRouteCallBack showPopRouteCallBack) {
        this.showPopRouteCallBack = showPopRouteCallBack;
    }

    public List<String> getList(){
        for (int i=1;i<=10;i++){
            list.add("航线"+i);
        }
        Log.e("list","list = "+list);
        return list;
    }

    public void initView() {

//        View view = View.inflate(context, R.layout.bt_hangxian, null);
        View view = LayoutInflater.from(context).inflate(R.layout.bt_hangxian,null);
//        drag_lv = (DragOwnView) view.findViewById(R.id.drag_lv);


//        daohang_lv = (ListView) view.findViewById(R.id.daohang_lv);
        daohang_jiahao = (TextView) view.findViewById(R.id.hangxian_jiahao);
        daohang_jiahao.setOnClickListener(this);
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

        dragAdapter = new DragAdapter(context);
        drag_lv.setAdapter(dragAdapter);
        this.setContentView(view);
        this.setWidth(5 * MyApplication.getWidth() / 8);
        this.setHeight(12* MyApplication.getWidth()/16);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());

    }

//    private void addItem()
//    {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("title", "标题");
//        map.put("text", "要显示的内容");
//        list.add(map);
//        adapter.notifyDataSetChanged();
//    }

    public static void deleteItem()
    {
        if( list.size() > 0 ) {
            list.remove(index);
            dragAdapter.notifyDataSetChanged();
//            adapter.notifyDataSetChanged();
        }
    }

    public void init() {
        drag_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AddOrDelPop addOrDelPop = new AddOrDelPop(context);
                index=(int)parent.getItemIdAtPosition(position);
                int x = (int) parent.getX()- addOrDelPop.popupWidth/4;
                int y = (int) parent.getY()-4*addOrDelPop.popupHeight/2;
                Point point = new Point(x, y);
//                if (showPopAddOrDelCallBack != null) {
//                    showPopAddOrDelCallBack.showAddOrDelPop(point);
//                }
//                AddOrDelPop addOrDelPop = new AddOrDelPop(context);
                addOrDelPop.showPopWindow(BaseActivity.mapView);
                return true;
            }
        });

//        drag_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String,String> map=(HashMap<String,String>)daohang_lv.getItemAtPosition(position);
//                info=map.get("name");
////                info=adapter.getItem(position).toString();
//                Log.e("xiaoxi","========="+info);
//                RoutePop routePop = new RoutePop(context);
//                routePop.showPopWindow(BaseActivity.mapView);
//                dismissPopWindow();
//                RoutePop.hangxian_title.setText(NaviPop.info);
//            }
//        });
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
                if(showPopRouteCallBack!=null){
                    showPopRouteCallBack.showRoutePop(point);
                }
                break;
        }
    }

    public int orgX, orgY;
    public int offsetX, offsetY;
    public int mScreenX=0,mScreenY=0;

    public interface ShowPopAddOrDelCallBack {
        void showAddOrDelPop(Point point);
    }

    public interface ShowPopRouteCallBack {
        void showRoutePop(Point point);
    }
}
