function updatePwd() {
    let currentUser = $("#currentUser").text();
    let form = layui.form;
    layer.open({
        type: 1,
        area: [($(window).width() * 0.6) + 'px', ($(window).height() - 400) + 'px'],
        fix: false, //不固定
        shadeClose: true,
        shade: 0.4,
        maxmin: true,
        title: '修改密码',
        content: $('#update-pwd-form'),
        success: function (index) {
            form.val("layui-update-pwd-form", {
                "account": currentUser,
            });
            form.render("select");
        }
    });
    form.on('submit(updatePwd)', function (new_obj) {
        let field = new_obj.field;
        if (_.isEqual(field.oldPwd, field.newPwd)) {
            error("新旧密码不能一致");
            return false;
        }

        if (!_.isEqual(field.newPwd, field.confirmPwd)) {
            error("两次输入不一致");
            return false;
        }

        let param = {
            "account": currentUser,
            "oldPwd": field.oldPwd,
            "newPwd": field.newPwd,
        }

        let res = patchBody("/userInfo/pwd", param);
        if (!isSuccess(res.code)) {
            error(res.message);
            return false;
        }
        return true;
    });
}


























