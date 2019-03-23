package com.hearatale.bw2000.ui.quiz_celebrate;

import com.hearatale.bw2000.ui.base.MvpPresenter;

public interface QuizCelebrateMvpPresenter<V extends QuizCelebrateMvpView> extends MvpPresenter<V> {
    void playAudioWithPath();
}
