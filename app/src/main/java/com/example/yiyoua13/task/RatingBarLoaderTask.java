package com.example.yiyoua13.task;

import android.os.AsyncTask;
import android.widget.Button;

import java.lang.ref.WeakReference;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingBarLoaderTask extends AsyncTask<Void, Void, String>{
    private WeakReference<MaterialRatingBar> materialRatingBar;
    private String ratingBarText;

    public RatingBarLoaderTask(MaterialRatingBar ratingBar, String ratingBarText) {
        materialRatingBar = new WeakReference<>(ratingBar);
        this.ratingBarText = ratingBarText;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // 模拟耗时操作，这里直接返回 buttonText
        return ratingBarText;
    }

    @Override
    protected void onPostExecute(String ratingBarText) {
        MaterialRatingBar ratingBar = materialRatingBar.get();
        if (ratingBar != null) {
            ratingBar.setRating(Float.parseFloat(ratingBarText));
        }
    }
}
