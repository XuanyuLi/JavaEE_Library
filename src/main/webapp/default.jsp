<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<h1>首页</h1>
<form action="user" method="post">
    <input type="hidden" name="action" value="login">
    <input type="text" name="username" placeholder="用户名" value="admin"><br>
    <input type="password" name="password" placeholder="密码" value="123"><br>
    <input type="submit" value="登录">
</form>
${requestScope.message}
<hr>
<a href="register.jsp">注册</a>
</body>
</html>
