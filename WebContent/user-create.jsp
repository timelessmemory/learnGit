<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create User</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/user/user-create.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/user/user-create.js"></script>
  </head>
  <body>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <div class="dialog" id="dialog">
      <img class="close" onclick="hideDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="dialog-title">Sorry, question is not enough in system, do you confirm to save as a draft?</div>
      <div class="dialog-btn"><label onclick="saveAsDraft()">Yes</label><label onclick="hideDialog()">No</label></div>
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
            <label class="profile">
              <a href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_INFO_SERVLET%>">${user.userName}</a>
            </label>
            <label class="logout">
              <a href="${pageContext.request.contextPath}<%=Constants.LOGOUT_SERVLET%>">Logout</a>
            </label> 
          </div>
        </div>
      </div>
      <div class="menu-tool">
        <ul>
          <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">User Management</a></li>
          <li class="exam-manage-li"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Create User</a></li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb">
          <ul>
            <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">User Management</a></li>
            <li>&gt;</li>
            <li>Create User</li>
          </ul>
        </div>
        <div class="content-wrap">
          <div class="content">
          <div class="left">
            <label class="us-label">User name :</label>
            <br />
            <label class="label">Chinese name :</label>
            <br />
            <label class="label">Gender :</label>
            <br />
            <label class="label">Role type :</label>
            <br />
            <label class="label">Telephone number :</label>
            <br />
            <label class="label">Email address :</label>
            <br />
            <label class="label">Password :</label>
          </div>
          <div class="right">
            <form action="${pageContext.request.contextPath}<%=Constants.CREATE_USER_SERVLET %>" method="post" id="form">
            <div class=user-div>
              <label>
                <input type="text" placeholder="Please input the username" id="userName" name="userName" maxlength="20" />
                <span id="examNameSpan" class="error-message">User name is required!</span>
              </label>
            </div>
            <div class="name-div label">
              <label>
                <input type="text" placeholder="Please input the chinese name" id="chineseName" name="chineseName" maxlength="20" />
                <span id="descriptionSpan" class="error-message">Chinese is required!</span>
              </label>
            </div>
            <div class="gender-div label">
              <input type="radio" checked="checked" name="gender" value="Female" />
              <span id="female" class="female">Female</span>
              <input type="radio" name="gender" value="Male" class="male-radio" />
              <span id="male" class="male">Male</span>
            </div>
            <div class="role-div label">
              <input type="checkBox" checked="checked" name="role" value="ContentAdmin" />
              <span id="contentAdmin" class="contentAdmin">ContentAdmin</span>
              <input type="checkBox" name="role" value="SystemAdmin" class="systemAdmin-radio" />
              <span id="systemAdmin" class="systemAdmin">SystemAdmin</span>
              <input type="checkBox" name="role" value="Teacher" class="teacher-radio" />
              <span id="teacher" class="teacher">Teacher</span>
              <input type="checkBox" name="role" value="Student" class="student-radio" />
              <span id="student" class="student">Student</span>
            </div>
            <div class="tel-div label">
              <label>
                <input type="text" placeholder="Please input the phone number" id="telephoneNumber" name="telephoneNumber" maxlength="20" />
                <span id="examNameSpan" class="error-message">TelephoneNumber is required!</span>
              </label>
            </div>
            <div class="email-div label">
              <label>
                <input type="text" placeholder="Please input the emailAddress" id="emailAddress" name="emailAddress" maxlength="20" />
                <span id="examNameSpan" class="error-message">EmailAddress is required!</span>
              </label>
            </div>
            <div class="pass-div label">
              <label>
                <input type="password" placeholder="Please input the password" id="password" name="password" maxlength="20" />
                <span class="receive-password">Receive Password</span>
                <span id="examNameSpan" class="error-message">Password is required!</span>
              </label>
            </div>
            <div class="qus-btn">
              <label onclick="createUser()">Create</label>
              <label onclick="history.back(-1)">Cancel</label>
            </div>
            </form>
            </div>
          </div>
        </div>
        <div class="footer">Copyright©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>