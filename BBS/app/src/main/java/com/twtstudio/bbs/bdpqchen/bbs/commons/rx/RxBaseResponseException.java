package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bdpqchen on 17-4-27.
 */

public class RxBaseResponseException extends RuntimeException{

    private static final List<Integer> ERROR_CODES = new ArrayList<>();
    private RxBaseResponse mResponse;

    static {
        ERROR_CODES.add(1000);
        // ...
    }

    public RxBaseResponseException(RxBaseResponse rxBaseResponse){
        this.mResponse = rxBaseResponse;
    }

    @Override
    public String getMessage() {
        return String.valueOf(mResponse.getErr());
    }

    public int getErrorCode() {
        return mResponse.getErr();
    }



}
