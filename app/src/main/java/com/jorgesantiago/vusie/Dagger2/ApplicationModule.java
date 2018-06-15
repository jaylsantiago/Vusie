package com.jorgesantiago.vusie.Dagger2;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @VusieApplicationScope
    public Application providesApplication() {
        return application;
    }
}
