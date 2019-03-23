package com.hearatale.bw2000.ui.quiz;

import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.QuestionModel;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.MvpPresenter;

import java.util.List;

public interface QuizMvpPresenter<V extends QuizMvpView> extends MvpPresenter<V> {
    void updateQuestion(String category);

    void playAudioQuestion();

    //play audio default
    void playAudioCorrect(AudioPlayerHelper.DonePlayingListener listener);

    void playAudioInCorrect(AudioPlayerHelper.DonePlayingListener listener);

    void startCongratulations();

    void startCelebration(String type, int endX, int endY, int countTruck);

    QuestionModel exportDataQuestion();

    //play audio with position list audio correct
    void playAudioCorrect(int position, AudioPlayerHelper.DonePlayingListener listener);

    void goToDisplayScore(boolean isAnimation);

    void getAllImage();

    List<Item> getAllMp3(String category);

    List<String> getListNameImage(String category);

    void resetAllScore();

    void setScore(String category, float score, float totalScore);

    float getTotalScore();

    int getCurrentPraiseIndex();

    void setCurrentPraiseIndex(int index);

    List<Item> getData(String titleCategory);

    void setCategory(String mCategory);
}
