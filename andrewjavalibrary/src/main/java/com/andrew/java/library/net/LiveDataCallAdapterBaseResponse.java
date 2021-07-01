package com.andrew.java.library.net;

import androidx.lifecycle.LiveData;

import com.andrew.java.library.model.AndrewResponse;

import org.apache.http.conn.ConnectTimeoutException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: zhangbin
 * created on: 2021/7/1 14:31
 * description:
 */
public class LiveDataCallAdapterBaseResponse implements CallAdapter<AndrewResponse, LiveData<AndrewResponse>> {

    private Type responseType;

    public LiveDataCallAdapterBaseResponse(Type type) {
        responseType = type;
    }

    @NotNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NotNull
    @Override
    public LiveData<AndrewResponse> adapt(@NotNull final Call<AndrewResponse> call) {
        return new LiveData<AndrewResponse>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                call.enqueue(new Callback<AndrewResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AndrewResponse> call, @NotNull Response<AndrewResponse> response) {
                        postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<AndrewResponse> call, Throwable t) {
                        String message = "";
                        if (t instanceof SocketTimeoutException || t instanceof ConnectTimeoutException) {
                            message = "网络连接超时，请稍后再试。";
                        } else if (t instanceof ConnectException) {
                            message = "网络连接失败，请检查网络。";
                        } else if (t instanceof UnknownHostException) {
                            message = "网络连接失败，请检查网络。";
                        } else {
                            message = "网络连接失败。";
                        }
                        postValue(new AndrewResponse(0, message));
                    }
                });
            }
        };
    }


}
