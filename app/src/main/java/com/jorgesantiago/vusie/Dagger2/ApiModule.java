package com.jorgesantiago.vusie.Dagger2;

import com.jorgesantiago.vusie.NewsAPI.ApiManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class ApiModule {

    @Provides
    public ApiManager newsApiManager(Retrofit retrofit) {
        return new ApiManager(retrofit);
    }

    @VusieApplicationScope
    @Provides
    public Retrofit newsApiRetroFit(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl("http://newsapi.org")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
