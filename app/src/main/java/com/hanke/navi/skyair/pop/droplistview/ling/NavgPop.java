package com.hanke.navi.skyair.pop.droplistview.ling;

import android.content.Context;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.pop.AddOrDelPop;
import com.hanke.navi.skyair.pop.NavHLPop;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Che on 2016/12/16.
 */
public class NavgPop extends PopupWindow implements View.OnClickListener, Handler.Callback {

    private Context context;
    private Handler handler;
    public ListView daohang_lv;
    private TextView daohang_jiahao,tv_air_line_item;
    private ShowPopAddOrDelCallBack showPopAddOrDelCallBack;
    private ShowPopRouteCallBack showPopRouteCallBack;
    public static List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
    public static SimpleAdapter adapter;
    public static int index;
    public static String info;

    public NavgPop(Context context) {
        this(context, null);
    }

    public NavgPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavgPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        init();
    }

    public void setShowPopAddOrDelCallBack(ShowPopAddOrDelCallBack showPopAddOrDelCallBack) {
        this.showPopAddOrDelCallBack = showPopAddOrDelCallBack;
    }

    public void setShowPopRouteCallBack(ShowPopRouteCallBack showPopRouteCallBack) {
        this.showPopRouteCallBack = showPopRouteCallBack;
    }


    private  List<Map<String, Object>> getList(){
        for(int i=1;i<=8;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("name", "航线"+i);
            list.add(map);
        }
        return list;
    }

    int orgX, orgY;
    int offsetX, offsetY;
    int mScreenX=0,mScreenY=0;

    public void initView() {
        View view = View.inflate(context, R.layout.bt_hangxian, null);
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
//                offsetX = orgX+(int) event.getRawX();
//                offsetY = orgY+(int) event.getRawY();
                        offsetX = (int) event.getRawX()-orgX+mScreenX;
                        offsetY = orgY-(int) event.getRawY()+mScreenY;
                        update(offsetX,-offsetY,-1,-1,true);
                        break;
                }
                return true;
            }
        });

        String[] from={"name"};
        int[] to={R.id.tv_air_line_item};
        adapter=new SimpleAdapter(context, getList(), R.layout.air_line_item, from, to);
        daohang_lv.setAdapter(adapter);

        this.setContentView(view);
        this.setWidth(5 * MyApplication.getWidth() / 8);
        this.setHeight(12* MyApplication.getWidth()/16);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());

        ListviewHelper helper = new ListviewHelper(daohang_lv) {
            @Override
            public void resetListview() {
// TODO Auto-generated method stub
// listview重新显示
                adapter = new SimpleAdapter(context, list,R.layout.air_line_item,new String[] { "sortName" },new int[] { R.id.tv_air_line_item });
                lv.setAdapter(adapter);
            }


            @Override
            public void changeItemPosition(int p1, int p2) {
// TODO Auto-generated method stub
// 根据自己listview的类型完成listview的数据源中id为p1和p2的两个item的替换
                Map<String, Object> tempMap = list.get(p2);
                list.set(p2, list.get(p1));
                list.set(p1, tempMap);
                adapter = new SimpleAdapter(context, list,
                        R.layout.air_line_item,
                        new String[] { "sortName" },
                        new int[] { R.id.tv_air_line_item });
                lv.setAdapter(adapter);
            }
        };
        helper.enableChangeItems(true);

    }

    private void addItem()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("image", R.drawable.icon);
        map.put("title", "标题");
        map.put("text", "要显示的内容");
        list.add(map);
        adapter.notifyDataSetChanged();
    }

    public static void deleteItem()
    {
        if( list.size() > 0 ) {
            list.remove(index);
            adapter.notifyDataSetChanged();
//            AddOrDelPop addOrDelPop = new AddOrDelPop(MyApplication.getAppContext());
//            addOrDelPop.dismissPopWindow();

        }
//        else{
//            Toast.makeText(MyApplication.getAppContext(),"当前位置没有内容可删除",Toast.LENGTH_SHORT).show();
//        }
    }

    public void init() {
        daohang_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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

                addOrDelPop.showPopWindow(BaseActivity.mapView);
                return true;
            }
        });

        daohang_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map=(HashMap<String,String>)daohang_lv.getItemAtPosition(position);
                info=map.get("name");
//                info=adapter.getItem(position).toString();
                Log.e("xiaoxi","========="+info);
                NavHLPop navHLPop = new NavHLPop(context);
                navHLPop.showPopWindow(BaseActivity.mapView);
                dismissPopWindow();
                NavHLPop.hangxian_title.setText(NavgPop.info);
//                AddOrDelPop addOrDelPop = new AddOrDelPop(context);
//                addOrDelPop.showPopWindow(daohang_lv);
//                Toast.makeText(context, "OnItemClickListener", Toast.LENGTH_SHORT).show();
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
                if(showPopRouteCallBack!=null){
                    showPopRouteCallBack.showRoutePop(point);
                }
                break;
        }
    }

    public interface ShowPopAddOrDelCallBack {
        void showAddOrDelPop(Point point);
    }

    public interface ShowPopRouteCallBack {
        void showRoutePop(Point point);
    }

}
