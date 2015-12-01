<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.augmentum.onlineexamsystem.util.StringUtils"%>
<%@page import="com.augmentum.onlineexamsystem.model.Question"%>
<%@ page import="com.augmentum.onlineexamsystem.util.Constants"%>
<%@page import="java.util.List"%>
<div class="list-items" id= "listItems">
     <c:set var="questions" value="${requestScope.QUESTION}"></c:set>
     <c:if test="${questions.size() > 0}">
       <c:forEach items="${questions}" var="question" varStatus="q">
           <div class="item">
             <label class="userid">${q.index + 1}</label>
             <label class="user question-id">Q00${question.id}</label>
             <label class="user description"><a class="des-a" href="${pageContext.request.contextPath}<%=Constants.SHOW_QUESTION_DETAIL%>?id=${question.id}" title='${question.title}'>${question.title}</a></label>
             <label class="edit">
                 <img src="static/images/ICN_Edit_15x15.png.png" onclick="window.location.href='${pageContext.request.contextPath}<%=Constants.EDIT_QUESTION_SHOW_DETAIL_SERVLET%>?id=${question.id}'" />
             </label>
             <label class="check">
               <input class="checkbox" type="checkbox" value="${question.id}" />
             </label>
           </div>
       </c:forEach>
     </c:if>  
</div>