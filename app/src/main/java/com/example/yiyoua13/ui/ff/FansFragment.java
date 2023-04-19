package com.example.yiyoua13.ui.ff;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yiyoua13.AppFragmentPageAdapter;
import com.example.yiyoua13.PersonAdapter;
import com.example.yiyoua13.R;
import com.example.yiyoua13.databinding.FragmentFansBinding;
import com.example.yiyoua13.databinding.FragmentUserBinding;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.fans;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FansFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FansFragment extends Fragment {
    private FragmentFansBinding binding;
    private RecyclerView fansRecyclerView;
    private int pagenum = 1;
    private RecyclerView.LayoutManager fansLayoutManager;
    private PersonAdapter fansAdapter;
    private SharedPreferences sp;
    private String user_token,user_id;
    private List<fans> list = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FansFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FansFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FansFragment newInstance(String param1, String param2) {
        FansFragment fragment = new FansFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private void init() {
        sp = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");

        fansRecyclerView = binding.fansRecyclerView;
        SmartRefreshLayout refreshLayout = binding.fansRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        //加载更多和刷新监听
        fansLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        fansAdapter = new PersonAdapter(getActivity(),buildDatafans());
        fansRecyclerView.setLayoutManager(fansLayoutManager);
        fansRecyclerView.setAdapter(fansAdapter);
    }
    private List<fans> buildDatafans() {
        Url_Request.sendRequestFollowFans(Url_Request.getUrl_head() + "/follow/fans", user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Me> meList = (List<Url_Request.Me>) bean;
                for (int i = 0; i < meList.size(); i++) {
                    fans fans = new fans();
                    fans.setFans_name(meList.get(i).getNickname());
                    fans.setFans_headpic(meList.get(i).getIcon());
                    fans.setFans_id(String.valueOf(meList.get(i).getId()));
                    fans.setIntroduction(meList.get(i).getIntroduction());
                    list.add(fans);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fansAdapter.notifyDataSetChanged();
                    }
                });


            }
        });


        return list;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFansBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        return root;
    }
}