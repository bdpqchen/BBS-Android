package com.twtstudio.bbs.bdpqchen.bbs.commons.tools

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

/**
 * Created by bdpqchen on 17-5-11.
 */

object WindowTool {

    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = 0
        val res = context.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    @JvmStatic
    fun getWindowHeight(context: Context): Int {
        return getWindowPoint(context).y
    }

    @JvmStatic
    fun getWindowWidth(context: Context): Int {
        return getWindowPoint(context).x
    }

    private fun getWindowPoint(context: Context): Point {
        val point = Point()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        //不含虚拟按键
        windowManager.defaultDisplay.getSize(point)
        return point
    }

}
