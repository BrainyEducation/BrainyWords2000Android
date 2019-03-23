package com.hearatale.bw2000.ui.main;


import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.ui.base.MvpPresenter;

import java.util.List;

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    List<InfoStreetView> getListInfoStreetView();

    void handleInfoButton(InfoButton infoButton);

    void goSplashScreen();
}
