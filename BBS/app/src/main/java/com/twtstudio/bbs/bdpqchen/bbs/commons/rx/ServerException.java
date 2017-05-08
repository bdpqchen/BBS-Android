package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.LogUtil;

import java.util.HashMap;

import retrofit2.Response;

/**
 * Created by bdpqchen on 17-5-8.
 */

public class ServerException extends Exception{

    private static final HashMap<Integer, String> ERROR_CODES_MAPS = new HashMap<Integer, String>();
    private BaseResponse mResponse;

    static {
        ERROR_CODES_MAPS.put(1001, "登录失败, 请仔细检查账户密码");
        ERROR_CODES_MAPS.put(1010, "失败, 请检查输入是否有误");

        // ...
    }


    private static String getMessage(Response<?> response) {
        if (response == null) throw new NullPointerException("response == null");
        return "HTTP " + response.code() + " " + response.message();
    }

    private final int code;
    private final String message;
    private final transient Response<?> response;
    private String errMessage = "网络请求错误";

    public ServerException(Response<?> response) {
        super(getMessage(response));
        this.code = response.code();
        this.message = response.message();
        this.response = response;
        BaseResponse baseResponse = (BaseResponse) response.body();
        LogUtil.d(String.valueOf(baseResponse.getErr()), message);

//        setErrMessage();
//        throw new ResponseException()
    }

    private void setErrMessage() {
        switch (code){
            case 500:

        }
    }

    //后台返回了错误码，比如500
    public String gerErr(){
        return errMessage;
    }

    /** HTTP status code. */
    public int code() {
        return code;
    }

    /** HTTP status message. */
    public String message() {
        return message;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response<?> response() {
        return response;
    }
}
