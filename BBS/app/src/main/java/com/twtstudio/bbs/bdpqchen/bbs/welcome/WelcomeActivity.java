package com.twtstudio.bbs.bdpqchen.bbs.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.register.RegisterActivity;
import com.twtstudio.bbs.bdpqchen.bbs.auth.replaceUser.ReplaceUserActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-20.
 */

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.tv_jump_login)
    TextView mTvJumpLogin;
    @BindView(R.id.civ_old_user)
    CircleImageView mCivOldUser;
    @BindView(R.id.civ_new_user)
    CircleImageView mCivNewUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.tv_jump_login, R.id.civ_old_user, R.id.civ_new_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jump_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.civ_old_user:
                startActivity(new Intent(this, ReplaceUserActivity.class));
                break;
            case R.id.civ_new_user:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
