package com.example.yiyoua13.ui.dashboard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNaviView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.example.yiyoua13.R;
import com.example.yiyoua13.TagAdapter;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.databinding.FragmentDashboardBinding;
import com.example.yiyoua13.ui.SpotActivity;
import com.example.yiyoua13.ui.TagActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {
    private SearchView searchView;
    private TextView search,navigate;
    private boolean state = false;


    private FragmentDashboardBinding binding;
    private MapView mapView= null;
    private EditText et;
    private Double x,y;
    private SharedPreferences sp;
    private List<String> spot,spot_id;
    private Map<String,String> name_id;
    private String id,user_token;
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
        AMap finalAMap = aMap;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state){
                    //定位到目的地
                    finalAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(y, x), 18));
                    //标记目的地
                    finalAMap.addMarker(new MarkerOptions().position(new LatLng(y, x)).title(searchView.getQuery().toString()));
                    //停止自我定位
                    finalAMap.setMyLocationEnabled(false);
                    //设置自我定位按钮可见
                    finalAMap.getUiSettings().setMyLocationButtonEnabled(true);
                }else{
                    Toast.makeText(getActivity(),"请先搜索",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        binding = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        /*if(locationClient!=null){
            locationClient.onDestroy();
        }*/
    }
    public void navigation(Context context, double slat, double slon, double dlat, double dlon, String from, String to, AmapNaviType type, String name) {
        Poi start = null;
        //如果设置了起点
        if (slat != 0 && slon != 0) {
            start = new Poi("我的位置", new LatLng(slat, slon), from);
        }
        Poi end = new Poi(name, new LatLng(dlat, dlon), to);
        AmapNaviParams params = new AmapNaviParams(start, null, end,type,null);//导航类型
        params.setUseInnerVoice(true);//使用内部语音播报
        params.setMultipleRouteNaviMode(true);//多路径
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);//退出导航后销毁导航对象


        //发起导航
        AmapNaviPage.getInstance().showRouteActivity(context, params, null);//启动导航
    }
    private void showTypeDialog(String name) {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_way, null);
        TextView walk = (TextView) view.findViewById(R.id.walk);
        TextView drive = (TextView) view.findViewById(R.id.drive);
        TextView ride = (TextView) view.findViewById(R.id.ride);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(getActivity(), bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),y,x,"","",AmapNaviType.WALK,name);
                dialog.dismiss();
            }
        });
        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(getActivity(),bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),y,x,"","",AmapNaviType.DRIVER,name);
                dialog.dismiss();
            }
        });
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(getActivity(),bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),y,x,"","",AmapNaviType.RIDE,name);
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }
    private void init() {


        spot=new ArrayList<String>();
        spot_id=new ArrayList<>();
        sp = getActivity().getSharedPreferences("Login", 0);
        user_token = sp.getString("TOKEN", "");


        search= binding.textSearch;

        navigate= binding.textNavigate;
        searchView = binding.searchNavi;
        searchView.setIconifiedByDefault(false);
        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state){
                    showTypeDialog(searchView.getQuery().toString());
                }else{
                    Toast.makeText(getActivity(),"请先搜索",Toast.LENGTH_SHORT).show();
                }

                //navigation(getActivity(),bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),39.908127,116.397428,"我的位置","北京市朝阳区阜通东大街6号",AmapNaviType.DRIVER,"");
            }
        });

        int[] ids = new int[20];




        et=binding.edSearch;


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getContext(), "请选择搜索或者导航", Toast.LENGTH_SHORT).show();
                Url_Request.sendRequestAttractionOfName(Url_Request.getUrl_head() + "/attraction/of/name", user_token, searchView.getQuery().toString(), "1", "10", bottomnavi.loc.getLongitude().toString(), bottomnavi.loc.getLatitude().toString(), new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Attraction> attractions = (List<Url_Request.Attraction>) bean;
                        String[] strings =  new String[attractions.size()] ;
                        for (int i = 0; i < attractions.size(); i++) {
                            spot.add(attractions.get(i).getName());
                            spot_id.add(attractions.get(i).getId().toString());
                        }
                        for (int i=0;i<spot.size();i++){
                            strings[i]=spot.get(i);
                            ids[i]=Integer.parseInt(spot_id.get(i));
                            Log.e("laile", "onBeanResponse: "+strings);
                        }

                        final BasePopupView popupView = new XPopup.Builder(getActivity())
                                .atView(searchView)
                                .isViewMode(true)      //开启View实现
                                .isRequestFocus(false) //不强制焦点
                                .isClickThrough(true)  //点击透传
                                .hasShadowBg(false)
                                .popupAnimation(PopupAnimation.ScaleAlphaFromLeftBottom)
                                .asAttachList(strings, null, new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        searchView.setQuery(spot.get(position), false);
                                        id=spot_id.get(position);
                                        x=Double.parseDouble(attractions.get(position).getX());
                                        y=Double.parseDouble(attractions.get(position).getY());
                                        state=true;
                                    }
                                });
                        popupView.show();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                state=false;
                return false;
            }


        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
            public void afterTextChanged(Editable s) {
                //输入后的监听
                Log.d("search", "afterTextChanged: "+s);
            }
        });*/




    }

}