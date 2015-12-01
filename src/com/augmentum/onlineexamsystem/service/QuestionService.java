package com.augmentum.onlineexamsystem.service;

import java.util.List;

import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.util.Pagination;

public interface QuestionService {

    public  int createQuestion(Question question) throws ParameterException;

    public  List<Question> findQuestions(String order, String condition, Pagination pagination);

    public  Question getQuestionByQuestionId(int id);

    public  void updateQuestionByQuestionId(Question question) throws ParameterException;

    public  void deleteQuestionByQuestionId(int id);

    public  void deleteQuestionsByQuestionId(int[] idInts);

    public  boolean questionTitleValidation(String title);

}