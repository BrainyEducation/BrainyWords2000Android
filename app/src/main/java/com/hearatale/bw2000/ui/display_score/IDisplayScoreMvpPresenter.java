package com.hearatale.bw2000.ui.display_score;

import com.hearatale.bw2000.ui.base.MvpPresenter;

public interface IDisplayScoreMvpPresenter<V extends IDisplayScoreMvpView> extends MvpPresenter<V> {
    float getTotalScore();

    void startCongratulations();
}
