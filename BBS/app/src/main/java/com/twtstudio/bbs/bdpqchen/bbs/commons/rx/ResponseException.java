package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import android.text.TextUtils;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;


/**
 * Created by bdpqchen on 17-4-27.
 */

public class ResponseException extends RuntimeException {

    private BaseResponse mBaseResponse;

    ResponseException(BaseResponse baseResponse) {
        if (baseResponse != null) {
            LogUtil.dd("err message is", baseResponse.getMessage());
            mBaseResponse = baseResponse;
        }
    }

    @Override
    public String getMessage() {
        if (!TextUtils.isEmpty(mBaseResponse.getMessage())) {
            return mBaseResponse.getMessage();
        } else {
            return "发生了未知错误";
        }
    }

    public int getErrCode() {
        return mBaseResponse.getErr();
    }

}
