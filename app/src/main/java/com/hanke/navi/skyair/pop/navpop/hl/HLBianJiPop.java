package com.hanke.navi.skyair.pop.navpop.hl;

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
import com.hanke.navi.framwork.share.SharepreferenceHelper;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.pop.bean.HangLuBean;

import java.util.ArrayList;

public class HLBianJiPop extends PopupWindow implements View.OnClickListener{

    private Context context;
    private EditText mingcheng,weidu,jingdu,gaodu;
    private Button baocun;
    private ArrayList<HangLuBean> data_hl;
    private HLAdapter hlAdapter;
    private HangLuBean hangLuBean;

    public HLBianJiPop(Context context) {
        this(context,null);
    }

    public HLBianJiPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public void setHangLuBean(HangLuBean hangLuBean) {
        this.hangLuBean = hangLuBean;
        if (hangLuBean != null){
            mingcheng.setText(hangLuBean.getHanglu());
            weidu.setText(""+hangLuBean.getWeidu());
            gaodu.setText(""+hangLuBean.getGaodu());
            jingdu.setText(""+hangLuBean.getJingdu());
        }
    }

    public HLBianJiPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        data_hl = new ArrayList<HangLuBean>();
        hlAdapter = new HLAdapter(context);

    }


    public void initView() {
        View view = View.inflate(context, R.layout.hl_bianji, null);

        mingcheng = (EditText) view.findViewById(R.id.mingcheng);
        weidu = (EditText) view.findViewById(R.id.weidu);
        jingdu = (EditText) view.findViewById(R.id.jingdu);
        gaodu = (EditText) view.findViewById(R.id.gaodu);
        baocun = (Button) view.findViewById(R.id.baocun);
        baocun.setOnClickListener(this);

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
        String mingchengHLu = mingcheng.getText().toString();
        String weiduHLu = weidu.getText().toString();
        String jingduHLu = jingdu.getText().toString();
        String gaoduHLu = gaodu.getText().toString();
        HangLuBean bean = new HangLuBean();
        if (mingchengHLu.length()==0||
                weiduHLu.length()==0||
                jingduHLu.length()==0||
                gaoduHLu.length()==0)
            Toast.makeText(context,"填写内容不能为空",Toast.LENGTH_SHORT).show();
        else{
            bean.setHanglu(mingchengHLu);
            bean.setWeidu(Double.parseDouble(weiduHLu));
            bean.setJingdu(Double.parseDouble(jingduHLu));
            bean.setGaodu(Double.parseDouble(gaoduHLu));
            data_hl.add(bean);
            hlAdapter.setHLData(data_hl);
            hlAdapter.notifyDataSetChanged();
            dismissPopWindow();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.baocun:
                initData();
                break;
        }
    }

}
