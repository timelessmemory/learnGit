<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@page import="com.augmentum.onlineexamsystem.model.Question"%>
<%@page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Question Detail</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/question-detail.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/question-detail.js"></script>
  </head>
  <body>
    <c:set var="question" value="${requestScope.QUESTION}"></c:set>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <div class="dialog" id="dialog">
         <img class="close" onclick="hideDialog()" src="static/images/BTN_Close_16x16.png.png" />
         <div class="dialog-title">Are you sure to delete the selected options?</div>
         <div class="dialog-btn"><label onclick="window.location.href = '${pageContext.request.contextPath}<%=Constants.DELETE_QUESTION_SERVLET%>?id=${question.id}'">Yes</label><label onclick="hideDialog()">No</label></div>
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
            <label class="logout"><a href="${pageContext.request.contextPath}<%=Constants.LOGOUT_SERVLET%>">Logout</a></label> 
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
            <li>Question Detail</li>
          </ul>
        </div>
        <div class="content-wrap">
          <div class="content">
          <c:if test="${question != null}">
            <div class="qus-id">
              <label>Question ID</label>
              <label>${question.id}</label>
            </div>
            <div class="qus-title">
              <label>Question</label>
              <label><c:out value="${question.title}" default="a default value"  escapeXml="true"/></label>
            </div>
            <div class="qus-answer">
              <label>Answer</label>
              <label <c:if test="${question.correctAnswer == 'A'}"> style="background-color:grey;"</c:if>><span>A</span>${question.answerA}</label>
            </div>
            <div class="qus-answer-b">
              <label <c:if test="${question.correctAnswer == 'B'}"> style="background-color:grey;"</c:if>><span>B</span>${question.answerB}</label>
            </div>
            <div class="qus-answer-c">
              <label <c:if test="${question.correctAnswer == 'C'}"> style="background-color:grey;"</c:if>><span>C</span>${question.answerC}</label>
            </div>
            <div class="qus-answer-d">
              <label <c:if test="${question.correctAnswer == 'D'}"> style="background-color:grey;"</c:if>><span>D</span>${question.answerD}</label>
            </div>
            <div class="qus-btn">
              <label><a href="${pageContext.request.contextPath}<%=Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET%>?id=${question.id}">Edit</a></label>
              <label><a onclick="showDialog()">Delete</a></label>
            </div>
            </c:if>
          </div>
        </div>
        <div class="footer">Copyright©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>