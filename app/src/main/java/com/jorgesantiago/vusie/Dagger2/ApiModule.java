package com.jorgesantiago.vusie.Dagger2;

import com.jorgesantiago.vusie.NewsAPI.ApiManager;

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

    @Provides
    public ApiManager newsApiManager(Retrofit retrofit) {
        return new ApiManager(retrofit);
    }

    @Singleton
    @Provides
    public Retrofit newsApiRetroFit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                //TODO add the /v2 to base url
                .baseUrl("http://newsapi.org")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
