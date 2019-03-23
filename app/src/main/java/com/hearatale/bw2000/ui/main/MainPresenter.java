package com.hearatale.bw2000.ui.main;

import android.content.Intent;

import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.typedef.ActionStreetDef;
import com.hearatale.bw2000.data.model.typedef.ScreenDef;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BasePresenter;
import com.hearatale.bw2000.ui.interior.InteriorActivity;
import com.hearatale.bw2000.ui.splash.SplashActivity;
import com.hearatale.bw2000.ui.store_display_items.DisplayItemsActivity;
import com.hearatale.bw2000.util.Config;

import java.util.List;


public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    private final String PREFIX_HEADING = "00";
    private AudioPlayerHelper mAudioPlayerHelper = AudioPlayerHelper.getInstance();

    @Override
    public List<InfoStreetView> getListInfoStreetView() {
        return getDataManager().getListInfoStreetView();
    }

    @Override
    public void handleInfoButton(InfoButton infoButton) {
        switch (infoButton.getTypeAction()) {
            case ActionStreetDef.PLAY_SOUND:
                AudioPlayerHelper.getInstance().playAudio(infoButton.getTag());
                break;
            case ActionStreetDef.OPEN_INTERIOR:
                playAudioAndOpenInterior(infoButton);
                break;
            case ActionStreetDef.OPEN_STORE:
                playAudioAndOpenStore(infoButton);
                break;
            case ActionStreetDef.OTHER:
                break;
        }
    }

    @Override
    public void goSplashScreen() {
        Intent intent = new Intent(getMvpView().getActivity(), SplashActivity.class);
        getMvpView().pushIntent(intent);
    }

    private void playAudioAndOpenInterior(InfoButton infoButton) {
        final String tag = infoButton.getTag();
        playAudioExtraHeading(tag, new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                goToInterior(tag);
            }
        });
    }

    private void goToInterior(String tag) {
        Intent intent = new Intent(getMvpView().getActivity(), InteriorActivity.class);
        intent.putExtra(Config.CATEGORY, tag);
        getMvpView().pushIntent(intent);
    }

    private void playAudioAndOpenStore(InfoButton infoButton) {
        final String tag = infoButton.getTag();
        String object = parseTag(tag); //get object
        playAudioExtraHeading(object, new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                goToDisplayItems(tag);
            }
        });
        //TODO go to DisplayItems
    }

    private void playAudioExtraHeading(String object, AudioPlayerHelper.DonePlayingListener listener) {
        if (object.equals("clothes")) {
            object = "Clothing";
        } else if (object.equals("Outdoors/beach/creatures")) {
            object = "beach_creatures";
        }
        mAudioPlayerHelper.playAudioWithPrefix(Config.ASSETS_PATH_EXTRA_HEADINGS, PREFIX_HEADING + object, listener);
    }

    private String parseTag(String tag) {
        if (!tag.equals("Construction") && !tag.equals("Outdoors/beach/creatures")) {
            String[] path = tag.split("/");
            tag = path[path.length - 1];
        }
        return tag;
    }

    private void goToDisplayItems(String titleCategory) {
        Intent intent = new Intent(getMvpView().getActivity(), DisplayItemsActivity.class);
        intent.putExtra(Config.CATEGORY, titleCategory);
        intent.putExtra(Config.FROM_SCREEN, ScreenDef.MAIN);
        getMvpView().pushIntent(intent);
    }
}
