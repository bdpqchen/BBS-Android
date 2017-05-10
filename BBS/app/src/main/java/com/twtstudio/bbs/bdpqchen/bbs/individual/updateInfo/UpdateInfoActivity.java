package com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword.UpdatePassword;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bdpqchen on 17-5-6.
 */

public class UpdateInfoActivity extends BaseActivity<UpdateInfoPresenter> implements UpdateInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.civ_avatar)
    CircleImageView mCivAvatar;
    @BindView(R.id.rl_avatar_update_info)
    RelativeLayout mRlAvatarUpdateInfo;
    @BindView(R.id.rl_nickname_update_info)
    RelativeLayout mRlNicknameUpdateInfo;
    @BindView(R.id.rl_signature_update_info)
    RelativeLayout mRlSignatureUpdateInfo;
    @BindView(R.id.rl_password_update_info)
    RelativeLayout mRlPasswordUpdateInfo;
    @BindView(R.id.tv_nickname_update)
    TextView mTvNicknameUpdate;
    @BindView(R.id.tv_signature_update)
    TextView mTvSignatureUpdate;

    private Activity mActivity;
    private String mNickname;
    private String mSignature;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_update_info;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("编辑资料");
        return mToolbar;
    }

    @Override
    protected boolean isShowBackArrow() {
        return true;
    }

    @Override
    protected boolean isSupportNightMode() {
        return true;
    }

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
        mActivity = this;
        mNickname = PrefUtil.getInfoNickname();
        mSignature = PrefUtil.getInfoSignature();
        mTvNicknameUpdate.setText(mNickname);
        mTvSignatureUpdate.setText(mSignature);
//        LogUtil.d(PrefUtil.getAuthToken());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
    }

    @OnClick({R.id.rl_avatar_update_info, R.id.rl_nickname_update_info, R.id.rl_signature_update_info, R.id.rl_password_update_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_avatar_update_info:

                break;
            case R.id.rl_nickname_update_info:
                showInputDialog("更改昵称", mNickname, 20, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence) {
                        String s = charSequence.toString();
                        mTvNicknameUpdate.setText(s);
                        PrefUtil.setInfoNickname(s);

                    }
                });
                break;
            case R.id.rl_signature_update_info:
                showInputDialog("更改签名", "最大长度为50", 50, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence) {
                        String s = charSequence.toString();
                        mTvSignatureUpdate.setText(s);
                        PrefUtil.setInfoSignature(s);

                    }
                });

                break;
            case R.id.rl_password_update_info:
                startActivity(new Intent(this, UpdatePassword.class));
                break;
        }
    }

    private void showInputDialog(String title, String hint, int range, MaterialDialog.InputCallback callback) {

        new MaterialDialog.Builder(this)
                .inputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE)
                .title(title)
                .inputRange(1, range)
                .input(hint, "", callback)
                .negativeText("取消")
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                updateInfo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressedSupport() {
        updateInfo();
        super.onBackPressedSupport();
    }

    private void updateInfo() {
        PrefUtil.setHasUnSyncInfo(true);
        LogUtil.d("data info had restored");

    }


}
