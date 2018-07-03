package com.jorgesantiago.vusie.dagger;

import com.jorgesantiago.vusie.api.request.NewsApiManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Module for our Dagger Graph that provides elements for API requests and consumption. This module is dependent on elements
 * defined in the NetworkModule so we include that module in this one.
 */
@Module(includes = NetworkModule.class)
public class ApiModule {

    @Singleton
    @Provides
    public NewsApiManager newsApiManager(Retrofit retrofit) {
        return new NewsApiManager(retrofit);
    }

    @Singleton
    @Provides
    public Retrofit newsApiRetroFit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://newsapi.org/v2/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
