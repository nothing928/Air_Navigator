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

/**
 * Created by Asus on 2017/2/15.
 */
public class TianJiaZhiPop extends PopupWindow implements Handler.Callback,TextWatcher,View.OnClickListener {

    private Context context;
    private Handler handler;
    private int popupHeight, popupWidth;
    private EditText mingcheng_HL,weidu_HL,jingdu_HL,gaodu_HL;
    private Button baocun;


    public TianJiaZhiPop(Context context) {
        this(context, null);
    }

    public TianJiaZhiPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TianJiaZhiPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        initData();
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
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
    }

    public void initData(){
        mingcheng_HL.setText(SharepreferenceHelper.getInstence(context).getHLitem());
        weidu_HL.setText(SharepreferenceHelper.getInstence(context).getHLuweidu());
        jingdu_HL.setText(SharepreferenceHelper.getInstence(context).getHLujingdu());
        gaodu_HL.setText(SharepreferenceHelper.getInstence(context).getHLugaodu());
//        weidu_HL.setText(""+34.498705);
//        jingdu_HL.setText(""+109.505039);
//        gaodu_HL.setText("473");

    }

    public void changeData(){
        String change_mingcheng=mingcheng_HL.getText().toString();
        String change_weidu=weidu_HL.getText().toString();
        String change_jingdu=jingdu_HL.getText().toString();
        String change_gaodu=gaodu_HL.getText().toString();

        //存数据
        SharepreferenceHelper.getInstence(context).setChangeHLumingcheng(change_mingcheng);
        SharepreferenceHelper.getInstence(context).setHLuweidu(change_weidu);
        SharepreferenceHelper.getInstence(context).setHLujingdu(change_jingdu);
        SharepreferenceHelper.getInstence(context).setHLugaodu(change_gaodu);
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

//        HangLuListViewAdapter hangLuListViewAdapter =new HangLuListViewAdapter(context);
        String mingchengHL = SharepreferenceHelper.getInstence(context).getHLumingcheng();
//        HangLuBean hangLuBean = new HangLuBean();
//        hangLuBean.hanglu=mingchengHL;
//        hangLuListViewAdapter.items_hanglu.add(mingchengHL);
//        hangLuListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.baocun:
                changeData();
                dismissPopWindow();
                break;
        }
    }
}
