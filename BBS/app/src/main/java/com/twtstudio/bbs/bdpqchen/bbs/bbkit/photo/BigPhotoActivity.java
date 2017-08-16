package com.twtstudio.bbs.bdpqchen.bbs.bbkit.photo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;
import com.twtstudio.bbs.bdpqchen.bbs.R;


/**
 * Created by retrox on 21/04/2017.
 */

public class BigPhotoActivity extends AppCompatActivity {

    public String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra("url");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_big_photo);
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
        bigImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BigPhotoActivity.this)
                        .setTitle("是否保存图片？")
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (ActivityCompat.checkSelfPermission(BigPhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(BigPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                                }
                                bigImageView.saveImageIntoGallery();
                            }
                        })
                        .setNegativeButton("不了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                return true;
            }
        });
        bigImageView.setProgressIndicator(new ProgressPieIndicator());
        bigImageView.showImage(Uri.parse(url));

//        Glide.with(this).load(url).placeholder(R.drawable.vista_title).crossFade().into(imageView);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
