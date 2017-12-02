package com.twtstudio.bbs.bdpqchen.bbs.commons.tools

import com.twtstudio.bbs.bdpqchen.bbs.commons.App
import java.io.File

/**
 * Created by bdpqchen on 17-12-2.
 *
 */
object FileTool {

    @JvmStatic
    fun getCacheFile(): File {
        return File(App.getContext().externalCacheDir, "network_cache")
    }

    @JvmStatic
    fun deleteCacheFile() {
/*
        LogUtil.dd("getCacheFile().listFiles()", getCacheFile().listFiles().size)
        if (getCacheFile().listFiles() != null) {
            getCacheFile().listFiles().toList().forEach {
                println(it)
            }
        }
*/
        getCacheFile().deleteRecursively()

    }

}