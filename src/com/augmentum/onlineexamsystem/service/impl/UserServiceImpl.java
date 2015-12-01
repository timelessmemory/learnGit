package com.augmentum.onlineexamsystem.service.impl;

import javax.annotation.Resource;

import com.augmentum.onlineexamsystem.dao.UserDao;
import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.exception.ServiceException;
import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.service.UserService;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.StringUtils;

public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String userName, String password) throws ParameterException,ServiceException {

        ParameterException peException = new ParameterException();

        if (StringUtils.isEmpty(userName)) {
            peException.addErrors(Constants.USER_NAME, Constants.USER_NAME_MESSAGE);
        }

        if (StringUtils.isEmpty(password)) {
            peException.addErrors(Constants.PASSWORD, Constants.PASSWORD_MESSAGE);
        }

        if (peException.isError()) {
            throw peException;
        }

        User user = userDao.getUserByName(userName);

        if (user == null) {
            throw new ServiceException(1, Constants.NO_USER);
        }

        if (!user.getPassword().equals(password)) {
            throw new ServiceException(2, Constants.ERROR_PASSWORD);
        } else {
            user.setRoles(userDao.findRoleNamesById(user.getId()));
        }

        return user;
    }

    @Override
    public void changePassword(Integer id, String password) throws ParameterException {
        ParameterException peException = new ParameterException();

        if (StringUtils.isEmpty(password)) {
            peException.addErrors(Constants.PASSWORD, Constants.PASSWORD_MESSAGE);
        }

        if (id == null) {
            peException.addErrors(Constants.ERROR_NAME, Constants.ERROR_MESSAGE);
        }

        if (peException.isError()) {
            throw peException;
        }

        userDao.updatePasswordById(id, password);
    }

    @Override
    public void changePhoto(int id, String path) {
        userDao.updatePic(id, path);
    }

    @Override
    public User getUserInfo(int id) {
        return userDao.getUser(id);
    }
}
