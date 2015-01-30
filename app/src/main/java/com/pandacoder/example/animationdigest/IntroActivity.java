package com.pandacoder.example.animationdigest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pandacoder.example.animationdigest.utils.FakeProgress;

public class IntroActivity extends Activity {

    private final static String STATE_CURRENT_PROGRESS_KEY = "current_progress";

    private Animation mLoadingAnim;
    private View mLoadingLayout;
    private ProgressBar mProgressBar;

    private FakeProgress mFakeProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_layout);

        mLoadingLayout = findViewById(R.id.intro_loading_layout);
        mProgressBar = (ProgressBar) findViewById(R.id.intro_progress_bar);
        mLoadingAnim = AnimationUtils.loadAnimation(this, R.anim.pulsation);

        int startProgress = (savedInstanceState != null)?savedInstanceState.getInt(STATE_CURRENT_PROGRESS_KEY, 0):0;
        mFakeProgress = new FakeProgress.Builder()
                .setStartProgress(startProgress)
                .setEndProgress(1000)
                .setProgressTimeMs(4500)
                .build();

        mFakeProgress.setOnProgressRunnable(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setProgress(mFakeProgress.getProgress());
                if (mFakeProgress.isMaxProgressReached()) {
                    onLoadedFinished();
                }
            }
        });

        mFakeProgress.reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFakeProgress.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFakeProgress.stop();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mLoadingLayout.startAnimation(mLoadingAnim);
        }
    }

    protected void onLoadedFinished() {
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(R.anim.sli.mainfadein,R.anim.splashfadeout);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_PROGRESS_KEY, mFakeProgress.getProgress());
    }

}
