package com.example.yiyoua13;

import static com.example.yiyoua13.R.drawable.selector_grey;
import static com.example.yiyoua13.R.drawable.selector_orange;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yiyoua13.variousclass.ChoseItem;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ChoseTagAdapter extends RecyclerView.Adapter<ChoseTagAdapter.ChoseTagViewHolder> {

    private Context mContext;
    public static List<ChoseItem> mTagList;
    private HashSet<String> mNameSet;
    private ChoseEvent mChoseEvent = new ChoseEvent();
    public static class ChoseEvent {
        public HashSet<String> nameSet;
    }
    public HashSet<String> get(){
        return mNameSet;
    }

    public static class ChoseTagViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        View clickView;
        public ChoseTagViewHolder(@NonNull View itemView) {
            super(itemView);
            clickView = itemView;
            tv = (TextView) itemView.findViewById(R.id.tag_chose_btn);
        }
    }



    public ChoseTagAdapter(Context context, List<ChoseItem> tagList) {
        mContext = context;
        mTagList = tagList;
    }
    public ChoseTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_liet, parent, false);

        return new ChoseTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoseTagViewHolder holder, int position) {
        mNameSet = new HashSet<>();
        ChoseTagViewHolder holder2 = (ChoseTagViewHolder) holder;
        ChoseItem choseItem = mTagList.get(position);
        holder2.tv.setText(choseItem.getName());
        ((ChoseTagViewHolder) holder).clickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder2.getBindingAdapterPosition();
                boolean isChose = mTagList.get(position).isChose();
                if (!isChose) {
                    if (mNameSet.size() == 8) {
                        Toast.makeText(mContext, "最多选择八个", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mTagList.get(position).setChose(true);
                    mNameSet.add(mTagList.get(position).getName());
                } else {
                    mTagList.get(position).setChose(false);
                    mNameSet.remove(mTagList.get(position).getName());
                }


                updateStatus(holder2.tv, isChose, mNameSet);
                mChoseEvent.nameSet=(mNameSet);
                EventBus.getDefault().post(mChoseEvent);

            }
        });
    }






    @Override
    public int getItemCount() {
        if(mTagList != null)
            return mTagList.size();
        else
            return 0;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateStatus(TextView textView, boolean isChose, HashSet<String> set) {
        if (!isChose) {
            if (set.size() < 9) {
                textView.setBackground(mContext.getResources().getDrawable(selector_orange));
                textView.setTextColor(Color.WHITE);
            }
        } else {
            textView.setBackground(mContext.getResources().getDrawable(selector_grey));
            textView.setTextColor(Color.parseColor("#4a4a4a"));
        }
    }

}
