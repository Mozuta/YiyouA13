package com.example.yiyoua13.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.example.yiyoua13.R;
import com.example.yiyoua13.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private MapView mapView= null;



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

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式


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
}