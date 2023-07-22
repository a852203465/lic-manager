$(function () {
    layui.use('laydate', function () {
        let layDate = layui.laydate;

        //执行一个layDate实例
        layDate.render({
            type: 'datetime',
            elem: '#start' //指定元素
        });

        //执行一个layDate实例
        layDate.render({
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
                    fixed: 'left',
                    type: 'checkbox',
                    toolbar: '<div class="layui-unselect layui-form-checkbox" lay-skin="primary"><i class="layui-icon" onclick="delAll()">&#xe605;</i></div> '
                },
                {field: 'name', title: '名称'},
                {field: 'validity', title: '有效期(单位:年)'},
                {field: 'password', title: '密码'},
                /*{title: '私钥', toolbar: '<div class="td-manage">\n' +
                        '              <a title="下载私钥" lay-event="privateEvent" href="javascript:;">\n' +
                        '                <i class="layui-icon layui-icon-download-circle"></i>\n' +
                        '              </a>\n' +
                        '            </div>'

                },*/
                {title: '公钥', toolbar: '<div class="td-manage">\n' +
                        '              <a title="下载公钥" lay-event="publicEvent" href="javascript:;">\n' +
                        '                <i class="layui-icon layui-icon-download-circle"></i>\n' +
                        '              </a>\n' +
                        '            </div>'
                },
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
                        '               <a title="刷新秘钥" lay-event="regenerate" href="javascript:;">\n' +
                        '                <i class="layui-icon">&#xe669;</i>\n' +
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
            } else if ("delete" === layEvent) {
                deleteData(data);
            } else if ("regenerate" === layEvent) {
                regenerate(data)
            }else if ("privateEvent" === layEvent) {
                downloadKey(data, true);
            }else if ("publicEvent" === layEvent) {
                downloadKey(data, false);
            }
        });
    });
}

/**
 * 下载秘钥
 * @param data
 * @param flag
 */
function downloadKey(data, flag) {
    window.location.href="/keystore/" + data.id + "/" + flag;
}

/**
 * 重新生成秘钥
 * @param data
 */
function regenerate(data) {
    layer.confirm('确认重新生成秘钥吗？', function (index) {
        patchPath("/keystore", data.id);
        layer.close(index);
        search();
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
function deleteData(obj) {
    layer.confirm('确认要删除吗？', function (index) {
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
    if (!_.isNil(start) && !_.isEmpty(start)) {
        start = new Date(start).getTime();
    }
    if (!_.isNil(end)&& !_.isEmpty(end)) {
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
    if (!_.isNil(datas) && !_.isEmpty(datas)) {
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
        area: [($(window).width() * 0.7) + 'px', ($(window).height() - 200) + 'px'],
        fix: false, //不固定
        shadeClose: true,
        shade: 0.4,
        maxmin: true,
        title: '新增秘钥库',
        content: $('#add-form'),
        success: function (index) {
            // 对弹层中的表单进行初始化渲染
            form.render();

            validate(form);

            // 表单提交事件
            form.on('submit(add)', function (data) {
                let field = data.field;
                let res = post("/keystore", field);
                if (!isSuccess(res.code)) {
                    error(res.message);
                    return false;
                }
                return true;
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
        area: [($(window).width() * 0.7) + 'px', ($(window).height() - 200) + 'px'],
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

    validate(form);
    form.on('submit(update)', function (new_obj) {
        let field = new_obj.field;
        field.id = data.id;
        let res = put("/keystore", field);
        if (!isSuccess(res.code)) {
            error(res.message);
            return false;
        }
        return true;
    });
}

/**
 * 校验字段
 * @param form 表单对象
 */
function validate(form) {
// 自定义验证规则，如下以验证用户名和密码为例
    form.verify({
        // 函数写法
        // 参数 value 为表单的值；参数 item 为表单的 DOM 对象
        name: function (value, item) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '名称不能有特殊字符';
            }
            if (/(^_)|(__)|(_+$)/.test(value)) return '名称首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '名称不能全为数字';
        },
        // 数组中两个成员值分别代表：[正则表达式、正则匹配不符时的提示文字]
        password: [/(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/, '密码必须由字母和数字组成的至少6个字符组成'],
        validity: [/^\d+$/, '有效期只能是数字且是整数']
    });
}





