package com.hearatale.bw2000.data.model.student;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayloadStudent {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("delete")
    @Expose
    private String delete;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

}
