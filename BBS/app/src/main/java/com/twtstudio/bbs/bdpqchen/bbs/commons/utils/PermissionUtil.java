package com.twtstudio.bbs.bdpqchen.bbs.commons.utils;

import android.Manifest;
import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by bdpqchen on 17-6-9.
 */

public final class PermissionUtil {

    private static boolean sIsGranted = false;

    public static boolean checkWriteStorage(Activity activity) {
        sIsGranted = false;
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        LogUtil.dd("onPermissionGranted");
                        sIsGranted = true;
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        LogUtil.dd("onPermissionDenied");
                        SnackBarUtil.error(activity, "请赋予我写入存储器内容的权限");
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        SnackBarUtil.error(activity, "请赋予我写入存储器内容的权限");
                    }
                })
                .check();
        return sIsGranted;
    }

    public static boolean checkReadStorage(Activity activity){
        sIsGranted = false;
        Dexter.withActivity(activity)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        LogUtil.dd("onPermissionGranted");
                        sIsGranted = true;
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        LogUtil.dd("onPermissionDenied");
                        sIsGranted = false;
                        SnackBarUtil.error(activity, "请赋予我读取存储器内容的权限");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        sIsGranted = false;
                        SnackBarUtil.error(activity, "请赋予我读取存储器内容的权限");
                    }
                })
                .check();
        return sIsGranted;
    }


}
