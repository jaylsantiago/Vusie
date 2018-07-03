package com.jorgesantiago.vusie.dagger;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Module for our Dagger Graph that provides elements for Networking.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }
}
