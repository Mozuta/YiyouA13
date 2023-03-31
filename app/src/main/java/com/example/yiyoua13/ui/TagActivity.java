package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.TagAdapter;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity implements View.OnClickListener{
    private SearchView searchView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TagAdapter mAdapter;

    private TextView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        init();
    }
    public void init(){
        mRecyclerView = findViewById(R.id.tag_recycler_view);
        cancel = findViewById(R.id.tag_cancel);
        cancel.setOnClickListener(this);
        searchView = findViewById(R.id.tag_search_view);
        searchView.setIconified(false);//设置searchView处于展开状态
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        //searchView.setQueryHint("请输入搜索内容");//设置提示信息
        searchView.clearFocus();//不获取焦点
        mLayoutManager =new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TagAdapter(this,buildData());
        mRecyclerView.setAdapter(mAdapter);

    }
    private List<TagAdapter.tag>buildData(){
        List<TagAdapter.tag> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TagAdapter.tag tag1 = new TagAdapter.tag();
            tag1.setName("tag"+i);
            tag1.setKind("kind"+i);
            list.add(tag1);

        }
        return list;
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