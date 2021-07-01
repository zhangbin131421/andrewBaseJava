package com.andrew.java.library.vmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * author: zhangbin
 * created on: 2021/6/29 14:10
 * description:
 */
public class AndrewLoadingViewModel extends AndrewViewModel {
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshTrigger = new MutableLiveData<>();

    public void attachLoading(final MutableLiveData<Boolean> otherState) {
        loading.observeForever(new Observer<Boolean>() {
                                   @Override
                                   public void onChanged(Boolean it) {
                                       otherState.postValue(it);
                                   }
                               }
        );
    }

    void refreshLoading() {
        loading.postValue(true);
        refreshTrigger.postValue(true);
    }

    void refreshPullDown() {
        refreshTrigger.postValue(true);
    }
}
