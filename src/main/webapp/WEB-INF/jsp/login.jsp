<%--
  Created by IntelliJ IDEA.
  User: liudh
  Date: 2017/5/28
  Time: 12:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
        <form action="/user/login.do" method="post">
        账号：<input type="text" name="username" ><br>
        密码：<input type="text" name="password" ><br>
         <input type="submit" value="登陆">
        </form>
</body>
</html>
