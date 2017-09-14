var datagrid;
var dialog;
$(function () {
    datagrid = $('#dg').datagrid({
        method: "get", url: '/sys/user/page',
        fit: true, fitColumns: true, border: true, idField: 'rowId', striped: true,
        pagination: true, rownumbers: true, pageNumber: 1, pageSize: 20,
        pageList: [10, 20, 30, 50, 100], singleSelect: false, selectOnCheck: true,
        checkOnSelect: true, toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'rowId', title: 'rowId', hidden: true},
            {field: 'username', title: '用户名', sortable: true, width: 100},
            {field: 'name', title: '姓名', sortable: true, width: 100},
            {
                field: 'sex', title: '性别', sortable: true,
                formatter: function (value, row, index) {
                    return value == 0 ? '男' : '女';
                }
            },
            {field: 'email', title: '邮箱', sortable: true, width: 100},
            {field: 'mobile', title: '电话', sortable: true, width: 100},
            {
                field: 'operate', title: '操作', width: 100,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.rowId + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + row.rowId + ")' class='button-edit button-blue'>编辑</a>";
                    var r = "<a onclick='getRoles(" + row.rowId + ")' class='button-edit button-teal'>角色设置</a>";
                    if (row.isFixed == 1) {//固定的
                        return e + '  ' + r;
                    } else {
                        return e + '  ' + d + '  ' + r;
                    }
                }
            }
        ]],
        onLoadSuccess: function (data) {
            $('.button-delete').linkbutton({});
            $('.button-edit').linkbutton({});

            if (data) {
                $.each(data.rows,
                    function (index, item) {
                        if (item.checked) {
                            $('#dg').datagrid('checkRow', index);
                        }
                    });
            }
        },
        onSelect: function (index, row) {
            if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                $('#btn-delete').hide();
            } else {
//                    $('#btn-edit').show();
                $('#btn-delete').show();
            }
        },
        queryParams: {
            s_username: $('#username').val(),
            s_mobile: $('#mobile').val(),
            s_sex: $('#sex').val()
        }
    });
});
//查询
function queryUsers() {
    $(datagrid).datagrid('load', {
            s_username: $('#username').val(),
            s_mobile: $('#mobile').val(),
            s_sex: $('#sex').val()
        }
    );
}
//添加用户
function add() {
    dialog = $("#dlg").dialog({
        title: '添加用户',
        width: 340,
        height: 360,
        href: '/sys/user/add',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/user/save",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog.dialog('close');
                                queryUsers();
                            } else if (data.code == 409) {
                                U.msg('用户已存在');
                            } else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });
}
function edit(id) {
    if (id == null) {
        var row = datagrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择用户');
            return
        } else {
            id = row.rowId;
        }
    }
    dialog = $("#dlg").dialog({
        title: '编辑用户',
        width: 400,
        height: 500,
        href: '/sys/user/edit/' + id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/user/update",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog.dialog('close');
                                queryUsers();
                            }else {
                                U.msg('服务器异常');
                            }
                        }
                    });
                }
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });
}
function remove(id) {
    if (id == null) {
        var row = datagrid.datagrid('getSelections');//获取所以选中的列
        if (row == null) {
            U.msg('请先选择用户');
            return
        } else {
            var rows = $.map(row, function (row) {
                return row.rowId;
            });
            id = rows.join(",");
        }
    }
    $.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
        if (data) {
            U.post({
                url: "/sys/user/delete",
                loading: true,
                data: {rowId: id},
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        queryUsers();
                    }else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}
function getRoles(id) {
    if (U.isEmpty(id)) {
        var row = datagrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择用户');
            return
        } else {
            id = row.rowId;
        }
    }
    dialog = $("#dlg").dialog({
        title: '用户角色管理',
        width: 600,
        height: 400,
        href: '/sys/user/role/list/'+id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                U.post({
                    url: '/sys/user/'+ id+'/role/change',
                    loading: true,
                    data: {
                        roleId: getSelectedRoles()
                    },
                    success: function (data) {
                        if (data.code == 200) {
                            U.msg('修改成功');
                            dialog.dialog('close');
                            queryUsers();
                        }else {
                            U.msg('服务器异常');
                        }
                    }
                });
            }
        }, {
            text: '取消',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });
}
/**
 * 获取选择的用户角色
 * @returns {Array}
 */
function getSelectedRoles() {
    //所选的角色列表
    var roleIds = '';
    var data = $('#role-dg').datagrid('getSelections');
    var rows = $.map(data, function (row) {
        return row.rowId;
    });
    return rows.join(",");
}