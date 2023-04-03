package com.example.yiyoua13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiyoua13.variousclass.Place;

import java.util.List;

public class PlaceAdapter extends BaseAdapter {
    private List<Place> mPlace;
    private Context mContext;
    public void updateData(List<Place> data) {
        //data中所有数据都添加到mData中
        mPlace.addAll(data);
        notifyDataSetChanged();
    }

    public PlaceAdapter(List<Place> places, Context context) {
        this.mPlace = places;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mPlace.size();
    }

    @Override
    public Object getItem(int i) {
        return mPlace.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        view=_LayoutInflater.inflate(R.layout.item_lict, null);
        if(view!=null)
        {
            TextView textView=(TextView)view.findViewById(R.id.name);
            ImageView imageView=view.findViewById(R.id.icon);
            textView.setText(mPlace.get(position).getName());
            imageView.setImageResource(mPlace.get(position).getImageId());
        }
        return view;
    }
}
