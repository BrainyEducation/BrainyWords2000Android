package com.hearatale.bw2000.data;


import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.data.model.typedef.InteriorViewDef;
import com.hearatale.bw2000.data.prefs.PreferencesHelper;

import java.util.List;

public interface DataManager extends PreferencesHelper {

    InfoStreetView getInfoInteriorView(@InteriorViewDef int interiorDef);

    List<Item> getDataDisplayItem(String category);

    List<String> getListCategory();

    float getTotalScore();

    void studentLogout();

    void teacherLogout();
}
