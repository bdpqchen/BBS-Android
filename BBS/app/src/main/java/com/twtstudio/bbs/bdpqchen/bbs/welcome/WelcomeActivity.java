package com.twtstudio.bbs.bdpqchen.bbs.welcome;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.paolorotolo.appintro.AppIntro;
import com.twtstudio.bbs.bdpqchen.bbs.R;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class WelcomeActivity extends AppIntro {
/*
    @BindView(R.id.tv_jump_login)
    TextView mTvJumpLogin;
    @BindView(R.id.civ_old_user)
    CircleImageView mCivOldUser;
    @BindView(R.id.civ_new_user)
    CircleImageView mCivNewUser;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome);
//        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addSlide(IntroFragment.newInstance(R.layout.fragment_intro_1));
        addSlide(IntroFragment.newInstance(R.layout.fragment_intro_2));
        addSlide(IntroFragment.newInstance(R.layout.fragment_intro_3));


        showSeparator(true);
//        showStatusBar(true);
        setProgressButtonEnabled(true);
        setBarColor(Color.parseColor("#2196F3"));
        setSeparatorColor(Color.parseColor("#2196F3"));

//        showStatusBar(true);
//        showSkipButton(false);
//        setVisible(false);
//        setVibrateIntensity(30);

//        setBarColor(Color.parseColor("#ffffff"));

//        setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(false);
        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);

    }

}
