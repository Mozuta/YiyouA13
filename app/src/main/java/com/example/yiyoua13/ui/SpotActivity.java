package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.example.yiyoua13.FragAct;
import com.example.yiyoua13.R;
import com.example.yiyoua13.TestActivity;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.task.RatingBarLoaderTask;
import com.example.yiyoua13.task.TextLoaderTask;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class SpotActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private Double x,y;
    private TextView textView8;
    private MaterialRatingBar materialRatingBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences sp;
    private int pagenum = 1;
    private ImageView imageView;
    private LikeButton likeButton;

    private SmartRefreshLayout refreshLayout;
    private WaterFallAdapter mAdapter;
    private ImageView map;
    private String id,user_token;

    private Integer[] list = {R.drawable.hog, R.drawable.lwr, R.drawable.light, R.drawable.ic_launcher_background, R.drawable.hog};
    private List<String> list2 = new ArrayList<String>();
    private MZBannerView<String> mMZBanner;
    private androidx.appcompat.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_way, null);
        TextView walk = (TextView) view.findViewById(R.id.walk);
        TextView drive = (TextView) view.findViewById(R.id.drive);
        TextView ride = (TextView) view.findViewById(R.id.ride);
        walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(SpotActivity.this, bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),y,x,"","",AmapNaviType.WALK,name);
                dialog.dismiss();
            }
        });
        drive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(SpotActivity.this,bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),y,x,"","",AmapNaviType.DRIVER,name);
                dialog.dismiss();
            }
        });
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigation(SpotActivity.this,bottomnavi.loc.getLatitude(),bottomnavi.loc.getLongitude(),y,x,"","",AmapNaviType.RIDE,name);
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
            showTypeDialog(textView.getText().toString());
        });



        textView = findViewById(R.id.gt_name);
        textView2 = findViewById(R.id.gt_rating);
        textView3 = findViewById(R.id.gt_price);
        textView4 = findViewById(R.id.gt_kind);
        textView5 = findViewById(R.id.gt_area);
        textView6 = findViewById(R.id.gt_distance);
        textView7 = findViewById(R.id.intro_gt);
        textView8 = findViewById(R.id.spot_title);
        imageView = findViewById(R.id.gt_icon);
        //String name = getIntent().getStringExtra("spot");
        id = getIntent().getStringExtra("id");
        String distance = getIntent().getStringExtra("distance");
        String blog = getIntent().getStringExtra("blog");
        if (id == null){
            Log.d("jieshou","null!");
        }
        else {
            Log.d("jieshou",id);
        }
        if (distance == null){
            Log.d("jieshou","null!");
        }
        else {
            Log.d("jieshou",distance);
        }
        if (blog == null){
            Log.d("jieshou","null!");
        }
        else {
            Log.d("jieshou",blog);
        }

        //Log.d("id",id);
        sp = SpotActivity.this.getSharedPreferences("Login", 0);
         user_token = sp.getString("TOKEN", "");
        likeButton = findViewById(R.id.detail_page_follow);

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (user_token.equals("")){
                    Toast.makeText(SpotActivity.this,"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SpotActivity.this, FragAct.class);
                    startActivity(intent);
                }
                else {
                    Url_Request.sendRequestUserCollected(Url_Request.getUrl_head()+"/user/Collected",user_token,id);
                    Toast.makeText(SpotActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (user_token.equals("")){
                    Toast.makeText(SpotActivity.this,"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SpotActivity.this, FragAct.class);
                    startActivity(intent);
                }
                else {
                    Url_Request.sendRequestUserCollected(Url_Request.getUrl_head()+"/user/Collected",user_token,id);
                    Toast.makeText(SpotActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                }

            }
        });
        toolbar = findViewById(R.id.spot_toolbar);
        mRecyclerView = findViewById(R.id.spot_detail_page_recycler_view);

        refreshLayout = findViewById(R.id.spot_detail_page_refresh_layout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
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
        mAdapter = new WaterFallAdapter(this, buildData());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        materialRatingBar = findViewById(R.id.gt_materialRatingBar);
        Url_Request.sendRequestQueryById(Url_Request.getUrl_head() + "/attraction/queryById", user_token, id, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Url_Request.Attraction attraction = (Url_Request.Attraction) bean;
                new TextLoaderTask(textView,attraction.getName()).execute();
                if (attraction.getScore() == 0){
                    new TextLoaderTask(textView2,"暂无评分").execute();
                    new RatingBarLoaderTask(materialRatingBar,"0").execute();
                } else{
                    new TextLoaderTask(textView2,String.format("%.1f", Double.parseDouble(String.valueOf(attraction.getScore())) / 10)).execute();
                    new RatingBarLoaderTask(materialRatingBar,String.format("%.1f", Double.parseDouble(String.valueOf(attraction.getScore())) / 10)).execute();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        x=Double.parseDouble(attraction.getX());
                        y=Double.parseDouble(attraction.getY());
                        for (int i = 0; i < attraction.getImagesList().size(); i++){

                            list2.add(attraction.getImagesList().get(i));
                            Log.d("list2",list2.get(i));
                            setSupportActionBar(toolbar);
                            //返回键返回上一级
                            toolbar.setNavigationOnClickListener(v -> finish());
                            //设置返回键可用


                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                            CollapsingToolbarLayout collapsingToolbar =
                                    (CollapsingToolbarLayout) findViewById(R.id.spot_collapsing_toolbar);
                            collapsingToolbar.setTitle(attraction.getName());
                            mMZBanner = findViewById(R.id.spot_detail_page_image);



                            //加入url图片




                            mMZBanner.setPages((list2), new MZHolderCreator<TestActivity.BannerViewHolder>() {
                                @Override
                                public TestActivity.BannerViewHolder createViewHolder() {
                                    return new TestActivity.BannerViewHolder();
                                }
                            });


                        }


                        Glide.with(SpotActivity.this).load(attraction.getImagesList().get(0)).into(imageView);
                    }
                });
                new TextLoaderTask(textView3,attraction.getPrice().toString()+"元起").execute();
                new TextLoaderTask(textView4,attraction.getType()).execute();
                new TextLoaderTask(textView5,attraction.getArea()).execute();

                new TextLoaderTask(textView6,distance).execute();
                new TextLoaderTask(textView7,blog).execute();
                new TextLoaderTask(textView8,attraction.getAddress()).execute();




            }
        });
        if(user_token.equals("")){
            likeButton.setLiked(false);
        }
        else {
            Url_Request.sendRequestUserIsCollection(Url_Request.getUrl_head()+"/user/isCollection",user_token,id, new Url_Request.OnIconResponseListener() {
                @Override
                public void onBeanResponse(Object bean) {
                    SpotActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("点击了",bean.toString()+id);
                            Boolean userIsCollection = (Boolean) bean;
                            if (userIsCollection){
                                likeButton.setLiked(true);
                            }
                            else {
                                likeButton.setLiked(false);
                            }
                        }
                    });

                }
            });
        }



    }
    private List<WaterFallAdapter.PersonCard> buildData() {



        List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
        Url_Request.sendRequestBlogFrom_Attraction(Url_Request.getUrl_head()+"/blog/from_Attraction",user_token,id,"1" ,"10",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Log.e("啊获取了", "onBeanResponse: "+pagenum);
                List<Url_Request.Note> blogHots = (List<Url_Request.Note>) bean;
                for (Url_Request.Note note : blogHots) {
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0).toString();
                    p.headurl = note.getIcon();
                    if (note.getIslike()!=null){
                        p.islike = note.getIslike();
                    }
                    else{
                        p.islike = false;

                    }

                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                SpotActivity.this.runOnUiThread(new Runnable() {
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
        Url_Request.sendRequestBlogFrom_Attraction(Url_Request.getUrl_head()+"/blog/from_Attraction",user_token,id,String.valueOf(pagenum) ,"10",new Url_Request.OnIconResponseListener() {

            @Override
            public void onBeanResponse(Object bean) {
                List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
                List<Url_Request.Note> blogHots = (List<Url_Request.Note>) bean;
                for (Url_Request.Note note : blogHots) {
                    Log.e("获取了", "onBeanResponse: "+pagenum);
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0);
                    p.headurl = note.getIcon();
                    if (note.getIslike()!=null){
                        p.islike = note.getIslike();
                    }
                    else{
                        p.islike = false;
                    }
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                SpotActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(list);
                    }
                });
            }
        });
        pagenum++;
    }

}