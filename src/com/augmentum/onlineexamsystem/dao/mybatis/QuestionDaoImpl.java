package com.augmentum.onlineexamsystem.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.augmentum.onlineexamsystem.dao.QuestionDao;
import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.Pagination;

public class QuestionDaoImpl extends SqlSessionDaoSupport implements QuestionDao {

    private static final String CLASS_NAME = Question.class.getName();
    private static final String SQL_ID_QUESTION_CREATE_QUESTION = ".createQuestion";
    private static final String SQL_ID_QUESTION_FIND_QUESTIONS = ".findQuestions";
    private static final String SQL_ID_QUESTION_GET_QUESTION_BY_ID = ".getQuestionByQuestionId";
    private static final String SQL_ID_QUESTION_UPDATE_QUESTION_BY_ID = ".updateQuestionByQuestionId";
    private static final String SQL_ID_QUESTION_DELETE_QUESTION_BY_ID = ".deleteQuestionByQuestionId";
    private static final String SQL_ID_QUESTION_DELETE_QUESTIONS_BY_ID = ".deleteQuestionsByQuestionId";
    private static final String SQL_ID_QUESTION_GET_TOTAL_COUNT = ".getTotalCount";
    private static final String SQL_ID_QUESTION_IS_QUESTION_TITLE_EXIST = ".isQuestionTitleExist";

    @Override
    public void createQuestion(Question question) {
        if (question == null) {
            return ;
        }
        getSqlSession().insert(CLASS_NAME + SQL_ID_QUESTION_CREATE_QUESTION, question);
    }

    @Override
    public List<Question> findQuestions(String order, String condition, Pagination pagination) {
        int totalCount =  this.getTotalCount(condition);
        pagination.setTotalCount(totalCount);
        if (pagination.getCurrentPage() > pagination.getTotalPage()) {
            pagination.setCurrentPage(pagination.getTotalPage());
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.CONDITION, condition);
        queryMap.put(Constants.ORDER, order);
        queryMap.put(Constants.OFFSET, pagination.getOffSet());
        queryMap.put(Constants.PAGESIZE, pagination.getPageSize());
        List<Question> list = getSqlSession().selectList(CLASS_NAME + SQL_ID_QUESTION_FIND_QUESTIONS, queryMap);
        return list;
    }

    @Override
    public Question getQuestionByQuestionId(int id) {
        Question question = getSqlSession().selectOne(CLASS_NAME + SQL_ID_QUESTION_GET_QUESTION_BY_ID, id);
        return question;
    }

    @Override
    public void updateQuestionByQuestionId(Question question) {
        getSqlSession().update(CLASS_NAME + SQL_ID_QUESTION_UPDATE_QUESTION_BY_ID, question);
    }

    @Override
    public void deleteQuestionByQuestionId(int id) {
        getSqlSession().delete(CLASS_NAME + SQL_ID_QUESTION_DELETE_QUESTION_BY_ID, id);
    }

    @Override
    public void deleteQuestionsByQuestionId(int[] idInts) {
        getSqlSession().update(CLASS_NAME + SQL_ID_QUESTION_DELETE_QUESTIONS_BY_ID, idInts);
    }

    @Override
    public int getTotalCount(String condition) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.CONDITION, condition);
        int count = getSqlSession().selectOne(CLASS_NAME + SQL_ID_QUESTION_GET_TOTAL_COUNT, map);
        return count;
    }

    @Override
    public boolean isQuestionTitleExist(String title) {
        List<Question> ls =  getSqlSession().selectList(CLASS_NAME + SQL_ID_QUESTION_IS_QUESTION_TITLE_EXIST, title);
        if (ls != null && ls.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

}
