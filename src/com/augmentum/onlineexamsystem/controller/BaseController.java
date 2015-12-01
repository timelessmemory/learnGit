package com.augmentum.onlineexamsystem.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.augmentum.onlineexamsystem.common.AppContext;
import com.augmentum.onlineexamsystem.util.Constants;
@Controller
public class BaseController {
    private Logger logger = Logger.getLogger(BaseController.class);
    private static final String PARAMTER_ERROR = "error/parameter-error";
    private static final String ERROR = "error/error";
    private static final String DOUBT_CHAR = "?";
    private static final String EQUAL_CHAR = "=";

    @ExceptionHandler
    public ModelAndView handleException(Exception e) {
        logger.error(e.getMessage(), e);
        e.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof TypeMismatchException) {
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.EXCEPTION + DOUBT_CHAR + Constants.FORWARD_PAGE + EQUAL_CHAR + PARAMTER_ERROR));
        } else {
            modelAndView.setView(new RedirectView(AppContext.getContextPath() + Constants.EXCEPTION + DOUBT_CHAR + Constants.FORWARD_PAGE + EQUAL_CHAR + ERROR));
        }
        return modelAndView;
    }
}
