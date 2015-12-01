package com.augmentum.onlineexamsystem.dao;

import java.util.List;

import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.util.Pagination;

public interface ExamDao {

    public void createExam(Exam exam);
    public void createExamAsDraft(Exam exam);
    public  List<Exam> findExams(String firstOrder, String secondOrder, String thirdOrder, String firstColumn, String secondColumn, String thirdColumn, String nameCondition, String fromDate, String toDate, Pagination pagination);
    public int getTotalCountByCondition(String nameCondition, String fromDate, String toDate);
    public int getQuestionCount();
    public Exam getExamById(int id);
    public void updateExam(Exam exam);
    public  void deleteExamByExamId(int id);
    public  void deleteExamsByExamIds(int[] idInts);
    public void insertExamQuestion(int id, int quantity);
    public void updateExamAsNotDraft(int id);
    public void deleteExistExamQuestion(int id, int number);
    public void insertExamQuestionEdit(int id, int number);
}