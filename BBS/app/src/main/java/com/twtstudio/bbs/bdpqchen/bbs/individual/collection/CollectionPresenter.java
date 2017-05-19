package com.twtstudio.bbs.bdpqchen.bbs.individual.collection;

import com.twtstudio.bbs.bdpqchen.bbs.commons.presenter.RxPresenter;
import com.twtstudio.bbs.bdpqchen.bbs.commons.utils.PrefUtil;
import com.twtstudio.bbs.bdpqchen.bbs.individual.model.CollectionBean;

/**
 * Created by HP on 2017/5/12.
 */

public class CollectionPresenter extends RxPresenter<CollectionContract.View> implements CollectionContract.Presenter {

    private CollectionContract.View collectionView;
    private CollectionClient collectionClient = new CollectionClient(this);

    // TODO: 2017/5/14 换成用户的uid和Token
    private String uid = String.valueOf(PrefUtil.getAuthUid());
    private String uidToken = uid +"|"+ PrefUtil.getAuthToken();
    String tempUidToken = "21145|wTAutgt4yr8LyE2Tl6tkiQstZkySpASnqs1R8yHell0";

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
        collectionClient.loadCollection(uidToken);
    }

    @Override
    public void deleteCollection(int tid) {
        collectionClient.deleteCollection(uidToken,tid);
    }

    @Override
    public void makeDeleteSuccessToast() {
        collectionView.makeDeleteSuccessToast();
    }

}
