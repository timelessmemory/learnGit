package com.augmentum.onlineexamsystem.model;

import java.util.Date;

public class Exam {
    private Integer id;
    private String examName;
    private String description;
    private Date effectiveTime;
    private int duration;
    private int questionQuantity;
    private int questionPoints;
    private int totalScore;
    private int passCriteria;
    private String creator;
    private int isDraft;

    public Exam() {}

    public Exam(String examName, String description, Date effectiveTime, int duration,
            int questionQuantity, int questionPoints, int totalScore, int passCriteria, String creator) {
        this.examName = examName;
        this.description = description;
        this.effectiveTime = effectiveTime;
        this.duration = duration;
        this.questionQuantity = questionQuantity;
        this.questionPoints = questionPoints;
        this.totalScore = totalScore;
        this.passCriteria = passCriteria;
        this.creator = creator;
    }

    public int getIsDraft() {
        return isDraft;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getQuestionQuantity() {
        return questionQuantity;
    }

    public void setQuestionQuantity(int questionQuantity) {
        this.questionQuantity = questionQuantity;
    }

    public int getQuestionPoints() {
        return questionPoints;
    }

    public void setQuestionPoints(int questionPoints) {
        this.questionPoints = questionPoints;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getPassCriteria() {
        return passCriteria;
    }

    public void setPassCriteria(int passCriteria) {
        this.passCriteria = passCriteria;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Exam [id=");
        builder.append(id);
        builder.append(", examName=");
        builder.append(examName);
        builder.append(", description=");
        builder.append(description);
        builder.append(", effectiveTime=");
        builder.append(effectiveTime);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", questionQuantity=");
        builder.append(questionQuantity);
        builder.append(", questionPoints=");
        builder.append(questionPoints);
        builder.append(", totalScore=");
        builder.append(totalScore);
        builder.append(", passCriteria=");
        builder.append(passCriteria);
        builder.append(", creator=");
        builder.append(creator);
        builder.append("]");
        return builder.toString();
    }
}
