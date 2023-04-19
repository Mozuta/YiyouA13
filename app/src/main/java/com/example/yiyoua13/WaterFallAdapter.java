package com.example.yiyoua13;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.ui.InfoActivity;
import com.example.yiyoua13.ui.MineActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.fans;
import com.facebook.drawee.view.SimpleDraweeView;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WaterFallAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private List<PersonCard> mData; //定义数据源

    public static class PersonCard implements Serializable {
        public String id;
        public String avatarUrl; //大图片的Url
        public String headurl; //头像的Url
        public String name;  //名字
        public int imgHeight;  //头像图片的高度
        public String content;//内容
        public String like;//点赞数
        public boolean islike;//是否点赞

    }
    public int clearData() {

        mData.clear();
        notifyDataSetChanged();
        return 1;
    }
    //定义构造方法，默认传入上下文和数据源
    public WaterFallAdapter(Context context, List<PersonCard> data) {
        mContext = context;
        mData = data;
    }
    public void updateData(List<PersonCard> data) {
        //data中所有数据都添加到mData中
        mData.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override  //将ItemView渲染进来，创建ViewHolder
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.item_liat, null);

        return new MyViewHolder(view);
    }

    @Override  //将数据源的数据绑定到相应控件上
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder2 = (MyViewHolder) holder;
        PersonCard personCard = mData.get(position);
        Glide.with(holder2.userAvatar.getContext()).load(personCard.avatarUrl).override(180,200).placeholder(R.drawable.hog).error(R.drawable.lwr).into(holder2.userAvatar);
        Glide.with(holder2.head.getContext()).load(personCard.headurl).placeholder(R.drawable.hog).error(R.drawable.lwr).into(holder2.head);
        holder2.userAvatar.getLayoutParams().height = personCard.imgHeight; //从数据源中获取图片高度，动态设置到控件上
        holder2.userName.setText(personCard.name);
        holder2.content.setText(personCard.content);
        holder2.like.setText(personCard.like);

        //长按事件
        holder2.clickView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //如果当前是InfoActivity，那么显示Toast
                if (mContext instanceof MineActivity) {
                    int position0 = holder.getBindingAdapterPosition();
                    PersonCard personCard = mData.get(position0);
                    //Toast.makeText(mContext, "长按"+personCard.name, Toast.LENGTH_SHORT).show();
                    new XPopup.Builder(mContext)
                            .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                            .asAttachList(new String[]{"删除", "取消"},
                                    new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher},
                                    new OnSelectListener() {
                                        @Override
                                        public void onSelect(int position, String text) {
                                            //删除
                                            if (position == 0) {
                                                mData.remove(position0);
                                                notifyDataSetChanged();
                                                SharedPreferences sp = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);

                                                String token = sp.getString("TOKEN","");
                                                Log.e("token",token);
                                                Toast.makeText(mContext, "已删除", Toast.LENGTH_SHORT).show();
                                                Url_Request.sendRequestBlogDelete(Url_Request.getUrl_head()+"/blog/delete/"+personCard.id,token);
                                            }
                                            //取消
                                            else if (position == 1) {
                                                Toast.makeText(mContext, "取消", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                            .show();
                }
                return true;
            }
        });

        //final MyViewHolder holder = new MyViewHolder(view);
        ((MyViewHolder) holder).clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);

                String token = sp.getString("TOKEN","");
                /*if (token.equals("")){
                    Toast.makeText(mContext,"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, FragAct.class);
                    ((Activity)mContext).startActivity(intent);
                }*/

                    int position = holder.getBindingAdapterPosition();
                    PersonCard personCard = mData.get(position);
                    Intent intent = new Intent(mContext, TestActivity.class);
                    intent.putExtra("person_data", personCard.id);
                    mContext.startActivity(intent);


                //销毁当前Activity
                //((Activity)mContext).finish();
            }


           /* public void onLongClick(View v) {
                //如果当前是InfoActivity，那么显示Toast
                if (mContext instanceof MineActivity) {
                    int position = holder.getBindingAdapterPosition();
                    PersonCard personCard = mData.get(position);
                    Toast.makeText(mContext, "长按"+personCard.name, Toast.LENGTH_SHORT).show();
                }

            }*/
        });
        ((MyViewHolder) holder).likeButton.setLiked(personCard.islike);
        ((MyViewHolder) holder).likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                SharedPreferences sp = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);

                String token = sp.getString("TOKEN","");
                if (token.equals("")){
                    Toast.makeText(mContext,"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, FragAct.class);
                    ((Activity)mContext).startActivity(intent);
                }
                else {
                    int position = holder.getBindingAdapterPosition();
                    PersonCard personCard = mData.get(position);
                    String likenum=((MyViewHolder) holder).like.getText().toString();
                    SharedPreferences sp2 = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String token2 = sp2.getString("TOKEN", "");
                    Url_Request.sendRequestBlogUserLike(Url_Request.getUrl_head()+"/blog/like/"+personCard.id,token2, new Url_Request.OnIconResponseListener() {
                        @Override
                        public void onBeanResponse(Object bean) {

                        }
                    });
                    if(likenum.equals("99+")){
                    }
                    else{
                        int likenum1=Integer.parseInt(likenum);
                        likenum1++;
                        ((MyViewHolder) holder).like.setText(String.valueOf(likenum1));
                    }
                }




            }

            @Override
            public void unLiked(LikeButton likeButton) {
                int position = holder.getBindingAdapterPosition();
                PersonCard personCard = mData.get(position);
                String likenum=((MyViewHolder) holder).like.getText().toString();
                SharedPreferences sp = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
                String token = sp.getString("TOKEN", "");
                if (token.equals("")){
                    Toast.makeText(mContext,"请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, FragAct.class);
                    ((Activity)mContext).startActivity(intent);
                }
                else {

                    SharedPreferences sp2 = mContext.getSharedPreferences("Login", Context.MODE_PRIVATE);
                    String token2 = sp2.getString("TOKEN", "");
                    Url_Request.sendRequestBlogUserLike(Url_Request.getUrl_head()+"/blog/like/"+personCard.id,token2, new Url_Request.OnIconResponseListener() {
                        @Override
                        public void onBeanResponse(Object bean) {

                        }
                    });
                    if(likenum.equals("99+")){
                    }
                    else{
                        int likenum1=Integer.parseInt(likenum);
                        likenum1--;
                        ((MyViewHolder) holder).like.setText(String.valueOf(likenum1));
                    }
                }


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

    //定义自己的ViewHolder，将View的控件引用在成员变量上
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View clickView;
        public ImageView userAvatar;
        public CircleImageView head;
        public LikeButton likeButton;

        public TextView userName;
        public TextView content;
        public TextView like;

        public MyViewHolder(View itemView) {
            super(itemView);
            clickView = itemView;
            //Glide.with(itemView.getContext()).load(R.drawable.ic_launcher_background).into(userAvatar);
            userAvatar = (ImageView) itemView.findViewById(R.id.mainpic);
            userName = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.textpic);
            like = (TextView) itemView.findViewById(R.id.like);
            head = (CircleImageView) itemView.findViewById(R.id.tx);
            likeButton = (LikeButton) itemView.findViewById(R.id.likeButton);

        }
    }
}
