package com.twtstudio.bbs.bdpqchen.bbs.individual.updateInfo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeActivity;
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
    private String mOldNickname = PrefUtil.getInfoNickname();
    private String mOldSignature = PrefUtil.getInfoSignature();
    private MaterialDialog mMaterialDialog;
    private int screenWidth;
    private final int REQ_IMAGE = 1433;

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
    }

    @OnClick({R.id.rl_avatar_update_info, R.id.rl_nickname_update_info, R.id.rl_signature_update_info, R.id.rl_password_update_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_avatar_update_info:
                hasPermission();
                break;
            case R.id.rl_nickname_update_info:
                showInputDialog("更改昵称", mNickname, 15, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence) {
                        String s = charSequence.toString();
                        mNickname = s;
                        mTvNicknameUpdate.setText(s);
                        PrefUtil.setInfoNickname(s);

                    }
                });
                break;
            case R.id.rl_signature_update_info:
                showInputDialog("更改签名", "最大长度为100", 100, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog materialDialog, CharSequence charSequence) {
                        String s = charSequence.toString();
                        mSignature = s;
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
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE)
                .title(title)
                .inputRange(6, range)
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
        boolean hasUnsyncInfo = true;
        if (mNickname.equals(mOldNickname) && mSignature.equals(mOldSignature)) {
            hasUnsyncInfo = false;
        }
        Intent intent = new Intent();
        intent.putExtra(HomeActivity.CODE_RESULT_FOR_UPDATE_INFO_TAG, hasUnsyncInfo);
        this.setResult(HomeActivity.CODE_RESULT_FOR_UPDATE_INFO, intent);
    }

    private void showImageList() {
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        AndroidImagePicker.getInstance().pickAndCrop(this, true, 120, new AndroidImagePicker.OnImageCropCompleteListener() {
            @Override
            public void onImageCropComplete(Bitmap bmp, float ratio, String imagePath) {
                showProgressBar();
                mPresenter.doUpdateAvatar(imagePath);
                mCivAvatar.setVisibility(View.VISIBLE);
                mCivAvatar.setImageBitmap(bmp);

            }
        });
    }

    private void showProgressBar() {
        if (mMaterialDialog == null){
            mMaterialDialog = DialogUtil.showProgressDialog(this, "提示", "正在上传，请稍后..");
        }else{
            mMaterialDialog.show();
        }
    }

    private void hideProgressBar() {
        if (mMaterialDialog != null){
            mMaterialDialog.dismiss();
        }
    }

    private void hasPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        LogUtil.dd("onPermissionGranted");
                        showImageList();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        LogUtil.dd("onPermissionDenied");
                        SnackBarUtil.error(mActivity, "请赋予我读取存储器内容的权限");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                        LogUtil.dd("onPermissionRationaleShouldBeShown");
                    }
                })
                .check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_IMAGE) {
                mCivAvatar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().onDestroy();
        super.onDestroy();
    }

    @Override
    public void updateAvatarFailed(String msg) {
        hideProgressBar();
        SnackBarUtil.error(this, msg);
    }

    @Override
    public void updateAvatarSuccess(BaseModel baseModel) {
        hideProgressBar();
        SnackBarUtil.normal(this, "头像上传成功");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlideBackLayout.lock(!PrefUtil.isSlideBackMode());
    }
}
