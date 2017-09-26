package com.twtstudio.bbs.bdpqchen.bbs.picture;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;
import com.twtstudio.bbs.bdpqchen.bbs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.IMG_URL;


/**
 * Created by retrox on 21/04/2017.
 */

public class BigPhotoActivity extends AppCompatActivity {

    public String url = "";
    @BindView(R.id.iv_save)
    ImageView mIvSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        url = getIntent().getStringExtra(IMG_URL);
        setContentView(R.layout.activity_big_photo);
        ButterKnife.bind(this);
        BigImageView bigImageView = (BigImageView) findViewById(R.id.image);


        bigImageView.setImageSaveCallback(new ImageSaveCallback() {
            @Override
            public void onSuccess(String uri) {
                Toast.makeText(BigPhotoActivity.this, "已保存至: " + bigImageView.getCurrentImageFile(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(BigPhotoActivity.this, "保存失败，请检查相关权限设置", Toast.LENGTH_SHORT).show();
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
