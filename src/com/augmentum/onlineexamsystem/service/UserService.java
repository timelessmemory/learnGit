package com.augmentum.onlineexamsystem.service;

import com.augmentum.onlineexamsystem.dao.UserDao;
import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.exception.ServiceException;
import com.augmentum.onlineexamsystem.model.User;

public interface UserService {

    public  void setUserDao(UserDao userDao);

    public  User login(String userName, String password) throws ParameterException, ServiceException;
    public void changePassword(Integer id, String password) throws ParameterException;
    public void changePhoto(int id, String path);
    public User getUserInfo(int id);
}