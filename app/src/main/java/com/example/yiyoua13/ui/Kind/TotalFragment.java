package com.example.yiyoua13.ui.Kind;

import static com.example.yiyoua13.bottomnavi.loc;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yiyoua13.R;
import com.example.yiyoua13.SpotAdapter;
import com.example.yiyoua13.databinding.FragmentDistanceBinding;
import com.example.yiyoua13.databinding.FragmentTotalBinding;
import com.example.yiyoua13.task.MyTask;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.Spot;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TotalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotalFragment extends Fragment {
    private FragmentTotalBinding binding;
    private int pagenum = 2;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedPreferences sp;
    private SpotAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TotalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TotalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TotalFragment newInstance(String param1, String param2) {
        TotalFragment fragment = new TotalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        // 在划入 Fragment 时进行数据刷新
        pagenum=mAdapter.clearData();
    }*/
    // 在划出 Fragment 时进行数据刷新



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void init() {
        mRecyclerView = binding.totalRecyclerView;
        SmartRefreshLayout refreshLayout = binding.totalRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(refresh -> {
            pagenum=mAdapter.clearData();
            loadData();
            refresh.finishRefresh();//传入false表示刷新失败
        });
        refreshLayout.setOnLoadMoreListener(refresh -> {


            loadData();

            refresh.finishLoadMore();//传入false表示刷新失败
        });
        //加载更多和刷新监听
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new SpotAdapter(getActivity(),buildData());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void loadData() {

        sp = getActivity().getSharedPreferences("Login", 0);
        String user_token = sp.getString("TOKEN", "");
        Url_Request.sendRequestTOBA(
                Url_Request.getUrl_head() + "/attraction/of/type",
                user_token,
                getActivity().getIntent().getStringExtra("id"),
                String.valueOf(pagenum),
                "5",
                String.valueOf(loc.getLongitude()),String.valueOf(loc.getLatitude()),
                new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                        List<Spot> list = new ArrayList<>();
                        for (int i = 0; i < attraction.size(); i++) {
                            Spot spot = new Spot();
                            spot.setId(String.valueOf(attraction.get(i).getId()));
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                            spot.setName(attraction.get(i).getName());
                            spot.setAddress(attraction.get(i).getArea());
                            spot.setDescription("现在有"+String.valueOf(attraction.get(i).getBlogs())+"人正在热评~");
                            spot.setImage(attraction.get(i).getImagesList().get(0));
                            spot.setPrice(String.valueOf(attraction.get(i).getPrice())+"元起");
                            if (attraction.get(i).getScore() == 0)
                                spot.setStars("暂无评分");
                            else
                                spot.setStars(String.format("%.1f", Double.parseDouble(String.valueOf(attraction.get(i).getScore())) / 10));
                            if (attraction.get(i).getDistance() > 1000)
                                spot.setDistance(String.valueOf(attraction.get(i).getDistance() / 1000) + "km");
                            else
                                spot.setDistance(String.valueOf(attraction.get(i).getDistance()) + "m");
                            spot.setKind(attraction.get(i).getType());
                            list.add(spot);
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
    private List<Spot> buildData (){

        List<Spot> list = new ArrayList<>();
        sp = getActivity().getSharedPreferences("Login", 0);
        String user_token = sp.getString("TOKEN", "");

        Url_Request.sendRequestTOBA(Url_Request.getUrl_head()+"/attraction/of/type", user_token, getActivity().getIntent().getStringExtra("id"), "1","5",String.valueOf(loc.getLongitude()),String.valueOf(loc.getLatitude()),new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                for (int i = 0; i < attraction.size(); i++) {
                    Spot spot = new Spot();
                    spot.setId(String.valueOf(attraction.get(i).getId()));
                    Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                    spot.setName(attraction.get(i).getName());
                    spot.setAddress(attraction.get(i).getArea());
                    spot.setDescription("现在有"+String.valueOf(attraction.get(i).getBlogs())+"人正在热评~");
                    spot.setImage(attraction.get(i).getImagesList().get(0));
                    spot.setPrice(String.valueOf(attraction.get(i).getPrice())+"元起");
                    if (attraction.get(i).getScore() == 0)
                        spot.setStars("暂无评分");
                    else
                        spot.setStars(String.format("%.1f", Double.parseDouble(String.valueOf(attraction.get(i).getScore())) / 10));
                    if (attraction.get(i).getDistance() > 1000)
                        spot.setDistance(String.valueOf(attraction.get(i).getDistance() / 1000) + "km");
                    else
                        spot.setDistance(String.valueOf(attraction.get(i).getDistance()) + "m");
                    spot.setKind(attraction.get(i).getType());
                    list.add(spot);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTotalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        init();
        return root;

    }
}