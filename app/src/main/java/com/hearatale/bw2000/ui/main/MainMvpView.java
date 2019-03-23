package com.hearatale.bw2000.ui.main;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.hearatale.bw2000.ui.base.MvpView;

public interface MainMvpView extends MvpView {


    AppCompatActivity getActivity();

    void pushIntent(Intent intent);

}
