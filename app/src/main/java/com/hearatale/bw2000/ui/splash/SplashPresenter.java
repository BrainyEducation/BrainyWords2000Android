package com.hearatale.bw2000.ui.splash;

import android.content.Intent;

import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.model.student.BaseResponseStudentLogin;
import com.hearatale.bw2000.data.model.teacher_login.BaseResponseTeacherLogin;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BasePresenter;
import com.hearatale.bw2000.ui.main.MainActivity;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {
    private static final String PATH = "xtra/HEADINGS/00brainy_words_2000.mp3";

    SplashPresenter() {
    }

    @Override
    public void playAudioWithPath() {
        AudioPlayerHelper.getInstance().playAudio(PATH, new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                goToMainActivity();
                getMvpView().getActivity().finish();
            }
        });
    }
    @Override
    public void goToMainActivity() {
        Intent intent = new Intent(getMvpView().getActivity(), MainActivity.class);
        getMvpView().pushIntent(intent);
    }
}