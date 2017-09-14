<%--
  Created by IntelliJ IDEA.
  User: liudh
  Date: 2017/9/14
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/static/css/easyui/easyui.css"/>
    <link rel="stylesheet" href="/static/css/bootstrap.css"/>
    <script type="text/javascript" src="/static/js/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap.js"></script>
    <style type="text/css">
        /*div{border: 1px solid red}*/
        .nav-header {
            display: block;
            padding: 3px 15px;
            font-size: 11px;
            font-weight: bold;
            line-height: 20px;
            color: #999;
            text-shadow: 0 1px 0 rgba(255,255,255,0.5);
            text-transform: uppercase;
        }
        /*左侧菜单*/
        .sidebar-menu{
            border-right: 1px solid #c4c8cb;
        }
        /*一级菜单*/
        .menu-first{
            height:45px;
            line-height:45px;
            background-color: #e9e9e9;
            border-top: 1px solid #efefef;
            border-bottom: 1px solid #e1e1e1;
            padding: 0;
            font-size: 14px;
            font-weight: normal;
            text-align: center;
        }
        /*一级菜单鼠标划过状态*/
        .menu-first:hover{
            text-decoration: none;
            background-color: #d6d4d5;
            border-top: 1px solid #b7b7b7;
            border-bottom: 1px solid #acacac;
        }
        /*二级菜单*/
        .menu-second li a{
            /*background-color: #f6f6f6;*/
            height:31px;
            line-height:31px;
            border-top: 1px solid #efefef;
            border-bottom: 1px solid #efefef;
            font-size: 12px;
            text-align:center;
        }
        /*二级菜单鼠标划过样式*/
        .menu-second li a:hover {
            text-decoration: none;
            background-color: #66c3ec;
            border-top: 1px solid #83ceed;
            border-bottom: 1px solid #83ceed;
            border-right: 3px solid #f8881c;
            border-left: 3px solid #66c3ec;
        }
        /*二级菜单选中状态*/
        .menu-second-selected {
            background-color: #66c3ec;
            height:31px;
            line-height:31px;
            border-top: 1px solid #83ceed;
            border-bottom: 1px solid #83ceed;
            border-right: 3px solid #f8881c;
            border-left: 3px solid #66c3ec;
            text-align:center;
        }
        /*覆盖bootstrap的样式*/
        .nav-list,.nav-list li a{
            padding: 0px;
            margin: 0px;
        }

    </style>

    <script type="text/javascript">

        //        var addTabs = function (obj) {
        //            id = "tab_" + obj.id;
        //
        //            $(".active").removeClass("active");
        //
        //            //如果TAB不存在，创建一个新的TAB
        //            if (!$("#" + id)[0]) {
        //                //固定TAB中IFRAME高度
        //                mainHeight = $(document.body).height() - 95;
        //                //创建新TAB的title
        //                title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + obj.title;
        //                //是否允许关闭
        //                if (obj.close) {
        //                    title += ' <i class="icon-cancel3" tabclose="' + id + '"></i>';
        //                }
        //                title += '</a></li>';
        //                //是否指定TAB内容
        //
        //                console.log(obj);
        //
        //                if (obj.content) {
        //                    content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
        //                } else {//没有内容，使用IFRAME打开链接
        //                    content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + obj.url + '" width="100%" height="' + mainHeight +
        //                            '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
        //                }
        //                //加入TABS
        //                $(".nav-tabs").append(title);
        //                $(".tab-content").append(content);
        //            }
        //
        //            //激活TAB
        //            $("#tab_" + id).addClass('active');
        //            $("#" + id).addClass("active");
        //        };
        //
        //        var closeTab = function (id) {
        //            //如果关闭的是当前激活的TAB，激活他的前一个TAB
        //            if ($("li.active").attr('id') == "tab_" + id) {
        //                $("#tab_" + id).prev().addClass('active');
        //                $("#" + id).prev().addClass('active');
        //            }
        //            //关闭TAB
        //            $("#tab_" + id).remove();
        //            $("#" + id).remove();
        //        };
        //
        //        $(function () {
        //            mainHeight = $(document.body).height() - 45;
        //            $('.main-left,.main-right').height(mainHeight);
        //            $("[addtabs]").click(function () {
        //                addTabs({id: $(this).attr("id"), title: $(this).attr('title'), close: true, url: $(this).attr('url')});
        //            });
        //
        //            $(".nav-tabs").on("click", "[tabclose]", function (e) {
        //                id = $(this).attr("tabclose");
        //                closeTab(id);
        //            });
        //        });

        //$('#tt').tabs({
        //    border: false,
        //    onSelect: function (title) {
        ////            alert(title+' is selected');
        //    }
        //})
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
</head>
<body>
<div class="container-fluid">
    <div class="row" style="background-color: #5151A2">
        <div class="col-xs-6"><h3 style="color: #FFF">商家管理后台</h3></div>
        <div class="col-xs-6">
            <p class="text-right" style="padding-top: 26px; padding-right: 20px">
                <a href="#" style="color: #FFF">当前用户：Admin</a> <a href="" class="" style="color: #FFF">退出</a>
            </p>
        </div>
    </div>
    <div role="tabpanel">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
            <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Messages</a></li>
            <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">Settings</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">
                <div class="row">
                    <div class="" style="width: 240px; float: left;padding-bottom:100%">
                        <div class="sidebar-menu">
                            <a href="#userMeun" class="nav-header menu-first collapsed" data-toggle="collapse"><i class="icon-user-md icon-large"></i> 用户管理</a>
                            <ul id="userMeun" class="nav nav-list collapse in menu-second">
                                <li><a href="#"><i class="icon-user"></i> 增加用户</a></li>
                                <li><a href="#"><i class="icon-edit"></i> 修改用户</a></li>
                                <li><a href="#"><i class="icon-trash"></i> 删除用户</a></li>
                                <li><a href="#list.do" data-tab="main"><i class="icon-list"></i> 用户列表</a></li>
                            </ul>
                            <a href="#articleMenu" class="nav-header menu-first collapsed" data-toggle="collapse"><i class="icon-book icon-large"></i> 文章管理</a>
                            <ul id="articleMenu" class="nav nav-list collapse in menu-second">
                                <li><a href="#"><i class="icon-pencil"></i> 添加文章</a></li>
                                <li><a href="#"><i class="icon-list-alt"></i> 文章列表</a></li>
                            </ul>
                        </div>
                    </div>
                    <div id="main-tab" class="easyui-tabs" style="margin-left: 240px;padding-bottom:100%; height: auto">
                        <div title="首页" style="padding:10px">
                            <p style="font-size:14px">jQuery EasyUI framework helps you build your web pages easily.</p>
                            <ul>
                                <li>easyui is a collection of user-interface plugin based on jQuery.</li>
                                <li>easyui provides essential functionality for building modem, interactive, javascript applications.</li>
                                <li>using easyui you don't need to write many javascript code, you usually defines user-interface by writing some HTML markup.</li>
                                <li>complete framework for HTML5 web page.</li>
                                <li>easyui save your time and scales while developing your products.</li>
                                <li>easyui is very easy but powerful.</li>
                            </ul>
                        </div>
                        <div title="My Documents" style="padding:10px">
                            this is the docs
                            <!--<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true"></ul>-->
                        </div>
                        <div title="Help" data-options="iconCls:'icon-reload',closable:true,iconCls:'icon-mini-refresh'" style="padding:10px">
                            This is the help content.
                        </div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="profile">2</div>
            <div role="tabpanel" class="tab-pane" id="messages">3</div>
            <div role="tabpanel" class="tab-pane" id="settings">4</div>
        </div>
    </div>
</div>
</body>
</html>
