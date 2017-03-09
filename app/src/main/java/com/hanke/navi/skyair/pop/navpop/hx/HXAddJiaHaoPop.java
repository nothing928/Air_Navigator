package com.hanke.navi.skyair.pop.navpop.hx;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.hanke.navi.R;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.callback.AddHXItemCallBack;
import com.hanke.navi.skyair.db.HXModel;
import com.hanke.navi.skyair.pop.bean.HangXianBean;

import java.util.ArrayList;

public class HXAddJiaHaoPop extends PopupWindow implements View.OnClickListener{

    private Context context;
    private EditText mc__hx_jiahao;
    private Button bc__hx_jiahao;
    private ArrayList<HangXianBean> data_hx;
    private HXAdapter hxAdapter;
    private AddHXItemCallBack addHXItemCallBack;
    private int position;

    public void setAddHXItemCallBack(AddHXItemCallBack addHXItemCallBack) {
        this.addHXItemCallBack = addHXItemCallBack;
    }

    public HXAddJiaHaoPop(Context context,int position) {
        super(context);
        this.position = position;
        this.context = context;
        data_hx = new ArrayList<HangXianBean>();
        hxAdapter = new HXAdapter(context);
        initView();
    }

    public HXAddJiaHaoPop(Context context) {
        this(context,null);
    }

    public HXAddJiaHaoPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HXAddJiaHaoPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void initView() {
        View view = View.inflate(context, R.layout.hxadd_jiahao, null);
        mc__hx_jiahao = (EditText) view.findViewById(R.id.mc__hx_jiahao);
        bc__hx_jiahao = (Button) view.findViewById(R.id.bc__hx_jiahao);
        bc__hx_jiahao.setOnClickListener(this);

        this.setContentView(view);
        this.setWidth(4* MyApplication.getWidth()/8);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
    }

    public void showPopWindow(View view) {
        if (!isShowing()) {
            this.showAtLocation(view, Gravity.CENTER,0, 0);//可以显示在指定view的指定位置
        }
    }

    public void dismissPopWindow() {
        if (this.context != null && this.isShowing()) {
            this.dismiss();
        }
    }

    public void initData(){
        String str = mc__hx_jiahao.getText().toString();
        HangXianBean hangXianBean = new HangXianBean();
        if (str.length()==0)
            Toast.makeText(context,"名称不能为空",Toast.LENGTH_SHORT).show();
        else{
            hangXianBean.setHangxian(str);
            addHXItemCallBack.addHXItem(position,hangXianBean);
            HXModel hxModel = new HXModel();
            hxModel.insertHX(hangXianBean);
            dismissPopWindow();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bc__hx_jiahao:
                initData();
                break;
        }
    }


}
