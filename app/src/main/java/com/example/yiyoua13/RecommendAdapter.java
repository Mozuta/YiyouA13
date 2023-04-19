package com.example.yiyoua13;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.Recommend;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private List<Recommend> rcmList;
    private int mLayoutId;
    public RecommendAdapter(List<Recommend> data, Context context, int layoutId) {
        mContext = context;
        rcmList = data;
        mLayoutId=layoutId;
    }
    public void updateData(List<Recommend> data) {
        //data中所有数据都添加到mData中
        rcmList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_likt, parent,false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecommendViewHolder recommendViewHolder=(RecommendViewHolder)holder;
        Recommend recommend=rcmList.get(position);

        recommendViewHolder.title.setText(recommend.getTitle());
        recommendViewHolder.content.setText(recommend.getContent());
        //recommendViewHolder.re01.setAdapter(recommend.getInsideAdapter());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recommendViewHolder.re01.setLayoutManager(linearLayoutManager);
        recommendViewHolder.re01.setAdapter(recommend.getInsideAdapter());
        SharedPreferences sp=mContext.getSharedPreferences("user", MODE_PRIVATE);
        sp = mContext.getSharedPreferences("Login", MODE_PRIVATE);
        String user_id, user_token;
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        recommendViewHolder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"点击了"+position,Toast.LENGTH_SHORT).show();
                Log.e("点击了",recommend.getId());
                Url_Request.sendRequestRecommendSelectRoute(Url_Request.getUrl_head()+"/recommend/selectRoute",user_token,recommend.getId());
            }
        });




    }

    @Override
    public int getItemCount() {
        if (rcmList!=null)
            return rcmList.size();
        else
            return 0;

    }
    public static class RecommendViewHolder extends RecyclerView.ViewHolder{
        View clickView;

        TextView title;
        TextView content;
        RecyclerView re01;





        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView=itemView;
            title=itemView.findViewById(R.id.re_total_title);
            content=itemView.findViewById(R.id.re_total_content);
            re01=itemView.findViewById(R.id.re_recyclerView);



        }
    }
}