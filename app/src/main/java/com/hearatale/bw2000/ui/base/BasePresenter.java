package com.hearatale.bw2000.ui.base;

import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.DataManager;

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mMvpView;

    private DataManager mDataManager;

    protected BasePresenter() {
        mDataManager = AppDataManager.getInstance();
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        this.mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }


    public DataManager getDataManager() {
        return mDataManager;
    }
}
