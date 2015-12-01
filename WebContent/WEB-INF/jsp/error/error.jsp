<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.augmentum.onlineexamsystem.util.Constants"%>
<!DOCTYPE>
  <html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>error</title>
      <style type="text/css">
          a:link {
            text-decoration:none;
            color: #2E4358;
          }
          a:hover {
            text-decoration:underline;
            color: #2E4358;
          }
          a:active {
            text-decoration:none;
            color: #2E4358;
          }
          a:visited {
            text-decoration:none;
            color:#2E4358;
          }
      </style>
    </head>
  <body>
    Sorry, the unknown error occurs, please contact administrator..<br />
    <a href="#" onclick='window.location.href = "<%=request.getContextPath()%><%=Constants.SHOW_QUESTION_SERVLET%>"'>return to main page..</a>
  </body>
</html>