package com.hearatale.bw2000.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hearatale.bw2000.R;
import com.hearatale.bw2000.data.model.InfoButton;
import com.hearatale.bw2000.data.model.InfoStreetView;
import com.hearatale.bw2000.ui.custom.StreetView;
import com.hearatale.bw2000.util.Config;
import com.hearatale.bw2000.util.FileHelper;

import java.util.List;

public class StreetsViewAdapter extends RecyclerView.Adapter<StreetsViewAdapter.ViewHolder> {

    int heightItem = 0;
    List<InfoStreetView> mData;
    Context mContext;
    OnGetInfoButton onGetInfoButton;

    public void setOnGetInfoButton(OnGetInfoButton onGetInfoButton) {
        this.onGetInfoButton = onGetInfoButton;
    }

    public StreetsViewAdapter(List<InfoStreetView> data, int heightItem, Context context) {
        this.heightItem = heightItem;
        this.mData = data;
        this.mContext = context;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        position = position % mData.size();
        InfoStreetView item = mData.get(position);
        StreetView streetView = holder.streetView;

        //Get bitmap original (not resize or decrease quality) => purpose to zoom view
        Bitmap bitmap = FileHelper.getBitmapFromAsset(mContext, Config.BUILDING_ASSETS + item.getImageLink());
        int width = (bitmap.getWidth() * heightItem / bitmap.getHeight());
        ViewGroup.LayoutParams layoutParams = streetView.getLayoutParams();
        layoutParams.width = width;
        streetView.setLayoutParams(layoutParams);
        streetView.setInfoView(item);
        streetView.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        StreetView streetView;

        ViewHolder(StreetView itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.streetView = itemView;
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public StreetsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        StreetView v = (StreetView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_street_view, parent, false);
        v.setOnClickGetInfoButtonListener(new StreetView.OnClickGetButtonInfoListener() {
            @Override
            public void onClick(View view, InfoButton infoButton) {
                if (onGetInfoButton != null) onGetInfoButton.onClick(view, infoButton);
            }
        });
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public interface OnGetInfoButton {
        void onClick(View view, InfoButton infoButton);
    }
}
