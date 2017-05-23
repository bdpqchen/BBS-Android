package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BasePresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.base.BaseView;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.IndividualInfoModel;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.SimpleBean;

/**
 * Created by whm on 2017/5/12.
 **/

interface CollectionContract  {
    interface View extends BaseView{
        void setCollectionAdapter(CollectionBean collectionDate);
        void makeDeleteSuccessToast();
        void setNoCollectionVisible();
        void setNoCollectionInvisible();
        void deleteCollectionSuccess();
        void deleteCollectionFail(String msg);
        void collectSuccess();
        void collectFail(String msg);

    }
    interface Presenter extends BasePresenter<View>{
        void setCollectionDate(CollectionBean collectionDate);
        void loadCollections();
        void deleteCollection(CollectionAdapter collectionAdapter, CollectionAdapter.CollectionViewHolder collectionViewHolder,int tid);

        void dealDeleteData(SimpleBean simpleBean);
        void collectByTid(CollectionAdapter collectionAdapter, CollectionAdapter.CollectionViewHolder collectionViewHolder,String tid);
        void dealCollectData(SimpleBean simpleBean);
    }
}
