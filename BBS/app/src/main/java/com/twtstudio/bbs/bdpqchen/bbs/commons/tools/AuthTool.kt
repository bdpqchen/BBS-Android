package com.twtstudio.bbs.bdpqchen.bbs.commons.tools

import android.content.Context
import com.twtstudio.bbs.bdpqchen.bbs.auth.login.LoginModel
import com.twtstudio.bbs.bdpqchen.bbs.commons.manager.ActivityManager
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImageUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IsUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel

/**
 * Created by bdpqchen on 17-12-1.
 *
 */
object AuthTool {

    /**
     * clear image cache
     * clear network cache
     * reset user status
     */
    @JvmStatic
    fun logout(context: Context) {
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
        ImageUtil.clearCachedData(context)
        FileTool.deleteCacheFile()
        ActivityManager.getActivityManager().finishAllActivity()
        context.startActivity(IntentUtil.toLogin(context))
    }

    @JvmStatic
    fun login(login: LoginModel) {
        PrefUtil.setFirstOpen(false)
        PrefUtil.setAuthToken(login.token)
        PrefUtil.setAuthGroup(login.group)
        PrefUtil.setAuthUid(login.uid)
        PrefUtil.setHadLogin(true)
    }

    @JvmStatic
    fun userInfo(info: IndividualInfoModel){
        PrefUtil.setAuthUsername(info.name)
        PrefUtil.setInfoNickname(info.nickname)
        PrefUtil.setInfoSignature(info.signature)
        PrefUtil.setInfoOnline(info.c_online)
        PrefUtil.setInfoPost(info.c_post)
        PrefUtil.setInfoPoints(info.points)
        PrefUtil.setInfoCreate(info.t_create)
        PrefUtil.setInfoGroup(info.group)
        PrefUtil.setInfoLevel(info.level)
    }


}