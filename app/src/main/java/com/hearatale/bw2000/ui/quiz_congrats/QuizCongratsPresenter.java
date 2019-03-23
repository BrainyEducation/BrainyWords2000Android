package com.hearatale.bw2000.ui.quiz_congrats;

import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BasePresenter;

public class QuizCongratsPresenter<V extends QuizCongratsMvpView> extends BasePresenter<V> implements QuizCongratsMvpPresenter<V> {
    private static final String PATH = "Quiz_Sounds/congratulations.mp3";

    public QuizCongratsPresenter() {
    }


    @Override
    public void playAudioWithPath() {
        AudioPlayerHelper.getInstance().playAudio(PATH, new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                getMvpView().donePlaying();
            }
        });
    }
}
