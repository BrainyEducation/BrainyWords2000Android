package com.hearatale.bw2000.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hearatale.bw2000.data.model.typedef.ActionStreetDef;

public class InfoButton implements Parcelable {

    private int id;
    private String name;

    @ActionStreetDef
    private int typeAction;
    private String tag;
    private float percentStartX;
    private float percentStartY;
    private float percentEndX;
    private float percentEndY;


    public boolean isClickInButton(float percentClickX, float percentClickY) {
        return percentStartX <= percentClickX
                && percentEndX >= percentClickX
                && percentStartY <= percentClickY
                && percentEndY >= percentClickY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ActionStreetDef
    public int getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(@ActionStreetDef int typeAction) {
        this.typeAction = typeAction;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getPercentStartX() {
        return percentStartX;
    }

    public void setPercentStartX(float percentStartX) {
        this.percentStartX = percentStartX;
    }

    public float getPercentEndX() {
        return percentEndX;
    }

    public void setPercentEndX(float percentEndX) {
        this.percentEndX = percentEndX;
    }

    public float getPercentStartY() {
        return percentStartY;
    }

    public void setPercentStartY(float percentStartY) {
        this.percentStartY = percentStartY;
    }

    public float getPercentEndY() {
        return percentEndY;
    }

    public void setPercentEndY(float percentEndY) {
        this.percentEndY = percentEndY;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.typeAction);
        dest.writeString(this.tag);
        dest.writeFloat(this.percentStartX);
        dest.writeFloat(this.percentEndX);
        dest.writeFloat(this.percentStartY);
        dest.writeFloat(this.percentEndY);
    }

    public InfoButton() {
    }

    protected InfoButton(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.typeAction = in.readInt();
        this.tag = in.readString();
        this.percentStartX = in.readFloat();
        this.percentEndX = in.readFloat();
        this.percentStartY = in.readFloat();
        this.percentEndY = in.readFloat();
    }

    public static final Creator<InfoButton> CREATOR = new Creator<InfoButton>() {
        @Override
        public InfoButton createFromParcel(Parcel source) {
            return new InfoButton(source);
        }

        @Override
        public InfoButton[] newArray(int size) {
            return new InfoButton[size];
        }
    };
}
