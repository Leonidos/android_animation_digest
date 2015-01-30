package com.pandacoder.example.animationdigest.utils;

import android.os.Handler;

public class FakeProgress {

    public static class Builder {
        private int mStartProgress;
        private int mEndProgress;
        private int mProgressTimeMs;

        public Builder setStartProgress(int startProgress) {
            mStartProgress = startProgress;
            return this;
        }

        public Builder setEndProgress(int endProgress) {
            mEndProgress = endProgress;
            return this;
        }

        public Builder setProgressTimeMs(int progressTimeMs) {
            mProgressTimeMs = progressTimeMs;
            return this;
        }

        public FakeProgress build() {
            return new FakeProgress(mStartProgress, mEndProgress, mProgressTimeMs);
        }
    }

    public final static int PROGRESS_UPDATE_INTERVAL_MS = 12;

    private final int mStartProgress;
    private final int mEndProgress;
    private final int mProgressTimeMs;

    private final Handler mHanler;

    private boolean mTicking;
    private int mCurrentTimeMs;
    private int mCurrentProgress;

    private Runnable mOnProgressRunnable = null;

    public FakeProgress(int start, int end, int progressTimeMs) {
        mStartProgress = start;
        mEndProgress = end;
        mProgressTimeMs = progressTimeMs;

        mHanler = new Handler();
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public boolean isMaxProgressReached() {
        return mCurrentTimeMs >= mProgressTimeMs;
    }

    public void setOnProgressRunnable(Runnable onProgressRunnable) {
        reset();
        mOnProgressRunnable = onProgressRunnable;
    }

    public void reset() {
        mCurrentProgress = 0;
        mCurrentTimeMs = 0;
        stop();
    }

    public void start() {
        mTicking = true;
        mUpdateProgressRunnable.run();
    }

    public void stop() {
        if (mOnProgressRunnable != null) {
            mHanler.removeCallbacks(mOnProgressRunnable);
        }
        mHanler.removeCallbacks(mUpdateProgressRunnable);
        mTicking = false;
    }

    private final Runnable mUpdateProgressRunnable = new Runnable() {
        @Override
        public void run() {
            updateProgress();
            if (mTicking) {
                mHanler.postDelayed(mUpdateProgressRunnable, PROGRESS_UPDATE_INTERVAL_MS);
            }
        }
    };

    protected void updateProgress() {
        float progress = mStartProgress + (mEndProgress - mStartProgress) * mCurrentTimeMs / mProgressTimeMs;
        if (progress > mEndProgress) {
            mCurrentProgress = mEndProgress;
        } else {
            mCurrentProgress = (int) progress;
        }

        if (mOnProgressRunnable != null) {
            mOnProgressRunnable.run();
        }

        if (mCurrentTimeMs > mProgressTimeMs) {
            stop();
        }

        mCurrentTimeMs += PROGRESS_UPDATE_INTERVAL_MS;
    }

}
