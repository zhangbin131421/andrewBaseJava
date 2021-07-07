package com.example.demojava.ui.fragment;


import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.andrew.java.library.model.AndrewResponse;
import com.example.demojava.base.BaseViewModel;
import com.example.demojava.model.AppUpdate;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author: zhangbin
 * created on: 2021/6/29 16:55
 * description:
 */
public class DemoVM extends BaseViewModel {
    public MutableLiveData<String> name = new MutableLiveData<>("MainVM");
    LiveData<AndrewResponse<AppUpdate>> app = Transformations.switchMap(refreshTrigger, new Function<Boolean, LiveData<AndrewResponse<AppUpdate>>>() {
        @Override
        public LiveData<AndrewResponse<AppUpdate>> apply(Boolean input) {
            return api.getNewAppVersion(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ""));
        }
    });

}
