package com.example.yiyoua13;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.variousclass.fans;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<fans> fansList; //定义数据源
    public PersonAdapter(Context context, List<fans> data) {
        mContext = context;
        fansList = data;
    }

    @NonNull
    @Override
    public PAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_libt, null);
        return new PAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PAViewHolder paViewHolder=(PAViewHolder)holder;
        fans fans=fansList.get(position);
        Glide.with(mContext).load(fans.getFans_headpic()).placeholder(R.drawable.hog).error(R.drawable.lwr).into(paViewHolder.avatar);;
        paViewHolder.name.setText(fans.getFans_name());
        paViewHolder.fans_num.setText(fans.getFans_num());
        paViewHolder.content_num.setText(fans.getContent_num());
        paViewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paViewHolder.follow.setText("已关注");
            }
        });



    }

    @Override
    public int getItemCount() {
        if (fansList!=null)
            return fansList.size();
        else
            return 0;
    }
    public static class PAViewHolder extends RecyclerView.ViewHolder{
        View clickView;
        ImageView avatar;
        TextView name;
        TextView fans_num;
        TextView content_num;
        Button follow;

        public PAViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView=itemView;
            avatar=itemView.findViewById(R.id.tx);
            name=itemView.findViewById(R.id.namein);
            fans_num=itemView.findViewById(R.id.fans_num);
            content_num=itemView.findViewById(R.id.content_num);
            follow=itemView.findViewById(R.id.follow_btn);
        }
    }

}
