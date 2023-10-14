$(function () {
    initDate('#start');
    initDate('#end');
    searchKeystore('#select-keystore');
    search();
})

/**
 * 查询秘钥库
 */
function searchKeystore(divId) {
    return new Promise(resolve => {
        let page = get("/keystore", {'currentPage': -1,});
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
                {title: '所属秘钥库', templet: '<div>{{d.keystore.name}}</div>'},
                {field: 'name', title: '名称'},
                {field: 'company', title: '公司'},
                {field: 'contact', title: '联系人'},
                {field: 'telephone', title: '联系电话'},
                {field: 'createdUser', title: '添加人'},
                {
                    field: 'createdTime', title: '添加时间', sort: true,
                    templet: function (row) {
                        return ts2Time(row.createdTime)
                    }
                },
                {
                    fixed: 'right', width: 130, title: '操作', toolbar: '<div class="td-manage">\n' +
                        '              <a class="layui-btn layui-btn-radius layui-btn-sm layui-bg-blue" lay-event="update" >编辑\n' +
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
    let keystoreId = $("#select-keystore").find("option:selected").val();
    let name = $("#name").val();
    let company = $("#company").val();
    let contact = $("#contact").val();
    let start = date2Timestamp($("#start").val());
    let end = date2Timestamp($("#end").val());

    let pageDTO = {
        'currentPage': currentPage,
        'pageSize': pageSize,
        'keystoreId' : keystoreId,
        'startTime': start,
        'endTime': end,
        'name': name,
        'company': company,
        'contact': contact,
    };
    return get("/project", pageDTO);
}

/**
 * 新增
 */
function add() {
    searchKeystore('#add-keystore')
        .then(res => {
            var form = layui.form;
            layer.open({
                type: 1,
                area: [($(window).width() * 0.7) + 'px', ($(window).height() - 200) + 'px'],
                fix: false, //不固定
                shadeClose: true,
                shade: 0.4,
                maxmin: true,
                title: '新增项目',
                content: $('#add-form'),
                success: function (index) {
                    form.render();
                    validate(form);

                    // 表单提交事件
                    form.on('submit(add)', function (data) {
                        let field = data.field;
                        let res = post("/project", field);
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
    searchKeystore('#update-keystore').then(res =>{
        let form = layui.form;
        layer.open({
            type: 1,
            area: [($(window).width() * 0.7) + 'px', ($(window).height() - 200) + 'px'],
            fix: false, //不固定
            shadeClose: true,
            shade: 0.4,
            maxmin: true,
            title: '修改项目',
            content: $('#update-form'),
            success: function (index) {
                form.val("layui-update-form", {
                    "keystoreId": data.keystore.id,
                    "name": data.name,
                    "company": data.company,
                    "contact": data.contact,
                    "telephone": data.telephone,
                    "description": data.description,
                });
                form.render("select");
            }
        });
        validate(form);
        form.on('submit(update)', function (new_obj) {
            let field = new_obj.field;
            field.id = data.id;
            let res = put("/project", field);
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
            if (/(^_)|(__)|(_+$)/.test(value)) return '名称首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '名称不能全为数字';
        },
        company: function (value, item) {
            if (/(^_)|(__)|(_+$)/.test(value)) return '公司名首尾不能出现 _ 下划线';
            if (/^\d+$/.test(value)) return '公司名不能全为数字';
        },
        keystoreId: function (value, item) {
            if (_.isNil(value) || _.isEmpty(value)) {
                return '秘钥库不能为空';
            }
        },
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
            delBody("/project/batch", ids);
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
        delPath("/project", obj.id);
        layer.close(index);
        search();
    });
}

/**
 * 清空条件
 */
function clean() {
    $("#start").val('');
    $("#end").val('');
    $("#name").val('');
    $("#subject").val('');
    $("#company").val('');
    $("#contact").val('');
    $("#select-keystore option[value='']").prop("selected", true);
    search();
}



