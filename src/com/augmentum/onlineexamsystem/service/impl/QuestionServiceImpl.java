package com.augmentum.onlineexamsystem.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.augmentum.onlineexamsystem.dao.QuestionDao;
import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.service.QuestionService;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.Pagination;
import com.augmentum.onlineexamsystem.util.StringUtils;

public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionDao questionDao;

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public int createQuestion(Question question) throws ParameterException {

        ParameterException peException = new ParameterException();

        if (StringUtils.isEmpty(question.getTitle()) || question.getTitle().length() > Constants.TITLE_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_TITLE, Constants.QUESTION_TITLE_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerA()) || question.getAnswerA().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_A, Constants.QUESTION_ANSWER_A_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerB()) || question.getAnswerB().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_B, Constants.QUESTION_ANSWER_B_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerC()) || question.getAnswerC().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_C, Constants.QUESTION_ANSWER_C_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerD()) || question.getAnswerD().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_D, Constants.QUESTION_ANSWER_D_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getCorrectAnswer())) {
            peException.addErrors(Constants.CORRECT_ANSWER, Constants.CORRECT_ANSWER_MESSAGE);
        }

        if (peException.isError()) {
            throw peException;
        }

        questionDao.createQuestion(question);
        return question.getId();
    }

    @Override
    public List<Question> findQuestions(String order, String condition, Pagination pagination) {
        return questionDao.findQuestions(order, condition, pagination);
    }

    @Override
    public Question getQuestionByQuestionId(int id) {
        return questionDao.getQuestionByQuestionId(id);
    }

    @Override
    public void updateQuestionByQuestionId(Question question) throws ParameterException {

        ParameterException peException = new ParameterException();

        if (StringUtils.isEmpty(question.getTitle()) || question.getTitle().length() > Constants.TITLE_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_TITLE, Constants.QUESTION_TITLE_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerA()) || question.getAnswerA().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_A, Constants.QUESTION_ANSWER_A_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerB()) || question.getAnswerB().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_B, Constants.QUESTION_ANSWER_B_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerC()) || question.getAnswerC().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_C, Constants.QUESTION_ANSWER_C_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getAnswerD()) || question.getAnswerD().length() > Constants.QUESTION_LENGTH_LIMIT) {
            peException.addErrors(Constants.QUESTION_ANSWER_D, Constants.QUESTION_ANSWER_D_MESSAGE);
        }

        if (StringUtils.isEmpty(question.getCorrectAnswer())) {
            peException.addErrors(Constants.CORRECT_ANSWER, Constants.CORRECT_ANSWER_MESSAGE);
        }

        if (peException.isError()) {
            throw peException;
        }

        questionDao.updateQuestionByQuestionId(question);
    }

    @Override
    public void deleteQuestionByQuestionId(int id) {
        questionDao.deleteQuestionByQuestionId(id);
    }

    @Override
    public void deleteQuestionsByQuestionId(int[] idInts) {
        questionDao.deleteQuestionsByQuestionId(idInts);
    }

    @Override
    public boolean questionTitleValidation(String title) {
        return questionDao.isQuestionTitleExist(title);
    }
}
