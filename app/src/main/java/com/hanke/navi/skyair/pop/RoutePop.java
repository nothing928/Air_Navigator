package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.framwork.base.BaseActivity;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.animatorPath.dao.DaoXianDi;
import com.hanke.navi.skyair.ui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Che on 2016/11/23.
 */
public class RoutePop extends PopupWindow implements View.OnClickListener,Handler.Callback,View.OnTouchListener{

    private Context context;
    private Handler handler;
    private ListView lv_hanglu;
    private TextView hangxian_jiahao,hangxian_pianzhi,hangxian_zhixing;
    public static TextView hangxian_title;
    private ArrayAdapter<String> arrayAdapter;

    public RoutePop(Context context) {
        this(context,null);
    }

    public RoutePop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoutePop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        init();
    }

//    public void initView(){
//        View view = View.inflate(context, R.layout.bt_hanglu,null);
//        hangxian_lv = (ListView) view.findViewById(R.id.hangxian_lv);
//        hangxian_jiahao = (TextView) view.findViewById(R.id.hangxian_jiahao);
//        hangxian_jiahao.setOnClickListener(this);
//        hangxian_pianzhi = (TextView) view.findViewById(R.id.hangxian_pianzhi);
//        hangxian_pianzhi.setOnClickListener(this);
//
//        String[] str = {"一航路点","二航路点","三航路点","四航路点","五航路点","六航路点","七航路点","八航路点"};
//        arrayAdapter = new ArrayAdapter<String>(context,R.layout.air_line_item,str);
//        hangxian_lv.setAdapter(arrayAdapter);
//        this.setContentView(view);
//        this.setWidth(5* MyApplication.getWidth()/8);
//        this.setHeight(12* MyApplication.getWidth()/16);
////        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setFocusable(true);
//        this.setOutsideTouchable(true);
////        this.update();
//        this.setBackgroundDrawable(new BitmapDrawable());
//
//    }

    public static ArrayList<HashMap<String, Object>> list_hangxian;
    public static SimpleAdapter adapter_hangxian;
    public static int index_hangxian;
    private   ArrayList<HashMap<String, Object>> getList(){
        list_hangxian=new ArrayList<HashMap<String,Object>>();
        for(int i=1;i<=5;i++){
            HashMap<String, Object> map=new HashMap<String, Object>();
            map.put("xian", "航路点"+i);
            list_hangxian.add(map);
        }
        return list_hangxian;
    }

    public void initView(){
        View view = View.inflate(context, R.layout.bt_hanglu,null);
        lv_hanglu = (ListView) view.findViewById(R.id.lv_hanglu);
        hangxian_jiahao = (TextView) view.findViewById(R.id.hangxian_jiahao);
        hangxian_jiahao.setOnClickListener(this);
        hangxian_pianzhi = (TextView) view.findViewById(R.id.hangxian_pianzhi);
        hangxian_pianzhi.setOnClickListener(this);
        hangxian_title = (TextView) view.findViewById(R.id.hangxian_title);
        view.setOnTouchListener(this);
        hangxian_zhixing = (TextView) view.findViewById(R.id.hangxian_zhixing);
        hangxian_zhixing.setOnClickListener(this);

//        String[] str = {"一航路点","二航路点","三航路点","四航路点","五航路点","六航路点","七航路点","八航路点"};
//        arrayAdapter = new ArrayAdapter<String>(context,R.layout.air_line_item,str);
//        hangxian_lv.setAdapter(arrayAdapter);

        String[] from={"xian"};
        int[] to={R.id.tv_air_way_item};
        adapter_hangxian=new SimpleAdapter(context, getList(), R.layout.air_way_item, from, to);
        lv_hanglu.setAdapter(adapter_hangxian);

        this.setContentView(view);
        this.setWidth(5* MyApplication.getWidth()/8);
        this.setHeight(12* MyApplication.getWidth()/16);
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        this.update();
        this.setBackgroundDrawable(new BitmapDrawable());

    }

    private void addItem()
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("image", R.drawable.icon);
        map.put("title", "标题");
        map.put("text", "要显示的内容");
        list_hangxian.add(map);
        adapter_hangxian.notifyDataSetChanged();
    }

    public static void deleteItem() {
        if( list_hangxian.size() > 0 ) {
            list_hangxian.remove(index_hangxian);
            adapter_hangxian.notifyDataSetChanged();
        }
    }
    private float OldListY = -1;
    public void init(){
        lv_hanglu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                String mu = hangxian_lv.getItemAtPosition(position).toString();
//                Toast.makeText(context,"wwwwww"+mu,Toast.LENGTH_SHORT).show();
                index_hangxian=(int)parent.getItemIdAtPosition(position);

                ZengOrRemovePop zengOrRemovePop = new ZengOrRemovePop(context);
                int[] location = new int[2];
                view.getLocationOnScreen(location);

                int Pos[] = { -1, -1 };  //保存当前坐标的数组
                view.getLocationOnScreen(Pos);  //获取选中的 Item 在屏幕中的位置，以左上角为原点 (0, 0)
                OldListY = (float) Pos[1];  //我们只取 Y 坐标就行了

                int heng = location[0]+view.getWidth()-ZengOrRemovePop.popupWidth;
                int shu = (int) OldListY-ZengOrRemovePop.popupHeight/4;
                zengOrRemovePop.showAtLocation(BaseActivity.mapView,Gravity.NO_GRAVITY,heng,shu);
                return true;
            }
        });

        lv_hanglu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                AddOrDelPop addOrDelPop = new AddOrDelPop(context);
//                addOrDelPop.showPopWindow(daohang_lv);
//                Toast.makeText(context,"OnItemClickListener",Toast.LENGTH_SHORT).show();
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
                handler.sendEmptyMessageDelayed(0,100);
            }
        },100);
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
        switch (v.getId()){
            case R.id.hangxian_jiahao:
                int x= (int) v.getX();
                int y = (int) v.getY();
                Point point = new Point(x,y);
                TianJiaPop tianJiaPop = new TianJiaPop(MyApplication.getAppContext());
                tianJiaPop.showPopWindow(BaseActivity.mapView);
            break;
            case R.id.hangxian_pianzhi:
                PianZhiPop pianZhiPop = new PianZhiPop(MyApplication.getAppContext());
                pianZhiPop.showPopWindow(BaseActivity.mapView);
            break;
            case R.id.hangxian_zhixing:
                DaoXianDi daoXianDi = new DaoXianDi(context);
                daoXianDi.DaoXian();
                DaoXianDi.setPath();
                MainActivity.setImgWei();
                DaoXianDi.startAnimatorPath(MainActivity.mm, "fab", DaoXianDi.path);
            break;
        }
    }
    int orgX, orgY;
    int offsetX, offsetY;
    int mScreenX=0,mScreenY=0;
    //手指移动pop
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
                this.update(offsetX,-offsetY,-1,-1,true);
                break;
        }
        return true;
    }


}
