package com.augmentum.onlineexamsystem.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.augmentum.onlineexamsystem.common.AppContext;
import com.augmentum.onlineexamsystem.exception.ParameterException;
import com.augmentum.onlineexamsystem.exception.ServiceException;
import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.service.UserService;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.FileUploadUtils;
import com.augmentum.onlineexamsystem.util.MD5;
import com.augmentum.onlineexamsystem.util.SessionUtil;
import com.augmentum.onlineexamsystem.util.StringUtils;
@Controller
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    private static final String LOGIN = "login";
    private static final String USER_INFO = "basic-information";
    private static final String USER_CHANGE_PASSWORD = "password-change";
    private static final String IMAGE_FILE = "imgFile";
    private static final int MAX_AGE = 30000;
    private static final int MIN_AGE = 0;
    private static final String DOUBT = "?";
    private Logger logger = Logger.getLogger(BaseController.class);

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = Constants.LOGIN_SERVLET, method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = Constants.REQUEST_GO, defaultValue = Constants.EMPTY_STR) String go) {
        String userName = (String)AppContext.getAppContext().getDataValue(Constants.USER);
        ModelAndView modelAndView = new ModelAndView();
        if (!StringUtils.isEmpty(userName)) {
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SLASH + Constants.SHOW_QUESTION_SERVLET));
        } else {
        modelAndView.addObject(Constants.REQUEST_GO, go);
        modelAndView.setViewName(LOGIN);
        }
        return modelAndView;
    }

    @RequestMapping(value = Constants.LOGIN_SERVLET, method = RequestMethod.POST)
    public ModelAndView loginPost(@RequestParam(value = Constants.USER_NAME, defaultValue = Constants.EMPTY_STR) String userName,
                                  @RequestParam(value = Constants.PASSWORD, defaultValue = Constants.EMPTY_STR) String password,
                                  @RequestParam(value = Constants.IS_REMEMBER, defaultValue = Constants.EMPTY_STR) String isRemember,
                                  @RequestParam(value = Constants.REQUEST_GO, defaultValue = Constants.EMPTY_STR) String go,
                                  @RequestParam(value = Constants.PARAM, defaultValue = Constants.EMPTY_STR) String param,
                                  HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String encryptPassword = "";
        if (!StringUtils.isEmpty(password)) {
            encryptPassword = MD5.GetMD5Code(password);
        }
        User user = null;
        try {
            user = userService.login(userName, encryptPassword);
            if (!StringUtils.isEmpty(isRemember)) {
                Cookie cookieName = new Cookie(Constants.USER_NAME, user.getUserName());
                cookieName.setMaxAge(MAX_AGE);
                Cookie cookiePassword = new Cookie(Constants.PASSWORD, password);
                cookiePassword.setMaxAge(MAX_AGE);
                response.addCookie(cookieName);
                response.addCookie(cookiePassword);
            } else {
                Cookie cookieName = new Cookie(Constants.USER_NAME, null);
                cookieName.setMaxAge(MIN_AGE);
                Cookie cookiePassword = new Cookie(Constants.PASSWORD, null);
                cookiePassword.setMaxAge(MIN_AGE);
                response.addCookie(cookieName);
                response.addCookie(cookiePassword);
            }

            user.setPassword(null);
            SessionUtil.addSessionAttr(Constants.USER, user);
            if (StringUtils.isEmpty(go)) {
                modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.LOGIN_SUC_SERVLET));
            } else {
                if (!StringUtils.isEmpty(param)) {
                    param = param.substring(1);
                    go = go + DOUBT + param;
                }
                modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SLASH + go));
            }

        } catch (ParameterException e) {
            logger.error(e.getMessage(), e);
            SessionUtil.addSessionAttr(Constants.ERRORS, e.getErrors());
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.LOGIN_SERVLET));
        } catch (ServiceException se) {
            logger.error(se.getMessage(), se);
            SessionUtil.addSessionAttr(Constants.TIP_MESSAGE, se.getMessage());
            SessionUtil.addSessionAttr(Constants.USER_NAME, userName);
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.LOGIN_SERVLET));
            return modelAndView;
        }
        return modelAndView;
    }

    @RequestMapping(value = Constants.LOGIN_SUC_SERVLET, method = RequestMethod.GET)
    public ModelAndView loginSuccess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_QUESTION_SERVLET));
        return modelAndView;
    }

    @RequestMapping(value = Constants.LOGOUT_SERVLET, method = RequestMethod.GET)
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        SessionUtil.removeSessionAttr(Constants.USER);
        modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.LOGIN_SERVLET));
        return modelAndView;
    }

    @RequestMapping(value = Constants.SHOW_USER_INFO_SERVLET, method = RequestMethod.GET)
    public ModelAndView showUserInfo() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(USER_INFO);
        return modelAndView;
      }

    @RequestMapping(value = Constants.SHOW_USER_CHANGE_PASSWORD, method = RequestMethod.GET)
    public ModelAndView changePassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(USER_CHANGE_PASSWORD);
        return modelAndView;
    }

    @RequestMapping(value = Constants.SHOW_USER_CHANGE_PASSWORD, method = RequestMethod.POST)
    public ModelAndView changePasswordPost(@RequestParam(value=Constants.PASSWORD, defaultValue = Constants.EMPTY_STR) String password,
                                           @RequestParam(value=Constants.USER_ID, defaultValue = Constants.EMPTY_STR) String userId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            password = MD5.GetMD5Code(password);
            userService.changePassword(Integer.valueOf(userId), password);
        } catch (ParameterException e) {
            logger.error(e.getMessage(), e);
            SessionUtil.addSessionAttr(Constants.ERRORS, e.getErrors());
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_USER_CHANGE_PASSWORD));
        }

        SessionUtil.addSessionAttr(Constants.SUCCESS_FLUSH_MESSAGE, Constants.SUCCESS_CHANGE_PASS_MESSAGE);
        modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_USER_CHANGE_PASSWORD));
        return modelAndView;
    }

    @RequestMapping(value = Constants.UPLOAD, method = RequestMethod.POST)
     public ModelAndView photoUpload(@RequestParam(IMAGE_FILE) CommonsMultipartFile file,
                                     @RequestParam(value=Constants.USER_ID, defaultValue = Constants.EMPTY_STR) int userId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.SHOW_USER_INFO_SERVLET));
        FileUploadUtils.upload(file, userService, userId, this);
        return modelAndView;
    }

    @RequestMapping(value = Constants.CREATE_USER_SERVLET, method = RequestMethod.POST)
    public ModelAndView createUSer(User user, @RequestParam(value = "role") String[] role) {
        ModelAndView modelAndView = new ModelAndView();


        return modelAndView;
    }
}
