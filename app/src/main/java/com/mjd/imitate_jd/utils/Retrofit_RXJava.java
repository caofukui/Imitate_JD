package com.mjd.imitate_jd.utils;

import com.mjd.imitate_jd.api.GetInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_RXJava {
    private static final int DEFAULT_TIMEOUT = 5;
    private static Retrofit_RXJava retrofit_rxJava=null;
    private static Retrofit retrofit;
    private static GetInterface mInterface;

    public static Retrofit_RXJava getInstance() {
        if (retrofit_rxJava==null){
            retrofit_rxJava=new Retrofit_RXJava();
        }
        return retrofit_rxJava;
    }

    private Retrofit_RXJava() {
    }

    public GetInterface getInterface(){
        return mInterface==null ? configRetrofit(GetInterface.class):mInterface;
    }

    private <T> T configRetrofit(Class<T> service){
        retrofit = new Retrofit.Builder()
                .baseUrl(JDApi.BAST)
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    private OkHttpClient configClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }
}
