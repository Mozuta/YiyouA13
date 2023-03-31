package com.example.yiyoua13;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.yiyoua13.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yiyoua13.databinding.ActivityBottomnaviBinding;

public class bottomnavi extends AppCompatActivity {

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


    private ActivityBottomnaviBinding binding;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    public AMapLocationClient mLocationClient = null;
    public static location loc = new location();

    //create button to jump to FragAct.java


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBottomnaviBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_user)
                .build();
        //press navigation_comment to jump to the FragAct.java

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottomnavi);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        try {
            mLocationClient = new AMapLocationClient(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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