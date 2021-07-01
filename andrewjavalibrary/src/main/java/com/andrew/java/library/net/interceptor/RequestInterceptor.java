package com.andrew.java.library.net.interceptor;

import android.text.TextUtils;

import com.andrew.java.library.utils.SpUtil;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: zhangbin
 * created on: 2021/7/1 15:44
 * description:
 */
public class RequestInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
//        Logger.e("RequestInterceptor intercept( Chain chain)");

        Request originalRequest = chain.request();//获取原始请求
        Request.Builder requestBuilder = originalRequest.newBuilder() //建立新的请求
//                    .header("Connection", "close")
                .addHeader("Connection", "close")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
//                    .addHeader("filePlatform", "task")
                .removeHeader("User-Agent")
//                    .addHeader("User-Agent", BaseUtils.getUserAgent())
                .method(originalRequest.method(), originalRequest.body());

        String token = SpUtil.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(requestBuilder.build());
        }
//            tokenRequest = requestBuilder.header("Authorization", Credentials.basic("token", "")).build();
        Request tokenRequest = requestBuilder.header("token", token)
//                    .header("companyId", SpUtil.getInstance().getCurrentCompanyId())
                .build();
        return chain.proceed(tokenRequest);
    }
}
