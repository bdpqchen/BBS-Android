package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.DialogUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.ImagePickUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PathUtil;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.SnackBarUtil;
import com.twtstudio.bbs.bdpqchen.bbs.forum.boards.thread.model.UploadImageModel;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.PRE_ATTACH;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.REQUEST_CODE_IMAGE_SELECTED;

public class EditorFragment extends BaseFragment<EditorPresenter> implements EditorContract.View {
    @BindView(R.id.tv_editor_title)
    TextView mTitle;
    @BindView(R.id.et_editor_content)
    EditText mEtContent;

    private String mContent = "";
    private PerformEditable mPerformEditable;
    private MaterialDialog mDialog;
    public EditorFragment() {}

    public static EditorFragment getInstance() {
        return new EditorFragment();
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_editor;
    }

    @Override
    protected void injectFragment() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initFragment() {
//        LogUtil.dd("InitView");
        mPerformEditable = new PerformEditable(mEtContent);
        PerformInputAfter.start(mEtContent);
        mTitle.setText(getTitle());
        mEtContent.setText(mContent);
        if (mContent != null && mContent.length() > 0){
            mEtContent.setSelection(mContent.length());
        }

    }

    public PerformEditable getPerformEditable() {
        return mPerformEditable;
    }

    public String getContent(){
        if (mEtContent == null){
            return "";
        }
        return mEtContent.getText().toString();
    }

    public String getTitle(){
        return mListener.getTitleOfContent();
    }

    OnContentListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnContentListener) context;
    }

    @Override
    public void onUpload(UploadImageModel entity) {
        dismissDialog();
        SnackBarUtil.normal(this.getActivity(), "图片上传成功");
        getPerformEditable().perform(R.id.id_shortcut_insert_photo, PRE_ATTACH + entity.getId());

    }

    @Override
    public void onUploadFailed(String msg) {
        dismissDialog();
        SnackBarUtil.error(this.getActivity(), msg);
    }

    public void uploadImage() {
        ImagePickUtil.commonPickImage(this);
    }

    private void showProgressDialog(){
        if (mDialog == null){
            mDialog = DialogUtil.showProgressDialog(mContext, "正在上传图片, 稍后...");
        }
        mDialog.show();
    }
    private void dismissDialog(){
        if (mDialog != null){
            mDialog.dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_SELECTED && resultCode == RESULT_OK) {
            if (data != null) {
                List<Uri> mSelected = Matisse.obtainResult(data);
                mPresenter.uploadImage(PathUtil.getRealPathFromURI(mContext, mSelected.get(0)));
                showProgressDialog();
                // TODO: 17-6-9  支持多张图片
            }
        }

    }

    public void setContent(String content) {
        mContent = content;
    }


}
