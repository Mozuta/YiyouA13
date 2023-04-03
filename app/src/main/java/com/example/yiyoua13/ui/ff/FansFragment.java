package com.example.yiyoua13.ui.ff;

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
    private RecyclerView.LayoutManager fansLayoutManager;
    private PersonAdapter fansAdapter;

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
        List<fans> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fans fans = new fans();
            fans.setFans_name("粉丝" + i);
            fans.setFans_headpic("https://i.postimg.cc/x1HHh4C7/1.png");
            //fans.setFans_id(String.valueOf(i));
            fans.setFans_num(String.valueOf(i));
            fans.setContent_num(String.valueOf(i*2));
            list.add(fans);
        }
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