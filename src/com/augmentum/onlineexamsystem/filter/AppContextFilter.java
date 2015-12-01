package com.augmentum.onlineexamsystem.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.augmentum.onlineexamsystem.common.AppContext;
import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.util.Constants;

public class AppContextFilter implements Filter {

    private static final String CSS = ".css";
    private static final String JS = ".js";
    private static final String JPG = ".jpg";
    private static final String PNG = ".png";
    private Logger logger = Logger.getLogger(AppContextFilter.class);

    public AppContextFilter() {
    }

   @Override
    public void destroy() {
    }

       @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest request = (HttpServletRequest)servletRequest;
       HttpServletResponse response = (HttpServletResponse)servletResponse;

       String originUri = request.getRequestURI();
       System.out.println(originUri);
       String uri = "";
       try {
           uri = originUri.substring(request.getContextPath().length()+1);
       } catch (Exception e) {
           logger.error(e.getMessage(), e);
           e.printStackTrace();
       }
       if (uri.endsWith(CSS) || uri.endsWith(JS) || uri.endsWith(JPG) || uri.endsWith(PNG)) {
           chain.doFilter(request, response);
           return;
       }
       AppContext.setContextPath(request.getContextPath());
       AppContext appContext = AppContext.getAppContext();
       appContext.addDataKeyValue(Constants.SESSION, request.getSession());
       User user = (User) request.getSession().getAttribute(Constants.USER);
       if (user != null) {
           appContext.addDataKeyValue(Constants.USER, user.getUserName());
       }

       try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            appContext.clearData();
        }
   }

       @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
