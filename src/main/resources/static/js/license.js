$(function () {
    initDate('#start');
    initDate('#end');
    searchProject('#select-project');
    search();
})

/**
 * 查询项目
 */
function searchProject(divId) {
    return new Promise(resolve => {
        let page = get("/project", {'currentPage': -1,});
        layui.use(['form'], function () {
            let form = layui.form;
            $.each(page.records, function (index, item) {
                let option = new Option(item.name, item.id);
                // 下拉菜单里添加元素
                $(divId).append(option);
            });
            form.render("select");
            resolve(true)
        });
    })
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
                {title: '所属项目', templet: '<div>{{d.project.name}}</div>'},
                {field: 'name', title: '名称'},
                {field: 'subject', title: '证书主题'},
                {field: 'consumerAmount', title: '用户数量'},
                {
                    field: 'genTime', title: '生成时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.genTime);
                    }
                },
                {
                    field: 'expiredTime', title: '失效时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.expiredTime);
                    }
                },
                {field: 'createdUser', title: '添加人'},
                {
                    field: 'createdTime', title: '添加时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.createdTime);
                    }
                },
                {field: 'description', title: '描述'},
                {
                    fixed: 'right', width: 100, title: '操作', toolbar: '<div class="td-manage">\n' +
                        '              <a title="详情" lay-event="details" href="javascript:;">\n' +
                        '                <i class="layui-icon">&#xe63c;</i>\n' +
                        '              </a>\n' +
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
            } else if ("delete" === layEvent) {
                deleteData(data);
            }else if ("details" === layEvent) {
                details(data);
            }
        });
    });
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
 * 分页查询
 * @param currentPage 当前页
 * @param pageSize 页大小
 * @returns {*} 分页数据
 */
function pageSearch(currentPage, pageSize) {
    let keystoreId = $("#select-project").find("option:selected").val();
    let name = $("#name").val();
    let subject = $("#subject").val();
    let start = date2Timestamp($("#start").val());
    let end = date2Timestamp($("#end").val());

    let pageDTO = {
        'currentPage': currentPage,
        'pageSize': pageSize,
        'keystoreId': keystoreId,
        'startTime': start,
        'endTime': end,
        'name': name,
        'subject': subject,
    };
    return get("/license", pageDTO);
}

/**
 * 新增
 */
function add() {
    searchProject('#add-project')
        .then(res => {
            var form = layui.form;
            layer.open({
                type: 1,
                area: [($(window).width() * 0.7) + 'px', ($(window).height() - 200) + 'px'],
                fix: false, //不固定
                shadeClose: true,
                shade: 0.4,
                maxmin: true,
                title: '新增许可证',
                content: $('#add-form'),
                success: function (index) {
                    form.render();
                    validate(form);

                    form.on('submit(add)', function (data) {
                        let field = data.field;
                        let res = post("/license", field);
                        if (!isSuccess(res.code)) {
                            error(res.message);
                            return false;
                        }
                        return true;
                    });
                }
            });
        })
}

/**
 * 修改
 */
function update(data) {
    searchProject('#update-project').then(res =>{
        let form = layui.form;
        layer.open({
            type: 1,
            area: [($(window).width() * 0.7) + 'px', ($(window).height() - 200) + 'px'],
            fix: false, //不固定
            shadeClose: true,
            shade: 0.4,
            maxmin: true,
            title: '修改许可证',
            content: $('#update-form'),
            success: function (index) {
                form.val("layui-update-form", {
                    "projectId": data.project.id,
                    "name": data.name,
                    "subject": data.subject,
                    "genTime": new Date(data.genTime),
                    "expiredTime": new Date(data.expiredTime),
                    "description": data.description,
                });
                form.render("select");
            }
        });
        validate(form);
        form.on('submit(update)', function (new_obj) {
            let field = new_obj.field;
            field.id = data.id;
            let res = put("/license", field);
            if (!isSuccess(res.code)) {
                error(res.message);
                return false;
            }
            return true;
        });
    })
}

/**
 * 校验字段
 * @param form 表单对象
 */
