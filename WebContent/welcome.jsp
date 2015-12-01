<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
  <html>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>forward</title>
    </head>
    <body>
      <script type="text/javascript">
        window.location.href = "<%=request.getContextPath()%>/login.action";
      </script>
    </body>
</html>