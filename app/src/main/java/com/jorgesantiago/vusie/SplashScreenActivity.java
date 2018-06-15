package com.jorgesantiago.vusie;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.jorgesantiago.vusie.RoomDB.ArticleRepository;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import rx.Observable;
import rx.Subscription;

public class SplashScreenActivity extends AppCompatActivity {
    private View loadingBlue;
    private View loadingGreen;
    private View loadingMidnight;
    private View loadingOrange;
    private View loadingPink;
    private View loadingTeal;
    private Subscription repositoryRefreshSubscription;
    private View appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        appName = findViewById(R.id.vusie_splash_screen_app_name);
        loadingBlue = findViewById(R.id.loadingBlue);
        loadingGreen = findViewById(R.id.loadingGreen);
        loadingMidnight = findViewById(R.id.loadingMidnight);
        loadingOrange = findViewById(R.id.loadingOrange);
        loadingPink = findViewById(R.id.loadingPink);
        loadingTeal = findViewById(R.id.loadingTeal);
    }

    @Override
    protected void onResume() {
        super.onResume();

        repositoryRefreshSubscription = ArticleRepository.notifyWhenRefreshIsComplete()
                .filter(refreshComplete -> refreshComplete)
                .flatMap(refreshDone -> Observable.timer(3, TimeUnit.SECONDS))
                .subscribe(refreshComplete -> {
                    startActivity(new Intent(SplashScreenActivity.this, BrowseArticlesActivity.class));
                    overridePendingTransition(R.anim.scale_and_fade_in, R.anim.scale_and_fade_out);
                    finish();
                }, Throwable::printStackTrace);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        float viewWidthInPixels = loadingBlue.getWidth() * displayMetrics.density;
        float distanceToTravel = screenWidth + viewWidthInPixels;

        long range = (1300 - 900);

        TranslateAnimation animationBlue = new TranslateAnimation(-distanceToTravel, distanceToTravel, 0, 0);
        animationBlue.setDuration((long) ((Math.random() * range) + 900));
        animationBlue.setRepeatCount(Animation.INFINITE);
        animationBlue.setRepeatMode(Animation.REVERSE);
        loadingBlue.startAnimation(animationBlue);

        TranslateAnimation animationGreen = new TranslateAnimation(distanceToTravel, -distanceToTravel, 0, 0);
        animationGreen.setDuration((long) ((Math.random() * range) + 900));
        animationGreen.setRepeatCount(Animation.INFINITE);
        animationGreen.setRepeatMode(Animation.REVERSE);
        loadingGreen.startAnimation(animationGreen);

        TranslateAnimation animationMidnight = new TranslateAnimation(-distanceToTravel, distanceToTravel, 0, 0);
        animationMidnight.setDuration((long) ((Math.random() * range) + 900));
        animationMidnight.setRepeatCount(Animation.INFINITE);
        animationMidnight.setRepeatMode(Animation.REVERSE);
        loadingMidnight.startAnimation(animationMidnight);

        TranslateAnimation animationOrange = new TranslateAnimation(distanceToTravel, -distanceToTravel, 0, 0);
        animationOrange.setDuration((long) ((Math.random() * range) + 900));
        animationOrange.setRepeatCount(Animation.INFINITE);
        animationOrange.setRepeatMode(Animation.REVERSE);
        loadingOrange.startAnimation(animationOrange);

        TranslateAnimation animationPink = new TranslateAnimation(distanceToTravel, -distanceToTravel, 0, 0);
        animationPink.setDuration((long) ((Math.random() * range) + 900));
        animationPink.setRepeatCount(Animation.INFINITE);
        animationPink.setRepeatMode(Animation.REVERSE);
        loadingPink.startAnimation(animationPink);

        TranslateAnimation animationTeal = new TranslateAnimation(-distanceToTravel, distanceToTravel, 0, 0);
        animationTeal.setDuration((long) ((Math.random() * range) + 900));
        animationTeal.setRepeatCount(Animation.INFINITE);
        animationTeal.setRepeatMode(Animation.REVERSE);
        loadingTeal.startAnimation(animationTeal);

        AlphaAnimation appNameFadeIn = new AlphaAnimation(0.0f, 1.0f);
        appNameFadeIn.setDuration(1500);

        appName.startAnimation(appNameFadeIn);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (repositoryRefreshSubscription != null) {
            repositoryRefreshSubscription.unsubscribe();
        }
    }
}
