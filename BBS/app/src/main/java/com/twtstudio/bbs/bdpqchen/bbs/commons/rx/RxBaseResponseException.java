package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxBaseResponseException extends RuntimeException{

    private static final HashMap<Integer, String> ERROR_CODES_MAPS = new HashMap<Integer, String>();
    private RxBaseResponse mResponse;

    static {
        ERROR_CODES_MAPS.put(1001, "登录失败");

        // ...
    }

    public RxBaseResponseException(RxBaseResponse rxBaseResponse){
        this.mResponse = rxBaseResponse;
    }

    @Override
    public String getMessage() {
        return getErrorMessage(getErrorCode());
    }

    public int getErrorCode() {
        return mResponse.getErr();
    }

    public String getErrorMessage(int errCode){
        if (!ERROR_CODES_MAPS.containsKey(errCode)){
            return "网络请求失败";
        }
        return ERROR_CODES_MAPS.get(mResponse.getErr());
    }

}
