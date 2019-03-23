package com.hearatale.bw2000.ui.store_display_items;

import com.hearatale.bw2000.data.AppDataManager;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.ui.base.BasePresenter;

import java.util.List;

public class DisplayItemsPresenter<V extends DisplayMvpView> extends BasePresenter<V> implements DisplayMvpPresenter<V> {
    AppDataManager appDataManager;

    public DisplayItemsPresenter() {
        appDataManager = AppDataManager.getInstance();
    }

    @Override
    public List<Item> getData(String titleCategory) {
        return appDataManager.getDataDisplayItem(titleCategory);
    }
}
