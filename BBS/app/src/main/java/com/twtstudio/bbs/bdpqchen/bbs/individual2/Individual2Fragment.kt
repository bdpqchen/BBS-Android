package com.twtstudio.bbs.bdpqchen.bbs.individual2

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment
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
class Individual2Fragment : SimpleFragment(), Individual2Contract.View {
    override fun getPerMainFragmentLayoutId(): Int = R.layout.fragment_individual2


    private lateinit var xPresenter: Individual2Presenter
    private val mInfo: ConstraintLayout by bindView(R.id.ind_info)
    private val mAvatar: CircleImageView by bindView(R.id.ind_avatar)
    private val mPastDays: TextView by bindView(R.id.ind_past_day_data)
    private val mPostCount: TextView by bindView(R.id.ind_post_count_data)
    private val mNickname: TextView by bindView(R.id.ind_nickname)
    private val mSignature: TextView by bindView(R.id.ind_signature)
    private val mPoints: TextView by bindView(R.id.ind_points_data)
    private val mHonor: TextView by bindView(R.id.ind_honor)
    private val mCollections: ConstraintLayout by bindView(R.id.ind_collection)
    private val mPublish: ConstraintLayout by bindView(R.id.ind_publish)
    private val mSettings: ConstraintLayout by bindView(R.id.ind_setting)
    private val ACT_IND = 1
    private val ACT_STAR = 2
    private val ACT_PUBLISH = 3
    private val ACT_SETS = 4
    private var NICKNAME = PrefUtil.getInfoNickname()

    override fun gotInfo(model: IndividualInfoModel) {
        AuthTool.userInfo(model)
        ImageUtil.loadMyAvatar(mContext, mAvatar)
        mPastDays.text = "" + model.c_online
        mPostCount.text = "" + model.c_thread
        mNickname.text = model.nickname
        mSignature.text = model.signature
        mPoints.text = "" + model.points
        mHonor.text = TextUtil.getHonor(model.points)
    }

    private fun getInfo() {
        xPresenter.initIndividualInfo()
    }


    override fun initFragments() {
        xPresenter = Individual2Presenter(this)
        xPresenter.initIndividualInfo()
        mInfo.setOnClickListener { startItemActivity(ACT_IND) }
        mCollections.setOnClickListener { startItemActivity(ACT_STAR) }
        mPublish.setOnClickListener { startItemActivity(ACT_PUBLISH) }
        mSettings.setOnClickListener { startItemActivity(ACT_SETS) }


    }

    private fun startItemActivity(index: Int) {
        val intent: Intent = Intent()
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
        if (NICKNAME.isEmpty()) {
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
        mNickname.text = PrefUtil.getInfoNickname()
        mSignature.text = PrefUtil.getInfoSignature()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = super.onCreateView(inflater, container, savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(this.activity!!, 255, null)
        return rootView
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        getInfo()
        ImageUtil.loadMyAvatar(mContext, mAvatar)
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
