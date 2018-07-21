package com.twtstudio.bbs.bdpqchen.bbs.forum.boards.edit_thread

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.design.widget.TextInputEditText
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.jaeger.library.StatusBarUtil
import com.twtstudio.bbs.bdpqchen.bbs.R
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseActivity
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter
import com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.*
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.*
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.ThreadModel
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel
import com.zhihu.matisse.Matisse
import kotterknife.bindView

class EditThreadActivity : BaseActivity() , EditThreadContract.View {

    private var tid = ""
    private var mTitle = ""
    private var mContent = ""
    private var isModified = false
    private val mTvOpenEditor : TextView by bindView(R.id.tv_open_editor)
    private val mTvSelectImage : TextView by bindView(R.id.tv_select_image)
    private val mToolbar : Toolbar by bindView(R.id.toolbar)
    private val mEtTitle : TextInputEditText by bindView(R.id.et_title)
    private val mEtContent : TextInputEditText by bindView(R.id.et_content)
    private val mPresenter = EditThreadPresenter(this)
    private lateinit var mAlertDialog :MaterialDialog
    private lateinit var mProgressDialog : MaterialDialog

    override fun getLayoutResourceId(): Int = R.layout.activity_edit_thread

    override fun getToolbarView(): Toolbar {
        mToolbar.title = "编辑帖子"
        return mToolbar
    }

    override fun getPresenter(): BasePresenter = mPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColor(this, Color.WHITE, 0)
        enableLightStatusBarMode(true)
        val intent = intent
        tid = intent.getStringExtra(TID)
        mTitle = intent.getStringExtra(TITLE)
        mContent = intent.getStringExtra(CONTENT)
        mPresenter.getDetailedContent(tid)
        mEtTitle.setText(mTitle,TextView.BufferType.EDITABLE)
        mEtContent.setText(mContent,TextView.BufferType.EDITABLE)
        mTvOpenEditor.setOnClickListener {
            startActivityForResult(IntentUtil.toEditor(mContext , mEtTitle.text.toString(), mEtContent.text.toString(),0 ), REQUEST_CODE_EDITOR)
        }
        mTvSelectImage.setOnClickListener {
            ImagePickUtil.commonPickImage(this)
        }
    }

    override fun onUpLoadSuccess(model: UploadImageModel) {
        mEtContent.isFocusable = true
        TextUtil.addImg2Content(model.id, mEtContent)
        SnackBarUtil.normal(this,"图片已填加")
        hideProgress()
    }

    override fun onUpLoadFailed(msg: String) {
        SnackBarUtil.error(this, "图片添加失败\n$msg")
        hideProgress()
    }

    override fun onModifySuccess(model: EditModel) {
        SnackBarUtil.normal(this, "发布成功")
        mProgressDialog.dismiss()
        Thread.sleep(2000)
        finishMe()
    }

    override fun onModifyFailed(msg: String) {
        mProgressDialog.dismiss()
        SnackBarUtil.error(this, msg, true)
    }

    override fun onGetContentSuccess(model: ThreadModel.ThreadBean) {
        mEtContent.setText(model.content)
    }

    override fun onGetContentFailed(msg: String) {
        SnackBarUtil.error(this,msg)
    }


    override fun onBackPressedSupport() {
        isDangerExit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_SELECTED && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val mSelected = Matisse.obtainResult(data)
                mPresenter.upLoadMessage(PathUtil.getRealPathFromURI(mContext, mSelected[0]))
                showProgress("正在添加图片，请稍后..")
            }
        }
        if (requestCode == REQUEST_CODE_EDITOR && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val contentResult = data.getStringExtra(INTENT_EDITOR_CONTENT)
                mEtContent.setText(contentResult)
                mEtContent.setSelection(contentResult.length)
            }
        }
    }

    private fun showProgress(content: String){
        mProgressDialog = DialogUtil.showProgressDialog(this, content)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menuInflater.inflate(R.menu.menu_create_thread,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_create_thread -> checkInput()
            android.R.id.home -> isDangerExit()
        }
        return false
    }

    private fun setUpData(){
        mTitle = mEtTitle.text.toString()
        mContent = mEtContent.text.toString()
    }

    private fun checkInput(){
        val err = "不能为空啊靓仔"
        var flag = true
        setUpData()
        if (mTitle.isBlank()){
            mEtTitle.error = err
            flag = false
        }
        if (mContent.isBlank()){
            mEtContent.error = err
            flag = false
        }
        if (flag){
            modify()
        }
    }

    private fun modify(){
        mPresenter.doModifyThread(tid,mTitle,mContent)
        showProgress("正在发布，请稍候..")
    }

    private fun hideProgress(){
        mProgressDialog.dismiss()
    }

    private fun isDangerExit() {
        if(isModified){
            finishMe()
            return
        }
        if (mEtTitle.text.toString() != mTitle || mEtContent.text.toString() == mContent ){
            mAlertDialog = DialogUtil.alertDialog(this, "确定放弃正在编辑的内容吗？？", "放弃",
                    "就不放弃", MaterialDialog.SingleButtonCallback { _, _ -> finishMe() },null)
        } else {
            finishMe()
        }
    }
}
