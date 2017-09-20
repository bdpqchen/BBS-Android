package com.twtstudio.bbs.bdpqchen.bbs.mdeditor;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twtstudio.bbs.bdpqchen.bbs.R;
import com.twtstudio.bbs.bdpqchen.bbs.commons.fragment.SimpleFragment;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.TextUtil;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.GlideImageGeter;
import com.twtstudio.bbs.bdpqchen.bbs.htmltextview.HtmlTextView;

import butterknife.BindView;

public class EditorMarkdownFragment extends SimpleFragment {
    protected TextView mName;
    @BindView(R.id.tv_preview_title)
    TextView mTitle;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @BindView(R.id.htv_preview_content)
    HtmlTextView mHtvPreviewContent;
    private String mContent;
    private OnContentListener mListener;

    public EditorMarkdownFragment() {}

    public static EditorMarkdownFragment getInstance() {
        return new EditorMarkdownFragment();
    }

    @Override
    protected int getPerMainFragmentLayoutId() {
        return R.layout.fragment_markdown;
    }

    @Override
    protected void initFragments() {
        mTitle.setText(mListener.getTitleOfContent());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnContentListener) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            drawPreview();
        }
    }

    private void drawPreview() {
        mContent = mListener.getContent();
        String htmlContent = TextUtil.convert2HtmlContent(mContent);
        mHtvPreviewContent.setHtml(htmlContent, new GlideImageGeter(mContext, mHtvPreviewContent));
    }

}
