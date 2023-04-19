package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.InsideAdapter;
import com.example.yiyoua13.R;
import com.example.yiyoua13.RecommendAdapter;
import com.example.yiyoua13.task.ImageLoaderTask;
import com.example.yiyoua13.task.TextLoaderTask;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.example.yiyoua13.variousclass.Inside;
import com.example.yiyoua13.variousclass.Recommend;

import java.util.ArrayList;
import java.util.List;

public class RecommendActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Recommend> recommendList=new ArrayList<>();
    private SharedPreferences sp;
    private String user_token,user_id;
    private ImageView rcm_1,rcm_2,rcm_3,rcm_4,rcm_5;
    private TextView rcm_1_name,rcm_2_name,rcm_3_name,rcm_4_name,rcm_5_name;
    private List<Url_Request.Attraction> attractionList=new ArrayList<>();
    private List<Url_Request.Route> routeList=new ArrayList<>();
    private RecommendAdapter adapter;

    //ondestroy


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView recyclerView=(RecyclerView) findViewById(R.id.parent_recycler_view);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        init();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new RecommendAdapter(recommendList,this, R.layout.item_likt );
        recyclerView.setAdapter(adapter);
    }
    public void init(){
        rcm_1=findViewById(R.id.rcm_img1);
        rcm_2=findViewById(R.id.rcm_img2);
        rcm_3=findViewById(R.id.rcm_img3);
        rcm_4=findViewById(R.id.rcm_img4);
        rcm_5=findViewById(R.id.rcm_img5);
        rcm_1.setOnClickListener(this);
        rcm_2.setOnClickListener(this);
        rcm_3.setOnClickListener(this);
        rcm_4.setOnClickListener(this);
        rcm_5.setOnClickListener(this);

        rcm_1_name=findViewById(R.id.rad_text1);
        rcm_2_name=findViewById(R.id.rad_text2);
        rcm_3_name=findViewById(R.id.rad_text3);
        rcm_4_name=findViewById(R.id.rad_text4);
        rcm_5_name=findViewById(R.id.rad_text5);
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        Url_Request.sendRequestModelRunDistinguish(Url_Request.getUrl_head() + "/model/runDistinguish", user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Url_Request.Model model = (Url_Request.Model) bean;
                attractionList = model.getAttractionlist();
                routeList = model.getRoutelist();
                for (int i=0;i<routeList.size();i++){
                    Recommend recommend = new Recommend();
                    recommend.setId(routeList.get(i).getId().toString());
                    recommend.setTitle(routeList.get(i).getRoutename());
                    recommend.setContent(routeList.get(i).getDescribe());
                    List<Inside> insideList=new ArrayList<>();
                    for (int j=0;j<routeList.get(i).getAttractionlist().size();j++){
                        Inside inside=new Inside();
                        inside.setId(routeList.get(i).getAttractionlist().get(j).getId().toString());
                        inside.setName(routeList.get(i).getAttractionlist().get(j).getName());
                        String newStr = routeList.get(i).getAttractionlist().get(j).getScore().toString().substring(0, 1) + "." + routeList.get(i).getAttractionlist().get(j).getScore().toString().substring(1);
                        inside.setRating(newStr);
                        if (routeList.get(i).getAttractionlist().get(j).getPrice()==0){
                            inside.setPrice("免费");
                        }else{
                            inside.setPrice(routeList.get(i).getAttractionlist().get(j).getPrice().toString()+"元");
                        }

                        inside.setTag(routeList.get(i).getAttractionlist().get(j).getType());
                        inside.setImage(routeList.get(i).getAttractionlist().get(j).getImagesList().get(0));
                        insideList.add(inside);
                    }
                    InsideAdapter insideAdapter=new InsideAdapter(insideList,R.layout.item_lilt,RecommendActivity.this);
                    recommend.setInsideAdapter(insideAdapter);
                    recommendList.add(recommend);


                }
                RecommendActivity.this.runOnUiThread(new Runnable() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void run() {
                        Glide.with(RecommendActivity.this).load(attractionList.get(0).getImagesList().get(0)).into(rcm_1);
                        Glide.with(RecommendActivity.this).load(attractionList.get(1).getImagesList().get(0)).into(rcm_2);
                        Glide.with(RecommendActivity.this).load(attractionList.get(2).getImagesList().get(0)).into(rcm_3);
                        Glide.with(RecommendActivity.this).load(attractionList.get(3).getImagesList().get(0)).into(rcm_4);
                        Glide.with(RecommendActivity.this).load(attractionList.get(4).getImagesList().get(0)).into(rcm_5);
                        new TextLoaderTask(rcm_1_name,attractionList.get(0).getName()).execute();
                        new TextLoaderTask(rcm_2_name,attractionList.get(1).getName()).execute();
                        new TextLoaderTask(rcm_3_name,attractionList.get(2).getName()).execute();
                        new TextLoaderTask(rcm_4_name,attractionList.get(3).getName()).execute();
                        new TextLoaderTask(rcm_5_name,attractionList.get(4).getName()).execute();
                        adapter.notifyDataSetChanged();

                    }
                });



            }
        });




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rcm_img1:
                Url_Request.sendRequestRecommendSelectAttraction(Url_Request.getUrl_head()+"/recommend/selectAttraction",user_token,attractionList.get(0).getId().toString());
                Intent intent1=new Intent(RecommendActivity.this, SpotActivity.class);
                intent1.putExtra("id",attractionList.get(0).getId().toString());
                startActivity(intent1);
                break;
            case R.id.rcm_img2:
                Url_Request.sendRequestRecommendSelectAttraction(Url_Request.getUrl_head()+"/recommend/selectAttraction",user_token,attractionList.get(1).getId().toString());
                Intent intent2=new Intent(RecommendActivity.this, SpotActivity.class);
                intent2.putExtra("id",attractionList.get(1).getId().toString());
                startActivity(intent2);
                break;
            case R.id.rcm_img3:
                Url_Request.sendRequestRecommendSelectAttraction(Url_Request.getUrl_head()+"/recommend/selectAttraction",user_token,attractionList.get(2).getId().toString());
                Intent intent3=new Intent(RecommendActivity.this, SpotActivity.class);
                intent3.putExtra("id",attractionList.get(2).getId().toString());
                startActivity(intent3);
                break;
            case R.id.rcm_img4:
                Url_Request.sendRequestRecommendSelectAttraction(Url_Request.getUrl_head()+"/recommend/selectAttraction",user_token,attractionList.get(3).getId().toString());
                Intent intent4=new Intent(RecommendActivity.this, SpotActivity.class);
                intent4.putExtra("id",attractionList.get(3).getId().toString());
                startActivity(intent4);
                break;
            case R.id.rcm_img5:
                Url_Request.sendRequestRecommendSelectAttraction(Url_Request.getUrl_head()+"/recommend/selectAttraction",user_token,attractionList.get(4).getId().toString());
                Intent intent5=new Intent(RecommendActivity.this, SpotActivity.class);
                intent5.putExtra("id",attractionList.get(4).getId().toString());
                startActivity(intent5);
                break;
        }
    }
}