function validate(form) {
    form.verify({
        name: function (value, item) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·-]+$").test(value)) {
                return '名称不能有特殊字符';
            }
            if (/(^_)|(__)|(_+$)/.test(value)) return '名称首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '名称不能全为数字';
        },
        subject: function (value, item) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·-]+$").test(value)) {
                return '证书主题不能有特殊字符';
            }
            if (/(^_)|(__)|(_+$)/.test(value)) return '证书主题首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '证书主题不能全为数字';
        },
        projectId: function (value, item) {
            if (_.isNil(value) || _.isEmpty(value)) {
                return '项目不能为空';
            }
        },
        description: function (value, item) {
            if (_.isNil(value) || _.isEmpty(value)) {
                return '证书描述不能为空';
            }
            if (/(^_)|(__)|(_+$)/.test(value)) return '证书主题首尾不能出现 _ 下划线';
        },
        privateAlias: function (value, item) {
            if (_.isNil(value) || _.isEmpty(value)) {
                return '证书别名不能为空';
            }
        },
        appCode: function (value, item) {
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '申请码不能有特殊字符';
            }
            if (/(^_)|(__)|(_+$)/.test(value)) return '申请码首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '申请码不能全为数字';
        },
        expiredTime: function (value, item) {
            if (_.isNil(value) || _.isEmpty(value)) {
                return '失效时间不能为空';
            }
            var now = new Date();
            var time = new Date(value);
            if(time < now) return '失效时间必须大于当前时间';
        }
    });
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
            delBody("/license/batch", ids);
            layer.close(index);
            search();
        });
    }
}

/**
 * 删除
 * @param obj 数据
 */
function deleteData(obj) {
    layer.confirm('确认要删除吗？', function (index) {
        delPath("/license", obj.id);
        layer.close(index);
        search();
    });
}

/**
 * 明细
 * @param data 数据
 */
function details(data) {
    searchProject('#details-project').then(res =>{
        let form = layui.form;

        initDate('#details-genTime');
        initDate('#details-expiredTime');

        layer.open({
            type: 1,
            area: [($(window).width() * 0.8) + 'px', ($(window).height()) + 'px'],
            fix: false, //不固定
            shadeClose: true,
            shade: 0.4,
            maxmin: true,
            title: '许可证详情',
            content: $('#details-form'),
            success: function (index) {
                form.val("layui-details-form", {
                    "projectId": data.project.id,
                    "name": data.name,
                    "subject": data.subject,
                    "privateAlias": data.privateAlias,
                    "appCode": data.appCode,
                    "consumerAmount": data.consumerAmount,
                    "genTime": ts2Time(data.genTime),
                    "expiredTime": ts2Time(data.expiredTime),
                    "description": data.description,
                });
                form.render("select");
            }
        });
        validate(form);
        form.on('submit(genLic)', function (new_obj) {
            let field = new_obj.field;
            field.id = data.id;
            field.genTime = date2Timestamp(field.genTime);
            field.expiredTime = date2Timestamp(field.expiredTime);
            let res = patchBody("/license/gen", field);
            if (!isSuccess(res.code)) {
                error(res.message);
                return false;
            }
            return true;
        });
        form.on('submit(download)', function (obj) {
            let res = getPath("/license/validate/" + data.id);
            if (!isSuccess(res.code)) {
                error(res.message);
                return false;
            }
            download(data);
        });
    })
}

/**
 * 下载授权文件
 * @param data
 */
function download(data) {
    window.open("/license/download/" + data.id, "_blank")
    // let a = document.createElement("a");
    // let objectUrl = window.URL.createObjectURL(new Blob([getPath("/license/download/" + data.id)]));
    // a.download = 'license.lic';
    // a.href = objectUrl;
    // a.click();
    // window.URL.revokeObjectURL(objectUrl);
    // a.remove();
}

/**
 * 清空条件
 */
function clean() {
    $("#start").val('');
    $("#end").val('');
    $("#name").val('');
    $("#subject").val('');
    $('#select-project').val('').trigger('chosen:updated');
    search();
}







