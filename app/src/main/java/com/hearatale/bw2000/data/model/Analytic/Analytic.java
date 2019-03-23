package com.hearatale.bw2000.data.model.Analytic;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Analytic {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("student")
    @Expose
    private String student;
    @SerializedName("program")
    @Expose
    private String program;
    @SerializedName("focus_item")
    @Expose
    private String focusItem;
    @SerializedName("correct_on")
    @Expose
    private Long correctOn;
    @SerializedName("time_spent")
    @Expose
    private Long timeSpent;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Long v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getFocusItem() {
        return focusItem;
    }

    public void setFocusItem(String focusItem) {
        this.focusItem = focusItem;
    }

    public Long getCorrectOn() {
        return correctOn;
    }

    public void setCorrectOn(Long correctOn) {
        this.correctOn = correctOn;
    }

    public Long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

}