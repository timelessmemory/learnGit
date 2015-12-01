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
<%@ taglib  uri="/WEB-INF/blockTaglib.tld" prefix="cc"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Basic Information</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/dialog.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/common.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/basic-information.css"></link>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/common.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/basic-information.js"></script>
  </head>
  <body>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <c:set var="flashMes" value="${sessionScope.TIP_MESSAGE}" scope="page"></c:set>
    <c:remove var="TIP_MESSAGE" scope="session"/>
    <div id="flashMessageDiv" class="flash-message-div" 
     style=<c:choose><c:when test="${flashMes != null}">'display:block'</c:when><c:otherwise>'display:none'</c:otherwise></c:choose> >
      ${flashMes}
      <script>hideTip();</script>
      <c:remove var="flushMessage" scope="session"/>
    </div>
    <div class="tip-dialog" id="tipDialog">
      <img class="close-dialog" onclick="hideTipDialog()" src="static/images/BTN_Close_16x16.png.png" />
      <div class="tip-dialog-title" id="tipMessage">Please choose picture like jpg, png, jpeg!</div>
      <div class="tip-dialog-btn"><label onclick="hideTipDialog()">Confirm</label></div>
    </div>
    <div id="mask" class="screen-mask"></div>
    <div class="photo-dialog" id="dialog">
      <div class="title">Set your avatar</div>
      <img src="<%=JdbcPropertiesUtil.getStaticUrl()%>/images/BTN_CLOSE.png" class="close" onclick="closeDialog()" />
      <div class="up-photo"><img class="img" alt="" id="preview" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/images/pixel.gif" /></div>
      <div class="bottom-btn">
         <form action="${pageContext.request.contextPath}<%=Constants.UPLOAD%>" method="post" enctype="multipart/form-data" id="formUpload">  
           <input name="imgFile" id="imgFile" type="file" onchange="change()" class="file-btn" />
           <input type="hidden" name="userId" value="${user.id}" />
         </form>
         <div class="add">Add a photo</div>
         <div class="submit" onclick="upload()">Set as a avatar</div>
      </div>
    </div>

    <div class="whole">
      <div class="header">
        <div class="logo"></div>
        <div class="sys-title">Online Exam Web System</div>
        <div class="head-right">
          <label class="chinese">中文</label>
          <div class="log-pro">
            <img class="pro-pic" src="<%=JdbcPropertiesUtil.getStaticUrl()%>/images/ICN_Web_PersonalInformation_20x20.png  .png" />
            <label class="profile"><a href="#">${user.userName}</a></label>
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
                <img class="photo" src="<%=JdbcPropertiesUtil.getStaticUrl()%>${user.pic}" onclick="showPhotoDialog()" />
                <div class="title-info">
                  <div class="user-name">${user.userName}</div>
                  <div class="user-id">YT00${user.id}</div>
                </div>
              </div>
              <ul class="menu-left">
                <li class="basic-info"><a id="pass-change" href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_INFO_SERVLET%>">Basic Information</a></li>
                <li><a href="${pageContext.request.contextPath}<%=Constants.SHOW_USER_CHANGE_PASSWORD%>">Change Password</a></li>
              </ul>
            </div>
            <cc:userInfo name="userInfoBlock" />
        </div>
        <div class="footer">Copyright ©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>