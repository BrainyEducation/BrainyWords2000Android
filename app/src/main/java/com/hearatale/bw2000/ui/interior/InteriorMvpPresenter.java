package com.hearatale.bw2000.ui.interior;


import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.typedef.InteriorSpecialDef;
import com.hearatale.bw2000.data.model.typedef.InteriorViewDef;
import com.hearatale.bw2000.ui.base.MvpPresenter;


public interface InteriorMvpPresenter<V extends InteriorMvpView> extends MvpPresenter<V> {

    InfoStreetView getInfoInteriorView(@InteriorViewDef int interiorDef);

    void handleInfoButton(InfoButton infoButton);

    void goInteriorSpecial(@InteriorSpecialDef int special);
}
