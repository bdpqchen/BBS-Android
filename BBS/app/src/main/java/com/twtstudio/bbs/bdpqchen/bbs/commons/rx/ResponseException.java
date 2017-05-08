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
        ERROR_CODES_MAPS.put(1010, "失败, 请检查输入是否有误");

        // ...
    }

    public ResponseException(BaseResponse baseResponse){
        this.mResponse = baseResponse;
        LogUtil.d("11111ssssss");
    }

    @Override
    public String getMessage() {
        return getErrorMessage(getErrorCode());
    }

    public int getErrorCode() {
        return mResponse.getErr();
    }

    public String getErrorMessage(int errCode){
        LogUtil.d("-----=");
        if (!ERROR_CODES_MAPS.containsKey(errCode)){
            return "网络请求失败";
        }
        return ERROR_CODES_MAPS.get(mResponse.getErr());
    }

}
