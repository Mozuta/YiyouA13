package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.SpotAdapter;
import com.example.yiyoua13.databinding.ActivityFavoriteBinding;
import com.example.yiyoua13.variousclass.Spot;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityFavoriteBinding binding;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SpotAdapter mAdapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        back = binding.favBack;
        back.setOnClickListener(this);
        mRecyclerView = binding.favRecycler;
        refreshLayout = binding.favRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            //刷新数据
            mAdapter = new SpotAdapter(this, buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishRefresh();
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载数据
            mAdapter = new SpotAdapter(this, buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishLoadMore();
        });
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SpotAdapter(this, buildData());
        mRecyclerView.setAdapter(mAdapter);
    }
    private List<Spot> buildData (){
        List<Spot> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Spot spot = new Spot();
            spot.setName("景点"+i);
            spot.setAddress("地址"+i);
            spot.setDescription("描述"+i);
            spot.setImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606780000&di=1b1f1b1f1b1f1b1f1b1f1b1f1b1f1b1f&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01b4e756e5b9fda801219c77f3b8a6.jpg%401280w_1l_2o_100sh.jpg");
            spot.setPrice("价格"+i);
            spot.setDistance("距离"+i);
            spot.setStars("4.2");
            spot.setKind("景点");
            list.add(spot);


        }
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