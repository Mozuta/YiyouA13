package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.SpotAdapter;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.ActivityFavoriteBinding;
import com.example.yiyoua13.databinding.ActivityMineBinding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        back = binding.mineBack;
        back.setOnClickListener(this);
        mRecyclerView = binding.mineRecycler;
        refreshLayout = binding.mineRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            //刷新数据
            mAdapter = new WaterFallAdapter(this, buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishRefresh();
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载数据
            mAdapter = new WaterFallAdapter(this, buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishLoadMore();
        });
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WaterFallAdapter(this, buildData());
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
            case R.id.mine_back:
                finish();
                break;
        }
    }
}