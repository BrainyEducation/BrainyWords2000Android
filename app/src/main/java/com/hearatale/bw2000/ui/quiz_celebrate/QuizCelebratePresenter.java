package com.hearatale.bw2000.ui.quiz_celebrate;

import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BasePresenter;

public class QuizCelebratePresenter<V extends QuizCelebrateMvpView> extends BasePresenter<V> implements QuizCelebrateMvpPresenter<V> {
    private static final String PATH = "Quiz_Sounds/correct_answer.mp3";

    @Override
    public void playAudioWithPath() {
        AudioPlayerHelper.getInstance().playAudio(PATH);
    }
}