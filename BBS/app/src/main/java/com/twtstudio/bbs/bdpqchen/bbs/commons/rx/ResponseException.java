package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import java.util.HashMap;



/**
 * Created by bdpqchen on 17-4-27.
 */

public class ResponseException extends RuntimeException{

    private static final HashMap<Integer, String> ERROR_CODES_MAPS = new HashMap<Integer, String>();
    private BaseResponse mResponse;

    static {
        ERROR_CODES_MAPS.put(1001, "登录失败, 请仔细检查账户密码");
//        ERROR_CODES_MAPS.put(1010, "失败, 请检查输入是否有误");
        ERROR_CODES_MAPS.put(1010, "姓名，身份证号不匹配");
        ERROR_CODES_MAPS.put(1012, "姓名，学号不匹配");
        ERROR_CODES_MAPS.put(4000, "未上传头像");

        // ...
    }

    public ResponseException(BaseResponse baseResponse){
        this.mResponse = baseResponse;
    }

    @Override
    public String getMessage() {
        LogUtil.d(mResponse.getMessage());
        return getErrorMessage(getErrorCode());
    }

    public int getErrorCode() {
        return mResponse.getErr();
    }

    public String getErrorMessage(int errCode){
        if (!ERROR_CODES_MAPS.containsKey(errCode)){
            LogUtil.d("messageerr", mResponse.getMessage());
            if (mResponse.getMessage() != null){
                return mResponse.getMessage();
            }
            return "网络请求失败";
        }
        return ERROR_CODES_MAPS.get(mResponse.getErr());
    }

}
