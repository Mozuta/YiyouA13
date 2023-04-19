package com.example.yiyoua13.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yiyoua13.SpotAdapter;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.Spot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MyTask extends AsyncTask<String, Integer, List<Spot>> {

    private WeakReference<Context> mContextRef;
    private RecyclerView mRecyclerView;
    private SpotAdapter mAdapter;
    private List<Spot> mSpotList;
    private SharedPreferences sp;

    public MyTask(Context context, RecyclerView recyclerView, SpotAdapter adapter, List<Spot> spotList) {
        mContextRef = new WeakReference<>(context);
        mRecyclerView = recyclerView;
        mAdapter = adapter;
        mSpotList = spotList;
    }

    @Override
    protected List<Spot> doInBackground(String... params) {
        // 执行耗时操作，如网络请求等
        List<Spot> list = new ArrayList<>();
        sp = mContextRef.get().getSharedPreferences("Login", 0);
        String user_token = sp.getString("TOKEN", "");
        Url_Request.sendRequestTOBA(Url_Request.getUrl_head()+"/attraction/of/type", user_token, "1", "1","5","120.354722","30.313242",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                for (int i=0;i<attraction.size();i++){
                    Spot spot = new Spot();
                    spot.setName(attraction.get(i).getName());
                    spot.setAddress(attraction.get(i).getAddress());
                    spot.setDescription(String.valueOf(attraction.get(i).getBlogs()));
                    spot.setImage(attraction.get(i).getImagesList().get(0));
                    spot.setPrice(String.valueOf(attraction.get(i).getPrice()));
                    spot.setDistance(String.valueOf(attraction.get(i).getDistance()));
                    spot.setStars(String.format("%.1f", Double.parseDouble(String.valueOf(attraction.get(i).getScore()))/10));
                    spot.setKind(attraction.get(i).getType());
                    list.add(spot);
                }
            }
        });
        return list;
    }

    @Override
    protected void onPostExecute(List<Spot> list) {
        // 处理 doInBackground() 方法的返回结果，更新 UI 界面
        if (mRecyclerView != null && mAdapter != null && mSpotList != null) {
            mSpotList.clear();
            mSpotList.addAll(list);
            mAdapter.notifyDataSetChanged();
        }
    }
}
