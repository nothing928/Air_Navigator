package com.hanke.navi.skyair.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.hanke.navi.R;
import com.hanke.navi.skyair.MyApplication;

/**
 * Created by Asus on 2017/1/1.
 */
public class GaoJingPop extends PopupWindow implements Handler.Callback,View.OnClickListener {

    private Context context;
    private Handler handler;
    private Spinner spinner;
    private EditText gaojing_juli,gaojing_gaodu,gaojing_time,gaojing_ky_yj_time,gaojing_zd_yj_time;
    private TextView[] textViews;
    private TextView gaojing_caz,gaojing_pal,gaojing_save;
    public static int popupHeight, popupWidth;

    public GaoJingPop(Context context) {
        this(context, null);
    }

    public GaoJingPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GaoJingPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        initData();
    }

    public void initView() {

        View view = View.inflate(context, R.layout.gaojing_set, null);
//        view.setBackgroundResource(R.drawable.infowin_bgd);
        this.setContentView(view);


//        spinner = (Spinner) view.findViewById(R.id.spinner);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                MyApplication.getAppContext(), R.array.array_fangxiangzhuang,
//                android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
        gaojing_juli = (EditText) view.findViewById(R.id.gaojing_juli);
        gaojing_gaodu = (EditText) view.findViewById(R.id.gaojing_gaodu);
        gaojing_time = (EditText) view.findViewById(R.id.gaojing_time);
        gaojing_ky_yj_time = (EditText) view.findViewById(R.id.gaojing_ky_yj_time);
        gaojing_zd_yj_time = (EditText) view.findViewById(R.id.gaojing_zd_yj_time);
        gaojing_caz = (TextView) view.findViewById(R.id.gaojing_caz);
        gaojing_pal = (TextView) view.findViewById(R.id.gaojing_pal);
        gaojing_save = (TextView) view.findViewById(R.id.gaojing_save);





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

    public void initData() {
        textViews = new TextView[]{gaojing_caz, gaojing_pal};
        gaojing_caz.setOnClickListener(this);
        gaojing_pal.setOnClickListener(this);

    }

    /**
     * 显示popwindow
     *
     * @param view
     */
    public void showPopWindow(View view) {
        if (!isShowing()) {
//            this.showAsDropDown(view);//显示在view的下方
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
    public void setSelect(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setBackgroundResource(R.drawable.caz_pal_bgd);
                textViews[i].setTextColor(Color.parseColor("#1AD5FD"));
                textViews[i].setEnabled(false);
            } else {
                textViews[i].setBackgroundResource(R.drawable.gj_jc_set_bgd);
                textViews[i].setTextColor(Color.WHITE);
                textViews[i].setEnabled(true);
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gaojing_caz:
                setSelect(0);
                break;
            case R.id.gaojing_pal:
                setSelect(1);
                break;
            case R.id.gaojing_save:

                break;
        }
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        if (BaseActivity.aMap != null) {
//            setSelectPos((String) parent.getItemAtPosition(position));
//        }
//    }
//    private void setSelectPos(String layerName) {
//        if (layerName.equals(context.getString(R.string.caz))) {
//            Toast.makeText(context,"点击了CAZ",Toast.LENGTH_SHORT).show();
//        } else if (layerName.equals(context.getString(R.string.pal))) {
//            Toast.makeText(context,"点击了PAL",Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {}

}
