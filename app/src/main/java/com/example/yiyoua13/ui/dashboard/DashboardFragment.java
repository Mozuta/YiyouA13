package com.example.yiyoua13.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.example.yiyoua13.R;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {
    private SearchView searchView;


    private FragmentDashboardBinding binding;
    private MapView mapView= null;
    //声明AMapLocationClient类对象
    /*public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;*/
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;


    /*@RequiresApi(api = Build.VERSION_CODES.M)
    void Request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case INTERNET:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO request success
                }
                break;
        }
    }*/
    public void navigation(Context context, double slat, double slon, double dlat, double dlon, String from, String to) {
        Poi start = null;
        //如果设置了起点
        if (slat != 0 && slon != 0) {
            start = new Poi("起点名称", new LatLng(slat, slon), from);
        }
        Poi end = new Poi("终点名称", new LatLng(dlat, dlon), to);
        AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER,null);//导航类型
        params.setUseInnerVoice(true);//使用内部语音播报
        params.setMultipleRouteNaviMode(true);//多路径
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);//退出导航后销毁导航对象


        //发起导航
        AmapNaviPage.getInstance().showRouteActivity(context, params, null);//启动导航
    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION), 100)
        MapsInitializer.updatePrivacyShow(getActivity(),true,true);
        MapsInitializer.updatePrivacyAgree(getActivity(),true);
        mapView = binding.map1;
        mapView.onCreate(savedInstanceState);// 此方法必须重写


        //获取当前位置经纬度


        AMap aMap = mapView.getMap();
        if (aMap == null) {
            aMap = mapView.getMap();
        }



        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        aMap.getUiSettings().setZoomControlsEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        init();

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
        searchView = binding.searchNavi;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //点击搜索按钮时触发
                Log.d("search", "onQueryTextSubmit: "+query);
                Toast.makeText(getActivity(), bottomnavi.loc.getLatitude().toString()+bottomnavi.loc.getLongitude().toString(), Toast.LENGTH_SHORT).show();
                navigation(getActivity(),bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),39.904556,116.427231,"here","there");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入框内容改变时触发
                Log.d("search", "onQueryTextChange: "+newText);
                return false;
            }
        });
        /*//初始化定位
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
        mLocationClient.startLocation();*/



    }
    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //Longitude = (aMapLocation.getLongitude());
                    //Latitude = (aMapLocation.getLatitude());
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