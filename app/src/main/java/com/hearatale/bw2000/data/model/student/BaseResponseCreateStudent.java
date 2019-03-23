package com.hearatale.bw2000.data.model.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponseCreateStudent {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("student")
    @Expose
    private PayloadStudent student;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PayloadStudent getStudent() {
        return student;
    }

    public void setStudent(PayloadStudent student) {
        this.student = student;
    }
}