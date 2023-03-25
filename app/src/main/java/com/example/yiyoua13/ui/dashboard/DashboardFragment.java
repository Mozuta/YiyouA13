package com.example.yiyoua13.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.example.yiyoua13.R;
import com.example.yiyoua13.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MapView mapView= null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MapsInitializer.updatePrivacyShow(getActivity(),true,true);
        MapsInitializer.updatePrivacyAgree(getActivity(),true);
        mapView = binding.map1;
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        init();


        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NAVI);// 卫星地图模式


        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        /*if(locationClient!=null){
            locationClient.onDestroy();
        }*/
    }
    private void init() {
        //初始化定位
        try {
            mLocationClient = new AMapLocationClient(getActivity());
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
    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
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
}