<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@page import="com.augmentum.onlineexamsystem.model.Exam"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exam Detail</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
        <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/exam/exam-detail.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/exam/exam-detail.js"></script>
  </head>
  <body>
      <c:set var="user" value="${sessionScope.USER}"></c:set>
      <c:set var="exam" value="${requestScope.exam}"></c:set>
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
          <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
          <li class="exam-manage-li"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb">
          <ul>
            <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
            <li>&gt;</li>
            <li>Exam Detail</li>
          </ul>
        </div>
        <div class="content-wrap">
          <div class="content">
            <div class="qus-title">
              <label>Exam Name :</label>
              <label><c:out value="${exam.examName}" default="a default value"  escapeXml="true"/>
              (
                <c:if test="${exam.isDraft == 0}">The formal papers</c:if>
                <c:if test="${exam.isDraft == 1}">The draft papers</c:if>
              )
              </label>
            </div>
            <div class="qus-title">
              <label>Description :</label>
              <label>${exam.description}</label>
            </div>
            <div class="qus-answer">
              <label>Effective Time :</label>
              <span id="effectiveTime"><fmt:formatDate value="${exam.effectiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            </div>
            <div class="qus-answer-b">
                <label>Duration :</label>
                <span class="duration-select">${exam.duration}</span>
                <label>min</label>
            </div>
            <div class="qus-answer-c">
                <label>Question Setting :</label>
                <div class="settings">
                  <div>
                    <label class="quality-label">Question Quantity:</label>
                    <span class="quality">${exam.questionQuantity}</span>
                    <label class="point-label">Question Points:</label>
                    <span class="point">${exam.questionPoints}</span>
                  </div>
                  <div class="div2">
                    <label class="score-label">Total score:</label>
                    <span class="score">${exam.totalScore}</span>
                    <label class="criteria-label">Pass Criteria:</label>
                    <span class="criteria">${exam.passCriteria}</span>
                  </div>
                </div>
            </div>
            <div class="qus-btn">
              <label><a href="${pageContext.request.contextPath}<%=Constants.EDIT_EXAM_SERVLET%>?id=${exam.id}">Edit</a></label>
              <label>Show paper</label>
            </div>
          </div>
        </div>
        <div class="footer">Copyright©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>