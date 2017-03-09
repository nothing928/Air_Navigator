package com.hanke.navi.framwork.base;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.VoiceInteractor;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.ArcOptionsCreator;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.Text;
import com.amap.api.maps.model.TextOptions;
import com.hanke.navi.R;
import com.hanke.navi.framwork.share.PreferenceWrapper;
import com.hanke.navi.framwork.share.SharepreferenceHelper;
import com.hanke.navi.framwork.tools.AppManager;
import com.hanke.navi.framwork.utils.Constants;
import com.hanke.navi.skyair.MyApplication;
import com.hanke.navi.skyair.circle.OptionCircleGreenView;
import com.hanke.navi.skyair.circle.OptionCircleRedView;
import com.hanke.navi.skyair.circle.OptionCircleYellowView;
import com.hanke.navi.skyair.infowindow.InfoWinAdapter;
import com.hanke.navi.skyair.infowindow.InfoWindowPop;
import com.hanke.navi.skyair.scale.VerticalScaleScrollViewLeft;
import com.hanke.navi.skyair.socket.ClientOne;
import com.hanke.navi.skyair.socket.ClientTask;
import com.hanke.navi.skyair.socket.ToClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

/**
 * Description: Activity基本
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, Handler.Callback {

    public TextView tv_error, tv_msg_zy, tv_msg_jg;
    public LinearLayout bottom_bar;
    public VerticalScaleScrollViewLeft verticalScaleScrollViewLeft;
    public static MapView mapView;
    public static AMap aMap;
    public LocationSource.OnLocationChangedListener mListener;
    public AMapLocationClient aMapLocationClient;//声明AMapLocationClient类对象
    public AMapLocationClientOption aMapLocationClientOption;//声明AMapLocationClientOption对象
    public AMapLocationListener aMapLocationListener;//声明定位回调监听
    public static double latitude, longitude, haiba;
    public double speed, height_ground;
    public double bearing;
    public static int width_include, height_include;
    //点击标记
    public Marker marker;
    public MarkerOptions markerOptions;
    public static double latitude_clc, longitude_clc;
    public static double distance;
    public OptionCircleGreenView circle_green;
    public OptionCircleRedView circle_red;
    public OptionCircleYellowView circle_yellow;
    public static Circle circle_nei, circle_wai, circle_nei_ben, circle_wai_ben;
    public static int radius_nei = 8000, radius_wai = 15000;
    public static ImageView image;
    //gps
    public LocationManager gpsManager;

    public void startGps() {
        // 获取到LocationManager对象
        gpsManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //provider可为gps定位，也可为为基站和WIFI定位
        String provider = gpsManager.getProvider(LocationManager.GPS_PROVIDER).getName();

        //3000ms为定位的间隔时间，10m为距离变化阀值，gpsListener为回调接口
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gpsManager.requestLocationUpdates(provider, 3000, 10, gpsListener);
    }

    private void stopGps() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gpsManager.removeUpdates(gpsListener);
    }

    // 创建位置监听器
    private LocationListener gpsListener = new LocationListener() {

        // 位置发生改变时调用
        @Override
        public void onLocationChanged(Location location) {
            Log.e("Location", "onLocationChanged");
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            float speed = location.getSpeed();
            long time = location.getTime();
            String s = "latitude--->" + latitude
                    +  "  longitude--->" + longitude
                    +  "  speed--->" + speed
                    +  "  time--->" + new Date(time).toLocaleString();
//            mTextView.setText("GPS定位\n" + s);
        }

        // provider失效时调用
        @Override
        public void onProviderDisabled(String provider) {
            Log.e("Location", "onProviderDisabled");
        }

        // provider启用时调用
        @Override
        public void onProviderEnabled(String provider) {
            Log.e("Location", "onProviderEnabled");
        }

        // 状态改变时调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("Location", "onStatusChanged");
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ClientTask clientTask = new ClientTask(this);
//        clientTask.execute();
        String swd = SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getWeidu();
        String sjd = SharepreferenceHelper.getInstence(MyApplication.getAppContext()).getJingdu();
        Log.e("100","swd = "+swd);
        Log.e("100","sjd = "+sjd);
        setContentView(getContentViewId());
        initId();
        //必须回调MapView的onCreate()方法
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();

//            initLocation();
            setaMap();
        }
//        initLocation();
        //如果存在actionBar，就隐藏
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        AppManager.getInstance().addActivity(this);

        setTranslucentStatus();//状态栏透明
        setHideStatus();//隐藏状态栏

        beforeInitView();
        initView();
        initData();

    }

    public void initLocation() {
        //初始化定位
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(MyApplication.getAppContext());
//            初始化AMapLocationClientOption对象
            aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClient.setLocationListener(aMapLocationListener);
        }
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
        aMapLocationClientOption.setWifiActiveScan(true);//设置是否强制刷新WIFI，默认为强制刷新
        aMapLocationClientOption.setMockEnable(false);//设置是否允许模拟位置,默认为false，不允许模拟位置
        aMapLocationClientOption.setOnceLocation(true);//单次定位
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9));//14是500m

//        MarkerOptions markerOption = new MarkerOptions();
//        markerOption.position(Constants.YANLIANG);
//        markerOption.perspective(true);
//        markerOption.draggable(true);
//        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.zfj)));
//        // 将Marker设置为贴地显示，可以双指下拉看效果
//        markerOption.setFlat(true);
//        aMap.addMarker(markerOption);
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.YANLIANG, 9));//14是500m

    }

    final int REQ_LOCATION = 0x12;

    public void applyPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQ_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQ_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                aMapLocationClient.startLocation();
//                Toast.makeText(MainActivity.this, "已经开始定位", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BaseActivity.this, "没有权限，无法获取位置信息~", Toast.LENGTH_SHORT).show();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void initId() {
        mapView = (MapView) findViewById(R.id.mapView);
        tv_error = (TextView) findViewById(R.id.tv_error);
        bottom_bar = findViewByIdNoCast(R.id.bottom_bar);
        tv_msg_zy = findViewByIdNoCast(R.id.tv_msg_zy);
        tv_msg_jg = findViewByIdNoCast(R.id.tv_msg_jg);
    }

    //设置aMap的属性
    public void setaMap() {

        aMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                mListener = onLocationChangedListener;
            }

            @Override
            public void deactivate() {
                stopLocation();
            }
        });//设置定位监听

        // 自定义定位蓝点图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        image=new ImageView(getApplicationContext());
        image.setImageResource(R.mipmap.zfj);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.height = MyApplication.getWidth()/26;//设置图片的高度
//        layoutParams.width = MyApplication.getWidth()/26; //设置图片的宽度
//        Log.e("图标_主的宽高BaseActivity","图标_主的宽 = "+ layoutParams.width+",图标_主的高 = "+layoutParams.height);
        image.setLayoutParams(layoutParams);
//        image.setVisibility(View.GONE);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromView(image));
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.zfj));
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
//        myLocationStyle.strokeWidth(2);// 自定义精度范围的圆形边框宽度
        aMap.setMyLocationStyle(myLocationStyle);// 将自定义的 myLocationStyle 对象添加到地图上
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));//设置缩放级别
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);//卫星地图
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);//设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);//设置定位模式
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(2));//设置缩放级别
        aMap.getUiSettings().setZoomControlsEnabled(true);//设置缩放控件是否显示
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);
        aMap.getUiSettings().setCompassEnabled(true);// 设置指南针是否显示
        aMap.getUiSettings().setScaleControlsEnabled(true);// 设置比例尺是否显示
        aMap.getUiSettings().setZoomGesturesEnabled(true);//设置缩放手势
        aMap.getUiSettings().setScrollGesturesEnabled(true);//设置平移手势
        aMap.getUiSettings().setRotateGesturesEnabled(true);//设置旋转手势
        aMap.getUiSettings().setTiltGesturesEnabled(true);//设置倾斜手势
        aMap.getUiSettings().setAllGesturesEnabled(true);//设置所有手势
        aMap.getUiSettings().setLogoPosition(AMapOptions.LOGO_MARGIN_LEFT);
//        aMap.getUiSettings().setLogoBottomMargin(-50);//隐藏logo，但是没有作用
        aMap.getUiSettings().setLogoMarginRate(-100,200);
//        aMap.getProjection().fromScreenLocation(point);
//        int x = (int) mapView.getX();
//        int y = (int) mapView.getY();
//        Point point = new Point(x,y);
//        aMap.getProjection().fromScreenLocation(point);
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));// 设置当前地图显示为当前位置以及比例尺

        //设置定位回调监听
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (mListener != null && aMapLocation != null) {
                    if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {

                        mListener.onLocationChanged(aMapLocation);//显示系统的小蓝点
                        Criteria criteria = new Criteria();
                        criteria.setAltitudeRequired(true);
                        boolean isAl = aMapLocation.hasAltitude();
                        if (!isAl) {
                            return;
                        } else {
                            haiba = aMapLocation.getAltitude();
                        }
                        Log.e("000", "isAl = " + isAl);
                        latitude = aMapLocation.getLatitude();//获取纬度
                        longitude = aMapLocation.getLongitude();//获取经度
                        Log.e("000", "longitude = " + longitude + "|||" + "latitude = " + latitude+ "|||" + "haiba = " + haiba);
                        bearing = aMapLocation.getBearing();//获取方向角信息,即角速度
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9));// 设置当前地图显示为当前位置以及比例尺
                        if (mListener != null) {
                            mListener.onLocationChanged(aMapLocation);
                        }
                    } else {
                        String msg_err = null;
                        String[] str = aMapLocation.getErrorInfo().split(" ");
                        for (int i = 0; i < str.length; i++) {
                            msg_err = "定位失败，" + aMapLocation.getErrorCode() + ":" + str[0];
                        }
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText(msg_err);
                    }
                }
            }
        };
        initLocation();

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                initLocation();
//                latitude = latLng.latitude;
//                longitude = latLng.longitude;
                latitude_clc = latLng.latitude;
                longitude_clc = latLng.longitude;
//                addWayPoint(latitude_clc, longitude_clc);
                addMarkerPoint(latitude_clc, longitude_clc);
//                 addMarkerPoint(latitude, longitude);

                double lat_cha = latitude - latitude_clc;
                double longt_cha = longitude - longitude_clc;
                Log.e("cha", "lat的差 = " + lat_cha + " , longt的差 = " + longt_cha);
                Log.e("cha", "当前lat为：" + latitude + "当前longt为：" + longitude);
                Log.e("分割线", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            }
        });
        aMap.setInfoWindowAdapter(new InfoWinAdapter(MyApplication.getAppContext()));
        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
//                Toast.makeText(BaseActivity.this,"点击了窗口"+marker.getId()+"数据"+marker.getObject(),Toast.LENGTH_SHORT).show();
                InfoWindowPop infoWindowPop = new InfoWindowPop(MyApplication.getAppContext());
                infoWindowPop.showPopWindow(bottom_bar);
                marker.hideInfoWindow();
            }
        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {// 设置点击marker事件监听器
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (aMap!=null){
                    marker.showInfoWindow();
                    return true;
                }
                return false;
            }
        });

    }

    public static final int VELOCITY = 10;//飞机的飞行速度
    public static final int TIME_B = 1;//飞机飞每一小段的时间

    public void getDaohang(double latitude_mu, double longitude_mu) {
        double sum_time = getDistance(latitude,longitude, latitude_mu,  longitude_mu) / VELOCITY; //总运行时间
        Log.e("qaz","ewqrdfsfqefdfv="+getDistance(latitude,longitude,  latitude_mu,longitude_mu));
        int step = (int) sum_time / TIME_B;//总步数
        for (int i = 0; i < step; i++) {
            double lat_z = latitude;
            double lon_z = longitude;
            double lat_b = (latitude_mu-lat_z)/step;
            double lon_b= (longitude_mu-lon_z)/step;
            lat_z = lat_z+lat_b;
            lon_z = lon_z+lon_b;
//            lat_z = latitude_mu-lat_b;
//            lon_z = longitude_mu-lon_b;
            //设置中心点和缩放比例
            LatLng location_wei = new LatLng(lat_z, lon_z);
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(location_wei));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            List<LatLng> latLngs = new ArrayList<LatLng>();
            List<Polyline> lp = new ArrayList<Polyline>();
            latLngs.add(new LatLng(lat_z,lon_z));
            latLngs.add(new LatLng(latitude_mu,longitude_mu));
//            polyline = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth()/100).color(Color.GREEN));
//            lp.add(polyline);
        }

    }

    public void addWayPoint(double new_latitude, double new_longitude) {
        double x=0,y=0;
        if (new_latitude!=latitude_clc||new_longitude!=longitude_clc){
            double old_latitude = new_latitude;
            double old_longitude =new_longitude;
            new_latitude = latitude_clc;
            new_longitude = longitude_clc;
            distance = getDistance(old_latitude,old_longitude, new_latitude ,new_longitude);
            markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(new_latitude, new_longitude));
//        markerOptions.title("当前标记点");
            markerOptions.title("纬度：" + new_latitude + "°");
//        markerOptions.snippet("纬度：" + latitude + "\n经度：" + longitude+ "\n距离：" + distance+"m");
            markerOptions.snippet("经度：" + new_longitude + "°" + "\n距离：" + distance / 1000 + "km");
            Log.e("msgMarker", "纬度是：" + new_latitude + "\n经度是：" + new_longitude + "\n距离是：" + distance / 1000);
            markerOptions.draggable(true).setFlat(false);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
//        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker));

            ImageView image=new ImageView(getApplicationContext());
//            image.setImageResource(R.mipmap.feijiqt);
//            RelativeLayout.LayoutParams layoutParams9 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
//            layoutParams9.height = MyApplication.getWidth()/26;//设置图片的高度
//            layoutParams9.width = MyApplication.getWidth()/26; //设置图片的宽度
//            Log.e("图标_其他的宽高BaseActivity","图标_其他的宽 = "+layoutParams9.width+",图标_其他的高 = "+layoutParams9.height);
//            image.setLayoutParams(layoutParams9);
            image.setMinimumWidth(MyApplication.getWidth()/26);
            image.setMinimumHeight(MyApplication.getWidth()/26);
            markerOptions.icon(BitmapDescriptorFactory.fromView(image));

//            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.feijiqt)));
//        markerOptions.anchor(1,1);
            markerOptions.perspective(true);
            marker = aMap.addMarker(markerOptions);
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
            marker.showInfoWindow();
            marker.setRotateAngle(10);
//        addCircleNei(new LatLng(latitude, longitude), 500);
//        addCircleWai(new LatLng(latitude, longitude), 800);
            List<LatLng> latLngs = new ArrayList<LatLng>();
            List<Polyline> lp = new ArrayList<Polyline>();
            latLngs.add(new LatLng(old_latitude, old_longitude));
            latLngs.add(new LatLng(new_latitude, new_longitude));
            pline = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth() / 200).color(Color.parseColor("#9c3b99")).geodesic(true));
            lp.add(pline);
        }
//        }

        aMap.removecache();
    }

    public boolean change = false;

    public void addMarkerPoint(double latitude, double longitude) {
        distance = getDistance( this.latitude,this.longitude,latitude ,longitude);
//        double distances = AMapUtils.calculateLineDistance(new LatLng(this.latitude,this.longitude),new LatLng(latitude,longitude));
//        BigDecimal bd = new BigDecimal(distances);
//        bd = bd.setScale(3,BigDecimal.ROUND_HALF_UP);
//        distance=bd.doubleValue();
//        distance=(double)(Math.round(distances*1000)/1000.0);
        markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitude, longitude));//设置坐标点
//        markerOptions.title("当前标记点");
        markerOptions.title("纬度：" + latitude + "°");
        Log.e("msgMarker", "纬度是：" + latitude + "\n经度是：" + longitude + "\n距离是：" + distance);
        markerOptions.perspective(true);
        markerOptions.draggable(true).setFlat(false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.qfj)));
        markerOptions.anchor(0.5f,0.5f);//默认（0.5f, 1.0f）水平居中，垂直下对齐
        markerOptions.perspective(true);
//        if(marker==null||!marker.isVisible()){
            marker = aMap.addMarker(markerOptions);
            marker.setObject("123456789");
            marker.showInfoWindow();
//        }else{
//            marker.remove();
//        }
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude, longitude)));
        if(distance<=1*radius_nei){
            circle_red.setVisibility(View.VISIBLE);
            circle_green.setVisibility(View.GONE);
            circle_yellow.setVisibility(View.GONE);
            Log.e("distance","distance1 = " +distance);
            tv_msg_zy.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(12*MyApplication.getWidth()/16,height_include,MyApplication.getWidth()/24,0);
            tv_msg_jg.setLayoutParams(layoutParams);
            Typeface typeface= Typeface.SANS_SERIF;
            tv_msg_jg.setTypeface(typeface,Typeface.BOLD);
            tv_msg_jg.setTextSize(4*MyApplication.getWidth()/200);
            tv_msg_jg.setMaxLines(4);
            tv_msg_jg.setGravity(Gravity.CENTER);
            tv_msg_jg.setText("警告"+"\n警示区域内有其他飞机进入");
            tv_msg_jg.setTextColor(Color.RED);
            tv_msg_jg.setVisibility(View.VISIBLE);
            TimerTask task = new TimerTask(){
                public void run() {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            if(change){
                                change = false;
                                tv_msg_jg.setTextColor(Color.parseColor("#7fff0000")); //这个是透明，即看不到文字
                            }else{
                                change = true;
//                                tv_msg_zy.setAlpha(0.5f);
                                tv_msg_jg.setTextColor(Color.RED);
                            }
                        }
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(task,300,500);
        }else if (distance>1*radius_nei&&distance<=1*radius_wai){
            circle_yellow.setVisibility(View.VISIBLE);
            circle_green.setVisibility(View.GONE);
            circle_red.setVisibility(View.GONE);
            Log.e("distance","distance2 = " +distance);
            tv_msg_jg.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(12*MyApplication.getWidth()/16,height_include,MyApplication.getWidth()/24,0);
            tv_msg_zy.setLayoutParams(layoutParams);
            Typeface typeface= Typeface.SANS_SERIF;
            tv_msg_zy.setTypeface(typeface,Typeface.BOLD);
            tv_msg_zy.setTextSize(4*MyApplication.getWidth()/200);
            tv_msg_zy.setMaxLines(4);
            tv_msg_zy.setGravity(Gravity.CENTER);
            tv_msg_zy.setText("注意"+"\n注意区域内有其他飞机进入");
            tv_msg_zy.setTextColor(Color.YELLOW);
            tv_msg_zy.setVisibility(View.VISIBLE);
            TimerTask task = new TimerTask(){
                public void run() {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            if(change){
                                change = false;
                                tv_msg_zy.setTextColor(Color.parseColor("#7fffff00")); //这个是透明，=看不到文字
                            }else{
                                change = true;
//                                tv_msg_zy.setAlpha(0.5f);
                                tv_msg_zy.setTextColor(Color.YELLOW);
                            }
                        }});
                }
            };
            Timer timer = new Timer();
            timer.schedule(task,600,800);
        }else {
            tv_msg_zy.setVisibility(View.GONE);
            tv_msg_jg.setVisibility(View.GONE);
            circle_green.setVisibility(View.VISIBLE);
            circle_yellow.setVisibility(View.GONE);
            circle_red.setVisibility(View.GONE);
            Log.e("distance","distance3 = " +distance);
        }
//        addCircleNei(new LatLng(latitude, longitude), 500);
//        addCircleWai(new LatLng(latitude, longitude), 800);

//        List<LatLng> latLngs = new ArrayList<LatLng>();
//        List<Polyline> lp = new ArrayList<Polyline>();
//        latLngs.add(new LatLng(this.latitude, this.longitude));
//        latLngs.add(new LatLng(latitude, longitude));
//        pline = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth() / 150).color(Color.CYAN));
//        lp.add(pline);
//        }

        aMap.removecache();
    }

    public static void addCircleNei(LatLng latLng, int radius_nei) {
        CircleOptions circleOptions_nei = new CircleOptions();
        circleOptions_nei.center(latLng);
        circleOptions_nei.radius(radius_nei);
        circleOptions_nei.strokeWidth(6);
//        circleOptions_nei.fillColor(Color.parseColor("#0093898d"));
        circleOptions_nei.strokeColor(Color.RED);
        circle_nei = aMap.addCircle(circleOptions_nei);
    }
    public static void addCircleWai(LatLng latLng, int radius_wai) {
        CircleOptions circleOptions_wai = new CircleOptions();
        circleOptions_wai.center(latLng);
        circleOptions_wai.radius(radius_wai);
        circleOptions_wai.strokeWidth(6);
//        circleOptions_wai.fillColor(Color.parseColor("#9093898d"));
        circleOptions_wai.strokeColor(Color.YELLOW);
        circle_wai = aMap.addCircle(circleOptions_wai);
    }

    public static void addCircleNeiBen(LatLng latLng, int radius_nei) {
        CircleOptions circleOptions_nei = new CircleOptions();
        circleOptions_nei.center(latLng);
        circleOptions_nei.radius(radius_nei);
        circleOptions_nei.strokeWidth(6);
//        circleOptions_nei.fillColor(Color.parseColor("#0093898d"));
        circleOptions_nei.strokeColor(Color.RED);
        circle_nei_ben = aMap.addCircle(circleOptions_nei);
    }
    public static void addCircleWaiBen(LatLng latLng, int radius_wai) {
        CircleOptions circleOptions_wai = new CircleOptions();
        circleOptions_wai.center(latLng);
        circleOptions_wai.radius(radius_wai);
        circleOptions_wai.strokeWidth(6);
//        circleOptions_wai.fillColor(Color.parseColor("#9093898d"));
        circleOptions_wai.strokeColor(Color.YELLOW);
        circle_wai_ben = aMap.addCircle(circleOptions_wai);
    }

    public static void addCircleHuan(LatLng latLng, int radius_nei, int radius_wai) {
        CircleOptions circleOptions_nei = new CircleOptions();
        circleOptions_nei.center(latLng);
        circleOptions_nei.radius(radius_nei);
        circleOptions_nei.strokeWidth(6);
        circleOptions_nei.strokeColor(Color.RED);
        circle_nei = aMap.addCircle(circleOptions_nei);
        CircleOptions circleOptions_wai = new CircleOptions();
        circleOptions_wai.center(latLng);
        circleOptions_wai.radius(radius_wai);
        circleOptions_wai.strokeWidth(6);
        circleOptions_wai.strokeColor(Color.YELLOW);
        circle_wai = aMap.addCircle(circleOptions_wai);
    }

    public static Polyline polyline,pline,polyline2,polyline3;
    public static Text text,text2,text3;

    public static void setSpaceAir(int minHeight,int maxHeight){
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9));// 设置当前地图显示为当前位置以及比例尺
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<Polyline> lp = new ArrayList<Polyline>();
        text=aMap.addText(new TextOptions().position(new LatLng(35.28, 108.2)).text("空域A"));
        latLngs.add(new LatLng(35.28, 108.2));
        latLngs.add(new LatLng(35.16, 108.15));
        latLngs.add(new LatLng(35.12, 108.40));
        latLngs.add(new LatLng(37.12, 109.12));
        latLngs.add(new LatLng(37.24, 108.00));
        latLngs.add(new LatLng(35.28, 108.2));
        polyline = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth() / 150).color(Color.BLUE).setDottedLine(true).geodesic(true));
        lp.add(polyline);
    }
    public static void setSpaceAir2(int minHeight,int maxHeight){
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9));// 设置当前地图显示为当前位置以及比例尺
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<Polyline> lp = new ArrayList<Polyline>();
        text2=aMap.addText(new TextOptions().position(new LatLng(36.10, 110.24)).text("空域B"));
        latLngs.add(new LatLng(36.10, 110.24));
        latLngs.add(new LatLng(35.40, 110.19));
        latLngs.add(new LatLng(35.50, 111.15));
        latLngs.add(new LatLng(36.45, 111.50));
        latLngs.add(new LatLng(36.45, 111.20));
        latLngs.add(new LatLng(36.10, 110.24));
        polyline2 = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth() / 150).color(Color.BLUE).setDottedLine(true).geodesic(true));
        lp.add(polyline);
    }
    public static void setSpaceAir3(int minHeight,int maxHeight){
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 9));// 设置当前地图显示为当前位置以及比例尺
        List<LatLng> latLngs = new ArrayList<LatLng>();
        List<Polyline> lp = new ArrayList<Polyline>();
        text3=aMap.addText(new TextOptions().position(new LatLng(34.10, 109.30)).text("空域C"));
        latLngs.add(new LatLng(34.10, 109.30));
        latLngs.add(new LatLng(34.0, 109.50));
        latLngs.add(new LatLng(34.10, 110.17));
        latLngs.add(new LatLng(34.25, 110.8));
        latLngs.add(new LatLng(34.25, 109.50));
        latLngs.add(new LatLng(34.30, 109.45));
        latLngs.add(new LatLng(34.40, 109.45));
        latLngs.add(new LatLng(34.30, 109.13));
        latLngs.add(new LatLng(34.10, 109.30));
        polyline3 = aMap.addPolyline(new PolylineOptions().addAll(latLngs).width(MyApplication.getWidth() / 150).color(Color.BLUE).setDottedLine(true).geodesic(true));
        lp.add(polyline);
    }

    //计算两点之间距离(根据经纬度)
//    public double getDistance(LatLng start,LatLng end){
//        double lat1 = (Math.PI/180)*start.latitude;
//        double lat2 = (Math.PI/180)*end.latitude;
//
//        double lon1 = (Math.PI/180)*start.longitude;
//        double lon2 = (Math.PI/180)*end.longitude;
//        //地球半径
//        double R = 6371;
//        //两点间距离 km，如果想要米的话，结果*1000就可以了
//        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
//
//        return d*1000;
//    }
    private final double EARTH_RADIUS = 6378137.0;

    // 返回单位是米
    public double getDistance( double latitude1,double longitude1,
                               double latitude2,double longitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private double rad(double d) {
        return d * Math.PI / 180.0;
    }

    //获取include布局的宽高
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        verticalScaleScrollViewLeft = new VerticalScaleScrollViewLeft(MyApplication.getAppContext());
        int bottom_bar_minHeight = verticalScaleScrollViewLeft.height / 21;
        bottom_bar.setMinimumHeight(bottom_bar_minHeight);
        width_include = bottom_bar.getWidth();
        height_include = bottom_bar.getHeight();
        if (height_include <= bottom_bar_minHeight) {
            height_include = bottom_bar_minHeight;
        }
        Log.e("include布局的宽高", "width_include = " + width_include + ", height_include = " + height_include);
        Log.e("~~~~~~~~~~~~~~~~~~", "bottom_bar_minHeight = " + bottom_bar_minHeight);
    }

    public abstract int getContentViewId();//放layoutId

    public abstract void beforeInitView();//初始化View之前做的事

    public abstract void initView();//初始化View

    public abstract void initData();//初始化数据


    /**
     * 隐藏状态栏
     */
    public void setHideStatus() {
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = BaseActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }

    /**
     * 状态栏透明只有Android 4.4 以上才支持
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 不用强制转换的findviewbyid
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewByIdNoCast(int id) {
        return (T) findViewById(id);
    }

    public void setOnClick(int... ids) {
        for (int id : ids)
            findViewById(id).setOnClickListener(this);

    }

    public void setOnClick(View... views) {
        for (View view : views)
            view.setOnClickListener(this);

    }

    private Dialog dialog;

    /**
     * 显示进度条
     */
//    public void showProgressBar() {
//        if (dialog == null)
//            dialog = new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
//        View view = View.inflate(this, R.layout.dialog_progressbar, null);
//        dialog.setContentView(view);
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        ImageView img_progressbar = (ImageView) view.findViewById(R.id.img_progressbar);
//        AnimationDrawable animationDrawable = (AnimationDrawable) img_progressbar.getDrawable();
//        animationDrawable.start();
//        dialog.show();
//    }

    private Handler handler;

    /**
     * 结束进度条
     */
    public void dismissProgressBar() {

        handler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        return false;
    }

    /**
     * 本段代码用来处理如果输入法还显示的话就消失掉输入键盘
     */
    protected void dismissSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    protected void showKeyboard(View view) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
        stopLocation();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onPause();
//    }

    public void stopLocation() {//停止定位
        mListener = null;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }

    @Override
    protected void onDestroy() {
//        AppManager.getInstance().remove(this);
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        PreferenceWrapper preferenceWrapper = new PreferenceWrapper(MyApplication.getAppContext());
        preferenceWrapper.clear();
//        ToClient toClient = new ToClient();
//        toClient.LiuClose();
//        ClientOne clientOne = new ClientOne();
//        clientOne.LiuClose();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }
}
