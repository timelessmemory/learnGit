<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@page import="com.augmentum.onlineexamsystem.model.Question"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Question</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/question-create.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/question-edit.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/question-edit.js"></script>
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
            <li>Edit Question</li>
          </ul>
        </div>
        <div class="content-wrap">
          <div class="content">
            <c:set var="question" value="${requestScope.QUESTION}"></c:set>
            <c:if test="${question != null}">
            <form action="${pageContext.request.contextPath}<%=Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET%>?id=${question.id}" method="post" id="questionForm">
            <div class="qus-title">
              <label>Question:</label>
              <label>
                <textarea id="question-title" rows="10" cols="73" class="option_question" maxlength="200" name="title">${question.title}</textarea>
                <span class="answer-error" id="titleErrorMessage">Question is required!</span>
              </label>
            </div>
            <div class="qus-answer">
              <label>Answer:</label>
                <input type="radio" name="option" <c:if test="${question.correctAnswer == 'A'}">checked="checked"</c:if> value="A" class="radio-input-a" />
                <span class="answer-option">A</span>
                <input type="text" maxlength="80" id="answerA" name="answerA" class="option_question" value="${question.answerA}" />
                <span class="answer-error" id="answerAErrorMessage">Answer A is required!</span>
            </div>
            <div class="qus-answer-b">
                <input type="radio" class="radio-input-b" name="option" <c:if test="${question.correctAnswer == 'B'}">checked="checked"</c:if> value="B" />
                <span class="answer-option">B</span>
                <input type="text" maxlength="80" id="answerB" name="answerB" class="option_question" value="${question.answerB}" />
                <span class="answer-error" id="answerBErrorMessage">Answer B is required!</span>
            </div>
            <div class="qus-answer-c">
                <input type="radio" class="radio-input-b" name="option" <c:if test="${question.correctAnswer == 'C'}">checked="checked"</c:if> value="C" />
                <span class="answer-option">C</span>
                <input type="text" maxlength="80" id="answerC" name="answerC" class="option_question" value="${question.answerC}" />
                <span class="answer-error" id="answerCErrorMessage">Answer C is required!</span>
            </div>
            <div class="qus-answer-d">
                <input type="radio" class="radio-input-b" name="option" <c:if test="${question.correctAnswer == 'D'}">checked="checked"</c:if> value="D" />
                <span class="answer-option">D</span>
                <input type="text" id="answerD" maxlength="80" class="option_question" name="answerD" value="${question.answerD}" />
                <span class="answer-error" id="answerDErrorMessage">Answer D is required!</span>
            </div>
            <div class="qus-btn">
              <label onclick="editQues()">Save</label>
              <label onclick="history.back(-1)">Cancel</label>
            </div>
            </form>
            </c:if>
          </div>
        </div>
        <div class="footer">Copyright©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>