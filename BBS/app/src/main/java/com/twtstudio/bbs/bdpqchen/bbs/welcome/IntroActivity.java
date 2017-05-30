package com.twtstudio.bbs.bdpqchen.bbs.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bdpqchen on 17-5-29.
 */

public class IntroActivity extends AppCompatActivity {

    @BindView(R.id.vp_intro)
    ViewPager mVpIntro;
    @BindView(R.id.intro_dot_1)
    View mIntroDot1;
    @BindView(R.id.intro_dot_2)
    View mIntroDot2;
    @BindView(R.id.intro_dot_3)
    View mIntroDot3;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        mPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        mVpIntro.setAdapter(mPagerAdapter);
        mVpIntro.setPageTransformer(true, new ZoomOutPageTransformer());
        mIntroDot1.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot_curr));

        mVpIntro.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.dd("onPageScrolled", String.valueOf(position));
                if (position == 0) {
//                    updateDotStatus(0);
                }
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.dd("onPageSelected", String.valueOf(position));
                updateDotStatus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }

    private void updateDotStatus(int p){
        int d = 1000;
        switch (p){
            case 0:
                mIntroDot1.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot_curr));
                mIntroDot2.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot));
                mIntroDot3.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot));
                YoYo.with(Techniques.BounceInUp).duration(d).playOn(mIntroDot1);
                break;
            case 1:
                mIntroDot2.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot_curr));
                mIntroDot1.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot));
                mIntroDot3.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot));
                YoYo.with(Techniques.BounceInUp).duration(d).playOn(mIntroDot2);
                break;
            case 2:
                mIntroDot3.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot_curr));
                mIntroDot1.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot));
                mIntroDot2.setBackground(ResourceUtil.getDrawable(this, R.drawable.shape_intro_dot));
                YoYo.with(Techniques.BounceInUp).duration(d).playOn(mIntroDot3);
                break;
        }
    }


}
