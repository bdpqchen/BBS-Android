package com.twtstudio.bbs.bdpqchen.bbs.person

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.home.HomeContract
import com.twtstudio.bbs.bdpqchen.bbs.people.PeopleModel

class PersonActivity : AppCompatActivity() , PersonContract.View {

    private val mPresenter = PersonPresenter(this)

    override fun onPersonInfoSuccess(person: PeopleModel) {
        SnackBarUtil.notice(this@PersonActivity,person.toString())
    }

    override fun onLoadFailed(info: String) {
        Toast.makeText(this@PersonActivity,"fail",Toast.LENGTH_SHORT).show()
    }

    override fun onThreadInfoSuccess(thread: ThreadModel.ThreadBean) {
        SnackBarUtil.notice(this@PersonActivity,"帖子加载成功")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        val uid = intent.getIntExtra("uid",0)
        mPresenter.getPersonInfo(uid)
    }
}
