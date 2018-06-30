package com.jorgesantiago.vusie.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.jorgesantiago.vusie.R;
import com.jorgesantiago.vusie.RoomDB.ArticleRepository;
import com.jorgesantiago.vusie.Utilities.DisplayUtility;
import com.jorgesantiago.vusie.Utilities.NetworkUtility;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import dagger.android.support.DaggerAppCompatActivity;
import rx.Observable;
import rx.Subscription;

public class SplashScreenActivity extends DaggerAppCompatActivity {

    @Inject
    ArticleRepository articleRepository;

    private View loadingBlue;
    private View loadingGreen;
    private View loadingMidnight;
    private View loadingOrange;
    private View loadingPink;
    private View loadingTeal;
    private View appName;

    private TranslateAnimation animationBlue;
    private TranslateAnimation animationGreen;
    private TranslateAnimation animationMidnight;
    private TranslateAnimation animationOrange;
    private TranslateAnimation animationPink;
    private TranslateAnimation animationTeal;
    private AlphaAnimation appNameFadeIn;

    private Subscription repositoryRefreshSubscription;

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
        tellRepositoryToRefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we want to subscribe in onResume because we want to make sure we are always subscribed to be able to redirect the user
        repositoryRefreshSubscription = ArticleRepository.notifyWhenRefreshIsComplete()
                .filter(refreshComplete -> refreshComplete)
                // 3 second delay just to use as a branding opportunity to display logo and animation on splash screen -- most likely wouldn't do this in an actual production app
                .flatMap(refreshDone -> Observable.timer(3, TimeUnit.SECONDS))
                .subscribe(refreshComplete -> {
                    startActivity(new Intent(SplashScreenActivity.this, BrowseArticlesActivity.class));
                    overridePendingTransition(R.anim.scale_and_fade_in, R.anim.scale_and_fade_out);
                    finish();
                }, Throwable::printStackTrace);

        setupAnimations();
        startAnimations();
    }

    private void setupAnimations() {
        // we want the colorful vusie bars to always translate accross the entire width of any screen size the app may be running on
        // we do this in onResume instead of onCreate because we run the risk of the view not yet being drawn in onCreate, the view should
        // have been drawn in onStart, so we measure the view in onResume just to be extra safe that the view has been drawn
        DisplayUtility.passInDisplayMetrics(getResources().getDisplayMetrics());

        // we want the bar to translate the entire width of the screen, and to actually go off the edge of the screen
        float distanceToTravel = DisplayUtility.getScreenWidthInPixels() + DisplayUtility.getViewWidthInPixels(loadingBlue);

        // the range of duration for our loading bars translation animation, in millis,
        // each loading bars gets its duration set randomly so each bar translates at a slightly different speed, for cool effect
        long range = (1300 - 900);

        // TODO see if there is way to clean up all these translations, make more concise, readable?
        animationBlue = new TranslateAnimation(-distanceToTravel, distanceToTravel, 0, 0);
        animationBlue.setDuration((long) ((Math.random() * range) + 900));
        animationBlue.setRepeatCount(Animation.INFINITE);
        animationBlue.setRepeatMode(Animation.REVERSE);

        animationGreen = new TranslateAnimation(distanceToTravel, -distanceToTravel, 0, 0);
        animationGreen.setDuration((long) ((Math.random() * range) + 900));
        animationGreen.setRepeatCount(Animation.INFINITE);
        animationGreen.setRepeatMode(Animation.REVERSE);

        animationMidnight = new TranslateAnimation(-distanceToTravel, distanceToTravel, 0, 0);
        animationMidnight.setDuration((long) ((Math.random() * range) + 900));
        animationMidnight.setRepeatCount(Animation.INFINITE);
        animationMidnight.setRepeatMode(Animation.REVERSE);

        animationOrange = new TranslateAnimation(distanceToTravel, -distanceToTravel, 0, 0);
        animationOrange.setDuration((long) ((Math.random() * range) + 900));
        animationOrange.setRepeatCount(Animation.INFINITE);
        animationOrange.setRepeatMode(Animation.REVERSE);

        animationPink = new TranslateAnimation(distanceToTravel, -distanceToTravel, 0, 0);
        animationPink.setDuration((long) ((Math.random() * range) + 900));
        animationPink.setRepeatCount(Animation.INFINITE);
        animationPink.setRepeatMode(Animation.REVERSE);

        animationTeal = new TranslateAnimation(-distanceToTravel, distanceToTravel, 0, 0);
        animationTeal.setDuration((long) ((Math.random() * range) + 900));
        animationTeal.setRepeatCount(Animation.INFINITE);
        animationTeal.setRepeatMode(Animation.REVERSE);

        appNameFadeIn = new AlphaAnimation(0.0f, 1.0f);
        appNameFadeIn.setDuration(1500);
    }

    private void startAnimations() {
        loadingBlue.startAnimation(animationBlue);
        loadingGreen.startAnimation(animationGreen);
        loadingMidnight.startAnimation(animationMidnight);
        loadingOrange.startAnimation(animationOrange);
        loadingPink.startAnimation(animationPink);
        loadingTeal.startAnimation(animationTeal);
        appName.startAnimation(appNameFadeIn);
    }

    private void tellRepositoryToRefresh() {
        if (NetworkUtility.validNetworkState(this)) {
            articleRepository.refreshRepository();
        } else {
            notifyUserOfInvalidNetworkState();
        }
    }

    private void notifyUserOfInvalidNetworkState() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.no_network_dialog_title));
        alertDialogBuilder.setMessage(getString(R.string.no_network_dialog_message));
        alertDialogBuilder.setNeutralButton(getString(R.string.ok), (dialogInterface, i) -> tellRepositoryToRefresh());
        alertDialogBuilder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (repositoryRefreshSubscription != null) {
            repositoryRefreshSubscription.unsubscribe();
        }
    }
}
