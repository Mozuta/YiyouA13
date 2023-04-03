package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.VSAdapter;
import com.example.yiyoua13.databinding.ActivityVariouspotBinding;
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

    private VSAdapter mAdapter;
    private ImageView imageView;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVariouspotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }
    private void init() {
        mRecyclerView = binding.vsRecycler;
        imageView = binding.vsBack;
        textView = binding.vsSearch;
        editText = binding.vsName;
        imageView.setOnClickListener(this);
        textView.setOnClickListener(this);
        refreshLayout = binding.vsRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));

        mLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new VSAdapter(this,VoidData());
        mRecyclerView.setAdapter(mAdapter);
    }
    private List<VSAdapter.VSBean> VoidData() {
        //空数据
        List<VSAdapter.VSBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VSAdapter.VSBean bean = new VSAdapter.VSBean();
            bean.name=("1");
            bean.distance=("1");
            bean.kind=("1");
            bean.location=("1");
            bean.price=("1");
            bean.stars= String.valueOf((i));
            list.add(bean);
        }
        return list;
    }
    private List<VSAdapter.VSBean> buildData() {
        List<VSAdapter.VSBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VSAdapter.VSBean bean = new VSAdapter.VSBean();
            bean.name=("vsName");
            bean.distance=("vsDistance");
            bean.kind=("vsKind");
            bean.location=("vsLocation");
            bean.price=("vsPrice");
            bean.stars= String.valueOf((i));
            list.add(bean);
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.vs_back:
                finish();
                break;
            case R.id.vs_search:
                mAdapter = new VSAdapter(this,buildData());
                mRecyclerView.setAdapter(mAdapter);
                break;
        }
    }
}