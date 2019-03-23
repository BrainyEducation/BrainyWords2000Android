package com.hearatale.bw2000.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.hearatale.bw2000.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    AppCompatActivity getActivity();

    void pushIntent(Intent intent);

}
