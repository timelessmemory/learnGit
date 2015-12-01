package com.augmentum.onlineexamsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.onlineexamsystem.util.Constants;
@Controller
public class ExceptionController {
    private static final String ERROR = "error/error";

    @RequestMapping(value = Constants.EXCEPTION, method = RequestMethod.GET)
    public ModelAndView redirectExceptionPage(@RequestParam(value = Constants.FORWARD_PAGE, defaultValue = ERROR) String forwardPage) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(forwardPage);
        return modelAndView;
    }
}
