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

import com.example.yiyoua13.R;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.FragmentHomeBinding;
import com.example.yiyoua13.ui.ContentActivity;
import com.example.yiyoua13.ui.KindActivity;
import com.example.yiyoua13.ui.RecommendActivity;
import com.example.yiyoua13.ui.Variouspot_Activity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private WaterFallAdapter mAdapter;
    private Button search_btn;
    private Button recommend_btn;
    private Button push_btn;
    private ImageButton kind_btn1;
    private ImageButton kind_btn2;
    private ImageButton kind_btn3;
    private ImageButton kind_btn4;
    private ImageButton kind_btn5;
    private ImageButton kind_btn6;
    private ImageButton kind_btn7;
    private ImageButton kind_btn8;
    private TextView kind_text1;
    private TextView kind_text2;
    private TextView kind_text3;
    private TextView kind_text4;
    private TextView kind_text5;
    private TextView kind_text6;
    private TextView kind_text7;
    private TextView kind_text8;


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
        kind_btn1 = binding.imageButton1;
        kind_btn2 = binding.imageButton2;
        kind_btn3 = binding.imageButton3;
        kind_btn4 = binding.imageButton4;
        kind_btn5 = binding.imageButton5;
        kind_btn6 = binding.imageButton6;
        kind_btn7 = binding.imageButton7;
        kind_btn8 = binding.imageButton8;
        kind_text1 = binding.homeText1;
        kind_text2 = binding.homeText2;
        kind_text3 = binding.homeText3;
        kind_text4 = binding.homeText4;
        kind_text5 = binding.homeText5;
        kind_text6 = binding.homeText6;
        kind_text7 = binding.homeText7;
        kind_text8 = binding.homeText8;
        kind_btn1.setOnClickListener(this);
        kind_btn2.setOnClickListener(this);
        kind_btn3.setOnClickListener(this);
        kind_btn4.setOnClickListener(this);
        kind_btn5.setOnClickListener(this);
        kind_btn6.setOnClickListener(this);
        kind_btn7.setOnClickListener(this);
        kind_btn8.setOnClickListener(this);
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
                Intent intent = new Intent(getActivity(), Variouspot_Activity.class);
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton1:
                Intent intent1 = new Intent(getActivity(), KindActivity.class);
                intent1.putExtra("name",kind_text1.getText().toString());
                startActivity(intent1);
                break;
            case R.id.imageButton2:
                Intent intent2 = new Intent(getActivity(), KindActivity.class);
                intent2.putExtra("name",kind_text2.getText().toString());
                startActivity(intent2);
                break;
            case R.id.imageButton3:
                Intent intent3 = new Intent(getActivity(), KindActivity.class);
                intent3.putExtra("name",kind_text3.getText().toString());
                startActivity(intent3);
                break;
            case R.id.imageButton4:
                Intent intent4 = new Intent(getActivity(), KindActivity.class);
                intent4.putExtra("name",kind_text4.getText().toString());
                startActivity(intent4);
                break;
            case R.id.imageButton5:
                Intent intent5 = new Intent(getActivity(), KindActivity.class);
                intent5.putExtra("name",kind_text5.getText().toString());
                startActivity(intent5);
                break;
            case R.id.imageButton6:
                Intent intent6 = new Intent(getActivity(), KindActivity.class);
                intent6.putExtra("name",kind_text6.getText().toString());
                startActivity(intent6);
                break;
            case R.id.imageButton7:
                Intent intent7 = new Intent(getActivity(), KindActivity.class);
                intent7.putExtra("name",kind_text7.getText().toString());
                startActivity(intent7);
                break;
            case R.id.imageButton8:
                Intent intent8 = new Intent(getActivity(), KindActivity.class);
                intent8.putExtra("name",kind_text8.getText().toString());
                startActivity(intent8);
                break;

        }
    }
}