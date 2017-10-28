package com.twtstudio.bbs.bdpqchen.bbs.picture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.IMG_URL;


/**
 * Created by retrox on 21/04/2017.
 */

public class BigPhotoActivity extends BaseActivity {

    public String url = "";
    @BindView(R.id.iv_save)
    ImageView mIvSave;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_big_photo;
    }

    @Override
    protected Toolbar getToolbarView() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra(IMG_URL);
        BigImageView bigImageView = (BigImageView) findViewById(R.id.image);

        bigImageView.setImageSaveCallback(new ImageSaveCallback() {
            @Override
            public void onSuccess(String uri) {
                SnackBarUtil.normal(BigPhotoActivity.this, "已保存至: " + bigImageView.getCurrentImageFile(), true);
            }

            @Override
            public void onFail(Throwable t) {
                SnackBarUtil.error(BigPhotoActivity.this, "保存失败，请检查相关权限设置", true);
            }
        });
        mIvSave.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(BigPhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BigPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
            bigImageView.saveImageIntoGallery();

        });

        bigImageView.setProgressIndicator(new ProgressPieIndicator());
        bigImageView.showImage(Uri.parse(url));

//        Glide.with(this).load(url).placeholder(R.drawable.vista_title).crossFade().into(imageView);

    }


}
