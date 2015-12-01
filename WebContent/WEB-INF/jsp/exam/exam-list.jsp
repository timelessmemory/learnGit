<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.model.Exam"%>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="com.augmentum.onlineexamsystem.util.Pagination"%>
<%@page import="com.augmentum.onlineexamsystem.model.Question"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page import="java.net.*"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exam List</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/exam/exam-list.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/exam/exam-list.js"></script>
   <script type="text/javascript">
        var sortArray = new Array();
        var columnArray = new Array();
        window.onload = function() {
          <%
          Map<String,String> map = (Map)request.getAttribute(Constants.ORDER_MAP);
          Iterator iterator = map.entrySet().iterator();
          while (iterator.hasNext()) {
          Entry entry = (Entry)iterator.next();%>
          columnArray.push('<%=entry.getKey()%>');
          sortArray.push('<%=entry.getValue()%>');
          <%
          }
          Entry entry = map.entrySet().iterator().next();
          String first =  (String)entry.getKey();
          String nameCondition = (String)request.getAttribute(Constants.NAME_CONDITION);
          if (nameCondition != null) {
              nameCondition = URLEncoder.encode(nameCondition);
          } else {
              nameCondition = "";
          }
          String fromDate = (String)request.getAttribute(Constants.FROM_DATE);
          String toDate = (String)request.getAttribute(Constants.TO_DATE);
          Pagination pagination = (Pagination)request.getAttribute(Constants.PAGINATION);
          String perPage = String.valueOf(pagination.getPageSize());
          String currentPage = String.valueOf(pagination.getCurrentPage());
          %>
          customPagination(<%=pagination.getCurrentPage()%>,<%=pagination.getTotalPage()%>);
          var selectObj = $('#selectPage');
          selectObj.val(<%=perPage%>);
          checkBoxFun();
          heighlight();
       }
          var urlOne = '${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>?<%=Constants.ORDER_STR%>=';
          var urlTwo = '&<%=Constants.COLUMN_STR%>=';
          var urlThree = '&<%=Constants.PER_PAGE%>=<%=perPage%>&<%=Constants.CUR_PAGE%>=';
          var urlFour = '&<%=Constants.NAME_CONDITION%>=<%=nameCondition%>&<%=Constants.FROM_DATE%>=<%=fromDate%>&<%=Constants.TO_DATE%>=<%=toDate%>';
          var urlFive = '${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>?<%=Constants.PER_PAGE%>=<%=perPage%>&<%=Constants.NAME_CONDITION%>=';
          var urlSix = '&<%=Constants.FROM_DATE%>=';
          var urlSeven = '&<%=Constants.TO_DATE%>=';
          var urlEight = '&<%=Constants.PER_PAGE%>=<%=perPage%>&<%=Constants.CUR_PAGE%>=<%=currentPage%>&<%=Constants.NAME_CONDITION%>=<%=nameCondition%>&<%=Constants.FROM_DATE%>=<%=fromDate%>&<%=Constants.TO_DATE%>=<%=toDate%>';
          var urlNine = '&<%=Constants.PER_PAGE%>=';
          var urlTen = '&<%=Constants.CUR_PAGE%>=<%=currentPage%>&<%=Constants.NAME_CONDITION%>=<%=nameCondition%>&<%=Constants.FROM_DATE%>=<%=fromDate%>&<%=Constants.TO_DATE%>=<%=toDate%>';
    </script>
  </head>
  <body onkeydown="autoClick(event, <%=pagination.getTotalPage() %>)">
    <%
    String flashMessage = (String)session.getAttribute(Constants.SUCCESS_FLUSH_MESSAGE);
    List<Exam> exams = (List<Exam>)request.getAttribute(Constants.EXAM);
    User user = (User)request.getSession().getAttribute(Constants.USER);
    if (nameCondition == null) {nameCondition = "";}
    %>
    <div id="flashMessageDiv" class="flash-message-div" style="display:<%if (flashMessage == null ) {
    %>none;<%} else { session.removeAttribute(Constants.SUCCESS_FLUSH_MESSAGE);%>block;<%}%>">
      <%= flashMessage %>
      <%if (flashMessage != null) {%>
        <script>
          setTimeout(function() {
            document.getElementById("flashMessageDiv").style.display = "none";
            window.location.reload();
          },3000);
        </script>
      <%}%>
    </div>
    <div class="tip-dialog" id="tipDialog">
      <img class="close-dialog" onclick="hideTipDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="tip-dialog-title" id="tipMessage">Please choose which to delete!</div>
      <div class="tip-dialog-btn"><label onclick="hideTipDialog()">Confirm</label></div>
    </div>
    <div class="dialog" id="dialog">
      <img class="close" onclick="hideDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="dialog-title">Are you sure to delete the selected options?</div>
      <div class="dialog-btn"><label onclick="deleteAll('${pageContext.request.contextPath}<%=Constants.DELETE_EXAM_BY_ID%>?id=')">Yes</label><label onclick="hideDialog()">No</label></div>
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
            <label class="profile"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_INFO_SERVLET%>"><%=user.getUserName()%></a></label>
            <label class="logout"><a href="${pageContext.request.contextPath}<%=Constants.LOGOUT_SERVLET%>">Logout</a></label> 
          </div>
        </div>
      </div>
      <div class="menu-tool">
        <ul>
          <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_SERVLET%>">Question Management</a></li>
          <li class="exam-manage-li">Exam Management</li>
        </ul>
      </div>
      <div class="content-whole">
        <div class="bread-crumb">
          <ul>
            <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam Management</a></li>
            <li>&gt;</li>
            <li class="exam-list-li">Exam List</li>
          </ul>
        </div>
        <div class="content-wrap">
            <div class="whole-left">
              <ul class="menu-left">
                <li class="exam-li"><a href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_SERVLET%>">Exam List</a></li>
                <li class="exam-create-li"><a href="${pageContext.request.contextPath}<%=Constants.CREATE_EXAM%>">Create Exam</a></li>
              </ul>
            </div>
            <div class="content">
              <div class="search-bar">
                <div class="search-line">
                  <input type="text" name="nameCondition" placeholder="Please input the key word" id="searchText" 
                  <%if (nameCondition != null && URLDecoder.decode(nameCondition).equals("\"")) {%> value='"' <%} else if (nameCondition != null){%>value="<%=URLDecoder.decode(nameCondition)%>" <%}%> > 
                  <label class="search">
                    <img class="search-icon" src="static/images/ICN_Search_15x20.png.png" id="searchBtn" onclick="searchForExam()"/>
                  </label>
                  <input id="dateFrom" type="date" value="<%=fromDate %>" name="fromDate" />
                  <label class="to">-</label>
                  <input id="dateTo" type="date" value="<%=toDate%>" name="toDate" />
                  <label class="dateLabel">Date</label>
                </div>
              </div>
              <div class="list">
                <div class="list-head">
                  <label <%if (first.equals(Constants.COLUMN_ID)) {%> class="user question-id id-column-click" <%} else {%> class="user question-id id-column"<%} %> id="order"><span class="col-id">ID&nbsp;</span><img id="id-img" <%if (first.equals(Constants.COLUMN_ID)) {%>class="id-img"<%}%> class="order-sort" <%if (map.get(Constants.COLUMN_ID).equals(Constants.SORT_ASC)) { %>class="increase_pic" src="static/images/ICN_Increase_10x15.png.png"<%} else {%> class="decrease_pic"  src="static/images/ICN_Decrese_10x15.png.png"<%} %> /></label>
                  <label <%if (first.equals(Constants.COLUMN_EXAM_NAME)) {%>class="user name name-column-click"<%} else {%> class="user name name-column" <%} %> id="nameOrder"><span class="col-name">Name&nbsp;</span><img id="name-img" <%if (first.equals(Constants.COLUMN_EXAM_NAME)) {%>class="name-img"<%}%> class="order-sort" <%if (map.get(Constants.COLUMN_EXAM_NAME).equals(Constants.SORT_ASC)) { %>class="increase-pic-name" src="static/images/ICN_Increase_10x15.png.png"<%} else {%> class="decrease-pic-name"  src="static/images/ICN_Decrese_10x15.png.png"<%} %> /></label>
                  <label <%if (first.equals(Constants.COLUMN_EFFECTIVE_TIME)) {%>class="user effective-time time-column-click" <%} else {%> class="user effective-time time-column" <%} %> id="timeOrder"><span class="col-time">Effective Time&nbsp;</span><img id="time-img" <%if (first.equals(Constants.COLUMN_EFFECTIVE_TIME)) {%>class="time-img"<%}%> class="order-sort" <%if (map.get(Constants.COLUMN_EFFECTIVE_TIME).equals(Constants.SORT_ASC)) { %>class="increase-pic-time" src="static/images/ICN_Increase_10x15.png.png"<%} else {%> class="decrease-pic-time"  src="static/images/ICN_Decrese_10x15.png.png"<%} %> /></label>
                  <label class="user duration">Duration(Mins)</label>
                  <label class="user creator">Creator</label>
                  <label class="edit">Edit</label>
                  <label class="check">
                    <input type="checkbox" id="checkFlag" onclick="isSelect()" />
                  </label>
                </div>
                <div class="list-items">
                 <%
                 if (exams.size() > 0) {
                 int i = 0;
                 for (Exam exam : exams) {
                     i++;
                 %>
                 <div <%if (exam.getIsDraft() == 1) {%> class="item draft-back" <%} else {%> class="item"<%} %> >
                     <label class="userid"><%=i%></label>
                     <label class="user question-id"><%=Constants.E + exam.getId() %></label>
                     <label class="user name"><a class="exam-name-a" href="${pageContext.request.contextPath}<%=Constants.SHOW_EXAM_DETAIL%>?id=<%=exam.getId()%>" title="<%=StringUtils.htmlEncode(exam.getExamName())%>"><%=StringUtils.htmlEncode(exam.getExamName())%></a></label>
                     <label class="user effective-time"><%=StringUtils.dateConvertToString(exam.getEffectiveTime())%></label>
                     <label class="user duration"><%=exam.getDuration() %></label>
                     <label class="user creator"><%=exam.getCreator() %></label>
                     <label class="edit">
                       <img src="static/images/ICN_Edit_15x15.png.png" onclick="window.location.href='${pageContext.request.contextPath}<%=Constants.EDIT_EXAM_SERVLET%>?id=<%=exam.getId()%>'" />
                     </label>
                     <label class="check">
                       <input class="checkbox" type="checkbox" value="<%=exam.getId()%>" />
                     </label>
                   </div>
                <%} }%>
                </div>
                <div class="bottom">
                  <div class="deletebtn" onclick="isShowDialog()">Delete</div>
                  <div class="pagination" id="pagination">
                    <img class="start" src="static/images/BTN_PageLeft_20x15.png.png" onclick="prepPage(<%=pagination.getCurrentPage() %>)" />
                    <div style="display:inline-block;" id="putPages"></div>
                    <img class="end" src="static/images/BTN_PageRight_20x15.png .png" onclick="nextPage(<%=pagination.getCurrentPage() %>,<%=pagination.getTotalPage() %>)" />
                    <input type="text" class="page-num" id="goPageNumber" />
                    <label class="page-go" onclick="go(<%=pagination.getTotalPage() %>)" >Go</label>
                    <label class="totalPage">TotalPage : <%=pagination.getTotalPage()%></label>
                    <select class="perPage" id="selectPage" onchange="selectChange(this)">
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