package com.augmentum.onlineexamsystem.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.augmentum.onlineexamsystem.dao.ExamDao;
import com.augmentum.onlineexamsystem.model.Exam;
import com.augmentum.onlineexamsystem.util.Pagination;

public class ExamDaoImpl implements ExamDao {
//    private final String COL_ID = "id";
//    private final String COL_EXAM_NAME = "exam_name";
//    private final String COL_EXAM_DESCRIPTION = "exam_description";
//    private final String COL_SINGLE_QUESTION_SCORE = "single_question_score";
//    private final String COL_QUESTION_QUANTITY = "question_quantity";
//    private final String COL_DURATION = "duration";
//    private final String COL_TOTAL_SCORE = "total_score";
//    private final String COL_EFFECTIVE_TIME = "effective_time";
//    private final String COL_CREATE_TIME = "create_time";
//    private final String COL_UPDATE_TIME = "update_time";
//    private final String COL_IS_FINISHED = "is_finished";
//    private final String COL_CREATOR = "creator";
//    private final String COL_PASS_CRITERIA = "pass_criteria";
//    private final String COL_IS_DRAFT = "is_draft";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createExam(final Exam exam) {

        if (exam == null) {
            return ;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                String sql = "INSERT INTO exam(exam_name, exam_description, single_question_score, question_quantity, duration, total_score, effective_time, create_time, update_time, creator, pass_criteria)"
                        + " VALUES(?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), ?, ?)";
                   PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                   ps.setString(1, exam.getExamName());
                   ps.setString(2, exam.getDescription());
                   ps.setInt(3, exam.getQuestionPoints());
                   ps.setInt(4, exam.getQuestionQuantity());
                   ps.setInt(5, exam.getDuration());
                   ps.setInt(6, exam.getTotalScore());
                   ps.setTimestamp(7, new Timestamp(exam.getEffectiveTime().getTime()));
                   ps.setString(8, exam.getCreator());
                   ps.setInt(9, exam.getPassCriteria());
                   return ps;
            }
        }, keyHolder);
        exam.setId(keyHolder.getKey().intValue());
    }

    @Override
    public int getTotalCountByCondition(String nameCondition, String fromDate, String toDate) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<Exam> findExams(String firstOrder, String secondOrder, String thirdOrder, String firstColumn,
            String secondColumn, String thirdColumn, String nameCondition, String fromDate, String toDate,
            Pagination pagination) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void createExamAsDraft(Exam exam) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getQuestionCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Exam getExamById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateExam(Exam exam) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteExamByExamId(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteExamsByExamIds(int[] idInts) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertExamQuestion(int id, int quantity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateExamAsNotDraft(int id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteExistExamQuestion(int id, int number) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertExamQuestionEdit(int id, int number) {
        // TODO Auto-generated method stub

    }

}
