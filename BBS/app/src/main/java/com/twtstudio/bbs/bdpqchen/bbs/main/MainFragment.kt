package com.twtstudio.bbs.bdpqchen.bbs.main

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.support.annotation.ColorRes
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import com.jaeger.library.StatusBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ResourceUtil
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.VersionUtil

/**
 * Created by bdpqchen on 17-5-3.
 */
public class MainFragment : SimpleFragment(), View.OnTouchListener {

    @BindView(R.id.main_tab_layout)
    internal var mTabLayout: TabLayout? = null
    @BindView(R.id.main_viewpager)
    internal var mViewpager: ViewPager? = null
    @BindView(R.id.appbar)
    internal var mAppbar: AppBarLayout? = null
    @BindView(R.id.tv_title)
    internal var mTvTitle: TextView? = null
    @BindView(R.id.iv_search)
    internal var mIvSearch: ImageView? = null
    @BindView(R.id.et_search)
    internal var mEtSearch: AppCompatEditText? = null
    @BindView(R.id.rl_input)
    internal var mRlInput: RelativeLayout? = null
    @BindView(R.id.ll_options)
    internal var mLlOptions: LinearLayout? = null
    @BindView(R.id.rl_search)
    internal var mRlSearch: RelativeLayout? = null
    @BindView(R.id.tv_option1)
    internal var mTvOption1: TextView? = null
    @BindView(R.id.tv_option2)
    internal var mTvOption2: TextView? = null

    internal val circularX: Int
        get() = mIvSearch!!.right

    internal val circularY: Int
        get() = mIvSearch!!.height / 2 + mIvSearch!!.top

    override fun getPerMainFragmentLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun initFragments() {
        mAppbar!!.setExpanded(false)
        StatusBarUtil.setColor(this.activity, ResourceUtil.getColor(this.activity, R.color.colorPrimaryDark), 0)
        val tabAdapter = MainTabAdapter(fragmentManager, mContext)
        mViewpager!!.adapter = tabAdapter
        mTabLayout!!.setupWithViewPager(mViewpager)
        mEtSearch!!.setOnTouchListener(this)
        mIvSearch!!.setOnClickListener { showSearch() }
        setViewClickEffects(mTvOption1!!, mTvOption2!!)
        setIconTint()

    }


    private fun setIconTint() {
        mIvSearch!!.setColorFilter(R.color.colorTintIconBlack, PorterDuff.Mode.SRC_IN)
        //        mEtSearch.getCompoundDrawables()[2].setColorFilter();
    }

    private fun setViewClickEffects(vararg views: View) {
        if (VersionUtil.eaMarshmallow()) {
            for (view in views) {
                view.foreground = ResourceUtil.getDrawable(mContext, R.drawable.selector_foreground_settings)
            }
        }
    }

    private fun print(s: String) {
        Log.d("====", s)
    }

    private fun print(i: Int) {
        print(i.toString())
    }

    private fun hideInput() {
        updateSoftInput(false)
    }

    private fun updateSoftInput(isShow: Boolean) {
        if (isShow) {
            mEtSearch!!.requestFocus()
        }
        mEtSearch!!.postDelayed({
            val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (isShow)
                imm.showSoftInput(mEtSearch, InputMethodManager.SHOW_IMPLICIT)
            else
                imm.hideSoftInputFromWindow(mEtSearch!!.windowToken, 0)
        }, 100)
    }

    private fun showInput() {
        updateSoftInput(true)
    }

    private fun translate(isReverse: Boolean) {
        val left = mTvTitle!!.left
        val ivLeft = mIvSearch!!.left
        val x = ivLeft - left
        print(x)
        val xx = if (isReverse) 0 else -x
        val translate = ObjectAnimator.ofFloat(mIvSearch, "translationX", xx.toFloat())
        translate.duration = 300
        mIvSearch!!.postDelayed({ translate.start() }, 50)

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
            if (mEtSearch!!.compoundDrawables[2] != null) {
                if (event.x > mEtSearch!!.width - mEtSearch!!.compoundDrawables[2].bounds.width()) {
                    hideSearch()
                }
            }
        }
        return true
    }

    private fun showSearch() {
        animateRevealColorFromCoordinates(mRlSearch, R.color.material_light_white, circularX, circularY)
        mTabLayout!!.visibility = View.GONE
        mEtSearch!!.visibility = View.VISIBLE
        mLlOptions!!.visibility = View.VISIBLE
        mTvTitle!!.visibility = View.GONE
        translate(false)
        showInput()
    }

    private fun hideSearch() {
        mEtSearch!!.visibility = View.GONE
        mLlOptions!!.visibility = View.GONE
        mTvTitle!!.visibility = View.VISIBLE
        mTabLayout!!.visibility = View.VISIBLE
        mRlSearch!!.setBackgroundColor(ResourceUtil.getColor(mContext, R.color.colorPrimary))
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
