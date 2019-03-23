package com.hearatale.bw2000.ui.display_score;

import android.content.Intent;

import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.ui.base.BasePresenter;
import com.hearatale.bw2000.ui.quiz_congrats.QuizCongratsActivity;
import com.hearatale.bw2000.util.Config;

public class DisplayScorePresenter<V extends IDisplayScoreMvpView> extends BasePresenter<V> implements IDisplayScoreMvpPresenter<V> {
    @Override
    public float getTotalScore() {
        return AppDataManager.getInstance().getTotalScore();
    }

    @Override
    public void startCongratulations() {
        Intent intent = new Intent(getMvpView().getActivity(), QuizCongratsActivity.class);
        getMvpView().pushIntent(intent);
    }
}
