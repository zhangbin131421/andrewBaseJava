package com.andrew.java.library.net.interceptor;

import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: zhangbin
 * created on: 2021/7/1 15:49
 * description:
 */
public class ResponseInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 500) {
//                isTokenExpired(response);
        }
        Logger.d("response.code()= " + response.code());
        Logger.d("response.body()= " + response.body().string());
        return response;
    }
}
