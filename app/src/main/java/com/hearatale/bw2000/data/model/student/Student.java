package com.hearatale.bw2000.data.model.student;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student implements Parcelable {

    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Long v;
    public final static Parcelable.Creator<Student> CREATOR = new Creator<Student>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return (new Student[size]);
        }

    };

    protected Student(Parcel in) {
        this.deleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.studentId = ((String) in.readValue((String.class.getClassLoader())));
        this.teacher = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.v = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    public Student() {
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(deleted);
        dest.writeValue(id);
        dest.writeValue(studentId);
        dest.writeValue(teacher);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(v);
    }

    public int describeContents() {
        return 0;
    }

}
