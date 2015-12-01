package com.augmentum.onlineexamsystem.service.impl;

import java.util.List;

import com.augmentum.onlineexamsystem.dao.ExamDao;
import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.service.ExamService;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.Pagination;
import com.augmentum.onlineexamsystem.util.StringUtils;

public class ExamServiceImpl implements ExamService {
    private ExamDao examDao;
    private static final int NOT_DRAFT = 0;
    private static final int IS_DRAFT = 1;
    private static final int REFUSE_DRAFT = -1;
    private static final int ALLOW = 0;

    public void setExamDao(ExamDao examDao) {
        this.examDao = examDao;
    }

    @Override
    public void createExam(Exam exam) throws ParameterException {
        ParameterException peException = new ParameterException();

        if (StringUtils.isEmpty(exam.getExamName()) || exam.getExamName().length() > Constants.EXAM_NAME_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_TITLE, Constants.QUESTION_TITLE_MESSAGE);
        }

        if (StringUtils.isEmpty(exam.getDescription()) ||exam.getDescription().length() > Constants.EXAM_DES_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_A, Constants.QUESTION_ANSWER_A_MESSAGE);
        }

        if (peException.isError()) {
            throw peException;
        }
        examDao.createExam(exam);
    }

    @Override
    public void createExamAsDraft(Exam exam) throws ParameterException {
        ParameterException peException = new ParameterException();

        if (StringUtils.isEmpty(exam.getExamName()) || exam.getExamName().length() > Constants.EXAM_NAME_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_TITLE, Constants.QUESTION_TITLE_MESSAGE);
        }

        if (StringUtils.isEmpty(exam.getDescription()) ||exam.getDescription().length() > Constants.EXAM_DES_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_A, Constants.QUESTION_ANSWER_A_MESSAGE);
        }

        if (peException.isError()) {
            throw peException;
        }
        examDao.createExamAsDraft(exam);
    }

    @Override
    public List<Exam> findExams(String firstOrder, String secondOrder, String thirdOrder, String firstColumn,
            String secondColumn, String thirdColumn, String nameCondition, String fromDate, String toDate,
            Pagination pagination) {
        return examDao.findExams(firstOrder, secondOrder, thirdOrder, firstColumn, secondColumn, thirdColumn, nameCondition, fromDate, toDate, pagination);
    }

    @Override
    public boolean isQuestionEnough(int quantity) {
        int count = examDao.getQuestionCount();
        if (quantity > count) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Exam showExamDetailById(int id) {
        return examDao.getExamById(id);
    }

    @Override
    public int editExam(Exam exam) {
        int i = ALLOW;
        Exam originExam = examDao.getExamById(exam.getId());
        int originalQuantity = originExam.getQuestionQuantity();
        int currentQuantity = exam.getQuestionQuantity();
        int originIsDraft = originExam.getIsDraft();
        int questionCount = examDao.getQuestionCount();
        if (currentQuantity == originalQuantity) {
            examDao.updateExam(exam);
        } else if (currentQuantity < originalQuantity) {
            //not draft
            if (originIsDraft == NOT_DRAFT) {
                examDao.updateExam(exam);
                examDao.deleteExistExamQuestion(exam.getId(), originalQuantity - currentQuantity);
            } else if (currentQuantity <= questionCount && originIsDraft == IS_DRAFT) {
                examDao.updateExam(exam);
                examDao.insertExamQuestion(exam.getId(), currentQuantity);
                examDao.updateExamAsNotDraft(exam.getId());
            } else if (currentQuantity > questionCount && originIsDraft == IS_DRAFT) {
                examDao.updateExam(exam);
            }
        } else if(currentQuantity > originalQuantity) {
            if(currentQuantity <= questionCount && originIsDraft == NOT_DRAFT) {
                examDao.updateExam(exam);
                examDao.insertExamQuestionEdit(exam.getId(), currentQuantity - originalQuantity);
            } else if (currentQuantity > questionCount && originIsDraft == NOT_DRAFT) {
                i = REFUSE_DRAFT;
            } else if (currentQuantity <= questionCount && originIsDraft == IS_DRAFT) {
                examDao.updateExam(exam);
                examDao.insertExamQuestion(exam.getId(), currentQuantity);
                examDao.updateExamAsNotDraft(exam.getId());
            } else if (currentQuantity > questionCount && originIsDraft == IS_DRAFT) {
                examDao.updateExam(exam);
            }
        }
        return i;
    }

    @Override
    public void deleteExamById(int id) {
        examDao.deleteExamByExamId(id);
    }

    @Override
    public void deleteExamsById(int[] ids) {
        examDao.deleteExamsByExamIds(ids);
    }

}
