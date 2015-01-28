package com.pandacoder.example.animationdigest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.pandacoder.example.animationdigest.utils.FakeProgress;

public class IntroActivity extends Activity {

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
        mProgressBar.setProgress(100);
        mLoadingAnim = AnimationUtils.loadAnimation(this, R.anim.pulsation);

        mFakeProgress = new FakeProgress.Builder()
                .setStartProgress(0)
                .setEndProgress(100)
                .setProgressTimeMs(5000)
                .build();

        mFakeProgress.setOnProgressRunnable(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setProgress(mFakeProgress.getProgress());
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

}
