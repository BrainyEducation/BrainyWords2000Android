package com.hearatale.bw2000.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hearatale.bw2000.data.model.student.Student;

import java.util.List;

public class BaseResponseGetAllStudent {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("students")
    @Expose
    private List<Student> students = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

}