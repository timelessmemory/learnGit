package com.augmentum.onlineexamsystem.service;

import java.util.List;

import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.util.Pagination;

public interface ExamService {
    public void createExam(Exam exam) throws ParameterException;
    public void createExamAsDraft(Exam exam) throws ParameterException;
    public List<Exam> findExams(String firstOrder, String secondOrder, String thirdOrder, String firstColumn, String secondColumn, String thirdColumn, String nameCondition,
            String fromDate, String toDate, Pagination pagination);
    public boolean isQuestionEnough(int quantity);
    public Exam showExamDetailById(int id);
    public int editExam(Exam exam);
    public void deleteExamById(int id);
    public void deleteExamsById(int[] ids);
}
