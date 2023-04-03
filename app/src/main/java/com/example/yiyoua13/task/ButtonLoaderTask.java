package com.example.yiyoua13.task;

import android.os.AsyncTask;
import android.widget.Button;

import java.lang.ref.WeakReference;

public class ButtonLoaderTask extends AsyncTask<Void, Void, String> {
    private WeakReference<Button> buttonRef;
    private String buttonText;

    public ButtonLoaderTask(Button button, String buttonText) {
        buttonRef = new WeakReference<>(button);
        this.buttonText = buttonText;
    }

    @Override
    protected String doInBackground(Void... voids) {
        // 模拟耗时操作，这里直接返回 buttonText
        return buttonText;
    }

    @Override
    protected void onPostExecute(String buttonText) {
        Button button = buttonRef.get();
        if (button != null) {
            button.setText(buttonText);
        }
    }
}

