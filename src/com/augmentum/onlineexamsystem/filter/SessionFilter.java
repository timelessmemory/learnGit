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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.augmentum.onlineexamsystem.model.User;
import com.augmentum.onlineexamsystem.util.Constants;
import com.augmentum.onlineexamsystem.util.StringUtils;

public class SessionFilter implements Filter {
    private Logger logger = Logger.getLogger(SessionFilter.class);
    private String noNeedLoginPages;
    private static final String CSS = ".css";
    private static final String JS = ".js";
    private static final String JPG = ".jpg";
    private static final String PNG = ".png";
    private static final String GET = "get";
    private static final String NO_NEED_PAGE = "NoNeedLoginPages";
    private static final String GO_PARAM = "?go=";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String originUri = request.getRequestURI();
        String uri = "";
        try {
            uri = originUri.substring(request.getContextPath().length()+1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        String pages[] = noNeedLoginPages.split(",");
        boolean isAllow = false;
        for(String page : pages) {
            if (uri.equals(page)) {
                isAllow = true;
                break;
            }
        }

        if (uri.endsWith(CSS) || uri.endsWith(JS) || uri.endsWith(JPG) || uri.endsWith(PNG)) {
            isAllow = true;
        }

        if (isAllow) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute(Constants.USER);
            if (user == null) {
                if (request.getMethod().toLowerCase().equals(GET)) {
                    if (!StringUtils.isEmpty(request.getQueryString())) {
                        uri += "#" + request.getQueryString();
                    }
                    response.sendRedirect(request.getContextPath() + Constants.LOGIN_SERVLET + GO_PARAM + uri);
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig.getInitParameter(NO_NEED_PAGE)!= null) {
            noNeedLoginPages = filterConfig.getInitParameter(NO_NEED_PAGE);
        }
    }

    @Override
    public void destroy() {
    }

}
