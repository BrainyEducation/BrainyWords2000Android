package com.hearatale.bw2000.data.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionData {
    private String category;
    private String answer;
    private List<QuestionModel> data = new ArrayList<>();

    public QuestionData(String category, String answer, List<QuestionModel> data) {
        this.category = category;
        this.answer = answer;
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<QuestionModel> getData() {
        return data;
    }

    public void setData(List<QuestionModel> data) {
        this.data = data;
    }
    public void addData(QuestionModel data) {
        this.data.add(data);
    }
}
