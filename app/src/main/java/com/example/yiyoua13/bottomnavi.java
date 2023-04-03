package com.example.yiyoua13;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.DPoint;
import com.amap.api.maps.MapFragment;
import com.amap.api.maps.MapsInitializer;
import com.example.yiyoua13.ui.User.UserFragment;
import com.example.yiyoua13.ui.dashboard.DashboardFragment;
import com.example.yiyoua13.ui.home.HomeFragment;
import com.example.yiyoua13.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.yiyoua13.databinding.ActivityBottomnaviBinding;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class bottomnavi extends AppCompatActivity {
    //public static final int GEOFENCE_IN 进入地理围栏
//public static final int GEOFENCE_OUT 退出地理围栏
//public static final int GEOFENCE_STAYED 停留在地理围栏内10分钟

    private ViewPager mcContainer;
    private List<Fragment> fragmentList;


    private TextToSpeech tts;
    public static class location{
        private Double Longitude;
        private Double Latitude;
        public Double getLongitude() {
            return Longitude;
        }
        public Double getLatitude() {
            return Latitude;
        }
        public void setLongitude(Double longitude) {
            Longitude = longitude;
        }
        public void setLatitude(Double latitude) {
            Latitude = latitude;
        }
    }
    public static boolean BroadcastPermission = true;
    public static boolean Isbroadcast = false;

//https://link.jscdn.cn/sharepoint/aHR0cHM6Ly9vbmVkcnYtbXkuc2hhcmVwb2ludC5jb20vOnU6L2cvcGVyc29uYWwvc3Rvcl9vbmVkcnZfb25taWNyb3NvZnRfY29tL0VTUS1YYWZvbmxKRXE4eUVIcFgwQkRRQnlCVmV4NHhEbEJjYXZaZHdpUnpvNEE.mp3
    public static final String GEO_FENCE_BROADCAST_ACTION = "com.example.yiyoua13.GEOFENCE_BROADCAST_ACTION";
    private ActivityBottomnaviBinding binding;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    public AMapLocationClient mLocationClient = null;
    MediaPlayer mediaPlayer=new MediaPlayer();
    AsyncPlayer player = new AsyncPlayer("music");
    public static location loc = new location();
    //private MediaPlayer mediaPlayer=new MediaPlayer();
    private String example="https://link.jscdn.cn/sharepoint/aHR0cHM6Ly9vbmVkcnYtbXkuc2hhcmVwb2ludC5jb20vOnU6L2cvcGVyc29uYWwvc3Rvcl9vbmVkcnZfb25taWNyb3NvZnRfY29tL0VTUS1YYWZvbmxKRXE4eUVIcFgwQkRRQnlCVmV4NHhEbEJjYXZaZHdpUnpvNEE.mp3";
    //create button to jump to FragAct.java

    public void PlayMusic(String str) throws IOException {
        Log.e("播放音乐","播放音乐");
        tts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {//实例化自带语音对象
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS){//设置语音
                    tts.setLanguage(Locale.CHINESE);//中文
                    //不循环播放
                    tts.setSpeechRate(1.0f);//语速
                    tts.setPitch(1.0f);//音调
                    tts.speak(str,TextToSpeech.QUEUE_FLUSH,null);//播放
                    //循环播放
                    //tts.speak(str,TextToSpeech.QUEUE_ADD,null);

                }
            }
        });
    }


    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    loc.Longitude = (aMapLocation.getLongitude());
                    loc.Latitude = (aMapLocation.getLatitude());
                    Log.e("位置：", aMapLocation.getAddress());
                    Log.e("经度：", aMapLocation.getLongitude() + "");
                    Log.e("纬度：", aMapLocation.getLatitude() + "");
                    Log.e("精度：", aMapLocation.getAccuracy() + "");
                    Log.e("时间：", aMapLocation.getTime() + "");
                    Log.e("速度：", aMapLocation.getSpeed() + "");
                    Log.e("方向：", aMapLocation.getBearing() + "");
                    Log.e("海拔：", aMapLocation.getAltitude() + "");
                    Log.e("国家：", aMapLocation.getCountry() + "");
                    Log.e("省：", aMapLocation.getProvince() + "");
                    Log.e("市：", aMapLocation.getCity() + "");
                    Log.e("城市编码：", aMapLocation.getCityCode() + "");
                    Log.e("区：", aMapLocation.getDistrict() + "");
                    Log.e("区域编码：", aMapLocation.getAdCode() + "");
                    Log.e("街道：", aMapLocation.getStreet() + "");
                    Log.e("街道编码：", aMapLocation.getStreetNum() + "");
                    Log.e("建筑物：", aMapLocation.getAoiName() + "");
                    Log.e("室内定位：", aMapLocation.getBuildingId() + "");
                    Log.e("室内定位：", aMapLocation.getFloor() + "");
                    Log.e("室内定位：", aMapLocation.getGpsAccuracyStatus() + "");
                    Log.e("室内定位：", aMapLocation.getLocationDetail() + "");
                    Log.e("室内定位：", aMapLocation.getLocationType() + "");
                    Log.e("室内定位：", aMapLocation.getPoiName() + "");
                    Log.e("室内定位：", aMapLocation.getRoad() + "");
                    Log.e("室内定位：", aMapLocation.getRoad() + "");
                    Log.e("室内定位：", aMapLocation.getSatellites() + "");
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }
    private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GEO_FENCE_BROADCAST_ACTION)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                    String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                    String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);

                    if((BroadcastPermission)&&status==1/*&&Isbroadcast==false*/){
                        //播放音乐
                        /*try {
                            PlayMusic("");

                            //Isbroadcast=true;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }*/

                    }
                    else if((BroadcastPermission)&&status==2){
                        //停止播放音乐
                        mediaPlayer.stop();
                        Log.e("停止播放音乐","停止播放音乐");
                    }
                    else if((BroadcastPermission)&&status==4){
                        //停止播放音乐
                        mediaPlayer.stop();
                        Log.e("停止播放音乐","停止播放音乐");
                    }
                    else{
                        Log.e("不播放音乐","不播放音乐");
                    }
                    Log.e("围栏状态", status + "");
                    Log.e("围栏id", fenceId + "");
                    Log.e("围栏自定义id", customId + "");
                }
            }
            else {
                Log.e("围栏广播", "围栏广播接收失败");
            }
        }
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomnaviBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MapsInitializer.updatePrivacyAgree(this, true);
        MapsInitializer.updatePrivacyShow(this, true, true);
        //申请定位权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        //以下是对接围栏的代码
        /*List<GeoFenceClient> geoFenceClients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GeoFenceClient mGeoFenceClient = new GeoFenceClient(getApplicationContext());
            mGeoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN | GeoFenceClient.GEOFENCE_OUT|GeoFenceClient.GEOFENCE_STAYED);
            DPoint dPoint = new DPoint();
            dPoint.setLatitude(30.313567);
            dPoint.setLongitude(120.354713);
            mGeoFenceClient.addGeoFence(dPoint, 1000, "测试围栏");
            mGeoFenceClient.createPendingIntent(GEO_FENCE_BROADCAST_ACTION);
            geoFenceClients.add(mGeoFenceClient);

        }*/
        mcContainer = binding.vpContainer;
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //以上是对接围栏的代码
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new DashboardFragment());
        fragmentList.add(new UserFragment());
        mcContainer.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(),fragmentList));
        //在item2处禁止滑动
        /*mcContainer.setOnTouchListener((v, event) -> {
            if(mcContainer.getCurrentItem()==1){
                return true;
            }
            return false;
        });*/
        mcContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Do Nothing
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //跳转到fragment时点亮按钮
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        switch (mcContainer.getCurrentItem()){
                            case 0:
                                navView.getMenu().getItem(0).setChecked(true);
                                break;
                            case 1:
                                navView.getMenu().getItem(1).setChecked(true);
                                break;
                            case 2:
                                navView.getMenu().getItem(2).setChecked(true);
                                break;
                            case 3:
                                navView.getMenu().getItem(3).setChecked(true);
                                break;
                        }
                        break;
                }
            }
        });
        GeoFenceClient mGeoFenceClient = new GeoFenceClient(getApplicationContext());
        //mGeoFenceClient.addGeoFence();围栏开始创建
        mGeoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN | GeoFenceClient.GEOFENCE_OUT|GeoFenceClient.GEOFENCE_STAYED);
        DPoint dPoint = new DPoint();
        dPoint.setLatitude(30.313567);
        dPoint.setLongitude(120.354713);
        mGeoFenceClient.addGeoFence(dPoint, 1000, "测试围栏");

        //围栏创建结束
        mGeoFenceClient.createPendingIntent(GEO_FENCE_BROADCAST_ACTION);

        GeoFenceListener geoFenceListener = new GeoFenceListener() {
            @Override
            public void onGeoFenceCreateFinished(List<GeoFence> geoFenceList,int errorCode, String s) {

                if(errorCode == GeoFence.ADDGEOFENCE_SUCCESS){//判断围栏是否创建成功
                    Log.e("围栏创建", "onGeoFenceCreateFinished: " +true);

                    //geoFenceList是已经添加的围栏列表，可据此查看创建的围栏
                } else {
                    Log.e("围栏创建", "onGeoFenceCreateFinished: " +false);
                }
            }

            public void onGeoFencePause() {
                Log.e("围栏暂停", "onGeoFencePause: " );
            }
            public void onGeoFenceTriggered(GeoFence geoFence, int i, float v, float v1, float v2, float v3) throws NoSuchFieldException {
                Field stayTimeField = geoFence.getClass().getDeclaredField("stayTime");
                Log.e("围栏触发", "onGeoFenceTriggered: " );
            }
        };
        mGeoFenceClient.setGeoFenceListener(geoFenceListener);//设置监听

        //停留时间改为一分钟
        //mGeoFenceClient.setGeoFenceStayedTime(1000*60);

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);//网络监听
        filter.addAction(GEO_FENCE_BROADCAST_ACTION);//围栏监听
        registerReceiver(mGeoFenceReceiver, filter);//注册广播接收器
        //每三秒钟弹出一个Toast
        /*new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(bottomnavi.this, "每三秒钟弹出一个Toast", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();*/
        /*new Thread(new Runnable() {
            public void run() {

            }
        }).start();*/

        //广播接收器


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard,  R.id.navigation_user)
                .build();
        //press navigation_comment to jump to the FragAct.java

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottomnavi);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(binding.navView, navController);
        try {
            mLocationClient = new AMapLocationClient(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mcContainer.setCurrentItem(0);
                        //设置颜色
                        //navView.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

                        break;
                    case R.id.navigation_dashboard:
                        mcContainer.setCurrentItem(1);
                        break;
                    /*case R.id.navigation_notifications:
                        mcContainer.setCurrentItem(2);
                        break;*/
                    case R.id.navigation_user:
                        mcContainer.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

}