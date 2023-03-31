package com.example.yiyoua13.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.yiyoua13.SpotAdapter;
import com.example.yiyoua13.databinding.FragmentNotificationsBinding;
import com.example.yiyoua13.variousclass.Spot;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SpotAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();


        return root;
    }
    private void init(){
        mRecyclerView = binding.spotRecyclerview;
        refreshLayout = binding.spotRefreshLayout;
        refreshLayout.setEnableLoadMore(false);
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            //刷新数据
            mAdapter = new SpotAdapter(getActivity(), buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishRefresh();
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载数据
            mAdapter = new SpotAdapter(getActivity(), buildData());
            mRecyclerView.setAdapter(mAdapter);
            refreshLayout.finishLoadMore();
        });
        //设置布局管理器
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        mAdapter = new SpotAdapter(getActivity(), buildData());
        mRecyclerView.setAdapter(mAdapter);

    }
    private List<Spot>buildData (){
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}