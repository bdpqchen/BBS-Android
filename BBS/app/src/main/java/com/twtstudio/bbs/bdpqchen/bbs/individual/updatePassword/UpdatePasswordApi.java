package com.twtstudio.bbs.bdpqchen.bbs.individual.updatePassword;

import com.twtstudio.bbs.bdpqchen.bbs.individual.model.SimpleBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.PUT;

/**
 * Created by HP on 2017/5/23.
 */

public interface UpdatePasswordApi {
    @FormUrlEncoded
    @PUT("home")
    Call<SimpleBean> sentUpdatePasswordInfo(@Header("authentication") String uidToken,
                                            @Field("old_password") String oldPassword,
                                            @Field("password") String newPassword);
}
