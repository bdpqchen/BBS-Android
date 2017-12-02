package com.twtstudio.bbs.bdpqchen.bbs.commons.tools

import android.content.Context
import android.text.TextUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.twtstudio.bbs.bdpqchen.bbs.BuildConfig
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * Created by bdpqchen on 17-11-30.
 *
 */
object UpdateTool {

    private val switchOff = BuildConfig.DEBUG

    @JvmStatic
    fun checkUpdate(){
        if (switchOff) return
        Beta.checkUpgrade()
    }

    @JvmStatic
    fun initBuglyReport(appContext: Context) {
        if (switchOff) return
        val context = appContext.applicationContext
        val packageName = context.packageName
        val processName = getProcessName(android.os.Process.myPid())
        val strategy = CrashReport.UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        Bugly.init(context, BuildConfig.ID_BUGLY, BuildConfig.DEBUG)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }

        }
        return null
    }

}