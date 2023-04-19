package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.R;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.ActivityPcenterBinding;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class PCenter_Activity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPcenterBinding binding;
    private ImageView back;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private LinearLayout ln_jump2info;
    private int pagenum = 1;
    private SharedPreferences sp;
    private String user_token,user_id,id;
    private TextView follow;
    private CircleImageView head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPcenterBinding.inflate(getLayoutInflater());
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
    private void init() {
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mRecyclerView= binding.pcRecyclerView;
        back = binding.pcBack;
        back.setOnClickListener(this);
        refreshLayout = binding.pcRefreshLayout;
        ln_jump2info = binding.pcSpace;
        follow = binding.ifFollow;
        head = binding.psTx;
        ln_jump2info.setOnClickListener(this);
        follow.setOnClickListener(this);

        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        //binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新
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
        //设置布局管理器
        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        mAdapter = new WaterFallAdapter(this,buildData());
        mRecyclerView.setAdapter(mAdapter);
        Url_Request.sendRequestFollowOrNot(Url_Request.getUrl_head() + "/follow/or/not/" + id, user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Boolean isfollow = (Boolean) bean;
                PCenter_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isfollow) {
                            follow.setText("已关注");
                        } else {
                            follow.setText("关注");
                        }
                    }
                });
            }
        });
        Url_Request.sendRequestBasicinfo_id(Url_Request.getUrl_head() + "/user/"+id, user_token,  new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Url_Request.User_info user_basicinfo = (Url_Request.User_info) bean;
                PCenter_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Glide.with(PCenter_Activity.this).load(user_basicinfo.getIcon()).into(head);
                    }
                });
            }
        });
        Url_Request.sendRequestinfo_id(Url_Request.getUrl_head() + "/user/info", user_token, id, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Url_Request.User_info user_info = (Url_Request.User_info) bean;
                PCenter_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.name.setText(user_info.getUserName());
                        binding.fansnum.setText(user_info.getFans());
                        binding.follownum.setText(user_info.getFollowee());
                    }
                });
            }
        });
    }
    private List<WaterFallAdapter.PersonCard> buildData() {
        List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
        Url_Request.sendRequestBlogOfUser(Url_Request.getUrl_head()+"/blog/of/user", user_token,"1","10",id,new Url_Request.OnIconResponseListener() {
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
                PCenter_Activity.this.runOnUiThread(new Runnable() {
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
        Url_Request.sendRequestBlogOfUser(Url_Request.getUrl_head()+"/blog/of/user", user_token,String.valueOf(pagenum),"10",id,new Url_Request.OnIconResponseListener() {
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
                PCenter_Activity.this.runOnUiThread(new Runnable() {
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pc_space:
                /*Intent intent = new Intent(this, FansFollowActivity.class);
                startActivity(intent);*/
                break;
            case R.id.if_follow:
                if (Objects.equals(user_id.toString(), id.toString())){
                    Toast.makeText(this,"不能关注自己哦~",Toast.LENGTH_SHORT).show();
                    return;
                }
                Url_Request.sendRequestFollowFollowed(Url_Request.getUrl_head()+"/follow/followed/"+id,user_token);
                if (follow.getText()=="已关注"){
                    follow.setText("关注");
                }else {
                    follow.setText("已关注");
                }
                break;
            case R.id.pc_back:
                onBackPressed();
                break;

        }
    }
}