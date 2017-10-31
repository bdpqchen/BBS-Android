package com.twtstudio.bbs.bdpqchen.bbs.commons.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.*
import de.hdodenhof.circleimageview.CircleImageView
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Created by bdpqchen on 17-5-4.
 */

object ImageUtil {

    private val radius = 30
    private val myUid = PrefUtil.getAuthUid()

    /**
     * Load icon with Glide if need.
     *
     * @param context
     * @param resourceId
     * @param view
     */
    @JvmStatic
    fun loadIconAsBitmap(context: Context, resourceId: Int, view: ImageView) {
        Glide.with(context).load(resourceId).asBitmap().centerCrop().into(view)
    }

    /**
     * Load anonymous user avatar.
     * @param context
     * @param view
     */
    @JvmStatic
    fun loadAnonAvatar(context: Context, view: ImageView) {
        Glide.with(context).load(R.drawable.avatar_anonymous_left).asBitmap().centerCrop().crossFade().into(view)
    }

    @JvmStatic
    fun loadForumCover(context: Context, fid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getCoverUrl(fid))
                .crossFade()
                .error(R.drawable.cover_login)
                .into(view)
    }

    @JvmStatic
    fun loadResouse(context: Context, res: Int, view: ImageView) {
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
     * Load an user avatar simply.
     */
    @JvmStatic
    fun loadAvatar(context: Context, uid: Int, view: ImageView) {
        loadAvatarByUid(context, uid, view)
    }

    /**
     * @param uid Can be 0
     * @param noUidThen Image according to User's status if uid = 0 that want to get.
     */
    private fun loadAvatarByUid(context: Context, uid: Int = 0, view: ImageView, noUidThen: Int = STATUS_USER_NORMAL) {
        if (uid != 0 && noUidThen == STATUS_USER_NORMAL) {
            loadAvatar(context, UrlUtil.getAvatarUrl(uid), view, getDefaultAvatar())
        } else {
            when (noUidThen) {
                STATUS_USER_ANONYMOUS -> loadAnonAvatar(context, view)
                STATUS_USER_NO_AVATAR -> loadResouse(context, getDefaultAvatar(), view)
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

    fun loadAvatarAsBitmapByUidWithLeft(context: Context, author_id: Int, civAvatarPost: ImageView) {
        if (IsUtil.is0(author_id)) {
            loadAnonAvatar(context, civAvatarPost)
            return
        }
        val request = getDefaultAvatarRequest(context, author_id, 0)
        request.asBitmap()
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(getDefaultAvatar())
                .into(civAvatarPost)
    }

    fun loadAvatarAsBitmapByUidWithRight(context: Context, author_id: Int, civAvatarPost: CircleImageView) {
        val request = getDefaultAvatarRequest(context, author_id, 0)
        request.asBitmap()
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.avatar_default_right)
                .into(civAvatarPost)
    }

    private fun getDefaultAvatarRequest(context: Context, uid: Int, side: Int): DrawableTypeRequest<*> {
        return Glide.with(context).load(UrlUtil.getAvatarUrl(uid))
    }

    fun loadMyAvatar(context: Context, civAvatar: ImageView) {
        loadAvatarByUid(context, myUid, civAvatar)
    }

    private fun loadAvatarByUid(context: Context, uid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view)

    }

    fun loadMyBg(context: Context, imageView: ImageView) {
        loadBgByUid(context, myUid, imageView)
    }

    fun loadBgByUid(context: Context, uid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .bitmapTransform(BlurTransformation(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view)
    }

    fun refreshMyBg(context: Context, view: ImageView) {
        refreshMyBg(context, myUid, view)
    }

    private fun refreshMyBg(context: Context, myUid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(myUid))
                .skipMemoryCache(true)
                .bitmapTransform(BlurTransformation(context, radius))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view)
    }

    fun refreshMyAvatar(context: Context, civAvatar: ImageView) {
        refreshAvatar(context, PrefUtil.getAuthUid(), civAvatar)
    }

    private fun refreshAvatar(context: Context, uid: Int, view: ImageView) {
        Glide.with(context)
                .load(UrlUtil.getAvatarUrl(uid))
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .crossFade()
                .into(view)
    }

    fun clearMemory(context: Context) {
        Glide.get(context).clearMemory()
    }

    fun clearDiskCache(context: Context) {
        Thread { Glide.get(context).clearDiskCache() }.start()
    }

}
