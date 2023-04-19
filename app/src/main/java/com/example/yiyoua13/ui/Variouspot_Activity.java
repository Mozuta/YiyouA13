package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.VSAdapter;
import com.example.yiyoua13.bottomnavi;
import com.example.yiyoua13.databinding.ActivityVariouspotBinding;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

public class Variouspot_Activity extends AppCompatActivity implements View.OnClickListener {
    private ActivityVariouspotBinding binding;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    List<VSAdapter.VSBean> list = new ArrayList<>();

    private VSAdapter mAdapter;
    private ImageView imageView;
    private TextView textView;
    private EditText editText;
    private SharedPreferences sp;
    private String user_token,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVariouspotBinding.inflate(getLayoutInflater());
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
        mRecyclerView = binding.vsRecycler;
        imageView = binding.vsBack;
        textView = binding.vsSearch;
        editText = binding.vsName;
        imageView.setOnClickListener(this);
        textView.setOnClickListener(this);
        refreshLayout = binding.vsRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        mAdapter = new VSAdapter(this,list);
        VoidData();
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
    private void VoidData() {
        //空数据
        Url_Request.sendRequestAttractionOfName(Url_Request.getUrl_head() + "/attraction/of/name", user_token,"断桥", "1", "10", bottomnavi.loc.getLongitude().toString(), bottomnavi.loc.getLatitude().toString(), new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attractions = (List<Url_Request.Attraction>) bean;
                for (int i = 0; i < attractions.size(); i++) {
                    VSAdapter.VSBean bean1 = new VSAdapter.VSBean();
                    bean1.id = attractions.get(i).getId().toString();
                    bean1.name = attractions.get(i).getName();
                    if (attractions.get(i).getDistance() > 1000)
                        bean1.distance = String.valueOf(attractions.get(i).getDistance() / 1000) + "km";
                    else
                        bean1.distance = String.valueOf(attractions.get(i).getDistance()) + "m";
                    bean1.kind = attractions.get(i).getType();
                    bean1.location = attractions.get(i).getArea();
                    if (attractions.get(i).getPrice() == 0){
                        bean1.price = "免费";
                    }
                    else {
                        bean1.price = attractions.get(i).getPrice().toString()+"元" ;
                    }
                    String newStr =attractions.get(i).getScore().toString().substring(0, 1) + "." + attractions.get(i).getScore().toString().substring(1);
                    bean1.stars = newStr;
                    list.add(bean1);
                }
                Variouspot_Activity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });



    }
    private void buildData() {
        list.clear();
        Url_Request.sendRequestAttractionOfName(Url_Request.getUrl_head() + "/attraction/of/name", user_token, editText.getText().toString(), "1", "10", bottomnavi.loc.getLongitude().toString(), bottomnavi.loc.getLatitude().toString(), new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attractions = (List<Url_Request.Attraction>) bean;
                for (int i = 0; i < attractions.size(); i++) {
                    VSAdapter.VSBean bean1 = new VSAdapter.VSBean();
                    bean1.id = attractions.get(i).getId().toString();
                    bean1.name = attractions.get(i).getName();
                    if (attractions.get(i).getDistance() > 1000)
                        bean1.distance = String.valueOf(attractions.get(i).getDistance() / 1000) + "km";
                    else
                        bean1.distance = String.valueOf(attractions.get(i).getDistance()) + "m";
                    bean1.kind = attractions.get(i).getType();
                    bean1.location = attractions.get(i).getArea();
                    if (attractions.get(i).getPrice() == 0){
                        bean1.price = "免费";
                    }
                    else {
                        bean1.price = attractions.get(i).getPrice().toString()+"元" ;
                    }
                    String newStr =attractions.get(i).getScore().toString().substring(0, 1) + "." + attractions.get(i).getScore().toString().substring(1);
                    bean1.stars = newStr;
                    list.add(bean1);
                }
                Variouspot_Activity.this.runOnUiThread(new Runnable() {
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
            case R.id.vs_back:
                finish();
                break;
            case R.id.vs_search:
                buildData();
                break;
        }
    }
}