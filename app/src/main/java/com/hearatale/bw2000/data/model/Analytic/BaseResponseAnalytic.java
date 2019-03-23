package com.hearatale.bw2000.data.model.Analytic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponseAnalytic {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("analytic")
    @Expose
    private Analytic analytic;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Analytic getAnalytic() {
        return analytic;
    }

    public void setAnalytic(Analytic analytic) {
        this.analytic = analytic;
    }

}
