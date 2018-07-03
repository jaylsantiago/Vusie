package com.jorgesantiago.vusie.dagger;

import com.jorgesantiago.vusie.ui.ArticlesFragment;
import com.jorgesantiago.vusie.ui.BrowseArticlesActivity;
import com.jorgesantiago.vusie.ui.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Module for our Dagger Graph that binds our subcomponents. This module is dependent on elements
 * defined in the AndroidSupportInjectionModule so we include that module in this one.
 * <p>
 * The @ContributesAndroidInjector annotation builds our concrete subcomponents for us (Dagger will generate the code)
 * instead of us manually building out the subcomponents.
 * We can do this since our Subcomponents are extremely simple and lack complexity.
 */
@Module(includes = AndroidSupportInjectionModule.class)
public abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract SplashScreenActivity bindsSplashScreenActivity();

    @ContributesAndroidInjector
    abstract BrowseArticlesActivity bindsBrowseArticlesActivity();

    @ContributesAndroidInjector
    abstract ArticlesFragment bindsArticlesFragment();
}
