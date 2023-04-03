package com.example.yiyoua13;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiyoua13.ui.SpotActivity;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class VSAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<VSBean> mData;

    public static class VSBean {
        public String name;
        public String stars;
        public String price;
        public String location;
        public String kind;
        public String distance;
    }
    public VSAdapter(Context context, List<VSBean> data) {
        mContext = context;
        mData = data;
    }
    public void updateData(List<VSBean> data) {
        //data中所有数据都添加到mData中
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_lijt, null);

        return new MyViewHolder(view);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View clickView;
        public TextView name;
        public MaterialRatingBar stars;
        public TextView price;
        public TextView location;
        public TextView kind;
        public TextView distance;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView = itemView;
            name = itemView.findViewById(R.id.spot_name);
            stars = itemView.findViewById(R.id.spot_rating);
            price = itemView.findViewById(R.id.spot_price);
            location = itemView.findViewById(R.id.spot_address);
            kind = itemView.findViewById(R.id.spot_kind);
            distance = itemView.findViewById(R.id.spot_distance);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.name.setText(mData.get(position).name);
        myViewHolder.stars.setRating(Float.parseFloat(mData.get(position).stars));
        myViewHolder.price.setText(mData.get(position).price);
        myViewHolder.location.setText(mData.get(position).location);
        myViewHolder.kind.setText(mData.get(position).kind);
        myViewHolder.distance.setText(mData.get(position).distance);

        ((MyViewHolder) holder).clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                VSBean bean = mData.get(position);
                Intent intent = new Intent(mContext, SpotActivity.class);
                intent.putExtra("spot", bean.name);
                mContext.startActivity(intent);
                //销毁当前Activity
                //((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }
}
