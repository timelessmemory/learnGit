<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@page import="com.augmentum.onlineexamsystem.model.Exam"%>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Exam</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/exam/exam-edit.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/exam/exam-edit.js"></script>
    <script type="text/javascript">
      $(function() {
    	  $("#selectDuration").val("${exam.duration}");
      })
    </script>
  </head>
  <body>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <c:set var="exam" value="${requestScope.exam}"></c:set>
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
          <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
          <li class="exam-manage-li"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb">
          <ul>
            <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
            <li>&gt;</li>
            <li>Create Exam</li>
          </ul>
        </div>
        <div class="content-wrap">
          <div class="content">
            <form action="${pageContext.request.contextPath}<%=Constants.EDIT_EXAM_SERVLET %>" method="post" id="examForm">
            <div class="qus-title">
              <label>Exam Name :</label>
              <label>
                <input type="hidden" value="${exam.id}" name="id" />
                <textarea rows="2" placeholder="Please input the exam name" id="examName" cols="72" name="examName" maxlength="100">${exam.examName}</textarea>
                <span id="examNameSpan" class="error-message">Exam name is required!</span>
              </label>
            </div>
            <div class="qus-title">
              <label>Description :</label>
              <label>
                <textarea rows="5" placeholder="Please input the description" id="description" cols="72" name="description" maxlength="200">${exam.description}</textarea>
                <span id="descriptionSpan" class="error-message">Description is required!</span>
              </label>
            </div>
            <div class="qus-answer">
              <label>Effective Time :</label>
              <fmt:formatDate value="${exam.effectiveTime}" pattern="yyyy-MM-dd HH:mm" var="time"/>
              <c:set var="time2" value="${fn:replace(time,' ', 'T')}"></c:set>
              <input type="datetime-local" id="effectiveTime" name="effectiveExamTime" value="${time2}" />
              <span id="timeSpan" class="error-message">Time is required!</span>
            </div>
            <div class="qus-answer-b">
                <label>Duration :</label>
                <select class="duration-select" id="selectDuration" name="duration">
                  <option value="30">30</option>
                  <option value="60">60</option>
                  <option value="90">90</option>
                  <option value="120">120</option>
                  <option value="150">150</option>
                </select>
                <label>min</label>
            </div>
            <div class="qus-answer-c">
                <label>Question Setting :</label>
                <div class="settings">
                  <div>
                    <label class="quality-label">Question Quantity:</label>
                    <input type="text" class="quality" name="questionQuantity" id="quantity" value="${exam.questionQuantity}" />
                    <input type="hidden" value="0" name="isDraft" id="isDraft" />
                    <label class="point-label">Question Points:</label>
                    <input type="text" class="point" name="questionPoints" id="point" value="${exam.questionPoints}" />
                  </div>
                  <span id="quantitySpan" class="error-message-bottom">Quantity is required!</span>
                  <span id="pointSpan" class="error-message-bottom">Question point is required!</span>
                  <div class="div2">
                    <label class="score-label">Total score:</label>
                    <input type="text" class="score" name="totalScore" id="totalScore" readonly="readonly" value="${exam.totalScore}" />
                    <label class="criteria-label">Pass Criteria:</label>
                    <input type="text" class="criteria" name="passCriteria" id="passCriteria" value="${exam.passCriteria}" />
                  </div>
                  <span id="scoreSpan" class="error-message-bottom">Total score is required!</span>
                  <span id="passCriteriaSpan" class="error-message-bottom">Pass criteria is required!</span>
                </div>
            </div>
            <div class="qus-btn">
              <label onclick="createExam('${pageContext.request.contextPath}<%=Constants.VALIDATION_IS_AS_DRAFT%>')">Save</label>
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