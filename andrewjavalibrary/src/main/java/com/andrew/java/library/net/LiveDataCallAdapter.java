package com.andrew.java.library.net;

import androidx.lifecycle.LiveData;

import com.andrew.java.library.utils.ToastUtils;

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
 * created on: 2021/7/1 14:15
 * description:
 */
public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>> {
    private Type responseType;

    LiveDataCallAdapter(Type type) {
        responseType = type;
    }

    @NotNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NotNull
    @Override
    public LiveData<T> adapt(@NotNull final Call<T> call) {
        return new LiveData<T>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> call, Response<T> response) {
                            if (response.code() == 200) {
                                postValue(response.body());
                            } else {
                                postValue(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
//                            ToastUtils.show(t.getMessage());
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
                            ToastUtils.show(message);
//                            postValue(null);
                        }
                    });
                }
            }
        };
    }
}
