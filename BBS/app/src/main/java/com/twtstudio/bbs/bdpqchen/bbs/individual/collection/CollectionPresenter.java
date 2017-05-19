package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;

/**
 * Created by HP on 2017/5/12.
 */

class CollectionPresenter extends RxPresenter<CollectionContract.View> implements CollectionContract.Presenter {

    CollectionContract.View collectionView;
    CollectionClient collectionClient = new CollectionClient(this);

    // TODO: 2017/5/14 换成用户的uid和Token
    String tempUidToken = "21145|SXNW5h9w1Njag3hXSt-2r8IGiOmk3b_tU-nqTddYhOk";

    public CollectionPresenter(CollectionContract.View collectionView) {
        this.collectionView = collectionView;
    }

    @Override
    public void setCollectionDate(CollectionBean collectionDate) {
        collectionView.setCollectionAdapter(collectionDate);
        if(collectionDate.data==null||collectionDate.data.size()==0){
            System.out.println("abcdefdatenull");
            collectionView.setNoCollectionVisible();
        }
        else{
            collectionView.setNoCollectionInvisible();
        }
    }

    @Override
    public void loadCollections() {
        collectionClient.loadCollection(tempUidToken);
    }

    @Override
    public void deleteCollection(int tid) {
        collectionClient.deleteCollection(tempUidToken,tid);
    }

    @Override
    public void makeDeleteSuccessToast() {
        collectionView.makeDeleteSuccessToast();
    }

}
