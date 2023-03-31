package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.example.yiyoua13.ChoseTagAdapter;
import com.example.yiyoua13.FragAct;
import com.example.yiyoua13.MainActivity;
import com.example.yiyoua13.R;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.databinding.ActivityInterestBinding;
import com.example.yiyoua13.databinding.ActivitySearchBinding;
import com.example.yiyoua13.variousclass.ChoseItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InterestActivity extends AppCompatActivity {
    private ActivityInterestBinding binding;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ChoseTagAdapter adapter;
    private HashSet<String> mNameSet;
    private List<ChoseItem> list = new ArrayList<>();
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInterestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        initView();
        EventBus.getDefault().register(this);
        setContentView(view);

    }
    //初始化tag列表
    /*public void navigation(Context context, double slat, double slon, double dlat, double dlon) {
        Poi start = null;
        //如果设置了起点
        if (slat != 0 && slon != 0) {
            start = new Poi("起点名称", new LatLng(slat, slon), "");
        }
        Poi end = new Poi("终点名称", new LatLng(dlat, dlon), "");
        AmapNaviParams params = new AmapNaviParams(start, null, end, AmapNaviType.DRIVER);
        params.setUseInnerVoice(true);
        params.setMultipleRouteNaviMode(true);
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);
        //发起导航
        AmapNaviPage.getInstance().showRouteActivity(context, params, null);
    }*/


    private void initView() {
        recyclerView = binding.picChoseList;
        button = binding.picChoseBtn;


        //handleMessage(new ChoseTagAdapter.ChoseEvent());
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        adapter = new ChoseTagAdapter(this,buildData());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InterestActivity.this, "你选择了" + mNameSet.toString(), Toast.LENGTH_SHORT).show();
                //navigation(InterestActivity.this,0,0,0,0);
                Intent intent = new Intent(InterestActivity.this, bottomnavi.class);
                startActivity(intent);
            }
        });


    }
    @Subscribe(threadMode = ThreadMode.MAIN)

    public void handleMessage(ChoseTagAdapter.ChoseEvent event) {
        mNameSet= new HashSet<>();

        mNameSet = event.nameSet;

        if (mNameSet.size() != 0) {
            button.setEnabled(true);
            button.setTextColor(Color.WHITE);
            button.setBackground(getResources().getDrawable(R.drawable.selector_orange));
        } else {
            button.setEnabled(false);
            button.setTextColor(Color.parseColor("#4a4a4a"));
            button.setBackground(getResources().getDrawable(R.drawable.selector_grey));
        }
    }
    private List <ChoseItem> buildData(){
        List<ChoseItem> list = new ArrayList<>();
        String[] tags = {"超级无敌", "浙江理工大学", "计算机科学与技术", "动物", "wolaaaaaa", "美食", "游戏", "爱情", "网红", "明星"};
        for (int i = 0; i < tags.length; i++) {
            ChoseItem item = new ChoseItem(tags[i], "1",false);
            list.add(item);
        }
        return list;
    }
    public void onDestory(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        binding = null;
    }
}