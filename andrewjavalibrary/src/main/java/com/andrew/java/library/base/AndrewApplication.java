package com.andrew.java.library.base;

import androidx.multidex.MultiDexApplication;

import com.tencent.mmkv.MMKV;

/**
 * author: zhangbin
 * created on: 2021/6/29 11:26
 * description:
 */
public class AndrewApplication extends MultiDexApplication {
    private static AndrewApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        MultiDex.install(this);
        MMKV.initialize(this);// /data/user/0/com.szybkj.task/files/mmkv
    }

    public static AndrewApplication getInstance() {
        return instance;
    }

    void jumpLoginActivity() {
    }
}
