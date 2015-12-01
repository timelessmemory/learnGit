package com.augmentum.oes.dao;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.augmentum.onlineexamsystem.dao.UserDao;
import com.augmentum.onlineexamsystem.model.Role;

public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        userDao = (UserDao) applicationContext.getBean("userDao");
    }

    @Test
    public void testShowRoleNames() {
        List<Role> roleList = userDao.findRoleNamesById(1);
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }

}
