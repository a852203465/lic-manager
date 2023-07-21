$(function () {
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
    search();
})

/**
 * 创建表格
 * @param records 记录
 */
function createTable(records) {
    layui.use('table', function () {
        var table = layui.table;

        // 已知数据渲染
        table.render({
            elem: '#table-data',
            //标题栏
            cols: [[
                {
                    fixed: 'left', type: 'checkbox', toolbar: '<div class="layui-unselect layui-form-checkbox" lay-skin="primary" data-id={id}><i class="layui-icon" onclick="delAll()">&#xe605;</i></div> '
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
                {field: 'description', title: '描述'},
                {
                    fixed: 'right', width: 100, title: '操作', toolbar: '<div class="td-manage">\n' +
                        '              <a title="编辑" lay-event="update" href="javascript:;">\n' +
                        '                <i class="layui-icon">&#xe642;</i>\n' +
                        '              </a>\n' +
                        '              <a title="删除" lay-event="delete" href="javascript:;">\n' +
                        '                <i class="layui-icon">&#xe640;</i>\n' +
                        '              </a>\n' +
                        '            </div>'
                }
            ]],
            data: records,
            // skin: 'line', // 表格风格
            even: true,// 是否开启隔行背景
            page: false, // 是否显示分页
        });

        table.on('tool(table-data)', function (obj) {
            let data = obj.data;
            let layEvent = obj.event;
            if ("update" === layEvent) {
                update(data);
            }else if ("delete" === layEvent) {
                deleteData(data);
            }else if ("delAll" === layEvent) {
                delAll(data)
            }
        });
    });
}

/**
 * 查询数据
 */
function search() {
    let page = pageSearch(1, 50);
    createTable(page.records);
    loadPage(page.total);
}

/**
 * 删除
 * @param obj 数据
 */
function deleteData(obj){
    layer.confirm('确认要删除吗？',function(index){
        delPath("/keystore", obj.id);
        layer.close(index);
        search();
    });
}

/**
 * 分页查询
 * @param currentPage 当前页
 * @param pageSize 页大小
 * @returns {*} 分页数据
 */
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

/**
 * 删除所有
 */
function delAll() {
    var datas = layui.table.checkStatus('table-data').data;
    if (isNotNull(datas)) {
        let ids = [];
        datas.forEach(a => {
            ids.push(a.id);
        })
        layer.confirm('确认要删除吗？', function (index) {
            delBody("/keystore/batch", ids);
            layer.close(index);
            search();
        });
    }

}

/**
 * 加载分页数据
 * @param total 总数
 */
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

/**
 * 新增
 */
function add() {
    var form = layui.form;
    layer.open({
        type: 1,
        area: [($(window).width()*0.4)+'px', ($(window).height() - 450) +'px'],
        fix: false, //不固定
        shadeClose: true,
        shade: 0.4,
        maxmin: true,
        title: '新增秘钥库',
        content: $('#add-form'),
        success: function (index) {
            // 对弹层中的表单进行初始化渲染
            form.render();
            // 表单提交事件
            form.on('submit(add)', function (data) {
                console.log(data);
                let field = data.field;
                post("/keystore", field);
                layer.close(index);
            });
        }
    });
}

/**
 * 修改
 */
function update(data) {
    let form = layui.form;
    layer.open({
        type: 1,
        area: [($(window).width()*0.4)+'px', ($(window).height() - 450) +'px'],
        fix: false, //不固定
        shadeClose: true,
        shade: 0.4,
        maxmin: true,
        title: '修改秘钥库',
        content: $('#update-form'),
        success: function () {
            form.val("layui-update-form", {
                "name": data.name,
                "validity": data.validity,
                "password": data.password,
                "createdUser": data.createdUser,
                "createdTime": data.createdTime,
                "description": data.description,
            });
        }
    });

    form.on('submit(update)', function (new_obj) {
        let field = new_obj.field;
        field.id = data.id;
        put("/keystore", field);
        layer.closeAll();
    });



}








