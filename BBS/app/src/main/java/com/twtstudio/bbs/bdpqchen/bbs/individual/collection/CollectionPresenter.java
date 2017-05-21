package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;

/**
 * Created by HP on 2017/5/12.
 */

class CollectionPresenter extends RxPresenter<CollectionContract.View> implements CollectionContract.Presenter {

    CollectionContract.View collectionView;
    CollectionClient collectionClient = new CollectionClient(this);


    String tempUidToken = PrefUtil.getAuthUid() + "|" +PrefUtil.getAuthToken();

    public CollectionPresenter(CollectionContract.View collectionView) {
        this.collectionView = collectionView;
    }

    @Override
    public void setCollectionDate(CollectionBean collectionDate) {
        collectionView.setCollectionAdapter(collectionDate);
        System.out.println("CollectionPresenter.setCollectionDate"+collectionDate);
        if(collectionDate.data==null||collectionDate.data.size()==0){

            collectionView.setNoCollectionVisible();
        }
        else{
            collectionView.setNoCollectionInvisible();
        }
    }

    @Override
    public void loadCollections() {
        System.out.println("CollectionPresenter.loadCollections"+tempUidToken);
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
