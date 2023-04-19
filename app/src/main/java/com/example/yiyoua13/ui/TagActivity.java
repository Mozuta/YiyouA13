package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.TagAdapter;
import com.example.yiyoua13.VSAdapter;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity implements View.OnClickListener{
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<TagAdapter.tag> list = new ArrayList<>();
    private SharedPreferences sp;
    private String user_token,user_id;
    private TagAdapter mAdapter;

    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
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
    public void init(){
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        mRecyclerView = findViewById(R.id.tag_recycler_view);
        cancel = findViewById(R.id.tag_cancel);
        cancel.setOnClickListener(this);
        searchView = findViewById(R.id.tag_search_view);
        searchView.setIconified(false);//设置searchView处于展开状态
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                setSearchView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        //searchView.setQueryHint("请输入搜索内容");//设置提示信息
        searchView.clearFocus();//不获取焦点
        mLayoutManager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TagAdapter(this,buildData());
        mRecyclerView.setAdapter(mAdapter);

    }
    private List<TagAdapter.tag>buildData(){
        Url_Request.sendRequestAttractionOfName(Url_Request.getUrl_head() + "/attraction/of/name", user_token, "西湖", "1", "10", bottomnavi.loc.getLongitude().toString(), bottomnavi.loc.getLatitude().toString(), new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attractions = (List<Url_Request.Attraction>) bean;
                for (int i = 0; i < attractions.size(); i++) {
                    TagAdapter.tag tag1 = new TagAdapter.tag();
                    tag1.setName(attractions.get(i).getName()+"  "+attractions.get(i).getType());
                    tag1.setKind(attractions.get(i).getAddress());
                    tag1.setId(attractions.get(i).getId().toString());


                    list.add(tag1);
                }
                TagActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });



        return list;
    }

    private void setSearchView() {
        //空数据
        list.clear();
        Url_Request.sendRequestAttractionOfName(Url_Request.getUrl_head() + "/attraction/of/name", user_token, searchView.getQuery().toString(), "1", "10", bottomnavi.loc.getLongitude().toString(), bottomnavi.loc.getLatitude().toString(), new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attractions = (List<Url_Request.Attraction>) bean;
                for (int i = 0; i < attractions.size(); i++) {
                    TagAdapter.tag tag1 = new TagAdapter.tag();
                    tag1.setName(attractions.get(i).getName()+"  "+attractions.get(i).getType());
                    tag1.setKind(attractions.get(i).getAddress());
                    tag1.setId(attractions.get(i).getId().toString());


                    list.add(tag1);
                }
                TagActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tag_cancel:
                finish();
                break;
        }
    }
}