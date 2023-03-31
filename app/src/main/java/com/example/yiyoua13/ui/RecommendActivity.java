package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.yiyoua13.R;
import com.example.yiyoua13.RecommendAdapter;
import com.example.yiyoua13.variousclass.Recommend;

import java.util.ArrayList;
import java.util.List;

public class RecommendActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecommendAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        init();
    }
    public void init(){
        mRecyclerView = findViewById(R.id.recommend_recycler_view);
        mLayoutManager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecommendAdapter(this,buildData());
        mRecyclerView.setAdapter(mAdapter);

    }
    private List<Recommend>buildData(){
        List<Recommend> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Recommend recommend = new Recommend();
            recommend.setTitle("title"+i);
            recommend.setContent("content"+i);
            recommend.setImage01("image01"+i);
            recommend.setImage02("image02"+i);
            recommend.setImage03("image03"+i);
            recommend.setImage04("image04"+i);
            recommend.setName01("name01"+i);
            recommend.setName02("name02"+i);
            recommend.setName03("name03"+i);
            recommend.setName04("name04"+i);
            recommend.setPrice01("price01"+i);
            recommend.setPrice02("price02"+i);
            recommend.setPrice03("price03"+i);
            recommend.setPrice04("price04"+i);
            recommend.setTag01("tag01"+i);
            recommend.setTag02("tag02"+i);
            recommend.setTag03("tag03"+i);
            recommend.setTag04("tag04"+i);
            recommend.setStar01("2.8"+i);
            recommend.setStar02("3.8"+i);
            recommend.setStar03("4.8"+i);
            recommend.setStar04("1.8"+i);

            list.add(recommend);

        }
        return list;
    }
}