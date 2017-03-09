package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.hanke.navi.R;
import com.hanke.navi.framwork.share.SharepreferenceHelper;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.pop.adapter.HangLuListViewAdapter;
import com.hanke.navi.skyair.pop.bean.HangLuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Che on 2016/11/30.
 */
public class TianJiaPop extends PopupWindow implements Handler.Callback,TextWatcher,View.OnClickListener {

    private Context context;
    private Handler handler;
    private int popupHeight, popupWidth;
    private EditText mingcheng_HL,weidu_HL,jingdu_HL,gaodu_HL;
    private Button baocun;
    public List<HangLuBean> list_hlb;


    public TianJiaPop(Context context) {
        this(context, null);
    }

    public TianJiaPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TianJiaPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
//        initData();
    }

    public void initView() {
        View view = View.inflate(context, R.layout.editor_page, null);

        mingcheng_HL = (EditText) view.findViewById(R.id.mingcheng_HL);
        weidu_HL = (EditText) view.findViewById(R.id.weidu_HL);
        jingdu_HL = (EditText) view.findViewById(R.id.jingdu_HL);
        gaodu_HL = (EditText) view.findViewById(R.id.gaodu_HL);
        baocun = (Button) view.findViewById(R.id.baocun);
        baocun.setOnClickListener(this);

        mingcheng_HL.addTextChangedListener(this);
        weidu_HL.addTextChangedListener(this);
        jingdu_HL.addTextChangedListener(this);
        gaodu_HL.addTextChangedListener(this);

        this.setContentView(view);
        this.setWidth(4* MyApplication.getWidth()/8);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(DisplayUtil.getDensity_Height(context));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
    }

    public void initData(){
        String mingchengHLu =mingcheng_HL.getText().toString();
        String weiduHLu =weidu_HL.getText().toString();
        String jingduHLu =jingdu_HL.getText().toString();
        String gaoduHLu =gaodu_HL.getText().toString();
        Log.e("msg","mingchengHLu = "+mingchengHLu);
        Log.e("msg","weiduHLu = "+weiduHLu);
        Log.e("msg","jingduHLu = "+jingduHLu);
        Log.e("msg","gaoduHLu = "+gaoduHLu);
        //存数据
        SharepreferenceHelper.getInstence(context).setHLumingcheng(mingchengHLu);
        SharepreferenceHelper.getInstence(context).setHLuweidu(weiduHLu);
        SharepreferenceHelper.getInstence(context).setHLujingdu(jingduHLu);
        SharepreferenceHelper.getInstence(context).setHLugaodu(gaoduHLu);
        //取数据
        String mingchengHL = SharepreferenceHelper.getInstence(context).getHLumingcheng();
        String weiduHL = SharepreferenceHelper.getInstence(context).getHLuweidu();
        String jingduHL = SharepreferenceHelper.getInstence(context).getHLujingdu();
        String gaoduHL = SharepreferenceHelper.getInstence(context).getHLugaodu();
        Log.e("msg","mingchengHLu = "+mingchengHL);
        Log.e("msg","weiduHLu = "+weiduHL);
        Log.e("msg","jingduHLu = "+jingduHL);
        Log.e("msg","gaoduHLu = "+gaoduHL);
    }

    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
            this.showAtLocation(view, Gravity.CENTER,0, 0);//可以显示在指定view的指定位置
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
//        initData();
//        HangLuListViewAdapter hangLuListViewAdapter =new HangLuListViewAdapter(context);
//        String mingchengHL = SharepreferenceHelper.getInstence(context).getHLumingcheng();
//        HangLuBean hangLuBean = new HangLuBean();
//        hangLuBean.hanglu=mingchengHL;
//        hangLuListViewAdapter.items_hanglu.add(mingchengHL);
//        hangLuListViewAdapter.notifyDataSetChanged();
    }
    public void addList(){
        list_hlb = new ArrayList<HangLuBean>();
        list_hlb.add(new HangLuBean(SharepreferenceHelper.getInstence(context).getHLumingcheng()));
        HangLuListViewAdapter hangLuListViewAdapter = new HangLuListViewAdapter(context);
        hangLuListViewAdapter.setItems_hanglu(list_hlb);
        hangLuListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.baocun:
                initData();
                NavHLPop.list.add(new HangLuBean(SharepreferenceHelper.getInstence(context).getHLumingcheng()));
                NavHLPop.hangLuListViewAdapter.notifyDataSetChanged();
                dismissPopWindow();
                break;
        }
    }
}
