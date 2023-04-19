package com.example.yiyoua13;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AsyncPlayer;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
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
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.track.AMapTrackClient;
import com.amap.api.track.ErrorCode;
import com.amap.api.track.OnTrackLifecycleListener;
import com.amap.api.track.TrackParam;
import com.amap.api.track.query.entity.Point;
import com.amap.api.track.query.entity.TrackPoint;
import com.amap.api.track.query.model.AddTerminalRequest;
import com.amap.api.track.query.model.AddTerminalResponse;
import com.amap.api.track.query.model.AddTrackResponse;
import com.amap.api.track.query.model.DistanceResponse;
import com.amap.api.track.query.model.HistoryTrackResponse;
import com.amap.api.track.query.model.LatestPointResponse;
import com.amap.api.track.query.model.OnTrackListener;
import com.amap.api.track.query.model.ParamErrorResponse;
import com.amap.api.track.query.model.QueryTerminalRequest;
import com.amap.api.track.query.model.QueryTerminalResponse;
import com.amap.api.track.query.model.QueryTrackResponse;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.ui.User.UserFragment;
import com.example.yiyoua13.ui.dashboard.DashboardFragment;
import com.example.yiyoua13.ui.home.HomeFragment;
import com.example.yiyoua13.utils.StatusBarUtil;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class bottomnavi extends AppCompatActivity {

    private ViewPager mcContainer;
    private List<Fragment> fragmentList;
    private String user_token, user_id;


    private TextToSpeech tts;
    private int counter=0;
    private int counter2=0;
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

    //create button to jump to FragAct.java
    public String generateRandomString() {
        String prefix = "user-";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

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
                    counter++;
                    if (counter==2& !user_token.equals("")){
                        Url_Request.sendRequestUserSendLocation(Url_Request.getUrl_head()+"/user/sendLocation",user_token,loc.Longitude.toString(),loc.Latitude.toString());
                        Log.e("发送位置",loc.Longitude.toString()+loc.Latitude.toString());


                    }
                    if (counter>30){
                        counter=0;
                        counter2++;
                        if (counter2>6){
                            Url_Request.sendRequestSign_In(Url_Request.getUrl_head()+"/user/sign_in",user_token,user_id);
                        }
                    }


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
                    Log.e("围栏广播", "围栏广播接收成功");
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
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }

        MapsInitializer.updatePrivacyAgree(this, true);
        MapsInitializer.updatePrivacyShow(this, true, true);


        final AMapTrackClient aMapTrackClient = new AMapTrackClient(getApplicationContext());//初始化轨迹服务客户端

        aMapTrackClient.setInterval(3, 15);//设置定位和打包周期
        aMapTrackClient.setCacheSize(50);//设置本地缓存大小
        final long serviceId = 915218;  // 这里填入你创建的服务id
         String terminalName = null;
        //Timer timer = new Timer();
        SharedPreferences sp =this.getSharedPreferences("Login", MODE_PRIVATE);
        if (sp.getString("terminal", "").equals("")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("terminal", generateRandomString());
            editor.commit();
            terminalName=sp.getString("terminal","");

        }else{
            terminalName=sp.getString("terminal","");
        }
        Log.e("terminal",terminalName);
        user_token=sp.getString("TOKEN","");
        Log.e("token",user_token);

        if (user_token.equals("")){
        }else{
            Url_Request.sendRequestUserMe(Url_Request.getUrl_head() + "/user/me", user_token, bottomnavi.this, new Url_Request.OnIconResponseListener() {
                @Override
                public void onBeanResponse(Object bean) {

                }
            });
        }


        user_id=sp.getString("USER_ID","");






        final OnTrackLifecycleListener onTrackLifecycleListener = new OnTrackLifecycleListener() {

            @Override
            public void onBindServiceCallback(int i, String s) {

            }

            @Override
            public void onStartGatherCallback(int status, String msg) {
                if (status == ErrorCode.TrackListen.START_GATHER_SUCEE ||
                        status == ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED) {
                    Log.e("轨迹", "定位采集开启成功！");
                    Toast.makeText(bottomnavi.this, "定位采集开启成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("轨迹", msg);
                    Toast.makeText(bottomnavi.this, "定位采集启动异常，" + msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackCallback(int status, String msg) {
                if (status == ErrorCode.TrackListen.START_TRACK_SUCEE ||
                        status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK ||
                        status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
                    // 服务启动成功，继续开启收集上报
                    aMapTrackClient.startGather(this);
                } else {
                    Log.e("轨迹", msg);
                    Toast.makeText(bottomnavi.this, "轨迹上报服务服务启动异常，" + msg, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStopGatherCallback(int i, String s) {

            }

            @Override
            public void onStopTrackCallback(int i, String s) {

            }
        };
        SharedPreferences sharedPreferences = getSharedPreferences("track", MODE_PRIVATE);
        final long terminalId = sharedPreferences.getLong("terminalId", 0);
        Log.e("轨迹终端id1", terminalId + "");
        long flagtrack = sharedPreferences.getLong("trackId", 0);
        if (flagtrack!=0){

        }else {
            aMapTrackClient.addTrack(new com.amap.api.track.query.model.AddTrackRequest(serviceId, terminalId), new OnTrackListener() {


                @Override
                public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

                }

                @Override
                public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

                }

                @Override
                public void onDistanceCallback(DistanceResponse distanceResponse) {

                }

                @Override
                public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

                }

                @Override
                public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

                }

                @Override
                public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

                }

                @Override
                public void onAddTrackCallback(AddTrackResponse addTrackResponse) {
                    if (addTrackResponse.isSuccess()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("track", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("trackId", addTrackResponse.getTrid());
                        editor.apply();
                        Log.e("轨迹callback", "轨迹上报服务服务启动成功！"+addTrackResponse.getTrid()+"终端id"+terminalId);


                    } else {
                        Log.e("轨迹", "轨迹上报服务服务启动失败！"+addTrackResponse.getErrorMsg());
                    }
                }

                @Override
                public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

                }
            });
        }

        String finalTerminalName = terminalName;
        Log.e("final", finalTerminalName + "");
        aMapTrackClient.queryTerminal(new QueryTerminalRequest(serviceId, terminalName), new OnTrackListener() {

            @Override
            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {
                if (queryTerminalResponse.isSuccess()) {
                    if (queryTerminalResponse.getTid() <= 0) {
                        // terminal还不存在，先创建
                        aMapTrackClient.addTerminal(new AddTerminalRequest(finalTerminalName, serviceId), new OnTrackListener() {

                            @Override
                            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

                            }

                            @Override
                            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {
                                if (addTerminalResponse.isSuccess()) {
                                    // 创建完成，开启猎鹰服务
                                    long terminalId = addTerminalResponse.getTid();
                                    Log.e("轨迹", "terminalId: " + terminalId);
                                    SharedPreferences sharedPreferences = getSharedPreferences("track", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putLong("terminalId", terminalId);
                                    editor.apply();

                                    Log.e("轨迹终端id1",  sharedPreferences.getLong("terminalId",0)+ "");
                                    long trackId = sharedPreferences.getLong("trackId", 0);
                                    Log.e("轨迹", "trackId: " + trackId);
                                    TrackParam trackParam = new TrackParam(serviceId, terminalId);
                                    trackParam.setTrackId(trackId);
                                    aMapTrackClient.startTrack(trackParam, onTrackLifecycleListener);


                                } else {
                                    // 请求失败
                                    Log.e("轨迹", "请求失败，" + addTerminalResponse.getErrorMsg());
                                    Toast.makeText(bottomnavi.this, "请求失败，" + addTerminalResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onDistanceCallback(DistanceResponse distanceResponse) {

                            }

                            @Override
                            public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

                            }

                            @Override
                            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

                            }

                            @Override
                            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

                            }

                            @Override
                            public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

                            }

                            @Override
                            public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

                            }
                        });
                    } else {
                        // terminal已经存在，直接开启猎鹰服务

                        long terminalId = queryTerminalResponse.getTid();
                        Log.e("轨迹", "terminalId已存在: " + terminalId);
                        SharedPreferences sharedPreferences = getSharedPreferences("track", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putLong("terminalId", terminalId);
                        editor.apply();
                        Log.e("轨迹终端id1",  sharedPreferences.getLong("terminalId",0)+ "");
                        long trackId = sharedPreferences.getLong("trackId", 0);
                        Log.e("轨迹track", "trackId已存在: " + trackId);
                        TrackParam trackParam = new TrackParam(serviceId, terminalId);
                        trackParam.setTrackId(trackId);
                        aMapTrackClient.startTrack(trackParam, onTrackLifecycleListener);
                        Log.e("轨迹", "trackId已存在: " + trackId);

                    }
                } else {
                    // 请求失败
                    Log.e("轨迹", "请求失败，" + queryTerminalResponse.getErrorMsg());
                    Toast.makeText(bottomnavi.this, "请求失败，" + queryTerminalResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

            }

            @Override
            public void onDistanceCallback(DistanceResponse distanceResponse) {

            }

            @Override
            public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

            }

            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

            }

            @Override
            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

            }

            @Override
            public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

            }

            @Override
            public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

            }
        });


        //final long trackId = sharedPreferences.getLong("trackId", 0);

        //下面是获取历史位置
        // 搜索最近12小时以内上报的属于某个轨迹的轨迹点信息，散点上报不会包含在该查询结果中

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long trackId = sharedPreferences.getLong("trackId", 0);
                com.amap.api.track.query.model.QueryTrackRequest queryTrackRequest = new com.amap.api.track.query.model.QueryTrackRequest(
                        serviceId,
                        terminalId,
                        trackId,	// 轨迹id，传-1表示查询所有轨迹
                        System.currentTimeMillis() - 5 * 60 * 1000,
                        System.currentTimeMillis(),
                        0,      // 不启用去噪
                        0,   // 绑路
                        0,      // 不进行精度过滤
                        com.amap.api.track.query.entity.DriveMode.DRIVING,  // 当前仅支持驾车模式
                        0,     // 距离补偿
                        100,   // 距离补偿，只有超过5km的点才启用距离补偿
                        1,  // 结果应该包含轨迹点信息
                        1,  // 返回第1页数据，由于未指定轨迹，分页将失效
                        300    // 一页不超过100条
                );
                aMapTrackClient.queryTerminalTrack(queryTrackRequest, new OnTrackListener() {

                    @Override
                    public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

                    }

                    @Override
                    public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

                    }

                    @Override
                    public void onDistanceCallback(DistanceResponse distanceResponse) {

                    }

                    @Override
                    public void onLatestPointCallback(LatestPointResponse latestPointResponse) {

                    }

                    @Override
                    public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

                    }

                    @Override
                    public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {
                        if (queryTrackResponse.isSuccess()) {

                            JsonArray jsonArray = new JsonArray();
                            List<com.amap.api.track.query.entity.Track> tracks =  queryTrackResponse.getTracks();
                            Log.e("轨迹", "查询成功"+terminalId+"  "+tracks.size());
                            //输出所有点的经纬度
                            for (int i = 0; i < tracks.size(); i++) {
                                List<com.amap.api.track.query.entity.Point> points = tracks.get(i).getPoints();
                                for (int j = 0; j < points.size(); j++) {
                                    Log.e("轨迹", "经度: " + points.get(j).getLng() + "纬度: " + points.get(j).getLat());
                                    Log.e("轨迹", "时间: " + points.get(j).getTime()+"  "+terminalId);
                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("x", points.get(j).getLng());
                                    jsonObject.addProperty("y", points.get(j).getLat());
                                    jsonObject.addProperty("times", points.get(j).getTime());
                                    jsonObject.addProperty("terminalId", terminalId);
                                    jsonArray.add(jsonObject);
                                }

                            }
                            Url_Request.sendRequestUserGetXY(Url_Request.getUrl_head()+"/user/getXY",jsonArray);

                            // 查询成功，tracks包含所有轨迹及相关轨迹点信息
                        } else {
                            // 查询失败
                            Log.e("轨迹", "查询失败，" + queryTrackResponse.getErrorMsg());
                        }
                    }

                    @Override
                    public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

                    }

                    @Override
                    public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

                    }
                });
            }
        }, 0,  60*1000); // 每分钟执行一次任务

        /*aMapTrackClient.queryLatestPoint(new LatestPointRequest(serviceId, terminalId), new OnTrackListener() {

            @Override
            public void onQueryTerminalCallback(QueryTerminalResponse queryTerminalResponse) {

            }

            @Override
            public void onCreateTerminalCallback(AddTerminalResponse addTerminalResponse) {

            }

            @Override
            public void onDistanceCallback(DistanceResponse distanceResponse) {

            }

            @Override
            public void onLatestPointCallback(LatestPointResponse latestPointResponse) {
                if (latestPointResponse.isSuccess()) {
                    Point point = latestPointResponse.getLatestPoint().getPoint();
                    // 查询实时位置成功，point为实时位置信息
                } else {
                    // 查询实时位置失败
                }
            }

            @Override
            public void onHistoryTrackCallback(HistoryTrackResponse historyTrackResponse) {

            }

            @Override
            public void onQueryTrackCallback(QueryTrackResponse queryTrackResponse) {

            }

            @Override
            public void onAddTrackCallback(AddTrackResponse addTrackResponse) {

            }

            @Override
            public void onParamErrorCallback(ParamErrorResponse paramErrorResponse) {

            }
        });*/







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

        //滑动切换fragmentpart1
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new DashboardFragment());
        fragmentList.add(new UserFragment());
        mcContainer.setAdapter(new AppFragmentPageAdapter(getSupportFragmentManager(),fragmentList));
        //滑动切换fragmentpart2
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


                                //滚动到item0

                                //切换到item1

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
                        //navController.navigate(R.id.navigation_home);
                        //设置颜色
                        //navView.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));

                        break;
                    case R.id.navigation_dashboard:
                        mcContainer.setCurrentItem(1);
                        //navController.navigate(R.id.navigation_dashboard);
                        break;
                    /*case R.id.navigation_notifications:
                        mcContainer.setCurrentItem(2);
                        break;*/
                    case R.id.navigation_user:
                        //navController.navigate(R.id.navigation_user);

                        //mcContainer.setCurrentItem(3);
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
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(2000);
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putBoolean("isPlay", true);
        editor.apply();
        //mLocationOption.
        //设置监听间隔，单位毫秒，默认为30000ms，最低10000ms。
        //mLocationOption.setGpsFirst(true);

//        Log.e("定位", "onCreate: " +loc.Longitude.toString()+loc.Latitude.toString() );
        /*timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // 在这里发送网络请求

                Url_Request.sendRequestUserSendLocation(Url_Request.getUrl_head()+"/user/sendLocation",user_token,loc.Longitude.toString(),loc.Latitude.toString());
                Log.e("发送位置",loc.Longitude.toString()+loc.Latitude.toString());
            }
        }, 0, 60 * 1000); // 每分钟执行一次任务*/

    }

}