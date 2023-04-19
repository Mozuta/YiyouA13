package com.example.yiyoua13;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.ui.PCenter_Activity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.fans;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PersonAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<fans> fansList; //定义数据源
    public PersonAdapter(Context context, List<fans> data) {
        mContext = context;
        fansList = data;
    }
    public void updateData(List<fans> data) {
        //data中所有数据都添加到mData中
        fansList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_libt, null);
        return new PAViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SharedPreferences sp;
        String user_token,user_id,if_follow;
        sp = mContext.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        PAViewHolder paViewHolder=(PAViewHolder)holder;
        fans fans=fansList.get(position);
        Glide.with(mContext).load(fans.getFans_headpic()).placeholder(R.drawable.hog).error(R.drawable.lwr).into(paViewHolder.avatar);;
        paViewHolder.name.setText(fans.getFans_name());
        paViewHolder.introduction.setText(fans.getIntroduction());
        Url_Request.sendRequestFollowOrNot(Url_Request.getUrl_head() + "/follow/or/not/" + fans.getFans_id(), user_token, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Boolean if_follow=(Boolean)bean;
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (if_follow){
                            paViewHolder.follow.setText("已关注");
                        }
                        else {
                            paViewHolder.follow.setText("关注");
                        }
                        //notifyDataSetChanged();
                    }
                });


            }
        });
        paViewHolder.fans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PCenter_Activity.class);
                intent.putExtra("id",fans.getFans_id());
                mContext.startActivity(intent);
            }
        });
        paViewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Url_Request.sendRequestFollowFollowed(Url_Request.getUrl_head()+"/follow/followed/"+fans.getFans_id().toString(),user_token);
                if (paViewHolder.follow.getText() == "关注") {
                    paViewHolder.follow.setText("已关注");
                } else {
                    paViewHolder.follow.setText("关注");
                }

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
        ConstraintLayout fans;
        TextView introduction;
        Button follow;

        public PAViewHolder(@NonNull View itemView) {
            super(itemView);
            fans=itemView.findViewById(R.id.headin);
            clickView=itemView;
            avatar=itemView.findViewById(R.id.tx);
            name=itemView.findViewById(R.id.namein);
            introduction=itemView.findViewById(R.id.introduction);
            follow=itemView.findViewById(R.id.follow_btn);
        }
    }

}
