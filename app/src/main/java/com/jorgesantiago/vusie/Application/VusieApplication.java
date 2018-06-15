package com.jorgesantiago.vusie.application;

import android.app.Activity;
import android.app.Application;

import com.jorgesantiago.vusie.Dagger2.DaggerVusieApplicationComponent;
import com.jorgesantiago.vusie.Dagger2.RoomDatabaseModule;
import com.jorgesantiago.vusie.Dagger2.VusieApplicationComponent;
import com.jorgesantiago.vusie.RoomDB.ArticleRepository;

public class VusieApplication extends Application {

    private ArticleRepository articleRepository;

    public static VusieApplication get(Activity activity) {
        return (VusieApplication) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        VusieApplicationComponent vusieApplicationComponent = DaggerVusieApplicationComponent.builder()
                .roomDatabaseModule(new RoomDatabaseModule(this))
                .build();

        articleRepository = vusieApplicationComponent.getArticleRepository();

        articleRepository.refreshRepository();
    }

    public ArticleRepository articleRepository() {
        return articleRepository;
    }

}
