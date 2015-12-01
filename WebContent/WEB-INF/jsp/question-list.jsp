<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.util.Pagination"%>
<%@page import="com.augmentum.onlineexamsystem.model.Question"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@page import="java.util.List"%>
<%@ page import="java.net.*"%>
<%@ taglib  uri="/WEB-INF/blockTaglib.tld" prefix="cc"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Question List</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/question-list.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/question-list.js"></script>
    <script type="text/javascript">
       var isSearch;
       var returnPerPage;
       var orderflag;
       var questionCondition;
       var returnCurrentPage;
       var returnTotalPage;
       var questionPrefix = 'Q00';
       var detailhref = "${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_DETAIL%>?id=";
       var editHref = "${pageContext.request.contextPath}<%=Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET%>?id=";
       var path = "${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>";
       $(function() {
          returnCurrentPage = ${requestScope.pagination.currentPage};
          returnTotalPage = ${requestScope.pagination.totalPage};
          questionCondition = '${requestScope.questionCondition}';
          returnPerPage = ${requestScope.pagination.pageSize};
          orderflag = '${requestScope.orderFlag}';
          autoCheckboxBind();
          showPic(orderflag);
          customPagination(returnCurrentPage, returnTotalPage, path);
          selectFun(returnPerPage);
      })
    </script>
  </head>
  <body onkeydown="autoClick(event, '${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>', returnTotalPage)">
    <c:set var="flashMes" value="${sessionScope.flushMessage}" scope="page"></c:set>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <div id="flashMessageDiv" class="flash-message-div" 
     style=<c:choose><c:when test="${flashMes != null}">'display:block'</c:when><c:otherwise>'display:none'</c:otherwise></c:choose> >
      ${flashMes}
      <c:if test="${flashMes != null}"><script>hideTip();</script></c:if>
      <c:remove var="flushMessage" scope="session"/>
    </div>
    <div class="tip-dialog" id="tipDialog">
      <img class="close-dialog" onclick="hideTipDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="tip-dialog-title" id="tipMessage">Please choose which to delete!</div>
      <div class="tip-dialog-btn"><label onclick="hideTipDialog()">Confirm</label></div>
    </div>
    <div class="dialog" id="dialog">
      <img class="close" onclick="hideDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="dialog-title">Are you sure to delete the selected options?</div>
      <div class="dialog-btn"><label onclick="deleteAll('${pageContext.request.contextPath}<%=Constants.AJAX_DELETE_QUESTION_SERVLET%>', '${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>')">Yes</label><label onclick="hideDialog()">No</label></div>
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
          <li>Question Management</li>
          <li class="exam-manage-li"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb">
          <ul>
            <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
            <li>&gt;</li>
            <li>Question List</li>
          </ul>
        </div>
        <div class="content-wrap">
            <div class="whole-left">
              <ul class="menu-left">
                <li class="ques-list"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question List</a></li>
                <li class="ques-create-li"><a href="${pageContext.request.contextPath}<%=Constants.CREATE_QUESTION%>">Create Question</a></li>
              </ul>
            </div>
            <div class="content">
              <div class="search-bar">
                <div class="search-line">
                  <input type="text" placeholder="Please input the key word" id="searchText" maxlength="21" />
                  <label class="search">
                    <img class="search-icon" src="static/images/ICN_Search_15x20.png.png" id="searchBtn" onclick="search('${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>')" />
                  </label>
                </div>
              </div>
              <div class="list">
                <div class="list-head">
                  <label class="user question-id">ID&nbsp;<img id="order" onclick="sort('${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>')" /></label>
                  <label class="user description">Description</label>
                  <label class="edit">Edit</label>
                  <label class="check">
                    <input type="checkbox" id="checkFlag" onclick="isSelect()" />
                  </label>
                </div>
                <cc:userInfo name="itemsListBlock" />
                <div class="bottom">
                  <div class="deletebtn" onclick="isShowDialog()">Delete</div>
                  <div class="pagination" id="pagination">
                    <img class="start" src="static/images/BTN_PageLeft_20x15.png.png" onclick="prepPage(returnCurrentPage, '${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>')" />
                    <div style="display:inline-block;" id="putPages"></div>
                    <img class="end" src="static/images/BTN_PageRight_20x15.png .png" onclick="nextPage(returnCurrentPage, returnTotalPage, '${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>')" />
                    <input type="text" class="page-num" id="goPageNumber" />
                    <label class="page-go" onclick="go(returnTotalPage, '${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_AJAX%>')" >Go</label>
                    <label class="totalPage">Per page</label>
                    <select class="perPage" id="selectPage">
                      <option value="5">5</option>
                      <option value="10">10</option>
                      <option value="15">15</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
        </div>    
        <div class="footer">Copyright ©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>