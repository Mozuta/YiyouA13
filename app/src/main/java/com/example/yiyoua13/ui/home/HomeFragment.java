package com.example.yiyoua13.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.yiyoua13.TestActivity;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.FragmentHomeBinding;
import com.example.yiyoua13.ui.ContentActivity;
import com.example.yiyoua13.ui.DetailActivity;
import com.example.yiyoua13.ui.RecommendActivity;
import com.example.yiyoua13.ui.SearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private Button search_btn;
    private Button recommend_btn;
    private ImageButton push_btn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init();

        return root;
    }
    private void init() {
        recommend_btn = binding.recommend;
        mRecyclerView= binding.recyclerview;
        search_btn = binding.search;
        push_btn = binding.addContent;
        //binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            //刷新数据
            mAdapter = new WaterFallAdapter(getActivity(), buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        });
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载数据
            mAdapter = new WaterFallAdapter(getActivity(), buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
        });
        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到推荐界面
                Intent intent = new Intent(getActivity(), RecommendActivity.class);
                startActivity(intent);
            }
        });

        push_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到发布界面
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                startActivity(intent);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到搜索界面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);

            }
        });
        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WaterFallAdapter(getActivity(), buildData());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}