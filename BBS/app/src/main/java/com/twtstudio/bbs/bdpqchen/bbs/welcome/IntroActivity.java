package com.twtstudio.bbs.bdpqchen.bbs.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twtstudio.bbs.bdpqchen.bbs.R;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

/**
 * Created by bdpqchen on 17-5-29.
 */

public class IntroActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideFragmentBuilder()
            .buttonsColor(R.color.material_amber_500)
                .image(R.drawable.intro1)
                .build()
        );



    }
}
