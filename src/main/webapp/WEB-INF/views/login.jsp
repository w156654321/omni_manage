<%--
  Created by IntelliJ IDEA.
  User: liudh
  Date: 2017/9/14
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<head>
    <title>商家后台管理系统</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="<%=basePath %>static/css/login/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>static/css/login/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>static/css/login/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath %>static/css/login/templatemo_style.css" rel="stylesheet" type="text/css">
</head>
<body class="templatemo-bg-gray">
<div class="container" style="margin-top: 120px">
    <div class="col-md-12">
        <h1 class="margin-bottom-15">商家后台管理系统</h1>
        <form class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form" action="/user/login.do" method="post">
            <div class="form-group">
                <div class="col-xs-12">
                    <div class="control-wrapper">
                        <label for="username" class="control-label fa-label"><i class="fa fa-user fa-medium"></i></label>
                        <input type="text" class="form-control" id="username" placeholder="userName" name="username" value="admin">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="control-wrapper">
                        <label for="password" class="control-label fa-label"><i class="fa fa-lock fa-medium"></i></label>
                        <input type="password" class="form-control" id="password" placeholder="passWord" name="password" value="1234">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="checkbox control-wrapper">
                        <label>
                            <input type="checkbox">记住我
                        </label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="control-wrapper">
                        <input type="submit" value="登陆" class="btn btn-info">
                        <a href="forgot-password.html" class="text-right pull-right">忘记密码?</a>
                    </div>
                </div>
            </div>
            <%--<hr>--%>
            <%--<div class="form-group">--%>
                <%--<div class="col-md-12">--%>
                    <%--<label>Login with: </label>--%>
                    <%--<div class="inline-block">--%>
                        <%--<a href="#"><i class="fa fa-facebook-square login-with"></i></a>--%>
                        <%--<a href="#"><i class="fa fa-twitter-square login-with"></i></a>--%>
                        <%--<a href="#"><i class="fa fa-google-plus-square login-with"></i></a>--%>
                        <%--<a href="#"><i class="fa fa-tumblr-square login-with"></i></a>--%>
                        <%--<a href="#"><i class="fa fa-github-square login-with"></i></a>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </form>
        <%--<div class="text-center">--%>
            <%--<a href="create-account.html" class="templatemo-create-new">Create new account <i class="fa fa-arrow-circle-o-right"></i></a>--%>
        <%--</div>--%>
    </div>
</div>
</body>
</html>
