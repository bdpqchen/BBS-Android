package com.twtstudio.bbs.bdpqchen.bbs.commons.base;

import android.view.View;
import android.widget.ImageView;

import com.twtstudio.bbs.bdpqchen.bbs.R;

import butterknife.BindView;

/**
 * Created by bdpqchen on 17-5-30.
 */

public class BaseFooterViewHolder extends BaseViewHolder {

    @BindView(R.id.iv_content)
    ImageView mIvContent;

    public BaseFooterViewHolder(View itemView) {
        super(itemView);


    }
}
