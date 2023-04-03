package com.example.yiyoua13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.ui.SpotActivity;
import com.example.yiyoua13.variousclass.Spot;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.SpotViewHolder> {
    private List<Spot> spots;
    private Context context;
    public SpotAdapter( Context context,List<Spot> spots) {
        this.spots = spots;
        this.context = context;
    }
    public void updateData(List<Spot> data) {
        //data中所有数据都添加到mData中
        spots.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SpotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lift, parent, false);

        return new SpotViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SpotViewHolder holder, int position) {
        Spot spot = spots.get(position);
        holder.name.setText(spot.getName());
        holder.address.setText(spot.getAddress());
        holder.description.setText(spot.getDescription());
        holder.stars.setText(spot.getStars());
        holder.price.setText(spot.getPrice());
        holder.distance.setText(spot.getDistance());
        holder.kind.setText(spot.getKind());
        holder.ratingBar.setRating(Float.parseFloat(spot.getStars()));
        Glide.with(holder.image.getContext()).load(spot.getImage()).placeholder(R.drawable.hog).error(R.drawable.lwr).into(holder.image);

        ((SpotViewHolder)holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Spot spot = spots.get(position);
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SpotActivity.class);
                intent.putExtra("spot", spot.getName());
                context.startActivity(intent);
                //销毁当前Activity
                //((Activity)context).finish();

            }
        });

    }
    @Override
    public int getItemCount() {
        return spots.size();
    }
    public class SpotViewHolder extends RecyclerView.ViewHolder {
        View view;
        private TextView name;
        private TextView address;
        private TextView description;
        private MaterialRatingBar ratingBar;
        private ImageView image;
        private TextView stars;
        private TextView price;
        private TextView distance;
        private TextView kind;
        public SpotViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.area);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.icon);
            stars = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.price);
            distance = itemView.findViewById(R.id.distance);
            kind = itemView.findViewById(R.id.kind);
            ratingBar = itemView.findViewById(R.id.materialRatingBar);
        }
    }
}

