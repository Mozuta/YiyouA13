package com.example.yiyoua13.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TextLoaderTask extends AsyncTask<Void, Void, String> {
    private WeakReference<TextView> textViewRef;
    private String text;

    public TextLoaderTask(TextView textView, String text) {
        textViewRef = new WeakReference<>(textView);
        this.text = text;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // 这里可以进行一些耗时操作，比如从本地或网络获取文本内容
        // 为了示例，这里直接返回构造函数传入的文本内容
        return text;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        TextView textView = textViewRef.get();
        if (textView != null) {
            textView.setText(s);
        }
    }
}

