<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.JdbcPropertiesUtil"%>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>login</title>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/reset.css"></link>
    <link type="text/css" rel="stylesheet" href="<%=JdbcPropertiesUtil.getStaticUrl()%>/style/login.css"></link>
    <script src="<%=JdbcPropertiesUtil.getStaticUrl()%>/js/login.js"></script>
  </head>
  <body onkeydown="login(event)">
    <div class="whole">
      <div class="login-left">
        <div class="login-logo"></div>
        <div class="login-system-title">Online Exam Web System</div>
      </div>
      <c:set var="message" value="${sessionScope.TIP_MESSAGE}"></c:set>
      <c:set var="userName" value="${sessionScope.userName}"></c:set>
      <c:set var="visibility" value="hidden"></c:set>
      <c:set var="cookieUserName" value="${cookie.userName.value}"></c:set>
      <c:set var="password" value="${cookie.password.value}"></c:set>
      <c:if test="${message != null}">
        <c:remove var="TIP_MESSAGE" scope="session"/>
        <c:set var="visibility" value="visible"></c:set>
      </c:if>
      <div class="login-wel">Welcome to login!</div>
      <div class="login-form">
        <form action="${pageContext.request.contextPath}<%=Constants.LOGIN_SERVLET %>"  id="loginForm" method="post" >
          <input type="hidden" name="go" value="<%=request.getAttribute("go") %>" />
          <input type="hidden" name="param" id="param" />
          <div class="login-error-message" id="errorMes" style="visibility:${visibility};">${message}</div>
          <div class="login-name-line">
            <div class="login-user-name">
              <img src="static/style/images/ICN_Usename_20x20.png.png" />
              <input type="text" placeholder="Username" name="userName" id="userName" <c:if test="${userName != null}">value="${userName}" <c:remove var="userName" scope="session"/> </c:if>
              <c:if test="${cookieUserName != null}">value = "${cookieUserName}"</c:if> />
            </div>
            <div class="error-icon" id="userNameErrorIcon"></div>
          </div>
          <div class="login-password-line">
            <div class="login-password">
              <img src="static/style/images/ICN_Password_20x15.png.png" />
              <input type="password" placeholder="Password" name="password" id="password" <c:if test="${password != null}">value="${password}"</c:if> />
            </div>
            <div class="error-icon" id="passwordErrorIcon"></div>
          </div>
          <div class="login-login" onclick="verify()">Login</div>
          <div class="login-forget">
            <div class="login-forget-left">
              <input name="isRemember" type="checkbox" checked="checked" />
              <span>Remember me</span>
            </div>
            <div class="login-forget-right">
              <a href="#">Forget password?</a>
            </div>
          </div>
        </form>
      </div>
      <div class="login-footer">
        <div>Copyright©2014Augmentum, Inc. All Rights Reserved.</div>
      </div>
    </div>
  </body>
</html>