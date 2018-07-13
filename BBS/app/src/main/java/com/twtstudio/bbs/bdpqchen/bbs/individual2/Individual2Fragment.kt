package com.twtstudio.bbs.bdpqchen.bbs.individual2

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.INTENT_RESULT_UPDATE_INFO
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_UPDATE_INFO
import com.twtstudio.bbs.bdpqchen.bbs.commons.tools.AuthTool
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.*
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel
import com.twtstudio.bbs.bdpqchen.bbs.individual.release.ReleaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.individual.settings.SettingsActivity
import com.twtstudio.bbs.bdpqchen.bbs.individual.star.StarActivity
import com.twtstudio.bbs.bdpqchen.bbs.person.PersonActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotterknife.bindView
import me.yokeyword.fragmentation.SupportFragment

/**
 * Created by linjiaxin on 2018/7/12.
 */
class Individual2Fragment : BaseFragment(), Individual2Contract.View {

    private val xPresenter: Individual2Presenter? = Individual2Presenter(this)
    private val mInfo: ConstraintLayout by bindView(R.id.ind_info)
    private val mAvatar: CircleImageView by bindView(R.id.ind_avatar)
    private val mPastDays: TextView by bindView(R.id.ind_past_day_data)
    private val mPostCount: TextView by bindView(R.id.ind_post_count_data)
    private val mNickname: TextView by bindView(R.id.ind_nickname)
    private val mSignature: TextView by bindView(R.id.ind_signature)
    private val mPoints: TextView by bindView(R.id.ind_points_data)
    private val mHonor: TextView by bindView(R.id.ind_honor)
    private val mcollections: LinearLayout by bindView(R.id.ind_collection)
    private val mPublish: LinearLayout by bindView(R.id.ind_publish)
    private val mSettings: LinearLayout by bindView(R.id.ind_setting)
    private val ACT_IND = 1
    private val ACT_STAR = 2
    private val ACT_PUBLISH = 3
    private val ACT_SETS = 4
    private var NICKNAME = PrefUtil.getInfoNickname()

    override fun getFragmentLayoutId(): Int = R.layout.fragment_individual2

    override fun gotInfo(info: IndividualInfoModel) {
        AuthTool.userInfo(info)
        ImageUtil.loadMyAvatar(mContext, mAvatar)
        mPastDays.setText(TextUtil.getPastDays(mContext, info.t_create), TextView.BufferType.SPANNABLE)
        mPostCount.setText(PrefUtil.getInfoPost().toString())
        mNickname.setText(PrefUtil.getInfoNickname())
        mSignature.setText(PrefUtil.getInfoSignature())
        mPoints.setText(PrefUtil.getInfoPoints().toString())
        mHonor.setText(TextUtil.getHonor(info.points))
    }

    private fun getInfo() {
        xPresenter!!.initIndividualInfo()
    }

    override fun getPresenter(): Individual2Presenter? = xPresenter

    override fun initFragment() {

        xPresenter!!.initIndividualInfo()
        mInfo.setOnClickListener { startItemActivity(ACT_IND) }
        mcollections.setOnClickListener { startItemActivity(ACT_STAR) }
        mPublish.setOnClickListener { startItemActivity(ACT_PUBLISH) }
        mSettings.setOnClickListener { startItemActivity(ACT_SETS) }


    }

    fun startItemActivity(index: Int) {
        var intent: Intent = Intent()
        when (index) {
            ACT_IND -> {
                intent.putExtra("uid", PrefUtil.getAuthUid())
                intent.setClass(mContext, PersonActivity::class.java)
            }
            ACT_STAR -> intent.setClass(mContext, StarActivity::class.java)
            ACT_PUBLISH -> intent.setClass(mContext, ReleaseActivity::class.java)
            ACT_SETS -> intent.setClass(mContext, SettingsActivity::class.java)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (NICKNAME.length == 0) {
            NICKNAME = "taken"
            getInfo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == REQUEST_CODE_UPDATE_INFO && resultCode == SupportFragment.RESULT_OK
                && data.getBooleanExtra(INTENT_RESULT_UPDATE_INFO, false)) {
            refreshInfo()
        }
    }

    private fun refreshInfo() {
        ImageUtil.refreshMyAvatar(mContext, mAvatar)
        mNickname.setText(PrefUtil.getInfoNickname())
        mSignature.setText(PrefUtil.getInfoSignature())
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(this.activity!!, 255, null)
        return rootView
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (mPresenter != null) {
            getInfo()
            ImageUtil.loadMyAvatar(mContext, mAvatar)
        }
    }

    override fun getInfoFailed(m: String) {
        if (m.contains("token") || m.contains("UID") || m.contains("过期") || m.contains("无效")) {
            SnackBarUtil.error(mActivity, "当前账户的登录信息已过期，请重新登录", true)
            HandlerUtil.postDelay({ AuthTool.logout(this.activity) }, 3000)
        }
    }

    companion object {
        fun newInstance(): Individual2Fragment {
            return Individual2Fragment()
        }
    }
}
