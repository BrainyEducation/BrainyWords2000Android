package com.hearatale.bw2000.ui.base;

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();
}
