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
    <link rel="stylesheet" href="/static/css/index.css"/>
    <script type="text/javascript" src="/static/js/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="/static/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap.js"></script>

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
                <a href="#" style="color: #FFF">当前用户:${currentUser.email} | ${currentUser.nickname}</a> <a href="" class="" style="color: #FFF">退出</a>
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
                            <a href="#userMeun" class="nav-header menu-first collapsed" data-toggle="collapse"><i class="icon-user-md icon-large"></i> 机构用户</a>
                            <ul id="userMeun" class="nav nav-list collapse in menu-second">
                                <li><a href="#/user/list.do" data-tab="main"><i class="icon-list"></i> 用户列表</a></li>
                                <li><a href="#" data-tab="main"><i class="icon-list"></i> 管理员列表</a></li>
                            </ul>
                            <a href="#articleMenu" class="nav-header menu-first collapsed" data-toggle="collapse"><i class="icon-book icon-large"></i> 系统设置</a>
                            <ul id="articleMenu" class="nav nav-list collapse in menu-second">
                                <li><a href="#/role/list.do" data-tab="main"><i class="icon-list-alt"></i>角色管理</a></li>
                                <li><a href="#"><i class="icon-pencil"></i>菜单管理</a></li>
                                <li><a href="#"><i class="icon-pencil"></i>字典管理</a></li>
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
