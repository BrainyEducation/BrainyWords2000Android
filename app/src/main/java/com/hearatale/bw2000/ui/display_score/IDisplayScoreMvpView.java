package com.hearatale.bw2000.ui.display_score;

import android.app.Activity;
import android.content.Intent;

import com.hearatale.bw2000.ui.base.MvpView;

public interface IDisplayScoreMvpView extends MvpView {
        Activity getActivity();

        void pushIntent(Intent intent);
}
