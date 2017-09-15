<%--
  Created by IntelliJ IDEA.
  User: liudh
  Date: 2017/9/14
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/static/css/bootstrap.css"/>
    <script type="text/javascript" src="/static/js/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="/static/js/require.js"></script>
    <script type="text/javascript" src="/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap.js"></script>
    <script type="text/javascript" src="/static/js/global.js"></script>
</head>
<body>
<script>
    $(function () {
        $('a[data-tab]').click(function(){
            var url = this.hash.replace('#', '');
            var tab = $(this).data('tab');
            var title = $.trim($(this).text());
            var $tabPanel = $('#' + tab + '-tab');
            if ($tabPanel.tabs('exists', title)) {
                $tabPanel.tabs('select', title);
            } else {
                $tabPanel.tabs('add',{
                    title: title,
                    href: url,
                    closable:true
                });
            }
        })
    })
</script>

<button id="me">click me</button>
<table class="table table-bordered table-hover">
    <thead>
    <tr>
        <th><input type="checkbox"/></th>
        <th>名称</th>
        <th>创建人</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><input type="checkbox"/></td>
        <td>颜色</td>
        <td>Admin</td>
    </tr>
    <tr>
        <td><input type="checkbox"/></td>
        <td>颜色</td>
        <td>Admin</td>
    </tr>
    <tr>
        <td><input type="checkbox"/></td>
        <td>颜色</td>
        <td>Admin</td>
    </tr>
    </tbody>
</table>

<script type="text/javascript">

    require(['js/common'], function(common){
        common.init();
    });

</script>

</body>
</html>
