package com.example.yiyoua13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.ui.RecommendActivity;
import com.example.yiyoua13.ui.SpotActivity;
import com.example.yiyoua13.variousclass.Inside;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class InsideAdapter extends RecyclerView.Adapter<InsideAdapter.InsideViewHolder> {
    private int mLayoutId;
    private Context mContext;
    private List<Inside> insideList;
    public InsideAdapter(List<Inside> data, int layoutId, Context context) {
        mLayoutId = layoutId;
        insideList = data;
        mContext = context;
    }
    public void updateData(List<Inside> data) {
        //data中所有数据都添加到mData中
        insideList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InsideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lilt, parent,false);
        return new InsideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsideViewHolder holder, int position) {
        InsideViewHolder insideViewHolder=(InsideViewHolder)holder;
        Inside inside=insideList.get(position);
        insideViewHolder.title.setText(inside.getName());
        insideViewHolder.rating.setText(inside.getRating());
        insideViewHolder.price.setText(inside.getPrice());
        insideViewHolder.tag.setText(inside.getTag());
        //insideViewHolder.image.setImageResource(inside.getImage());
        Glide.with(mContext).load(inside.getImage()).into(insideViewHolder.image);

        insideViewHolder.ratingBar.setRating(Float.parseFloat(inside.getRating()));
        insideViewHolder.clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(mContext, SpotActivity.class);
                intent1.putExtra("id",inside.getId().toString());
                mContext.startActivity(intent1);
            }
        });
    }



    @Override
    public int getItemCount() {
        if (insideList!=null)
            return insideList.size();
        else
            return 0;
    }
    public static class InsideViewHolder extends RecyclerView.ViewHolder {
        View clickView;
        MaterialRatingBar ratingBar;

        TextView title;
        TextView rating;
        TextView price;
        TextView tag;
        ImageView image;

        public InsideViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView = itemView;
            ratingBar = itemView.findViewById(R.id.re_materialRatingBar01);
            title = itemView.findViewById(R.id.re_name01);
            rating = itemView.findViewById(R.id.re_rating01);
            price = itemView.findViewById(R.id.re_price01);
            tag = itemView.findViewById(R.id.re_tag01);
            image = itemView.findViewById(R.id.icon01);
        }
    }
}
