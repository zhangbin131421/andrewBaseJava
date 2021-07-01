package com.andrew.java.library.net;

import com.andrew.java.library.net.interceptor.RequestInterceptor;
import com.andrew.java.library.net.interceptor.ResponseInterceptor;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.orhanobut.logger.BuildConfig;

import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import static android.util.Log.VERBOSE;

public class RetrofitHelper {
    private static RetrofitHelper instance;
    private static final int DEFAULT_TIME_OUT = 25;
    private static final int DEFAULT_READ_TIME_OUT = 0;

    private Interceptor requestInterceptor;
    private Interceptor responseInterceptor;

    private RetrofitHelper() {
    }

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    private OkHttpClient getOkClient() {
        LoggingInterceptor LoggingInterceptor = new LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(VERBOSE)
//                .addHeader("cityCode", "53")
//                .addQueryParam("moonStatus", "crescent")
                .build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), new
                    X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    })//配置
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier());//配置
        }
        builder.retryOnConnectionFailure(true)
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.MILLISECONDS)
//                .followRedirects(false)
//                .cache(new Cache(new File("sdcard/cache","okhttp"),1024))
//                .addNetworkInterceptor(loggingInterceptor)
                .addInterceptor(LoggingInterceptor)
//                .addInterceptor(new RequestInterceptor())
//                .addInterceptor(new RespInterceptor())
        ;
        if (requestInterceptor != null) {
            builder.addInterceptor(requestInterceptor);
        } else {
            builder.addInterceptor(new RequestInterceptor());
        }
        if (responseInterceptor != null) {
            builder.addInterceptor(responseInterceptor);
        } else {
            builder.addInterceptor(new ResponseInterceptor());
        }
        return builder.build();
    }

    public Retrofit.Builder getBuilder(String apiUrl) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getOkClient());
        builder.baseUrl(apiUrl);
        builder.addConverterFactory(MyGsonConverterFactory.create());
        builder.addCallAdapterFactory(new LiveDataCallAdapterFactory());
        return builder;
    }

    public void setRequestInterceptor(Interceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    public void setResponseInterceptor(Interceptor responseInterceptor) {
        this.responseInterceptor = responseInterceptor;
    }

}
