package com.hearatale.bw2000.data.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class AnswerModel implements Parcelable{
    private String imageFileName;
    private String pathImage;
    private String app_id;
    private boolean isAnswer = false;

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageFileName);
        dest.writeString(this.pathImage);
        dest.writeString(this.app_id);
        dest.writeByte(this.isAnswer ? (byte) 1 : (byte) 0);
    }

    public AnswerModel() {
    }
    public AnswerModel(String nameImage, String pathImage, boolean isAnswer , String app_id) {
        this.imageFileName = nameImage;
        this.pathImage = pathImage;
        this.isAnswer = isAnswer;
        this.app_id = app_id;
    }

    public AnswerModel(String nameImage, String pathImage, boolean isAnswer) {
        this.imageFileName = nameImage;
        this.pathImage = pathImage;
        this.isAnswer = isAnswer;
    }

    protected AnswerModel(Parcel in) {
        this.imageFileName = in.readString();
        this.pathImage = in.readString();
        this.app_id = in.readString();
        this.isAnswer = in.readByte() != 0;
    }

    public static final Creator<AnswerModel> CREATOR = new Creator<AnswerModel>() {
        @Override
        public AnswerModel createFromParcel(Parcel source) {
            return new AnswerModel(source);
        }

        @Override
        public AnswerModel[] newArray(int size) {
            return new AnswerModel[size];
        }
    };
}
