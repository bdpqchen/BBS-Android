package com.twtstudio.bbs.bdpqchen.bbs.individual.letter;

import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.commons.model.BaseModel;

import java.util.List;

/**
 * Created by bdpqchen on 17-7-4.
 */

interface LetterContract {
    interface View extends BaseView{
        void onGetLetterList(List<LetterModel> modelList);
        void onRefreshList(List<LetterModel> modelList);
        void onGetLetterFailed(String m);
        void onSend(BaseModel model);
        void onSendFailed(String m);
    }
    interface Presenter extends BasePresenter<View>{
        void getLetterList(int uid, int page, int mode);
        void sendLetter(int to_uid, String content);
    }
}
