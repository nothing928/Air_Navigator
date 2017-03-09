package com.hanke.navi.skyair.pop.navpop.hl;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
import com.hanke.navi.skyair.callback.AddHLItemCallback;
import com.hanke.navi.skyair.callback.DeleteCallback;
import com.hanke.navi.skyair.daohang.DaoXianLu;
import com.hanke.navi.skyair.db.HLModel;
import com.hanke.navi.skyair.pop.bean.HangLuBean;
import com.hanke.navi.skyair.pop.navpop.hx.HXAdapter;

import java.util.ArrayList;
import java.util.List;

public class HLPop extends PopupWindow implements View.OnClickListener,View.OnTouchListener{

    private Context context;
    private ListView lv_hanglu;
    public HLAdapter hlAdapter;
    public List<HangLuBean> data_hl;
    private TextView hangxian_title,hanglu_jiahao,hangxian_pianzhi,hangxian_zhixing;
    private int position;
    private HLModel hlModel;

    public HLPop(Context context) {
        this(context,null);
    }

    public HLPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HLPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        beforeInitView();
        initView();
        initData();
    }

    public void beforeInitView() {
        if (data_hl == null)
            data_hl = new ArrayList<>();
        hlModel = new HLModel();
        data_hl = hlModel.getAll();
    }

    public void initView(){
        View view = View.inflate(context, R.layout.bt_hanglu,null);
        lv_hanglu = (ListView) view.findViewById(R.id.lv_hanglu);
        hangxian_title = (TextView) view.findViewById(R.id.hangxian_title);
        hangxian_title.setText(SharepreferenceHelper.getInstence(context).getHXitem());
        hanglu_jiahao = (TextView) view.findViewById(R.id.hanglu_jiahao);
        hanglu_jiahao.setOnClickListener(this);
        hangxian_pianzhi = (TextView) view.findViewById(R.id.hangxian_pianzhi);
        hangxian_pianzhi.setOnClickListener(this);
        hangxian_zhixing = (TextView) view.findViewById(R.id.hangxian_zhixing);
        hangxian_zhixing.setOnClickListener(this);
        hlAdapter = new HLAdapter(context);
        hlAdapter.setHLData(data_hl);
        lv_hanglu.setAdapter(hlAdapter);


        this.setContentView(view);
        this.setWidth(5* MyApplication.getWidth()/8);
        this.setHeight(12* MyApplication.getWidth()/16);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        view.setOnTouchListener(this);
    }

    private float OldListY = -1;
    public void initData(){
        lv_hanglu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                SharePrefreceHelper.getInstence(context).setPos((int)lv_hanglu.getItemIdAtPosition(position));
                HangLuBean bean = data_hl.get(position);
                HLZengShanPop hlZengShanPop = new HLZengShanPop(context,position);
                hlZengShanPop.setHangLuBean(bean);
                hlZengShanPop.setDeleteCallback(new DeleteCallback() {
                    @Override
                    public void deletePosition(int position) {
                        data_hl.remove(position);
                        hlModel.deleteHL(position);
                        hlAdapter.setHLData(data_hl);
                        hlAdapter.notifyDataSetChanged();
                    }
                });
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                int Pos[] = { -1, -1 };  //保存当前坐标的数组
                view.getLocationOnScreen(Pos);  //获取选中的 Item 在屏幕中的位置，以左上角为原点 (0, 0)
                OldListY = (float) Pos[1];  //我们只取 Y 坐标就行了
                int heng = location[0]+view.getWidth()- HLZengShanPop.popupWidth;
                int shu = (int) OldListY- HLZengShanPop.popupHeight/4;
                hlZengShanPop.showAtLocation(BaseActivity.mapView, Gravity.NO_GRAVITY,heng,shu);
                return true;
            }
        });
        lv_hanglu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HLPop.this.position = position;
                HLAddJiaHaoPop hlAddJiaHaoPop = new HLAddJiaHaoPop(context,position);

                hlAdapter.setSelectHLItem(position);
                hlAdapter.notifyDataSetChanged();
            }
        });

    }

    public void showPopWindow(View view) {
        if (!isShowing()) {
            this.showAtLocation(view, Gravity.CENTER, 0, 0);//可以显示在指定view的指定位置
        }
    }

    public void dismissPopWindow() {
        if (this.context != null && this.isShowing()) {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hanglu_jiahao:
                HLAddJiaHaoPop hlAddJiaHaoPop = new HLAddJiaHaoPop(context,position);
                hlAddJiaHaoPop.showPopWindow(BaseActivity.mapView);
                hlAddJiaHaoPop.setAddHLItemCallback(new AddHLItemCallback() {
                    @Override
                    public void addHLItem(int position,HangLuBean hangLuBean) {
                        int pos = lv_hanglu.getLastVisiblePosition();
                        if (data_hl.size() == 0){
                            data_hl.add(position,hangLuBean);
                        }else
                            data_hl.add(pos+1,hangLuBean);
                        hlModel.insertHL(hangLuBean);
                        hlAdapter.setHLData(data_hl);
                        hlAdapter.notifyDataSetChanged();
                    }
                });
//                hlAddJiaHaoPop.setAddHLItemCallback(new HLAddJiaHaoPop.AddHLItemCallback() {
//                    @Override
//                    public void addHLItem(HangLuBean bean) {
//                        data_hl.add(bean);
//                        hlAdapter.setHLData(data_hl);
//                        hlAdapter.notifyDataSetChanged();
//                    }
//                });
                break;
            case R.id.hangxian_zhixing:
                DaoXianLu daoXianLu = new DaoXianLu(context);
                daoXianLu.initRoadData();
                dismissPopWindow();
                daoXianLu.moveLooper();
                break;
        }
    }

    //手指移动pop
    int orgX, orgY;
    int offsetX, offsetY;
    int mScreenX=0,mScreenY=0;
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
                this.update(offsetX,-offsetY,-1,-1,true);
                break;
        }
        return true;
    }

}
