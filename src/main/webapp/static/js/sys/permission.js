var datagrid;
var dialog;
$(function () {
    datagrid = $('#dg').datagrid({
        method: "get", url: '/sys/perm/page', fit: true, fitColumns: true,
        border: true, idField: 'rowId', iconCls: 'icon', animate: true,
        rownumbers: true, striped: true, singleSelect: false, selectOnCheck: true, checkOnSelect: true,
        toolbar: '#tb',
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'rowId', title: 'id', hidden: true, width: 100},
            {field: 'url', title: '资源URL', width: 100},
            {field: 'rule', title: '拦截权限', width: 100},
            {field: 'sequence', title: '排序', width: 100},
            {
                field: 'operate', title: '操作', width: 100,
                formatter: function (value, row, index) {
                    var d = "<a onclick='remove(" + row.rowId + ")' class='button-delete button-red'>删除</a>";
                    var e = "<a onclick='edit(" + row.rowId + ")' class='button-edit button-blue'>编辑</a>";
                    return e + '  ' + d + '  ';
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
    });
});
//添加权限
function add() {
    dialog = $("#dlg").dialog({
        title: '添加权限',
        width: 340,
        height: 360,
        href: '/sys/perm/add',
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/perm/save",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('添加成功');
                                dialog.dialog('close');
                                datagrid.datagrid('load',null);
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
        var row = datagrid.datagrid('getSelections');
        if (row == null) {
            U.msg('请先选择数据');
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
                url: "/sys/perm/delete/"+id,
                loading: true,
                success: function (data) {
                    if (data.code == 200) {
                        U.msg('删除成功');
                        datagrid.datagrid('load',null);
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
}
function edit(id) {
    if (id == null) {
        var row = datagrid.datagrid('getSelected');
        if (row == null) {
            U.msg('请先选择数据');
            return
        } else {
            id = row.rowId;
        }
    }
    dialog = $("#dlg").dialog({
        title: '编辑权限',
        width: 400,
        height: 500,
        href: '/sys/perm/edit/' + id,
        maximizable: false,
        modal: true,
        buttons: [{
            text: '确认',
            iconCls: 'icon-ok',
            handler: function () {
                var isValid = $("#form").form('validate');
                if (isValid) {
                    U.post({
                        url: "/sys/perm/update",
                        loading: true,
                        data: $('#form').serialize(),
                        success: function (data) {
                            if (data.code == 200) {
                                U.msg('修改成功');
                                dialog.dialog('close');
                                datagrid.datagrid('load',null);
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