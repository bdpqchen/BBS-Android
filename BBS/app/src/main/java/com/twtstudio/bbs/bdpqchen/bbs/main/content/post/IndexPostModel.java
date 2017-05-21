package com.twtstudio.bbs.bdpqchen.bbs.main.content.post;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhangyulong on 5/20/17.
 */

public class IndexPostModel {

    /**
     * content : 评论内容
     * [reply] : 回复的评论ID
     */

    private String content;
    @SerializedName("[reply]")
    private String _$Reply23; // FIXME check this code

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String get_$Reply23() {
        return _$Reply23;
    }

    public void set_$Reply23(String _$Reply23) {
        this._$Reply23 = _$Reply23;
    }
}
