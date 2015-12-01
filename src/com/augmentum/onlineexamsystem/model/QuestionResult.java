package com.augmentum.onlineexamsystem.model;

import java.util.ArrayList;
import java.util.List;

import com.augmentum.onlineexamsystem.util.Pagination;

public class QuestionResult {
     private List<Question> questionList = new ArrayList<Question>();
     private String orderflag;
     private Pagination pagination;
     private String questionCondition;
     private boolean isQuestionSearch;

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getOrderflag() {
        return orderflag;
    }

    public void setOrderflag(String orderflag) {
        this.orderflag = orderflag;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getQuestionCondition() {
        return questionCondition;
    }

    public void setQuestionCondition(String questionCondition) {
        this.questionCondition = questionCondition;
    }

    public boolean getIsQuestionSearch() {
        return isQuestionSearch;
    }

    public void setQuestionSearch(boolean isQuestionSearch) {
        this.isQuestionSearch = isQuestionSearch;
    }
}
