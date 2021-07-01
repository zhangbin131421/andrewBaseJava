package com.example.demojava.base;

import androidx.annotation.Nullable;

import com.andrew.java.library.base.AndrewApplication;
import com.example.demojava.BuildConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * author: zhangbin
 * created on: 2021/7/1 16:50
 * description:
 */
public class BaseApplication extends AndrewApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }
}
