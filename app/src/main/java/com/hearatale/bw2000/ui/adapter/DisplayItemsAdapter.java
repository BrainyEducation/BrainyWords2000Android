package com.hearatale.bw2000.ui.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.Guideline;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hearatale.bw2000.Application;
import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.model.Item;
import com.hearatale.bw2000.util.glide.GlideApp;
import com.orhanobut.logger.Logger;

import java.util.List;

public class DisplayItemsAdapter extends BaseQuickAdapter<Item, BaseViewHolder> {
    private static final String ROOT_URI = "file:///android_asset/";

    public DisplayItemsAdapter(@Nullable List<Item> data) {
        super(R.layout.item_displayitem, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Item item) {
        if(helper.getAdapterPosition() == 0){
            Guideline guideline = helper.getView(R.id.guideline1);
            guideline.setGuidelinePercent(0f);
            guideline.setGuidelineBegin(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_16));
        }
        helper.setText(R.id.textView, item.getTitle());
        ImageView imageView = helper.getView(R.id.imageView);
        int WIDTH = Application.Context.getResources().getDimensionPixelOffset(R.dimen._200sdp);
        int HEIGHT = Application.Context.getResources().getDimensionPixelOffset(R.dimen._200sdp);
        GlideApp.with(mContext)
                .load(ROOT_URI + item.getImageUri())
                .override(WIDTH, HEIGHT)
                .into(imageView);
    }

}
