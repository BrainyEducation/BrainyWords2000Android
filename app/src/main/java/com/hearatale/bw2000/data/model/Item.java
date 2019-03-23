package com.hearatale.bw2000.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable
{

    private boolean isAnswer = false;

    @SerializedName("app_id")
    @Expose
    private String appId;

    @SerializedName("imageUri")
    @Expose
    private String imageUri;
    @SerializedName("title")
    @Expose
    private String title;


    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isAnswer ? (byte) 1 : (byte) 0);
        dest.writeString(this.appId);
        dest.writeString(this.imageUri);
        dest.writeString(this.title);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.isAnswer = in.readByte() != 0;
        this.appId = in.readString();
        this.imageUri = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}