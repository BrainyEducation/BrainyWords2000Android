package com.hearatale.bw2000.data.model.student;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponseStudentLogin {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("student")
    @Expose
    private Student student;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}

