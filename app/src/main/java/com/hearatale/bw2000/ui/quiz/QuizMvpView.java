package com.hearatale.bw2000.ui.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.hearatale.bw2000.data.model.QuestionModel;
import com.hearatale.bw2000.ui.base.MvpView;

public interface QuizMvpView extends MvpView {
    void finishUpdateQuestion(QuestionModel question);

    void pushIntentForResult(Intent intent, int requestCode);

    AppCompatActivity getActivity();

    void pushIntent(Intent intent);

}
