<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/1
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<link href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/static/css/login-style.css" rel="stylesheet">
<html>
<head>
    <title>Title</title>
</head>
<body>
${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/
   当前用户：${currentUser.email}${currentUser.nickname}
   <shiro:hasPermission name="/role/aaa.shtml">
       <p>你有权限看到此处!</p>
   </shiro:hasPermission>
   <shiro:hasRole name="admin">
       <p>系统管理员权限看到此处!</p>
   </shiro:hasRole>

</body>
</html>
