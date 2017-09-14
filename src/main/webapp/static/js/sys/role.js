var roleDataGrid, resourceDataGrid, dialog;
$(function () {
    roleDataGrid = $('#dg').datagrid({
        method: "get", url: '/sys/role/page',
        fit: true, fitColumns: true, border: true, idField: 'rowId', striped: true,
        pagination: true, rownumbers: true, pageNumber: 1, pageSize: 20,
        pageList: [10, 20, 30, 50, 100], singleSelect: true, selectOnCheck: true,
        checkOnSelect: true, toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'rowId', title: 'id', hidden: true},
            {field: 'name', title: '角色名称', sortable: false, width: 20},
            {field: 'code', title: '角色编码', sortable: false, width: 20},
            {field: 'description', title: '描述', sortable: false, width: 25},
            {
                field: 'operate', title: '操作', width: 35,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.rowId + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + row.rowId + ")' class='button-edit button-blue'>编辑</a>";
                    var r = "<a onclick='getUsers(" + row.rowId + ")' class='button-edit button-info'>查看用户</a>";
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

            if (data != null && data.rows != null && data.rows.length > 0) {
                $('#dg').datagrid('checkRow', 0);
            }
        },
        onSelect: function (index, row) {
            queryResourceByRole(row.rowId);

            if (row.isFixed == 1) {//固定的
//                    $('#btn-edit').hide();
                $('#btn-delete').hide();
            } else {
//                    $('#btn-edit').show();
                $('#btn-delete').show();
            }
        }
    });
    //菜单资源列表
    resourceDataGrid = $('#rdg').treegrid({
        method: "get", url: '/sys/resource/menus', fit: true, fitColumns: true,
        border: true, idField: 'rowId', treeField: 'name', iconCls: 'icon', animate: true,
        rownumbers: true, striped: true, singleSelect: false, selectOnCheck: true, checkOnSelect: true,
        toolbar: '#rtb', footer: '#rft',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'rowId', title: 'id', hidden: true, width: 100},
            {field: 'name', title: '资源/权限名称', width: 100},
            {field: 'description', title: '描述', width: 100, tooltip: true}
        ]],
        onClickRow: function (row) {
            //级联选择
            $(this).treegrid('cascadeCheck', {
                id: row.id, //节点ID
                deepCascade: true //深度级联
            });
        },
        onLoadSuccess: function (data) {
        }
    });
});

/**
 * 获取角色
 */
function queryRoles() {
    $(roleDataGrid).datagrid('load', {});
}

/**
 * 添加角色
 */
function add() {
    dialog = $("#dlg").dialog({
        title: '添加角色',
        width: 340,
        height: 260,
        href: '/sys/role/add',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/role/save",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog.dialog('close');
                                queryRoles();
                            }else if (data.code == 409) {
                                U.msg('角色名称已存在');
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

/**
 * 编辑角色
 */
function edit(id) {
    if (id == null) {
        var row = roleDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择用户');
            return
        } else {
            id = row.rowId;
        }
    }

    dialog = $("#dlg").dialog({
        title: '编辑角色',
        width: 340,
        height: 260,
        href: '/sys/role/edit/' + id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/role/update",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog.dialog('close');
                                queryRoles();
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
/**
 * 删除角色
 */
function remove(id) {
    if (id == null) {
        var row = roleDataGrid.datagrid('getSelections');
        if (row == null) {
            U.msg('请先选择角色');
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
                url: "/sys/role/delete/",
                loading: true,
                data: {ids: id},
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        queryRoles();
                    } else if (data.code == 424) {
                        U.msg('该角色已被使用，无法删除');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}

/**
 * 查看用户
 * @param id
 */
function getUsers(id) {
    if (U.isEmpty(id)) {
        var row = roleDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择角色');
            return
        } else {
            id = row.rowId;
        }
    }

    dialog = $("#dlg").dialog({
        title: '用户管理',
        width: 650,
        height: 450,
        href: '/sys/role/user/list/' + id ,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '取消',
            handler: function () {
                dialog.dialog('close');
            }
        }]
    });
}

/**
 * 获取角色拥有的权限
 * @param roleId
 */
function queryResourceByRole(roleId) {
    resourceDataGrid.treegrid('unselectAll');
    U.get({
        url: "/sys/resource/menu/role/" + roleId,
        loading: true,
        success: function (data) {
            if (data.code == 200) {
                var result = data.body;
                for (var i = 0, j = result.length; i < j; i++) {
                    resourceDataGrid.treegrid('select', result[i].rowId);
                    resourceDataGrid.treegrid('checkRow', result[i].rowId);
                }
            } else {
                U.msg('服务器异常');
            }
        }
    });
}

/**
 * 保存修改权限
 */
function saveRolePermission() {
    var roleRow = roleDataGrid.datagrid('getSelected');
    if (roleRow == null || roleRow.rowId == null) {
        U.msg('请先选择角色');
        return
    }
    $.messager.confirm('提示', '确认要保存授权？', function (data) {
        if (data) {
            U.post({
                url: '/sys/role/' + roleRow.rowId + '/resource/menu/change',
                loading: true,
                data: {
                    resourceIds: getSelectedResources()
                },
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('修改成功');
                        queryRoles();
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}

/**
 * 获取所选的权限
 * @returns {Array}
 */
function getSelectedResources() {
    var resources = '';
    var data = resourceDataGrid.datagrid('getSelections');
    var rows = $.map(data, function (row) {
        return row.rowId;
    });
    return rows.join(",");
}