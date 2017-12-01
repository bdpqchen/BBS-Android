package com.twtstudio.bbs.bdpqchen.bbs.commons.tools

import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil

/**
 * Created by bdpqchen on 17-12-1.
 *
 */
object AuthTool{

    @JvmStatic
    fun logout(){
        PrefUtil.setHadLogin(false)
        PrefUtil.setAuthToken("")
        PrefUtil.setAuthUsername("")
        PrefUtil.setAuthGroup(0)
        PrefUtil.setAuthUid(0)
        PrefUtil.setInfoNickname("")
        PrefUtil.setInfoSignature("")
        PrefUtil.setInfoCreate(0)
        PrefUtil.setInfoPoints(0)
        PrefUtil.setInfoUnread(0)
        PrefUtil.setHasUnSyncInfo(false)
    }

    @JvmStatic
    fun login(login: LoginModel){
        PrefUtil.setFirstOpen(false)
        PrefUtil.setAuthToken(login.token)
        PrefUtil.setAuthGroup(login.group)
        PrefUtil.setAuthUid(login.uid)
    }




}