package com.hearatale.bw2000.data.model.Analytic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayLoadAnalytic {

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

}
