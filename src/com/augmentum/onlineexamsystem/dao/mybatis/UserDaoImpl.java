package com.augmentum.onlineexamsystem.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.augmentum.onlineexamsystem.dao.UserDao;
import com.augmentum.onlineexamsystem.model.Role;
import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.util.Constants;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao{
    private static final String CLASS_NAME = User.class.getName();
    private static final String SQL_ID_USER_GET_USER_BY_NAME = ".getUserByName";
    private static final String SQL_ID_USER_UPDATE_PASSWORD_BY_ID = ".updatePasswordById";
    private static final String SQL_ID_UPDATE_PIC = ".updatePic";
    private static final String SQL_ID_GET_USER = ".getUser";
    private static final String SQL_ID_FIND_ROLE_BY_NAME = ".findRoleNameById";

    @Override
    public User getUserByName(String userName) {
        return getSqlSession().selectOne(CLASS_NAME + SQL_ID_USER_GET_USER_BY_NAME, userName);
    }

    @Override
    public List<Role> findRoleNamesById(int id) {
        return getSqlSession().selectList(CLASS_NAME + SQL_ID_FIND_ROLE_BY_NAME, id);
    }

    @Override
    public void updatePasswordById(int id, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.ID, id);
        map.put(Constants.PASSWORD, password);
        getSqlSession().update(CLASS_NAME + SQL_ID_USER_UPDATE_PASSWORD_BY_ID, map);
    }

    @Override
    public void updatePic(int id, String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constants.ID, id);
        map.put(Constants.PATH, path);
        getSqlSession().update(CLASS_NAME + SQL_ID_UPDATE_PIC, map);
    }

    @Override
    public User getUser(int id) {
        User user = getSqlSession().selectOne(CLASS_NAME + SQL_ID_GET_USER, id);
        return user;
    }
}
