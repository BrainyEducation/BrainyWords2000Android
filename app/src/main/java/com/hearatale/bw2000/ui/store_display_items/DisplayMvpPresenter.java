package com.hearatale.bw2000.ui.store_display_items;

import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.ui.base.MvpPresenter;

import java.util.List;

public interface DisplayMvpPresenter<V extends DisplayMvpView> extends MvpPresenter<V> {
    List<Item> getData(String titleCategory);
}
