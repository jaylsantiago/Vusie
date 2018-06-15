package com.jorgesantiago.vusie.Dagger2;

import com.jorgesantiago.vusie.RoomDB.ArticleRepository;

import dagger.Component;

@VusieApplicationScope
@Component(modules = RoomDatabaseModule.class)
public interface VusieApplicationComponent {

    ArticleRepository getArticleRepository();

}
