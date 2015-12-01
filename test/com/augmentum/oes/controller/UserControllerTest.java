package com.augmentum.oes.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.augmentum.onlineexamsystem.common.AppContext;
import com.augmentum.onlineexamsystem.controller.UserController;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.SessionUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:oes-mvc.xml"})
public class UserControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void setUp() throws Exception {
        AppContext appContext = AppContext.getAppContext();
        appContext.addDataKeyValue(Constants.SESSION, new MockHttpSession());
        AppContext.setContextPath("/oes");
    }

    @After
    public void tearDown() throws Exception {
        AppContext appContext = AppContext.getAppContext();
        appContext.clearData();
    }

    @Test
    public void testLogin() {
        UserController userController = (UserController) this.applicationContext.getBean("userController");
        ModelAndView modelAndView = userController.loginPost("admin", "12s3", "on", "", "", new MockHttpServletResponse());
        RedirectView redirectView = (RedirectView) modelAndView.getView();
        Assert.assertEquals(redirectView.getUrl(), "/oes/login.action");
        Assert.assertNull(SessionUtil.getSessionAttr(Constants.USER));
    }
}
