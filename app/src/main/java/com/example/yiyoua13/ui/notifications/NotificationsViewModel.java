package com.example.yiyoua13.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("帖子界面");
    }

    public LiveData<String> getText() {
        return mText;
    }
}