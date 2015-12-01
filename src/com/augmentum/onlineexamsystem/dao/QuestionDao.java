package com.augmentum.onlineexamsystem.dao;

import java.util.List;

import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.util.Pagination;

public interface QuestionDao {

    public  void createQuestion(Question question);

    public  List<Question> findQuestions(String order, String condition, Pagination pagination);

    public  Question getQuestionByQuestionId(int id);

    public  void updateQuestionByQuestionId(Question question);

    public  void deleteQuestionByQuestionId(int id);

    public  void deleteQuestionsByQuestionId(int[] idInts);

    public  int getTotalCount(String condition);

    public  boolean isQuestionTitleExist(String title);

}