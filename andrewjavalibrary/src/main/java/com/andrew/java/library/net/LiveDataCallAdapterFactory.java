package com.andrew.java.library.net;

import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * author: zhangbin
 * created on: 2021/7/1 14:06
 * description:
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(@NotNull Type returnType, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        if (rawType != LiveData.class) {
            throw new IllegalStateException("return type must be LiveData");
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        Type responseType;
        if (rawObservableType.isInstance(Response.class)) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalArgumentException("Response must be ParameterizedType");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);

        } else {
            responseType = observableType;
        }

        return new LiveDataCallAdapter(responseType);
    }
}
