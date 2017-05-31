package com.twtstudio.bbs.bdpqchen.bbs.individual.avatar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import java.io.File;

import butterknife.BindView;

public class UpdateAvatarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.result_image)
    ImageView resultView;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;
    private Uri mImagePath = null;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_update_avatar;
    }

    @Override
    protected Toolbar getToolbarView() {
        mToolbar.setTitle("编辑头像");
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
//        getActivityComponent().inject(this);
    }

    @Override
    protected Activity supportSlideBack() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBtnFinish.setOnClickListener(v -> {
            if (mImagePath != null) {
                Intent data = new Intent();
                data.putExtra(Constants.INTENT_RESULT_IMAGE_PATH, mImagePath.toString());
                setResult(Constants.RESULT_CODE_AVATAR, data);
                finish();
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_avatar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_update_avatar_done) {
            resultView.setImageDrawable(null);
            Crop.pickImage(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
            LogUtil.dd("begincrop send");
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
            LogUtil.dd("handlecrop");
        }
    }

    private void beginCrop(Uri source) {
        LogUtil.dd("begincrop");
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
        LogUtil.d("source", source);
        LogUtil.d("destination", destination);

    }

    private void handleCrop(int resultCode, Intent result) {
        LogUtil.dd("handlecrop");
        if (resultCode == RESULT_OK) {
            mImagePath = Crop.getOutput(result);
            resultView.setImageURI(mImagePath);
            LogUtil.d("result", Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
