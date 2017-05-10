package com.twtstudio.bbs.bdpqchen.bbs.individual.avatar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by bdpqchen on 17-5-10.
 */

public class ImagePickerActivity extends AppCompatActivity {

    @BindView(R.id.iv_crop)
    ImageView mIvCrop;

    private int screenWidth;
    private final int REQ_IMAGE = 1433;
//    SelectAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        ButterKnife.bind(this);
        hasPermission();

    }

    private void showImageList() {
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        AndroidImagePicker.getInstance().pickAndCrop(ImagePickerActivity.this, true, 120, new AndroidImagePicker.OnImageCropCompleteListener() {
            @Override
            public void onImageCropComplete(Bitmap bmp, float ratio, String imagePath) {
                LogUtil.dd("", "=====onImageCropComplete (get bitmap=" + bmp.toString());
                mIvCrop.setVisibility(View.VISIBLE);
                mIvCrop.setImageBitmap(bmp);
            }
        });
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
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        LogUtil.dd("onPermissionRationaleShouldBeShown");
                    }
                })
                .check();

    }

    @OnClick(R.id.iv_crop)
    public void onViewClicked() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_IMAGE) {
                mIvCrop.setVisibility(View.GONE);

            }/*else if(requestCode == REQ_IMAGE_CROP){
                Bitmap bmp = (Bitmap)data.getExtras().get("bitmap");
                Log.i(TAG,"-----"+bmp.getRowBytes());
            }*/
        }

    }

    @Override
    protected void onDestroy() {
        AndroidImagePicker.getInstance().onDestroy();
        super.onDestroy();
    }


}
