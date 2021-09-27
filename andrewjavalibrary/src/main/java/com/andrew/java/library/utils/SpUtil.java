package com.andrew.java.library.utils;


import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

public class SpUtil {
    private static volatile SpUtil spUtil = null;
    private final MMKV mmkv;

    private SpUtil() {
        mmkv = MMKV.defaultMMKV();
    }

    public static SpUtil getInstance() {
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                if (spUtil == null) {
                    spUtil = new SpUtil();
                }
            }
        }
        return spUtil;
    }

    public void removeKey(String key) {
        mmkv.removeValueForKey(key);
    }

    public void clear(String key) {
        mmkv.clearAll();
    }

    public void putString(String key, String value) {
        mmkv.encode(key, value);
    }

    public String getString(String key) {
        return mmkv.decodeString(key, "");
    }

    public void putInt(String key, int value) {
        mmkv.encode(key, value);
    }

    public int getInt(String key) {
        return mmkv.decodeInt(key, 0);
    }

    public void putLong(String key, long value) {
        mmkv.encode(key, value);
    }

    public long getLong(String key) {
        return mmkv.decodeLong(key, 0);
    }


    public void putFloat(String key, float value) {
        mmkv.encode(key, value);
    }

    public float getFloat(String key) {
        return mmkv.decodeFloat(key, 0F);
    }

    public void putBoolean(String key, boolean value) {
        mmkv.encode(key, value);
    }

    public boolean getBoolean(String key) {
        return mmkv.decodeBool(key, false);
    }

    public void putParcelable(String key, Parcelable value) {
        mmkv.encode(key, value);
    }

    public <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        return mmkv.decodeParcelable(key, tClass);
    }


    public void setToken(String token) {
        putString(ConstantsUtil.TOKEN, token);
    }

    public String getToken() {
        return getString(ConstantsUtil.TOKEN);
    }


}