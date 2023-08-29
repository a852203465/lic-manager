$(function () {
    initDate('#start');
    initDate('#end');
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
                {field: 'validity', width: 130,title: '有效期(单位:年)'},
                {field: 'storePwd', title: '秘钥库密码'},
                {field: 'privatePwd', title: '私钥密码'},
                {field: 'publicPwd', title: '公钥密码'},
                /*{title: '公钥', toolbar: '<div class="private-key-manage">\n' +
                        '              <a lay-event="privateEvent" class="layui-btn layui-btn-radius layui-btn-sm layui-btn-normal">下载私钥</a>\n' +
                        '            </div>'
                },*/
                {
                    title: '公钥', toolbar: '<div class="td-manage">\n' +
                        '                       <a lay-event="publicEvent" class="layui-btn layui-btn-radius layui-btn-sm layui-bg-orange">下载公钥</a>\n' +
                        '                   </div>'
                },
                {field: 'createdUser', width: 100 ,title: '添加人'},
                {
                    field: 'createdTime', title: '添加时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.createdTime)
                    }
                },
                {
                    fixed: 'right', width: 220, title: '操作', toolbar: '<div class="td-manage">\n' +
                        '              <a class="layui-btn layui-btn-radius layui-btn-sm layui-bg-blue" lay-event="update" >编辑\n' +
                        '              </a>\n' +
                        '               <a class="layui-btn layui-btn-radius layui-btn-sm layui-bg-purple" lay-event="regenerate">刷新秘钥\n' +
                        '              </a>\n' +
                        '              <a class="layui-btn layui-btn-radius layui-btn-sm layui-bg-red" lay-event="delete">删除\n' +
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
            } else if ("privateEvent" === layEvent) {
                downloadKey(data, true);
            } else if ("publicEvent" === layEvent) {
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
    window.open("/keystore/" + data.id + "/" + flag, "_blank")
    // let a = document.createElement("a");
    // let objectUrl = window.URL.createObjectURL(new Blob([getPath("/keystore/" + data.id + "/" + flag)]));
    // if (flag) {
    //     a.download = 'privateKeys.keystore';
    // }else {
    //     a.download = 'publicCerts.keystore';
    // }
    // a.href = objectUrl;
    // a.click();
    // window.URL.revokeObjectURL(objectUrl);
    // a.remove();
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
    let start = date2Timestamp($("#start").val());
    let end = date2Timestamp($("#end").val());
    let name = $("#name").val();

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
                "storePwd": data.storePwd,
                "privatePwd": data.privatePwd,
                "publicPwd": data.publicPwd,
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
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·.\\-@#*%]+$").test(value)) {
                return '名称不能有特殊字符';
            }
            if (/(^_)|(__)|(_+$)/.test(value)) return '名称首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '名称不能全为数字';
        },
        // 数组中两个成员值分别代表：[正则表达式、正则匹配不符时的提示文字]
        storePwd: [/(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/, '秘钥库密码必须由字母和数字组成的至少6个字符组成'],
        privatePwd: [/(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/, '私钥密码必须由字母和数字组成的至少6个字符组成'],
        publicPwd: [/(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/, '公钥密码必须由字母和数字组成的至少6个字符组成'],
        validity: [/^\d+$/, '有效期只能是数字且是整数']
    });
}

/**
 * 清空条件
 */
function clean() {
    $("#start").val('');
    $("#end").val('');
    $("#name").val('');
    search();
}


