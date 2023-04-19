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
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.ActivityFavoriteBinding;
import com.example.yiyoua13.databinding.ActivityMineBinding;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

public class MineActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMineBinding binding;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private ImageView back;
    private int pagenum = 1;
    private SharedPreferences sp;
    private String user_token,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMineBinding.inflate(getLayoutInflater());
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
        sp = MineActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        back = binding.mineBack;
        back.setOnClickListener(this);
        mRecyclerView = binding.mineRecycler;
        refreshLayout = binding.mineRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            //刷新数据
            pagenum=mAdapter.clearData();
            loadData();
            refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载数据
            loadData();
            refreshLayout.finishLoadMore(/*,false*/);//传入false表示加载失败
        });
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WaterFallAdapter(this, buildData());
        mRecyclerView.setAdapter(mAdapter);
    }
    private List<WaterFallAdapter.PersonCard> buildData() {
        List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
        Url_Request.sendRequestBlogOfMe(Url_Request.getUrl_head()+"/blog/of/me", user_token,"1","10",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Note> blogHots = (List<Url_Request.Note>) bean;
                for (Url_Request.Note note : blogHots) {
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0).toString();
                    p.headurl = note.getIcon();
                    p.islike = note.getIslike();
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                MineActivity.this.runOnUiThread(new Runnable() {
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
    private void loadData(){
        Url_Request.sendRequestBlogOfMe(Url_Request.getUrl_head()+"/blog/of/me", user_token,String.valueOf(pagenum),"10",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
                List<Url_Request.Note> blogHots = (List<Url_Request.Note>) bean;
                for (Url_Request.Note note : blogHots) {
                    Log.d("TAG", "onBeanResponse: "+pagenum);
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0);
                    p.headurl = note.getIcon();
                    p.islike = note.getIslike();
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                MineActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(list);
                    }
                });
            }
        });
        pagenum++;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_back:
                finish();
                break;
        }
    }
}