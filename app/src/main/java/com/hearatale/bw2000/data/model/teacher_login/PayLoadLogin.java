package com.hearatale.bw2000.data.model.teacher_login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PayLoadLogin {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
