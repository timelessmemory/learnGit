package com.augmentum.onlineexamsystem.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.augmentum.onlineexamsystem.dao.UserDao;
import com.augmentum.onlineexamsystem.model.Role;
import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.util.StringUtils;

public class UserDaoImpl implements UserDao {
    private final String COL_ID = "id";
    private final String COL_USER_NAME = "user_name";
    private final String COL_PASSWORD = "password";
    private final String COL_CHINESE_NAME = "chinese_name";
    private final String COL_PIC = "pic";
    private final String COL_GENDER = "gender";
    private final String COL_TELEPHONE_NUMBER = "telephone_number";
    private final String COL_EMAIL_ADDRESS = "email_address";

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByName(final String userName) {

        if (StringUtils.isEmpty(userName)) {
            return null;
        }

        String sql = "SELECT id, user_name, password, chinese_name, pic, gender, telephone_number, email_address FROM user WHERE user_name = ?";

        Object [] args = new Object[] {userName};

        List<User> userList = jdbcTemplate.query(sql, args,  new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setId(rs.getInt(COL_ID));
                user.setUserName(rs.getString(COL_USER_NAME));
                user.setPassword(rs.getString(COL_PASSWORD));
                user.setChineseName(rs.getString(COL_CHINESE_NAME));
                user.setPic(rs.getString(COL_PIC));
                user.setGender(rs.getString(COL_GENDER));
                user.setTelephoneNumber(rs.getString(COL_TELEPHONE_NUMBER));
                user.setEmailAddress(rs.getString(COL_EMAIL_ADDRESS));
                return user;
            }
        });
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void updatePasswordById(int id, String password) {
        String sql = "UPDATE user SET password = ? WHERE id = ?";
        Object[] objects = new Object[] {password, id};
        jdbcTemplate.update(sql, objects);
     }

    @Override
    public void updatePic(int id, String path) {
        // TODO Auto-generated method stub

    }

    @Override
    public User getUser(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Role> findRoleNamesById(int id) {
        // TODO Auto-generated method stub
        return null;
    }
}
