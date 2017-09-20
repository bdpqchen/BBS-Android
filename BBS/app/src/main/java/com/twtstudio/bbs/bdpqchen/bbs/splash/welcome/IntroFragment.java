package com.twtstudio.bbs.bdpqchen.bbs.splash.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.renew.identify.IdentifyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bdpqchen on 17-5-29.
 */

public class IntroFragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layout_resId";
    Unbinder unbinder;
    private int layoutResId;
    private static IntroActivity sActivity;

    public static IntroFragment newInstance(int layoutId, IntroActivity introActivity) {
        IntroFragment sampleSlide = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutId);
        sampleSlide.setArguments(args);
        sActivity = introActivity;
        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);
        unbinder = ButterKnife.bind(this, view);
        switch (layoutResId){
            case R.layout.fragment_intro_1:
                ImageView view1 = (ImageView) view.findViewById(R.id.intro1);
                Glide.with(this.getContext()).load(R.drawable.intro1).into(view1);
                break;
            case R.layout.fragment_intro_2:
                ImageView view2 = (ImageView) view.findViewById(R.id.intro2);
                Glide.with(this.getContext()).load(R.drawable.intro2).into(view2);
                break;
            case R.layout.fragment_intro_3:
                ImageView view3 = (ImageView) view.findViewById(R.id.intro3);
                Button buttonOld = (Button) view.findViewById(R.id.old_user);
                Button buttonNew = (Button) view.findViewById(R.id.new_user);
                TextView buttonJump = (TextView) view.findViewById(R.id.jump_to_login);
                Glide.with(this.getContext()).load(R.drawable.intro3).into(view3);
                Context context = this.getContext();
                buttonOld.setOnClickListener(v -> {
                    startActivity(new Intent(context, IdentifyActivity.class));
                    sActivity.finish();
                });
                buttonNew.setOnClickListener(v -> {
                    startActivity(new Intent(context, RegisterActivity.class));
                    sActivity.finish();
                });
                buttonJump.setOnClickListener(v -> {
                    startActivity(new Intent(context, LoginActivity.class));
                    sActivity.finish();
                });
                break;
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
