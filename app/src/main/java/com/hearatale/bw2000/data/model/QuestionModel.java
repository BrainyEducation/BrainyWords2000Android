package com.hearatale.bw2000.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class QuestionModel implements Parcelable {

    private String question;
    private List<Item> data = new ArrayList<>();

    public QuestionModel() {
    }

    public QuestionModel(String question, List<Item> data) {
        this.question = question;
        this.data = data;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.question);
        dest.writeTypedList(this.data);
    }

    protected QuestionModel(Parcel in) {
        this.question = in.readString();
        this.data = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<QuestionModel> CREATOR = new Creator<QuestionModel>() {
        @Override
        public QuestionModel createFromParcel(Parcel source) {
            return new QuestionModel(source);
        }

        @Override
        public QuestionModel[] newArray(int size) {
            return new QuestionModel[size];
        }
    };
}
