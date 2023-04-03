package com.example.yiyoua13;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.ui.SpotActivity;
import com.example.yiyoua13.variousclass.Recommend;
import com.example.yiyoua13.variousclass.fans;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RecommendAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private List<Recommend> rcmList;
    public RecommendAdapter(Context context, List<Recommend> data) {
        mContext = context;
        rcmList = data;
    }
    public void updateData(List<Recommend> data) {
        //data中所有数据都添加到mData中
        rcmList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_liit, null);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecommendViewHolder recommendViewHolder=(RecommendViewHolder)holder;
        Recommend recommend=rcmList.get(position);
        recommendViewHolder.title.setText(recommend.getTitle());
        recommendViewHolder.content.setText(recommend.getContent());
        recommendViewHolder.name01.setText(recommend.getName01());
        recommendViewHolder.name02.setText(recommend.getName02());
        recommendViewHolder.name03.setText(recommend.getName03());
        recommendViewHolder.name04.setText(recommend.getName04());
        recommendViewHolder.star01.setText(recommend.getStar01());
        recommendViewHolder.star02.setText(recommend.getStar02());
        recommendViewHolder.star03.setText(recommend.getStar03());
        recommendViewHolder.star04.setText(recommend.getStar04());
        recommendViewHolder.price01.setText(recommend.getPrice01());
        recommendViewHolder.price02.setText(recommend.getPrice02());
        recommendViewHolder.price03.setText(recommend.getPrice03());
        recommendViewHolder.price04.setText(recommend.getPrice04());
        recommendViewHolder.tag01.setText(recommend.getTag01());
        recommendViewHolder.tag02.setText(recommend.getTag02());
        recommendViewHolder.tag03.setText(recommend.getTag03());
        recommendViewHolder.tag04.setText(recommend.getTag04());
        recommendViewHolder.ratingBar01.setRating(Float.parseFloat(recommend.getStar01()));
        recommendViewHolder.ratingBar02.setRating(Float.parseFloat(recommend.getStar02()));
        recommendViewHolder.ratingBar03.setRating(Float.parseFloat(recommend.getStar03()));
        recommendViewHolder.ratingBar04.setRating(Float.parseFloat(recommend.getStar04()));
        Glide.with(mContext).load(recommend.getImage01()).placeholder(R.drawable.hog).error(R.drawable.sqw).into(recommendViewHolder.image01);;
        Glide.with(mContext).load(recommend.getImage02()).placeholder(R.drawable.hog).error(R.drawable.sqw).into(recommendViewHolder.image02);;
        Glide.with(mContext).load(recommend.getImage03()).placeholder(R.drawable.hog).error(R.drawable.sqw).into(recommendViewHolder.image03);;
        Glide.with(mContext).load(recommend.getImage04()).placeholder(R.drawable.hog).error(R.drawable.sqw).into(recommendViewHolder.image04);;
        //Glide.with(mContext).load(recommend.getImage04()).placeholder(R.drawable.hog).error(R.drawable.sqw).into(recommendViewHolder.re_total.setBackground());;
        //recommendViewHolder.re_total.setBackground(recommend.getBackground().);
        recommendViewHolder.re01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=recommendViewHolder.getLayoutPosition();
                Recommend recommend=rcmList.get(position);
                Intent intent=new Intent(mContext, SpotActivity.class);
                intent.putExtra("spot",recommend.getName01());
                mContext.startActivity(intent);
            }
        });
        recommendViewHolder.re02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=recommendViewHolder.getLayoutPosition();
                Recommend recommend=rcmList.get(position);
                Intent intent=new Intent(mContext, SpotActivity.class);
                intent.putExtra("spot",recommend.getName02());
                mContext.startActivity(intent);
            }
        });
        recommendViewHolder.re03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=recommendViewHolder.getLayoutPosition();
                Recommend recommend=rcmList.get(position);
                Intent intent=new Intent(mContext, SpotActivity.class);
                intent.putExtra("spot",recommend.getName03());
                mContext.startActivity(intent);
            }
        });
        recommendViewHolder.re04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=recommendViewHolder.getLayoutPosition();
                Recommend recommend=rcmList.get(position);
                Intent intent=new Intent(mContext, SpotActivity.class);
                intent.putExtra("spot",recommend.getName04());
                mContext.startActivity(intent);
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
        MaterialRatingBar ratingBar01;
        MaterialRatingBar ratingBar02;
        MaterialRatingBar ratingBar03;
        MaterialRatingBar ratingBar04;
        TextView title;
        TextView content;
        TextView name01;
        TextView name02;
        TextView name03;
        TextView name04;
        TextView star01;
        TextView star02;
        TextView star03;
        TextView star04;
        TextView price01;
        TextView price02;
        TextView price03;
        TextView price04;
        TextView tag01;
        TextView tag02;
        TextView tag03;
        TextView tag04;
        ImageView image01;
        ImageView image02;
        ImageView image03;
        ImageView image04;
        LinearLayout re_total;
        LinearLayout re01;
        LinearLayout re02;
        LinearLayout re03;
        LinearLayout re04;




        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView=itemView;
            title=itemView.findViewById(R.id.re_total_title);
            content=itemView.findViewById(R.id.re_total_content);
            name01=itemView.findViewById(R.id.re_name01);
            name02=itemView.findViewById(R.id.re_name02);
            name03=itemView.findViewById(R.id.re_name03);
            name04=itemView.findViewById(R.id.re_name04);
            star01=itemView.findViewById(R.id.re_rating01);
            star02=itemView.findViewById(R.id.re_rating02);
            star03=itemView.findViewById(R.id.re_rating03);
            star04=itemView.findViewById(R.id.re_rating04);
            price01=itemView.findViewById(R.id.re_price01);
            price02=itemView.findViewById(R.id.re_price02);
            price03=itemView.findViewById(R.id.re_price03);
            price04=itemView.findViewById(R.id.re_price04);
            tag01=itemView.findViewById(R.id.re_tag01);
            tag02=itemView.findViewById(R.id.re_tag02);
            tag03=itemView.findViewById(R.id.re_tag03);
            tag04=itemView.findViewById(R.id.re_tag04);
            image01=itemView.findViewById(R.id.icon01);
            image02=itemView.findViewById(R.id.icon02);
            image03=itemView.findViewById(R.id.icon03);
            image04=itemView.findViewById(R.id.icon04);
            re_total=itemView.findViewById(R.id.re_total);
            re01=itemView.findViewById(R.id.re01);
            re02=itemView.findViewById(R.id.re02);
            re03=itemView.findViewById(R.id.re03);
            re04=itemView.findViewById(R.id.re04);
            ratingBar01=itemView.findViewById(R.id.re_materialRatingBar01);
            ratingBar02=itemView.findViewById(R.id.re_materialRatingBar02);
            ratingBar03=itemView.findViewById(R.id.re_materialRatingBar03);
            ratingBar04=itemView.findViewById(R.id.re_materialRatingBar04);


        }
    }
}
