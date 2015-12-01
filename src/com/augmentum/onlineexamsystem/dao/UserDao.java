package com.augmentum.onlineexamsystem.dao;

import java.util.List;

import com.augmentum.onlineexamsystem.model.Role;
import com.augmentum.onlineexamsystem.model.User;

public interface UserDao {

    public User getUserByName(String userName);

    public void updatePasswordById(int id, String password);

    public void updatePic(int id, String path);

    public User getUser(int id);

    public List<Role> findRoleNamesById(int id);
}