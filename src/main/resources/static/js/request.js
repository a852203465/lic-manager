// let notice;
$(function () {
    $.ajaxSetup({
        async: false,
        // headers: {
        //     'Authorization': 'Bearer ' + token
        // }
    });
    // layui.config({
    //     base: 'plugins/notice/'
    // })
    // layui.use(['notice'], function () {
    //     notice = layui.notice;
    //     // 初始化配置，同一样式只需要配置一次，非必须初始化，有默认配置
    //     notice.options = {
    //         closeButton: false,//显示关闭按钮
    //         debug: false,//启用debug
    //         positionClass: "toast-top-right",//弹出的位置,
    //         showDuration: "300",//显示的时间
    //         hideDuration: "1000",//消失的时间
    //         timeOut: "2000",//停留的时间
    //         extendedTimeOut: "1000",//控制时间
    //         showEasing: "swing",//显示时的动画缓冲方式
    //         hideEasing: "linear",//消失时的动画缓冲方式
    //         iconClass: 'toast-info', // 自定义图标，有内置，如不需要则传空 支持layui内置图标/自定义iconfont类名
    //         onclick: null, // 点击关闭回调
    //     };
    // });
})

function get(url, params) {
    let data;
    $.get(url, params, function (res) {
        data = callback(res);
    },'json');
    return data;
}

function post(url, params) {
    let data;
    $.ajax({
        method: "POST",
        url: url,
        async: false,
        contentType: 'application/json',
        data: JSON.stringify(params),
        success: function(res) {
            data = res;
        }
    });
    return data;
}

function put(url, params) {
    let data;
    $.ajax({
        method: "PUT",
        url: url,
        async: false,
        contentType: 'application/json',
        data: JSON.stringify(params),
        success: function(res) {
            data = callback(res);
        }
    });
    return data;
}

function delPath(url, param) {
    let data;
    $.ajax({
        method: "DELETE",
        url: url + "/" + param,
        async: false,
        contentType: 'application/json',
        success: function(res) {
            data = callback(res);
        }
    });
    return data;
}

function patchPath(url, param) {
    let data;
    $.ajax({
        method: "PATCH",
        url: url + "/" + param,
        async: false,
        contentType: 'application/json',
        success: function(res) {
            data = callback(res);
        }
    });
    return data;
}

function delBody(url, body) {
    let data;
    $.ajax({
        method: "DELETE",
        url: url,
        async: false,
        data: JSON.stringify(body),
        contentType: 'application/json',
        success: function(res) {
            data = callback(res);
        }
    });
    return data;
}

function callback(res) {
    if (res.code !== 0) {
        // notice.error(res.message);
        return false;
    }
    return res.data;
}





















