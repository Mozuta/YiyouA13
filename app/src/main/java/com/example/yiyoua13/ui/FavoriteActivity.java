package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.SpotAdapter;
import com.example.yiyoua13.databinding.ActivityFavoriteBinding;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.example.yiyoua13.variousclass.Spot;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityFavoriteBinding binding;
    private SharedPreferences sp;
    private String user_token,user_id;
    private SmartRefreshLayout refreshLayout;
    private int pagenum = 1;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SpotAdapter mAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        init();
    }
    private void init(){
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        back = binding.favBack;
        back.setOnClickListener(this);
        mRecyclerView = binding.favRecycler;
        refreshLayout = binding.favRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        refreshLayout.setOnRefreshListener(refresh -> {
            pagenum=mAdapter.clearData();
            loadData();
            refresh.finishRefresh();//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refresh -> {


            loadData();

            refresh.finishLoadMore();//传入false表示刷新失败
        });
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SpotAdapter(this, buildData());
        mRecyclerView.setAdapter(mAdapter);
    }
    private void loadData(){
        Url_Request.sendRequestCollection(Url_Request.getUrl_head() + "/user/collection/" + pagenum, user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                List<Spot> list = new ArrayList<>();
                for (int i = 0; i < attraction.size(); i++) {
                    Spot spot = new Spot();
                    spot.setId(String.valueOf(attraction.get(i).getId()));
                    Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                    spot.setName(attraction.get(i).getName());
                    spot.setAddress(attraction.get(i).getArea());
                    spot.setDescription("现在有" + String.valueOf(attraction.get(i).getBlogs()) + "人正在热评~");
                    spot.setImage(attraction.get(i).getImagesList().get(0));
                    if (attraction.get(i).getPrice() == 0)
                        spot.setPrice("免费");
                    else
                        spot.setPrice(String.valueOf(attraction.get(i).getPrice())+"元起");
                    if (attraction.get(i).getScore() == 0)
                        spot.setStars("暂无评分");
                    else
                        spot.setStars(String.format("%.1f", Double.parseDouble(String.valueOf(attraction.get(i).getScore())) / 10));
                    if (attraction.get(i).getDistance() > 1000)
                        spot.setDistance(String.valueOf(attraction.get(i).getDistance() / 1000) + "km");
                    else
                        spot.setDistance(String.valueOf(attraction.get(i).getDistance()) + "m");
                    spot.setKind(attraction.get(i).getType());
                    list.add(spot);
                }
                FavoriteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(list);
                    }
                });

            }
        });
        pagenum++;
    }
    private List<Spot> buildData (){
        List<Spot> list = new ArrayList<>();
        Url_Request.sendRequestCollection(Url_Request.getUrl_head() + "/user/collection/" + "1", user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                for (int i = 0; i < attraction.size(); i++) {
                    Spot spot = new Spot();
                    spot.setId(String.valueOf(attraction.get(i).getId()));
                    Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                    spot.setName(attraction.get(i).getName());
                    spot.setAddress(attraction.get(i).getArea());
                    spot.setDescription("现在有"+String.valueOf(attraction.get(i).getBlogs())+"人正在热评~");
                    spot.setImage(attraction.get(i).getImagesList().get(0).toString());
                    if (attraction.get(i).getPrice() == 0)
                        spot.setPrice("免费");
                    else
                        spot.setPrice(String.valueOf(attraction.get(i).getPrice())+"元起");
                    if (attraction.get(i).getScore() == 0)
                        spot.setStars("暂无评分");
                    else
                        spot.setStars(String.format("%.1f", Double.parseDouble(String.valueOf(attraction.get(i).getScore())) / 10));
                    if (attraction.get(i).getDistance() > 1000)
                        spot.setDistance(String.valueOf(attraction.get(i).getDistance() / 1000) + "km");
                    else
                        spot.setDistance(String.valueOf(attraction.get(i).getDistance()) + "m");
                    spot.setKind(attraction.get(i).getType());
                    list.add(spot);
                }
                FavoriteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        pagenum=2;


        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fav_back:
                finish();
                break;
        }
    }
}