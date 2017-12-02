package com.twtstudio.bbs.bdpqchen.bbs.commons.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.*
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Created by bdpqchen on 17-5-4.
 */

object ImageUtil {

    private val radius = 30

    /**
     * Disabled all cache when the value is true.
     */
    private val cacheMode = if (PrefUtil.isDisabledImageCache()) DiskCacheStrategy.NONE else DiskCacheStrategy.ALL

    /**
     * Load icon with Glide if need.
     * @param context
     * @param resourceId
     * @param view
     */
    @JvmStatic
    fun loadIcon(context: Context, resourceId: Int, view: ImageView) {
        Glide.with(context).load(resourceId).asBitmap().centerCrop().diskCacheStrategy(cacheMode).into(view)
    }

    /**
     * Load anonymous user avatar.
     * @param context
     * @param view
     */
    @JvmStatic
    fun loadAnonAvatar(context: Context, view: ImageView) {
        Glide.with(context).load(R.drawable.avatar_anonymous_left).asBitmap().centerCrop().crossFade().diskCacheStrategy(cacheMode).into(view)
    }

    @JvmStatic
    fun loadForumCover(context: Context, fid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getCoverUrl(fid))
                .crossFade()
                .diskCacheStrategy(cacheMode)
                .error(R.drawable.cover_login)
                .into(view)
    }

    @JvmStatic
    private fun loadResourse(context: Context, res: Int, view: ImageView) {
        loadSrc(context, res, view)
    }

    private fun loadSrc(context: Context, src: Int, view: ImageView, isBitmap: Boolean = true, isCrossFade: Boolean = true) {
        val request = getGlide(context, src)
        if (isBitmap) request.asBitmap()
        if (isCrossFade) request.crossFade().into(view)
    }

    private fun loadAvatar(context: Context, url: String, view: ImageView, placeHolder: Int = 0,
                           errorHolder: Int = 0, isCrossFade: Boolean = true,
                           isCenterCrop: Boolean = true, isBitmap: Boolean = true) {
        val request = getGlide(context, url)
        if (placeHolder != 0) request.placeholder(placeHolder)
        if (errorHolder != 0) request.error(errorHolder)
        if (isCrossFade) request.crossFade()
        if (isCenterCrop) request.centerCrop()
        if (isBitmap) request.asBitmap()
        request.into(view)
    }

    /**
     * Load an user avatar simply on the left side (default).
     */
    @JvmStatic
    fun loadAvatar(context: Context, uid: Int, view: ImageView) {
        loadAvatar(context, UrlUtil.getAvatarUrl(uid), view, 0, getDefaultAvatar())
    }

    /**
     * Load an user avatar simply on the right.
     */
    @JvmStatic
    fun loadAvatarOnTheRight(context: Context, uid: Int, view: ImageView) {
        loadAvatar(context, UrlUtil.getAvatarUrl(uid), view, 0, getDefaultAvatar(true))
    }

    /**
     * Load user avatar, if uid=0 then load default avatar.
     */
    @JvmStatic
    fun loadAvatarButDefault(context: Context, uid: Int, view: ImageView) {
        loadAvatarByUid(context, uid, view, STATUS_USER_NO_AVATAR)
    }

    /**
     * Load user avatar, if uid=0 then load anonymous avatar.
     */
    @JvmStatic
    fun loadAvatarButAnon(context: Context, uid: Int, view: ImageView) {
        loadAvatarByUid(context, uid, view, STATUS_USER_ANONYMOUS)
    }

    /**
     * @param uid Can be 0
     * @param noUidThen Image according to User's status if uid = 0 that want to get.
     */
    private fun loadAvatarByUid(context: Context, uid: Int = 0, view: ImageView, noUidThen: Int = STATUS_USER_NORMAL) {
        if (uid != 0) {
            loadAvatar(context, UrlUtil.getAvatarUrl(uid), view, 0, getDefaultAvatar())
        } else {
            when (noUidThen) {
                STATUS_USER_ANONYMOUS -> loadAnonAvatar(context, view)
                STATUS_USER_NO_AVATAR -> loadResourse(context, getDefaultAvatar(), view)
            }
        }
    }

    private fun getGlide(context: Context, url: String): DrawableTypeRequest<String> {
        return Glide.with(context).load(url)
    }

    private fun getGlide(context: Context, src: Int): DrawableTypeRequest<Int> {
        return Glide.with(context).load(src)
    }

    private fun getDefaultAvatar(isRightSide: Boolean = false): Int {
        if (isRightSide) return R.drawable.avatar_default_right
        return R.drawable.avatar_default_left
    }

    @JvmStatic
    fun loadMyAvatar(context: Context, civAvatar: ImageView) {
        loadAvatarByUid(context, PrefUtil.getAuthUid(), civAvatar)
    }

    @JvmStatic
    fun loadMyBg(context: Context, imageView: ImageView) {
        loadBgByUid(context, PrefUtil.getAuthUid(), imageView)
    }

    @JvmStatic
    fun loadBgByUid(context: Context, uid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .bitmapTransform(BlurTransformation(context, radius))
                .diskCacheStrategy(cacheMode)
                .crossFade()
                .into(view)
    }

    @JvmStatic
    fun refreshMyBg(context: Context, view: ImageView) {
        refreshMyBg(context, PrefUtil.getAuthUid(), view)
    }

    /**
     * Load background by skipMemoryCache() like {@link #refreshAvatar(Context, Int, ImageView)}
     */
    private fun refreshMyBg(context: Context, myUid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(PrefUtil.getAuthUid()))
                .skipMemoryCache(true)
                .bitmapTransform(BlurTransformation(context, radius))
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .diskCacheStrategy(cacheMode)
                .into(view)
    }

    @JvmStatic
    fun refreshMyAvatar(context: Context, civAvatar: ImageView) {
        refreshAvatar(context, PrefUtil.getAuthUid(), civAvatar)
    }

    /**
     * Load avatar by skipMemoryCache() and cache both original data and transformed data to
     * both memory and hard disk.
     */
    private fun refreshAvatar(context: Context, uid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .skipMemoryCache(true)
                .diskCacheStrategy(cacheMode)
                .crossFade()
                .into(view)
    }

    /**
     * clear all cached data of images, the method only called when
     * current time stamp over the stamp of last time cached.
     */
    @JvmStatic
    fun clearCachedData(context: Context) {
        LogUtil.ii("Clearing image cache", "true -------------->")
        Thread {
            Glide.get(context).clearDiskCache()
        }.start()
        //Must call on main thread.
        Glide.get(context).clearMemory()
    }

}
