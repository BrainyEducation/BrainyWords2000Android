package com.hearatale.bw2000.ui.splash;

import com.hearatale.bw2000.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void playAudioWithPath();

    void goToMainActivity();
}
