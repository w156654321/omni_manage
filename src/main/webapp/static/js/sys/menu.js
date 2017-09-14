var resourceDataGrid, dialog;
$(function () {
    resourceDataGrid = $('#rdg').treegrid({
        method: "get",
        url: '/sys/resource/menus',
        fit: true,
        fitColumns: true,
        border: false,
        idField: 'rowId',
        treeField: 'name',
        iconCls: 'icon',
        animate: true,
        rownumbers: true,
        striped: true,
        singleSelect: false,
        selectOnCheck: true,
        checkOnSelect: true,
        toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'rowId', title: 'id', hidden: true, width: 100},
            {field: 'name', title: '菜单名称', width: 100},
            {field: 'url', title: '路径', width: 100},
            {field: 'sequence', title: '排序', width: 100},
            {field: 'description', title: '描述', width: 100, tooltip: true}
        ]],
        onLoadSuccess: function (data) {
        },
        onSelect: function (row) {
            if (row.isFixed && row.isFixed == 1) {//固定的
                $('#btn-delete').hide();
            } else {
                $('#btn-delete').show();
            }
        },
        queryParams: {
            type: 0
        }
    });
});

/**
 * 获取菜单
 */
function queryResources() {
    $(resourceDataGrid).treegrid('load', {
        type: 0
    });
}

/**
 * 添加菜单
 */
function add() {
    dialog = $("#dlg").dialog({
        title: '添加菜单',
        width: 500,
        height: 400,
        href: '/sys/resource/add',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/resource/menu/save",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog.dialog('close');
                                queryResources();
                            } else if (data.code == 409) {
                                U.msg('菜单名称已存在');
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
 * 编辑菜单
 */
function edit(id) {
    if (id == null) {
        var row = resourceDataGrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择菜单');
            return
        } else {
            id = row.rowId;
        }
    }

    dialog = $("#dlg").dialog({
        title: '编辑菜单',
        width: 500,
        height: 400,
        href: '/sys/resource/edit/' + id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/resource/menu/update",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog.dialog('close');
                                queryResources();
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
 * 删除菜单
 */
function remove(id) {
    if (id == null) {
        var row = resourceDataGrid.datagrid('getSelections');//获取所以选中的列
        if (row == null) {
            U.msg('请先选择菜单');
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
                url: "/sys/resource/menu/delete/"+id,
                loading: true,
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        queryResources();
                    } else if (data.code == 424) {
                        U.msg('该菜单已被使用，无法删除');
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}
