package com.augmentum.onlineexamsystem.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.augmentum.onlineexamsystem.dao.ExamDao;
import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.Pagination;

public class ExamDaoImpl extends SqlSessionDaoSupport implements ExamDao {
    private static final String CLASS_NAME = Exam.class.getName();
    private static final String SQL_ID_EXAM_CREATE_EXAM = ".createExam";
    private static final String SQL_ID_EXAM_CREATE_EXAM_INSERT_QUESTION = ".createExamInsertQuestion";
    private static final String SQL_ID_AS_DRAFT = ".asDraft";
    private static final String SQL_ID_AS_NOT_DRAFT = ".asNotDraft";
    private static final String SQL_ID_FIND_EXAMS = ".findExams";
    private static final String SQL_ID_GET_TOTAL_COUNT_BY_CONDITION = ".getTotalCountByCondition";
    private static final String SQL_ID_GET_QUESTION_COUNT = ".getQuestionCount";
    private static final String SQL_ID_GET_EXAM_BY_ID = ".getExamById";
    private static final String SQL_ID_UPDATE_EXAM_BY_ID = ".updateExam";
    private static final String SQL_ID_INSERT_EXAM_QUESTION_EDIT = ".insertExamQuestionEdit";
    private static final String SQL_ID_DELETE_EXAM_BY_EXAMID = ".deleteExamByExamId";
    private static final String SQL_ID_DELETE_EXIST_EXAM_QUESTION = ".deleteExistExamQuestion";
    //private static final String SQL_ID_DELETE_EXAM_AND_QUES_BY_EXAMID = ".deleteExamAndQuesByExamId";
    private static final String SQL_ID_DELETE_EXAMS_BY_EXAMIDS = ".deleteExamsByExamIds";
    //private static final String SQL_ID_DELETE_EXAMS_AND_QUES_BY_EXAMIDS = ".deleteExamsAndQuesByExamIds";
    private static final String ID = "id";

    @Override
    public void createExam(Exam exam) {
        SqlSession session = getSqlSession();
        session.insert(CLASS_NAME + SQL_ID_EXAM_CREATE_EXAM, exam);
        insertExamQuestion(exam.getId(), exam.getQuestionQuantity());
    }

    @Override
    public void insertExamQuestion(int id, int quantity) {
        SqlSession session = getSqlSession();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ID, id);
        map.put(Constants.QUANTITY, quantity);
        session.insert(CLASS_NAME + SQL_ID_EXAM_CREATE_EXAM_INSERT_QUESTION, map);
    }

    @Override
    public void createExamAsDraft(Exam exam) {
        SqlSession session = getSqlSession();
        session.insert(CLASS_NAME + SQL_ID_EXAM_CREATE_EXAM, exam);
        session.update(CLASS_NAME + SQL_ID_AS_DRAFT, exam.getId());
    }

    @Override
    public void updateExamAsNotDraft(int id) {
        SqlSession session = getSqlSession();
        session.update(CLASS_NAME + SQL_ID_AS_NOT_DRAFT, id);
    }

    @Override
    public List<Exam> findExams(String firstOrder, String secondOrder, String thirdOrder, String firstColumn, String secondColumn, String thirdColumn, String nameCondition,
            String fromDate, String toDate, Pagination pagination) {

        int totalCount =  this.getTotalCountByCondition(nameCondition, fromDate, toDate);
        pagination.setTotalCount(totalCount);
        if (pagination.getCurrentPage() > pagination.getTotalPage()) {
            pagination.setCurrentPage(pagination.getTotalPage());
        }
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put(Constants.NAME_CONDITION, nameCondition);
        queryMap.put(Constants.FROM_DATE, fromDate);
        queryMap.put(Constants.TO_DATE, toDate);
        queryMap.put(Constants.FIRST_ORDER, firstOrder);
        queryMap.put(Constants.SECOND_ORDER, secondOrder);
        queryMap.put(Constants.THIRD_ORDER, thirdOrder);
        queryMap.put(Constants.FIRST_COL, firstColumn);
        queryMap.put(Constants.SECOND_COL, secondColumn);
        queryMap.put(Constants.THIRD_COL, thirdColumn);
        queryMap.put(Constants.OFFSET, pagination.getOffSet());
        queryMap.put(Constants.PAGESIZE, pagination.getPageSize());
        List<Exam> list = getSqlSession().selectList(CLASS_NAME + SQL_ID_FIND_EXAMS, queryMap);
        return list;
    }

    @Override
    public int getTotalCountByCondition(String nameCondition, String fromDate, String toDate) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.NAME_CONDITION, nameCondition);
        map.put(Constants.FROM_DATE, fromDate);
        map.put(Constants.TO_DATE, toDate);
        int count = getSqlSession().selectOne(CLASS_NAME + SQL_ID_GET_TOTAL_COUNT_BY_CONDITION, map);
        return count;
    }

    @Override
    public int getQuestionCount() {
        int count = getSqlSession().selectOne(CLASS_NAME + SQL_ID_GET_QUESTION_COUNT);
        return count;
    }

    @Override
    public Exam getExamById(int id) {
        Exam exam = getSqlSession().selectOne(CLASS_NAME + SQL_ID_GET_EXAM_BY_ID, id);
        return exam;
    }

    @Override
    public void updateExam(Exam exam) {
        getSqlSession().update(CLASS_NAME + SQL_ID_UPDATE_EXAM_BY_ID, exam);
    }

    @Override
    public void deleteExamByExamId(int id) {
        //getSqlSession().delete(CLASS_NAME + SQL_ID_DELETE_EXAM_AND_QUES_BY_EXAMID, id);
        getSqlSession().update(CLASS_NAME + SQL_ID_DELETE_EXAM_BY_EXAMID, id);
    }

    @Override
    public void deleteExamsByExamIds(int[] idInts) {
        //getSqlSession().delete(CLASS_NAME + SQL_ID_DELETE_EXAMS_AND_QUES_BY_EXAMIDS, idInts);
        getSqlSession().update(CLASS_NAME + SQL_ID_DELETE_EXAMS_BY_EXAMIDS, idInts);
    }

    @Override
    public void deleteExistExamQuestion(int id, int number) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.ID, id);
        map.put(Constants.NUMBER, number);
        getSqlSession().delete(CLASS_NAME + SQL_ID_DELETE_EXIST_EXAM_QUESTION, map);
    }

    @Override
    public void insertExamQuestionEdit(int id, int number) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.ID, id);
        map.put(Constants.NUMBER, number);
        getSqlSession().delete(CLASS_NAME + SQL_ID_INSERT_EXAM_QUESTION_EDIT, map);
    }
}
