<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create Question</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/question-create.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/question-create.js"></script>
  </head>
  <body>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
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
          <li class="question-manage"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
          <li class="exam-manage-li"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb">
          <ul>
            <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
            <li>&gt;</li>
            <li>Create Question</li>
          </ul>
        </div>
        <div class="content-wrap">
          <div class="content">
            <form action="${pageContext.request.contextPath}<%=Constants.CREATE_QUESTION %>" method="post" id="questionForm">
            <div class="qus-title">
              <label class="ques-label">Question:</label>
              <label>
                <textarea rows="11" placeholder="Please input the question" onblur="validationQues('${pageContext.request.contextPath}<%=Constants.VALIDATION_QUESTION%>')" id="questionTitle" cols="67" name="title" maxlength="200"></textarea>
                <span class="title-error" id="titleErrorMessage">Question is required!</span>
              </label>
            </div>
            <div class="qus-answer">
              <label class="answer-label">Answer:</label>
                <input type="radio" checked="checked" name="option"  value="A" class="radio-input-a" />
                <span class="answer-option">A</span>
                <input type="text" maxlength="80" id="answerA" name="answerA" placeholder="Please input the answer" class="option_question" />
                <span class="answer-error" id="answerAErrorMessage">Answer A is required!</span>
            </div>
            <div class="qus-answer-b">
                <input type="radio" name="option"  value="B" class="radio-input-b" />
                <span class="answer-option">B</span>
                <input type="text" id="answerB" maxlength="80" name="answerB" placeholder="Please input the answer" class="option_question" />
                <span class="answer-error" id="answerBErrorMessage">Answer B is required!</span>
            </div>
            <div class="qus-answer-c">
                <input type="radio" name="option"  value="C" class="radio-input-b" />
                <span class="answer-option">C</span>
                <input type="text" id="answerC" maxlength="80" name="answerC" placeholder="Please input the answer" class="option_question" />
                <span class="answer-error" id="answerCErrorMessage">Answer C is required!</span>
            </div>
            <div class="qus-answer-d">
                <input type="radio" name="option"  value="D" class="radio-input-b" />
                <span class="answer-option">D</span>
                <input type="text" id="answerD" maxlength="80" name="answerD" placeholder="Please input the answer" class="option_question" />
                <span class="answer-error" id="answerDErrorMessage">Answer D is required!</span>
            </div>
            <div class="qus-btn">
              <label onclick="createQues()">Create</label>
              <label onclick="history.back(-1)">Cancel</label>
            </div>
            </form>
          </div>
        </div>
        <div class="footer">Copyright©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>