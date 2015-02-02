package com.pandacoder.example.animationdigest;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TutorialFragment extends Fragment {

    private View mUseSlideMenuLayout;
    private Button mGotItButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tutorial_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUseSlideMenuLayout = view.findViewById(R.id.tutorial_use_side_menu_layout);
        mGotItButton = (Button) view.findViewById(R.id.tutorial_got_it_button);
        initView();
    }

    protected void finishTutorial() {

    }

    private void initView() {
        AnimatorSet gotItButtonAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.use_sliding_menu_animation);
        gotItButtonAnimator.setTarget(mUseSlideMenuLayout);
        gotItButtonAnimator.start();

        mGotItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTutorial();
            }
        });
    }
}
