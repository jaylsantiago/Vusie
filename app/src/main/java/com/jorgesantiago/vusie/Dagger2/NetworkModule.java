package com.jorgesantiago.vusie.Dagger2;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    @Provides
    @VusieApplicationScope
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }
}