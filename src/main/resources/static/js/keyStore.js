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
    search();
})

function createTable(records) {
    layui.use('table', function () {
        var table = layui.table;

        // 已知数据渲染
        table.render({
            elem: '#table-data',
            //标题栏
            cols: [[
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
            data: records,
            // skin: 'line', // 表格风格
            even: true,// 是否开启隔行背景
            page: false, // 是否显示分页
            // parseData: function (res) {
            //     return {
            //         "code": res.code, //解析接口状态
            //         "msg": res.message, //解析提示文本
            //         "count": res.data.total, //解析数据长度
            //         "data": res.data.records //解析数据列表
            //     };
            // },
            // limits: [50, 100, 200],
            // limit: 50, // 每页默认显示的数量
            // request: {
            //     pageName: 'currentPage',
            //     limitName: 'pageSize',
            // }
        });
    });
}

function search() {
    let page = pageSearch(1, 50);
    createTable(page.records);
    loadPage(page.total);
}

function pageSearch(currentPage, pageSize) {
    let start = $("#start").val();
    let end = $("#end").val();
    let name = $("#name").val();
    if (typeof start != "undefined" && start != null && start !== "") {
        start = new Date(start).getTime();
    }
    if (typeof end != "undefined" && end != null && end !== "") {
        end = new Date(end).getTime();
    }

    let pageDTO = {
        'currentPage': currentPage,
        'pageSize': pageSize,
        'startTime': start,
        'endTime': end,
        'name': name
    };
    return get("/keystore", pageDTO);
}

function delAll(argument) {

    var data = tableCheck.getData();

    layer.confirm('确认要删除吗？' + data, function (index) {
        //捉到所有被选中的，发异步进行删除
        layer.msg('删除成功', {icon: 1});
        $(".layui-form-checked").not('.header').parents('tr').remove();
    });
}

function loadPage(total) {
    layui.use(function () {
        var laypage = layui.laypage;
        laypage.render({
            elem: 'page', // 元素 id
            limit: 50, // 每页显示的条数
            count: total, // 数据总数
            limits: [50, 100, 150],//每页条数的选择项
            layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip'], // 功能布局
            jump: function (obj, first) {
                if (!first) {
                    let page = pageSearch(obj.curr, obj.limit);
                    createTable(page.records);
                }
            }
        });
    });


}

function addKeystore() {
    var $ = layui.$;
    var form = layui.form;

    layer.open({
        type: 1,
        area: [($(window).width()*0.4)+'px', ($(window).height() - 450) +'px'],
        fix: false, //不固定
        shadeClose: true,
        shade: 0.4,
        maxmin: true,
        title: '新增秘钥库',
        content: $("#keystore-add"),
        success: function (index) {
            // 对弹层中的表单进行初始化渲染
            form.render();
            // 表单提交事件
            form.on('submit(add)', function (data) {
                let field = data.field; // 获取表单字段值
                post("/keystore", field);
                layer.close(index);
                return false; // 阻止默认 form 跳转
            });
        }
    });


}










