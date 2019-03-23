package com.hearatale.bw2000.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class InfoStreetView implements Parcelable {
    private String imageLink;

    private List<InfoButton> listInfoButton;

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


    public List<InfoButton> getListInfoButton() {
        return listInfoButton;
    }

    public void setListInfoButton(List<InfoButton> listInfoButton) {
        this.listInfoButton = listInfoButton;
    }

    public InfoStreetView() {
    }

    public InfoButton getInfoButtonClick(float percentClickX, float percentClickY) {
        for (InfoButton infoButton : listInfoButton) {
            if (infoButton.isClickInButton(percentClickX, percentClickY))
                return infoButton;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageLink);
        dest.writeTypedList(this.listInfoButton);
    }

    protected InfoStreetView(Parcel in) {
        this.imageLink = in.readString();
        this.listInfoButton = in.createTypedArrayList(InfoButton.CREATOR);
    }

    public static final Creator<InfoStreetView> CREATOR = new Creator<InfoStreetView>() {
        @Override
        public InfoStreetView createFromParcel(Parcel source) {
            return new InfoStreetView(source);
        }

        @Override
        public InfoStreetView[] newArray(int size) {
            return new InfoStreetView[size];
        }
    };
}
