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
import com.example.yiyoua13.databinding.FragmentFocusBinding;
import com.example.yiyoua13.variousclass.fans;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FocusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FocusFragment extends Fragment {
    private FragmentFocusBinding binding;
    private RecyclerView focusRecyclerView;
    private RecyclerView.LayoutManager focusLayoutManager;
    private PersonAdapter focusAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FocusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FocusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FocusFragment newInstance(String param1, String param2) {
        FocusFragment fragment = new FocusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void init(){
        focusRecyclerView = binding.focusRecyclerView;
        SmartRefreshLayout refreshLayout = binding.focusRefreshLayout;
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        //加载更多和刷新监听
        focusLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        focusAdapter = new PersonAdapter(getActivity(),buildDatafollow());
        focusRecyclerView.setLayoutManager(focusLayoutManager);
        focusRecyclerView.setAdapter(focusAdapter);
    }
    private List<fans> buildDatafollow() {
        List<fans> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fans fans = new fans();
            fans.setFans_name("关注" + i);
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
        binding = FragmentFocusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        return root;
    }
}