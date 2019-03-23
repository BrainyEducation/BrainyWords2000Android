package com.hearatale.bw2000.ui.quiz_congrats;

import com.hearatale.bw2000.ui.base.MvpPresenter;

public interface QuizCongratsMvpPresenter<V extends QuizCongratsMvpView> extends MvpPresenter<V> {
    void playAudioWithPath();

}
