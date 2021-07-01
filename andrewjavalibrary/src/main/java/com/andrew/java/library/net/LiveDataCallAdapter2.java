package com.andrew.java.library.net;

import androidx.lifecycle.LiveData;

import com.andrew.java.library.model.AndrewResponse;
import com.orhanobut.logger.Logger;

import org.apache.http.conn.ConnectTimeoutException;

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
public class LiveDataCallAdapter2<T> implements CallAdapter<T, LiveData<T>> {
    private Type responseType;

    LiveDataCallAdapter2(Type type) {
        responseType = type;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<T> adapt(final Call<T> call) {
        return new LiveData<T>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> call, Response<T> response) {
                            switch (response.code()) {
                                case 200:
                                    postValue(response.body());
                                    break;
                                case 500:
                                    Logger.e("onResponse.errorBody()======$body");
                                    postValue((T) new AndrewResponse(0, "服务器开小差了，请稍后再试"));

                                    break;
                                default:
                                    postValue((T) new AndrewResponse<T>(0,  "服务器异常，请稍后再试"));
//                                    postValue(null);
                                    break;
                            }

                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
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
                            postValue((T) new AndrewResponse<T>(0, message));

                        }
                    });
                }
            }
        };
    }
}
