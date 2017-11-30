package com.twtstudio.bbs.bdpqchen.bbs.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatEditText
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_THREAD
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.MODE_SEARCH_USER
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.IntentUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil
import kotterknife.bindView

/**
 * Created by bdpqchen on 17-5-3.
 * 这个类所有没有用到的代码 是第一个搜索版本.
 */
class MainFragment : SimpleFragment(), View.OnTouchListener, View.OnClickListener {

    private val mTabLayout: TabLayout by bindView(R.id.main_tab_layout)

    private val mViewpager: ViewPager by bindView(R.id.main_viewpager)

    private val mTvTitle: TextView by bindView(R.id.tv_title)

    private val mIvSearch: ImageView by bindView(R.id.iv_search)

    private val mEtSearch: AppCompatEditText by bindView(R.id.et_search)

    private val mLlOptions: LinearLayout by bindView(R.id.ll_options)

    private val mLlSearch: LinearLayout by bindView(R.id.ll_search)

    private val mTvOption1: TextView by bindView(R.id.tv_option1)

    private val mTvOption2: TextView by bindView(R.id.tv_option2)
    private val circularX: Int
        get() = mIvSearch.right

    private val circularY: Int
        get() = mIvSearch.height / 2 + mIvSearch.top

    override fun getPerMainFragmentLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initFragments() {
        StatusBarUtil.setColor(this.activity, ResourceUtil.getColor(this.activity, R.color.colorPrimaryDark), 0)
        val tabAdapter = MainTabAdapter(fragmentManager, mContext)
        mViewpager.adapter = tabAdapter
        mTabLayout.setupWithViewPager(mViewpager)
        mEtSearch.setOnTouchListener(this)
        mIvSearch.setOnClickListener { showSearch() }
        setViewClickEffects(mTvOption1, mTvOption2)
        setItemOptionClickListener(mTvOption1, mTvOption2)
        setIconTint()
    }

    private fun setItemOptionClickListener(vararg views: View) {
        for (view in views) view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_option1 -> search(MODE_SEARCH_THREAD)
            R.id.tv_option2 -> search(MODE_SEARCH_USER)
        }
    }

    private fun search(mode: Int) {
        println("mode is $mode")
        val keyword = mEtSearch.text.toString()
        if (keyword.isEmpty()) {
            return
        }
        startActivity(IntentUtil.toSearch(mContext, mode))
    }

    private fun setIconTint() {
        mEtSearch.compoundDrawablesRelative[2]?.setColorFilter(ResourceUtil.getColor(mContext, R.color.colorTintIconBlack), PorterDuff.Mode.SRC_IN)
    }

    private fun setIvSearchIconTint(color: Int) {
        mIvSearch.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    private fun setViewClickEffects(vararg views: View) {
        if (VersionUtil.eaMarshmallow()) {
            for (view in views) {
                view.foreground = ResourceUtil.getDrawable(mContext, R.drawable.selector_foreground_settings)
            }
        }
    }

    private fun hideInput() {
        updateSoftInput(false)
    }

    private fun updateSoftInput(isShow: Boolean) {
        if (isShow) {
            mEtSearch.requestFocus()
        }
        mEtSearch.postDelayed({
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (isShow)
                imm.showSoftInput(mEtSearch, InputMethodManager.SHOW_IMPLICIT)
            else
                imm.hideSoftInputFromWindow(mEtSearch.windowToken, 0)
        }, 100)
    }

    private fun showInput() {
        updateSoftInput(true)
    }

    private fun translate(isReverse: Boolean) {
        val left = mTvTitle.left
        val ivLeft = mIvSearch.left
        val x = ivLeft - left
        print(x)
        val xx = if (isReverse) 0 else -x
        val translate = ObjectAnimator.ofFloat(mIvSearch, "translationX", xx.toFloat())
        translate.duration = 300
        if (isReverse) {
            mIvSearch.clearColorFilter()
        } else {
            setIvSearchIconTint(R.color.colorTintIconBlack)
        }
        mIvSearch.postDelayed({
            translate.start()
        }, 100)

    }

    private fun animateRevealColorFromCoordinates(viewRoot: ViewGroup?, @ColorRes color: Int, x: Int, y: Int) {
        val finalRadius = Math.hypot(viewRoot!!.width.toDouble(), viewRoot.height.toDouble()).toFloat()
        var anim: Animator? = null
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0f, finalRadius)
        }
        viewRoot.setBackgroundColor(ResourceUtil.getColor(mContext, color))
        if (anim != null) {
            anim.duration = 500
            anim.interpolator = AccelerateDecelerateInterpolator()
            anim.start()
        }
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v.id == R.id.et_search && event.action == MotionEvent.ACTION_DOWN) {
            if (mEtSearch.compoundDrawables[2] != null) {
                if (event.x > mEtSearch.width - mEtSearch.compoundDrawables[2].bounds.width()) {
                    hideSearch()
                } else {
                    updateSoftInput(true)
                }
            }
        }
        return true
    }

    private fun showSearch() {
        startActivity(IntentUtil.toSearch(mContext))
        return

/*
        var bgColor = R.color.material_light_white;
        if (PrefUtil.isNightMode()) {
            bgColor = R.color.material_light_black
        }
        animateRevealColorFromCoordinates(mLlSearch, bgColor, circularX, circularY)
        mTabLayout.visibility = View.GONE
        mEtSearch.visibility = View.VISIBLE
        mLlOptions.visibility = View.VISIBLE
        mTvTitle.visibility = View.GONE
        translate(false)
        showInput()
*/
    }

    private fun hideSearch() {
        mEtSearch.visibility = View.GONE
        mLlOptions.visibility = View.GONE
        mTvTitle.visibility = View.VISIBLE
        mTabLayout.visibility = View.VISIBLE
        mLlSearch.setBackgroundColor(ResourceUtil.getColor(mContext, R.color.colorPrimary))
        translate(true)
        hideInput()
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }

    }

}
