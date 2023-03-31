package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.example.yiyoua13.R;
import com.example.yiyoua13.TestActivity;
import com.example.yiyoua13.WaterFallAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpotActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private SmartRefreshLayout refreshLayout;
    private WaterFallAdapter mAdapter;
    private ImageView map;

    private Integer[] list = {R.drawable.hog, R.drawable.lwr, R.drawable.light, R.drawable.ic_launcher_background, R.drawable.hog};
    private MZBannerView<Integer> mMZBanner;
    private androidx.appcompat.widget.Toolbar toolbar;
    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @SuppressLint("MissingInflatedId")
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_lidt, null);
            mImageView = (ImageView) view.findViewById(R.id.spot_detail_page_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
        init();
    }
    public void navigation(Context context, double slat, double slon, double dlat, double dlon, String from, String to, AmapNaviType type) {
        Poi start = null;
        //如果设置了起点
        if (slat != 0 && slon != 0) {
            start = new Poi("起点名称", new LatLng(slat, slon), from);
        }
        Poi end = new Poi("终点名称", new LatLng(dlat, dlon), to);
        AmapNaviParams params = new AmapNaviParams(start, null, end,type,null);//导航类型
        params.setUseInnerVoice(true);//使用内部语音播报
        params.setMultipleRouteNaviMode(true);//多路径
        params.setNeedDestroyDriveManagerInstanceWhenNaviExit(true);//退出导航后销毁导航对象


        //发起导航
        AmapNaviPage.getInstance().showRouteActivity(context, params, null);//启动导航
    }
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_way, null);
        TextView walk = (TextView) view.findViewById(R.id.walk);
        TextView drive = (TextView) view.findViewById(R.id.drive);
        TextView ride = (TextView) view.findViewById(R.id.ride);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(SpotActivity.this,0,0,0,0,"","",AmapNaviType.WALK);
                dialog.dismiss();
            }
        });
        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(SpotActivity.this,0,0,0,0,"","",AmapNaviType.DRIVER);
                dialog.dismiss();
            }
        });
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(SpotActivity.this,0,0,0,0,"","",AmapNaviType.RIDE);
                dialog.dismiss();
            }
        });

        dialog.setView(view);
        dialog.show();
    }
    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String name = data.getStringExtra("spot");
            textView.setText(name);
            Toast.makeText(this, "done!", Toast.LENGTH_SHORT).show();
        }
    }*/
    public void init(){
        map = findViewById(R.id.spot_map);
        map.setOnClickListener(v -> {
            showTypeDialog();
        });
        toolbar = findViewById(R.id.spot_toolbar);
        mRecyclerView = findViewById(R.id.spot_detail_page_recycler_view);

        refreshLayout = findViewById(R.id.spot_detail_page_refresh_layout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //刷新数据
            mAdapter = new WaterFallAdapter(this, buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示刷新失败
        });

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WaterFallAdapter(this, buildData());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);


        textView = findViewById(R.id.name);
        String name = getIntent().getStringExtra("spot");
        textView.setText(name);

        setSupportActionBar(toolbar);
        //返回键返回上一级
        toolbar.setNavigationOnClickListener(v -> finish());
        //设置返回键可用


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.spot_collapsing_toolbar);
        collapsingToolbar.setTitle("详情");
        mMZBanner = findViewById(R.id.spot_detail_page_image);
        mMZBanner.setCanLoop(false);
        mMZBanner.setPages(Arrays.asList(list), new MZHolderCreator<TestActivity.BannerViewHolder>() {
            @Override
            public TestActivity.BannerViewHolder createViewHolder() {
                return new TestActivity.BannerViewHolder();
            }
        });
    }
    private List<WaterFallAdapter.PersonCard> buildData() {

        String[] names = {"拉格纳罗斯","甘雨","伦纳德","nox","盖伦","陈博晟"};
        String[] contents = {"a","b","c5641561654564564","d","e","f"};
        String[] likes = {"99+","2","3","4","5","6"};

        String[] imgUrs = {"https://i.postimg.cc/x1HHh4C7/1.png","","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg",

        };
        String[] headUrs = {
                "https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg",

        };

        List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
        for(int i=0;i<6;i++) {
            WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();

            p.avatarUrl = imgUrs[i];
            p.name = names[i];
            p.content = contents[i];
            p.like = likes[i];
            p.headurl = headUrs[i];
            p.imgHeight = 500;//(i % 2)*100 + 400; //偶数和奇数的图片设置不同的高度，以到达错开的目的
            list.add(p);
        }

        return list;
    }
}