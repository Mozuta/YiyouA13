package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.donkingliang.imageselector.utils.StringUtils;
import com.example.yiyoua13.ChoseTagAdapter;
import com.example.yiyoua13.FragAct;
import com.example.yiyoua13.MainActivity;
import com.example.yiyoua13.R;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.databinding.ActivityInterestBinding;
import com.example.yiyoua13.utils.StatusBarUtil;
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
    private List<String> list = new ArrayList<>();
    private Button button;
    private SharedPreferences sp;
    private String user_token,user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInterestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
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
        /*for (int i=0;i<mNameSet.size();i++){
            list.add((ChoseItem) mNameSet.toArray()[i]);
        }*/


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mNameSet.toString();
                str = str.substring(1, str.length()-1);
                str=str.replace(" ","");
                //System.out.println(str); // 输出: Hello World

                Toast.makeText(InterestActivity.this, "你选择了" + str, Toast.LENGTH_SHORT).show();
                Url_Request.sendRequestUserSetPreference(Url_Request.getUrl_head()+"/user/SetPreference",user_token,str);
                //navigation(InterestActivity.this,0,0,0,0);
                Intent intent = new Intent(InterestActivity.this, bottomnavi.class);
                startActivity(intent);
                SharedPreferences sp2 = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp2.edit();
                editor.putString("if_tag","true");
                finish();

            }
        });


    }
    @Subscribe(threadMode = ThreadMode.MAIN)

    public void handleMessage(ChoseTagAdapter.ChoseEvent event) {


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
        Url_Request.sendRequestAttraction_TypeSmall_List(Url_Request.getUrl_head() + "/attraction-type/small_list", user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.SKind> attraction_typeSmall_lists = (List<Url_Request.SKind>) bean;
                for (Url_Request.SKind attraction_typeSmall_list : attraction_typeSmall_lists) {
                    ChoseItem item = new ChoseItem(attraction_typeSmall_list.getName(), attraction_typeSmall_list.getId().toString(),false);
                    list.add(item);
                }
                InterestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return list;
    }
    public void onDestory(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        binding = null;
    }
}