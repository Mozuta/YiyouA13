package com.example.yiyoua13.ui.home;

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

import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;

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
        mRecyclerView= binding.recyclerview;
        //设置布局管理器为2列，纵向
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new WaterFallAdapter(getActivity(), buildData());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private List<WaterFallAdapter.PersonCard> buildData() {

        String[] names = {"邓紫棋","范冰冰","杨幂","Angelababy","唐嫣","柳岩"};
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