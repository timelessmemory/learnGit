package com.augmentum.onlineexamsystem.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.augmentum.onlineexamsystem.dao.QuestionDao;
import com.augmentum.onlineexamsystem.model.Question;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.Pagination;
import com.augmentum.onlineexamsystem.util.StringUtils;

public class QuestionDaoImpl implements QuestionDao {

    private final String COL_ID = "id";
    private final String COL_TITLE = "title";
    private final String COL_ANSWER_A = "answer_a";
    private final String COL_ANSWER_B = "answer_b";
    private final String COL_ANSWER_C = "answer_c";
    private final String COL_ANSWER_D = "answer_d";
    private final String COL_CORRECT_ANSWER = "correct_answer";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createQuestion(final Question question) {

        if (question == null) {
            return ;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                String sql = "INSERT INTO question(title, answer_a, answer_b, answer_c, answer_d, correct_answer, create_time, update_time)"
                        + " VALUES(?, ?, ?, ?, ?, ?, NOW(), NOW())";
                   PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                   ps.setString(1, question.getTitle());
                   ps.setString(2, question.getAnswerA());
                   ps.setString(3, question.getAnswerB());
                   ps.setString(4, question.getAnswerC());
                   ps.setString(5, question.getAnswerD());
                   ps.setString(6, question.getCorrectAnswer());
                   return ps;
            }
        }, keyHolder);
        question.setId(keyHolder.getKey().intValue());
    }

    @Override
    public List<Question> findQuestions(String order, final String condition, final Pagination pagination) {
        String sql = "";
        int totalCount =  this.getTotalCount(condition);
        pagination.setTotalCount(totalCount);
        if (pagination.getCurrentPage() > pagination.getTotalPage()) {
            pagination.setCurrentPage(pagination.getTotalPage());
        }

        if (StringUtils.isEmpty(condition)) {
            if (Constants.ORDER_ASC.equals(order)) {
                sql = "SELECT id, title FROM question WHERE is_delete = 0 ORDER BY id ASC LIMIT ?, ?";
            } else {
                sql = "SELECT id, title FROM question WHERE is_delete = 0 ORDER BY id DESC LIMIT ?, ?";
            }

            Object [] args = new Object[] {pagination.getOffSet(), pagination.getPageSize()};

            return jdbcTemplate.query(sql, args, new RowMapper<Question>() {
                @Override
                public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Question question = new Question();
                    question.setId(rs.getInt(COL_ID));
                    question.setTitle(rs.getString(COL_TITLE));
                    return question;
                }
            });

        } else {
            if (Constants.ORDER_ASC.equals(order)) {
                sql = "SELECT id, title FROM question WHERE title  LIKE ? AND is_delete = 0 ORDER BY id ASC LIMIT ?, ?";
            } else {
                sql = "SELECT id, title FROM question  WHERE title LIKE ? AND is_delete = 0 ORDER BY id DESC LIMIT ?, ?";
            }

            Object [] args = new Object[] {"%" + condition + "%", pagination.getOffSet(), pagination.getPageSize()};

            return jdbcTemplate.query(sql, args, new RowMapper<Question>() {
                @Override
                public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Question question = new Question();
                    question.setId(rs.getInt(COL_ID));
                    question.setTitle(rs.getString(COL_TITLE));
                    return question;
                }
            });
        }
    }

    @Override
    public Question getQuestionByQuestionId(final int id) {

        String sql = "SELECT id, title, answer_a, answer_b, answer_c, answer_d, correct_answer FROM question WHERE id = ? AND is_delete = 0";

        Object [] args = new Object[] {id};

        List<Question> questionLs = jdbcTemplate.query(sql, args,  new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                Question question = new Question();
                question.setId(rs.getInt(COL_ID));
                question.setTitle(rs.getString(COL_TITLE));
                question.setAnswerA(rs.getString(COL_ANSWER_A));
                question.setAnswerB(rs.getString(COL_ANSWER_B));
                question.setAnswerC(rs.getString(COL_ANSWER_C));
                question.setAnswerD(rs.getString(COL_ANSWER_D));
                question.setCorrectAnswer(rs.getString(COL_CORRECT_ANSWER));
                return question;
            }
        });
        if (questionLs != null && questionLs.size() > 0) {
            return questionLs.get(0);
        }
        return null;
    }

    @Override
    public void updateQuestionByQuestionId(final Question question) {
            String sql = "UPDATE question SET title = ? , answer_a = ?, answer_b = ?, answer_c = ?, answer_d = ?, correct_answer = ? WHERE id = ? AND is_delete = 0";

            jdbcTemplate.update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, question.getTitle());
                    ps.setString(2, question.getAnswerA());
                    ps.setString(3, question.getAnswerB());
                    ps.setString(4, question.getAnswerC());
                    ps.setString(5, question.getAnswerD());
                    ps.setString(6, question.getCorrectAnswer());
                    ps.setInt(7, question.getId());
                }
            });
    }

    @Override
    public void deleteQuestionByQuestionId(final int id) {
        String sql = "UPDATE question SET is_delete = 1 WHERE id = ?";

        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });
    }

    @Override
    public void deleteQuestionsByQuestionId(final int[] idInts) {
            StringBuffer buffer = getDeleteChar(idInts);
            String sql = "UPDATE question SET is_delete = 1 WHERE id IN ("
                    + buffer.toString() + ")";

            jdbcTemplate.update(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    setDeleteParameter(idInts, ps);
                }
            });
    }

    private StringBuffer getDeleteChar(int[] idInts) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < idInts.length; i++) {
            buffer.append("?,");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return buffer;
    }

    private void setDeleteParameter(final int[] idInts, PreparedStatement ps) throws SQLException {
        for (int i = 0; i < idInts.length; i++) {
            ps.setInt(i + 1, idInts[i]);
        }
    }

    @Override
    public int getTotalCount(final String condition) {
        String sql = "";
        if (StringUtils.isEmpty(condition)) {
            sql = "SELECT COUNT(id) FROM question WHERE is_delete = 0";
            return jdbcTemplate.queryForInt(sql);
        } else {
            sql = "SELECT COUNT(id) FROM question WHERE title LIKE ? AND is_delete = 0";
            Object [] args = new Object[] {"%" + condition + "%"};
            return jdbcTemplate.queryForInt(sql, args);
        }
    }

    @Override
    public boolean isQuestionTitleExist(final String title) {
        String  sql = "SELECT id FROM question WHERE title = ? AND is_delete = 0";

        List<Question> ls = jdbcTemplate.query(sql, new Object[]{title}, new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                Question question = new Question();
                question.setId(rs.getInt(COL_ID));
                return question;
            }
        });
        if (ls != null && ls.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

}
