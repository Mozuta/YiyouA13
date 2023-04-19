package com.example.yiyoua13;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiyoua13.ui.TagActivity;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<tag> mData;
    public static class tag{
        private String id;
        private String name;
        private String kind;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public tag(String name,String kind){
            this.name=name;
            this.kind=kind;
        }
        public tag(){
            this.name="";
            this.kind="";
        }
        public void setName(String name){
            this.name=name;
        }
        public void setKind(String kind){
            this.kind=kind;
        }
        public String getName(){
            return name;
        }
        public String getKind(){
            return kind;
        }

    }
    public void updateData(List<tag> data) {
        //data中所有数据都添加到mData中
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public TagAdapter(Context context, List<tag> data) {
        mContext = context;
        mData = data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_liht, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder= (MyViewHolder) holder;
        myViewHolder.name.setText(mData.get(position).getName());
        myViewHolder.kind.setText(mData.get(position).getKind());
        ((MyViewHolder) holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
                tag tag=mData.get(position);
                Intent intent=new Intent();
                String str = tag.name;
                String tagid=tag.id;
                str = str.replaceAll("^\\S+\\s+|\\s{2,}|\\s+$", "");
                intent.putExtra("id",tagid);
                intent.putExtra("tag",str);
                //返回上一个活动
                ((TagActivity)mContext).setResult(RESULT_OK,intent);
                ((TagActivity)mContext).finish();

                //Toast.makeText(mContext, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
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
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        private TextView name;
        private TextView kind;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            name=itemView.findViewById(R.id.tag_name);
            kind=itemView.findViewById(R.id.tag_kind);
        }
    }
}
