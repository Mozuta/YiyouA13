package com.example.yiyoua13.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.FragAct;
import com.example.yiyoua13.R;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.FragmentHomeBinding;
import com.example.yiyoua13.task.ImageLoaderTask;
import com.example.yiyoua13.task.TextLoaderTask;
import com.example.yiyoua13.ui.ContentActivity;
import com.example.yiyoua13.ui.KindActivity;
import com.example.yiyoua13.ui.RecommendActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.ui.Variouspot_Activity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

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
    private int pagenum = 1;
    private List<String> mDatas=new ArrayList<>();
    private ImageView kind_btn1;
    private ImageView kind_btn2;
    private ImageView kind_btn3;
    private ImageView kind_btn4;
    private ImageView kind_btn5;
    private ImageView kind_btn6;
    private ImageView kind_btn7;
    private ImageView kind_btn8;
    private TextView kind_text1;
    private TextView kind_text2;
    private TextView kind_text3;
    private TextView kind_text4;
    private TextView kind_text5;
    private TextView kind_text6;
    private TextView kind_text7;
    private TextView kind_text8;
    private SharedPreferences sp;
    private String user_token,user_id;

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
        sp = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
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
        Url_Request.sendRequestAtrractionType(Url_Request.getUrl_head() + "/attraction-type/list",  new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.AttractionList> attractionTypes = (List<Url_Request.AttractionList>) bean;

                new TextLoaderTask(kind_text1,attractionTypes.get(0).getName()).execute();
                new TextLoaderTask(kind_text2,attractionTypes.get(1).getName()).execute();
                new TextLoaderTask(kind_text3,attractionTypes.get(2).getName()).execute();
                new TextLoaderTask(kind_text4,attractionTypes.get(3).getName()).execute();
                new TextLoaderTask(kind_text5,attractionTypes.get(4).getName()).execute();
                new TextLoaderTask(kind_text6,attractionTypes.get(5).getName()).execute();
                new TextLoaderTask(kind_text7,attractionTypes.get(6).getName()).execute();
                new TextLoaderTask(kind_text8,attractionTypes.get(7).getName()).execute();
                //启动ui线程
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getActivity()).load(attractionTypes.get(0).getIcon()).error(R.drawable.sqw).into(kind_btn1);
                        Glide.with(getActivity()).load(attractionTypes.get(1).getIcon()).error(R.drawable.sqw).into(kind_btn2);
                        Glide.with(getActivity()).load(attractionTypes.get(2).getIcon()).error(R.drawable.sqw).into(kind_btn3);
                        Glide.with(getActivity()).load(attractionTypes.get(3).getIcon()).error(R.drawable.sqw).into(kind_btn4);
                        Glide.with(getActivity()).load(attractionTypes.get(4).getIcon()).error(R.drawable.sqw).into(kind_btn5);
                        Glide.with(getActivity()).load(attractionTypes.get(5).getIcon()).error(R.drawable.sqw).into(kind_btn6);
                        Glide.with(getActivity()).load(attractionTypes.get(6).getIcon()).error(R.drawable.sqw).into(kind_btn7);
                        Glide.with(getActivity()).load(attractionTypes.get(7).getIcon()).error(R.drawable.sqw).into(kind_btn8);
                        mDatas.add(String.valueOf(attractionTypes.get(0).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(1).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(2).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(3).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(4).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(5).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(6).getId()));
                        mDatas.add(String.valueOf(attractionTypes.get(7).getId()));
                    }
                });

            }
        });
        //下拉刷新
        binding.refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        binding.refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            pagenum=mAdapter.clearData();
            loadData();
            refreshLayout.finishRefresh(/*,false*/);//传入false表示刷新失败
        });
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            //加载数据
            loadData();
            refreshLayout.finishLoadMore(/*,false*/);//传入false表示加载失败
        });
        recommend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_token.equals("")){
                    Toast.makeText(getActivity(),"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), FragAct.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), RecommendActivity.class);
                    startActivity(intent);
                }
                //跳转到推荐界面

            }
        });

        push_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_token.equals("")){
                    Toast.makeText(getActivity(),"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), FragAct.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), ContentActivity.class);
                    startActivity(intent);
                }

                //跳转到发布界面

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
        List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
        Url_Request.sendRequestBlogHot(Url_Request.getUrl_head()+"/blog/hot", user_token,"1",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Note> blogHots = (List<Url_Request.Note>) bean;
                for (Url_Request.Note note : blogHots) {
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0).toString();
                    p.headurl = note.getIcon();
                    if (note.getIslike()!=null)
                        p.islike = note.getIslike();
                    else
                        p.islike = false;

                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        pagenum=2;


        return list;
    }
    private void loadData(){
        Url_Request.sendRequestBlogHot(Url_Request.getUrl_head()+"/blog/hot", user_token,String.valueOf(pagenum),new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
                List<Url_Request.Note> blogHots = (List<Url_Request.Note>) bean;
                for (Url_Request.Note note : blogHots) {
                    Log.d("TAG", "onBeanResponse: "+pagenum);
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0);
                    p.headurl = note.getIcon();
                    if (note.getIslike()!=null)
                        p.islike = note.getIslike();
                    else
                        p.islike = false;
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.updateData(list);
                    }
                });
            }
        });
        pagenum++;
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
                intent1.putExtra("id",mDatas.get(0));
                //intent1.putExtra("name",kind_text1.getText().toString());
                startActivity(intent1);
                break;
            case R.id.imageButton2:
                Intent intent2 = new Intent(getActivity(), KindActivity.class);
                intent2.putExtra("name",kind_text2.getText().toString());
                intent2.putExtra("id",mDatas.get(1));
                startActivity(intent2);
                break;
            case R.id.imageButton3:
                Intent intent3 = new Intent(getActivity(), KindActivity.class);
                intent3.putExtra("name",kind_text3.getText().toString());
                intent3.putExtra("id",mDatas.get(2));
                startActivity(intent3);
                break;
            case R.id.imageButton4:
                Intent intent4 = new Intent(getActivity(), KindActivity.class);
                intent4.putExtra("name",kind_text4.getText().toString());
                intent4.putExtra("id",mDatas.get(3));
                startActivity(intent4);
                break;
            case R.id.imageButton5:
                Intent intent5 = new Intent(getActivity(), KindActivity.class);
                intent5.putExtra("name",kind_text5.getText().toString());
                intent5.putExtra("id",mDatas.get(4));
                startActivity(intent5);
                break;
            case R.id.imageButton6:
                Intent intent6 = new Intent(getActivity(), KindActivity.class);
                intent6.putExtra("name",kind_text6.getText().toString());
                intent6.putExtra("id",mDatas.get(5));
                startActivity(intent6);
                break;
            case R.id.imageButton7:
                Intent intent7 = new Intent(getActivity(), KindActivity.class);
                intent7.putExtra("name",kind_text7.getText().toString());
                intent7.putExtra("id",mDatas.get(6));
                startActivity(intent7);
                break;
            case R.id.imageButton8:
                Intent intent8 = new Intent(getActivity(), KindActivity.class);
                intent8.putExtra("name",kind_text8.getText().toString());
                intent8.putExtra("id",mDatas.get(7));
                startActivity(intent8);
                break;

        }
    }
}