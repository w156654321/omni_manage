
var roleDataGrid;
$(function () {
    roleDataGrid = $('#role-dg').datagrid({
        method: "get",
        url: '/sys/role/page',
        fit: true, fitColumns: true, border: true, idField: 'rowId', striped: true,
        pagination: true, rownumbers: true, pageNumber: 1, pageSize: 20,
        pageList: [10, 20, 30, 50, 100], singleSelect: false, selectOnCheck: true,
        checkOnSelect: true,
        columns: [[
            {field: 'ck', checkbox: true},
            {field: 'rowId', title: 'id', hidden: true},
            {field: 'name', title: '角色名称', sortable: false, width: 25},
            {field: 'code', title: '角色编码', sortable: false, width: 25},
            {field: 'description', title: '描述', sortable: false, width: 50}
        ]],
        onLoadSuccess: function (data) {
            U.get({
                url: '/sys/role/user/' + $("#userId").val(),
                loading: true,
                success: function (data) {
                    if (data.code == 200) {
                        if (data.body) {
                            for (var i = 0, j = data.body.length; i < j; i++) {
                                roleDataGrid.datagrid('selectRecord', data.body[i].rowId);
                            }
                        }
                    } else {
                        U.msg('服务器异常');
                    }
                }
            });
        }
    });
});