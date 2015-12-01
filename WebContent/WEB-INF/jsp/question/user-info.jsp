
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.model.User"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
    <c:set var="user" value="${sessionScope.USER}"></c:set>
    <div class="right">
       <div class="info-title">Basic Information</div>
       <div class="info-line"></div>
       <div class="info-content">
         <div class="info-left">
           <div>
             <label class="left-con">UserName:</label>
           </div>
           <div>
             <label class="left-con">Chinese Name:</label>
           </div>
           <div>
             <label class="left-con">ID:</label>
           </div>
           <div>
             <label class="left-con">Gender:</label>
           </div>
           <div>
             <label class="left-con">Role Type:</label>
           </div>
           <div>
             <label class="left-con">Telephone Number:</label>
           </div>
           <div>
             <label class="left-con">Email:</label>
           </div>
         </div>
         <div class="info-right">
           <div>
             <label class="right-con">${user.userName}</label>
           </div>
           <div>
             <label class="right-con">${user.chineseName}</label>
           </div>
           <div>
             <label class="right-con">${user.id}</label>
           </div>
           <div>
             <label class="right-con">${user.gender}</label>
           </div>
           <div>
             <label class="right-con"><c:forEach items="${user.roles}" var="role">${role.roleName}  </c:forEach></label>
           </div>
           <div>
             <label class="right-con">${user.telephoneNumber}</label>
           </div>
           <div>
             <label class="right-con">${user.emailAddress}</label>
           </div>
         </div>
       </div>
     </div>