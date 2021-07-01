package com.andrew.java.library.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

//    private static final int TOKEN_EXPIRE = 1;  // token过期
//    private static final int SERVER_EXCEPTION = 2;  // 服务器异常

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String originalBody = value.string();
            JSONObject jsonObject = new JSONObject(originalBody);
            boolean success = jsonObject.optInt("code") == 200;
            if (!success) {
                jsonObject.remove("data");
                originalBody = jsonObject.toString();
//                ToastUtils.show(jsonObject.optString("message"));
            }
            return adapter.fromJson(originalBody);
        } catch (JSONException e) {
            Logger.e("convert=====" + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }

//    private void verify(String json) {
//        BaseResponse baseResponse = gson.fromJson(json, BaseResponse.class);
//        Logger.e("json=====" + json);
//        if (!baseResponse.getSuccess()) {
//            switch (baseResponse.getCode()) {
//                case SERVER_EXCEPTION:
//                case TOKEN_EXPIRE:
//                    throw new MyException(baseResponse.getMessage());
//                default:
//                    throw new MyException("不清楚什么原因！");
//            }
//        }
//    }

}