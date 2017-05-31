package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.SimpleBean;

/**
 * Created by whm on 2017/5/12.
 **/

class CollectionPresenter extends RxPresenter<CollectionContract.View> implements CollectionContract.Presenter {

    private CollectionContract.View collectionView;
    private CollectionClient collectionClient = new CollectionClient(this);
    private CollectionAdapter collectionAdapter;
    CollectionAdapter.CollectionViewHolder collectionViewHolder;

    CollectionPresenter(CollectionContract.View collectionView) {
        this.collectionView = collectionView;
    }

    @Override
    public void setCollectionDate(CollectionBean collectionDate) {
        if (collectionDate != null) {
            collectionView.setCollectionAdapter(collectionDate);
//            System.out.println("CollectionPresenter.setCollectionDate" + collectionDate);
            if (collectionDate.data == null || collectionDate.data.size() == 0) {
                collectionView.setNoCollectionVisible();
            } else {
                collectionView.setNoCollectionInvisible();
            }
        }
    }

    @Override
    public void loadCollections() {
        collectionClient.loadCollection();
    }

    @Override
    public void deleteCollection(CollectionAdapter collectionAdapter, CollectionAdapter.CollectionViewHolder collectionViewHolder, int tid) {
        this.collectionAdapter = collectionAdapter;
        this.collectionViewHolder = collectionViewHolder;
        collectionClient.deleteCollection(tid);
    }

    @Override
    public void dealDeleteData(SimpleBean simpleBean) {
        if (simpleBean.err == 0) {
            collectionView.deleteCollectionSuccess();
            collectionAdapter.goneCollectedStar(collectionViewHolder);
            collectionAdapter.setCollectStar(collectionViewHolder);
        } else {
            collectionView.deleteCollectionFail(simpleBean.data);
        }
    }

    @Override
    public void collectByTid(CollectionAdapter collectionAdapter, CollectionAdapter.CollectionViewHolder collectionViewHolder, String tid) {
        this.collectionAdapter = collectionAdapter;
        this.collectionViewHolder = collectionViewHolder;
        collectionClient.collectByTid(tid);
    }

    @Override
    public void dealCollectData(SimpleBean simpleBean) {
        if (simpleBean == null) {
            collectionView.collectFail("网络错误");
        } else if (simpleBean.err != 0) {
            collectionView.collectFail(simpleBean.data);
        } else {
            collectionView.collectSuccess();
            collectionAdapter.setCollectedStar(collectionViewHolder);
            collectionAdapter.goneCollectStar(collectionViewHolder);
        }
    }

}
