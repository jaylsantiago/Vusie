package com.jorgesantiago.vusie.dagger;

import com.jorgesantiago.vusie.application.VusieApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Our top level component in our dependency graph. This component has access to all the modules and subcomponents we need in order
 * to provide us the dependencies we need when we ask for them.
 */
@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        DataModule.class, // this module includes ApiModule(includes NetworkModule), so no need to explicitly list them here
        BuildersModule.class
})
public interface VusieApplicationComponent extends AndroidInjector<VusieApplication> {

    @Override
    void inject(VusieApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(VusieApplication application);

        VusieApplicationComponent build();
    }
}
