package com.hearatale.bw2000.ui.interior;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.hearatale.bw2000.ui.base.MvpView;

public interface InteriorMvpView extends MvpView {

    AppCompatActivity getActivity();

    void pushIntent(Intent intent);
}
