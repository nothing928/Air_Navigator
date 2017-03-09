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
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.callback.AddHLItemCallback;
import com.hanke.navi.skyair.db.HLModel;
import com.hanke.navi.skyair.pop.bean.HangLuBean;

import java.util.ArrayList;

/**
 * Created by Che on 2017/3/4.
 */
public class HLAddJiaHaoPop extends PopupWindow implements View.OnClickListener{

    private Context context;
    private EditText mc_jiahao,wd_jiahao,jd_jiahao,gd_jiahao;
    private Button bc_jiahao;
    private ArrayList<String> data;
    private HLAdapter hlAdapter;
    private AddHLItemCallback addHLItemCallback;
    private int position;

    public void setAddHLItemCallback(AddHLItemCallback addHLItemCallback) {
        this.addHLItemCallback = addHLItemCallback;
    }

    public HLAddJiaHaoPop(Context context, int position) {
        this(context,null);
        this.context = context;
        this.position = position;
        initView();
        data = new ArrayList<String>();
        hlAdapter = new HLAdapter(context);
    }
    public HLAddJiaHaoPop(Context context) {
        this(context,null);
    }

    public HLAddJiaHaoPop(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HLAddJiaHaoPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initView() {
        View view = View.inflate(context, R.layout.hladd_jiahao, null);

        mc_jiahao = (EditText) view.findViewById(R.id.mc_jiahao);
        wd_jiahao = (EditText) view.findViewById(R.id.wd_jiahao);
        jd_jiahao = (EditText) view.findViewById(R.id.jd_jiahao);
        gd_jiahao = (EditText) view.findViewById(R.id.gd_jiahao);
        bc_jiahao = (Button) view.findViewById(R.id.bc_jiahao);
        bc_jiahao.setOnClickListener(this);

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
        String mingchengHLu_kong = mc_jiahao.getText().toString();
        String weiduHLu_kong = wd_jiahao.getText().toString();
        String jingduHLu_kong = jd_jiahao.getText().toString();
        String gaoduHLu_kong = gd_jiahao.getText().toString();
        HangLuBean hangLuBean = new HangLuBean();
        if (mingchengHLu_kong.length()==0||
                weiduHLu_kong.length()==0||
                jingduHLu_kong.length()==0||
                gaoduHLu_kong.length()==0)
            Toast.makeText(context,"填写内容不能为空",Toast.LENGTH_SHORT).show();
        else{
            hangLuBean.setHanglu(mingchengHLu_kong);
            hangLuBean.setGaodu(Double.parseDouble(gaoduHLu_kong));
            hangLuBean.setJingdu(Double.parseDouble(jingduHLu_kong));
            hangLuBean.setWeidu(Double.parseDouble(weiduHLu_kong));
            addHLItemCallback.addHLItem(position,hangLuBean);
            HLModel hlModel = new HLModel();
            hlModel.insertHL(hangLuBean);
            dismissPopWindow();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bc_jiahao:
                initData();
                break;
        }
    }


}
