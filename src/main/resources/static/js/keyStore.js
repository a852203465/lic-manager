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
                {
                    fixed: 'left', type: 'checkbox', toolbar: '<td> ' +
                        '<div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon">&#xe605;</i></div> ' +
                        '</td>'
                },
                {field: 'name', title: '名称'},
                {field: 'validity', title: '有效期(单位:年)'},
                {field: 'password', title: '密码'},
                {field: 'createdUser', title: '添加人'},
                {
                    field: 'createdTime', title: '添加时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.createdTime)
                    }
                },
                {
                    fixed: 'right', width: 100, title: '操作', toolbar: '<td class="td-manage">\n' +
                        '              <a title="编辑"  onclick="operationKeystore(\'编辑\')" href="javascript:;">\n' +
                        '                <i class="layui-icon">&#xe642;</i>\n' +
                        '              </a>\n' +
                        '              <a title="删除" onclick="member_del(this)" href="javascript:;">\n' +
                        '                <i class="layui-icon">&#xe640;</i>\n' +
                        '              </a>\n' +
                        '            </td>'
                }
            ]],
            data: records,
            // skin: 'line', // 表格风格
            even: true,// 是否开启隔行背景
            page: false, // 是否显示分页
        });

        // 增加点击事件
        // table.on('row(table-data)', function (obj) {
        //     console.log(obj.data);
        // })
    });
}

function search() {
    let page = pageSearch(1, 50);
    createTable(page.records);
    loadPage(page.total);
}

function member_del(obj){
    layer.confirm('确认要删除吗？',function(index){
        //发异步删除数据
        $(obj).parents("tr").remove();
        layer.msg('已删除!',{icon:1,time:1000});
    });
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

function operationKeystore(option) {
    console.log(option);
    var form = layui.form;

    layer.open({
        type: 1,
        area: [($(window).width()*0.4)+'px', ($(window).height() - 450) +'px'],
        fix: false, //不固定
        shadeClose: true,
        shade: 0.4,
        maxmin: true,
        title: option + '秘钥库',
        content: $('#keystore-option'),
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










