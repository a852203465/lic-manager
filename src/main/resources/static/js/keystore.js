layui.use('laydate', function () {
    var laydate = layui.laydate;

    //执行一个laydate实例
    laydate.render({
        type: 'datetime',
        elem: '#start' //指定元素
    });

    //执行一个laydate实例
    laydate.render({
        type: 'datetime',
        elem: '#end' //指定元素
    });
});

$(function () {
    layui.use('table', function () {
        var table = layui.table;

        // 已知数据渲染
        table.render({
            elem: '#table-data',
            url: "/keystore",
            cols: [[ //标题栏
                {field: 'id', title: 'ID', sort: true},
                {field: 'name', title: '名称'},
                {field: 'validity', title: '有效期(单位:年)'},
                {field: 'password', title: '密码'},
                {field: 'createdUser', title: '添加人'},
                {
                    field: 'createdTime', title: '添加时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.createdTime)
                    }
                }
            ]],
            //skin: 'line', // 表格风格
            //even: true,
            page: true, // 是否显示分页
            parseData: function (res) {
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count": res.data.total, //解析数据长度
                    "data": res.data.records //解析数据列表
                };
            },
            limits: [50, 100, 200],
            limit: 50, // 每页默认显示的数量
            request: {
                pageName: 'currentPage',
                limitName: 'pageSize',
            }
        });
    });


})

function search() {
    var start = $("#start").val();
    var end = $("#end").val();
    var name = $("#name").val();

    console.log(start)
    console.log(end)
    console.log(name)


}


function delAll(argument) {

    var data = tableCheck.getData();

    layer.confirm('确认要删除吗？' + data, function (index) {
        //捉到所有被选中的，发异步进行删除
        layer.msg('删除成功', {icon: 1});
        $(".layui-form-checked").not('.header').parents('tr').remove();
    });
}















