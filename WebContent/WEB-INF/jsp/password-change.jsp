<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@page import="com.augmentum.onlineexamsystem.util.Pagination"%>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@page import="com.augmentum.onlineexamsystem.model.Question"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@ page import="java.net.*"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Change Password</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/password-change.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/password-change.js"></script>
  </head>
  <body>
    <div class="tip-dialog" id="tipDialog">
      <img class="close-dialog" onclick="hideTipDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="tip-dialog-title" id="tipMessage">Password is required!</div>
      <div class="tip-dialog-btn"><label onclick="hideTipDialog()">Confirm</label></div>
    </div>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <c:set var="flashMes" value="${sessionScope.flushMessage}" scope="page"></c:set>
    <div id="flashMessageDiv" class="flash-message-div" 
     style=<c:choose><c:when test="${flashMes != null}">'display:block'</c:when><c:otherwise>'display:none'</c:otherwise></c:choose> >
      ${flashMes}
      <c:if test="${flashMes != null}"><script>hideTip();</script></c:if>
      <c:remove var="flushMessage" scope="session"/>
    </div>
    <div id="mask" class="screen-mask"></div>

    <div class="whole">
      <div class="header">
        <div class="logo"></div>
        <div class="sys-title">Online Exam Web System</div>
        <div class="head-right">
          <label class="chinese">中文</label>
          <div class="log-pro">
            <img class="pro-pic" src="static/images/ICN_Web_PersonalInformation_20x20.png  .png" />
            <label class="profile"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_INFO_SERVLET%>">${user.userName}</a></label>
            <label class="logout"><a href="${pageContext.request.contextPath}<%=Constants.LOGOUT_SERVLET%>">Logout</a></label> 
          </div>
        </div>
      </div>
      <div class="menu-tool">
        <ul>
          <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
          <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb"></div>
        <div class="content-wrap">
            <div class="whole-left">
              <div class="left-top">
                <img class="photo" src="<%=JdbcPropertiesUtil.getStaticUrl()%>${user.pic}" />
                <div class="title-info">
                  <div class="user-name">${user.userName}</div>
                  <div class="user-id">YT00${user.id}</div>
                </div>
              </div>
              <ul class="menu-left">
                <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_INFO_SERVLET%>">Basic Information</a></li>
                <li class="pass-change"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_CHANGE_PASSWORD%>">Change Password</a></li>
              </ul>
            </div>
            <div class="right">
              <div class="info-title">Change Password</div>
              <div class="info-line"></div>
              <div class="info-content">
                <div class="info-left">
                  <div>
                    <label class="left-con">UserName:</label>
                  </div>
                  <div>
                    <label class="left-con"><span class="star">*</span>Password:</label>
                  </div>
                  <div>
                    <label class="left-con"><span class="star">*</span>Password Confirm:</label>
                  </div>
                </div>
                <div class="info-right">
                  <div>
                    <label class="right-con">${user.userName}</label>
                    <label class="error-message" id="passBoth">two password not same!</label>
                  </div>
                  <form id="form" action="${pageContext.request.contextPath}<%=Constants.SHOW_USER_CHANGE_PASSWORD %>" method="post">
                    <input type="hidden" name="userId" value="${user.id}" />
                    <div>
                      <input type="password" id="firstPassword" name="password"  maxlength="20" />
                      <label class="error-message" id="pass1" >password is required!</label>
                    </div>
                    <div>
                      <input type="password" id="secondPassword"  name="passwordConfirm" maxlength="20" />
                      <label class="error-message" id="pass2" >password is required!</label>
                    </div>
                  </form>
                </div>
                <div class="bottom-btn">
                  <div class="cancel" onclick="history.back(-1)">Cancel</div>
                  <div class="submit" id="submit">Submit</div>
                </div>
              </div>
            </div>
        </div>
        <div class="footer">Copyright ©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>