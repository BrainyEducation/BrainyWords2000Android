package com.hearatale.bw2000.ui.interior;

import android.content.Intent;

import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.typedef.ActionStreetDef;
import com.hearatale.bw2000.data.model.typedef.InteriorSpecialDef;
import com.hearatale.bw2000.data.model.typedef.InteriorViewDef;
import com.hearatale.bw2000.service.AudioPlayerHelper;
import com.hearatale.bw2000.ui.base.BasePresenter;
import com.hearatale.bw2000.ui.store_display_items.DisplayItemsActivity;
import com.hearatale.bw2000.util.Config;


public class InteriorPresenter<V extends InteriorMvpView> extends BasePresenter<V> implements InteriorMvpPresenter<V> {

    private final String PREFIX_HEADING = "00";
    private AudioPlayerHelper mAudioPlayerHelper = AudioPlayerHelper.getInstance();

    @Override
    public InfoStreetView getInfoInteriorView(@InteriorViewDef int interiorDef) {
        return getDataManager().getInfoInteriorView(interiorDef);
    }

    @Override
    public void handleInfoButton(InfoButton infoButton) {
        switch (infoButton.getTypeAction()) {
            case ActionStreetDef.PLAY_SOUND:
                AudioPlayerHelper.getInstance().playAudio(infoButton.getTag());
                break;
            case ActionStreetDef.OPEN_INTERIOR:
                playAudioAndOpenInterior(infoButton, ActionStreetDef.OPEN_INTERIOR);
                break;
            case ActionStreetDef.OPEN_STORE:
                playAudioAndOpenStore(infoButton, ActionStreetDef.OPEN_STORE);
                break;
            case ActionStreetDef.OTHER:
                break;
        }
    }

    @Override
    public void goInteriorSpecial(@InteriorSpecialDef int special) {
        if (special == InteriorSpecialDef.INTERIOR_PARK) {
            goInteriorPark();
        } else if (special == InteriorSpecialDef.INTERIOR_BUILDING7A) {
            goInteriorBuilding7A();
        } else if (special == InteriorSpecialDef.INTERIOR_BUILDING11A) {
            goInteriorBuilding11A();
        }
    }

    private void goInteriorBuilding11A() {
        goToInterior("school_playground");
    }

    private void goInteriorBuilding7A() {
        goToInterior("people");
    }

    private void goInteriorPark() {
        goToInterior("park");
    }

    private void playAudioAndOpenInterior(InfoButton infoButton, @ActionStreetDef int typeAction) {
        String tag = infoButton.getTag();
        playAudioExtraHeading(tag, tag, typeAction);


    }

    private void playAudioAndOpenStore(InfoButton infoButton, @ActionStreetDef int typeAction) {
        String tag = infoButton.getTag();
        String object = parseTag(tag); //get object
        playAudioExtraHeading(tag, object, typeAction);
        //TODO go to DisplayItems
    }

    private void playAudioExtraHeading(final String category, String cat, @ActionStreetDef final int typeAction) {

        if (cat.contains("zoo")) {
            if (cat.contains("water")) {
                cat = "aquatic_water_animals";
            } else if (cat.contains("dino")) {
                cat = "dinosaurs";
            } else if (cat.contains("meat")) {
                cat = "carnivore_meat_eaters";
            } else if (cat.contains("reptiles")) {
                cat = "reptiles";
            } else if (cat.contains("plant")) {
                cat = "herbivore_plant_eaters";
            } else if (cat.contains("monkeys")) {
                cat = "monkeys_and_apes";
            } else if (cat.contains("birds")) {
                cat = "birds_for_zoo_only";
            }
        }
        mAudioPlayerHelper.playAudioWithPrefix(Config.ASSETS_PATH_EXTRA_HEADINGS, PREFIX_HEADING + cat, new AudioPlayerHelper.DonePlayingListener() {
            @Override
            public void donePlaying() {
                switch (typeAction) {
                    case ActionStreetDef.OPEN_INTERIOR:
                        goToInterior(category);
                        break;
                    case ActionStreetDef.OPEN_STORE:
                        String cate = category;
                        if (cate.equals("Health/psychologist")) {
                            cate = "Health/feelings";
                        }
                        goToDisplayItems(cate);
                        break;
                }
            }
        });
    }

    private String parseTag(String tag) {
        if (tag.equals("Vehicles/fun")) {
            tag = tag.replace("/", "_");
        } else if (tag.equals("Mall/hair_salon/boys")) {
            tag = "hair_boys";
        } else if (tag.equals("Mall/hair_salon/girls")) {
            tag = "hair_girls";
        } else {

            String[] path = tag.split("/");
            tag = path[path.length - 1];
        }

        return tag;

    }

    private void goToDisplayItems(String titleCategory) {
        Intent intent = new Intent(getMvpView().getActivity(), DisplayItemsActivity.class);
        intent.putExtra(Config.CATEGORY, titleCategory);
        getMvpView().pushIntent(intent);
    }

    private void goToInterior(String category) {
        Intent intent = new Intent(getMvpView().getActivity(), InteriorActivity.class);
        intent.putExtra(Config.CATEGORY, category);
        getMvpView().pushIntent(intent);
    }


}
