package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.ActivityPcenterBinding;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

public class PCenter_Activity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPcenterBinding binding;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private LinearLayout ln_jump2info;
    private TextView follow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPcenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init() {
        mRecyclerView= binding.pcRecyclerView;
        refreshLayout = binding.pcRefreshLayout;
        ln_jump2info = binding.pcSpace;
        follow = binding.ifFollow;
        ln_jump2info.setOnClickListener(this);
        follow.setOnClickListener(this);

        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        //binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
        });
        //设置布局管理器
        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        mAdapter = new WaterFallAdapter(this,buildData());
        mRecyclerView.setAdapter(mAdapter);
    }
    private List<WaterFallAdapter.PersonCard> buildData() {

        String[] names = {"刘8","刘3333","伦纳德","nox","盖伦","陈博晟"};
        String[] contents = {"来点作用啊组长","整点ui啊组长","你说得对，但是家人们谁懂啊，浙江理工大学是一所","d","e","f"};
        String[] likes = {"99+","2","3","4","5","6"};

        String[] imgUrs = {"https://i.postimg.cc/NMQjFFVv/cat.jpg","","https://i.postimg.cc/k4wXb936/flower.jpg","https://i.postimg.cc/90MXPSCx/sqw.jpg","https://i.postimg.cc/kGpJTc57/tree.jpg","https://i.postimg.cc/3JGhkJ8C/whl.jpg",

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pc_space:
                Intent intent = new Intent(this, FansFollowActivity.class);
                startActivity(intent);
                break;
            case R.id.if_follow:
                follow.setText("已关注");
                break;
        }
    }
}